package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.Order;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;
import au.edu.rmit.sept.webapp.services.OrderService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.PrescribedMedicationService;

@Controller
public class CheckoutController {

    @Autowired
    private PrescribedMedicationService prescriptionService;

    @Autowired
    private PetService petService;

    @Autowired
    private PetOwnerService ownerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String showCheckoutPage(@RequestParam("prescriptionId") int prescriptionId, Model model) {

        PrescribedMedication selectedPrescription = prescriptionService.getPrescribedMedicationByID(prescriptionId);
    
        // Add the selected prescription to the model if found
        if (selectedPrescription != null) {
            Appointment appointment = selectedPrescription.getAppointment();
            int petID = appointment.getPetID();
            Pet pet = petService.getPetByPetID(petID);
            PetOwner owner = pet.getPetOwner();

            model.addAttribute("selectedPrescription", selectedPrescription);
            model.addAttribute("owner", owner);

        } else {
            model.addAttribute("errorMessage", "Prescription not found");
        }

        return "checkout";
    }


    @PostMapping("/checkout")
    public String processCheckout(
        @RequestParam("prescriptionId") int prescriptionId,
        @RequestParam("ownerId") int ownerId,
        Model model
    ){
        PrescribedMedication selectedPrescription = prescriptionService.getPrescribedMedicationByID(prescriptionId);
        Order order = selectedPrescription.getOrder();
        
        order.setStatus("Packing order");
        orderService.createOrder(order);//Yes I'm using the createMethod but it achieves the same functionality

        PetOwner owner = ownerService.getPetOwnerByPetOwnerID(ownerId);

        return "redirect:/petOwnerWelcome?userId=" + owner.getUserID() + "&petOwnerId=" + ownerId;
    }
}
