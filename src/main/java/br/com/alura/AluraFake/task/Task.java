package br.com.alura.AluraFake.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long courseId;

    @Length(min= 4, max= 255)
    private String Statement;

    private Integer orderIndex;

    private Type type_task;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Task() {}

    public Task(Long courseId, String statement, Integer orderIndex, Type type_task) {
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

    public Type getType_task() {
        return type_task;
    }

    public void setType_task(Type type_task) {
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
