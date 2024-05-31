package com.example.switchwon.domain.repository;

import com.example.switchwon.domain.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
