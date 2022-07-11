CREATE TABLE `role`
(
    id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `description` TEXT         NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    updated_at    VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
    created_at    VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
    version       INT          NULL DEFAULT '1',
    is_active     BIT(1)       NULL DEFAULT b'1',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uc_role_name` (`name`) USING BTREE
)
    COLLATE='utf8_unicode_ci'
    ENGINE=InnoDB
;