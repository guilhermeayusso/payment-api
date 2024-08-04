insert into USUARIOS (id, username, password, role) values (100, 'ana@email.com', '$2a$12$vfV9DMREnjT/jmtpgzEBa.UuTnYWGQVdZeZYP7nd7aTmTNeSY2qny', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101, 'bia@email.com', '$2a$12$vfV9DMREnjT/jmtpgzEBa.UuTnYWGQVdZeZYP7nd7aTmTNeSY2qny', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102, 'bob@email.com', '$2a$12$vfV9DMREnjT/jmtpgzEBa.UuTnYWGQVdZeZYP7nd7aTmTNeSY2qny', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (103, 'toby@email.com', '$2a$12$vfV9DMREnjT/jmtpgzEBa.UuTnYWGQVdZeZYP7nd7aTmTNeSY2qny', 'ROLE_CLIENTE');

INSERT INTO clientes (id_cliente, cpf, nome, email, telefone, rua, cidade, estado, cep, pais) VALUES
    (10, '12345678901', 'João da Silva', 'joao@email.com', '11123456789', 'Rua das Flores', 'São Paulo', 'SP', '12345-678', 'Brasil');

INSERT INTO clientes (id_cliente, cpf, nome, email, telefone, rua, cidade, estado, cep, pais) VALUES
    (20, '98765432100', 'Maria Oliveira', 'maria@email.com', '11987654321', 'Avenida Central', 'Rio de Janeiro', 'RJ', '23456-789', 'Brasil');

INSERT INTO clientes (id_cliente, cpf, nome, email, telefone, rua, cidade, estado, cep, pais) VALUES
    (30, '45612378902', 'Pedro Santos', 'pedro@email.com', '11456123789', 'Rua da Praia', 'Salvador', 'BA', '34567-890', 'Brasil');

INSERT INTO clientes (id_cliente, cpf, nome, email, telefone, rua, cidade, estado, cep, pais) VALUES
    (40, '34567890123', 'Ana Lima', 'ana@email.com', '11321654987', 'Alameda dos Anjos', 'Belo Horizonte', 'MG', '45678-901', 'Brasil');


INSERT INTO cartoes (cpf, limite, numero_cartao, data_validade, cvv) VALUES
                                                                       ('23456789012', 7500.50, '2345 6789 0123 4567', '2025-09-15 14:45:00', '456'),
                                                                       ('34567890123', 10000.75, '3456 7890 1234 5678', '2026-11-25 16:00:00', '789'),
                                                                       ('45678901234', 3000.25, '4567 8901 2345 6789', '2027-01-10 09:15:00', '012');
