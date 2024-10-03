package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleAppointmentSelectDateTimeController.class)
class ScheduleAppointmentSelectDateTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test rendering the appointmentSelectDateTime page for scheduling new appointment
    @Test
    void testScheduleAppointmentDisplayDateTime() throws Exception {
        // Perform a GET request to /appointment/new/select_date_time with vetId and appointmentTypeId parameters
        mockMvc.perform(get("/appointment/new/select_date_time")
                .param("vetId", "1")
                .param("appointmentTypeId", "1"))
                .andExpect(status().isOk()) // Expect the status to be OK
                .andExpect(view().name("appointmentSelectDateTime")); // Expect the view to be appointmentSelectDateTime
    }

    // Test rendering the appointmentSelectDateTime page for reschedule appointment
    @Test
    void testRescheduleAppointmentDisplayDateTime() throws Exception {
        // Perform a GET request to /appointment/new/select_date_time with vetId and appointmentTypeId parameters
        mockMvc.perform(get("/appointment/reschedule/select_date_time")
                .param("appointmentId", "1")
                .param("vetId", "1")
                .param("appointmentTypeDuration", "30"))
                .andExpect(status().isOk()) // Expect the status to be OK
                .andExpect(view().name("rescheduleAppointmentSelectDateTime")); // Expect the view to be appointmentSelectDateTime
    }
}