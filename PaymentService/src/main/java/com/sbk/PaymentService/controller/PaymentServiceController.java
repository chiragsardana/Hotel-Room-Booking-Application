package com.sbk.PaymentService.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbk.PaymentService.Dto.PaymentDto;
import com.sbk.PaymentService.Entity.TransactionDetailsEntity;
import com.sbk.PaymentService.service.PaymentService;


@RestController
@RequestMapping(value = "/payment")
public class PaymentServiceController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    ModelMapper modelMapper;

    /**
     * Method to add payment data in DB and get transaction id
     * HTTP METHOD: POST
     * URI <a href="http://localhost:9191/payment/transaction">http://localhost:9191/payment/transaction</a>
     *
     * Request Body
     *
     *  {
     *     "paymentMode": "UPI",
     *     "bookingId": 1,
     *     "upiId": "upi details",
     *     "cardNumber": ""
     *  }
     *
     * @param paymentDto
     * @return transactionId
     */
    @PostMapping(value = "/transaction",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer>  addPaymentDetails(@RequestBody PaymentDto paymentDto) {

        Integer transactionId = paymentService.addPaymentDetails(paymentDto);

        return new ResponseEntity<>(transactionId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction/{transactionId}")
    public ResponseEntity requestPaymentDetails(@PathVariable(name = "transactionId") Integer transactionId) {
        TransactionDetailsEntity requestedDetails = paymentService.getPaymentDetails(transactionId);
        if (requestedDetails != null) {
            return new ResponseEntity(requestedDetails, HttpStatus.OK);
        }
        return null;
    }


}
