package com.vcare.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vcare.beans.Ambulance;
import com.vcare.beans.HospitalBranch;
import com.vcare.service.AmbulanceImplementation;
import com.vcare.service.AmbulanceService;
import com.vcare.service.HospitalBranchService;

@Controller
public class AmbulanceController {

	@Autowired
	AmbulanceService ambulaceservice;
	@Autowired
	HospitalBranchService hospitalBranchService;
 @Autowired
 AmbulanceImplementation  ambulanceImplementation;
	
	@GetMapping("/AmbulanceList/{hbid}")
	public String getAllAmbulance(Model model,@PathVariable("hbid") int hbid,HttpServletRequest request) {
		List<Ambulance> AmbList=new ArrayList<>();
		List<Ambulance> List = ambulanceImplementation.ambulanceByBranch(hbid);
		for (Ambulance Amb : List) {
			if (Amb.getIsActive() == 'Y' || Amb.getIsActive() == 'y') {
				AmbList.add(Amb);
			}
		}
		HttpSession session=request.getSession();
		if(session.getAttribute("Ambadd")=="Ambadd") {
			model.addAttribute("adminmsg",session.getAttribute("Ambname")+"\t Ambulance\t Added");
			session.setAttribute("Ambadd", "");
			session.setAttribute("Anbname","");
		}
		if(session.getAttribute("Ambup")=="Ambup") {
			model.addAttribute("adminmsg", session.getAttribute("Ambname")+"\t Ambulance \t Updated");
			session.setAttribute("Ambup", "");
			session.setAttribute("Anbname","");
			
		}
		if(session.getAttribute("Ambdelete")=="Ambdelete") {
			model.addAttribute("adminmsg","Ambulance\t Successfully\t Deleted");
			session.setAttribute("Ambdelete", "");
		}
		model.addAttribute("hbid",hbid);
		model.addAttribute("ambulanceList", AmbList);
		return "ambulanceList";
	}

	@GetMapping("/addAmbulance/{branchId}")
	public String showNewForm(Model model, Ambulance ambulance, @PathVariable("branchId") int branchId) {
		HospitalBranch hospitalbranch = hospitalBranchService.getHospitalbranchId(branchId);
		model.addAttribute("branchId", hospitalbranch.getHospitalBranchId());
		model.addAttribute("ambulanceobj", ambulance);
		return "ambulanceForm";
	}

	@RequestMapping(value = "/saveAmbulance/{hbrnchId}", method = RequestMethod.POST)
	public String addAmbulance(Model model, Ambulance ambulance,@PathVariable("hbrnchId")int hbrnchId,HttpServletRequest request) {
		ambulance.setIsActive('Y');
		model.addAttribute("ambulanceobj", ambulance);
		model.addAttribute("branchId",hbrnchId);
		
		ambulaceservice.addAmbulance(ambulance);
		HttpSession session =request.getSession();
		
		session.setAttribute("Ambname", ambulance.getAmbulanceType());
		
		if(ambulance.getAmbulanceId()!=0) {
			session.setAttribute("Ambup", "Ambup");
			session.setAttribute("Ambname", ambulance.getAmbulanceType());
		}else {
			session.setAttribute("Ambadd", "Ambadd");
		}
		return "redirect:/AmbulanceList/"+ ambulance.getHospitalbranch().getHospitalBranchId();
	}

	@GetMapping("/deleteAmbulance/{id}")
	public String deleteService(Model model, @PathVariable("id") int ambulanceId,HttpServletRequest request) {
		Ambulance as=ambulaceservice.getById(ambulanceId);
		as.setIsActive('N');
		ambulaceservice.UpdateAmbulance(as);
		List<Ambulance> AmbList = ambulaceservice.getAllAmbulance();
		model.addAttribute("ambulanceList", AmbList);
		HttpSession session=request.getSession();
		session.setAttribute("Ambdelete", "Ambdelete");
		return "redirect:/AmbulanceList/"+as.getHospitalbranch().getHospitalBranchId();
	}

	@GetMapping("/editAmbulance/{id}")
	public String getById(Model model, @PathVariable("id") int ambulanceId) {
		Ambulance ambulance = ambulaceservice.getById(ambulanceId);
		model.addAttribute("ambulanceobj", ambulance);
		System.out.println("inside getHospitalbranchId id is:::" + ambulaceservice.getById(ambulanceId));
		model.addAttribute("branchId", ambulance.getHospitalbranch().getHospitalBranchId());
		ambulaceservice.UpdateAmbulance(ambulance);
		// model.addAttribute("employeeObj", objSecEmployee);
		return "ambulanceForm";
	}

}
