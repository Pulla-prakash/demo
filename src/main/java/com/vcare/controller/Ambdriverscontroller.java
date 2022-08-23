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
import org.springframework.web.bind.annotation.PostMapping;

import com.vcare.beans.Ambulance;
import com.vcare.beans.AmbulanceDriverAssosiation;
import com.vcare.beans.Department;
import com.vcare.beans.Employees;
import com.vcare.beans.HospitalBranch;
import com.vcare.repository.Ambdriversrepository;
import com.vcare.repository.AmbulanceRepository;
import com.vcare.repository.DepartmentRepository;
import com.vcare.repository.EmployeesRepository;
import com.vcare.service.AmbulanceDriverService;
import com.vcare.service.AmbulanceService;
import com.vcare.service.EmployeesService;
import com.vcare.service.HospitalBranchService;

@Controller
public class Ambdriverscontroller {

	@Autowired
	AmbulanceDriverService ambulanceDriverService;
	@Autowired
	AmbulanceService ambulanceservice;
	@Autowired
	EmployeesService employeeService;
	@Autowired
	Ambdriversrepository ambdriversrepository;

	@Autowired
	HospitalBranchService hospitalBranchService;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	EmployeesRepository employeesRepository;
	@Autowired
	AmbulanceRepository ambulancerepo;

	@GetMapping("/AmbulanceDriverList/{brnchId}")
	public String getAllAmbdriver(Model model, @PathVariable("brnchId") String brnchId, HttpServletRequest request) {

		List<AmbulanceDriverAssosiation> ambdriverlist = new ArrayList<>();
		List<AmbulanceDriverAssosiation> AmbdriverList = ambdriversrepository.ambdriverList(Integer.parseInt(brnchId));
		for (AmbulanceDriverAssosiation ADA : AmbdriverList) {
			if (ADA.getIsactive() == 'Y' || ADA.getIsactive() == 'y') {
				ambdriverlist.add(ADA);
			}
		}
		HttpSession session = request.getSession();
		if (session.getAttribute("Driadd") == "Driadd") {
			model.addAttribute("adminmsg", session.getAttribute("Driname") + "\tAmbulance \t Added");
			session.setAttribute("Driadd", "");
		}
		if (session.getAttribute("Driup") == "Driup") {
			model.addAttribute("adminmsg", session.getAttribute("Driname") + "\t Ambulance \t updated");
			session.setAttribute("Driup", "");
			session.setAttribute("Driname", "");
		}
		if (session.getAttribute("Dridelete") == "Dridelete") {
			model.addAttribute("adminmsg", "\t Successfully deleted");
			session.setAttribute("Dridelete", "");
		}
		model.addAttribute("ambdriverlist", ambdriverlist);

		return "ambulancedriverlist";
	}

	@GetMapping("/AddAmbdriver/{HbranchId}")
	public String showNewdoctorsForm(Model model, AmbulanceDriverAssosiation ambulancedriverobj,
			@PathVariable("HbranchId") int HbranchId, HospitalBranch hospitalBranch, HttpServletRequest request) {
		HospitalBranch hospitalbranch = hospitalBranchService.getHospitalbranchId(HbranchId);
		System.out.println("fhjefbwegjhw" + HbranchId);
		model.addAttribute("HbranchId", hospitalbranch.getHospitalBranchId());
		System.out.println("branchid::::::::::::::::" + hospitalBranch.getHospitalBranchEmailId());
		System.out.println("this method is implemented");
		List<Ambulance> AmbList = ambulancerepo.getambbyBranchList(HbranchId);
		model.addAttribute("ambulanceList", AmbList);
		Department deptObj = departmentRepository.dept(HbranchId);

		List<Employees> empdriverlist = employeesRepository.empDriverList(deptObj.getDepartmentId());
		model.addAttribute("empdriverlist", empdriverlist);
		return "ambdriverform";
	}

