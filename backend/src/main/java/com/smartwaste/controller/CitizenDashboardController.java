package com.smartwaste.controller;

import com.smartwaste.service.CitizenDashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class CitizenDashboardController {

    private final CitizenDashboardService dashboardService;

    public CitizenDashboardController(CitizenDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/citizen/dashboard")
    public String citizenDashboard(Authentication authentication, Model model) {

        Map<String, Object> dashboardData =
                dashboardService.getDashboardData(authentication.getName());

        model.addAllAttributes(dashboardData);

        return "citizen/dashboard";
    }
}