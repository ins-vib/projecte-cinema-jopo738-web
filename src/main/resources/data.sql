-- 1. CINEMES
INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES ('Major,15','Tarragona','Oscars','43100');
INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES ('Avinguda dels Pins,5','Reus','Jack','43206');
INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES ('Carrer dels plàtans,20','Cambrils','Nemo','43205');

-- 2. SALES 
INSERT INTO room (name, capacity, cinema_id) VALUES ('Sala Oscars 1', 150, 1);
INSERT INTO room (name, capacity, cinema_id) VALUES ('Sala Jack VIP', 30, 2);
INSERT INTO room (name, capacity, cinema_id) VALUES ('Sala Nemo 1', 90, 3);

-- 3. PEL·LÍCULES 
INSERT INTO movie (titol, duration_minutes, genere, descripcio, release_date) VALUES
('Dune: Part Two', 166, 'Ciència-ficció', 'En Paul Atreides...', '2024-03-01'),
('Inside Out 2', 96, 'Animació', 'Les emocions de...', '2024-06-14'),
('Deadpool 2', 119, 'Acció / Comèdia', 'El mercenari...', '2018-05-18');

-- 4. PASSIS 
INSERT INTO screening (screening_date_time, price, movie_id, room_id) VALUES
('2026-04-20T18:00', 8.50, 1, 1),
('2026-04-20T21:00', 9.50, 1, 1),
('2026-04-20T18:00', 8.50, 2, 1),
('2026-04-20T21:00', 9.50, 2, 1),
('2026-04-20T18:00', 8.50, 3, 1);