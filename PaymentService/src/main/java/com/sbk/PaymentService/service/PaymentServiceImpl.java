package com.sbk.PaymentService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbk.PaymentService.Dao.PaymentDao;
import com.sbk.PaymentService.Dto.PaymentDto;
import com.sbk.PaymentService.Entity.TransactionDetailsEntity;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentDao paymentDao;

    // Method to add Payment Details
     @Override
      public int addPaymentDetails(PaymentDto paymentDto) {
        TransactionDetailsEntity transactionDetailsEntity =new TransactionDetailsEntity();
        transactionDetailsEntity.setTransactionId(paymentDto.getTransactionId());
        transactionDetailsEntity.setPaymentMode(paymentDto.getPaymentMode());
        transactionDetailsEntity.setCardNumber(paymentDto.getCardNumber());
        transactionDetailsEntity.setUpiId(paymentDto.getUpiId());
        transactionDetailsEntity.setBookingId(paymentDto.getBookingId());
                paymentDao.save(transactionDetailsEntity);

        return transactionDetailsEntity.getTransactionId();
    }

    // Method to get payment details for a given transactionId
    @Override
    public TransactionDetailsEntity getPaymentDetails(Integer transactionId) {
        Optional<TransactionDetailsEntity> reqDetails = paymentDao.findById(transactionId);
        if (reqDetails.isPresent()) {
            return reqDetails.get();
        }
        return null;
    }
}
