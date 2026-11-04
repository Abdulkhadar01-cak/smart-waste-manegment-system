package com.smartwaste.service;

import com.smartwaste.entity.User;
import com.smartwaste.repository.ComplaintRepository;
import com.smartwaste.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class CitizenDashboardService {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;

    public CitizenDashboardService(
            UserRepository userRepository,
            ComplaintRepository complaintRepository) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDashboardData(String email) {

        User citizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found"));

        UUID citizenId = citizen.getId();

        long total = complaintRepository.countByCreatedById(citizenId);
        long pending = complaintRepository.countByCreatedByIdAndStatus(citizenId, "PENDING");
        long inProgress = complaintRepository.countByCreatedByIdAndStatus(citizenId, "IN_PROGRESS")
                + complaintRepository.countByCreatedByIdAndStatus(citizenId, "ASSIGNED");
        long completed = complaintRepository.countByCreatedByIdAndStatus(citizenId, "COMPLETED");

        var recentComplaints =
                complaintRepository.findTop5ByCreatedByIdOrderByCreatedAtDesc(citizenId);

        return Map.of(
                "citizen", citizen,
                "total", total,
                "pending", pending,
                "inProgress", inProgress,
                "completed", completed,
                "recentComplaints", recentComplaints
        );
    }
}