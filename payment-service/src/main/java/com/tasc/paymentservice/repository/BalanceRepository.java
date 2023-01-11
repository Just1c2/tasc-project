package com.tasc.paymentservice.repository;

import com.tasc.paymentservice.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
