// Configurações e Utilitários Globais
const API_URL = '/api';
let token = localStorage.getItem('auth_token');
let currentUser = null;

// Função para verificar se o usuário está autenticado
function isAuthenticated() {
    return token !== null && token !== '';
}

// Função para obter headers com autenticação
function getAuthHeaders() {
    const headers = {
        'Content-Type': 'application/json'
    };
    
    if (isAuthenticated()) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    
    return headers;
}

// Função para fazer requisições à API
async function api(endpoint, options = {}) {
    const url = `${API_URL}${endpoint}`;
    
    if (!options.headers) {
        options.headers = getAuthHeaders();
    }
    
    try {
        const response = await fetch(url, options);
        
        // Se a resposta for 401, redirecionar para login
        if (response.status === 401) {
            logout();
            window.location.href = '/login.html';
            return null;
        }
        
        // Para respostas sem conteúdo
        if (response.status === 204) {
            return true;
        }
        
        // Para outras respostas com erro
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Ocorreu um erro na requisição');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Erro na API:', error);
        showAlert(error.message || 'Erro na conexão com o servidor', 'error');
        return null;
    }
}

// Função para renderizar produtos
function renderProduct(product, container) {
    const productElement = document.createElement('div');
    productElement.className = 'product-card fade-in';
    
    // Formatar preço
    const formattedPrice = new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(product.preco);
    
    // Calcular desconto (simulado)
    const hasDiscount = Math.random() > 0.7;
    let oldPrice = '';
    
    if (hasDiscount) {
        const originalPrice = product.preco * (1 + Math.random() * 0.3);
        oldPrice = `<span class="old-price">${new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(originalPrice)}</span>`;
        
        productElement.innerHTML = `<span class="product-badge">OFERTA</span>`;
    }
    
    // Adicionar HTML do produto
    productElement.innerHTML += `
        <div class="product-image">
            <img src="${product.imagem || 'https://via.placeholder.com/300x300?text=Produto'}" alt="${product.nome}">
        </div>
        <div class="product-info">
            <div class="product-category">${product.categoria ? product.categoria.nome : 'Eletrônicos'}</div>
            <h3 class="product-title">${product.nome}</h3>
            <div class="product-price">
                <span class="current-price">${formattedPrice}</span>
                ${oldPrice}
            </div>
            <div class="product-actions">
                <button class="btn btn-primary btn-add-cart" data-id="${product.id}">
                    <i class="fas fa-shopping-cart"></i> Adicionar
                </button>
                <a href="produto.html?id=${product.id}" class="btn-view">
                    <i class="fas fa-eye"></i>
                </a>
            </div>
        </div>
    `;
    
    // Adicionar ao container
    container.appendChild(productElement);
    
    // Adicionar evento ao botão de adicionar ao carrinho
    productElement.querySelector('.btn-add-cart').addEventListener('click', () => {
        addToCart(product.id);
    });
}

// Função para renderizar categorias
function renderCategory(category, container) {
    const categoryElement = document.createElement('div');
    categoryElement.className = 'category-card fade-in';
    
    categoryElement.innerHTML = `
        <div class="category-icon">
            <i class="${category.icone || 'fas fa-microchip'}"></i>
        </div>
        <div class="category-info">
            <h3>${category.nome}</h3>
            <p>${category.descricao || `Os melhores produtos de ${category.nome}`}</p>
        </div>
    `;
    
    categoryElement.addEventListener('click', () => {
        window.location.href = `produtos.html?categoria=${category.id}`;
    });
    
    container.appendChild(categoryElement);
}

// Funções para o carrinho de compras
// Carrinho armazenado localmente
let cart = JSON.parse(localStorage.getItem('cart')) || [];

// Atualizar contador do carrinho
function updateCartCount() {
    const cartCount = document.getElementById('cart-count');
    if (cartCount) {
        const totalItems = cart.reduce((total, item) => total + item.quantidade, 0);
        cartCount.textContent = totalItems;
    }
}

// Adicionar produto ao carrinho
function addToCart(productId, quantity = 1) {
    // Buscar produto existente no carrinho
    const existingItem = cart.find(item => item.produtoId === productId);
    
    if (existingItem) {
        existingItem.quantidade += quantity;
    } else {
        cart.push({
            produtoId: productId,
            quantidade: quantity
        });
    }
    
    // Salvar carrinho e atualizar contador
    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartCount();
    
    // Mostrar mensagem de sucesso
    showAlert('Produto adicionado ao carrinho!', 'success');
}

