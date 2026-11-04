package com.smartwaste.service;

import com.smartwaste.repository.ComplaintRepository;
import com.smartwaste.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;

    public AdminDashboardService(
            UserRepository userRepository,
            ComplaintRepository complaintRepository) {

        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
    }

    public Map<String, Long> getDashboardStatistics() {

        Map<String, Long> statistics = new LinkedHashMap<>();

        // User statistics
        statistics.put("totalCitizens",
                userRepository.countByRole("CITIZEN"));

        statistics.put("totalWorkers",
                userRepository.countByRole("WORKER"));

        statistics.put("activeWorkers",
                userRepository.countByRoleAndActiveTrue("WORKER"));

        // Complaint statistics
        statistics.put("totalComplaints",
                complaintRepository.count());

        statistics.put("pendingComplaints",
                complaintRepository.countByStatus("PENDING"));

        statistics.put("assignedComplaints",
                complaintRepository.countByStatus("ASSIGNED"));

        statistics.put("inProgressComplaints",
                complaintRepository.countByStatus("IN_PROGRESS"));

        statistics.put("resolvedComplaints",
                complaintRepository.countByStatus("RESOLVED"));

        statistics.put("rejectedComplaints",
                complaintRepository.countByStatus("REJECTED"));

        return statistics;
    }
}