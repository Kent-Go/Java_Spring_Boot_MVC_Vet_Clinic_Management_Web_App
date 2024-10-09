package au.edu.rmit.sept.webapp.controllers;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Order;
import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.SurgeryHistory;
import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import au.edu.rmit.sept.webapp.models.PrescribedMedication;

import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.OrderService;
import au.edu.rmit.sept.webapp.services.AddressService;
import au.edu.rmit.sept.webapp.services.PetOwnerService;
import au.edu.rmit.sept.webapp.services.MedicineService;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.SurgeryHistoryService;
import au.edu.rmit.sept.webapp.services.ImmunisationHistoryService;
import au.edu.rmit.sept.webapp.services.PrescribedMedicationService;

@Controller
public class PetProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SurgeryHistoryService surgeryHistoryService;

    @Autowired
    private ImmunisationHistoryService immunisationHistoryService;

    @Autowired
    private PrescribedMedicationService prescribedMedicationService;

    // Display the pet profile page
    @GetMapping("/petProfile")
    public String showPetProfile(@RequestParam("petId") int petId,
            @RequestParam(value = "editMode", defaultValue = "false") boolean editMode,
            @RequestParam(value = "editMedicationMode", defaultValue = "false") boolean editMedicationMode,
            @RequestParam(value = "editHealthConcernsMode", defaultValue = "false") boolean editHealthConcernsMode,
            @RequestParam(value = "editImmunisationMode", defaultValue = "false") boolean editImmunisationMode,
            @RequestParam(value = "editSurgeryMode", defaultValue = "false") boolean editSurgeryMode,
            Model model) {
        // Get the pet by petId and the pet owner by the petOwnerId that is associated
        // with the pet
        Pet pet = petService.getPetByPetID(petId);

        // Associate the pet with the pet owner
        pet.setPetOwner(petOwnerService.getPetOwnerByPetOwnerID(pet.getPetOwnerID()));

        // Associate the pet owner with the user
        pet.getPetOwner().setUser(userService.getUserByUserID(pet.getPetOwner().getUserID()));

        // Associate the user with the address
        Address address = addressService.getAddressByUserID(pet.getPetOwner().getUserID());
        address.setUser(pet.getPetOwner().getUser());

        // Get the appointments of the pet
        Collection<Appointment> appointments = appointmentService.getAppointmentByPetID(petId);

        // Associate the appointments with the pet
        appointments.forEach(appointment -> {
            appointment.setPet(pet);
        });

        // For each appointment, get the prescribed medications and add them to the list
        List<PrescribedMedication> prescribedMedications = new ArrayList<>();
        appointments.forEach(appointment -> {
            prescribedMedications
                    .addAll(prescribedMedicationService.getPrescribedMedicationByAppointmentID(appointment.getId()));
        });

        // For each prescribed medication, get the medicine
        prescribedMedications.forEach(prescribedMedication -> {
            prescribedMedication.setMedicine(prescribedMedication.getMedicine());
        });

        // Get the list of immunisation history of the pet
        List<ImmunisationHistory> immunisationHistories = immunisationHistoryService
                .getImmunisationHistoryByPetID(petId);

        // Get the list of surgery history of the pet
        List<SurgeryHistory> surgeryHistories = surgeryHistoryService.getSurgeryHistoryByPetID(petId);

        // Add the pet to the model
        model.addAttribute("pet", pet);
        model.addAttribute("address", address);
        model.addAttribute("surgeryHistories", surgeryHistories);
        model.addAttribute("immunisationHistories", immunisationHistories);
        model.addAttribute("prescribedMedications", prescribedMedications);

        // Add flags to the model to determine if the page is in edit mode
        model.addAttribute("editMode", editMode);
        model.addAttribute("editMedicationMode", editMedicationMode);
        model.addAttribute("editHealthConcernsMode", editHealthConcernsMode);
        model.addAttribute("editImmunisationMode", editImmunisationMode);
        model.addAttribute("editSurgeryMode", editSurgeryMode);

        return "petProfile"; // This refers to the petProfile.html page
    }

    // Method to handle form submission for updating the pet basic information
    @PostMapping("/updatePetProfile")
    public String updatePetProfile(@RequestParam("petId") int petId,
            @RequestParam("name") String name,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("species") String species,
            @RequestParam("breed") String breed,
            @RequestParam("gender") String gender,
            @RequestParam("weight") float weight) {

        // Fetch the pet by ID and update the details
        Pet pet = petService.getPetByPetID(petId);

        String regex = "^[a-zA-Z0-9 ]*$";

        // Validate the input before updating the pet profile
        if (name == null || name.isEmpty() || birthDate == null || birthDate.isEmpty() || species == null
                || species.isEmpty() || breed == null || breed.isEmpty() || gender == null || gender.isEmpty()
                || weight <= 0 || !name.matches(regex) || !species.matches(regex) || !breed.matches(regex)) {
            return "redirect:/petProfile?petId=" + petId + "&editMode=true";
        }

        pet.setName(name);
        pet.setBirthDate(LocalDate.parse(birthDate));
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setGender(gender);
        pet.setWeight(weight);

        // Save the updated pet profile
        petService.updatePet(pet);

        // Redirect to view mode after saving
        return "redirect:/petProfile?petId=" + petId;
    }

    // Method to handle form submission for updating the pet's prescribed
    // medications
    @PostMapping("/updateMedication")
    public String updateMedication(
            @RequestParam("petId") int petId,
            @RequestParam(value = "medicationId", required = false) String medicationId,
            @RequestParam("name") String name,
            @RequestParam("dosage") String dosage,
            @RequestParam("frequency") String frequency,
            @RequestParam("duration") String duration,
            @RequestParam("instruction") String instruction) {

        // Handle multiple medications based on presence of "|", because "|" shows how
        // many medications need to be processed
        if (medicationId == null) {
            if (hasMultipleMedications(name)) {
                return handleMultipleMedications(petId, name, dosage, frequency, duration, instruction);
            } else {
                return handleSingleMedication(petId, null, name, dosage, frequency, duration, instruction);
            }
        } else {
            return updateExistingMedications(petId, medicationId, name, dosage, frequency, duration, instruction);
        }
    }

    // Helper method to determine if multiple medications are being processed
    private boolean hasMultipleMedications(String name) {
        int countName = name.length() - name.replace("|", "").length();

        return countName > 1;
    }

    // Helper method to process multiple medications
    private String handleMultipleMedications(int petId, String name, String dosage, String frequency, String duration,
            String instruction) {
        String[] names = cleanAndSplit(name, "\\|,");
        String[] dosages = dosage.split(",");
        String[] frequencies = frequency.split(",");
        String[] durations = duration.split(",");
        String[] instructions = cleanAndSplit(instruction, "\\|,");

        List<Map<String, Object>> medicationList = buildMedicationList(null, names, dosages, frequencies, durations,
                instructions);

        return processMedications(petId, medicationList);
    }

    // Helper method to process a single medication
    private String handleSingleMedication(int petId, Integer medicationId, String name, String dosage, String frequency,
            String duration, String instruction) {
        String cleanedName = name.replace("|", "");
        String cleanedInstruction = instruction.replace("|", "");

        Map<String, Object> medicationMap = buildSingleMedicationMap(medicationId, cleanedName, dosage, frequency,
                duration, cleanedInstruction);

        List<Map<String, Object>> medicationList = Collections.singletonList(medicationMap);

        return processMedications(petId, medicationList);
    }

    // Helper method to update existing medications
    private String updateExistingMedications(int petId, String medicationId, String name, String dosage,
            String frequency, String duration, String instruction) {
        String[] names = cleanAndSplit(name, "\\|,");
        String[] dosages = dosage.split(",");
        String[] frequencies = frequency.split(",");
        String[] durations = duration.split(",");
        String[] instructions = cleanAndSplit(instruction, "\\|,");
        String[] medicationIds = medicationId.split(",");

        List<Map<String, Object>> medicationList = buildMedicationList(medicationIds, names, dosages, frequencies,
                durations, instructions);

        return processMedications(petId, medicationList);
    }

    // Helper method to build a medication list
    private List<Map<String, Object>> buildMedicationList(String[] medicationIds, String[] names, String[] dosages,
            String[] frequencies, String[] durations, String[] instructions) {
        List<Map<String, Object>> medicationList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            Integer medId = (medicationIds != null && i < medicationIds.length && !medicationIds[i].isEmpty())
                    ? Integer.parseInt(medicationIds[i])
                    : null;

            Map<String, Object> medicationMap = buildSingleMedicationMap(medId, names[i], dosages[i], frequencies[i],
                    durations[i], instructions[i]);
            medicationList.add(medicationMap);
        }

        return medicationList;
    }

    // Helper method to build a single medication map
    private Map<String, Object> buildSingleMedicationMap(Integer medicationId, String name, String dosage,
            String frequency, String duration, String instruction) {
        Map<String, Object> medicationMap = new HashMap<>();
        medicationMap.put("medicationId", medicationId);
        medicationMap.put("name", name);
        medicationMap.put("dosage", Integer.parseInt(dosage));
        medicationMap.put("frequency", Integer.parseInt(frequency));
        medicationMap.put("duration", Integer.parseInt(duration));
        medicationMap.put("instruction", instruction);

        return medicationMap;
    }

    // Helper method to clean and split strings
    private String[] cleanAndSplit(String input, String delimiter) {
        return Arrays.stream(input.split(delimiter))
                .map(s -> s.replace("|", "").trim())
                .toArray(String[]::new);
    }

    // Main logic to process medications (new or existing)
    private String processMedications(int petId, List<Map<String, Object>> medicationList) {
        try {
            for (Map<String, Object> medicationMap : medicationList) {
                PrescribedMedication prescribedMedication = getOrCreatePrescribedMedication(medicationMap, petId);
                Medicine medicine = medicineService.getMedicineByName((String) medicationMap.get("name"));

                if (medicine == null) {
                    return "redirect:/petProfile?petId=" + petId + "&error=Medicine does not exist!";
                }

                updatePrescribedMedicationFields(prescribedMedication, medicationMap, medicine);
                Order order = new Order(LocalDate.now(), "Pending");
                orderService.createOrder(order);

                prescribedMedication.setOrderID(order.getId());
                prescribedMedicationService.updatePrescribedMedication(prescribedMedication);
            }
        } catch (Exception e) {
            return "redirect:/petProfile?petId=" + petId + "&error=" + e.getMessage();
        }

        return "redirect:/petProfile?petId=" + petId;
    }

    // Helper method to get or create a PrescribedMedication
    private PrescribedMedication getOrCreatePrescribedMedication(Map<String, Object> medicationMap, int petId) {
        Integer medicationId = (Integer) medicationMap.get("medicationId");

        if (medicationId != null) {
            try {
                return prescribedMedicationService.getPrescribedMedicationByID(medicationId);
            } catch (Exception e) {
                // Log if no medication is found, fall back to creating a new one
                System.out.println("No medication found for ID: " + medicationId + " - Creating a new entry.");
            }
        }

        PrescribedMedication prescribedMedication = new PrescribedMedication();
        setAppointmentId(prescribedMedication, petId);

        return prescribedMedication;
    }

    // Helper method to set appointment ID for today
    private void setAppointmentId(PrescribedMedication prescribedMedication, int petId) {
        Collection<Appointment> appointments = appointmentService.getAppointmentByPetID(petId);
        appointments.stream()
                .filter(appointment -> appointment.getDate().equals(LocalDate.now()))
                .findFirst()
                .ifPresent(appointment -> prescribedMedication.setAppointmentID(appointment.getId()));

        if (prescribedMedication.getAppointmentID() == 0) {
            throw new IllegalStateException("No appointment found for today!");
        }
    }

    // Helper method to update prescribed medication fields
    private void updatePrescribedMedicationFields(PrescribedMedication prescribedMedication,
            Map<String, Object> medicationMap, Medicine medicine) {
        prescribedMedication.setDosage((int) medicationMap.get("dosage"));
        prescribedMedication.setDailyFrequency((int) medicationMap.get("frequency"));
        prescribedMedication.setDuration((int) medicationMap.get("duration"));
        prescribedMedication.setInstruction((String) medicationMap.get("instruction"));
        prescribedMedication.setMedicineID(medicine.getId());
        prescribedMedication.setMedicine(medicine);
    }

    // Method to handle deletion of medication
    @DeleteMapping("/deleteMedication/{id}")
    public ResponseEntity<Map<String, String>> deleteMedication(@PathVariable int id,
            @RequestParam("petId") int petId) {
        try {
            prescribedMedicationService.deletePrescribedMedicationByID(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Medication deleted successfully");
            response.put("redirectUrl", "/petProfile?petId=" + petId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error deleting medication: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Method to handle form submission for editing pet's allergies and existing
    // conditions
    @PostMapping("/updateHealthConcerns")
    public RedirectView updateHealthConcerns(
            @RequestParam("petId") int petId,
            @RequestParam(value = "allergies", required = false) String allergies,
            @RequestParam(value = "existingConditions", required = false) String existingConditions) {
        try {
            // Use empty strings if null
            if (allergies == null) {
                allergies = "";
            }
            if (existingConditions == null) {
                existingConditions = "";
            }

            petService.updatePetAllergiesAndExistingConditionsByPetID(petId, allergies, existingConditions);
            return new RedirectView("/petProfile?petId=" + petId);
        } catch (Exception e) {
            // Handle error and redirect to an error page or the same page with error
            // message
            return new RedirectView("/petProfile?petId=" + petId + "&error=" + e.getMessage());
        }
    }

    // Method to handle form submission for adding a new immunisation history
    @PostMapping("/updateImmunisationHistory")
    public RedirectView updateImmunisationHistory(
            @RequestParam("petId") int petId,
            @RequestParam(value = "immunisationId", required = false) String immunisationId,
            @RequestParam("immunisationDate") String immunisationDate,
            @RequestParam("immunisationName") String immunisationName,
            @RequestParam("immunisationNotes") String immunisationNotes) {

        // Split the input strings by ',' to get the individual values
        String[] immunisationIDSplit = immunisationId != null ? immunisationId.split(",") : new String[0]; // Handle
                                                                                                           // empty IDs
        String[] immunisationDateSplit = immunisationDate.split(",");

        // Split name and notes using '|'
        String[] immunisationNameSplit = immunisationName.split("\\|,");
        String[] immunisationNotesSplit = immunisationNotes.split("\\|,");

        // If the name or notes contains '|', then change it to ""
        for (int i = 0; i < immunisationNameSplit.length; i++) {
            if (immunisationNameSplit[i].contains("|")) {
                immunisationNameSplit[i] = immunisationNameSplit[i].replace("|", "");
            }
        }

        // Ensure the other arrays have the same length
        for (int i = 0; i < immunisationNotesSplit.length; i++) {
            if (immunisationNotesSplit[i].contains("|")) {
                immunisationNotesSplit[i] = immunisationNotesSplit[i].replace("|", "");
            }
        }

        // Ensure the other arrays have the same length
        if (immunisationDateSplit.length != immunisationNameSplit.length
                || immunisationNameSplit.length != immunisationNotesSplit.length) {
            return new RedirectView("/petProfile?petId=" + petId + "&error=Inconsistent%20data%20lengths");
        }

        try {
            // Loop through the split values and append them to a list
            List<Map<String, Object>> immunisationList = new ArrayList<>();
            for (int i = 0; i < immunisationDateSplit.length; i++) {
                Map<String, Object> immunisationMap = new HashMap<>();

                // Handle immunisation ID: check if it exists (for existing records)
                if (i < immunisationIDSplit.length && immunisationIDSplit[i] != null
                        && !immunisationIDSplit[i].isEmpty()) {
                    immunisationMap.put("immunisationId", Integer.parseInt(immunisationIDSplit[i]));
                } else {
                    // No ID for new rows
                    immunisationMap.put("immunisationId", null);
                }

                immunisationMap.put("immunisationDate", LocalDate.parse(immunisationDateSplit[i]));
                immunisationMap.put("immunisationName", immunisationNameSplit[i]);
                immunisationMap.put("immunisationsNotes", immunisationNotesSplit[i]);
                immunisationList.add(immunisationMap);
            }

            // Fetch the pet by ID
            Pet pet = petService.getPetByPetID(petId);

            // For each immunisation in the list, update or create them
            for (Map<String, Object> immunisationMap : immunisationList) {
                ImmunisationHistory immunisationHistory = null;

                // Check if the immunisation ID exists (for existing entries)
                Integer immunisationID = (Integer) immunisationMap.get("immunisationId");

                if (immunisationID != null) {
                    try {
                        // Try to fetch the existing immunisation history by ID
                        immunisationHistory = immunisationHistoryService.getImmunisationHistoryByID(immunisationID);
                    } catch (Exception e) {
                        // Log if the ID was invalid (for debugging)
                        System.out.println(
                                "No immunisation found for ID: " + immunisationID + " - Creating a new entry.");
                    }
                }

                // If no existing immunisation is found or it's a new entry, create a new
                // instance
                if (immunisationHistory == null) {
                    immunisationHistory = new ImmunisationHistory();
                }

                // Update the fields for the immunisation history
                immunisationHistory.setDate((LocalDate) immunisationMap.get("immunisationDate"));
                immunisationHistory.setName((String) immunisationMap.get("immunisationName"));
                immunisationHistory.setNotes((String) immunisationMap.get("immunisationsNotes"));
                immunisationHistory.setPetID(pet.getId());
                immunisationHistory.setPet(pet);

                // Save the new or updated immunisation history
                immunisationHistoryService.saveOrUpdateImmunisationHistory(immunisationHistory);
            }

            // Redirect back to the pet's profile after successful update
            return new RedirectView("/petProfile?petId=" + petId);
        } catch (Exception e) {
            return new RedirectView("/petProfile?petId=" + petId + "&error=" + e.getMessage());
        }
    }

    // Method to handle form submission for adding a new surgery history
    @PostMapping("/updateSurgeryHistory")
    public RedirectView updateSurgeryHistory(
            @RequestParam("petId") int petId,
            @RequestParam(value = "surgeryId", required = false) String surgeryId,
            @RequestParam("surgeryDate") String surgeryDate,
            @RequestParam("surgeryName") String surgeryName,
            @RequestParam("surgeryNotes") String surgeryNotes) {

        // Split the input strings by ',' to get the individual values
        String[] surgeryIDSplit = surgeryId != null ? surgeryId.split(",") : new String[0]; // Handle empty IDs
        String[] surgeryDateSplit = surgeryDate.split(",");

        // Split name and notes using '|'
        String[] surgeryNameSplit = surgeryName.split("\\|,");
        String[] surgeryNotesSplit = surgeryNotes.split("\\|,");

        // If the name or notes contains '|', then change it to ""
        for (int i = 0; i < surgeryNameSplit.length; i++) {
            if (surgeryNameSplit[i].contains("|")) {
                surgeryNameSplit[i] = surgeryNameSplit[i].replace("|", "");
            }
        }

        for (int i = 0; i < surgeryNotesSplit.length; i++) {
            if (surgeryNotesSplit[i].contains("|")) {
                surgeryNotesSplit[i] = surgeryNotesSplit[i].replace("|", "");
            }
        }

        // Ensure the other arrays have the same length
        if (surgeryDateSplit.length != surgeryNameSplit.length
                || surgeryNameSplit.length != surgeryNotesSplit.length) {
            return new RedirectView("/petProfile?petId=" + petId + "&error=Inconsistent%20data%20lengths");
        }

        try {
            // Loop through the split values and append them to a list
            List<Map<String, Object>> surgeryList = new ArrayList<>();
            for (int i = 0; i < surgeryDateSplit.length; i++) {
                Map<String, Object> surgeryMap = new HashMap<>();

                // Handle surgery ID: check if it exists (for existing records)
                if (i < surgeryIDSplit.length && surgeryIDSplit[i] != null
                        && !surgeryIDSplit[i].isEmpty()) {
                    surgeryMap.put("surgeryId", Integer.parseInt(surgeryIDSplit[i]));
                } else {
                    // No ID for new rows
                    surgeryMap.put("surgeryId", null);
                }

                surgeryMap.put("surgeryDate", LocalDate.parse(surgeryDateSplit[i]));
                surgeryMap.put("surgeryName", surgeryNameSplit[i]);
                surgeryMap.put("surgeryNotes", surgeryNotesSplit[i]);
                surgeryList.add(surgeryMap);
            }

            // Fetch the pet by ID
            Pet pet = petService.getPetByPetID(petId);

            // For each surgery in the list, update or create them
            for (Map<String, Object> surgeryMap : surgeryList) {
                SurgeryHistory surgeryHistory = null;

                // Check if the surgery ID exists (for existing entries)
                Integer surgeryID = (Integer) surgeryMap.get("surgeryId");

                if (surgeryID != null) {
                    try {
                        // Try to fetch the existing surgery history by ID
                        surgeryHistory = surgeryHistoryService.getSurgeryHistoryByID(surgeryID);
                    } catch (Exception e) {
                        // Log if the ID was invalid (for debugging)
                        System.out.println(
                                "No surgery found for ID: " + surgeryID + " - Creating a new entry.");
                    }
                }

                // If no existing surgery is found or it's a new entry, create a new
                // instance
                if (surgeryHistory == null) {
                    surgeryHistory = new SurgeryHistory();
                }

                // Update the fields for the immunisation history
                surgeryHistory.setDate((LocalDate) surgeryMap.get("surgeryDate"));
                surgeryHistory.setName((String) surgeryMap.get("surgeryName"));
                surgeryHistory.setNotes((String) surgeryMap.get("surgeryNotes"));
                surgeryHistory.setPetID(pet.getId());
                surgeryHistory.setPet(pet);

                // Save the new or updated immunisation history
                surgeryHistoryService.saveOrUpdateSurgeryHistory(surgeryHistory);
            }

            // Redirect back to the pet's profile after successful update
            return new RedirectView("/petProfile?petId=" + petId);
        } catch (Exception e) {
            return new RedirectView("/petProfile?petId=" + petId + "&error=" + e.getMessage());
        }
    }
}
