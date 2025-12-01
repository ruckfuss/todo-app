package com.berke.todo.service;

import com.berke.todo.domain.Priority;
import com.berke.todo.domain.Status;
import com.berke.todo.domain.Task;
import com.berke.todo.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void createTaskWorks() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task t = service.create("Test", "Desc", Priority.HIGH);
        assertEquals("Test", t.getTitle());
        assertEquals(Priority.HIGH, t.getPriority());
        assertEquals(Status.PENDING, t.getStatus());
        assertNotEquals(0, t.getId());
    }

    @Test
    void changeStatusToDoneSetsCompletedAt() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task t = service.create("Test", "Desc", Priority.MEDIUM);
        Optional<Task> changed = service.changeStatus(t.getId(), Status.DONE);
        assertTrue(changed.isPresent());
        assertNotNull(changed.get().getCompletedAt());
    }

    @Test
    void cannotRevertDoneTask() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task t = service.create("Test", "Desc", Priority.LOW);
        service.changeStatus(t.getId(), Status.DONE);
        assertThrows(IllegalStateException.class,
                () -> service.changeStatus(t.getId(), Status.PENDING));
    }

    @Test
    void listPendingOnlyReturnsPending() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task pendingTask = service.create("Pending", "Desc", Priority.LOW);
        Task activeTask = service.create("Active", "Desc", Priority.HIGH);
        service.changeStatus(activeTask.getId(), Status.IN_PROGRESS);

        List<Task> pending = service.listPending();
        assertTrue(pending.contains(pendingTask));
        assertFalse(pending.contains(activeTask));
        assertTrue(pending.stream().allMatch(t -> t.getStatus() == Status.PENDING));
    }
}