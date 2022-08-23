package com.vcare.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vcare.beans.Appointment;
import com.vcare.beans.HospitalBranch;
import com.vcare.beans.Patients;
import com.vcare.beans.Prescription;
import com.vcare.service.AppointmentService;
import com.vcare.service.HospitalBranchService;
import com.vcare.service.PatientsService;
import com.vcare.service.PrescriptionService;
import com.vcare.service.PrescriptionServiceImp;

@Controller
public class PrescriptionController {

	@Autowired
	PrescriptionService prescriptionService;
	@Autowired
	PatientsService patientService;
	@Autowired
	AppointmentService appointmentService;
	@Autowired
	HospitalBranchService hospitalBranchService;
	@Autowired
	PrescriptionServiceImp prescriptionServiceImpl;

	static Logger log = Logger.getLogger(PrescriptionController.class.getClass());

	@GetMapping("/prescriptionlist/{did}")
	public String prescriptionlist(Model model,@PathVariable("did") int docId,
			HttpServletRequest request) {

		List<Prescription> prescription = prescriptionServiceImpl.prescriptionListByDoctorId(docId);

		List<Prescription> list = new ArrayList<>();
		for (Prescription pre : prescription) {
			if (pre.getIsactive() == 'Y' || pre.getIsactive() == 'y') {
				list.add(pre);
				model.addAttribute("doctormsg","Prescription Uploaded Successfully");
			}
		}
		HttpSession session=request.getSession();
		if(session.getAttribute("presupload")=="prescriptionUpload") {
			model.addAttribute("presupload","Prescription uploaded successfully");
			session.setAttribute("presupload","");
		}
		
		model.addAttribute("prescription", list);
		return "prescriptionlist";
	}
	


	@RequestMapping(value = "/addprescription/{aid}", method = RequestMethod.GET)
	public String newInsurance(Model model, @PathVariable("aid") long aid,
			@ModelAttribute(value = "prescriptionobj") Prescription prescription ,HttpServletRequest request) {
		HttpSession session =request.getSession();
		Appointment appointment = appointmentService.getAppointmentById(aid);
		List<Prescription> prescriptions = prescriptionService.getAllPrescription();
		for (Prescription obj : prescriptions) {
			System.out.println("hello"+obj.getAppointment().getAppointmentId());
			if(obj.getAppointment().getAppointmentId() == aid) {
		log.info("appointment ---ID:::" + appointment.getAppointmentId());
		log.info("appointment:::" + appointment.getAppointmentId());
		System.out.println("appointment:::" + obj.getPrescription());
		model.addAttribute("name", appointment.getPatient().getFirstName());
		model.addAttribute("lastname", appointment.getPatient().getLastName());
		model.addAttribute("age", appointment.getPatient().getPatientAge());
		model.addAttribute("address", appointment.getPatient().getPatientAddress());
		model.addAttribute("mobile", appointment.getPatient().getPatientMobile());
		model.addAttribute("initial", appointment.getDoctor().getDoctorName());
		model.addAttribute("Prescription", obj.getPresctiption());
		model.addAttribute("PrescriptionId", obj.getPrescriptionId());
		
		return "normal";
			}
		}
		 HospitalBranch hb=hospitalBranchService.getHospitalbranchId(appointment.getHospitalBranchId());
		 model.addAttribute("hositalBranch",hb);
		model.addAttribute("appointment", appointment);
		model.addAttribute("prescriptionobj", prescription);
		return "prescriptionform";
	}

	@RequestMapping(value = "/saveprescription", method = RequestMethod.POST)
	public String addPrescription(Model model, @ModelAttribute(value = "prescriptionobj") Prescription pres,
			HttpServletRequest request) {
		pres.setIsactive('Y');
		pres.setCreated(LocalDate.now());
		log.info(pres.getPrescriptionId());
		model.addAttribute("prescriptionobj", pres);
		prescriptionService.addPrescription(pres);
		List<Appointment> appointment = appointmentService.getAllAppointment();
		model.addAttribute("appointment", appointment);
		List<Prescription> prescriptionlist = prescriptionService.getAllPrescription();
		model.addAttribute("prescription", prescriptionlist);
		HttpSession session=request.getSession();
		session.setAttribute("presupload","prescriptionUpload");
		return "redirect:/acceptpres/"+pres.getAppointment().getPatient().getPatientId();
	}

