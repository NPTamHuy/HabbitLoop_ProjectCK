package com.habitloop.service;

import com.habitloop.entity.*;
import com.habitloop.entity.Habit.Frequency;
import com.habitloop.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final CheckinRepository checkinRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;

    public HabitService(HabitRepository habitRepository,
                        CheckinRepository checkinRepository,
                        UserRepository userRepository,
                        BadgeRepository badgeRepository,
                        UserBadgeRepository userBadgeRepository) {
        this.habitRepository = habitRepository;
        this.checkinRepository = checkinRepository;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.userBadgeRepository = userBadgeRepository;
    }

    // Lấy danh sách habit của user
    public List<Habit> getHabitsByUser(Long userId) {
        return habitRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // Tạo habit mới
    public Habit createHabit(Long userId, String name, String description,
                              String icon, String color, String frequency) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Habit habit = new Habit();
        habit.setUser(user);
        habit.setName(name);
        habit.setDescription(description);
        habit.setIcon(icon != null && !icon.isEmpty() ? icon : "⭐");
        habit.setColor(color != null && !color.isEmpty() ? color : "#6366f1");
        habit.setFrequency("WEEKLY".equals(frequency) ? Frequency.WEEKLY : Frequency.DAILY);

        return habitRepository.save(habit);
    }

    // Xóa habit
    public void deleteHabit(Long habitId, Long userId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit không tồn tại"));
        if (!habit.getUser().getId().equals(userId)) {
            throw new RuntimeException("Không có quyền xóa habit này");
        }
        habitRepository.delete(habit);
    }

    // Check-in habit
    public String checkin(Long habitId, Long userId) {
        LocalDate today = LocalDate.now();

        // Kiểm tra đã check-in hôm nay chưa
        if (checkinRepository.existsByHabitIdAndCheckinDate(habitId, today)) {
            return "Bạn đã check-in hôm nay rồi!";
        }

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit không tồn tại"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // Tính streak
        LocalDate yesterday = today.minusDays(1);
        boolean checkedYesterday = checkinRepository
                .existsByHabitIdAndCheckinDate(habitId, yesterday);

        if (checkedYesterday) {
            habit.setCurrentStreak(habit.getCurrentStreak() + 1);
        } else {
            habit.setCurrentStreak(1);
        }

        if (habit.getCurrentStreak() > habit.getLongestStreak()) {
            habit.setLongestStreak(habit.getCurrentStreak());
        }

        habitRepository.save(habit);

        // Lưu checkin
        Checkin checkin = new Checkin();
        checkin.setHabit(habit);
        checkin.setUser(user);
        checkin.setCheckinDate(today);
        checkinRepository.save(checkin);

        // Tặng XP
        user.setXp(user.getXp() + 10);
        user.setLevel(user.getXp() / 100 + 1);
        userRepository.save(user);

        // Kiểm tra badge
        checkAndGrantBadge(user, habit.getCurrentStreak());

        return "Check-in thành công! 🔥 Streak: " + habit.getCurrentStreak() + " ngày";
    }

    // Kiểm tra đã check-in hôm nay chưa
    public boolean isCheckedInToday(Long habitId) {
        return checkinRepository.existsByHabitIdAndCheckinDate(habitId, LocalDate.now());
    }

 // Tặng badge theo streak
    private void checkAndGrantBadge(User user, int streak) {
        String[] milestones = {"STREAK_7", "STREAK_30", "STREAK_100"};
        int[] values = {7, 30, 100};

        for (int i = 0; i < values.length; i++) {
            if (streak == values[i]) {
                final String milestone = milestones[i];
                badgeRepository.findByConditionType(milestone).ifPresent(badge -> {
                    if (!userBadgeRepository.existsByUserIdAndBadgeId(
                            user.getId(), badge.getId())) {
                        UserBadge ub = new UserBadge();
                        ub.setUser(user);
                        ub.setBadge(badge);
                        userBadgeRepository.save(ub);
                    }
                });
            }
        }
    }
}