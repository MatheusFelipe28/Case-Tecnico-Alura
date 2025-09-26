CREATE TABLE tasks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  course_id BIGINT NOT NULL,
  type VARCHAR(50) NOT NULL, -- OPEN_TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE
  statement VARCHAR(255) NOT NULL,
  order_index INT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  discriminator VARCHAR(50),
  CONSTRAINT fk_task_course FOREIGN KEY (course_id) REFERENCES course(id),
  CONSTRAINT uq_course_statement UNIQUE (course_id, statement)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;


