CREATE TABLE `resource`
(
    id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `description` TEXT         NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    updated_at    VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
    created_at    VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
    version       INT          NULL DEFAULT '1',
    is_active     BIT(1)       NULL DEFAULT b'1',
    path_pattern  VARCHAR(255) NOT NULL,
    role_type     VARCHAR(10)  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX uc_resource_path_pattern (path_pattern) USING BTREE
)
    COLLATE='utf8_unicode_ci'
    ENGINE=InnoDB
;