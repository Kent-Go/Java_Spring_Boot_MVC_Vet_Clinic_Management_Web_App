package au.edu.rmit.sept.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
import au.edu.rmit.sept.webapp.models.ClinicAppointmentTypePrice;
import au.edu.rmit.sept.webapp.models.ClinicAddress;

import au.edu.rmit.sept.webapp.services.ClinicService;
import au.edu.rmit.sept.webapp.services.VetService;
import au.edu.rmit.sept.webapp.services.VetAppointmentTypeOfferedService;
import au.edu.rmit.sept.webapp.services.ClinicAppointmentTypePriceService;
import au.edu.rmit.sept.webapp.services.ClinicAddressService;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

@Controller
public class ScheduleAppointmentSelectClinicController {

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private VetService vetService;

    @Autowired
    private VetAppointmentTypeOfferedService vetAppointmentTypeOfferedService;

    @Autowired
    private ClinicAppointmentTypePriceService clinicAppointmentTypePriceService;

    @Autowired
    private ClinicAddressService clinicAddressService;

    @GetMapping("/appointment/new/select_clinic")
    public String showClinic(
        @RequestParam("appointmentTypeId") int appointmentTypeId,
        Model model) {
        // List of VetAppointmentTypeOffered object
        Collection<VetAppointmentTypeOffered> vetAppointmentTypeOffereds = vetAppointmentTypeOfferedService.getVetAppointmentTypeOfferedByAppointmentTypeID(appointmentTypeId);

        // ClinicAppointmentTypePrice object collection
        Collection<ClinicAppointmentTypePrice> clinicAppointmentTypePrices = new ArrayList<>();

        // Clinic id list
        List<Integer> clinicIDList = new ArrayList<>();

        // iterate all vetAppointmentTypeOffered to get vet id
        for (VetAppointmentTypeOffered vetAppointmentTypeOffered : vetAppointmentTypeOffereds) {
            // Get vet id from vetAppointmentTypeOffered
            int vetID = vetAppointmentTypeOffered.getVetID();
            // Get vet object from vet id
            Vet vet = vetService.getVetByVetID(vetID);
            // get clinic id of the vet
            int clinicID = vet.getClinicID();
            // if the clinic id is not in the clinicIDList
            if (!clinicIDList.contains(clinicID)) {
                // Add the clinicID to the clinicIDList
                clinicIDList.add(clinicID);
            }
        }

        // if clinicIDList contains clinic id
        if (clinicIDList.size() > 0) {
            // iterate each clinic id
            for (int clinicID : clinicIDList) {
                // add the clinic object that is retrieve using clinic service
                ClinicAppointmentTypePrice clinicAppointmentTypePrice = clinicAppointmentTypePriceService.getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(clinicID, appointmentTypeId);
                Clinic clinic = clinicService.getClinicByClinicID(clinicID);
                clinic.setClinicAddress(clinicAddressService.getClinicAddressByClinicAddressID(clinic.getClinicAddressID()));
                clinicAppointmentTypePrice.setClinic(clinic);
                clinicAppointmentTypePrices.add(clinicAppointmentTypePrice);
            }
        }
       
        // Add the clinicAppointmentTypePrices to the model
        model.addAttribute("clinicAppointmentTypePrices", clinicAppointmentTypePrices);

        // Return the Thymeleaf view (appointmentSelectClinic.html)
        return "appointmentSelectClinic";
    }
}
