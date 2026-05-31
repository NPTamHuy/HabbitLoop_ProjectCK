package com.habitloop.repository;

import com.habitloop.entity.ActivityFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityFeedRepository extends JpaRepository<ActivityFeed, Long> {

    // Lấy 20 hoạt động mới nhất của challenge
    List<ActivityFeed> findTop20ByChallengeIdOrderByCreatedAtDesc(Long challengeId);
}