package com.kirov.goals.goals;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goals")
public class GoalController {
    private final GoalRepository goals;

    public GoalController(GoalRepository goals) {
        this.goals = goals;
    }

    @PostMapping
    public ResponseEntity<Goal> save(@RequestBody Goal goalDto) {
        Goal goal = Goal.create(goalDto.getTitle(), goalDto.getDueDate());
        return ResponseEntity.ok(this.goals.save(goal));
    }

    @PostMapping("/{goalId}/tasks")
    public ResponseEntity<Goal> addTask(@PathVariable Long goalId, @RequestBody Task taskDto) {
        Goal goal = this.getGoal(goalId);
        if(goal == null) return ResponseEntity.notFound().build();

        Task task = Task.create(taskDto.getTitle(), taskDto.getDueDate());
        goal.addTask(task);

        return ResponseEntity.ok(this.goals.save(goal));
    }

    @PatchMapping("/{goalId}/tasks/complete-all")
    public ResponseEntity<Goal> completeAllTasks(@PathVariable Long goalId) {
        Goal goal = this.getGoal(goalId);
        if(goal == null) return ResponseEntity.notFound().build();

        goal.completeAllTasks();
        return ResponseEntity.ok(this.goals.save(goal));
    }

    @PatchMapping("/{goalId}/tasks/{taskId}/complete")
    public ResponseEntity<Goal> completeTask(@PathVariable Long goalId, @PathVariable Long taskId) {
        Goal goal = this.getGoal(goalId);
        if(goal == null) return ResponseEntity.notFound().build();

        goal.completeTask(taskId);
        return ResponseEntity.ok(this.goals.save(goal));
    }

    private Goal getGoal(Long goalId) {
        return this.goals.findById(goalId).orElse(null);
    }
}
