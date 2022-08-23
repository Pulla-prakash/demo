package com.vcare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vcare.beans.AmbulanceDriverAssosiation;
import com.vcare.beans.Appointment;
import com.vcare.beans.BookingAmbulance;
import com.vcare.beans.HospitalBranch;
import com.vcare.service.AmbulanceDriverService;
import com.vcare.service.BookingAmbService;
import com.vcare.service.HospitalBranchService;


@Controller
public class BookingAmbulanceController {
	
	@Autowired
	BookingAmbService bookingAmbService;
	@Autowired
	AmbulanceDriverService ambulanceDriverService;
	@Autowired
	HospitalBranchService hospitalBranchService;
	
	@GetMapping("/BookingList/{hbid}")
	public String getbyid(Model model,BookingAmbulance BookingAmbulance,@PathVariable("hbid")int hbid) {
		List<BookingAmbulance> Bookinglist = bookingAmbService.getAllBookings();
		HospitalBranch hosptlbrnc=hospitalBranchService.getHospitalbranchId(hbid);
		model.addAttribute("hosptlbrnc",hosptlbrnc.getHospitalBranchId());
		List<BookingAmbulance> Bookingambulancelist = new ArrayList<>();
		for (BookingAmbulance app :Bookinglist ) {
			if (app.getIsactive() == 'y' || app.getIsactive() == 'Y') {
				Bookingambulancelist.add(app);
			}
		}
		model.addAttribute("Bookingambulancelist", Bookinglist);
		return "BookingAmblist";
		
}
	
	 @RequestMapping(value=("/bookingform/{branchid}/{ambid}/{empid}"), method=RequestMethod.GET)
	  public String Getbyid(Model model,@PathVariable("ambid")int ambid,@PathVariable("branchid")int branchid,BookingAmbulance BookingAmbulance,@PathVariable("empid")int empid) {
		 System.out.println("::::::::::::::"+ambid);
		 AmbulanceDriverAssosiation  ambulancedriverob = ambulanceDriverService.getById(ambid);
		 HospitalBranch hospitalbranch= hospitalBranchService.getHospitalbranchId(branchid);
			model.addAttribute("hbranchId",hospitalbranch.getHospitalBranchId());
		  System.out.println("this is bookinga amabuybiwjx"+ambulancedriverob.getAmbulancetype());
		  ambulancedriverob.setStatus("booked");
		  ambulanceDriverService.addAmbulancedriver(ambulancedriverob);
	  model.addAttribute("drivername", ambulancedriverob.getDriverId());
	  model.addAttribute("ambulancetype", ambulancedriverob.getAmbulancetype());
	  model.addAttribute("empid",empid);
	  System.out.println("this is bookinga amabuybiwjx"+ambulancedriverob.getAmbulancetype());
	  model.addAttribute("BookingAmbulance",BookingAmbulance);
	  return"bookingambulance";
	  
	  }
	  @PostMapping("/savebookings/{bid}/{eid}")
	  public String updatebookingfrom(Model model,@PathVariable("bid") int bid,@PathVariable("eid") int eid,BookingAmbulance BookingAmbulance) {
 System.out.println("this mthod ");
 HospitalBranch hospitalbranch= hospitalBranchService.getHospitalbranchId(bid);
	model.addAttribute("hbranchId",hospitalbranch.getHospitalBranchId());
 model.addAttribute("BookingAmbulance", BookingAmbulance);
 BookingAmbulance.setIsactive('y');
 bookingAmbService.addBookings(BookingAmbulance);
	List<AmbulanceDriverAssosiation> ambdriverlist= ambulanceDriverService.getAllAambulancedriver();
	model.addAttribute("ambdriverlist", ambdriverlist);
	model.addAttribute("empid",eid);
 return "ambulanceselection";
	 }
	  @GetMapping("/deleteBooking/{baid}")
		public String deleteBooking(Model model, @PathVariable int baid) {
			BookingAmbulance ba=bookingAmbService.getById(baid);
			ba.setIsactive('N');
			List<BookingAmbulance> Bookingambulancelist = bookingAmbService.getAllBookings();
			model.addAttribute("Bookingambulancelist", Bookingambulancelist);
			 return "redirect:/BookingList";
	  }
	  @GetMapping("/editbooking/{bookingId}")
		public String getById(Model model, @PathVariable("bookingId") int bookingId, BookingAmbulance BookingAmbulance) {
		  System.out.println("::::::::::::"+BookingAmbulance.getBookingId());
		  BookingAmbulance BookingAmbulance2 = bookingAmbService.getById(bookingId);
		  System.out.println("::::::::::::"+BookingAmbulance2.getToAddress());
		  model.addAttribute("BookingAmbulance", BookingAmbulance);
		  model.addAttribute("ambulancetype", BookingAmbulance2.getAmbulancetype());
		  model.addAttribute("drivername", BookingAmbulance2.getDriverName());
		  BookingAmbulance.setIsactive('y');
		  model.addAttribute("BookingAmbulance", BookingAmbulance2);
		  System.out.println("this is bookinga amabuybiwjx");
			System.out.println("inside getHospitalbranchId id is:::" + bookingAmbService.getById(bookingId));
			
			return "bookingambulance";
	
	  }
	  @GetMapping("/statuschange/{bookingId}")
	  public String statusByid(Model model,@PathVariable("bookingId") int bookingId) {
		  BookingAmbulance objBookingAmbulance= bookingAmbService.getById(bookingId);
		  objBookingAmbulance.setIsactive('n');
		  System.out.println("''''''''"+objBookingAmbulance.getAddressFrom());
		  model.addAttribute("BookingAmbulance", objBookingAmbulance);
		  bookingAmbService.Updatebooking(objBookingAmbulance);
		  return "redirect:/BookingList";
	  }	  
}