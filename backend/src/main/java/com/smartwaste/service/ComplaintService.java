package com.smartwaste.service;
import java.util.UUID;
import com.smartwaste.entity.Complaint;
import com.smartwaste.entity.User;
import com.smartwaste.repository.ComplaintRepository;
import com.smartwaste.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintService(
            ComplaintRepository complaintRepository,
            UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void submitComplaint(
            String email,
            String complaintType,
            String description,
            String address) {

        User citizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found."));

        if (!"CITIZEN".equals(citizen.getRole())) {
            throw new IllegalArgumentException(
                    "Only citizens can submit complaints.");
        }

        if (complaintType == null || complaintType.isBlank()
                || description == null || description.isBlank()
                || address == null || address.isBlank()) {
            throw new IllegalArgumentException(
                    "Please fill in all required fields.");
        }

        Complaint complaint = new Complaint(
                complaintType,
                description,
                address,
                null,
                null,
                citizen);

        complaintRepository.save(complaint);
    }

    @Transactional(readOnly = true)
    public List<Complaint> getCitizenComplaints(String email) {

        User citizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found."));

        if (!"CITIZEN".equals(citizen.getRole())) {
            throw new IllegalArgumentException(
                    "Only citizens can view their complaints.");
        }

        return complaintRepository.findByCreatedById(citizen.getId());
    }
    @Transactional(readOnly = true)
public List<Complaint> getWorkerComplaints(String email) {

    User worker = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Worker not found."));

    if (!"WORKER".equals(worker.getRole())) {
        throw new IllegalArgumentException(
                "Only workers can view assigned complaints.");
    }

    return complaintRepository.findByAssignedWorkerId(worker.getId());
}
// Get all complaints for admin
@Transactional(readOnly = true)
public List<Complaint> getAllComplaints() {
    return complaintRepository.findAllWithCreator();
}

// Get active workers for assignment
@Transactional(readOnly = true)
public List<User> getActiveWorkers() {
    return userRepository.findByRole("WORKER")
            .stream()
            .filter(worker -> Boolean.TRUE.equals(worker.getActive()))
            .toList();
}

// Assign a complaint to a worker
@Transactional
public void assignComplaint(UUID complaintId, UUID workerId) {

    Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Complaint not found."));

    User worker = userRepository.findById(workerId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Worker not found."));

    if (!"WORKER".equals(worker.getRole())
            || !Boolean.TRUE.equals(worker.getActive())) {
        throw new IllegalArgumentException("Invalid or inactive worker.");
    }

    complaint.setAssignedWorker(worker);
    complaint.setStatus("ASSIGNED");

    complaintRepository.save(complaint);
}

// Worker updates complaint status
@Transactional
public void updateWorkerComplaintStatus(
        String email,
        UUID complaintId,
        String newStatus) {

    // Find the logged-in worker
    User worker = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new IllegalArgumentException("Worker not found."));

    if (!"WORKER".equals(worker.getRole())) {
        throw new IllegalArgumentException(
                "Only workers can update complaint status.");
    }

    // Find the complaint
    Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() ->
                    new IllegalArgumentException("Complaint not found."));

    // Verify that this complaint belongs to this worker
    if (complaint.getAssignedWorker() == null ||
            !complaint.getAssignedWorker().getId().equals(worker.getId())) {

        throw new IllegalArgumentException(
                "You are not authorized to update this complaint.");
    }

    // Allow only valid status transitions
    if ("IN_PROGRESS".equals(newStatus)
            && "ASSIGNED".equals(complaint.getStatus())) {

        complaint.setStatus("IN_PROGRESS");

    } else if ("RESOLVED".equals(newStatus)
            && "IN_PROGRESS".equals(complaint.getStatus())) {

        complaint.setStatus("RESOLVED");

    } else {
        throw new IllegalArgumentException(
                "Invalid complaint status update.");
    }

    complaintRepository.save(complaint);
}
}