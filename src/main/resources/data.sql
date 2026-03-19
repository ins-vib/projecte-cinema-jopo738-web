INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES
('Major,15','Tarragona','Oscars','43100');

INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES
('Avinguda dels Pins,5','Reus','Jack','43206');

INSERT INTO CINEMA(ADDRESS,CITY,NAME,POSTAL_CODE) VALUES
('Carrer dels plàtans,20','Cambrils','Nemo','43205');

INSERT INTO room (id, name, capacity, cinema_id) 
VALUES (10, 'Sala Nemo Estàndard', 20, 2);

-- Sales per al Cinema 1 (Oscars - Tarragona)
INSERT INTO room (name, capacity, cinema_id) VALUES 
('Sala Oscars 1', 150, 1),
('Sala Oscars 2', 100, 1),
('Sala Oscars IMAX', 250, 1);

-- Sales extres per al Cinema 2 (Jack - Reus) 
INSERT INTO room (name, capacity, cinema_id) VALUES 
('Sala Jack VIP', 30, 2),
('Sala Jack 2', 120, 2),
('Sala Jack Infantil', 80, 2);

-- Sales per al Cinema 3 (Nemo - Cambrils)
INSERT INTO room (name, capacity, cinema_id) VALUES 
('Sala Nemo 1', 90, 3),
('Sala Nemo 2', 90, 3),
('Sala Nemo 3D', 110, 3);

INSERT INTO movie (títol, duration_minutes, genere, descripcio, release_date) VALUES
-- ID 1: DUNE
('Dune: Part Two', 166, 'Ciència-ficció', 'En Paul Atreides s''uneix a Chani i als Fremen mentre busca venjança contra els conspiradors que van destruir la seva família.', '2024-03-01'),

-- ID 2: INSIDE OUT 2
('Inside Out 2', 96, 'Animació', 'Les emocions de la Riley tornen a la seva ment quan ella es converteix en adolescent i apareixen noves emocions inesperades.', '2024-06-14'),

-- ID 3: DEADPOOL 2
('Deadpool 2', 119, 'Acció / Comèdia', 'El mercenari mutat Wade Wilson reuneix un equip de mutants per protegir un jove amb habilitats sobrenaturals.', '2018-05-18');



INSERT INTO screening (screening_date_time, price, movie_id, room_id) VALUES
-- DUNE (id 1)
('2026-03-20T18:00',8.50,1,1),
('2026-03-20T21:00',9.50,1,1),
('2026-03-23T18:00',8.00,1,1),
('2026-04-22T20:00',7.50,1,1),
('2026-03-22T19:00',6.50,1,1),

-- INSIDE OUT 2 (ID=2)
('2026-03-20T18:00',8.50,2,1),
('2026-03-20T21:00',9.50,2,1),
('2026-03-23T18:00',8.00,2,1),
('2026-04-22T20:00',7.50,2,1),
('2026-03-22T19:00',6.50,2,1),

-- DEADPOOL 2 (ID=3)
('2026-03-20T18:00',8.50,3,1),
('2026-03-20T21:00',9.50,3,1),
('2026-03-23T18:00',8.00,3,1),
('2026-04-22T20:00',7.50,3,1),
('2026-03-22T19:00',6.50,3,1);


