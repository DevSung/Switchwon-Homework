package com.example.switchwon.domain.repository;

import com.example.switchwon.domain.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
