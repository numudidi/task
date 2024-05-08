CREATE TABLE task_assignment
(
    id VARCHAR(255) NOT NULL,
    task_id VARCHAR(255) NULL,
    user_id VARCHAR(255) NULL,
    assigned_by VARCHAR(255) NULL,
    PRIMARY KEY (id)
);
