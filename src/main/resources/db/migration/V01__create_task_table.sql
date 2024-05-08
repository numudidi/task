CREATE TABLE IF NOT EXISTS tasks (
    task_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    start_date datetime NULL,
    end_date datetime NULL,
    project VARCHAR(255) NULL,
    description LONGTEXT NULL,
    priority VARCHAR(255) NULL,
    file LONGBLOB NULL,
    created_by VARCHAR(255) NULL,
    created_date datetime NULL,
    PRIMARY KEY (task_id)
    );