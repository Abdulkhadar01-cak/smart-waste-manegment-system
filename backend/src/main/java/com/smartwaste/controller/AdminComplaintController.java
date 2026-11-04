package com.smartwaste.controller;

import com.smartwaste.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/admin/complaints")
public class AdminComplaintController {

    private final ComplaintService complaintService;

    public AdminComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // Display all complaints and available workers
    @GetMapping
    public String showComplaints(Model model) {

        model.addAttribute(
                "complaints",
                complaintService.getAllComplaints()
        );

        model.addAttribute(
                "workers",
                complaintService.getActiveWorkers()
        );

        return "admin/complaints";
    }

    // Assign complaint to a worker
    @PostMapping("/assign")
    public String assignComplaint(
            @RequestParam UUID complaintId,
            @RequestParam UUID workerId,
            RedirectAttributes redirectAttributes) {

        try {
            complaintService.assignComplaint(complaintId, workerId);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Complaint assigned successfully!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }
        return "redirect:/admin/dashboard";
    }
}