	@PostMapping("/saveAmbdriver")
	public String saveAmbDriver(Model model, AmbulanceDriverAssosiation ambulancedriverobj,
			HospitalBranch hospitalBranch, HttpServletRequest request) {
		System.out.println("helooooooo");
		String objambdriver = ambulanceDriverService.validateduplicate(ambulancedriverobj.getAmbulancetype());
		ambulancedriverobj.setIsactive('y');
		ambulancedriverobj.setStatus("available");
		System.out.println("save method2");
		HttpSession session = request.getSession();

		session.setAttribute("Driname", ambulancedriverobj.getAmbulancetype());
		session.setAttribute("brnchid", ambulancedriverobj.getHospitalbranch().getHospitalBranchId());
		if (ambulancedriverobj.getAmbdriversId() != 0) {
			session.setAttribute("Driup", "Driup");
			session.setAttribute("Driname", ambulancedriverobj.getAmbulancetype());

		} else {
			session.setAttribute("Driadd", "Driadd");
		}

		ambulanceDriverService.addAmbulancedriver(ambulancedriverobj);
		return "redirect:/AmbulanceDriverList/" + ambulancedriverobj.getHospitalbranch().getHospitalBranchId();
	}

	@GetMapping("/deleteAmbdriver/{ambdriversId}")
	public String deleteHospitalBranch(Model model, @PathVariable("ambdriversId") int ambdriversId,
			HttpServletRequest request) {
		AmbulanceDriverAssosiation inactive = ambulanceDriverService.getAmbdriverId(ambdriversId);
		inactive.setIsactive('N');
		HttpSession session = request.getSession();
		session.setAttribute("Dridelete", "Dridelete");
		ambulanceDriverService.addAmbulancedriver(inactive);
		return "redirect:/AmbulanceDriverList/" + inactive.getHospitalbranch().getHospitalBranchId();
	}

	@GetMapping("/editAmbulancedriver/{ambdriversId}")
	public String getById(Model model, @PathVariable("ambdriversId") int ambdriversId) {

		AmbulanceDriverAssosiation ambulancedriverob = ambulanceDriverService.getById(ambdriversId);
		model.addAttribute("ambulancedriverobj", ambulancedriverob);
		int brnchid = ambulancedriverob.getHospitalbranch().getHospitalBranchId();
		HospitalBranch hospitalbranch = hospitalBranchService.getHospitalbranchId(brnchid);
		System.out.println("fhjefbwegjhw" + brnchid);
		model.addAttribute("HbranchId", hospitalbranch.getHospitalBranchId());
		System.out.println("this method is implemented");
		List<Ambulance> AmbList = ambulancerepo.getambbyBranchList(brnchid);
		model.addAttribute("ambulanceList", AmbList);
		Department deptObj = departmentRepository.dept(brnchid);

		List<Employees> empdriverlist = employeesRepository.empDriverList(deptObj.getDepartmentId());
		model.addAttribute("empdriverlist", empdriverlist);
		return "ambdriverform";
	}

	@GetMapping("/selectambulancedriver/{hbranchId}/{empid}")
	public String getbyid(Model model, AmbulanceDriverAssosiation ambulancedriverob,
			@PathVariable("hbranchId") String hbranchId, @PathVariable("empid") int empid) {
		List<AmbulanceDriverAssosiation> AmbdriverList = ambdriversrepository
				.ambdriverList(Integer.parseInt(hbranchId));
		model.addAttribute("ambdriverlist", AmbdriverList);
		model.addAttribute("hbranchId", hbranchId);
		model.addAttribute("employee", empid);
		return "ambulanceselection";

	}

	@GetMapping("/changestatus/{id}/{ambdriversId}")
	public String statusById(Model model, @PathVariable("ambdriversId") int ambdriversId, @PathVariable("id") int id) {
		AmbulanceDriverAssosiation ambulancedriverob = ambulanceDriverService.getById(ambdriversId);
		HospitalBranch hospitalbranch = hospitalBranchService.getHospitalbranchId(id);
		model.addAttribute("hbranchId", hospitalbranch.getHospitalBranchId());
		ambulancedriverob.setStatus("available");
		ambulanceDriverService.addAmbulancedriver(ambulancedriverob);
		model.addAttribute("HbranchId", hospitalbranch.getHospitalBranchId());
		List<AmbulanceDriverAssosiation> ambdriverlist = ambulanceDriverService.getAllAambulancedriver();
		model.addAttribute("ambdriverlist", ambdriverlist);
		return "ambulanceselection";

	}

}
