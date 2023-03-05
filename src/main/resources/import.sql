/* Populate tables */
INSERT INTO usuarios (dni, nombre, apellido, fecha_nacimiento, nacionalidad, contrasena, tipo_usuario) VALUES(45372772, 'Leo', 'Soto', '2004/04/28', 'Argentina', '123', 'ADMIN');
INSERT INTO usuarios (dni, nombre, apellido, fecha_nacimiento, nacionalidad, contrasena, tipo_usuario) VALUES(45372773, 'Ariel', 'Impala', '2000/08/14', 'England', '321', 'HUESPED');

/* ingresar usuarios con sus roles */
INSERT INTO users (dni, contrasena, enabled) VALUES('45372772', '$2a$10$SF9nLWNfIcN8R7C2gdsuLOvfla1KxrA.8rcfuny9kqwXSYlsdQ8O.', 1);
INSERT INTO users (dni, contrasena, enabled) VALUES('45372773', '$2a$10$oj.lSOAsjsNstov7ixHWeuoBhAyWZhYIaUFuWtqle8vf7ezBP8T3i', 1);

INSERT INTO roles (user_dni, rol) VALUES('45372773', 'ROLE_HUESPED');
INSERT INTO roles (user_dni, rol) VALUES('45372772', 'ROLE_HUESPED');
INSERT INTO roles (user_dni, rol) VALUES('45372772', 'ROLE_ADMIN');