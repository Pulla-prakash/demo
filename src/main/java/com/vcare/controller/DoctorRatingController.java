package com.vcare.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vcare.beans.Doctor;
import com.vcare.beans.Patients;
import com.vcare.beans.Rating;
import com.vcare.repository.DoctorRatingRepository;
import com.vcare.service.DoctorImplementation;
import com.vcare.service.DoctorRatingService;
import com.vcare.service.PatientsService;

@Controller
public class DoctorRatingController {

	@Autowired
	DoctorRatingService doctorRatingService;

	@Autowired
	PatientsService patientsService;

	@Autowired
	DoctorRatingRepository doctorRatingRepository;

	@Autowired
	DoctorImplementation doctorImplementation;

	static Logger log = Logger.getLogger(DoctorRatingController.class.getClass());

	// IN DoctorRatingController//http://localhost:8080/drating/4/1
	@GetMapping("/drating/{pid}/{id}")
	public String doctorRating(Model model, @PathVariable("pid") int pid, @PathVariable("id") int id, Rating drating) {
		Doctor doctor = doctorImplementation.GetDocotorById(id);
		Patients patient = patientsService.getPatientById(pid);
		List<Rating> rating = doctorRatingService.GetAllDoctorRating();
		model.addAttribute("patientname", patient.getFirstName());
		List<Rating> list = new ArrayList<>();
		for (Rating r : rating) {
			if (doctor.getDoctorId() == r.getDoctor().getDoctorId()) {
				Rating rate = doctorRatingService.getDoctorRatingById(r.getDoctorRatingId());
				list.add(rate);
			}
		}
		model.addAttribute("pi", patient);
		model.addAttribute("pid", pid);
//	model.addAttribute("doc",doctor.getDoctorName());
		model.addAttribute("id", id);
		model.addAttribute("rating", list);
		model.addAttribute("drating", drating);
		return "doctorRating";
	}

	@PostMapping(value = "/savedoctorratings/{pid}/{id}")
	public String saveDoctorRating(Model model, @PathVariable("pid") int pid, @PathVariable("id") int id,
			Rating doctorRating) {
		doctorRating.setIsactive('Y');
		doctorRating.setCreated(LocalDate.now());
		// doctorRating.setCreated();
		log.info("This is save method");
		doctorRatingService.addDoctorRating(doctorRating);
		return "redirect:/Viewdoctors/" + pid + "/" + id;

	}

	@GetMapping(value = "dratinglist")
	public String doctorRatingList(Model model) {
		List<Rating> doctorlist = doctorRatingService.GetAllDoctorRating();
		model.addAttribute("doctorlist", doctorlist);
		return "doctorratinglist";
	}

	@GetMapping(value = "/adddoctorrating")
	public String addDoctorRating(Model model, Rating doctorRating) {
		model.addAttribute("doctorlist", doctorRating);
		return "doctorrating";

	}
}
