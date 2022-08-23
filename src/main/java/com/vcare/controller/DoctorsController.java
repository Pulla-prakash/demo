
package com.vcare.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.vcare.beans.Appointment;
import com.vcare.beans.Doctor;
import com.vcare.beans.DoctorAvailability;
import com.vcare.beans.DoctorBranch;
import com.vcare.beans.HospitalBranch;
import com.vcare.beans.MyQr;
import com.vcare.beans.Patients;
import com.vcare.beans.Prescription;
import com.vcare.beans.Rating;
import com.vcare.beans.Services;
import com.vcare.repository.AppointmentRepository;
import com.vcare.repository.DoctorAvailabilityRepository;
import com.vcare.repository.DoctorBranchRepository;
import com.vcare.repository.DoctorRatingRepository;
import com.vcare.repository.DoctorRepository;
import com.vcare.repository.ServiceRepository;
import com.vcare.service.AppointmentService;
import com.vcare.service.DoctorBranchService;
import com.vcare.service.DoctorImplementation;
import com.vcare.service.DoctorRatingService;
import com.vcare.service.DoctorService;
import com.vcare.service.HospitalBranchService;
import com.vcare.service.PatientsService;
import com.vcare.service.PrescriptionService;
import com.vcare.service.ServiceService;
import com.vcare.utils.VcareUtilies;

@Controller
@RequestMapping("/")
public class DoctorsController {

	@Autowired
	DoctorService doctorservice;
	
	@Autowired
	AppointmentService appointmentService;

	@Autowired
	HospitalBranchService hospitalBranchService;

	@Autowired
	ServiceService serServices;

	@Autowired
	DoctorBranchService doctorBranchService;

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired

	DoctorBranchRepository doctorBranchRepository;

	@Autowired
	DoctorRatingRepository doctorRatingRepository;
	@Autowired
	DoctorRatingService doctorRatingService;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	DoctorImplementation doctorImplementation;

	@Autowired
	DoctorAvailabilityRepository doctorAvailabilityRepository;

	@Autowired
	AppointmentRepository appointmentRepository;

	static Logger log = Logger.getLogger(DoctorsController.class.getClass());

	@GetMapping("/Drlist")
	public String viewAllDoctrs(Model model) {
		log.info("==========this is Doctors list==========");

		model.addAttribute("doctorlist", doctorservice.getAllDoctor());
		List<Doctor> docList = doctorservice.getAllDoctor();

		List<Doctor> list = new ArrayList<>();
		for (Doctor doctor : docList) {
			if (doctor.getIsActive() == 'Y' || doctor.getIsActive() == 'y') {
				list.add(doctor);
			}
		}
		model.addAttribute("doctorlist", list);
		return "newdoctors";
	}

	@GetMapping("/deletedoctors/{Id}")
	public String deletedoctors(Model model, @PathVariable(value = "Id") int doctorId) {
		log.info("===========This is delete method===========");

		Doctor inactive = doctorservice.GetDocotorById(doctorId);
		inactive.setIsActive('N');
		doctorservice.updateDoctor(inactive);
		log.info("doctor is delete by id no :" + doctorId);
		model.addAttribute("doctorlist", doctorservice.getAllDoctor());
		return "redirect:/Drlist";
	}

	@RequestMapping(value = "/adddoctor", method = RequestMethod.GET)
	public String showNewdoctorsForm(Model model) {
		log.info("==========Adding doctor=============");

		Doctor doctorObj = new Doctor();

		Map<Integer, String> HospitalBranchMap = dropDownHsptlBranch();
		Map<Integer, String> ServieMap = dropDownServices();

		model.addAttribute("objdoctor", doctorObj);
		model.addAttribute("name", doctorObj.getDoctorName());
		log.info("doctor name is  :" + doctorObj.getDoctorName());
		model.addAttribute("HospitalBranchMap", HospitalBranchMap);
		model.addAttribute("ServieMap", ServieMap);

		model.addAttribute("hospitalBranchId", HospitalBranchMap.keySet());
		model.addAttribute("serviceId", ServieMap.keySet());

		log.info("doctor email id is  :  " + doctorObj.getDoctorMailId());
		return "Drlist";

	}

	@RequestMapping(value = "/savedoctors", method = RequestMethod.POST)
	public String addNews(Model model, Doctor doctorObj) {
		log.info("=========== Doctors save method=======");
		log.info("new record:set id: :" + doctorObj.getDoctorId());

		doctorObj.setCreated(LocalDate.now());

		if (doctorObj.getDoctorId() != 0) {
			log.info("inside new doctor saving :::" + doctorObj.getDoctorId());

			model.addAttribute("objdoctor", doctorObj);
			doctorservice.addDoctor(doctorObj);
		} else {
			model.addAttribute("objdoctor", doctorObj);
			doctorservice.updateDoctor(doctorObj);
		}
		List<Doctor> doctorlist = doctorservice.getAllDoctor();
		model.addAttribute("doctorlist", doctorlist);
		return "newdoctors";
	}

	@RequestMapping(value = "/editdoctor/{Id}", method = RequestMethod.GET)
	public String editDoctor(Model model, @PathVariable(value = "Id") int doctorId) {
		log.info("============== Doctor Edit Method===============");
		Doctor doctorObj = doctorservice.GetDocotorById(doctorId);
		log.info(" Updating Doctor by DoctorId  : " + doctorId);
		doctorservice.updateDoctor(doctorObj);

		model.addAttribute("objdoctor", doctorObj);
		model.addAttribute("doctorId", doctorId);
		log.info("this is doctorid :  " + doctorObj.getDoctorId());
		Map<Integer, String> HospitalBranchMap = dropDownHsptlBranch();
		Map<Integer, String> ServieMap = dropDownServices();
		model.addAttribute("HospitalBranchMap", HospitalBranchMap);
		model.addAttribute("ServieMap", ServieMap);
		model.addAttribute("hospitalbranchId", HospitalBranchMap.keySet());
		model.addAttribute("serviceId", ServieMap.keySet());
		model.addAttribute("name", doctorObj.getDoctorName());
		model.addAttribute("objMailId", doctorObj.getDoctorMailId());
		model.addAttribute("pswrd", doctorObj.getPassword());
		log.info("doctor profile : " + doctorObj);
		return "doctorprofile";
	}

	@GetMapping("/ViewDoctors")

	public String viewDoctrs(Model model) {
		log.info("============Doctors list For View============");

		model.addAttribute("doctorlist", doctorservice.getAllDoctor());
		return "viewdoctors";
	}

