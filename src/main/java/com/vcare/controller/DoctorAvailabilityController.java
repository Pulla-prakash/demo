
package com.vcare.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vcare.beans.Doctor;
import com.vcare.beans.DoctorAvailability;
import com.vcare.beans.HospitalBranch;
import com.vcare.repository.DoctorAvailabilityRepository;
import com.vcare.service.DoctorAvailabilityService;
import com.vcare.service.DoctorService;
import com.vcare.service.HospitalBranchService;

@Controller
@RequestMapping("/")
public class DoctorAvailabilityController {
	@Autowired
	DoctorAvailabilityService doctorAvailabilityService;
	@Autowired
	DoctorService docService;
	
	@Autowired
	DoctorAvailabilityRepository availabilityRepository;
	
	@Autowired
	HospitalBranchService hospitalBranchService;

	@GetMapping(value = "/availabilityList/{id}")
    public String getAllDoctorAvailability(Model model,@PathVariable("id") int doctorid) {

        //List<DoctorAvailability> availabilityList = doctorAvailabilityService.getAllDoctorAvailability();
        List<DoctorAvailability> availabilityList = availabilityRepository.availableDoctor(doctorid);

        //model.addAttribute("id", availabilityList.get(0).getDoctor().getDoctorId());
        model.addAttribute("availabilityList", availabilityList);
        model.addAttribute("doctorid", doctorid);
        return "availabilitylist";
    }

	@RequestMapping(value = "/addAvailability/{did}", method = RequestMethod.GET)
    public String newAvailability(Model model, @PathVariable("did") int id,HttpSession session, DoctorAvailability doctorAvailability) {

        Doctor doc = docService.GetDocotorById(id);
        List<HospitalBranch> branchList = new ArrayList<>();
        List<HospitalBranch> hbList = hospitalBranchService.getAllHospitalbranch();
        String dbId = doc.getHospitalBranchId();
        if (dbId == null) {
            session.setAttribute("drname",doc.getDoctorName());
            model.addAttribute("nodocbranches","You are not working in any Hospital, please update your profile    ");
            model.addAttribute("objdoctor", doc);
            return "dashboard";
        }
        else {
            // String[] arrOfStr=new String[hbList.size()];
            String[] arrOfStr = dbId.split(",");
            if (arrOfStr.length == 0) {
                HospitalBranch hd = hospitalBranchService.getHospitalbranchId(Integer.parseInt(dbId));
                branchList.add(hd);
            } else {
                for (String a : arrOfStr) {
                    for (HospitalBranch hb : hbList) {
                        if (hb.getHospitalBranchId() == Integer.parseInt(a)) {
                            branchList.add(hb);
                        }
                    }
                }
            }
        }
        model.addAttribute("doctorAvailability", doctorAvailability);
        model.addAttribute("branchList", branchList);
        model.addAttribute("doc", doc.getDoctorId());
        System.out.println("wertyuiopsdfghjk" + doc);
        model.addAttribute("id", id);
        return "doctoravailability";
    }

	@RequestMapping(value = "/saveAvailability/{id}", method = RequestMethod.POST)
    public String addDoctorAvailability(Model model, @PathVariable("id") int id, DoctorAvailability doctorAvailability,
            HttpSession session, @RequestParam(name = "hospitalBranch", required = false) int hospitalBranchId) {
        Doctor doc = docService.GetDocotorById(id);
        model.addAttribute("availabilityList", doctorAvailability);
        model.addAttribute("doc", doc.getDoctorName());
        model.addAttribute("branches", 1);
        // Ajith
        List<DoctorAvailability> docAvailList = doctorAvailabilityService.getAllDoctorAvailability();
        System.out.println("docAvailList.size()=" + docAvailList.size());
        if (docAvailList.size() == 0) {
            doctorAvailabilityService.addDoctorAvailability(doctorAvailability);
            return "redirect:/availabilityList/" + doc.getDoctorId();
        }
        ArrayList<Integer> a = new ArrayList<>();
        for (DoctorAvailability docavail : docAvailList) {
            a.add(docavail.getDoctor().getDoctorId());
            a.add(docavail.getHospitalBranch().getHospitalBranchId());
        }
        if (doctorAvailability.getAvailabilityId() != 0) {
            doctorAvailabilityService.addDoctorAvailability(doctorAvailability);
            session.setAttribute("docAvailExists", "Updated Sucessfully");
        } else {
            if (a.contains(hospitalBranchId) && a.contains(doctorAvailability.getDoctor().getDoctorId())) {
                HospitalBranch hb = hospitalBranchService.getHospitalbranchId(hospitalBranchId);
                session.setAttribute("docAvailExists",
                        "Record already exists in " + hb.getHospitalBranchName() + ", please update!");
            } else {
                doctorAvailabilityService.addDoctorAvailability(doctorAvailability);
                session.setAttribute("docAvailExists", "");
            }
        }
        // Ajith end
        model.addAttribute("branchId", doc.getHospitalBranchId());
        return "redirect:/availabilityList/" + doc.getDoctorId();
    }
	@GetMapping("/editAvailability/{availid}")
	public String getAvailabilityById(Model model, @PathVariable("availid") int availId,DoctorAvailability doctorAvailability) {
		doctorAvailability=doctorAvailabilityService.getDoctorAvailabilityId(availId);
		int docId=doctorAvailability.getDoctor().getDoctorId();
		doctorAvailability.getDoctor().getHospitalBranchId();
		
		List<HospitalBranch >branchList=new  ArrayList<>();
		List<HospitalBranch > hbList=hospitalBranchService.getAllHospitalbranch();
		Doctor doc=docService.GetDocotorById(docId);
		
		String dbId=doc.getHospitalBranchId();
		String[] arrOfStr = dbId.split(",");
		System.out.println("arrOfStr="+arrOfStr);
		for (String a : arrOfStr)
				for(HospitalBranch hb:hbList) {
			if(hb.getHospitalBranchId()==Integer.parseInt(a)) {
				branchList.add(hb);
			}
		}
		
		 doctorAvailability.setStartTimings(doctorAvailability.getStartTimings());
		 doctorAvailability.setEndTimings(doctorAvailability.getEndTimings());
		model.addAttribute("branchList", branchList);
		model.addAttribute("doctorAvailability", doctorAvailability);
//		model.addAttribute("doc", doc.getDoctorId());
//		System.out.println("wertyuiopsdfghjk"+doc);
		model.addAttribute("id", docId);
		return "doctoravailability";
	}

	@GetMapping("/deleteAvailability/{id}")
	public String deleteAvailability(Model model, @PathVariable int id) {
		doctorAvailabilityService.deleteEmployeeById(id);
		List<DoctorAvailability> availabilityList = doctorAvailabilityService.getAllDoctorAvailability();
		model.addAttribute("availabilityList", availabilityList);
		return "availabilitylist";
	}
}
