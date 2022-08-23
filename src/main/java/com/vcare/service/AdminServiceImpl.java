package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcare.beans.Admin;
import com.vcare.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public void saveAdmin(Admin admin) {
		this.adminRepository.save(admin);

	}

	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();

	}

	@Override
	public Admin getAdmin(String email, String password) {
		return adminRepository.findByEmailIgnoreCaseAndPassword(email, password);
	}

	@Override
	public Admin findByMail(String email) {
		// TODO Auto-generated method stub
		return adminRepository.findByEmail(email);
	}

	@Override
	public void UpdateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		adminRepository.save(admin);
	}

	@Override
	public Admin getadminById(int adminID) {
		// TODO Auto-generated method stub
		return adminRepository.getById(adminID);
	}

}
