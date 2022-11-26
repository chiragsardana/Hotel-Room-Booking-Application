package com.sbk.BookingService.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbk.BookingService.entity.BookingInfoEntity;


public interface BookingDao extends JpaRepository<BookingInfoEntity,Integer> {

}
