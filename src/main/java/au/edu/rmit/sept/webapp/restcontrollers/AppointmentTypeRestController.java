package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.AppointmentType;
import au.edu.rmit.sept.webapp.repositories.AppointmentTypeRepository;

import java.util.Collection;
import java.util.Optional;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/appointment_type")
public class AppointmentTypeRestController {
    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    @GetMapping("/{AppointmentTypeID}")
    public AppointmentType getAppointmentTypeByAppointmentTypeId(@PathVariable("AppointmentTypeID") int AppointmentTypeID) {
        return appointmentTypeRepository.findById(AppointmentTypeID);
    }
}
