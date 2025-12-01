package com.berke.todo.app;

import com.berke.todo.domain.Task;
import com.berke.todo.domain.Priority;
import com.berke.todo.domain.Status;
import com.berke.todo.repository.InMemoryTaskRepository;
import com.berke.todo.service.TaskService;
import com.berke.todo.util.TaskFormatter;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Locale;

public class CommandLineApp {

    private final TaskService service;
    private final Scanner scanner;

    public CommandLineApp() {
        this.service = new TaskService(new InMemoryTaskRepository());
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new CommandLineApp().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addTask();
                case "2" -> listTasks();
                case "3" -> updateTask();
                case "4" -> changeStatus();
                case "5" -> deleteTask();
                case "6" -> searchTasks();
                case "7" -> listPending();
                case "8" -> sortByCreated();
                case "9" -> sortByCompleted();
                case "0" -> running = false;
                default -> System.out.println("Invalid choice.");
            }
            System.out.println();
        }
        System.out.println("Exiting...");
    }

    private void printMenu() {
        System.out.println("""
                ===== Task Management Menu =====
                1 - Add Task
                2 - List Tasks
                3 - Update Task
                4 - Change Status
                5 - Delete Task
                6 - Search by Title
                7 - List Pending Tasks
                8 - Sort by Created Date
                9 - Sort by Completion Date
                0 - Exit
                Choice:""");
    }

    private void addTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        Priority priority = readPriority("Priority (LOW/MEDIUM/HIGH): ");

        try {
            Task created = service.create(title, description, priority);
            System.out.println("Created:");
            System.out.println(TaskFormatter.format(created));
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void listTasks() {
        List<Task> all = service.listAll();
        System.out.println(TaskFormatter.formatList(all));
    }

    private void updateTask() {
        int id = readInt("Task ID to update: ");
        Optional<Task> opt = service.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Not found: " + id);
            return;
        }

        System.out.print("New title (leave empty to skip): ");
        String newTitle = scanner.nextLine();

        System.out.print("New description (leave empty to skip): ");
        String newDescription = scanner.nextLine();

        Priority newPriority = readPriority("New priority (LOW/MEDIUM/HIGH, leave empty to skip): ", true);

        Optional<Task> updated = service.update(id,
                emptyToNull(newTitle),
                emptyToNull(newDescription),
                newPriority);

        System.out.println(updated.isPresent() ? TaskFormatter.format(updated.get()) : "Update failed.");
    }

    private void changeStatus() {
        int id = readInt("Task ID to change status: ");
        Status newStatus = readStatus("New status (PENDING/IN_PROGRESS/DONE): ");
        try {
            Optional<Task> changed = service.changeStatus(id, newStatus);
            System.out.println(changed.isPresent()
                    ? TaskFormatter.format(changed.get())
                    : "Not found: " + id);
        } catch (IllegalStateException e) {
            System.out.println("Business rule violation: " + e.getMessage());
        }
    }

    private void deleteTask() {
        int id = readInt("Task ID to delete: ");
        boolean ok = service.delete(id);
        System.out.println(ok ? ("Deleted: " + id) : ("Not found: " + id));
    }

    private void searchTasks() {
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine();
        List<Task> found = service.searchByTitle(keyword);
        System.out.println(TaskFormatter.formatList(found));
    }

    private void listPending() {
        List<Task> pending = service.listPending();
        System.out.println(TaskFormatter.formatList(pending));
    }

    private void sortByCreated() {
        List<Task> sorted = service.sortByCreatedDate();
        System.out.println(TaskFormatter.formatList(sorted));
    }

    private void sortByCompleted() {
        List<Task> sorted = service.sortByCompletionDate();
        System.out.println(TaskFormatter.formatList(sorted));
    }

    // Helpers
    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = scanner.nextLine();
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    private Priority readPriority(String prompt) {
        return readPriority(prompt, false);
    }

    private Priority readPriority(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (allowEmpty && s.isEmpty()) return null;

            switch (s) {
                case "low", "l", "1" -> { return Priority.LOW; }
                case "medium", "m", "2" -> { return Priority.MEDIUM; }
                case "high", "h", "3" -> { return Priority.HIGH; }
                default -> System.out.println("Invalid priority. Use LOW / MEDIUM / HIGH.");
            }
        }
    }

    private Status readStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            switch (s) {
                case "pending", "p", "1" -> { return Status.PENDING; }
                case "in_progress", "inprogress", "progress", "in progress", "ip", "2" -> { return Status.IN_PROGRESS; }
                case "done", "completed", "d", "3" -> { return Status.DONE; }
                default -> System.out.println("Invalid status. Use PENDING / IN_PROGRESS / DONE.");
            }
        }
    }

    private String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}