package com.gnegdev.splitast.service.manager.repository;

import com.gnegdev.splitast.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
}