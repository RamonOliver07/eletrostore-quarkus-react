-- Script SQL para inicializar dados no H2
-- Este arquivo é executado automaticamente quando a aplicação inicia

-- Inserir categorias
INSERT INTO Categoria (id, nome, descricao, icone) VALUES (1, 'Smartphones', 'Celulares e Smartphones', 'fas fa-mobile-alt');
INSERT INTO Categoria (id, nome, descricao, icone) VALUES (2, 'Notebooks', 'Notebooks e Laptops', 'fas fa-laptop');
INSERT INTO Categoria (id, nome, descricao, icone) VALUES (3, 'Tablets', 'Tablets e iPads', 'fas fa-tablet-alt');
INSERT INTO Categoria (id, nome, descricao, icone) VALUES (4, 'Acessórios', 'Acessórios e Periféricos', 'fas fa-headphones');

-- Inserir produtos
INSERT INTO Produto (id, nome, descricao, preco, estoque, imagem, marca, modelo, destaque, categoria_id) VALUES 
(1, 'Smartphone Galaxy S23', 'O mais recente smartphone Samsung com câmera incrível de 200MP', 4999.99, 50, 'https://images.pexels.com/photos/47261/pexels-photo-47261.jpeg', 'Samsung', 'Galaxy S23', true, 1);

INSERT INTO Produto (id, nome, descricao, preco, estoque, imagem, marca, modelo, destaque, categoria_id) VALUES 
(2, 'MacBook Pro M2', 'Notebook potente com o novo chip M2 da Apple', 9999.99, 25, 'https://images.pexels.com/photos/812264/pexels-photo-812264.jpeg', 'Apple', 'MacBook Pro M2', true, 2);

INSERT INTO Produto (id, nome, descricao, preco, estoque, imagem, marca, modelo, destaque, categoria_id) VALUES 
(3, 'iPad Pro', 'Tablet profissional da Apple com tela Liquid Retina', 6999.99, 30, 'https://images.pexels.com/photos/1334597/pexels-photo-1334597.jpeg', 'Apple', 'iPad Pro', false, 3);

INSERT INTO Produto (id, nome, descricao, preco, estoque, imagem, marca, modelo, destaque, categoria_id) VALUES 
(4, 'Fones Bluetooth JBL', 'Fones sem fio com qualidade excepcional e cancelamento de ruído', 299.99, 100, 'https://images.pexels.com/photos/3394665/pexels-photo-3394665.jpeg', 'JBL', 'Tune 760NC', true, 4);

INSERT INTO Produto (id, nome, descricao, preco, estoque, imagem, marca, modelo, destaque, categoria_id) VALUES 
(5, 'iPhone 14 Pro', 'iPhone com chip A16 Bionic e câmera de 48MP', 7999.99, 40, 'https://images.pexels.com/photos/9555008/pexels-photo-9555008.jpeg', 'Apple', 'iPhone 14 Pro', true, 1);

INSERT INTO Produto (id, nome, descricao, preco, estoque, imagem, marca, modelo, destaque, categoria_id) VALUES 
(6, 'Dell XPS 13', 'Ultrabook premium com tela InfinityEdge', 5999.99, 20, 'https://images.pexels.com/photos/205421/pexels-photo-205421.jpeg', 'Dell', 'XPS 13', false, 2);

-- Inserir usuários (senhas já criptografadas com BCrypt)
INSERT INTO Usuario (id, email, senha, nome, papel) VALUES 
(1, 'admin@eletrostore.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P8jW9TukLv.Saa', 'Administrador', 'admin');

INSERT INTO Usuario (id, email, senha, nome, papel) VALUES 
(2, 'cliente@exemplo.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Cliente Exemplo', 'usuario');