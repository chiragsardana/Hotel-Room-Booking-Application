package com.sbk.BookingService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.sbk.BookingService.dao.BookingDao;
import com.sbk.BookingService.dto.PaymentDto;
import com.sbk.BookingService.entity.BookingInfoEntity;
import com.sbk.BookingService.exceptions.InvalidBookingIdException;
import com.sbk.BookingService.exceptions.InvalidPaymentModeException;

import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingDao bookingDao;

    @Autowired
    RestTemplate restTemplate;

    @Value("${api-gateway-url}")
    String apiGateway;

    @Autowired
    public void setBookingDao(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public BookingInfoEntity addBooking(BookingInfoEntity bookingInfoEntity) {

        int noOfRooms = bookingInfoEntity.getNumOfRooms();

        long diff = bookingInfoEntity.getToDate().getTime() - bookingInfoEntity.getFromDate().getTime();
        int totalDays = (int) (diff / (1000 * 3600 * 24));
        int roomPrice = 1000 * noOfRooms * totalDays;

        ArrayList<String> roomNumbers = getRandomNumbers(bookingInfoEntity.getNumOfRooms());
        String roomNumber = String.join(",", roomNumbers);

        bookingInfoEntity.setRoomNumbers(roomNumber);
        bookingInfoEntity.setRoomPrice(roomPrice);

        bookingInfoEntity.setBookedOn(new Date());

        bookingDao.save(bookingInfoEntity);
        return bookingInfoEntity;

    }


    @Override
    public BookingInfoEntity getBookingDetails(Integer bookingId) {
        Optional<BookingInfoEntity> reqDetails = bookingDao.findById(bookingId);
        if (reqDetails.isPresent()) {
            return reqDetails.get();
        }
        return null;
    }


    @Override
    public BookingInfoEntity addPaymentDetails(int bookingId, PaymentDto paymentDto) throws InvalidBookingIdException, InvalidPaymentModeException {
        String PaymentServiceURL = apiGateway + "/payment/transaction";
        if ("UPI".equals(paymentDto.getPaymentMode()) || "CARD".equals(paymentDto.getPaymentMode())) {
            if (!bookingDao.existsById(bookingId)) {
                throw new InvalidBookingIdException();
            }
            // Getting Booking Details based on BookingID
            BookingInfoEntity bookingDetails = getBookingDetails(bookingId);

            // Getting transactionId from Payment Service
            Integer transactionId = restTemplate.postForObject(PaymentServiceURL, paymentDto, Integer.class);

            //Updating the transaction ID received from Payment service in booking data
            bookingDetails.setTransactionId(transactionId);
            bookingDao.save(bookingDetails);

            String message = "Booking confirmed for user with aadhaar number: "
                    + bookingDetails.getAadharNumber()
                    + "    |    "
                    + "Here are the booking details:    " + bookingDetails.toString();

            //Print message to console
            System.out.println(message);

            return bookingDetails;

        } else {
            throw new InvalidPaymentModeException();
        }
    }

    //Method to generate Random room numbers
    public static ArrayList<String> getRandomNumbers(int count) {
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String> numberList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }

        return numberList;
    }
}
