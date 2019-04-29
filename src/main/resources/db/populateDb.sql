INSERT INTO application_user VALUES ('15ba3454-65e2-439c-8519-9ba135cf97b9', 'cus.paul@yahoo.com', '$2a$12$7vR732layHAo0h5zYZy4rOX2gb7F7bHHEexES00dkoGOQ7bhR4Lrm', 'STUDENT', 'paul')
  , ('15ba3454-65e2-439c-8519-9ba135cf97b4', 'alex.baies@yahoo.com', '$2a$12$7vR732layHAo0h5zYZy4rOX2gb7F7bHHEexES00dkoGOQ7bhR4Lrm', 'STUDENT', 'alex')
  , ('15ba3454-65e2-439c-8519-9ba135cf97b5', 'camelia.serban@yahoo.com', '$2a$12$7vR732layHAo0h5zYZy4rOX2gb7F7bHHEexES00dkoGOQ7bhR4Lrm', 'PROFESSOR', 'cserban');

INSERT INTO student (id, name, user_id) VALUES ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1b', 'Paul Cus', '15ba3454-65e2-439c-8519-9ba135cf97b9');
INSERT INTO student (id, name, user_id) VALUES ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1d', 'Alex Baies', '15ba3454-65e2-439c-8519-9ba135cf97b4');

INSERT INTO professor (id, name, user_id) VALUES ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1c', 'Camelia Serban', '15ba3454-65e2-439c-8519-9ba135cf97b5');
INSERT INTO subject VALUES ('15ba3454-65e2-439c-8519-9ba135cf97b1', 'MAP');

INSERT INTO topic VALUES ('15ba3454-65e2-439c-8519-9ba135cf97b2', 'Java', '15ba3454-65e2-439c-8519-9ba135cf97b1');
INSERT INTO topic VALUES ('15ba3454-65e2-439c-8519-9ba135cf97b3', 'C#', '15ba3454-65e2-439c-8519-9ba135cf97b1');
