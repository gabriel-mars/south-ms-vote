package com.gabriel.martins.vote.entity;

import com.gabriel.martins.vote.enums.VoteType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "vote")
@Entity
@Data
public class VoteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = AssociateEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "associate_id", referencedColumnName = "id")
    private AssociateEntity associate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", length = 3, nullable = false)
    private VoteType vote;

    @ManyToOne(targetEntity = AssemblyEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assembly_id", referencedColumnName = "id")
    private AssemblyEntity assembly;
}
