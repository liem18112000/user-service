CREATE TABLE refresh_token
(
    id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    `description` TEXT         NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
    updated_at    VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
    created_at    VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
    version       INT          NULL DEFAULT '1',
    is_active     BIT(1)       NULL DEFAULT b'1',
    user_id       BIGINT(20)   NOT NULL,
    token         VARCHAR(255) NOT NULL,
    expired_at    datetime     NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    CONSTRAINT uc_refresh_token_token UNIQUE (token),
    CONSTRAINT FK_REFRESH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id)
);