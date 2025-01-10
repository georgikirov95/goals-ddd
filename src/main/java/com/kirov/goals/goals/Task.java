package com.kirov.goals.goals;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * The Task class represents a rich domain object for individual tasks in the system.
 * It encapsulates both the state and behavior of a task,
 * adhering to the principles of object-oriented design.
 */
 @Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean completed = false;
    private LocalDate dueDate;

    public static Task create(String title, LocalDate dueDate) {
        Task task = new Task();
        task.setTitle(title);
        task.setDueDate(dueDate);
        return task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if(dueDate.isBefore(LocalDate.now())) throw new RuntimeException("Due date is in the past.");
        this.dueDate = dueDate;
    }

    public void markAsCompleted() {
        this.completed = true;
    }
}
