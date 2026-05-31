package com.habitloop.repository;

import com.habitloop.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    boolean existsByHabitIdAndCheckinDate(Long habitId, LocalDate date);

    List<Checkin> findByHabitIdOrderByCheckinDateDesc(Long habitId);

    // Lấy tất cả checkin của user trong 1 tháng (cho heatmap)
    @Query("SELECT c FROM Checkin c WHERE c.user.id = :userId " +
           "AND c.checkinDate >= :from AND c.checkinDate <= :to")
    List<Checkin> findByUserIdAndDateRange(
        @Param("userId") Long userId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );

    // Đếm số checkin trong challenge (cho leaderboard)
    @Query("SELECT COUNT(c) FROM Checkin c " +
           "JOIN c.habit h JOIN ChallengeMember cm ON cm.user = c.user " +
           "WHERE cm.challenge.id = :challengeId AND c.user.id = :userId")
    Long countCheckinsByChallengeAndUser(
        @Param("challengeId") Long challengeId,
        @Param("userId") Long userId
    );
}