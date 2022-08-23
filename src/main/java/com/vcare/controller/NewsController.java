package com.vcare.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.vcare.beans.News;
import com.vcare.service.HospitalService;
import com.vcare.service.NewsService;

@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
	NewsService newsservice;
   @Autowired
   HospitalService hospitalService;
	@GetMapping("/viewAllNews")
	public String viewHomePages(Model model) {
		List<Hospital> hospList = hospitalService.getAllHospitals();
		model.addAttribute("hospitalsList", hospList);
		List<News> newslist = newsservice.getAllNews();
		
		model.addAttribute("newslist", newslist);
		return "indexnews";
	}

	
	@GetMapping("/newsdetails/{id}")
	public String displayNews(Model model, @PathVariable("id") int NewsId) {
		News obj = newsservice.GetNewsById(NewsId);
		model.addAttribute("newspage", obj);
		List<News> newslists = newsservice.getLatestNews();
		model.addAttribute("newsLists", newslists);
		try {
		List<News> newslist = newslists.subList(0, 2);
		System.out.println("fdgyuhjkvgchfyuikjhgfyuil"+newslist);
		model.addAttribute("newsList", newslist);
		}catch(IndexOutOfBoundsException e) {
		System.out.println("vgqedvgdhvedhvEYUDGHebdhjB4534567456");
		model.addAttribute("newsList", null);
		}


		return "newspage";
		
	}

	@GetMapping("/newstable")
	public String viewalldepartment(Model model,HttpServletRequest request) {
		model.addAttribute("newslist", newsservice.getAllNews());
		HttpSession session=request.getSession();
		if(session.getAttribute("admminNews")=="News added") {
			model.addAttribute("adminmsg",session.getAttribute("Nname")+"\tNews Added" );
			session.setAttribute("admminNews", "");
			session.setAttribute("Nname", "");
		}
		if(session.getAttribute("Newsmsg")=="Newsmsg") {
			model.addAttribute("adminmsg", session.getAttribute("newsname")+"\t Updated");
			session.setAttribute("newsname", "");
			session.setAttribute("Newmsg", "");
		}
		
		if(session.getAttribute("newsdelete")=="newsdelete") {
			model.addAttribute("adminmsg", "\tSucccessfully deleted");
			session.setAttribute("newsdelete", "");
		}
		return "Newslist";
	}

	@GetMapping("/deletenews/{NewsId}")
	public String deleteNews(Model model, @PathVariable(value = "NewsId") int NewsId,HttpServletRequest request) {
		System.out.println("Hi delete");
		newsservice.deleteNewsById(NewsId);
		System.out.println("delete operation is invoked");
		model.addAttribute("newslist", newsservice.getAllNews());
		HttpSession session=request.getSession();
		session.setAttribute("newsdelete", "newsdelete");
		return "redirect:/news/newstable";
	}

	@GetMapping("/addnews")
	public String showNewNewsForm(Model model) {
		News news = new News();
		model.addAttribute("objnews", news);
		return "AddNews";
	}

	
	@RequestMapping(value = "/savenews", method = RequestMethod.POST)
	public String addNews(Model model,@RequestParam("file") MultipartFile file ,@RequestParam("file1") MultipartFile file1 ,News newsObj,HttpServletRequest request) throws IOException {
	newsObj.setCreated(LocalDate.now());
	
	if(file.getOriginalFilename()=="") {
		newsObj.setNewsImage(newsObj.getNewsImage());
	}else {
	newsObj.setNewsImage(Base64.getEncoder().encodeToString(file.getBytes()));
	}
	newsObj.setVideo(Base64.getEncoder().encodeToString(file1.getBytes()));
	newsservice.SaveMultimedia(file, newsObj);
	newsservice.SaveMultimedia(file1, newsObj);
	List<News> newslist = newsservice.getAllNews();
	model.addAttribute("newslist", newslist);
    HttpSession session=request.getSession();
    
    model.addAttribute("Nname", newsObj.getContentName());
    if(newsObj.getNewsId()!=0) {
    	session.setAttribute("Newsmsg", "Newsmsg");
    	session.setAttribute("newsname",newsObj.getContentName());
    }else {
    	session.setAttribute("admminNews", "News added");
    }
	return "redirect:/news/newstable";
	}

	@GetMapping("/editnews/{NewsId}")
	public String editNews(Model model, @PathVariable(value = "NewsId") int NewsId) {
		News NewsObj = newsservice.GetNewsById(NewsId);
		model.addAttribute("objnews", NewsObj);
		model.addAttribute("NewsId", NewsId);
		return "AddNews";
	}

}
