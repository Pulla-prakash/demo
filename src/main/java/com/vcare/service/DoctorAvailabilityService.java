package com.vcare.service;
import java.util.List;
import com.vcare.beans.DoctorAvailability;

public interface DoctorAvailabilityService {
	
DoctorAvailability addDoctorAvailability(DoctorAvailability emp); 
DoctorAvailability getDoctorAvailabilityId(int availabilityId); 
void editEmployee(DoctorAvailability availability); 
void deleteEmployeeById(int availabilityId);
List<DoctorAvailability> getAllDoctorAvailability();
}