	@GetMapping("/acceptprescription/{id}")
	public String getHospitalById(Model model, @PathVariable("id") int Id, Prescription appointment) {
		Prescription prescriptionlist = prescriptionService.getPrescriptionById(Id);
		log.info("inside getHospitalbranchId id is:::" + prescriptionlist.getPrescriptionId());
		model.addAttribute("hospitalBranchList", prescriptionlist);

		return "prescriptionform";
	}

	@GetMapping("/deleteprescription/{id}")
	public String deleteHospitalBranch(Model model, @PathVariable int id) {
		log.info("inside deleteHospitalBranch id:::" + id);
		Prescription inactive = prescriptionService.getPrescriptionById(id);
		inactive.setIsactive('N');
		prescriptionService.updatePrescription(inactive);
		List<Prescription> appointmentList = prescriptionService.getAllPrescription();
		model.addAttribute("hospitalBranchList", appointmentList);
		return "prescriptionlist";

	}

	@GetMapping("/editprescription/{id}")
    public String getById(Model model, @PathVariable("id") int prescriptionId) {
        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
        log.info("inside getHospitalbranchId id is:::" + prescriptionService.getPrescriptionById(prescriptionId));
        Appointment appointments=appointmentService.getAppointmentById(prescription.getAppointment().getAppointmentId());
        HospitalBranch hospitalBranch=hospitalBranchService.getHospitalbranchId(prescription.getAppointment().getHospitalBranchId());
        model.addAttribute("hositalBranch",hospitalBranch);
        model.addAttribute("appointment",appointments);
        model.addAttribute("prescriptionobj", prescription);
        return "prescriptionform";
    }

	@GetMapping("/list/{pid}")
	public String getByIdList(Model model, @PathVariable("pid") int pid) {
		List<Prescription> prescription = prescriptionService.getByPid(pid);
		model.addAttribute("prescription", prescription);
		return "separatelist";
	}

	@GetMapping("/acceptpres/{pid}")
	public String getHospital(Model model, @PathVariable("pid") int pid, Prescription prescription) {
		Patients patient = patientService.getPatientById(pid);
		log.info("inside getHospitalbranchId id is:::" + patientService.getPatientById(pid));
		model.addAttribute("name", patient.getFirstName());
		model.addAttribute("lastname", patient.getLastName());
		model.addAttribute("age", patient.getPatientAge());
		model.addAttribute("address", patient.getPatientAddress());
		model.addAttribute("mobile", patient.getPatientMobile());
		List<Prescription> prescriptions = prescriptionService.getAllPrescription();
		System.err.println("hello thiss is a prescription"+prescriptionService.getAllPrescription());
		for (Prescription obj : prescriptions) {
			if (obj.appointment.getPatient().getPatientId() == pid) {
				model.addAttribute("initial", obj.appointment.getDoctor().getDoctorName());
				model.addAttribute("Prescription", obj.getPrescription());
				model.addAttribute("prescriptionId", obj.getPrescriptionId());
				return "normal";
			}
		}
		return "normal";
	}
	
	@GetMapping("/prescription/{pesid}")
	public String getPreccriptionId(Model model, @PathVariable("pesid") int pesid, Prescription prescription) {
		
		Prescription prescriptions = prescriptionService.getByPesid(pesid);
		System.err.println("hello thiss is a prescription"+prescriptionService.getAllPrescription());
		/*for (Prescription obj : prescriptions) {
			if (obj.appointment.getPatient().getPatientId() != 0) {*/
				model.addAttribute("initial", prescriptions.getAppointment().getDoctor().getDoctorName());
				model.addAttribute("name",prescriptions.getAppointment().getPatient().getFirstName());
				model.addAttribute("age",prescriptions.getAppointment().getPatient().getPatientAge());
				model.addAttribute("mobile",prescriptions.getAppointment().getPatient().getPatientMobile());
				model.addAttribute("address",prescriptions.getAppointment().getPatient().getPatientAddress());
				model.addAttribute("Prescription", prescriptions.getPrescription());
				model.addAttribute("prescriptionId", prescriptions.getPrescriptionId());
				return "normal";
		
	}
}
