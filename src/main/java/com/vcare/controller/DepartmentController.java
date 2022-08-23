package com.vcare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.vcare.beans.Department;
import com.vcare.beans.HospitalBranch;
import com.vcare.service.DepartmentService;
import com.vcare.service.DepartmentServiceImpl;
import com.vcare.service.HospitalBranchService;

@Controller
@RequestMapping("/")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	@Autowired
	DepartmentServiceImpl departmentServiceImpl;
	@Autowired
	HospitalBranchService hospitalBranchService;

	static Logger log = Logger.getLogger(DepartmentController.class.getClass());

	@GetMapping("/Dephome/{bid}")
	public String getAllDepartments(Model model, @PathVariable("bid") int branchId, Department objDepart,HttpServletRequest request) {

		// List<Department> depList = departmentService.getAllDepartments();
		List<Department> depList = departmentServiceImpl.departmentByBranch(branchId);
		log.info("list of all departments");
		List<Department> list = new ArrayList<>();
		for (Department dep : depList) {
			if (dep.getIsactive() == 'Y' || dep.getIsactive() == 'y') {
				list.add(dep);
			}
		}
		
		HttpSession session=request.getSession();
		if(session.getAttribute("Depadd")=="Depadd") {
			model.addAttribute("adminmsg",session.getAttribute("Depname")+ "\t Department \t Added");
			session.setAttribute("Depadd", "");
			session.setAttribute("Depname", "");
		}
		
		if(session.getAttribute("Depup")=="Depup") {
			model.addAttribute("adminmsg",session.getAttribute("Depname")+"\t Department \tUpdated" );
			session.setAttribute("Depup", "");
			session.setAttribute("Depname", "");
			
		}
		if(session.getAttribute("Depdelete")=="Depdelete") {
			model.addAttribute("adminmsg", "\t Successfully deleted");
		}
		model.addAttribute("departmentList", list);
		HospitalBranch objBranch = hospitalBranchService.getHospitalbranchId(branchId);
		model.addAttribute("branch", objBranch.getHospitalBranchId());
		model.addAttribute("objName", objDepart.getDepartmentName());
		model.addAttribute("objId", objDepart.getDepartmentId());
		return "departmentlist";
	}

	@RequestMapping(value = "/addDep", method = RequestMethod.GET)
	public String newEmployee(Model model, @ModelAttribute(value = "departmentObj") Department objDepartment) {
		log.info("department form ");
		model.addAttribute("departmentObj", objDepartment);
		Map<Integer, String> BranchMap = dropDownBranch();
		model.addAttribute("BranchMap", BranchMap);
		model.addAttribute("branchId", BranchMap.keySet());
		model.addAttribute("branch", hospitalBranchService.getAllHospitalbranch());
		return "departmentform";
	}

	@RequestMapping(value = "/saveDep", method = RequestMethod.POST)
	public String addDepartment(Model model, @ModelAttribute(value = "departmentObj") Department objDepartment,HttpServletRequest request) {
		objDepartment.setIsactive('y');
		log.info("inside deleteDepartment id:::" + objDepartment.getDepartmentId());
		log.info("inside deleteDepartment name:::" + objDepartment.getDepartmentName());
		log.info("inside deleteDepartment name:::" + objDepartment.getDescription());
		model.addAttribute("departmentObj", objDepartment);
		departmentService.updateDepartment(objDepartment);
		List<Department> empList = departmentService.getAllDepartments();
		HttpSession session=request.getSession();
		session.setAttribute("Depadd", "Depadd");
		session.setAttribute("Depname", objDepartment.getDepartmentName());
		if(objDepartment.getDepartmentId()!=0) {
			session.setAttribute("Depup", "Depup");
			session.setAttribute("Depname", objDepartment.getDepartmentName());
		}
		model.addAttribute("departmentList", empList);
		return "redirect:/Dephome/" + objDepartment.getHospitalBranch().getHospitalBranchId();
	}

	@GetMapping("/editDepartment/{id}")
	public String getById(Model model, @PathVariable("id") int departmentId) {

		Department objSecDepartment = departmentService.getDepartmentById(departmentId);
		log.info("Edit of department in ...");
		log.info("inside getHospitalbranchId id is:::" + departmentService.getDepartmentById(departmentId));
		model.addAttribute("departmentObj", objSecDepartment);
		Map<Integer, String> BranchMap = dropDownBranch();
		model.addAttribute("BranchMap", BranchMap);
		model.addAttribute("branchId", BranchMap.keySet());
		model.addAttribute("branch", hospitalBranchService.getAllHospitalbranch());
		return "departmentform";
	}
	@GetMapping("/deleteDepartment/{id}")
	public String deleteService(Model model, @PathVariable int id,HttpServletRequest request) {
		log.info("inside deleteHospitalBranch id:::" + id);
		Department inactive = departmentService.getDepartmentById(id);
		inactive.setIsactive('N');
		departmentService.updateDepartment(inactive);
		List<Department> departmentList = departmentService.getAllDepartments();
		model.addAttribute("serviceList", departmentList);
		HttpSession session=request.getSession();
		session.setAttribute("Depdelete", "Depdelete");
		return "redirect:/Dephome/"+inactive.getHospitalBranch().getHospitalBranchId();

	}
	public Map<Integer, String> dropDownBranch() {
		Map<Integer, String> BranchMap = new HashMap<Integer, String>();
		List<HospitalBranch> branchList = hospitalBranchService.getAllHospitalbranch();
		for (HospitalBranch branchobj : branchList) {
			BranchMap.put(branchobj.getHospitalBranchId(), branchobj.getHospitalBranchName());
		}
		return BranchMap;
	}
}
