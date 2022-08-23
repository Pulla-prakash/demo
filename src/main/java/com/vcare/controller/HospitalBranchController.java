package com.vcare.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.Hospital;
import com.vcare.beans.HospitalBranch;
import com.vcare.beans.News;
import com.vcare.beans.Patients;
import com.vcare.service.HospitalBranchService;
import com.vcare.service.HospitalService;
import com.vcare.service.NewsService;
import com.vcare.service.PatientsService;

@Controller
@RequestMapping("/hospitalBranch")
public class HospitalBranchController {

	@Autowired
	HospitalBranchService hospitalBranchService;
	@Autowired
	HospitalService hospitalService;
	@Autowired
	NewsService newsService;
	static Logger log = Logger.getLogger(HospitalBranchController.class.getClass());

	@GetMapping("/hospitalBranchList")
	public String getAllHospitals(Model model, HospitalBranch hospitalBranch,HttpServletRequest request) {
		log.info("inside getAllHospitals this will get the all hospitalsBranch:::");
		List<HospitalBranch> hospBranchList = hospitalBranchService.getAllHospitalbranch();
		List<HospitalBranch> list = new ArrayList<>();
		for (HospitalBranch objHos : hospBranchList) {
			if (objHos.getIsactive() == 'Y' || objHos.getIsactive() == 'y') {
				System.out.println("gdgdgdgdg"+objHos);
				list.add(objHos);
			}
		}
		HttpSession session=request.getSession();
		if(session.getAttribute("HUpdate")=="Hbranch") {
			model.addAttribute("Branch", session.getAttribute("HBName")+"\t HospitalBranch Added");
			session.setAttribute("Branch", "");
			session.setAttribute("HBName", "");
		}
		else if(session.getAttribute("HUpdate")=="HUpdate") {
            model.addAttribute("Branch", session.getAttribute("HName")+" \tUpdated");
            session.setAttribute("HUpdate", "");
            session.setAttribute("HName", "");
        }

		else if(session.getAttribute("Hdelete")=="Hdelete") {
            model.addAttribute("Branch", "\tSuccessfully deleted");
            session.setAttribute("Hdelete", " ");
        }
		log.info("inside getAllHospitals this will get the all hospitalsBranch:::");
		model.addAttribute("hospitalBranchList", list);
		return "hospitalbranchlist";
	}

	@GetMapping("/hospitalBranchList/{id}")
	public String getBranchHospital(Model model, @PathVariable("id") int hospitalId, HospitalBranch hospitalBranch) {
		log.info("inside getAllHospitals this will get the all hospitalsBranch:::");
		Hospital hospital = hospitalService.getHospitalById(hospitalId);
		List<HospitalBranch> HBranch = hospitalBranchService.getBranchList(hospital);
		for (HospitalBranch a : HBranch) {
			log.info(a.getHospitalBranchId());
			log.info(a.getHospitalBranchLocation());
			log.info(a.getHospitalBranchName());
			log.info(a.getHospitalBranchNumber());

		}
		model.addAttribute("hospitalBranchList", HBranch);
		return "hospitalbranchlist";
	}

	@Autowired
	PatientsService patientsService;

	// APPOINTMENT
	@GetMapping("/hospitalBranchList/{pid}/{id}")
	public String getBranchHospitals(Model model, @PathVariable("pid") int pid, @PathVariable("id") int hospitalId,
			HospitalBranch hospitalBranch) {
		log.info("inside getAllHospitals this will get the all hospitalsBranch:::");
		Hospital hospital = hospitalService.getHospitalById(hospitalId);
		List<HospitalBranch> HBranch = hospitalBranchService.getBranchList(hospital);

		Patients patient = patientsService.getPatientById(pid);
		model.addAttribute("pid", patient.getPatientId());

		for (HospitalBranch a : HBranch) {
			log.info(a.getHospitalBranchId());
			log.info(a.getHospitalBranchLocation());
			log.info(a.getHospitalBranchName());
			log.info(a.getHospitalBranchNumber());

		}
		model.addAttribute("hospitalBranchList", HBranch);
		return "phospitalbranchlist";
	}

