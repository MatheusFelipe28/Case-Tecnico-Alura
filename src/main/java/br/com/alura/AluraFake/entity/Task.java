package br.com.alura.AluraFake.entity;

import br.com.alura.AluraFake.ENUM.TaskType;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "course_id", nullable = false)
    private Long courseId;

    @Length(min= 4, max= 255)
    private String Statement;

    private Integer orderIndex;

    private TaskType type_task;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Task() {}

    public Task(Long courseId, String statement, Integer orderIndex, TaskType type_task) {
        this.courseId = courseId;
        Statement = statement;
        this.orderIndex = orderIndex;
        this.type_task = type_task;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStatement() {
        return Statement;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public TaskType getType_task() {
        return type_task;
    }

    public void setType_task(TaskType type_task) {
        this.type_task = type_task;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
