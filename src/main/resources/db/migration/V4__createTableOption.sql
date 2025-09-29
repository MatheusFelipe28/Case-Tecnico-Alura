
CREATE TABLE options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    activity_id BIGINT NOT NULL,
    option_text VARCHAR(80) NOT NULL,
    is_correct BOOLEAN NOT NULL,
    FOREIGN KEY (activity_id) REFERENCES activities(id)
);
