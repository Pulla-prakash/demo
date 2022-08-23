package com.vcare.controller;

import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vcare.beans.Appointment;
import com.vcare.beans.Doctor;
import com.vcare.beans.HospitalBranch;
import com.vcare.beans.Patients;
import com.vcare.repository.PatientsRepository;
import com.vcare.repository.ServiceRepository;
import com.vcare.service.AppointmentService;
import com.vcare.service.DoctorService;
import com.vcare.service.EmailSenderService;
import com.vcare.service.HospitalBranchService;
import com.vcare.service.HospitalService;
import com.vcare.service.PatientsService;
import com.vcare.service.ServiceService;
import com.vcare.utils.VcareUtilies;

@Controller

public class PatientsController {

	@Autowired
	PatientsService patientService;
	@Autowired
	ServiceService serviceservice;
	@Autowired
	ServiceRepository serviceRespository;

	@Autowired
	HospitalService hospitalService;

	@Autowired
	HospitalBranchService hospitalBranchService;

	@Autowired
	DoctorService doctorService;
	
	@Autowired
	EmailSenderService emailSenderService;

	static Logger log = Logger.getLogger(PatientsController.class.getClass());

	@GetMapping("/patientsList")
	public String patientsList(Model model) {

		List<Patients> list = patientService.getAllPatients();
		model.addAttribute("patientObj", list);
		return "patientslist";
	}

	@GetMapping("/signup")
	public String newStudent(Model model) {
		Patients patientObj = new Patients();
		model.addAttribute("objPatient", patientObj);
		return "patientregistration";
	}

	@GetMapping(value ="saveuser")
	public String saveRegistration(Model model, @ModelAttribute(value = "patientObj") Patients patientObj,HttpServletRequest request) {
		patientObj.setIsactive('Y');
		patientObj.setCreated(LocalDate.now());

		log.info(patientObj.getPatientMailId());
		log.info(patientObj.getPatientPassword());
		HttpSession session=request.getSession();
		List<Patients> list = patientService.getAllPatients();
		model.addAttribute("patientlist", list);
		String patientObj1 = patientService.validateduplicate(patientObj.getPatientMailId());
		if (patientObj1 == null) {
			
			model.addAttribute("patientObj", patientObj);
			log.info(patientObj.getPatientId());
			String strEncPassword = VcareUtilies.getEncryptSecurePassword(patientObj.getPatientPassword(), "vcare");
			patientObj.setPatientPassword(strEncPassword);
			patientService.addNewPatient(patientObj);
			session.setAttribute("pId", patientObj.getPatientId());
			return "patientdashboard";
		}
		session.setAttribute("indexmsg","save");
		return "redirect:/";

	}

	@GetMapping("/patientprofile/{id}")
	public String patientprofile(Model model, @PathVariable("id") int id, Patients patients) {

		Patients patient = patientService.getPatientById(id);
		model.addAttribute("patientObj", patient);
		model.addAttribute("patientId", id);
		
		return "pprofile";
	}

	@PostMapping("/patientupload")
	public String uploadPatient(Model model, Patients patientObj) {
		model.addAttribute("patientObj", patientObj);
		patientService.updatePatient(patientObj);
		model.addAttribute("patientmsg",patientObj.getFirstName()+", Your profile is updated successfully");
		return "patientdashboard";
	}

	@GetMapping(value = "/signin")
	public String Signin(Model model, @ModelAttribute(value = "objPatient") Patients patientObj) {
		model.addAttribute("patientObj", patientObj);
		return "PatientLogin";
	}

