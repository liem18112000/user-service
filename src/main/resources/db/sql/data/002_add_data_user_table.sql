INSERT INTO `user` (`id`, `name`, `username`, `password`, `description`, `updated_at`, `created_at`, `version`, `is_active`, `locked`, `expired`)
VALUES
       (1, 'Super admin user', 'superadmin001', 'NOOP@superadmin001', 'super admin account', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', b'0', b'0'),
       (2, 'Admin user', 'admin001', 'NOOP@admin001', 'admin account', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', b'0', b'0'),
       (3, 'Viewer user', 'viewer001', 'NOOP@viewer001', 'viewer account', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', b'0', b'0'),
       (4, 'Guest user', 'guest0001', 'NOOP@guest0001', 'guest account', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', b'0', b'0');
