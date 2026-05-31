package com.habitloop.service;

import com.habitloop.entity.*;
import com.habitloop.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class DashboardService {

    private final HabitRepository habitRepository;
    private final CheckinRepository checkinRepository;
    private final UserBadgeRepository userBadgeRepository;

    public DashboardService(HabitRepository habitRepository,
                            CheckinRepository checkinRepository,
                            UserBadgeRepository userBadgeRepository) {
        this.habitRepository = habitRepository;
        this.checkinRepository = checkinRepository;
        this.userBadgeRepository = userBadgeRepository;
    }

    // Đếm số habit của user
    public long countHabits(Long userId) {
        return habitRepository.findByUserId(userId).size();
    }

    // Tổng checkin 7 ngày gần nhất
    public long countCheckinsLastWeek(Long userId) {
        LocalDate from = LocalDate.now().minusDays(6);
        LocalDate to = LocalDate.now();
        return checkinRepository.findByUserIdAndDateRange(userId, from, to).size();
    }

    // Streak cao nhất trong tất cả habit
    public int getBestStreak(Long userId) {
        return habitRepository.findByUserId(userId).stream()
                .mapToInt(Habit::getLongestStreak)
                .max().orElse(0);
    }

    // Lấy badges của user
    public List<UserBadge> getUserBadges(Long userId) {
        return userBadgeRepository.findByUserIdWithBadge(userId);
    }

    // Dữ liệu chart 7 ngày (số checkin mỗi ngày)
    public Map<String, Long> getWeeklyChartData(Long userId) {
        Map<String, Long> data = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            LocalDate finalDate = date;
            long count = checkinRepository
                    .findByUserIdAndDateRange(userId, finalDate, finalDate).size();
            data.put(date.toString(), count);
        }
        return data;
    }
}