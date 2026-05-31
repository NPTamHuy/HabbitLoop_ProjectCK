package com.habitloop.controller;

import com.habitloop.entity.Notification;
import com.habitloop.repository.UserRepository;
import com.habitloop.service.NotificationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationController(NotificationService notificationService,
                                   UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/read-all")
    public String markAllRead(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow().getId();
        notificationService.markAllAsRead(userId);
        return "redirect:/dashboard";
    }

    // API trả về JSON cho notification bell
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow().getId();

        List<Notification> notifications =
            notificationService.getNotifications(userId);
        long unreadCount = notificationService.countUnread(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("unreadCount", unreadCount);

        List<Map<String, Object>> items = new java.util.ArrayList<>();
        for (Notification n : notifications) {
            Map<String, Object> item = new HashMap<>();
            item.put("message", n.getMessage());
            item.put("isRead", n.getIsRead());
            item.put("createdAt", n.getCreatedAt().toString());
            items.add(item);
        }
        result.put("notifications", items);
        return ResponseEntity.ok(result);
    }
}