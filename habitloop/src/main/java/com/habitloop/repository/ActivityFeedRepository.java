package com.habitloop.repository;

import com.habitloop.entity.ActivityFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ActivityFeedRepository extends JpaRepository<ActivityFeed, Long> {

    List<ActivityFeed> findTop20ByChallengeIdOrderByCreatedAtDesc(Long challengeId);

    @Query("SELECT COUNT(a) FROM ActivityFeed a WHERE a.challenge.id = :challengeId AND a.user.id = :userId AND a.createdAt >= :startOfDay AND a.createdAt < :endOfDay AND a.message LIKE '%check-in%'")
    Long countCheckinToday(@Param("challengeId") Long challengeId,
                           @Param("userId") Long userId,
                           @Param("startOfDay") java.time.LocalDateTime startOfDay,
                           @Param("endOfDay") java.time.LocalDateTime endOfDay);
}