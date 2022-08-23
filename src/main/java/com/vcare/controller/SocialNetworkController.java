package com.vcare.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.SocialNetworks;
import com.vcare.service.SocialNetworkService;

@Controller
public class SocialNetworkController {

	@Autowired

	SocialNetworkService socialNetworkService;

	static Logger log = Logger.getLogger(SocialNetworkController.class.getClass());

	@GetMapping("/network")
	public String getAllnetworks(Model model, SocialNetworks socialNetwork,HttpServletRequest request) {

		List<SocialNetworks> networkList = socialNetworkService.getAllNetworks();
		log.info("list of all departments");

		List<SocialNetworks> list = new ArrayList<>();
		for (SocialNetworks network : networkList) {
			if (network.getIsActive() == 'Y' || network.getIsActive() == 'y') {
				list.add(network);
			}
		}
		HttpSession session=request.getSession();
		if(session.getAttribute("netadd")=="netadd") {
			model.addAttribute("adminmsg",session.getAttribute("netname")+"\t Added" );
			session.setAttribute("netadd", "");
			session.setAttribute("netname", "");
		}
		if(session.getAttribute("netUp")=="netUp") {
			model.addAttribute("adminmsg", session.getAttribute("netname")+"\t Updated");
			session.setAttribute("netUp", "");
			session.setAttribute("netname", "");
		}
		if(session.getAttribute("netdelete")=="netdelete") {
			model.addAttribute("adminmsg", "Successfully deleted");
			session.setAttribute("netdelete", "");
		}
		model.addAttribute("networkList", list);
		model.addAttribute("objName", socialNetwork.getNetworkName());
		return "networklist";
	}

	@RequestMapping(value = "/addnetwork", method = RequestMethod.GET)
	public String newnetwork(Model model, @ModelAttribute(value = "networkObj") SocialNetworks socialNetwork) {
		log.info("network form ");
		model.addAttribute("networkObj", socialNetwork);

		return "networkform";
	}

	@RequestMapping(value = "/savenetwork", method = RequestMethod.POST)
	public String addNetwork(Model model, @RequestParam("file") MultipartFile file,
			@ModelAttribute(value = "networkObj") SocialNetworks socialNetwork,HttpServletRequest request) throws IOException {
		socialNetwork.setIsActive('Y');
		log.info("inside deleteDepartment id:::" + socialNetwork.getNetworkId());
		log.info("inside deleteDepartment name:::" + socialNetwork.getNetworkName());
if(file.getOriginalFilename()=="") {
	socialNetwork.setIcon(socialNetwork.getIcon());
}else {
		socialNetwork.setIcon(Base64.getEncoder().encodeToString(file.getBytes()));
}
		model.addAttribute("networkObj", socialNetwork);
		socialNetworkService.addService(file, socialNetwork);
		List<SocialNetworks> networkList = socialNetworkService.getAllNetworks();
		model.addAttribute("networkList", networkList);
		HttpSession session=request.getSession();
		
		session.setAttribute("netname", socialNetwork.getNetworkName());
		if(socialNetwork.getNetworkId()!=0) {
			session.setAttribute("netUp", "netUp");
			session.setAttribute("netname",socialNetwork.getNetworkName());
		}else {
			session.setAttribute("netadd", "netadd");
		}
		return "redirect:/network";
	}

	@GetMapping("/editNetwork/{id}")
	public String getById(Model model, @PathVariable("id") int networkId) {

		SocialNetworks objSecNetwork = socialNetworkService.getNetworkById(networkId);
		log.info("Edit of department in ...");
		log.info("inside getHospitalbranchId id is:::" + socialNetworkService.getNetworkById(networkId));
		model.addAttribute("networkObj", objSecNetwork);

		return "networkform";

	}

	@GetMapping("/deleteNetwork/{id}")
	public String deleteNetwork(Model model, @PathVariable("id") int networkId,HttpServletRequest request) {
		log.info("inside deleteHospitalBranch id:::" + networkId);

		SocialNetworks inactive = socialNetworkService.getNetworkById(networkId);
		inactive.setIsActive('N');
		socialNetworkService.updateNetwork(inactive);
		List<SocialNetworks> networkList = socialNetworkService.getAllNetworks();
		model.addAttribute("networkList", networkList);
		HttpSession session=request.getSession();
		session.setAttribute("netdelete", "netdelete");
		return "redirect:/network";

	}

}