// Função para exibir alertas
function showAlert(message, type = 'info') {
    // Verificar se já existe um alerta
    const existingAlert = document.querySelector('.alert-floating');
    if (existingAlert) {
        existingAlert.remove();
    }
    
    // Criar novo alerta
    const alert = document.createElement('div');
    alert.className = `alert-floating alert-${type} fade-in`;
    alert.innerHTML = `
        <div class="alert-content">
            <i class="fas ${type === 'success' ? 'fa-check-circle' : type === 'error' ? 'fa-exclamation-circle' : 'fa-info-circle'}"></i>
            <span>${message}</span>
        </div>
        <button class="alert-close"><i class="fas fa-times"></i></button>
    `;
    
    // Adicionar ao corpo do documento
    document.body.appendChild(alert);
    
    // Adicionar evento para fechar
    alert.querySelector('.alert-close').addEventListener('click', () => {
        alert.classList.add('fade-out');
        setTimeout(() => {
            alert.remove();
        }, 300);
    });
    
    // Fechar automaticamente após 5 segundos
    setTimeout(() => {
        if (document.body.contains(alert)) {
            alert.classList.add('fade-out');
            setTimeout(() => {
                if (document.body.contains(alert)) {
                    alert.remove();
                }
            }, 300);
        }
    }, 5000);
}

// Função para fazer logout
function logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user_name');
    localStorage.removeItem('user_role');
    token = null;
    currentUser = null;
    
    const loginButton = document.getElementById('login-button');
    const userDropdown = document.getElementById('user-dropdown');
    
    if (loginButton && userDropdown) {
        loginButton.style.display = 'flex';
        userDropdown.style.display = 'none';
    }
    
    // Redirecionar para a página inicial
    if (window.location.pathname !== '/index.html' && window.location.pathname !== '/') {
        window.location.href = '/index.html';
    }
}

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    // Verificar autenticação e atualizar interface
    if (isAuthenticated()) {
        const userName = localStorage.getItem('user_name');
        const userRole = localStorage.getItem('user_role');
        
        if (userName) {
            const loginButton = document.getElementById('login-button');
            const userDropdown = document.getElementById('user-dropdown');
            const userNameElement = document.getElementById('user-name');
            
            if (loginButton && userDropdown && userNameElement) {
                loginButton.style.display = 'none';
                userDropdown.style.display = 'block';
                userNameElement.textContent = `Olá, ${userName}`;
                
                // Adicionar ou remover link do admin
                const adminLink = document.querySelector('#user-dropdown a[href="admin/index.html"]');
                if (userRole === 'admin' && !adminLink) {
                    const adminLi = document.createElement('li');
                    adminLi.innerHTML = '<a href="admin/index.html">Painel Admin</a>';
                    document.querySelector('#user-dropdown ul').insertBefore(
                        adminLi, 
                        document.querySelector('#user-dropdown a[href="#"]').parentNode
                    );
                }
            }
        }
    }
    
    // Configurar logout
    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', (e) => {
            e.preventDefault();
            logout();
        });
    }
    
    // Atualizar contador do carrinho
    updateCartCount();
    
    // Configurar menu mobile
    const mobileMenuToggle = document.getElementById('mobile-menu-toggle');
    if (mobileMenuToggle) {
        mobileMenuToggle.addEventListener('click', () => {
            const navMenu = document.querySelector('.nav-menu');
            navMenu.classList.toggle('active');
        });
    }
    
    // Configurar formulário de busca
    const searchForm = document.querySelector('.search-bar');
    if (searchForm) {
        const searchInput = document.getElementById('search-input');
        const searchButton = document.getElementById('search-button');
        
        searchButton.addEventListener('click', () => {
            if (searchInput.value.trim()) {
                window.location.href = `produtos.html?busca=${encodeURIComponent(searchInput.value.trim())}`;
            }
        });
        
        searchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                if (searchInput.value.trim()) {
                    window.location.href = `produtos.html?busca=${encodeURIComponent(searchInput.value.trim())}`;
                }
            }
        });
    }
    
    // Configurar formulário de newsletter
    const newsletterForm = document.getElementById('newsletter-form');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const emailInput = newsletterForm.querySelector('input[type="email"]');
            if (emailInput.value.trim()) {
                // Simulação de envio
                newsletterForm.classList.add('loading');
                setTimeout(() => {
                    newsletterForm.classList.remove('loading');
                    showAlert('Inscrição realizada com sucesso!', 'success');
                    emailInput.value = '';
                }, 1000);
            }
        });
    }
});

// Criar estilos para os alertas flutuantes
const alertStyles = document.createElement('style');
alertStyles.textContent = `
    .alert-floating {
        position: fixed;
        top: 20px;
        right: 20px;
        max-width: 350px;
        background-color: white;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        border-radius: 8px;
        padding: 16px;
        z-index: 1000;
        display: flex;
        align-items: center;
        justify-content: space-between;
        animation: slideIn 0.3s ease-out;
    }
    
    .alert-content {
        display: flex;
        align-items: center;
        gap: 12px;
    }
    
    .alert-success {
        border-left: 4px solid var(--success);
    }
    
    .alert-error {
        border-left: 4px solid var(--error);
    }
    
    .alert-info {
        border-left: 4px solid var(--primary-color);
    }
    
    .alert-close {
        background: none;
        border: none;
        cursor: pointer;
        color: var(--text-light);
    }
    
    .alert-floating.fade-out {
        animation: fadeOut 0.3s ease-out forwards;
    }
    
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes fadeOut {
        from {
            opacity: 1;
        }
        to {
            opacity: 0;
        }
    }
`;

document.head.appendChild(alertStyles);