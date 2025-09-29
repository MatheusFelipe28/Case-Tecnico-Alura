package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.entity.Course;
import br.com.alura.AluraFake.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>{
    List<Course> findByInstructor(User instructor);
}
