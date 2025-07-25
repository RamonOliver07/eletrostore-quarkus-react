<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Produtos - EletroStore</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>

    <!-- Menu de Navegação -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">
                <span class="fw-bold text-primary">Eletro</span><span class="fw-bold text-warning">Store</span>
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="#" id="logout-button">Sair</a>
            </div>
        </div>
    </nav>

    <!-- Conteúdo Principal -->
    <main class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h2">Gestão de Produtos</h1>
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#productModal" id="add-product-button">
                Adicionar Novo Produto
            </button>
        </div>

        <!-- Tabela de Produtos -->
        <div class="card shadow-sm">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Preço</th>
                                <th>Stock</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody id="products-table-body">
                            <!-- Os produtos serão inseridos aqui pelo JavaScript -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>

    <!-- Modal para Adicionar/Editar Produto -->
    <div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="productModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="productModalLabel">Adicionar Produto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="product-form">
                        <input type="hidden" id="productId">
                        <div class="mb-3">
                            <label for="productName" class="form-label">Nome</label>
                            <input type="text" class="form-control" id="productName" required>
                        </div>
                        <div class="mb-3">
                            <label for="productDescription" class="form-label">Descrição</label>
                            <textarea class="form-control" id="productDescription" rows="3"></textarea>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="productPrice" class="form-label">Preço</label>
                                <input type="number" class="form-control" id="productPrice" step="0.01" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="productStock" class="form-label">Stock</label>
                                <input type="number" class="form-control" id="productStock" required>
                            </div>
                        </div>
                         <div class="mb-3">
                            <label for="productImage" class="form-label">URL da Imagem</label>
                            <input type="text" class="form-control" id="productImage">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="productBrand" class="form-label">Marca</label>
                                <input type="text" class="form-control" id="productBrand">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="productModel" class="form-label">Modelo</label>
                                <input type="text" class="form-control" id="productModel">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="productCategory" class="form-label">Categoria</label>
                            <select class="form-select" id="productCategory" required>
                                <!-- As categorias serão inseridas aqui -->
                            </select>
                        </div>
                         <div class="form-check mb-3">
                            <input class="form-check-input" type="checkbox" id="productFeatured">
                            <label class="form-check-label" for="productFeatured">
                                Produto em Destaque
                            </label>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                    <button type="submit" class="btn btn-primary" form="product-form">Salvar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Nosso Script de Gestão -->
    <script>
        const API_URL = 'http://localhost:8080/api';
        const token = localStorage.getItem('user_token');
        const userRole = localStorage.getItem('user_role');

        document.addEventListener('DOMContentLoaded', function() {
            // Segurança: Verifica se o utilizador é admin
            if (!token || userRole !== 'admin') {
                alert('Acesso negado. Por favor, faça login como administrador.');
                window.location.href = '/login.html';
                return;
            }

            loadProducts();
            loadCategories();

            document.getElementById('product-form').addEventListener('submit', handleFormSubmit);
            document.getElementById('add-product-button').addEventListener('click', openAddModal);
            document.getElementById('logout-button').addEventListener('click', handleLogout);
        });

        async function fetchApi(endpoint, options = {}) {
            const defaultOptions = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                ...options
            };
            const response = await fetch(`${API_URL}${endpoint}`, defaultOptions);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || `Erro na API: ${response.statusText}`);
            }
            // Retorna JSON apenas se houver conteúdo
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                return response.json();
            }
        }

        async function loadProducts() {
            try {
                const products = await fetchApi('/produtos');
                const tableBody = document.getElementById('products-table-body');
                tableBody.innerHTML = '';
                products.forEach(product => {
                    tableBody.innerHTML += `
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.nome}</td>
                            <td>${new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(product.preco)}</td>
                            <td>${product.estoque}</td>
                            <td>
                                <button class="btn btn-sm btn-outline-primary me-2" onclick="openEditModal(${product.id})">Editar</button>
                                <button class="btn btn-sm btn-outline-danger" onclick="deleteProduct(${product.id})">Apagar</button>
                            </td>
                        </tr>
                    `;
                });
            } catch (error) {
                console.error('Erro ao carregar produtos:', error);
                alert('Não foi possível carregar os produtos.');
            }
        }
        
        async function loadCategories() {
             try {
                const categories = await fetchApi('/categorias');
                const categorySelect = document.getElementById('productCategory');
                categorySelect.innerHTML = '<option value="">Selecione uma categoria</option>';
                categories.forEach(category => {
                    categorySelect.innerHTML += `<option value="${category.id}">${category.nome}</option>`;
                });
            } catch (error) {
                console.error('Erro ao carregar categorias:', error);
            }
        }

        function openAddModal() {
            document.getElementById('product-form').reset();
            document.getElementById('productId').value = '';
            document.getElementById('productModalLabel').textContent = 'Adicionar Novo Produto';
        }

        async function openEditModal(id) {
            try {
                const product = await fetchApi(`/produtos/${id}`);
                document.getElementById('productId').value = product.id;
                document.getElementById('productName').value = product.nome;
                document.getElementById('productDescription').value = product.descricao;
                document.getElementById('productPrice').value = product.preco;
                document.getElementById('productStock').value = product.estoque;
                document.getElementById('productImage').value = product.imagem;
                document.getElementById('productBrand').value = product.marca;
                document.getElementById('productModel').value = product.modelo;
                document.getElementById('productFeatured').checked = product.destaque;
                document.getElementById('productCategory').value = product.idCategoria; // Supondo que o DTO tenha idCategoria
                document.getElementById('productModalLabel').textContent = 'Editar Produto';
                new bootstrap.Modal(document.getElementById('productModal')).show();
            } catch (error) {
                console.error('Erro ao buscar produto para edição:', error);
                alert('Não foi possível carregar os dados do produto.');
            }
        }

        async function handleFormSubmit(event) {
            event.preventDefault();
            const productId = document.getElementById('productId').value;
            const isEditing = !!productId;

            const productData = {
                nome: document.getElementById('productName').value,
                descricao: document.getElementById('productDescription').value,
                preco: parseFloat(document.getElementById('productPrice').value),
                estoque: parseInt(document.getElementById('productStock').value, 10),
                imagem: document.getElementById('productImage').value,
                marca: document.getElementById('productBrand').value,
                modelo: document.getElementById('productModel').value,
                destaque: document.getElementById('productFeatured').checked,
                idCategoria: parseInt(document.getElementById('productCategory').value, 10)
            };

            const endpoint = isEditing ? `/produtos/${productId}` : '/produtos';
            const method = isEditing ? 'PUT' : 'POST';

            try {
                await fetchApi(endpoint, {
                    method: method,
                    body: JSON.stringify(productData)
                });
                bootstrap.Modal.getInstance(document.getElementById('productModal')).hide();
                loadProducts();
            } catch (error) {
                console.error('Erro ao salvar produto:', error);
                alert(`Erro ao salvar produto: ${error.message}`);
            }
        }

        async function deleteProduct(id) {
            if (confirm('Tem a certeza que deseja apagar este produto?')) {
                try {
                    await fetchApi(`/produtos/${id}`, { method: 'DELETE' });
                    loadProducts();
                } catch (error) {
                    console.error('Erro ao apagar produto:', error);
                    alert(`Erro ao apagar produto: ${error.message}`);
                }
            }
        }

        function handleLogout() {
            localStorage.removeItem('user_token');
            localStorage.removeItem('user_name');
            localStorage.removeItem('user_role');
            window.location.href = '/login.html';
        }

    </script>
</body>
</html>
