package com.vcare.service;

import java.util.List;

import com.vcare.beans.AmbulanceDriverAssosiation;
import com.vcare.beans.BookingAmbulance;

public interface BookingAmbService {
	
	List<BookingAmbulance> getAllBookings();
	BookingAmbulance addBookings(BookingAmbulance bookingAmbulance);
	 void Updatebooking(BookingAmbulance bookingAmbulance);
	 void deleteBookingsById(int bookingId );
	 BookingAmbulance getById(int bookingId);
	 BookingAmbulance getbookingId(int bookingId);

	
}
