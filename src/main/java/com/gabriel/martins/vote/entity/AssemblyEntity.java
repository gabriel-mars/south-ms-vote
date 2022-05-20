package com.gabriel.martins.vote.entity;

import com.gabriel.martins.vote.enums.AssemblyStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "assembly")
@Entity
@Data
public class AssemblyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "assembly_status", length = 10, nullable = false)
    private AssemblyStatus status;

    @Column(name = "ending_date", nullable = false)
    private LocalDate endingDate;

    @Column(name = "starting_date", nullable = false)
    private LocalDate startingDate;
}