	@GetMapping("/loginvalid")
	public String loginValidation(Model model, @ModelAttribute(value = "patientObj") Patients patientObj,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!patientObj.getCaptcha().equals(patientObj.getUserCaptcha())) {
			session.setAttribute("indexmsg","logininvalidcaptcha");
			return "redirect:/";
		}
		model.addAttribute("warninig", "invalid credentials..please try to login again...!");
		String str = VcareUtilies.getEncryptSecurePassword(patientObj.getPatientPassword(), "vcare");
		Patients signinObj = patientService.getPatient(patientObj.getPatientMailId(), str);
		model.addAttribute("patientObj", signinObj);
		if (signinObj != null) {
			session.setAttribute("name", signinObj.getFirstName());
			session.setAttribute("pId", signinObj.getPatientId());
			model.addAttribute("patientmsg","Hello "+signinObj.getFirstName()+", you have successfully logged in " );
			return "patientdashboard";
		}
		session.setAttribute("indexmsg","logininvalidpasswordorusername");
		return "redirect:/";
	}

	@GetMapping("/hospitalBranchList/{id}")
	public String getBranchPatients(Model model, @PathVariable("id") int hospitalBranchId,
			HospitalBranch hospitalBranch) {
		log.info("inside getAllHospitals this will get the all hospitalsBranch:::");
		HospitalBranch hBranch = hospitalBranchService.getHospitalbranchId(hospitalBranchId);
		List<Patients> patients = patientService.getBranchPatients(hospitalBranch);
		model.addAttribute("patientObj", patients);
		return "patientslist";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("name", null);
		log.info("logout done" + session.getAttributeNames());
		log.info("logout done");
		return "/";
	}

	@RequestMapping(value = "/for", method = RequestMethod.GET)
	public ModelAndView UserforgotPasswordPage(Patients patient) {
		System.out.println("entered into user/controller::::forgot paswword method");
		ModelAndView mav = new ModelAndView("for");
		mav.addObject("patientObj", patient);
		return mav;
	}

	@PostMapping(value = "/valid")
	public String checkMailId(Model model, Patients patient) {
		System.out.println("entered into user/controller::::check EmailId existing or not");
		System.out.println("UI given mail Id:" + patient.getPatientMailId());
		Patients objpatient = patientService.findByMail(patient.getPatientMailId());
		if (objpatient != null) {
			String s1 = "";
			model.addAttribute("message", s1);
			System.out.println("UI given mail Id:" + objpatient.getPatientMailId());
			model.addAttribute("patientObj", patient);
			return "resetdata";
		} else {
			System.out.println("Invalid Mail");
			String s1 = "Email-Id Not Exists";
			model.addAttribute("message", s1);
			model.addAttribute("patientObj", new Patients());
			return "for";
		}
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	public ModelAndView updateUserPassword(Model model, @ModelAttribute("objdoctor") Patients patient) {
		Patients objPatient = patientService.findByMail(patient.getPatientMailId());
		System.out.println("inside updateClientPassword after update id is:::" + patient.getPatientMailId());
		String strEncPassword = VcareUtilies.getEncryptSecurePassword(patient.getPatientPassword(), "vcare");
		patient.setPatientPassword(strEncPassword);
		objPatient.setPatientPassword(patient.getPatientPassword());
		System.out.println(patient.getPatientPassword());
		System.out.println(" in update method Client created date::" + objPatient.getCreated());
		System.out.println("in update method pUser:: name " + objPatient.getFirstName());
		patientService.updatePatient(objPatient);

		System.out.println("password is updated sucessfully");
		ModelAndView mav = new ModelAndView("index");
		System.out.println("login page is displayed");
		model.addAttribute("objdoctor", objPatient);
		model.addAttribute("message","Password changed successfully");
		return mav;
	}
	//@EventListener(ApplicationReadyEvent.class)
	@RequestMapping("/verification")
	public String validatemail(Model model, Patients patientObj, final HttpServletRequest request,
			final HttpServletResponse response,HttpSession session ) throws MessagingException{
		
		if (!patientObj.getCaptcha().equals(patientObj.getUserCaptcha())) {
			session.setAttribute("indexmsg","logininvalidcaptcha");
			return "redirect:/";
		}
		
		final StringBuffer uri = request.getRequestURL();
		
		//HttpSession session1 = request.getSession();
		
		System.out.println(":::::::::::::"+session.getAttribute("url"));
		System.out.println(":::::::::::::::::"+uri);
		System.out.println(":::::::::::::::::"+patientObj.getPatientMailId());
		model.addAttribute("Emailid",patientObj.getPatientMailId());
		SimpleMailMessage mailMessage = new SimpleMailMessage(); 
		System.out.println(":::::::::::::::::2"+patientObj.getPatientMailId());
        mailMessage.setTo(patientObj.getPatientMailId());
        System.out.println("::::::::::::::::: 3"+patientObj.getPatientMailId());
        mailMessage.setSubject("Complete Registration!");
        System.out.println("::::::::::::::::: 4"+patientObj.getPatientMailId());
        mailMessage.setFrom("ajithnetha@gmail.com");
        System.out.println("::::::::::::::::: 5"+patientObj.getPatientMailId());
        mailMessage.setText("To confirm your account, please click here : "
                +session.getAttribute("url")+"/saveuser?patientMailId="+patientObj.getPatientMailId()+"&patientPassword="+patientObj.getPatientPassword());
        System.out.println("::::::::::::::::: 6"+session.getAttribute("url")+"saveuser?patientMailId="+patientObj.getPatientMailId()+"&patientPassword="+patientObj.getPatientPassword());
//        mailMessage.setText("To confirm your account, please click here : "
//        +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());
        patientService.sendSimpleEmail(patientObj.getPatientMailId(),"To confirm your account, please click here : "
                +session.getAttribute("url")+"saveuser?patientMailId="+patientObj.getPatientMailId()+"&patientPassword="+patientObj.getPatientPassword(),"Mailid Verification" );
        //emailSenderService.sendEmail(mailMessage);
        System.out.println("::::::::::::::::: 7"+patientObj.getPatientMailId());
        model.addAttribute("Emailid",patientObj.getPatientMailId());
        System.out.println("::::::::::::::::: 8"+patientObj.getPatientMailId());
        return "successfulRegistration";
}
	@Autowired
	AppointmentService appointmentService;
	@Autowired
	PatientsRepository patientsRepository;
	@PostMapping("/patientupload/{hbid}/{did}/{eid}")
	public String uploadPatient(Model model, Patients patientObj,@RequestParam("slot") String slot,@PathVariable("eid") int empid,@RequestParam("consultantType") String consultantType,@RequestParam("date") String date,
	@PathVariable("did") int did,Appointment appointment,@PathVariable("hbid") int hbid) {
	model.addAttribute("patientObj", patientObj);
	patientService.addNewPatient(patientObj);
	List<Patients> patients=patientsRepository.findPatientforAppointment(patientObj.getPatientMailId(), patientObj.getFirstName(),patientObj.getPatientMobile());
	patientService.updatePatient(patientObj);
	Doctor doctor=doctorService.GetDocotorById(did);
	model.addAttribute("patientObj",patients.get(0));
	
	model.addAttribute("consultantType",consultantType);
	model.addAttribute("doctor",doctor);
	model.addAttribute("hbid",hbid);
	model.addAttribute("slot",slot);
	model.addAttribute("date",date);
	model.addAttribute("employeeId", empid);
	if(patients.get(0).getPatientId()!=0) {
	model.addAttribute("appointment",appointment);
	return "offlineapppointmentform";
	}

	return "patientdashboard";
	}
	

}