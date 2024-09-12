package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.AppointmentType;

public interface AppointmentTypeService {
    // get AppointmentType entity by appointmentTypeID
    public AppointmentType getAppointmentTypeByAppointmentTypeID(int appointmentTypeID);
}
