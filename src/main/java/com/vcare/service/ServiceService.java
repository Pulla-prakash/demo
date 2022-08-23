package com.vcare.service;

import java.util.List;

import com.vcare.beans.Services;

public interface ServiceService {

	List<Services> getAllServices();

	Services addServices(Services service);

	void UpdateServices(Services service);

	void deleteServicesById(int serviceId);

	Services getById(int serviceId);
	
	List<Services> findSubCatagoryByCatagory(int Id);

}
