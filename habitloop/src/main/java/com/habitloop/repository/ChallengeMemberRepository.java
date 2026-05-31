package com.habitloop.repository;

import com.habitloop.entity.ChallengeMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long> {

    boolean existsByChallengeIdAndUserId(Long challengeId, Long userId);

    Optional<ChallengeMember> findByChallengeIdAndUserId(Long challengeId, Long userId);

    // Leaderboard: lấy danh sách thành viên sắp xếp theo totalCheckins
    @Query("SELECT cm FROM ChallengeMember cm " +
           "JOIN FETCH cm.user " +
           "WHERE cm.challenge.id = :challengeId " +
           "ORDER BY cm.totalCheckins DESC")
    List<ChallengeMember> findLeaderboard(@Param("challengeId") Long challengeId);
}