package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
