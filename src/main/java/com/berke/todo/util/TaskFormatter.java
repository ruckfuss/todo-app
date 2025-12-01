package com.berke.todo.util;

import com.berke.todo.domain.Task;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class TaskFormatter {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withLocale(Locale.forLanguageTag("tr-TR"));

    private TaskFormatter() {}

    public static String format(Task t) {
        String created = (t.getCreatedAt() == null) ? "-" : t.getCreatedAt().format(FMT);
        String completed = (t.getCompletedAt() == null) ? "-" : t.getCompletedAt().format(FMT);
        return "Task{id=" + t.getId()
                + ", title='" + safe(t.getTitle()) + '\''
                + ", priority=" + t.getPriority()
                + ", status=" + t.getStatus()
                + ", createdAt=" + created
                + ", completedAt=" + completed
                + "}";
    }

    public static String formatList(List<Task> list) {
        if (list == null || list.isEmpty()) return "No tasks.";
        return list.stream().map(TaskFormatter::format).collect(Collectors.joining(System.lineSeparator()));
    }

    private static String safe(String s) { return (s == null) ? "" : s; }
}