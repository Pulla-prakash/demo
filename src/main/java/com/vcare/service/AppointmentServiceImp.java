package com.vcare.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.vcare.beans.Appointment;
import com.vcare.repository.AppointmentRepository;

@Service
public class AppointmentServiceImp implements AppointmentService {

	// Appointment Service
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public List<Appointment> getAllAppointment() {
		return appointmentRepository.findAll();
	}

	@Override
	public Appointment getAppointmentById(long appointmentId) {
		return appointmentRepository.getById(appointmentId);
	}

	@Override
	public Appointment addAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public void updateAppointment(Appointment appointment) {
		appointmentRepository.save(appointment);

	}

	@Override
	public void deleteAppointmentById(long appointmentId) {
		try {
			appointmentRepository.deleteById(appointmentId);
		} catch (DataAccessException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	@Override
    public List<Appointment> getPatientAppointment(List<Appointment> appointment) {
        LocalDate startdate = LocalDate.now();
        for (Appointment app : appointment) {
            LocalDate enddate = LocalDate.parse(app.getDate());
            LocalTime endtime=LocalTime.parse(app.getSlot());
            if (enddate.isBefore(startdate) || enddate.equals(LocalDate.now()) && endtime.plusMinutes(10).isBefore(LocalTime.now()) ) {
                Appointment papp = appointmentRepository.getById(app.getAppointmentId());
                papp.setIsactive('N');
                appointmentRepository.save(papp);
            } else {
                Appointment papp = appointmentRepository.getById(app.getAppointmentId());
                if (papp.getIsactive() != 'N') {
                    papp.setIsactive('Y');
                    appointmentRepository.save(papp);
                }
            }
        }
        return appointment;
    }
}
