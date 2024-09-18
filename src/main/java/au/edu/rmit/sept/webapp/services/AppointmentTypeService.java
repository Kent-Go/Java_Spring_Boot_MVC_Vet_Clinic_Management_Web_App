package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.AppointmentType;

import java.util.Collection;

public interface AppointmentTypeService {
    // get AppointmentType entity by appointmentTypeID
    public AppointmentType getAppointmentTypeByAppointmentTypeID(int appointmentTypeID);

    public Collection<AppointmentType> getAllAppointmentType();
}
