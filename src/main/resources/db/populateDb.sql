INSERT INTO application_user VALUES
    ('15ba3454-65e2-439c-8519-9ba135cf97b9', 'cus.paul@yahoo.com', false, '$2a$12$7vR732layHAo0h5zYZy4rOX2gb7F7bHHEexES00dkoGOQ7bhR4Lrm', 'STUDENT', 'paul'),
    ('15ba3454-65e2-439c-8519-9ba135cf97b4', 'alex.baies@yahoo.com', false, '$2a$12$7vR732layHAo0h5zYZy4rOX2gb7F7bHHEexES00dkoGOQ7bhR4Lrm', 'STUDENT', 'alex'),
    ('15ba3454-65e2-439c-8519-9ba135cf97b5', 'camelia.serban@yahoo.com', false, '$2a$12$7vR732layHAo0h5zYZy4rOX2gb7F7bHHEexES00dkoGOQ7bhR4Lrm', 'PROFESSOR', 'cserban'),
    ('15ba3454-65e2-439c-8519-9ba135cf97b6', 'ioan.lazar@yahoo.com', false,'$2a$12$7vR732layHAo0h5zYZy4rOX2gb7F7bHHEexES00dkoGOQ7bhR4Lrm', 'PROFESSOR', 'ilazar');

INSERT INTO student (id, name, user_id) VALUES
    ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1b', 'Paul Cus', '15ba3454-65e2-439c-8519-9ba135cf97b9'),
    ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1d', 'Alex Baies', '15ba3454-65e2-439c-8519-9ba135cf97b4');

INSERT INTO professor (id, name, user_id) VALUES
    ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1c', 'Camelia Serban', '15ba3454-65e2-439c-8519-9ba135cf97b5'),
    ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1d', 'Ioan Lazar', '15ba3454-65e2-439c-8519-9ba135cf97b6');

INSERT INTO subject VALUES
    ('15ba3454-65e2-439c-8519-9ba135cf97b1', 'MAP'),
    ('15ba3454-65e2-439c-8519-9ba135cf97b2', 'PDM');

INSERT INTO professor_subject (professor_id, subject_id) VALUES
    ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1c','15ba3454-65e2-439c-8519-9ba135cf97b1'),
    ('0bba19e9-3c15-43ca-8eb5-3124c0f80e1d','15ba3454-65e2-439c-8519-9ba135cf97b2');

INSERT INTO topic VALUES
    ('15ba3454-65e2-439c-8519-9ba135cf97ba', 'Java', '15ba3454-65e2-439c-8519-9ba135cf97b1'),
    ('15ba3454-65e2-439c-8519-9ba135cf97bb', 'C#', '15ba3454-65e2-439c-8519-9ba135cf97b1'),
    ('15ba3454-65e2-439c-8519-9ba135cf97bc', 'React', '15ba3454-65e2-439c-8519-9ba135cf97b2');
