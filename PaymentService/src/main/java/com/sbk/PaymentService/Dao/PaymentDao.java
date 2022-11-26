package com.sbk.PaymentService.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbk.PaymentService.Entity.TransactionDetailsEntity;

public interface PaymentDao extends JpaRepository<TransactionDetailsEntity,Integer> {
}
