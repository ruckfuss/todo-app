package com.berke.todo.service;

import com.berke.todo.domain.Task;
import com.berke.todo.domain.Priority;
import com.berke.todo.domain.Status;
import com.berke.todo.repository.TaskRepository;
import com.berke.todo.util.TextNormalizer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task create(String title, String description, Priority priority) {
        validateTitle(title);
        Task task = new Task(
                0,
                title.trim(),
                sanitize(description),
                (priority == null) ? Priority.MEDIUM : priority,
                Status.PENDING,
                LocalDateTime.now()
        );
        return repository.save(task);
    }

    public Optional<Task> update(int id, String newTitle, String newDescription, Priority newPriority) {
        Optional<Task> opt = repository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        Task task = opt.get();
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            validateTitle(newTitle);
            task.setTitle(newTitle.trim());
        }
        if (newDescription != null) {
            task.setDescription(newDescription.trim());
        }
        if (newPriority != null) {
            task.setPriority(newPriority);
        }
        repository.save(task);
        return Optional.of(task);
    }

    public Optional<Task> changeStatus(int id, Status newStatus) {
        Optional<Task> opt = repository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        Task task = opt.get();
        task.changeStatus(newStatus);
        repository.save(task);
        return Optional.of(task);
    }

    public boolean delete(int id) {
        return repository.deleteById(id);
    }

    public Optional<Task> findById(int id) {
        return repository.findById(id);
    }

    public List<Task> listAll() {
        return repository.findAll();
    }

    public List<Task> searchByTitle(String keyword) {
        String k = TextNormalizer.normalize(keyword);
        return repository.findByTitleContains(k);
    }

    // ✅ Düzeltilmiş: sadece PENDING görevler
    public List<Task> listPending() {
        return repository.findAll().stream()
                .filter(t -> t.getStatus() == Status.PENDING)
                .toList();
    }

    public List<Task> sortByCreatedDate() {
        return repository.findAll().stream()
                .sorted((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
                .toList();
    }

    public List<Task> sortByCompletionDate() {
        return repository.findAll().stream()
                .sorted((a, b) -> {
                    if (a.getCompletedAt() == null && b.getCompletedAt() == null) return 0;
                    if (a.getCompletedAt() == null) return 1;
                    if (b.getCompletedAt() == null) return -1;
                    return a.getCompletedAt().compareTo(b.getCompletedAt());
                })
                .toList();
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (title.trim().length() > 200) {
            throw new IllegalArgumentException("Title cannot exceed 200 characters.");
        }
    }

    private String sanitize(String s) {
        return (s == null) ? "" : s.trim();
    }
}