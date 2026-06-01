package com.habitloop.controller;

import com.habitloop.entity.*;
import com.habitloop.repository.UserRepository;
import com.habitloop.service.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final HabitService habitService;
    private final NotificationService notificationService;

    public ChallengeController(ChallengeService challengeService,
                                UserRepository userRepository,
                                SimpMessagingTemplate messagingTemplate,
                                HabitService habitService,
                                NotificationService notificationService) {
        this.challengeService = challengeService;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
        this.habitService = habitService;
        this.notificationService = notificationService;
    }

    // Danh sách challenge
    @GetMapping
    public String listChallenges(@AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        Long userId = getUserId(userDetails);
        model.addAttribute("publicChallenges",
            challengeService.getPublicChallenges());
        model.addAttribute("myChallenges",
            challengeService.getMyChallenges(userId));
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);
        return "challenge/list";
    }

    // Form tạo challenge
    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);
        return "challenge/create";
    }

    // Xử lý tạo challenge
    @PostMapping("/create")
    public String createChallenge(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam String title,
                                   @RequestParam(required = false) String description,
                                   @RequestParam String startDate,
                                   @RequestParam String endDate,
                                   @RequestParam(defaultValue = "false") boolean isPublic,
                                   RedirectAttributes redirectAttributes) {
        Long userId = getUserId(userDetails);
        Challenge challenge = challengeService.createChallenge(
            userId, title, description,
            LocalDate.parse(startDate),
            LocalDate.parse(endDate),
            isPublic
        );
        redirectAttributes.addFlashAttribute("success",
            "Tạo challenge thành công! Mã mời: " + challenge.getInviteCode());
        return "redirect:/challenges";
    }

    // Tham gia bằng invite code
    @PostMapping("/join")
    public String joinChallenge(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam String inviteCode,
                                 RedirectAttributes redirectAttributes) {
        Long userId = getUserId(userDetails);
        String result = challengeService.joinByCode(userId, inviteCode);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/challenges";
    }

    // Trang chi tiết challenge + leaderboard
    @GetMapping("/{id}")
    public String challengeDetail(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable Long id,
                                   Model model) {
        Long userId = getUserId(userDetails);

        System.out.println("=== challengeDetail: id=" + id + ", userId=" + userId);
        System.out.println("=== isMember: " + challengeService.isMember(id, userId));

        Challenge challenge = challengeService.getChallengeById(id);

        if (!challengeService.isMember(id, userId)) {
            return "redirect:/challenges";
        }

        model.addAttribute("challenge", challenge);
        model.addAttribute("leaderboard", challengeService.getLeaderboard(id));
        model.addAttribute("activityFeed", challengeService.getActivityFeed(id));
        model.addAttribute("myHabits", habitService.getHabitsByUser(userId));
        model.addAttribute("isMember", true);
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);
        return "challenge/detail";
    }
    // Check-in trong challenge (push WebSocket)
    @PostMapping("/{id}/checkin")
    public String checkinChallenge(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable Long id,
                                    @RequestParam(required = false) Long habitId,
                                    RedirectAttributes redirectAttributes) {
        Long userId = getUserId(userDetails);

        String result = challengeService.checkinChallenge(id, habitId, userId);

        if ("already".equals(result)) {
            redirectAttributes.addFlashAttribute("message", "Bạn đã check-in hôm nay rồi!");
            return "redirect:/challenges/" + id;
        }

        // Push WebSocket
        List<ChallengeMember> leaderboard = challengeService.getLeaderboard(id);
        List<java.util.Map<String, Object>> leaderboardDto = leaderboard.stream()
            .map(cm -> {
                java.util.Map<String, Object> map = new java.util.HashMap<>();
                map.put("username", cm.getUser().getUsername());
                map.put("totalCheckins", cm.getTotalCheckins());
                return map;
            })
            .collect(java.util.stream.Collectors.toList());

        messagingTemplate.convertAndSend(
            "/topic/challenge/" + id + "/leaderboard", leaderboardDto);

        redirectAttributes.addFlashAttribute("success", "Check-in thành công! 🔥");
        return "redirect:/challenges/" + id;
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow().getId();
    }
}