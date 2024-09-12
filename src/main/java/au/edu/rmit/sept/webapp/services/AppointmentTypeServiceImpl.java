package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.services.AppointmentTypeService;
import au.edu.rmit.sept.webapp.repositories.AppointmentTypeRepository;

@Service
public class AppointmentTypeServiceImpl implements AppointmentTypeService{

    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    public AppointmentTypeServiceImpl(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    @Override
    public AppointmentType getAppointmentTypeByAppointmentTypeID(int appointmentTypeID) {
        return appointmentTypeRepository.findById(appointmentTypeID);
    }
}
