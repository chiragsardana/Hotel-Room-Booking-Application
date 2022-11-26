package com.sbk.BookingService.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.sbk.BookingService.dto.BookingDto;
import com.sbk.BookingService.dto.PaymentDto;
import com.sbk.BookingService.entity.BookingInfoEntity;
import com.sbk.BookingService.exceptions.InvalidBookingIdException;
import com.sbk.BookingService.exceptions.InvalidPaymentModeException;
import com.sbk.BookingService.service.BookingService;


@RestController
@RequestMapping(value = "/hotel")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * HTTP POST METHOD: to save Booking DATA
     * URL : http://localhost:9191/hotel/booking
     *
     * Request Body
     *          {
     *          "fromDate": "2022-04-20",
     *          "toDate": "2022-04-25",
     *           "aadharNumber": "123456789",
     *           "numOfRooms": 3
     *          }
     * http://localhost:8081/hotel/booking
     *
     */

    @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBooking(@RequestBody BookingDto bookingDto) {

        BookingInfoEntity newBooking = modelMapper.map(bookingDto, BookingInfoEntity.class);

        BookingInfoEntity savedBooking = bookingService.addBooking(newBooking);

        BookingDto savedBookingDto = modelMapper.map(savedBooking, BookingDto.class);

        return new ResponseEntity(savedBookingDto, HttpStatus.CREATED);
    }

    /**
     *
     * URL: http://localhost:9191/hotel/booking/{bookingId}/transaction
     *
     * Request Body
     * {
     *     "paymentMode": "CARD",
     *     "bookingId": 0,
     *     "upiId": "",
     *     "cardNumber": "Card Details"
     * }
     *
     * http://localhost:8081/hotel/booking/{bookingId}/transaction
     *
     */
    @PostMapping(value = "/booking/{bookingId}/transaction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPaymentDetails(@PathVariable(name = "bookingId") int bookingId, @RequestBody PaymentDto paymentDto) throws InvalidBookingIdException, InvalidPaymentModeException {

         BookingInfoEntity updatedpayment = bookingService.addPaymentDetails(bookingId, paymentDto);

        return new ResponseEntity(updatedpayment, HttpStatus.CREATED);
    }



}
