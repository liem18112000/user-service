CREATE TABLE `user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL DEFAULT 'User' COLLATE 'utf8_unicode_ci',
    `username` VARCHAR(255) NOT NULL COLLATE 'utf8_unicode_ci',
    `password` VARCHAR(255) NOT NULL COLLATE 'utf8_unicode_ci',
    `description` TEXT NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `updated_at` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `created_at` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `version` INT(11) NULL DEFAULT '1',
    `is_active` BIT(1) NULL DEFAULT b'1',
    `locked` BIT(1) NULL DEFAULT b'0',
    expired BIT(1) NULL DEFAULT b'0',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uc_user_password` (`password`) USING BTREE,
    UNIQUE INDEX `uc_user_username` (`username`) USING BTREE
)
    COLLATE='utf8_unicode_ci'
    ENGINE=InnoDB
;
