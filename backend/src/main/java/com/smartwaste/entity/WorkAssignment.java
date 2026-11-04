package com.smartwaste.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * WorkAssignment Entity
 * 
 * Maps to database table 'work_assignments'.
 * Links an open Complaint to an assigned field worker, tracking assignment and completion timestamps.
 */
@Entity
@Table(name = "work_assignments")
public class WorkAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false, foreignKey = @ForeignKey(name = "fk_assignment_complaint"))
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false, foreignKey = @ForeignKey(name = "fk_assignment_worker"))
    private User worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", foreignKey = @ForeignKey(name = "fk_assignment_assigner"))
    private User assignedBy;

    @Column(name = "assigned_at", nullable = false, updatable = false)
    private Instant assignedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    public WorkAssignment() {
    }

    public WorkAssignment(Complaint complaint, User worker, User assignedBy) {
        this.complaint = complaint;
        this.worker = worker;
        this.assignedBy = assignedBy;
    }

    @PrePersist
    protected void onCreate() {
        if (this.assignedAt == null) {
            this.assignedAt = Instant.now();
        }
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
