package com.gnegdev.splitast.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    @JsonBackReference
    private Report report;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Enumerated
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "severity")
    private String severity;
    @Column(name = "message", length = 1024)
    private String message;
    @Column(name = "uri")
    private String uri;
    @Column(name = "line")
    private Integer line;
}