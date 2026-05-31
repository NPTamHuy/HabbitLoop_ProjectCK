package com.habitloop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer xp = 0;

    @Column(nullable = false)
    private Integer level = 1;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Habit> habits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();

    // Constructors
    public User() {}

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Integer getXp() { return xp; }
    public Integer getLevel() { return level; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Habit> getHabits() { return habits; }
    public List<Notification> getNotifications() { return notifications; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setXp(Integer xp) { this.xp = xp; }
    public void setLevel(Integer level) { this.level = level; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setHabits(List<Habit> habits) { this.habits = habits; }
    public void setNotifications(List<Notification> notifications) { this.notifications = notifications; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String username;
        private String email;
        private String password;
        private Integer xp = 0;
        private Integer level = 1;

        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder xp(Integer xp) { this.xp = xp; return this; }
        public Builder level(Integer level) { this.level = level; return this; }

        public User build() {
            User u = new User();
            u.username = this.username;
            u.email = this.email;
            u.password = this.password;
            u.xp = this.xp;
            u.level = this.level;
            return u;
        }
    }
}