	@RequestMapping(value = "/addHospitalBranch/{id}", method = RequestMethod.GET)
	public String newHospitalBranch(Model model, @PathVariable("id") int id, HospitalBranch hospitalBranch) {
		Hospital hospital = hospitalService.getAllHospital(id);

		model.addAttribute("Hname", hospital.getHospitalName());
		model.addAttribute("foreignkey", hospital.getHospitalId());
		model.addAttribute("hospitalBranchList", hospitalBranch);
		return "hospitalbranch";
	}

	@RequestMapping(value = "/saveHospitalBranch", method = RequestMethod.POST)
	public String addHospital(Model model, HospitalBranch hospitalBranch,@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {
		HttpSession session =request.getSession();
		if(hospitalBranch.getHospitalBranchId() != 0) {
            session.setAttribute("HUpdate", "HUpdate");
            session.setAttribute("HName", hospitalBranch.getHospitalBranchName());
        }
		if (hospitalBranch.getHospitalBranchId() == 0) {
			int Random = (int) (Math.random() * 90);
			hospitalBranch.setHospitalBranchId(Random);
			session.setAttribute("HUpdate", "Hbranch");
		}
		hospitalBranch.setIsactive('Y');
		hospitalBranch.setCreated(LocalDate.now());
		hospitalBranch.setHospitalBranchImage(Base64.getEncoder().encodeToString(file.getBytes()));
		System.out.println("Hospital Branch Id::::"+hospitalBranch.getHospitalBranchId());
		hospitalBranch.setIsactive('Y');
		
		session.setAttribute("HBName", hospitalBranch.getHospitalBranchName());
		
		hospitalBranchService.addHospitalbranch(hospitalBranch);
		model.addAttribute("hospitalBranchList", hospitalBranch);
		return "redirect:/hospitalBranch/hospitalBranchList";
	}

	@GetMapping("/editHospitalBranch/{id}")
	public String getHospitalById(Model model, @PathVariable("id") int HospitalbranchId,
			HospitalBranch hospitalBranch) {
		HospitalBranch objHospitalBranch = hospitalBranchService.getHospitalbranchId(HospitalbranchId);
		hospitalBranch.setUpdated(LocalDate.now());

		HospitalBranch hbranch = hospitalBranchService.getHospitalbranchId(HospitalbranchId);
		int a = hbranch.getHospitals().getHospitalId();
		model.addAttribute("foreignkey", a);
		model.addAttribute("Hname", hbranch.getHospitals().getHospitalName());
		model.addAttribute("branchname", hbranch.getHospitalBranchName());
		log.info("inside getHospitalbranchId id is:::" + objHospitalBranch.getHospitalBranchId());
		model.addAttribute("hospitalBranchList", objHospitalBranch);

		return "hospitalbranch";

	}

	@GetMapping("/deleteHospitalBranch/{id}")
	public String deleteHospitalBranch(Model model, @PathVariable int id,HttpServletRequest request) {
		log.info("inside deleteHospitalBranch id:::" + id);
		HospitalBranch inactive = hospitalBranchService.getHospitalbranchId(id);
		inactive.setIsactive('N');
		HttpSession session=request.getSession();
        session.setAttribute("Hdelete", "Hdelete");
		hospitalBranchService.updateHospitalbranch(inactive);
		List<HospitalBranch> hospBranchList = hospitalBranchService.getAllHospitalbranch();
		model.addAttribute("hospitalBranchList", hospBranchList);
		return "redirect:/hospitalBranch/hospitalBranchList";

	}
	
	@GetMapping("/hospitalPage/{id}")
	public String hospitalPage(Model model,@PathVariable("id") int HospitalbranchId){
	List<HospitalBranch> hospBranchList = hospitalBranchService.getAllHospitalbranch();
	model.addAttribute("hospitalBranchList", hospBranchList);
	StringBuffer obj = new StringBuffer();
	int i=1;
	for (HospitalBranch a : hospBranchList) { obj.append("[\"" +a.getHospitalBranchName()+ "\"," + a.getLatitude() + "," + a.getLongitude() + "," + i + "],");
	i++;
	}
	model.addAttribute("branches", obj.toString());
	List<News> newslists = newsService.getLatestNews();
	model.addAttribute("newsLists", newslists);
	HospitalBranch objHospitalBranch = hospitalBranchService.getHospitalbranchId(HospitalbranchId);
	model.addAttribute("objHospitalBranch", objHospitalBranch); 
	return "hospitalpage";
	}


}
