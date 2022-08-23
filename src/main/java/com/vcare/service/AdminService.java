package com.vcare.service;

import java.util.List;

import com.vcare.beans.Admin;

public interface AdminService {

	void saveAdmin(Admin admin);
	void UpdateAdmin(Admin admin);

	List<Admin> getAllAdmins();
	
	Admin findByMail(String email);

	Admin getAdmin(String email, String password);

	Admin getadminById(int adminID);
}
