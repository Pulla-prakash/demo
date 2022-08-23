package com.vcare.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vcare.beans.Aboutus;
import com.vcare.service.AboutusService;

@Controller
public class AboutusController {

	@Autowired

	AboutusService aboutusService;

	static Logger log = Logger.getLogger(AboutusController.class.getClass());

	@GetMapping("/about")
	public String getAllAbouts(Model model, Aboutus aboutus) {

		List<Aboutus> listAbout = aboutusService.getAllAbouts();

		List<Aboutus> list = new ArrayList<>();
		for (Aboutus about : listAbout) {
			if (about.getIsActive() == 'Y' || about.getIsActive() == 'y') {
				list.add(about);
			}
		}

		model.addAttribute("aboutlist", list);
		return "aboutlist";
	}

	@RequestMapping(value = ("/addabout"), method = RequestMethod.GET)
	public String newAbout(Model model, @ModelAttribute(value = "aboutObj") Aboutus aboutus) {
		log.info("department form ");
		model.addAttribute("departmentObj", aboutus);

		return "aboutform";
	}

	@RequestMapping(value = "/saveAbout", method = RequestMethod.POST)
	public String addDepartment(Model model, @ModelAttribute(value = "aboutObj") Aboutus aboutus) {
		aboutus.setIsActive('Y');
		log.info("inside deleteDepartment id:::" + aboutus.getId());
		log.info("inside deleteDepartment name:::" + aboutus.getHeader());
		log.info("inside deleteDepartment name:::" + aboutus.getDescription());
		model.addAttribute("aboutObj", aboutus);
		aboutusService.updateAboutus(aboutus);
		List<Aboutus> empList = aboutusService.getAllAbouts();
		model.addAttribute("aboutlist", empList);
		return "redirect:/about";
	}

	@GetMapping("/editAbout/{id}")
	public String getById(Model model, @PathVariable("id") int id) {

		Aboutus objSecAbout = aboutusService.getAboutusById(id);
		log.info("Edit of department in ...");
		log.info("inside getHospitalbranchId id is:::" + aboutusService.getAboutusById(id));
		model.addAttribute("aboutObj", objSecAbout);

		return "aboutform";

	}

	@GetMapping("/deleteAbout/{id}")
	public String deleteService(Model model, @PathVariable int id) {
		log.info("inside deleteHospitalBranch id:::" + id);

		Aboutus inactive = aboutusService.getAboutusById(id);
		inactive.setIsActive('N');
		aboutusService.updateAboutus(inactive);
		List<Aboutus> aboutList = aboutusService.getAllAbouts();
		model.addAttribute("aboutList", aboutList);

		return "redirect:/about";

	}

}
