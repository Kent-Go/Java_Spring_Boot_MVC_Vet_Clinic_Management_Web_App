// package au.edu.rmit.sept.webapp.controllers;

// public class ClinicRegisterController {
    
// }

package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClinicRegisterController {

    @GetMapping("/clinicRegisterPage")
    public String showClinicRegisiter(Model model) {
        return "clinicRegisterPage";
    }

    @PostMapping("/clinicRegisterPage")
    public String clinicRegister() {
        // Handle the form submission, save the pet owner details
        return "redirect:/clinicRegisterPage"; // Redirect to the same page or to a success page
    }
}
