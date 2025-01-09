package com.kirov.goals.goals;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Goal class is a rich domain object that represents a goal in the system.
 * A goal is a high-level entity that tracks progress towards an objective,
 * defined by a title, a due date, and a status.
 * It is composed of multiple tasks that can be individually managed.
 * The class encapsulates both data and business logic,
 * adhering to the principles of object-oriented design.
 */
@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    private List<Task> tasks = new ArrayList<>();

    private Goal() {}

    public static Goal create(String title, LocalDate dueDate) {
        Goal goal = new Goal();
        goal.setTitle(title);
        goal.setDueDate(dueDate);
        goal.setStatus(Status.ACTIVE);
        return goal;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getProgress() {
        int completedTasks = (int) this.tasks.stream()
                .filter(Task::getCompleted)
                .count();
        return (int) (((double) completedTasks / tasks.size()) * 100);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        updateStatus();
    }

    public void completeTask(Long taskId) {
        Task task = getTask(taskId);
        if(task == null) return;

        task.markAsCompleted();
        updateStatus();
    }

    public void completeAllTasks() {
        for(Task task : getTasks()) {
            task.markAsCompleted();
        }
        updateStatus();
    }

    private Task getTask(Long taskId) {
        for(Task task : getTasks()) {
            Long compId = task.getId();
            if(compId.equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    private void updateStatus() {
        this.status = areTasksCompleted() ? Status.COMPLETED : Status.ACTIVE;
    }

    private boolean areTasksCompleted() {
        return this.tasks.stream().allMatch(Task::getCompleted);
    }
}