	@GetMapping("/ViewDoctors/{id}")
	// All Doctors in a branch
	public String viewDoctrs(Model model, @PathVariable("id") int id) {

		List<Doctor> Dlist = doctorservice.getAllDoctor();
		int count = Dlist.size();
		List<Doctor> doc = new ArrayList<>();
		model.addAttribute("doctorlist", doctorservice.getAllDoctor());
		return "viewdoctors";
	}

	@Autowired
	PatientsService patientsService;

	// Appointment
	@GetMapping("/ViewDoctors/{pid}/{bid}")
	public String ViewDoctors(Model model, @PathVariable("pid") int pid, @PathVariable("bid") int bid) {

		Patients patient = patientsService.getPatientById(pid);
		model.addAttribute("pid", patient.getPatientId());

		List<Doctor> Dlist = doctorservice.getAllDoctor();
		int count = Dlist.size();
		List<Doctor> doc = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			String hbidlist = Dlist.get(i).getHospitalBranchId();
			System.out.println(hbidlist);
			String[] arrhblist = hbidlist.split(",");

			for (String blist : arrhblist) {
				int hbid = Integer.parseInt(blist);
				if (bid == hbid) {

					int did = Dlist.get(i).getDoctorId();
					Doctor oneEntry = doctorservice.GetDocotorById(did);
					doc.add(oneEntry);
				}
			}
		}
		model.addAttribute("doctorlist", doc);
		return "pviewdoctors";

	}

	@GetMapping("/Viewdoctors/{doctorId}")
	public String ViewDoctor(Model model, @PathVariable(value = "doctorId") int doctorId) {
		log.info("===========select doctor==============");
		Doctor doctorObj = doctorservice.GetDocotorById(doctorId);
		log.info("DoctorId  : " + doctorObj);
		model.addAttribute("objViewdoctor", doctorObj.getDoctorName());
		/* model.addAttribute("objBranch",doctorObj.getDoctorBranch()); */
		model.addAttribute("objExperience", doctorObj.getDoctorExperience());
		model.addAttribute("objMailId", doctorObj.getDoctorMailId());
		model.addAttribute("objMobileNo", doctorObj.getDoctorMobileNo());
		model.addAttribute("objSpecialization", doctorObj.getDoctorSpecialization());
		model.addAttribute("fee", doctorObj.getDoctorFee());
		return "selectdoctors";
	}

	// after appointments screen in patient_appointment form
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/Viewdoctors/{hbid}/{pid}/{doctorId}")
	public String viewdoctor(Model model, @PathVariable("pid") int pid, @PathVariable(value = "doctorId") int doctorId,
			@PathVariable(value = "hbid") int hbid, Rating drating, Date timeStart, Date timeEnd,
			HttpServletRequest request) throws Exception {

		Patients patient = patientsService.getPatientById(pid);
		model.addAttribute("pid", patient.getPatientId());
		Doctor doc = doctorservice.GetDocotorById(doctorId);
		List<Rating> rating = doctorRatingRepository.findAllRatingsInDescOrder();// doctorRatingService.GetAllDoctorRating();
		model.addAttribute("patientname", patient.getFirstName());
		List<Rating> list = new ArrayList<>();
		for (Rating r : rating) {
			if (doc.getDoctorId() == r.getDoctor().getDoctorId()) {
				Rating rate = doctorRatingService.getDoctorRatingById(r.getDoctorRatingId());
				list.add(rate);
			}
		}
		model.addAttribute("pi", patient);
		model.addAttribute("pid", pid);
		model.addAttribute("id", doctorId);
		model.addAttribute("rating", list);
		model.addAttribute("drating", drating);
		model.addAttribute("doc", doc);
		model.addAttribute("hbid", hbid);

		double average;
		long totalrating;
		// Getting Average rating for doctor
		if (doctorRatingRepository.sumOfRating(doc.getDoctorId()) == null) {
			totalrating = 0;
			average = 0.0;
			model.addAttribute("average", average);
		} else {
			totalrating = doctorRatingRepository.sumOfRating(doc.getDoctorId());// Sum of ratings

			List<Rating> pcount = doctorRatingService.GetAllDoctorRating();// total entries
			long count = pcount.size(); // no.of patients given rating
			average = (totalrating / count);
			model.addAttribute("average", Math.ceil(average));
		}
		// getting no.of persons according to rating
		Long fivecount = doctorRatingRepository.fivecount(doc.getDoctorId());
		model.addAttribute("fivecount", fivecount);
		Long fourcount = doctorRatingRepository.fourcount(doc.getDoctorId());
		model.addAttribute("fourcount", fourcount);
		Long threecount = doctorRatingRepository.threecount(doc.getDoctorId());
		model.addAttribute("threecount", threecount);
		Long twocount = doctorRatingRepository.twocount(doc.getDoctorId());
		model.addAttribute("twocount", twocount);
		Long onecount = doctorRatingRepository.onecount(doc.getDoctorId());
		model.addAttribute("onecount", onecount);
		Long totalReviews=doctorRatingRepository.totalReviews(doc.getDoctorId());
        model.addAttribute("totalreviews", totalReviews);

		// Getting DoctorAvailability records by doctorID
		List<DoctorAvailability> docAvailable = doctorAvailabilityRepository.availableDoctor(doctorId);
		System.out.println("StartTimings==========" + docAvailable.get(0).getStartTimings());
		// Formatting the date from html format to java
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		DateFormat formatterDay = new SimpleDateFormat("EEE");// gets week days as MON,TUE....
		// getting start and end timings from DoctorAvailability and setting to Date
		System.out.println("formatter.parse(docAvailable.get(0).getStartTimings())===="
				+ formatter.parse(docAvailable.get(0).getStartTimings()));
		timeStart = (Date) formatter.parse(docAvailable.get(0).getStartTimings());
		timeEnd = (Date) formatter.parse(docAvailable.get(0).getEndTimings());

		// Separating Start and End Dates to year,month,date
		LocalDate sd = LocalDate.of(timeStart.getYear() + 1900, timeStart.getMonth() + 1, timeStart.getDate());
		LocalDate ed = LocalDate.of(timeEnd.getYear() + 1900, timeEnd.getMonth() + 1, timeEnd.getDate());
		System.out.println("sdf" + formatterDay.format(timeStart));
		List<String> times = new ArrayList<>();// To store slots
		Calendar calendar = Calendar.getInstance();// get present calendar
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

		int minutes = calendar.get(Calendar.MINUTE);
		// to Store dates
		List<LocalDate> datesInRange = new ArrayList<>();
		//

		List<String> monthdate = new ArrayList<>();
		/* HashMap<String, ArrayList<String>> map = new HashMap<>(); */
		times = new ArrayList<>();
		System.out.println("============" + sd);

		// stores only hour of endtime
		int endHour = timeEnd.getHours();

		// Present calender for getting todays data
		Calendar calObj = Calendar.getInstance();
		// Gets listOfAppointmnetByDoctorId&Date
		List<Appointment> app = appointmentRepository.appointmentListByDoctorid(doctorId, request.getParameter("date"));
		// For storing the booked slots as string
		ArrayList<String> totalSlots = new ArrayList<>();
		for (Appointment a : app) {
			totalSlots.add(a.getSlot());
		}
//		app.forEach((a)->{
//			totalSlots.add(a.getSlot());
//		});

		ArrayList<String> timeList = new ArrayList<>();
		// Dates drop-down according to DoctorAvailability
		while (sd.isBefore(ed) || sd.equals(ed)) {
			datesInRange.add(sd);

			String day = sd.format(DateTimeFormatter.ofPattern("EEE"));
			String month = sd.format(DateTimeFormatter.ofPattern("MMM"));
			String s = Integer.toString(sd.getDayOfMonth());
			if (sd.isAfter(LocalDate.now()) || sd.isEqual(LocalDate.now())) {
				String slot = month.toString() + s;
				monthdate.add(sd.toString());
				sd = sd.plusDays(1);
				model.addAttribute("date", monthdate);
			} else {
				sd = sd.plusDays(1);
			}

		}

		model.addAttribute("times", timeList);
		model.addAttribute("monthdate", monthdate);

		return "doctorsscreen";

	}
	
	@RequestMapping("/validatEmail")
    public String validatEmail(Model model, Doctor doctorObj, final HttpServletRequest request,
            final HttpServletResponse response,HttpSession session ) throws MessagingException{
        final StringBuffer uri = request.getRequestURL();

        if (!doctorObj.getCaptchaOne().equals(doctorObj.getUserCaptcha())) {
			session.setAttribute("indexmsg","logininvalidcaptcha");
			return "redirect:/";
		}
        model.addAttribute("Emailid",doctorObj.getDoctorMailId());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        System.out.println(":::::::::::::::::2"+doctorObj.getDoctorMailId());
        mailMessage.setTo(doctorObj.getDoctorMailId());
        System.out.println("::::::::::::::::: 3"+doctorObj.getDoctorMailId());
        mailMessage.setSubject("Doctor Consultation");
        System.out.println("::::::::::::::::: 4"+doctorObj.getDoctorMailId());
        System.out.println("::::::::::::::::: 5"+doctorObj.getDoctorMailId());
        doctorservice.sendSimpleEmail(doctorObj.getDoctorMailId(),"To consult the doctor, please click here in your slot time: "
                +session.getAttribute("url")+"saveDoc?doctorMailId="+doctorObj.getDoctorMailId()+"&password="+doctorObj.getPassword(),"Mailid Verification" );
        //emailSenderService.sendEmail(mailMessage);
        System.out.println("::::::::::::::::: 7"+doctorObj.getDoctorMailId());
        model.addAttribute("Emailid",doctorObj.getDoctorMailId());
        System.out.println("::::::::::::::::: 8"+doctorObj.getDoctorMailId());
        session.setAttribute("DocId", doctorObj.getDoctorId());
        return "successfulRegistration";
}

	@RequestMapping(value = "/saveDoc", method = RequestMethod.GET)
	public String addDoctor(Model model, @ModelAttribute(value = "objdoctor") Doctor doctorObj,
			@RequestParam(name = "serviceList", required = false) List<String> serviceList,HttpServletRequest request) {
		log.info("================ Save Doctor Method==========");
		doctorObj.setIsActive('Y');
		log.info(doctorObj.getDoctorMailId());

		String objectDoctor = doctorservice.validateduplicate(doctorObj.getDoctorMailId());
		HttpSession session =request.getSession();
		if (objectDoctor == null) {
			model.addAttribute("objdoctor", doctorObj);
			log.info("Doctro Id  :  " + doctorObj.getDoctorId());
			String strEncPassword = VcareUtilies.getEncryptSecurePassword(doctorObj.getPassword(), "vcare");
			doctorObj.setPassword(strEncPassword);

			Map<Integer, String> HospitalBranchMap = dropDownHsptlBranch();
			Map<Integer, String> ServieMap = dropDownServices();
			model.addAttribute("HospitalBranchMap", HospitalBranchMap);
			model.addAttribute("ServieMap", ServieMap);

			model.addAttribute("hospitalBranchId", HospitalBranchMap.keySet());
			model.addAttribute("serviceId", ServieMap.keySet());

			List<String> serviceNames = serviceRepository.findDistinctService(serviceList);
			model.addAttribute("serviceList", serviceNames);
			model.addAttribute("selectedService", serviceNames.size());
			log.info("Doctor MailId & Password  : " + objectDoctor);

			log.info("Doctro Password  : " + doctorObj.getPassword());
			doctorservice.addDoctor(doctorObj);
			session.setAttribute("DocId",doctorObj.getDoctorId());
			;
			model.addAttribute("doctormsg","Successfully Registered, Please complete your profile");
			return "doctorprofile";
		} else {
			session.setAttribute("indexmsg","save");
			return "redirect:/";
		}

	}

    @RequestMapping("/selectBranch")
    public String selectBranch(Model model, Doctor objdoctor, final HttpServletRequest request,
            @RequestParam(name = "serviceList", required = false) List<String> serviceList, HttpSession session) {
        String getQry = request.getParameter("getQry");
        String serviceName = request.getParameter("serviceName");
        System.out.println(getQry + "++++++++" + serviceName);
        if (getQry != null && getQry.equals("selectBranch") && serviceName != null && serviceName != "") {
            List<Services> servicelist = serviceRepository.findService(serviceName);// method in repository
            List<Integer> bid = new ArrayList<>();// for storing hospital PKs
            for (Services slist : servicelist) {
                bid.add(slist.getHospitalbranch().getHospitalBranchId());// Adds One by one id to list
            }
            List<HospitalBranch> hblist = new ArrayList<>();// To store rows of matched service branches
            for (Integer serid : bid) {
                // getting hospital by id
                HospitalBranch hospitalBranchById = hospitalBranchService.getHospitalbranchId(serid);
                // adding hospital list by id to list
                	hblist.add(hospitalBranchById);
            }
            List<String> serviceNames = serviceRepository.findDistinctService(serviceList);
            model.addAttribute("serviceList", serviceNames);
            model.addAttribute("selectedService", serviceNames.size());
            model.addAttribute("objdoctor", objdoctor);
            model.addAttribute("hblist", hblist);
            model.addAttribute("ServiceName", serviceName);
        }
        model.addAttribute("doctormsg","registered successfully, Please complete your profile");
        return "doctorprofile";
    }
	@GetMapping("/valid")
	public String Validation(Model model, @ModelAttribute(value = "objectdoctor") Doctor doctorObj,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!doctorObj.getCaptchaOne().equals(doctorObj.getUserCaptcha())) {
			session.setAttribute("indexmsg","logininvalidcaptcha");
			return "redirect:/";
		}
		log.info("Get doctor name : " + doctorObj.getDoctorName());
		log.info("get doctor password  : " + doctorObj.getPassword());
		String str = VcareUtilies.getEncryptSecurePassword(doctorObj.getPassword(), "vcare");
		Doctor objDoc;
		objDoc = doctorservice.getDoctors(doctorObj.getDoctorMailId(), str);
		log.info("validation details : " + objDoc);
		model.addAttribute("objdoctor", objDoc);
		if (objDoc != null) {
			log.info("===========login successfully======");
			session.setAttribute("drname", objDoc.getDoctorName());
			session.setAttribute("DocId", objDoc.getDoctorId());
			return "dashboard";
		} else {
			session.setAttribute("indexmsg","logininvalidpasswordorusername");
			return "redirect:/";
		}

	}

	@GetMapping("/uploadDoctorProfile/{did}")
	public String uploaddoctorProfile(Model model, @PathVariable("did") int did, Doctor doctorObj) {
		log.info("============login completed==========");
		model.addAttribute("objdoctor", doctorObj);
		doctorservice.updateDoctor(doctorObj);
		log.info("DoctorId after login : " + doctorObj.getDoctorId());

		model.addAttribute("objdoctor", doctorservice.GetDocotorById(did));
		model.addAttribute("did", doctorservice.GetDocotorById(did).getDoctorId());

		return "completeprofile";
	}

	   @PostMapping("/uploadDoctorProfile")
	    public String uploaddoctorProfiles(Model model, Doctor doctorObj, @RequestParam("file") MultipartFile file,
	            @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
	            final HttpServletRequest request,@RequestParam(name = "serviceList", required = false) List<String> serviceList) throws IOException, Exception {
	        log.info("===========regestration completed============");
	        doctorObj.setIsActive('y');
	        System.out.println(doctorObj.getHospitalBranchId());
	        String data = doctorObj.toString();
	        String path = "C:\\Users\\Lenovo\\Documents\\workspace-spring-tool-suite-4-4.14.0.RELEASE\\Vcare\\src\\main\\resources\\static\\images\\qr"
	                + doctorObj.getDoctorId() + ".png";
	        String charset = "UTF-8";
	        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
	        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	        byte[] d = MyQr.createQR(data, path, charset, hashMap, 200, 200);
	        doctorObj.setDocQrcode(Base64.getEncoder().encodeToString(d));
	        if(file.getOriginalFilename()=="") {
	        	doctorObj.setProfilepic(doctorObj.getProfilepic());
	        }else {
	        doctorObj.setProfilepic(Base64.getEncoder().encodeToString(file.getBytes()));
	   }
	        doctorObj.setImage(Base64.getEncoder().encodeToString(file1.getBytes()));
	        doctorObj.setVideo(Base64.getEncoder().encodeToString(file2.getBytes()));
	        doctorservice.updateDoctor(doctorObj);
	        if (doctorObj.getHospitalBranchId() == null) {
	            model.addAttribute("objdoctor", doctorObj);
	            List<String> serviceNames = serviceRepository.findDistinctService(serviceList);
	            model.addAttribute("serviceList", serviceNames);
	            return "doctorprofile";
	        } else {
	            Doctor doctor = doctorRepository.findByFields(doctorObj.getDoctorMailId(), doctorObj.getDoctorMobileNo(),
	                    doctorObj.getMedicallicense());
	            doctorBranchRepository.doctorBranchDeleteById((long) doctor.getDoctorId());
	            String hbidlist = doctorObj.getHospitalBranchId();
	            String[] arrhblist = hbidlist.split(",");
	            List<HospitalBranch> hblist=new ArrayList<HospitalBranch>();
	            for (String blist : arrhblist) {
	                int hid = Integer.parseInt(blist);
	                DoctorBranch docBranch = new DoctorBranch();
	                docBranch.setHospitalId(hid);
	                docBranch.setDoctorId(doctor.getDoctorId());
	                doctorBranchService.addDoctorBranch(docBranch);
	                HospitalBranch hbObj=hospitalBranchService.getHospitalbranchId(hid);
	                hblist.add(hbObj);
	            }
	            model.addAttribute("hblist",hblist);
	        }
	        model.addAttribute("objdoctor", doctorObj);
	        log.info("doctorid after regestration  : " + doctorObj.getDoctorId());
	        //new
	        List<String> serviceNames = serviceRepository.findDistinctService(serviceList);
	        model.addAttribute("serviceList", serviceNames);
	        model.addAttribute("selectedService",serviceNames.size());
	        model.addAttribute("ServiceName",doctorObj.getServiceName());
	        model.addAttribute("doctormsg","Dr."+doctorObj.getDoctorName()+" your profile Successfully updated");
	        //new end
	        return "doctorprofile";
	    }

	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView UserforgotPasswordPage(Doctor doctorObj) {
		System.out.println("entered into user/controller::::forgot paswword method");
		ModelAndView mav = new ModelAndView("forgot");
		mav.addObject("objdoctor", doctorObj);
		return mav;
	}

	@PostMapping(value = "/validate")
	public String checkMailId(Model model, Doctor doctorObj) {
		model.addAttribute("warninig", "invalid credentials..please try to login again...!");
		System.out.println("entered into user/controller::::check EmailId existing or not");
		System.out.println("UI given mail Id:" + doctorObj.getDoctorMailId());
		Doctor objdoc = doctorservice.findByMail(doctorObj.getDoctorMailId());
		if (objdoc != null) {
			String s1 = "";
			model.addAttribute("message", s1);
			System.out.println("UI given mail Id:" + objdoc.getDoctorMailId());
			model.addAttribute("objdoctor", doctorObj);
			return "reset";
		} else {
			System.out.println("Invalid Mail");
			String s1 = "Email-Id Not Exists";
			model.addAttribute("message", s1);
			model.addAttribute("objdoctor", new Doctor());
			return "forgot";
		}
	}

	@RequestMapping(value = "/updateClient", method = RequestMethod.POST)
	public ModelAndView updateUserPassword(Model model, @ModelAttribute("objdoctor") Doctor doctorObj) {
		Doctor pUser = doctorservice.findByMail(doctorObj.getDoctorMailId());
		System.out.println("inside updateClientPassword after update id is:::" + doctorObj.getDoctorId());
		String strEncPassword = VcareUtilies.getEncryptSecurePassword(doctorObj.getPassword(), "vcare");
		doctorObj.setPassword(strEncPassword);
		pUser.setPassword(doctorObj.getPassword());
		System.out.println(doctorObj.getPassword());
		System.out.println(" in update method Client created date::" + pUser.getCreated());
		System.out.println("in update method pUser:: name " + pUser.getDoctorName());
		doctorservice.updateDoctor(pUser);
		// pUser.setUpdatedDate(LocalDateTime.now());
		System.out.println("password is updated sucessfully");
		ModelAndView mav = new ModelAndView("index");
		System.out.println("login page is displayed");
		model.addAttribute("objdoctor", pUser);
		return mav;
	}

	@GetMapping("/updatedDoctorProfile/{did}")
    public String viewUodateddoctorProfile(Model model, @PathVariable("did") int did,
            @RequestParam(name = "serviceList", required = false) List<String> serviceList) {
        Doctor doctorObj = doctorservice.GetDocotorById(did);
        model.addAttribute("objdoctor", doctorObj);
        Map<Integer, String> HospitalBranchMap = dropDownHsptlBranch();
        Map<Integer, String> ServieMap = dropDownServices();
        log.info(ServieMap);
        List<String> serviceNames = serviceRepository.findDistinctService(serviceList);
        String hbidlist = doctorObj.getHospitalBranchId();
        List<HospitalBranch> hblist = new ArrayList<HospitalBranch>();
        if (hbidlist == null) {
            model.addAttribute("hblist", hospitalBranchService.getAllHospitalbranch().get(0));

        } else {

            String[] arrhblist = hbidlist.split(",");
            if (arrhblist.length == 0) {
                HospitalBranch hd = hospitalBranchService.getHospitalbranchId(Integer.parseInt(hbidlist));
                hblist.add(hd);
            } else {
                for (String blist : arrhblist) {
                    int hid = Integer.parseInt(blist);
                    DoctorBranch docBranch = new DoctorBranch();
                    docBranch.setHospitalId(hid);
                    docBranch.setDoctorId(doctorObj.getDoctorId());
                    doctorBranchService.addDoctorBranch(docBranch);
                    HospitalBranch hbObj = hospitalBranchService.getHospitalbranchId(hid);
                    hblist.add(hbObj);
                }
                model.addAttribute("hblist",hblist);
            }
        }

model.addAttribute("objdoctor",doctorObj);
log.info("doctorid after regestration  : "+doctorObj.getDoctorId());
    model.addAttribute("ServiceName",doctorObj.getServiceName());

    model.addAttribute("serviceList",serviceNames);
    model.addAttribute("selectedService",serviceNames.size());

    model.addAttribute("HospitalBranchMap",HospitalBranchMap);
    model.addAttribute("ServieMap",ServieMap);
    model.addAttribute("name",doctorObj.getDoctorName());
    model.addAttribute("objMailId",doctorObj.getDoctorMailId());
    model.addAttribute("hospitalBranchId",HospitalBranchMap.keySet());
    model.addAttribute("serviceId",ServieMap.keySet());

    return"doctorprofile";
    }

	@GetMapping("/Viewdoctors/{pid}/{doctorId}")
    public String viewdoctor(Model model, @PathVariable("pid") int pid, @PathVariable(value = "doctorId") int doctorId,
            Date timeStart, Date timeEnd, Rating drating, HttpServletRequest request) throws Exception {
        Patients patient = patientsService.getPatientById(pid);
        model.addAttribute("pid", patient.getPatientId());
        Doctor doc = doctorservice.GetDocotorById(doctorId);
        List<Rating> rating = doctorRatingRepository.findAllRatingsInDescOrder();// doctorRatingService.GetAllDoctorRating();
        model.addAttribute("patientname", patient.getFirstName());
        List<Rating> list = new ArrayList<>();
        for (Rating r : rating) {
            if (doc.getDoctorId() == r.getDoctor().getDoctorId()) {
                Rating rate = doctorRatingService.getDoctorRatingById(r.getDoctorRatingId());
                list.add(rate);
            }
        }
        model.addAttribute("pi", patient);
        model.addAttribute("pid", pid);
        model.addAttribute("id", doctorId);
        model.addAttribute("rating", list);
        model.addAttribute("drating", drating);
        model.addAttribute("doc", doc);
        double average;
        long totalrating;
        // Getting Average rating for doctor
        if (doctorRatingRepository.sumOfRating(doc.getDoctorId()) == null) {
            totalrating = 0;
            average = 0.0;
            model.addAttribute("average", average);
        } else {
            totalrating = doctorRatingRepository.sumOfRating(doc.getDoctorId());// Sum of ratings
            List<Rating> pcount = doctorRatingService.GetAllDoctorRating();
            // total entries
            long count = pcount.size(); // no.of patients given rating
            average = (totalrating / count);
            model.addAttribute("average", Math.ceil(average));
        }
        // getting no.of persons according to rating
        Long fivecount = doctorRatingRepository.fivecount(doc.getDoctorId());
        model.addAttribute("fivecount", fivecount);
        Long fourcount = doctorRatingRepository.fourcount(doc.getDoctorId());
        model.addAttribute("fourcount", fourcount);
        Long threecount = doctorRatingRepository.threecount(doc.getDoctorId());
        model.addAttribute("threecount", threecount);
        Long twocount = doctorRatingRepository.twocount(doc.getDoctorId());
        model.addAttribute("twocount", twocount);
        Long onecount = doctorRatingRepository.onecount(doc.getDoctorId());
        model.addAttribute("onecount", onecount);
        List<DoctorAvailability> docAvailable = doctorAvailabilityRepository.availableDoctor(doctorId);
        // for(DoctorAvailability dAvail:docAvailable) {
        System.out.println("StartTimings==========" + docAvailable.get(0).getStartTimings());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        DateFormat formatterDay = new SimpleDateFormat("EEE");
        timeStart = (Date) formatter.parse(docAvailable.get(0).getStartTimings());
        timeEnd = (Date) formatter.parse(docAvailable.get(0).getEndTimings());
        System.out.println("start" + timeStart.getHours());
        LocalDate sd = LocalDate.of(timeStart.getYear() + 1900, timeStart.getMonth() + 1, timeStart.getDate());
        // LocalDate sd=LocalDate.of(2022,05,20);
        LocalDate ed = LocalDate.of(timeEnd.getYear() + 1900, timeEnd.getMonth() + 1, timeEnd.getDate());
        // LocalDate ed=LocalDate.of(2022,05,31);
        // java.time.DayOfWeek dw=ld.getDayOfWeek();
        // System.out.println("Ld"+dw.toString());
        System.out.println("sdf" + formatterDay.format(timeStart));
        // }
        List<String> times = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        int ti = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        // System.out.println(LocalDate.now());
        List<LocalDate> datesInRange = new ArrayList<>();
        List<String> slots = new ArrayList<>();
        System.out.println(minutes);
        String[] quarterHours = { "00", "30", };
        boolean isflag = false;
        List<String> days = new ArrayList<>();
        List<String> monthdate = new ArrayList<>();
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        times = new ArrayList<>();
        System.out.println("============" + sd);
        int endHour = timeEnd.getHours();
        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, timeStart.getHours());
        cal.set(Calendar.MINUTE, timeStart.getMinutes());
        cal.set(Calendar.SECOND, 0);

        while (sd.isBefore(ed)) {
            datesInRange.add(sd);
            sd = sd.plusDays(1);
            String day = sd.format(DateTimeFormatter.ofPattern("EEE"));
            String month = sd.format(DateTimeFormatter.ofPattern("MMM"));
            System.out.println(day);
            System.out.println(sd);
            System.out.println(month);
            String s = Integer.toString(sd.getDayOfMonth());
            if (sd.isAfter(LocalDate.now()) || sd.isEqual(LocalDate.now())) {
                days.add(day);
                slots.add(month);
                slots.add(s);
                String slot = month.toString() + s;
                monthdate.add(sd.toString());
                model.addAttribute("date", monthdate);
            }
        }
        int count = 0;
        for (int i = timeStart.getHours(); i < timeEnd.getHours(); i++) {
            if (ti > 1) {
                for (int j = 0; j < 2; j++) {
                    if ((i == ti && minutes < Integer.parseInt(quarterHours[j])) || (i != ti) || isflag == true) {
                        System.out.println("Integer.parseInt(quarterHours[j])=" + Integer.parseInt(quarterHours[j]));
                        System.out.println("minutes=" + minutes);
                        System.out.println("ti=" + ti);
                        isflag = true;
                        String time = i + ":" + quarterHours[j];
                        if (i < 24) {
                            time = "" + time;
                        }
                        String hourFormat = i + ":" + quarterHours[j];
                        if (i < 12) {
                            hourFormat = time + " AM";
                        } else
                            hourFormat = time + " PM";
                        String timeslot = hourFormat;
                        System.out.println("++++++++++" + timeslot);
                        times.add(timeslot);
                    }
                    map.put(sd.toString(), (ArrayList<String>) times);
                }
            }
            System.out.println("============count=============" + count);
            model.addAttribute("count", count);
        }
        model.addAttribute("times", "");
        model.addAttribute("map", map);
        model.addAttribute("monthdate", monthdate);
        model.addAttribute("days", days);
        model.addAttribute("selectedDate", "");

        String[] s=doc.getHospitalBranchId().split(",");
        int HospitalBranchId=0;
        if(s.length==0) {
            HospitalBranchId=Integer.parseInt(doc.getHospitalBranchId());
        }
        else {
            for(String st:s) {
                HospitalBranchId=Integer.parseInt(st);
                break;
            }
        }
        return "redirect:/Viewdoctors/"+HospitalBranchId+"/{pid}/{doctorId}";

    }
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/Viewdoctors")
	public String viewdoctorSelectedDropdown(Model model, Date timeStart, Date timeEnd, Rating drating,
			HttpServletRequest request) throws Exception {
		String doctorId = request.getParameter("doctorId");
		String pid = request.getParameter("patientId");
		String hospitalBranchId = request.getParameter("hbid");
		model.addAttribute("hbid", Integer.parseInt(hospitalBranchId));
		Patients patient = patientsService.getPatientById(Integer.parseInt(pid));
		model.addAttribute("pid", patient.getPatientId());
		Doctor doc = doctorservice.GetDocotorById(Integer.parseInt(doctorId));
		List<Rating> rating = doctorRatingRepository.findAllRatingsInDescOrder();// doctorRatingService.GetAllDoctorRating();
		model.addAttribute("patientname", patient.getFirstName());
		List<Rating> list = new ArrayList<>();
		for (Rating r : rating) {
			if (doc.getDoctorId() == r.getDoctor().getDoctorId()) {
				Rating rate = doctorRatingService.getDoctorRatingById(r.getDoctorRatingId());
				list.add(rate);
			}
		}
		model.addAttribute("pi", patient);
		model.addAttribute("pid", pid);
		model.addAttribute("id", doctorId);
		model.addAttribute("rating", list);
		model.addAttribute("drating", drating);
		model.addAttribute("doc", doc);
		double average;
		long totalrating;
		// Getting Average rating for doctor
		if (doctorRatingRepository.sumOfRating(doc.getDoctorId()) == null) {
			totalrating = 0;
			average = 0.0;
			model.addAttribute("average", average);
		} else {
			totalrating = doctorRatingRepository.sumOfRating(doc.getDoctorId());// Sum of ratings
			List<Rating> pcount = doctorRatingService.GetAllDoctorRating();// total entries
			long count = pcount.size(); // no.of patients given rating
			average = (totalrating / count);
			model.addAttribute("average", Math.ceil(average));
		}
		// getting no.of persons according to rating
		Long fivecount = doctorRatingRepository.fivecount(doc.getDoctorId());
		model.addAttribute("fivecount", fivecount);
		Long fourcount = doctorRatingRepository.fourcount(doc.getDoctorId());
		model.addAttribute("fourcount", fourcount);
		Long threecount = doctorRatingRepository.threecount(doc.getDoctorId());
		model.addAttribute("threecount", threecount);
		Long twocount = doctorRatingRepository.twocount(doc.getDoctorId());
		model.addAttribute("twocount", twocount);
		Long onecount = doctorRatingRepository.onecount(doc.getDoctorId());
		model.addAttribute("onecount", onecount);
		// Getting DoctorAvailability records by doctorID
		List<DoctorAvailability> docAvailable = doctorAvailabilityRepository
				.availableDoctor(Integer.parseInt(doctorId));
		System.out.println("StartTimings==========" + docAvailable.get(0).getStartTimings());
		// Formatting the date from html format to java
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		DateFormat formatterDay = new SimpleDateFormat("EEE");// gets week days as MON,TUE....
		// getting start and end timings from DoctorAvailability and setting to Date
		timeStart = (Date) formatter.parse(docAvailable.get(0).getStartTimings());
		timeEnd = (Date) formatter.parse(docAvailable.get(0).getEndTimings());
		// Separating Start and End Dates to year,month,date
		LocalDate sd = LocalDate.of(timeStart.getYear() + 1900, timeStart.getMonth() + 1, timeStart.getDate());
		LocalDate ed = LocalDate.of(timeEnd.getYear() + 1900, timeEnd.getMonth() + 1, timeEnd.getDate());
		System.out.println("sdf" + formatterDay.format(timeStart));
		List<String> times = new ArrayList<>();// To store slots
		Calendar calendar = Calendar.getInstance();// get present calendar
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
		int minutes = calendar.get(Calendar.MINUTE);
		// to Store dates
		List<LocalDate> datesInRange = new ArrayList<>();
		//
		List<String> slots = new ArrayList<>();
		// to store week days
		List<String> days = new ArrayList<>();
		// to store dates
		List<String> monthdate = new ArrayList<>();
		/* HashMap<String, ArrayList<String>> map = new HashMap<>(); */
		times = new ArrayList<>();
		System.out.println("============" + sd);
		// stores only hour of endtime
		int endHour = timeEnd.getHours();
		// Setting the time to Hours:Minutes
		DateFormat df = new SimpleDateFormat("HH:mm");
		// sets start-time hour and minutes and seconds
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, timeStart.getHours());
		cal.set(Calendar.MINUTE, timeStart.getMinutes());
		cal.set(Calendar.SECOND, 0);
		// ======================== //Getting the Selected dated from URL
		LocalDate selectedDate = LocalDate.parse(request.getParameter("date"));
		LocalDate today = LocalDate.parse(request.getParameter("date"));
		System.out.println("today=" + today);
		System.out.println("selectedDate=" + selectedDate);
		// STORES SLOTS
		ArrayList<String> timeList = new ArrayList<>();
		if (selectedDate.equals(null)) {
			model.addAttribute("selectedDate", "");// Before selecting date
		} else {
			model.addAttribute("selectedDate", selectedDate);// After Selecting date
		}
		Calendar c = Calendar.getInstance();
		// System.out.println("+++++DF++++++" + df.format(cal.getTime())); /*String t =
		// timeStart.getHours() + ":" + timeStart.getMinutes();
		// System.out.println("=========T" + t);
		String n = (c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)).toString();// df.format(LocalTime.now()).toString();
		System.out.println("+++++++++N" + n); // Present calender for getting todays data
		Calendar calObj = Calendar.getInstance();
		// Gets listOfAppointmnetByDoctorId&Date
		List<Appointment> app = appointmentRepository.appointmentListByDoctorid(Integer.parseInt(doctorId),
				request.getParameter("date"));
		// For storing the booked slots as string
		ArrayList<String> totalSlots = new ArrayList<>();
		for (Appointment a : app) {
			totalSlots.add(a.getSlot());
		}
		// For Present Day Slots
		int amount = 30;
		//Array list For Storing the booked slots
		ArrayList<String> bookedSlots=new ArrayList<>();
		while (LocalDate.now().equals(selectedDate)) {
			System.out.println(df.format(cal.getTime()));
			if (cal.getTime().getHours() == endHour) {
				System.out.println("todayIf=" + today);
				break;
			} else {
				// if no Appointments are booked for present day
				if (app.size() == 0) {
					// Displaying slots for only current hour
					if (cal.getTime().getHours() == calObj.get(Calendar.HOUR_OF_DAY)) {
						if (cal.getTime().getMinutes() >= calObj.get(Calendar.MINUTE)) {
							timeList.add(df.format(cal.getTime()));
							model.addAttribute("times", timeList);
						}
					}
				}
				// if Appointments are booked for present day
				else {
					// For "Not Displaying" the slots booked
					if (totalSlots.contains(df.format(cal.getTime()).toString())) {
						//adding the booked slots
						bookedSlots.add(df.format(cal.getTime()).toString());
						System.out.println("app.contains(df.format(cal.getTime()).toString()=="
								+ totalSlots.contains(df.format(cal.getTime()).toString()));
						System.out
								.println("df.format(cal.getTime()).toString()==" + df.format(cal.getTime()).toString());
						cal.add(Calendar.MINUTE, amount);
						continue;
					}
					// For Displaying the slots not booked
					else {
						if (cal.getTime().getHours() == calObj.get(Calendar.HOUR_OF_DAY)) {
							if (cal.getTime().getMinutes() >= calObj.get(Calendar.MINUTE)) {
								timeList.add(df.format(cal.getTime()));
								model.addAttribute("times", timeList);
							}
						}
					}
				}
				System.out.println("cal.add(Calendar.MINUTE, 15)==" + df.format(cal.getTime()));
				// Displaying slots for after current hour

				if (cal.getTime().getHours() > calObj.get(Calendar.HOUR_OF_DAY)) {
					if (cal.getTime().getMinutes() >= calObj.get(Calendar.MINUTE))
						System.out.println("cal.getTime()==" + cal.getTime());
					timeList.add(df.format(cal.getTime()));
					model.addAttribute("times", timeList);
				}
				cal.add(Calendar.MINUTE, amount);
			}
		} // For Slots from next-day
		while (!LocalDate.now().equals(selectedDate)) {
			if (cal.getTime().getHours() == endHour) {
				today = today.minusDays(1);
				System.out.println("todayIf=" + today);
				break;
			} else {
				if (app.size() == 0) {
					timeList.add(df.format(cal.getTime()));
					cal.add(Calendar.MINUTE, amount);
					model.addAttribute("times", timeList);
				} else {
					if (totalSlots.contains(df.format(cal.getTime()).toString())) {
						System.out.println("cal.getTime()==" + cal.getTime());
						cal.add(Calendar.MINUTE, amount);
						continue;
					} else {
						timeList.add(df.format(cal.getTime()));
						cal.add(Calendar.MINUTE, amount);
						model.addAttribute("times", timeList);
					}
				}
			}
		}
		// Dates drop-down according to DoctorAvailability
		while (sd.isBefore(ed) || sd.equals(ed)) {
			datesInRange.add(sd);
			System.out.println("=======" + sd);
			String day = sd.format(DateTimeFormatter.ofPattern("EEE"));
			String month = sd.format(DateTimeFormatter.ofPattern("MMM"));
			String s = Integer.toString(sd.getDayOfMonth());
			if (sd.isAfter(LocalDate.now()) || sd.isEqual(LocalDate.now())) {
				days.add(day);
				slots.add(month);
				slots.add(s);
				String slot = month.toString() + s;
				monthdate.add(sd.toString());
				sd = sd.plusDays(1);
				System.out.println(sd);
				model.addAttribute("date", monthdate);
			} else {
				sd = sd.plusDays(1);
			}
		}
		ArrayList<String> totalslots=new ArrayList<>();
		if(bookedSlots.size()==0)
			model.addAttribute("bookedslots",0);
		else
			model.addAttribute("bookedslots",bookedSlots);
		totalslots.addAll(bookedSlots);
		totalslots.addAll(timeList);
		//model.addAttribute("times", timeList);
		model.addAttribute("totalslots", totalslots);
		/* model.addAttribute("map", map); */
		model.addAttribute("monthdate", monthdate);
		model.addAttribute("days", days);
		
		return "doctorsscreen";
	}

	@RequestMapping(value = "/selectedDate")
	public String selectedDoctors(Model model, HttpServletRequest request, Doctor docObj) {
		System.out.println("docId" + docObj.getDoctorId());
		LocalDate selectedDate = LocalDate.parse(request.getParameter("Date"));
		LocalDate today = LocalDate.parse(request.getParameter("Date"));
		System.out.println("today=" + today);
		System.out.println("selectedDate=" + selectedDate);
		ArrayList<String> timeList = new ArrayList<>();
		if (selectedDate.equals(null)) {
			model.addAttribute("selectedDate", "");
		} else {
			model.addAttribute("selectedDate", selectedDate);
		}
		/*
		 * while (today.equals(selectedDate)) {
		 * System.out.println(df.format(cal.getTime()));
		 * if(cal.getTime().getHours()==endHour) { timeList.remove(timeList.size()-1);
		 * today=today.minusDays(1); System.out.println("todayIf="+today); }else {
		 * cal.add(Calendar.MINUTE,10 ); timeList.add(df.format(cal.getTime())); } }
		 * for(String s:timeList) { System.out.println("sssss"+s); }
		 */
		return "doctorsscreen";
	}

	public Map<Integer, String> dropDownHsptlBranch() {
		log.info("===========DropdownMethod for Hbranch================");
		Map<Integer, String> HospitalBranchMap = new HashMap<Integer, String>();
		log.info("HosptlBranch list : " + HospitalBranchMap);
		List<HospitalBranch> hospitalBranchList = hospitalBranchService.getAllHospitalbranch();
		for (HospitalBranch Hsptlbrnchobj : hospitalBranchList) {
			HospitalBranchMap.put(Hsptlbrnchobj.getHospitalBranchId(), Hsptlbrnchobj.getHospitalBranchName());
		}
		return HospitalBranchMap;
	}

	public Map<Integer, String> dropDownServices() {
		log.info("============Dropdown for service=====");
		Map<Integer, String> ServieMap = new HashMap<Integer, String>();
		List<Services> serviceList = serServices.getAllServices();
		for (Services servicesobj : serviceList) {
			ServieMap.put(servicesobj.getServiceId(), servicesobj.getServiceName());
		}
		return ServieMap;
	}
	// for Id Crad

	@GetMapping("/docid/{id}")
	public String showId(Model model, @PathVariable("id") int doctorId) {
		Doctor doctorObj = doctorservice.GetDocotorById(doctorId);
		model.addAttribute("objdoctor", doctorObj);
		return "docidcard";
	}

	@GetMapping("/checkallappointments/{id}")
	public String allAppointments(Model model, @PathVariable("id") int doctorId) {
		Doctor doctorObj = doctorservice.GetDocotorById(doctorId);
		model.addAttribute("objdoctor", doctorObj);
		return "pappointmentlist";
	}

	@Autowired

	PrescriptionService prescriptionService;

	@GetMapping("/getallappointments/{id}")
	public String getAllAppointments(Model model, @PathVariable("id") int doctorId, Appointment appointment,
			Doctor docObj, String from, String to, HttpSession session) {
		docObj = doctorservice.GetDocotorById(doctorId);
		model.addAttribute("objdoctor", docObj);
		List<Appointment> appList = appointmentRepository.doctorAppointmentList(doctorId, from, to);
		List<Prescription> pres = prescriptionService.getAllPrescription();
		if (appList.size() == 0) {
			model.addAttribute("nodata", "No Appointments found!");
		}
		List<Appointment> appointmentList=new ArrayList<>();
		for(Appointment app:appList) {
			if(app.getIsactive() != 'N' && app.getIsactive() != 'n') {
				appointmentList.add(app);
			}
		}
		
		model.addAttribute("prescreptionlist", pres);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("prescreptionlist", pres);
		model.addAttribute("appList", appointmentList);
		model.addAttribute("doctorid", doctorId);
		Doctor doctor = doctorservice.GetDocotorById(doctorId);

		session.setAttribute("drname", doctor.getDoctorName());
		model.addAttribute("meetingLink", appointment.getLink());
		return "pappointmentlist";
	}

	@GetMapping("/Doctorlist/{pid}")
	public String doctrsForpatien(Model model, @PathVariable("pid") int pid) {
		model.addAttribute("doctorlist", doctorservice.getAllDoctor());
		List<Doctor> docList = doctorservice.getAllDoctor();
		model.addAttribute("doctorlist", docList);
		model.addAttribute("pid", pid);
		return "drlist";
	}
	
	
	
	@GetMapping("/BranchList/{did}")
	public String BranchListByDoctor(Model model,@PathVariable("did") String did) {
		
		List<Doctor> objDoc=doctorImplementation.doctorByBranch(did);
		
		model.addAttribute("doctoId", objDoc);
		
		return "hospitalbranchlist";
	}
	
	@GetMapping("/viewAllDoctors")
    public String viewHomePages(Model model) {
        List<Doctor> doctorlist = doctorservice.getAllDoctor();
        model.addAttribute("doctorlist", doctorlist);
        return "indexdoctors";
    }
	
	@GetMapping("/payment/{docId}")
	public String payments(Model model,@PathVariable("docId")int doctorId,Appointment objAppointment) {
		List<Appointment> appointment= appointmentService.getAllAppointment();
		List<Appointment> objApp =  appointmentRepository.appointmentListByDoctorid(doctorId);
		model.addAttribute("objectapp", objApp);
		List<Appointment> list=new ArrayList<>();
		for(Appointment app:appointment) {
			 System.out.println("hello this is a doctor id::"+app.getDoctor().getDoctorId());
			if(app.getDoctor().getDoctorId() ==doctorId) {
				list.add(app);
				
			}
		}
	int payment = doctorservice.GetDocotorById(doctorId).getDoctorFee(); 
	int total=	list.size() * payment;
	System.err.println("hello ::::::::::"+total);
	model.addAttribute("name", doctorservice.GetDocotorById(doctorId).getDoctorName());
	model.addAttribute("amount", total);	
		return "payment";
	}
	
	
	
	@GetMapping("/appointmentReschedule/{appid}")
    public String appointmentReschedule(Model model,@PathVariable("appid") int appid,HttpSession session) {
        Appointment appointment=appointmentService.getAppointmentById(appid);
        session.setAttribute("iddoc", appointment.getDoctor().getDoctorId());
        session.setAttribute("idpatient", appointment.getPatient().getPatientId());
        session.setAttribute("idhbranch", appointment.getHospitalBranchId());
        session.setAttribute("appid", appointment.getAppointmentId());
        session.setAttribute("reschedule","reschedule");
        return "redirect:/Viewdoctors/"+session.getAttribute("idhbranch")+"/"+session.getAttribute("idpatient")+"/"+session.getAttribute("iddoc");
    }
}
