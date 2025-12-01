package com.berke.todo.repository;

import com.berke.todo.domain.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    int generateId();

    Task save(Task task);

    Optional<Task> findById(int id);

    List<Task> findAll();

    boolean deleteById(int id);

    List<Task> findByTitleContains(String keywordNormalized);
}