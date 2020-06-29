/* Populate tabla regiones */
INSERT INTO regiones (id, nome) VALUES (1, 'Sudamérica');
INSERT INTO regiones (id, nome) VALUES (2, 'Centroamérica');
INSERT INTO regiones (id, nome) VALUES (3, 'Norteamérica');
INSERT INTO regiones (id, nome) VALUES (4, 'Europa');
INSERT INTO regiones (id, nome) VALUES (5, 'Asia');
INSERT INTO regiones (id, nome) VALUES (6, 'Africa');
INSERT INTO regiones (id, nome) VALUES (7, 'Oceanía');
INSERT INTO regiones (id, nome) VALUES (8, 'Antártida');

/* Populate tabla clientes */
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(1, 'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(2, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(4, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(4, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(3, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(3, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(5, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(6, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(7, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clientes (region_id, nome, apelido, email, create_at) VALUES(8, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');


/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, enabled,nome,apelido,email) VALUES ('andres',	'$2a$10$wWvP8xBzMYQT7UgOldtthOVg3IP/CN21M1keOon9B.EHcj8mThhAW',	1, 'Andres','Guzmán','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, enabled,nome,apelido,email) VALUES ('admin',	'$2a$10$MSYO8kr8EkTnx7LMfAQuF.QB3rhAvgSskPq328/8skB3W6HVzzupC',	1, 'John','Doe','johnson@gmail.com');

INSERT INTO `roles` (nome) VALUES ('ROLE_USER');
INSERT INTO `roles` (nome) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);

/* Populate tabla productos */
INSERT INTO produtos (nome, preco, data_criacao, codigo, quantidade_estoque) VALUES('Panasonic Pantalla LCD', 259990, NOW(), '001', 98);
INSERT INTO produtos (nome, preco, data_criacao, codigo, quantidade_estoque) VALUES('Sony Camara digital DSC-W320B', 123490, NOW(), '003', 83);
INSERT INTO produtos (nome, preco, data_criacao, codigo, quantidade_estoque) VALUES('Apple iPod shuffle', 1499990, NOW(), '002', 94);
INSERT INTO produtos (nome, preco, data_criacao, codigo, quantidade_estoque) VALUES('Sony Notebook Z110', 37990, NOW(), '004', 98);
INSERT INTO produtos (nome, preco, data_criacao, codigo, quantidade_estoque) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW(), '005', 40);
INSERT INTO produtos (nome, preco, data_criacao, codigo, quantidade_estoque) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW(), '006', 73);
INSERT INTO produtos (nome, preco, data_criacao, codigo, quantidade_estoque) VALUES('Mica Comoda 5 Cajones', 299990, NOW(), '009', 847);

