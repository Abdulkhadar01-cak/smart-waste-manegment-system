package com.smartwaste.controller;

import com.smartwaste.service.AdminDashboardService;
import com.smartwaste.service.ComplaintService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminDashboardService adminDashboardService;
    private final ComplaintService complaintService;

    public AdminController(
            AdminDashboardService adminDashboardService,
            ComplaintService complaintService) {

        this.adminDashboardService = adminDashboardService;
        this.complaintService = complaintService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {

        Map<String, Long> statistics =
                adminDashboardService.getDashboardStatistics();

        model.addAttribute("stats", statistics);

        model.addAttribute(
                "complaints",
                complaintService.getAllComplaints()
        );

        model.addAttribute(
                "workers",
                complaintService.getActiveWorkers()
        );

        return "admin/dashboard";
    }
}