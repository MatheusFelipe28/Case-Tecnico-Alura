package br.com.alura.AluraFake.dto;

import br.com.alura.AluraFake.ENUM.TaskType;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class TaskDTO {
    private Long courseId;
    private TaskType type_task;
    private String statement;
    private int orderIndex;
    private List<OptionDTO> options; // Apenas para SINGLE_CHOICE e MULTIPLE_CHOICE

    public TaskType getType_task() {
        return type_task;
    }

    public void setType_task(TaskType type_task) {
        this.type_task = type_task;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }
}


