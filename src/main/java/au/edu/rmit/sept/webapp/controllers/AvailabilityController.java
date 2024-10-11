package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Availability;
import au.edu.rmit.sept.webapp.models.VetAvailability;
import au.edu.rmit.sept.webapp.services.AvailabilityService;
import au.edu.rmit.sept.webapp.services.VetAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;

@Controller
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private VetAvailabilityService vetAvailabilityService;

    @GetMapping("/availability")
    public String getAvailability(@RequestParam("vetId") int vetId, Model model) {
        // Retrieve all availabilities for the given vetId
        List<VetAvailability> availabilities = vetAvailabilityService.findAvailabilitiesByVetId(vetId);
        // Calculate the current week's start date (Monday)
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Add the list of availabilities, vetId, and weekStart to the model
        model.addAttribute("availabilities", availabilities);
        model.addAttribute("vetId", vetId);
        model.addAttribute("weekStart", weekStart);

        // Return the view name for displaying the availability list
        return "availability";
    }

    @PostMapping("/availability")
    public String saveAvailability(
            @RequestParam("vetId") int vetId,
            @RequestParam("date") LocalDate date,
            @RequestParam("startTime") LocalTime startTime,
            @RequestParam("endTime") LocalTime endTime,
            RedirectAttributes redirectAttributes) {

        // Check if the date is missing
        if (date == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Date is required.");
            return "redirect:/availability?vetId=" + vetId;
        }

        // Find if there's already an availability for the vet on the given date
        VetAvailability existingVetAvailability = vetAvailabilityService.findByVetIdAndDate(vetId, date);

        if (existingVetAvailability != null) {
            // Update the existing availability
            Availability existingAvailability = existingVetAvailability.getAvailability();
            existingAvailability.setStartTime(startTime);
            existingAvailability.setEndTime(endTime);
            availabilityService.saveAvailability(existingAvailability);
            redirectAttributes.addFlashAttribute("successMessage", "Availability updated successfully.");
        } else {
            // Create a new availability if it does not exist
            Availability newAvailability = new Availability(date, startTime, endTime);
            Availability savedAvailability = availabilityService.saveAvailability(newAvailability);

            if (savedAvailability == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Failed to save availability.");
                return "redirect:/availability?vetId=" + vetId;
            }

            // Link the vet to the new availability
            vetAvailabilityService.saveOrUpdateVetAvailability(vetId, savedAvailability.getId(), date);
            redirectAttributes.addFlashAttribute("successMessage", "Availability created successfully.");
        }

        return "redirect:/availability?vetId=" + vetId;
    }

    @GetMapping("/availability/delete")
    public String deleteAvailability(
            @RequestParam("vetId") int vetId,
            @RequestParam("date") LocalDate date,
            RedirectAttributes redirectAttributes) {

        // Find the availability for the vet on the given date
        VetAvailability existingVetAvailability = vetAvailabilityService.findByVetIdAndDate(vetId, date);

        if (existingVetAvailability != null) {
            // Delete the availability record
            vetAvailabilityService.deleteVetAvailability(existingVetAvailability);
            redirectAttributes.addFlashAttribute("successMessage", "Availability deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No availability found for the given vet and date.");
        }

        return "redirect:/availability?vetId=" + vetId;
    }
}
