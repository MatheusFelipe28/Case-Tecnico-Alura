package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.ENUM.Status;
import br.com.alura.AluraFake.entity.Course;
import br.com.alura.AluraFake.repository.CourseRepository;
import br.com.alura.AluraFake.entity.Task;
import br.com.alura.AluraFake.repository.TaskRepository;
import br.com.alura.AluraFake.ENUM.TaskType;
import br.com.alura.AluraFake.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public CourseService(CourseRepository courseRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void publishCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new ValidationException("Course must be in BUILDING status to be published.");
        }

        List<Task> tasks = taskRepository.findByCourseId(courseId);
        validateTasksForPublishing(tasks);

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());
        courseRepository.save(course);
    }

    private void validateTasksForPublishing(List<Task> tasks) {
        if (!hasAllTaskTypes(tasks)) {
            throw new ValidationException("Course must have at least one task of each type.");
        }

        if (!isSequentialOrder(tasks)) {
            throw new ValidationException("Tasks must have sequential order.");
        }
    }

    private boolean hasAllTaskTypes(List<Task> tasks) {
        return  tasks.stream().anyMatch(t -> t.getType_task() == TaskType.OPEN_TEXT) &&
                tasks.stream().anyMatch(t -> t.getType_task() == TaskType.SINGLE_CHOICE) &&
                tasks.stream().anyMatch(t -> t.getType_task() == TaskType.MULTIPLE_CHOICE);
    }

    private boolean isSequentialOrder(List<Task> tasks) {
        List<Integer> orders = tasks.stream()
                .map(Task::getOrderIndex)
                .sorted()
                .toList();

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }
}
