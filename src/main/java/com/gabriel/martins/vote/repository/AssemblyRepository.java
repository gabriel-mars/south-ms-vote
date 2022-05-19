package com.gabriel.martins.vote.repository;

import com.gabriel.martins.vote.entity.AssemblyEntity;
import com.gabriel.martins.vote.enums.AssemblyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AssemblyRepository extends JpaRepository<AssemblyEntity, Long> {

    @Query(value = "SELECT a FROM AssemblyEntity a WHERE a.startingDate=:startingDate AND a.status =:status AND a.available=:available")
    List<AssemblyEntity> findByStargingDateAndStatusAndAvailable(@Param("startingDate") LocalDate startingDate, @Param("status") AssemblyStatus status, @Param("available") Boolean available);

    @Query(value = "SELECT a FROM AssemblyEntity a WHERE a.endingDate=:endingDate AND a.status =:status AND a.available=:available")
    List<AssemblyEntity> findByEndingDateAndStatusAndAvailable(@Param("endingDate") LocalDate startingDate, @Param("status") AssemblyStatus status, @Param("available") Boolean available);
}
