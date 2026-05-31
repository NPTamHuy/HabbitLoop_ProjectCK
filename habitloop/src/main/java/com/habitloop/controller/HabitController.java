package com.habitloop.controller;

import com.habitloop.entity.*;
import com.habitloop.service.HabitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.habitloop.repository.UserRepository;
import java.util.List;

@Controller
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;
    private final UserRepository userRepository;

    public HabitController(HabitService habitService, UserRepository userRepository) {
        this.habitService = habitService;
        this.userRepository = userRepository;
    }

    // Danh sách habit
    @GetMapping
    public String listHabits(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Long userId = getUserId(userDetails);
        List<Habit> habits = habitService.getHabitsByUser(userId);
        model.addAttribute("habits", habits);
        model.addAttribute("checkedInToday", habits.stream()
                .filter(h -> habitService.isCheckedInToday(h.getId()))
                .map(Habit::getId)
                .toList());
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);
        return "habit/list";
    }

    // Form tạo habit
    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);
        return "habit/create";
    }

    // Xử lý tạo habit
    @PostMapping("/create")
    public String createHabit(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam String name,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String icon,
                               @RequestParam(required = false) String color,
                               @RequestParam String frequency,
                               RedirectAttributes redirectAttributes) {
        Long userId = getUserId(userDetails);
        habitService.createHabit(userId, name, description, icon, color, frequency);
        redirectAttributes.addFlashAttribute("success", "Tạo habit thành công! 🎉");
        return "redirect:/habits";
    }

    // Check-in
    @PostMapping("/{id}/checkin")
    public String checkin(@AuthenticationPrincipal UserDetails userDetails,
                          @PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        Long userId = getUserId(userDetails);
        String message = habitService.checkin(id, userId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/habits";
    }

    // Xóa habit
    @PostMapping("/{id}/delete")
    public String deleteHabit(@AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        Long userId = getUserId(userDetails);
        habitService.deleteHabit(id, userId);
        redirectAttributes.addFlashAttribute("success", "Đã xóa habit!");
        return "redirect:/habits";
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow().getId();
    }
}