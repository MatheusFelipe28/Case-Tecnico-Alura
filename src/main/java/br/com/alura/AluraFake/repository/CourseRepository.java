package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
