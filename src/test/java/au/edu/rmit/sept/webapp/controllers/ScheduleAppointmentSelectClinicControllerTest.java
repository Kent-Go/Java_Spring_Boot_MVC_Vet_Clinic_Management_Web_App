package au.edu.rmit.sept.webapp.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.models.ClinicAddress;
import au.edu.rmit.sept.webapp.models.ClinicAppointmentTypePrice;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
import au.edu.rmit.sept.webapp.services.ClinicAddressService;
import au.edu.rmit.sept.webapp.services.ClinicAppointmentTypePriceService;
import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.services.VetAppointmentTypeOfferedService;
import au.edu.rmit.sept.webapp.services.VetService;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

import static org.hamcrest.Matchers.containsString;

@WebMvcTest(controllers = ScheduleAppointmentSelectClinicController.class)
public class ScheduleAppointmentSelectClinicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VetAppointmentTypeOfferedService vetAppointmentTypeOfferedService;

    @MockBean
    private ClinicAppointmentTypePriceService clinicAppointmentTypePriceService;

    @MockBean
    private VetService vetService;

    @MockBean
    private ClinicService clinicService;

    @MockBean
    private ClinicAddressService clinicAddressService;

    @Test
    public void testShowClinic() throws Exception {
        // Mock test data
        Integer appointmentTypeId = 1;

        // VetAppointmentTypeOffered object
        VetAppointmentTypeOffered vetAppointmentTypeOffered1 = new VetAppointmentTypeOffered(1, 1);
        VetAppointmentTypeOffered vetAppointmentTypeOffered2 = new VetAppointmentTypeOffered(2, 1);
        VetAppointmentTypeOffered vetAppointmentTypeOffered3 = new VetAppointmentTypeOffered(3, 1);

        Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = new ArrayList<>();

        vetAppointmentTypeOffereds.add(vetAppointmentTypeOffered1);
        vetAppointmentTypeOffereds.add(vetAppointmentTypeOffered2);
        vetAppointmentTypeOffereds.add(vetAppointmentTypeOffered3);

        // Vet object
        Vet vet1 = new Vet();
        Vet vet2 = new Vet();
        Vet vet3 = new Vet();

        vet1.setId(1);
        vet1.setClinicID(1);
        vet2.setId(2);
        vet2.setClinicID(2);
        vet3.setId(3);
        vet3.setClinicID(3);

        // ClinicAppointmentTypePrice object
        ClinicAppointmentTypePrice clinicAppointmentTypePrice1 = new ClinicAppointmentTypePrice(1, 1, 100);
        ClinicAppointmentTypePrice clinicAppointmentTypePrice2 = new ClinicAppointmentTypePrice(2, 1, 200);
        ClinicAppointmentTypePrice clinicAppointmentTypePrice3 = new ClinicAppointmentTypePrice(3, 1, 150);

        // Clinic object
        Clinic clinic1 = new Clinic();
        Clinic clinic2 = new Clinic();
        Clinic clinic3 = new Clinic();

        clinic1.setName("Frank Samways Veterinary Clinic");
        clinic1.setClinicAddressID(1);
        clinic2.setName("Port Melbourne Veterinary Clinic");
        clinic2.setClinicAddressID(2);
        clinic3.setName("Brunswick Central Vet Clinic");
        clinic3.setClinicAddressID(3);

        ClinicAddress clinicAddress1 = new ClinicAddress("1 Boundary Rd", "North Melbourne", "Victoria", "3051");
        ClinicAddress clinicAddress2 = new ClinicAddress("109 Bay St", "Port Melbourne", "Victoria", "3207");
        ClinicAddress clinicAddress3 = new ClinicAddress("210 Lygon St", "Brunswick East", "Victoria", "3055");

        // Mock service methods
        when(vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(appointmentTypeId)).thenReturn(vetAppointmentTypeOffereds);

        when(vetService.getVetByVetID(1)).thenReturn(vet1);
        when(vetService.getVetByVetID(2)).thenReturn(vet2);
        when(vetService.getVetByVetID(3)).thenReturn(vet3);

        when(clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(1, 1)).thenReturn(clinicAppointmentTypePrice1);
        when(clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(2, 1)).thenReturn(clinicAppointmentTypePrice2);
        when(clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(3, 1)).thenReturn(clinicAppointmentTypePrice3);

        when(clinicService.getClinicByClinicID(1)).thenReturn(clinic1);
        when(clinicService.getClinicByClinicID(2)).thenReturn(clinic2);
        when(clinicService.getClinicByClinicID(3)).thenReturn(clinic3);

        when(clinicAddressService.getClinicAddressByClinicAddressID(1)).thenReturn(clinicAddress1);
        when(clinicAddressService.getClinicAddressByClinicAddressID(2)).thenReturn(clinicAddress2);
        when(clinicAddressService.getClinicAddressByClinicAddressID(3)).thenReturn(clinicAddress3);

        // Perform the GET request and check the rendered content
        MvcResult result = mockMvc.perform(get("/appointment/new/select_clinic")
                .param("appointmentTypeId", appointmentTypeId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("appointmentSelectClinic"))
                .andExpect(model().attributeExists("clinicAppointmentTypePrices"))
                .andExpect(content().string(containsString("Frank Samways Veterinary Clinic")))
                .andExpect(content().string(containsString("Port Melbourne Veterinary Clinic")))
                .andExpect(content().string(containsString("Brunswick Central Vet Clinic")))
                .andExpect(content().string(containsString("$ 100.00")))
                .andExpect(content().string(containsString("$ 200.00")))
                .andExpect(content().string(containsString("$ 150.00")))
                .andReturn();

        // Verify the model attributes are set correctly
        Collection<ClinicAppointmentTypePrice> returnedClinicAppointmentTypePrices = (Collection<ClinicAppointmentTypePrice>) result.getModelAndView().getModel().get("clinicAppointmentTypePrices");
        assertEquals(3, returnedClinicAppointmentTypePrices.size());

        // Retrieve the elements
        Iterator<ClinicAppointmentTypePrice> iterator = returnedClinicAppointmentTypePrices.iterator();
        ClinicAppointmentTypePrice returnedClinicAppointmentTypePrice1 = iterator.next(); // First element
        ClinicAppointmentTypePrice returnedClinicAppointmentTypePrice2 = iterator.next(); // Second element
        ClinicAppointmentTypePrice returnedClinicAppointmentTypePrice3 = iterator.next(); // Third element

        // assert first element
        assertEquals("Frank Samways Veterinary Clinic", returnedClinicAppointmentTypePrice1.getClinic().getName());
        assertEquals(100, returnedClinicAppointmentTypePrice1.getPrice());
        ClinicAddress returnedClinicAddress1 = returnedClinicAppointmentTypePrice1.getClinic().getClinicAddress();
        assertEquals("1 Boundary Rd", returnedClinicAddress1.getStreet());
        assertEquals("North Melbourne", returnedClinicAddress1.getSuburb());
        assertEquals("Victoria", returnedClinicAddress1.getState());
        assertEquals("3051", returnedClinicAddress1.getPostcode());

        // Assert second Element
        assertEquals("Port Melbourne Veterinary Clinic", returnedClinicAppointmentTypePrice2.getClinic().getName());
        assertEquals(200, returnedClinicAppointmentTypePrice2.getPrice());
        ClinicAddress returnedClinicAddress2 = returnedClinicAppointmentTypePrice2.getClinic().getClinicAddress();
        assertEquals("109 Bay St", returnedClinicAddress2.getStreet());
        assertEquals("Port Melbourne", returnedClinicAddress2.getSuburb());
        assertEquals("Victoria", returnedClinicAddress2.getState());
        assertEquals("3207", returnedClinicAddress2.getPostcode());

        // Assert third Element
        assertEquals("Brunswick Central Vet Clinic", returnedClinicAppointmentTypePrice3.getClinic().getName());
        assertEquals(150, returnedClinicAppointmentTypePrice3.getPrice());
        ClinicAddress returnedClinicAddress3 = returnedClinicAppointmentTypePrice3.getClinic().getClinicAddress();
        assertEquals("210 Lygon St", returnedClinicAddress3.getStreet());
        assertEquals("Brunswick East", returnedClinicAddress3.getSuburb());
        assertEquals("Victoria", returnedClinicAddress3.getState());
        assertEquals("3055", returnedClinicAddress3.getPostcode());
    }
}
