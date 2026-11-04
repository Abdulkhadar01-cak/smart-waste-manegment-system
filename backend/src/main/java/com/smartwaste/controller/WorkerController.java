package com.smartwaste.controller;

import com.smartwaste.entity.Complaint;
import com.smartwaste.service.ComplaintService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/worker")
public class WorkerController {

    private final ComplaintService complaintService;

    public WorkerController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/dashboard")
    public String workerDashboard(
            Authentication authentication,
            Model model,
            @RequestParam(required = false) String status) {

        String workerEmail = authentication.getName();

        List<Complaint> complaints =
                complaintService.getWorkerComplaints(workerEmail);

        if (status != null &&
                List.of("ASSIGNED", "IN_PROGRESS", "RESOLVED").contains(status)) {

            complaints = complaints.stream()
                    .filter(c -> status.equals(c.getStatus()))
                    .toList();
        }

        model.addAttribute("assignedComplaints", complaints);
        model.addAttribute("currentStatus", status);

        return "worker/dashboard";
    }
    @PostMapping("/update-status")
public String updateComplaintStatus(
        Authentication authentication,
        @RequestParam UUID complaintId,
        @RequestParam String status,
        RedirectAttributes redirectAttributes) {

    try {
        complaintService.updateWorkerComplaintStatus(
                authentication.getName(),
                complaintId,
                status
        );

        redirectAttributes.addFlashAttribute(
                "success", "Complaint status updated successfully!");

    } catch (IllegalArgumentException e) {

        redirectAttributes.addFlashAttribute(
                "error", e.getMessage());
    }

    return "redirect:/worker/dashboard";
}
}