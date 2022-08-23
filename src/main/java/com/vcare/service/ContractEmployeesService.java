package com.vcare.service;

import java.util.List;

import com.vcare.beans.ContractEmployees;
import com.vcare.beans.Employees;

public interface ContractEmployeesService {
	
	List<ContractEmployees> getAllContactEmployee();

	ContractEmployees addContactEmployee(ContractEmployees contactEmployees);

	void updateContactEmployee(ContractEmployees contactEmployees);

	void deleteContactEmployeeId(int id);
	ContractEmployees getcontractEmployee(String email, String password);

	ContractEmployees getContactEmployeeById(int id);
	public String sendSimpleEmail(String toEmail, String body, String subject);

}
