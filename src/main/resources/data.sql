-- 1. CINEMES
INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES ('Major,15','Tarragona','Oscars','43100');
INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES ('Avinguda dels Pins,5','Reus','Jack','43206');
INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES ('Carrer dels plàtans,20','Cambrils','Nemo','43205');

-- 2. SALES 
INSERT INTO room (name, capacity, cinema_id) VALUES ('Sala Oscars 1', 150, 1);
INSERT INTO room (name, capacity, cinema_id) VALUES ('Sala Jack VIP', 30, 2);
INSERT INTO room (name, capacity, cinema_id) VALUES ('Sala Nemo 1', 90, 3);

-- 3. PEL·LÍCULES 
INSERT INTO movie (titol, duration_minutes, descripcio, release_date) 
VALUES 
('Dune: Part Two', 166, 'En Paul Atreides...', '2024-03-01'), 
('Inside Out 2', 96, 'Les emocions de...', '2024-06-14'), 
('Deadpool 2', 119, 'El mercenari...', '2018-05-18');

-- 4. PASSIS 
INSERT INTO screening (screening_date_time, price, movie_id, room_id) VALUES
('2026-04-20T18:00', 8.50, 1, 1),
('2026-04-20T21:00', 9.50, 1, 2),
('2026-04-20T18:00', 8.50, 2, 1),
('2026-04-20T21:00', 9.50, 2, 1),
('2026-04-20T18:00', 8.50, 3, 1);

INSERT INTO MENJAR (NOM, PREU) VALUES ('Crispetes Petites', 5.00);
INSERT INTO MENJAR (NOM, PREU) VALUES ('Crispetes Grans', 7.50);
INSERT INTO MENJAR (NOM, PREU) VALUES ('Refresc Mitjà', 3.50);
INSERT INTO MENJAR (NOM, PREU) VALUES ('Refresc Gran', 4.50);
INSERT INTO MENJAR (NOM, PREU) VALUES ('MENÚ INDIVIDUAL (Crispetes P + Refresc)', 8.00);
INSERT INTO MENJAR (NOM, PREU) VALUES ('MENÚ DUO (2 Crispetes M + 2 Refrescs)', 15.00);

-- Primer netegem per evitar duplicats si cal
-- 1. Netegem per evitar duplicats en reiniciar


-- 2. Inserim els gèneres (el que tu has posat)
INSERT INTO genere (id, nom) VALUES (1, 'Acció'), (2, 'Comèdia'), (3, 'Drama'), (4, 'Aventura'), (5, 'Thriller'), (6, 'Ciència-ficció');

-- -- 3. Inserim les pelis SENSE la columna 'genere'
-- INSERT INTO movie (id, titol, duration_minutes, descripcio, release_date) 
-- VALUES (1, 'Dune: Part Two', 166, 'En Paul Atreides...', '2024-03-01');

-- -- 4. Creem la connexió (Això és el nou!)
-- INSERT INTO movie_genere (movie_id, genere_id) VALUES (1, 6); -- Dune és Ciència-ficció (ID 6)

-- Creació dels usuaris (Taula users)
-- L'estat '1' indica que l'usuari està actiu
INSERT INTO users (username, password, enabled) VALUES 
('adminexam', '$2a$10$8.UnVuG9HHgffUDAlk8q7Ou5f2L99v7jLp.tB.8YI.2G.y5Wf9Y2u', 1),
('clientexam', '$2a$10$8.UnVuG9HHgffUDAlk8q7Ou5f2L99v7jLp.tB.8YI.2G.y5Wf9Y2u', 1);

-- Assignació de rols (Taula authorities o roles)
INSERT INTO authorities (username, authority) VALUES 
('adminexam', 'ROLE_ADMIN'),
('clientexam', 'ROLE_CLIENT');