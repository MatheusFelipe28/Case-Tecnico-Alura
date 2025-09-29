package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.ENUM.Role;
import br.com.alura.AluraFake.ENUM.Status;
import br.com.alura.AluraFake.entity.Course;
import br.com.alura.AluraFake.entity.User;
import br.com.alura.AluraFake.repository.CourseRepository;
import br.com.alura.AluraFake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public InstructorService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<Course> getCoursesByInstructor(Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        if (!instructor.getRole().equals(Role.INSTRUCTOR)) {
            throw new IllegalArgumentException("User is not an instructor");
        }

        List<Course> courses = courseRepository.findByInstructor(instructor);

        long publishedCount = courses.stream()
                .filter(course -> course.getStatus().equals(Status.PUBLISHED))
                .count();

        List<Course> courseReports = courses.stream()
                .map(course -> new Course(
                        course.getId(),
                        course.getTitle(),
                        course.getStatus(),
                        course.getPublishedAt()
                ))
                .collect(Collectors.toList());

             return courseReports;
    }

}
