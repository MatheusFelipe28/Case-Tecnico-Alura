package br.com.alura.AluraFake.controller;

import br.com.alura.AluraFake.dto.CourseListItemDTO;
import br.com.alura.AluraFake.entity.User;
import br.com.alura.AluraFake.repository.CourseRepository;
import br.com.alura.AluraFake.dto.NewCourseDTO;
import br.com.alura.AluraFake.entity.Course;
import br.com.alura.AluraFake.repository.UserRepository;
import br.com.alura.AluraFake.service.CourseService;
import br.com.alura.AluraFake.service.InstructorService;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final InstructorService instructorService;
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseRepository courseRepository, UserRepository userRepository, InstructorService instructorService, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @Transactional
    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {

        //Caso implemente o bonus, pegue o instrutor logado
        Optional<User> possibleAuthor = userRepository
                .findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor);

        if(possibleAuthor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("emailInstructor", "Usuário não é um instrutor"));
        }

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor.get());

        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/course/all")
    public ResponseEntity<List<CourseListItemDTO>> createCourse() {
        List<CourseListItemDTO> courses = courseRepository.findAll().stream()
                .map(CourseListItemDTO::new)
                .toList();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/course/publish")
    public ResponseEntity<?> publishCourse(@PathVariable("id") Long id) {
        try {
            courseService.publishCourse(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("publish", e.getMessage()));
        }
    }

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<?> getCoursesByInstructor(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(instructorService.getCoursesByInstructor(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("instructor", e.getMessage()));
        }
    }
}
