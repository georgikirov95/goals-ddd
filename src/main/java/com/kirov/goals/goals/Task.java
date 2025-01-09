package com.kirov.goals.goals;

import jakarta.persistence.*;

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

    public static Task create(String title) {
        Task task = new Task();
        task.setTitle(title);
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

    public void markAsCompleted() {
        this.completed = true;
    }
}
