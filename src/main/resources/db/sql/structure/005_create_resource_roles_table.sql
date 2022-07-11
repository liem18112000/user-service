CREATE TABLE `resources_roles` (
   `resource_id` INT(11) NOT NULL,
   `role_id` INT(11) NOT NULL,
   PRIMARY KEY (`resource_id`, `role_id`) USING BTREE
)
    COLLATE='utf8_unicode_ci'
    ENGINE=InnoDB
;
