package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcare.beans.Aboutus;
import com.vcare.repository.AboutusRepository;

@Service
public class AboutusServiceImpl implements AboutusService {
	
	@Autowired
	
	AboutusRepository aboutusRepository;

	@Override
	public List<Aboutus> getAllAbouts() {
		// TODO Auto-generated method stub
		return aboutusRepository.findAll();
	}

	@Override
	public Aboutus addAbouts(Aboutus about) {
		// TODO Auto-generated method stub
		return aboutusRepository.save(about);
	}

	@Override
	public void updateAboutus(Aboutus about) {
		// TODO Auto-generated method stub
		aboutusRepository.save(about);
	}

	@Override
	public void deleteAboutusById(int id) {
		// TODO Auto-generated method stub
		aboutusRepository.deleteById(id);
	}

	@Override
	public Aboutus getAboutusById(int id) {
		// TODO Auto-generated method stub
		return aboutusRepository.findById(id).get();
	}

}
