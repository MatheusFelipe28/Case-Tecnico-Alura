CREATE TABLE tasks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  course_id BIGINT NOT NULL,
  type VARCHAR(50) NOT NULL, -- OPEN_TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE
  statement VARCHAR(255) NOT NULL,
  order_index INT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  discriminator VARCHAR(50),
  CONSTRAINT fk_task_course FOREIGN KEY (course_id) REFERENCES courses(id),
  CONSTRAINT uq_course_statement UNIQUE (course_id, statement)
);

CREATE INDEX idx_task_course ON tasks(course_id);

CREATE TABLE choice_options (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  task_id BIGINT NOT NULL,
  option_text VARCHAR(80) NOT NULL,
  is_correct BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_option_task FOREIGN KEY (task_id) REFERENCES tasks(id)
);

