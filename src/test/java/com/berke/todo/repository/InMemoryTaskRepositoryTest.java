package com.berke.todo.repository;

import com.berke.todo.domain.Priority;
import com.berke.todo.domain.Status;
import com.berke.todo.domain.Task;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskRepositoryTest {

    @Test
    void saveAndFindByIdWorks() {
        InMemoryTaskRepository repo = new InMemoryTaskRepository();
        Task t = new Task(0, "Title", "Desc", Priority.MEDIUM, Status.PENDING, null);
        repo.save(t);

        Optional<Task> found = repo.findById(t.getId());
        assertTrue(found.isPresent());
        assertEquals("Title", found.get().getTitle());
    }

    @Test
    void deleteByIdRemovesTask() {
        InMemoryTaskRepository repo = new InMemoryTaskRepository();
        Task t = new Task(0, "Title", "Desc", Priority.LOW, Status.PENDING, null);
        repo.save(t);

        boolean deleted = repo.deleteById(t.getId());
        assertTrue(deleted);
        assertTrue(repo.findById(t.getId()).isEmpty());
    }

    @Test
    void findByTitleContainsWorks() {
        InMemoryTaskRepository repo = new InMemoryTaskRepository();
        Task t = new Task(0, "Shopping", "Buy milk", Priority.HIGH, Status.PENDING, null);
        repo.save(t);

        List<Task> results = repo.findByTitleContains("shopping");
        assertFalse(results.isEmpty());
        assertEquals("Shopping", results.get(0).getTitle());
    }
}