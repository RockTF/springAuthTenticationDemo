CREATE TABLE `user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user_roles` (
    `user_id` INT NOT NULL,
    role` ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);


INSERT INTO `user` (`username`, `email`, `password`)
VALUES ('john_doe', 'john.doe@example.com', '{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m');

INSERT INTO `user_roles` (`user_id`, `role`)
VALUES (1, 'ROLE_ADMIN');

