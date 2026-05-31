package com.habitloop.repository;

import com.habitloop.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Optional<Challenge> findByInviteCode(String inviteCode);

    // Lấy các challenge công khai còn đang diễn ra
    @Query("SELECT c FROM Challenge c WHERE c.isPublic = true AND c.endDate >= :today")
    List<Challenge> findPublicActiveChallenges(@Param("today") LocalDate today);

    // Lấy challenge mà user đang tham gia
    @Query("SELECT c FROM Challenge c JOIN c.members m WHERE m.user.id = :userId")
    List<Challenge> findChallengesByUserId(@Param("userId") Long userId);

    // Lấy challenge sắp hết hạn (trong vòng 3 ngày) để gửi notification
    @Query("SELECT c FROM Challenge c WHERE c.endDate BETWEEN :today AND :soon")
    List<Challenge> findChallengesEndingSoon(
        @Param("today") LocalDate today,
        @Param("soon") LocalDate soon
    );
}