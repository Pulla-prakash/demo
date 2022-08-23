
package com.vcare.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.MedicalHistory;
import com.vcare.beans.Patients;
import com.vcare.repository.MedicalHistoryRepository;
import com.vcare.service.MedicalHistoryService;
import com.vcare.service.PatientsService;

@Controller
@RequestMapping("/med")
public class MedicalHistoryController {

	@Autowired
	MedicalHistoryRepository repo;
	@Autowired
	MedicalHistoryService medicalHistoryService;

	@Autowired
	PatientsService patientsService;

	static Logger log = Logger.getLogger(MedicalHistoryController.class.getClass());

	@GetMapping("/medhistory/{id}")
	public String medHistory(Model model, @PathVariable("id") int id, MedicalHistory medObj) {
		log.info(id);

		Patients patient = patientsService.getPatientById(id);
		model.addAttribute("patientObj", patient.getPatientId());

		model.addAttribute("medObj", medObj);
		log.info(id);

		return "medicalhistory";
	}

	@RequestMapping(value = "/uploadImage/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String fileUpload(@RequestParam("image") MultipartFile[] file, HttpServletRequest request, Model model,
			@PathVariable(value = "id") int id, MedicalHistory medObj) throws IOException {
		HttpSession session=request.getSession();
		Patients patient = patientsService.getPatientById(id);
		Integer a = patient.getPatientId();
		model.addAttribute("patientObj", a);
		log.info(patient);
		StringBuilder filejoin = new StringBuilder();
		String UploadDir = "C:\\Users\\Lenovo\\Documents\\workspace-spring-tool-suite-4-4.14.0.RELEASE\\Vcare\\src\\main\\resources\\static\\uploads\\"
				+ id + "\\";

		for (MultipartFile f : file) {
			filejoin.append(f.getOriginalFilename() + ",");
			String fileName = StringUtils.cleanPath(f.getOriginalFilename());
			Path uploadPath = Paths.get(UploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputstream = f.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputstream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				throw new IOException("Could not save image file: " + fileName, ioe);
			}
			medObj.setHistoryPath("/../uploads/" + "/" + a + "/" + f.getOriginalFilename());
			medObj.setImageName(f.getOriginalFilename());
			model.addAttribute("medObj", medObj);
		}
		MedicalHistory savedUser = repo.save(medObj);
		log.info("savedUser" + savedUser);
		session.setAttribute("medhistory","");
		session.setAttribute("upload","upload");
		return "redirect:/med/view/{id}";
	}

	@RequestMapping("/view/{id}")
	public String view(Model model, @PathVariable("id") int id, MedicalHistory medObj,HttpServletRequest request) {
		log.info(id);
		Patients patient = patientsService.getPatientById(id);
		model.addAttribute("patientObj", patient.getPatientId());
		List<MedicalHistory> medicalHistoryList = medicalHistoryService.getMedicalHistory(patient);
		model.addAttribute("medicalHistoryList", medicalHistoryList);
		medObj = medicalHistoryService.getMedicalById(id);
		HttpSession session=request.getSession();
		System.out.println("request.getSession().toString()"+session.getAttribute("medhistory"));
		if(session.getAttribute("medhistory")=="delete") {
		model.addAttribute("patientmsg","File deleted successfully");
		session.setAttribute("medhistory","");
		}
		else if(session.getAttribute("upload")=="upload"){
			model.addAttribute("patientmsg","File uploaded successfully");
			session.setAttribute("upload","");
		}
		return "viewmedicalhistory";
	}

	@GetMapping("/getimage/{id}")
	public String Image(Model model, @PathVariable("id") int id) {
		log.info(id);
		MedicalHistory medObj = medicalHistoryService.getMedicalById(id);
		log.info("str" + medObj.getHistoryPath());
		model.addAttribute("medObj", medObj.getHistoryPath());

		return "viewimage";
	}

	@GetMapping("/deleteimage/{id}")
	public String deletedoctors(Model model, @PathVariable("id") int id,HttpServletRequest request) {
		
		MedicalHistory med=medicalHistoryService.getMedicalById(id);
		int patientid=med.getPatient().getPatientId();
		medicalHistoryService.deleteImageById(id);
		List<MedicalHistory> medicalHistoryList = medicalHistoryService.getAllHistory();
		model.addAttribute("medicalHistoryList", medicalHistoryList);
		HttpSession session=request.getSession();
		session.setAttribute("medhistory","delete");
		return "redirect:/med/view/"+patientid;
	}

}
