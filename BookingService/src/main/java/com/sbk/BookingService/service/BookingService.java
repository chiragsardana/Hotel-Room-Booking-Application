package com.sbk.BookingService.service;

import com.sbk.BookingService.dto.PaymentDto;
import com.sbk.BookingService.entity.BookingInfoEntity;
import com.sbk.BookingService.exceptions.InvalidBookingIdException;
import com.sbk.BookingService.exceptions.InvalidPaymentModeException;

public interface BookingService {
    BookingInfoEntity addBooking(BookingInfoEntity bookingInfoEntity);
    BookingInfoEntity addPaymentDetails(int bookingId,PaymentDto paymentDto) throws InvalidBookingIdException, InvalidPaymentModeException;
    BookingInfoEntity getBookingDetails(Integer bookingId);

}
