package com.habitloop.service;

import com.habitloop.entity.*;
import com.habitloop.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMemberRepository challengeMemberRepository;
    private final UserRepository userRepository;
    private final CheckinRepository checkinRepository;
    private final NotificationRepository notificationRepository;
    private final ActivityFeedRepository activityFeedRepository;

    public ChallengeService(ChallengeRepository challengeRepository,
                            ChallengeMemberRepository challengeMemberRepository,
                            UserRepository userRepository,
                            CheckinRepository checkinRepository,
                            NotificationRepository notificationRepository,
                            ActivityFeedRepository activityFeedRepository) {
        this.challengeRepository = challengeRepository;
        this.challengeMemberRepository = challengeMemberRepository;
        this.userRepository = userRepository;
        this.checkinRepository = checkinRepository;
        this.notificationRepository = notificationRepository;
        this.activityFeedRepository = activityFeedRepository;
    }

    // Lấy danh sách challenge công khai
    public List<Challenge> getPublicChallenges() {
        return challengeRepository.findPublicActiveChallenges(LocalDate.now());
    }

    // Lấy challenge user đang tham gia
    public List<Challenge> getMyChallenges(Long userId) {
        return challengeRepository.findChallengesByUserId(userId);
    }

    // Tạo challenge mới
    public Challenge createChallenge(Long userId, String title, String description,
                                      LocalDate startDate, LocalDate endDate,
                                      boolean isPublic) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Challenge challenge = new Challenge();
        challenge.setCreator(user);
        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setStartDate(startDate);
        challenge.setEndDate(endDate);
        challenge.setIsPublic(isPublic);
        challenge.setInviteCode(generateInviteCode());

        challenge = challengeRepository.save(challenge);

        // Người tạo tự động tham gia
        ChallengeMember member = new ChallengeMember();
        member.setChallenge(challenge);
        member.setUser(user);
        challengeMemberRepository.save(member);

        return challenge;
    }

    // Tham gia challenge bằng invite code
    public String joinByCode(Long userId, String inviteCode) {
        Challenge challenge = challengeRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Mã mời không hợp lệ!"));

        if (challengeMemberRepository.existsByChallengeIdAndUserId(
                challenge.getId(), userId)) {
            return "Bạn đã tham gia challenge này rồi!";
        }

        User user = userRepository.findById(userId).orElseThrow();
        ChallengeMember member = new ChallengeMember();
        member.setChallenge(challenge);
        member.setUser(user);
        challengeMemberRepository.save(member);

        // Tạo activity feed
        ActivityFeed feed = new ActivityFeed();
        feed.setUser(user);
        feed.setChallenge(challenge);
        feed.setMessage(user.getUsername() + " đã tham gia challenge! 🎉");
        activityFeedRepository.save(feed);

        return "Tham gia thành công!";
    }

    // Lấy leaderboard
    public List<ChallengeMember> getLeaderboard(Long challengeId) {
        return challengeMemberRepository.findLeaderboard(challengeId);
    }

    // Check-in trong challenge
    public String checkinChallenge(Long challengeId, Long habitId, Long userId) {
        if (checkinRepository.existsByHabitIdAndCheckinDate(habitId, LocalDate.now())) {
            return "already";
        }

        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        // Cập nhật totalCheckins của member
        challengeMemberRepository.findByChallengeIdAndUserId(challengeId, userId)
                .ifPresent(member -> {
                    member.setTotalCheckins(member.getTotalCheckins() + 1);
                    challengeMemberRepository.save(member);
                });

        // Tạo activity feed
        ActivityFeed feed = new ActivityFeed();
        feed.setUser(user);
        feed.setChallenge(challenge);
        feed.setMessage(user.getUsername() + " vừa check-in! 🔥");
        activityFeedRepository.save(feed);

        return "success";
    }

    // Lấy activity feed của challenge
    public List<ActivityFeed> getActivityFeed(Long challengeId) {
        return activityFeedRepository
                .findTop20ByChallengeIdOrderByCreatedAtDesc(challengeId);
    }

    // Lấy thông tin challenge
    public Challenge getChallengeById(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Challenge không tồn tại"));
    }

    // Kiểm tra user có trong challenge không
    public boolean isMember(Long challengeId, Long userId) {
        return challengeMemberRepository
                .existsByChallengeIdAndUserId(challengeId, userId);
    }

    // Generate invite code ngẫu nhiên 6 ký tự
    private String generateInviteCode() {
        return UUID.randomUUID().toString()
                .replace("-", "").substring(0, 6).toUpperCase();
    }
}