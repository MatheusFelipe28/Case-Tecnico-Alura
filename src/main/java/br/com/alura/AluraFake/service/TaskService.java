package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.ENUM.TaskType;
import br.com.alura.AluraFake.dto.OptionDTO;
import br.com.alura.AluraFake.dto.TaskDTO;
import br.com.alura.AluraFake.entity.Course;
import br.com.alura.AluraFake.entity.Option;
import br.com.alura.AluraFake.entity.Task;
import br.com.alura.AluraFake.repository.CourseRepository;
import br.com.alura.AluraFake.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    public Task createTask(TaskDTO taskDTO) {
        // Validar curso
        Course course = validateCourse(taskDTO.getCourseId());

        // Validar statement
        validateStatement(taskDTO.getStatement(), course);

        // Validar ordem
        //validateOrderAndReorganize(course, taskDTO.getOrder());

        // Criar tarefa com base no tipo
        TaskDTO  task = new TaskDTO();
        task.setCourseId(taskDTO.getCourseId());
        task.setStatement(taskDTO.getStatement());
        task.setOrderIndex(taskDTO.getOrderIndex());
        task.setType_task(taskDTO.getType_task());

        if (taskDTO.getType_task() == TaskType.SINGLE_CHOICE || taskDTO.getType_task() == TaskType.MULTIPLE_CHOICE) {
            validateAndAddOptions(taskDTO, taskDTO.getOptions());
        }

        return taskRepository.save(task);
    }

    private Course validateCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    private void validateStatement(String statement, Course course) {
        if (statement.length() < 4 || statement.length() > 255) {
            throw new IllegalArgumentException("Statement must be between 4 and 255 characters");
        }

      /*  boolean statementExists = taskRepository.existsByCourseAndStatement(course, statement);
        if (statementExists) {
            throw new IllegalArgumentException("Duplicate statement in the course");
        } */
    }

   /* private void validateOrderAndReorganize(Course course, int order) {
        List<Task> tasks = taskRepository.findAllByCourseOrderByOrderAsc(course);

        for (Task task : tasks) {
            if (task.getOrder() >= order) {
                task.setOrder(task.getOrder() + 1);
                taskRepository.save(task);
            }
        }
    } */

    private void validateAndAddOptions(TaskDTO taskDTO, List<OptionDTO> options) {
        if (taskDTO.getType_task() == TaskType.SINGLE_CHOICE) {
            validateSingleChoiceOptions(options);
        } else if (taskDTO.getType_task() == TaskType.MULTIPLE_CHOICE) {
            validateMultipleChoiceOptions(options);
        }

        List<Option> taskOptions = options.stream()
                .map(opt -> new Option(opt.getId(), opt.getText(), opt.getIsCorrect()))
                .collect(Collectors.toList());

        taskDTO.setOptions(options);
    }

    private void validateSingleChoiceOptions(List<OptionDTO> options) {
        if (options.size() < 2 || options.size() > 5) {
            throw new IllegalArgumentException("Single choice tasks must have between 2 and 5 options");
        }

        long correctCount = options.stream().filter(OptionDTO::getIsCorrect).count();
        if (correctCount != 1) {
            throw new IllegalArgumentException("Single choice tasks must have exactly one correct option");
        }
    }

    private void validateMultipleChoiceOptions(List<OptionDTO> options) {
        if (options.size() < 3 || options.size() > 5) {
            throw new IllegalArgumentException("Multiple choice tasks must have between 3 and 5 options");
        }

        long correctCount = options.stream().filter(OptionDTO::getIsCorrect).count();
        long incorrectCount = options.size() - correctCount;

        if (correctCount < 2 || incorrectCount < 1) {
            throw new IllegalArgumentException("Multiple choice tasks must have at least 2 correct options and 1 incorrect option");
        }
    }
}

