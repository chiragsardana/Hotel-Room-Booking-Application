package com.sbk.PaymentService.service;

import com.sbk.PaymentService.Dto.PaymentDto;
import com.sbk.PaymentService.Entity.TransactionDetailsEntity;

public interface PaymentService {

    int addPaymentDetails(PaymentDto paymentDto);
    TransactionDetailsEntity getPaymentDetails(Integer transactionId);
}
