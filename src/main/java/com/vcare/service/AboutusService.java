package com.vcare.service;

import java.util.List;

import com.vcare.beans.Aboutus;


public interface AboutusService {
	
	List<Aboutus> getAllAbouts();

	Aboutus addAbouts(Aboutus about);

	void updateAboutus(Aboutus about);

	void deleteAboutusById(int id);

	Aboutus getAboutusById(int id);


}
