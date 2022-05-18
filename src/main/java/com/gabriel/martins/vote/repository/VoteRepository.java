package com.gabriel.martins.vote.repository;

import com.gabriel.martins.vote.entity.VoteEntity;
import com.gabriel.martins.vote.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    @Query(value = "SELECT COUNT(v) FROM VoteEntity v WHERE v.vote=:vote AND assembly.id=:assemblyId")
    Long countVotesByVoteTypeAndAssemblyId(@Param("vote") VoteType vote, @Param("assemblyId") Long assemblyId);
}
