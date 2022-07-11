INSERT INTO `resource` (`id`, `name`, `description`, `updated_at`, `created_at`, `version`, `is_active`, `path_pattern`, `role_type`)
VALUES
(1, 'No auth', 'No auth', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/auth/ping/no-auth', 'ANY'),
(2, 'Guest auth', 'Guest auth', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/auth/ping/guest', 'ANY'),
(3, 'Viewer auth', 'Viewer auth', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/auth/ping/viewer', 'ANY'),
(4, 'Admin auth', 'Admin auth', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/auth/ping/admin', 'ANY'),
(5, 'Super admin auth', 'Super admin auth', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/auth/ping/super-admin', 'ANY'),
(6, 'Users resource extend', 'Users resource extend', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/users/**', 'ANY'),
(7, 'Resources resource extend', 'Resources resource extend', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/resources/**', 'ANY'),
(8, 'Role resource extend', 'Role resource extend', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/roles/**', 'ANY'),
(9, 'Users resource extend', 'Users resource', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/users', 'ANY'),
(10, 'Resources resource extend', 'Resources resource', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/resources', 'ANY'),
(11, 'Role resource extend', 'Role resource', '2022-05-29 10:21:29', '2022-05-29 10:21:29', 1, b'1', '/roles', 'ANY');
