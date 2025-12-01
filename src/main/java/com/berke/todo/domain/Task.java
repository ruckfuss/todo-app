package com.berke.todo.domain;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public Task(int id, String title, String description, Priority priority, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = (priority == null) ? Priority.MEDIUM : priority;
        this.status = (status == null) ? Status.PENDING : status;
        this.createdAt = (createdAt == null) ? LocalDateTime.now() : createdAt;
        this.completedAt = (this.status == Status.DONE) ? LocalDateTime.now() : null;
    }

    public void changeStatus(Status newStatus) {
        if (this.status == Status.DONE && newStatus != Status.DONE) {
            throw new IllegalStateException("Completed tasks cannot be reverted.");
        }
        this.status = newStatus;
        if (newStatus == Status.DONE) {
            this.completedAt = LocalDateTime.now();
        }
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}