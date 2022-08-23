package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcare.beans.BookingAmbulance;
import com.vcare.repository.BookingAmbulanceRepository;
@Service
public class BookingAmbulanceImplementaton  implements BookingAmbService{

	
	@Autowired
	
	BookingAmbulanceRepository bookingAmbrepository;
	
	
	@Override
	public List<BookingAmbulance> getAllBookings() {
		return  bookingAmbrepository.findAll();
	}

	@Override
	public BookingAmbulance addBookings(BookingAmbulance bookingAmbulance) {
		
		return bookingAmbrepository.save(bookingAmbulance);
	}

	@Override
	public void Updatebooking(BookingAmbulance bookingAmbulance) {
		bookingAmbrepository.save(bookingAmbulance);	
	}

	@Override
	public void deleteBookingsById(int bookingId) {
		bookingAmbrepository.deleteById(bookingId);		
	}

	@Override
	public BookingAmbulance getById(int bookingId) {
		return bookingAmbrepository.getById(bookingId);
	}

	@Override
	public BookingAmbulance getbookingId(int bookingId) {
		return bookingAmbrepository.findById(bookingId).get();
	}

}
