package com.gabriel.martins.vote.repository;

import com.gabriel.martins.vote.entity.AssemblyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssemblyRepositoty extends JpaRepository<AssemblyEntity, Long> {
}
