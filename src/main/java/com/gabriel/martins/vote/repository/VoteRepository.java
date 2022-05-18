package com.gabriel.martins.vote.repository;

import com.gabriel.martins.vote.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
}
