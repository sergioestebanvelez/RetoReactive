-- Insertar usuarios en la tabla users
INSERT INTO users (name, email, balance) VALUES
                                             ('Carlos', 'carlos@email.com', 1000.0),
                                             ('Ana', 'ana@email.com', 2000.0),
                                             ('Pedro', 'pedro@email.com', 1500.0),
                                             ('Lucía', 'lucia@email.com', 2500.0),
                                             ('Juan', 'juan@email.com', 1800.0);

-- Insertar cashouts (retiros) en la tabla cashouts
INSERT INTO cashouts (user_id, amount, status, created_at) VALUES
                                                               (1, 200.0, 'approved'),
                                                               (1, 300.0, 'pending'),
                                                               (2, 500.0, 'approved'),
                                                               (3, 150.0, 'rejected'),
                                                               (4, 800.0, 'approved'),
                                                               (5, 400.0, 'pending');

-- Si tu base de datos requiere valores de fecha específicos o no soporta NOW(), puedes usar una fecha estática:
-- '2024-11-19 12:00:00'
