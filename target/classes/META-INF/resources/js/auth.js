// Este script deve ser incluído em todas as páginas HTML

function handleAuth() {
    const token = localStorage.getItem('user_token');
    const userName = localStorage.getItem('user_name');
    const userRole = localStorage.getItem('user_role');

    // --- Lógica de Proteção de Páginas ---
    const currentPage = window.location.pathname;
    const isAuthPage = currentPage.includes('login.html') || currentPage.includes('cadastro.html');

    // Se o utilizador está numa página de autenticação MAS já está logado, redireciona para a home.
    if (token && isAuthPage) {
        window.location.href = '/';
        return; // Para a execução para evitar piscar a tela
    }

    // Se o utilizador NÃO está logado e tenta aceder a uma página protegida, redireciona para o login.
    const protectedPages = ['/carrinho.html', '/meus-pedidos.html', '/admin-produtos.html'];
    if (!token && protectedPages.some(page => currentPage.includes(page))) {
        window.location.href = '/login.html';
        return;
    }

    // --- Lógica de Atualização da UI (Menu de Navegação) ---
    const authLinkContainer = document.getElementById('auth-link-container');
    const navItems = document.getElementById('nav-items');

    if (token && userName && authLinkContainer) {
        // Se o utilizador está logado, substitui o botão "Entrar"
        authLinkContainer.innerHTML = `
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Olá, ${userName}
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="/meus-pedidos.html">Meus Pedidos</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" id="logout-button">Sair</a></li>
                </ul>
            </li>
        `;

        // Se o utilizador for admin, adiciona o link para a página de gestão
        if (userRole === 'admin' && navItems) {
            const adminLink = document.createElement('li');
            adminLink.className = 'nav-item';
            adminLink.innerHTML = '<a class="nav-link" href="/admin-produtos.html">Gerir Produtos</a>';
            // Insere o link de admin antes do link do carrinho
            const cartLink = navItems.querySelector('a[href="/carrinho.html"]').parentElement;
            if (cartLink) {
                 navItems.insertBefore(adminLink, cartLink);
            }
        }
        
        // Adiciona o evento de logout
        document.getElementById('logout-button').addEventListener('click', function(e) {
            e.preventDefault();
            localStorage.removeItem('user_token');
            localStorage.removeItem('user_name');
            localStorage.removeItem('user_role');
            window.location.href = '/login.html';
        });
    }
}

// Executa a função assim que o conteúdo da página carregar
document.addEventListener('DOMContentLoaded', handleAuth);
