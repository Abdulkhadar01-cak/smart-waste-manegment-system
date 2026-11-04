package com.smartwaste.controller;

import com.smartwaste.service.ComplaintService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // Open complaint submission page
    @GetMapping("/citizen/complaints/new")
    public String showComplaintForm() {
        return "citizen/complaint-form";
    }

    // Submit a new complaint
    @PostMapping("/citizen/complaints")
    public String submitComplaint(
            @RequestParam String complaintType,
            @RequestParam String description,
            @RequestParam String address,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            complaintService.submitComplaint(
                    authentication.getName(),
                    complaintType,
                    description,
                    address);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Your complaint has been submitted successfully!");

            return "redirect:/citizen/dashboard";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());

            return "redirect:/citizen/complaints/new";
        }
    }

    @GetMapping("/citizen/complaints")
    public String showMyComplaints(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "complaints",
                complaintService.getCitizenComplaints(authentication.getName()));

        return "citizen/my-complaints";
    }

    @GetMapping("/citizen/complaints/track")
    public String trackComplaints(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "complaints",
                complaintService.getCitizenComplaints(authentication.getName()));

        return "citizen/track-complaints";
    }
}