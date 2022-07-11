CREATE TABLE `users_roles` (
   `user_id` INT(11) NOT NULL,
   `role_id` INT(11) NOT NULL,
   PRIMARY KEY (`user_id`, `role_id`) USING BTREE
)
    COLLATE='latin2_general_ci'
    ENGINE=InnoDB
;
