package com.berke.todo.repository;

import com.berke.todo.domain.Task;
import com.berke.todo.util.TextNormalizer;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Integer, Task> store = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1000);

    @Override
    public int generateId() {
        return seq.incrementAndGet();
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == 0) {
            task.setId(generateId());
        }
        store.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(int id) {
        return store.remove(id) != null;
    }

    @Override
    public List<Task> findByTitleContains(String keywordNormalized) {
        String k = (keywordNormalized == null) ? "" : keywordNormalized;
        return store.values().stream()
                .filter(t -> t.getTitle() != null && TextNormalizer.normalize(t.getTitle()).contains(k))
                .collect(Collectors.toList());
    }
}