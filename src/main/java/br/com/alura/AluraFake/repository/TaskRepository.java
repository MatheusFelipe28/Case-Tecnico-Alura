package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.entity.Course;
import br.com.alura.AluraFake.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCourseId(Long courseId);

}
