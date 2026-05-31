package com.habitloop.config;

import com.habitloop.entity.*;
import com.habitloop.repository.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMemberRepository challengeMemberRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public ScheduledTasks(ChallengeRepository challengeRepository,
                          ChallengeMemberRepository challengeMemberRepository,
                          NotificationRepository notificationRepository,
                          UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.challengeMemberRepository = challengeMemberRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // Chạy mỗi ngày lúc 8h sáng
    @Scheduled(cron = "0 0 8 * * *")
    public void notifyChallengeEndingSoon() {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3);

        List<Challenge> endingSoon = challengeRepository
                .findChallengesEndingSoon(today, threeDaysLater);

        for (Challenge challenge : endingSoon) {
            List<ChallengeMember> members =
                challengeMemberRepository.findLeaderboard(challenge.getId());

            for (ChallengeMember member : members) {
                Notification notification = new Notification();
                notification.setUser(member.getUser());
                notification.setMessage("⏰ Challenge \"" + challenge.getTitle()
                    + "\" sẽ kết thúc vào " + challenge.getEndDate() + "!");
                notificationRepository.save(notification);
            }
        }
    }
}