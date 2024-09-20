package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.repositories.AppointmentRepository;

import java.util.Collection;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentRestController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/{vetID}")
    public Collection<Appointment> getAppointmentByVetID(@PathVariable("vetID") int vetID, @RequestParam("date") LocalDate date) {
        return appointmentRepository.findByVetIDAndDateOrderByStartTimeAsc(vetID, date);
    }
}
