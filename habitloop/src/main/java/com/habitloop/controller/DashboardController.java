package com.habitloop.controller;

import com.habitloop.entity.User;
import com.habitloop.repository.UserRepository;
import com.habitloop.service.DashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    public DashboardController(DashboardService dashboardService,
                                UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        model.addAttribute("user", user);
        model.addAttribute("habitCount",
            dashboardService.countHabits(user.getId()));
        model.addAttribute("weeklyCheckins",
            dashboardService.countCheckinsLastWeek(user.getId()));
        model.addAttribute("bestStreak",
            dashboardService.getBestStreak(user.getId()));
        model.addAttribute("badges",
            dashboardService.getUserBadges(user.getId()));

        // Build JSON string không cần Jackson
        Map<String, Long> weeklyData =
            dashboardService.getWeeklyChartData(user.getId());
        StringBuilder labels = new StringBuilder("[");
        StringBuilder values = new StringBuilder("[");
        weeklyData.forEach((date, count) -> {
            labels.append("\"").append(date).append("\",");
            values.append(count).append(",");
        });
        if (labels.charAt(labels.length() - 1) == ',') {
            labels.deleteCharAt(labels.length() - 1);
            values.deleteCharAt(values.length() - 1);
        }
        labels.append("]");
        values.append("]");

        model.addAttribute("chartLabels", labels.toString());
        model.addAttribute("chartValues", values.toString());

        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("currentUser", currentUser);
        
        return "dashboard/index";
    }
}