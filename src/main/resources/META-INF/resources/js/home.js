// Script específico para a página inicial

document.addEventListener('DOMContentLoaded', async () => {
    // Carregar categorias
    loadCategories();
    
    // Carregar produtos em destaque
    loadFeaturedProducts();
    
    // Carregar produtos recentes
    loadNewProducts();
});

// Função para carregar categorias
async function loadCategories() {
    const categoriesContainer = document.getElementById('categories-container');
    if (!categoriesContainer) return;
    
    // Limpar container e mostrar loading
    categoriesContainer.innerHTML = '<div class="category-loading">Carregando categorias...</div>';
    
    try {
        const categories = await api('/categorias');
        
        // Se não houver categorias ou ocorrer erro
        if (!categories || categories.length === 0) {
            // Criar categorias de exemplo para visualização da interface
            const demoCategories = [
                { id: 1, nome: 'Smartphones', descricao: 'Celulares e Smartphones', icone: 'fas fa-mobile-alt' },
                { id: 2, nome: 'Notebooks', descricao: 'Notebooks e Laptops', icone: 'fas fa-laptop' },
                { id: 3, nome: 'Tablets', descricao: 'Tablets e iPads', icone: 'fas fa-tablet-alt' },
                { id: 4, nome: 'Acessórios', descricao: 'Acessórios e Periféricos', icone: 'fas fa-headphones' },
                { id: 5, nome: 'Áudio', descricao: 'Caixas de Som e Fones', icone: 'fas fa-volume-up' }
            ];
            
            categoriesContainer.innerHTML = '';
            demoCategories.forEach(category => {
                renderCategory(category, categoriesContainer);
            });
            
            return;
        }
        
        // Limpar container e renderizar categorias
        categoriesContainer.innerHTML = '';
        categories.forEach(category => {
            renderCategory(category, categoriesContainer);
        });
        
        // Atualizar também as categorias no footer
        updateFooterCategories(categories);
        
    } catch (error) {
        console.error('Erro ao carregar categorias:', error);
        categoriesContainer.innerHTML = '<p>Não foi possível carregar as categorias. Tente novamente mais tarde.</p>';
    }
}

// Função para atualizar as categorias no footer
function updateFooterCategories(categories) {
    const footerCategories = document.getElementById('footer-categories');
    if (!footerCategories || !categories || categories.length === 0) return;
    
    footerCategories.innerHTML = '';
    categories.slice(0, 5).forEach(category => {
        const li = document.createElement('li');
        li.innerHTML = `<a href="produtos.html?categoria=${category.id}">${category.nome}</a>`;
        footerCategories.appendChild(li);
    });
}

// Função para carregar produtos em destaque
async function loadFeaturedProducts() {
    const featuredContainer = document.getElementById('featured-products-container');
    if (!featuredContainer) return;
    
    // Limpar container e mostrar loading
    featuredContainer.innerHTML = '<div class="product-loading">Carregando produtos em destaque...</div>';
    
    try {
        const featuredProducts = await api('/produtos/destaques');
        
        // Se não houver produtos ou ocorrer erro
        if (!featuredProducts || featuredProducts.length === 0) {
            // Criar produtos de exemplo para visualização da interface
            const demoProducts = generateDemoProducts(4, true);
            
            featuredContainer.innerHTML = '';
            demoProducts.forEach(product => {
                renderProduct(product, featuredContainer);
            });
            
            return;
        }
        
        // Limpar container e renderizar produtos
        featuredContainer.innerHTML = '';
        featuredProducts.forEach(product => {
            renderProduct(product, featuredContainer);
        });
        
    } catch (error) {
        console.error('Erro ao carregar produtos em destaque:', error);
        featuredContainer.innerHTML = '<p>Não foi possível carregar os produtos em destaque. Tente novamente mais tarde.</p>';
    }
}

// Função para carregar produtos recentes
async function loadNewProducts() {
    const newProductsContainer = document.getElementById('new-products-container');
    if (!newProductsContainer) return;
    
    // Limpar container e mostrar loading
    newProductsContainer.innerHTML = '<div class="product-loading">Carregando produtos recentes...</div>';
    
    try {
        // Poderíamos ter um endpoint específico para produtos recentes
        // Por enquanto, usaremos todos os produtos
        const products = await api('/produtos');
        
        // Se não houver produtos ou ocorrer erro
        if (!products || products.length === 0) {
            // Criar produtos de exemplo para visualização da interface
            const demoProducts = generateDemoProducts(8);
            
            newProductsContainer.innerHTML = '';
            demoProducts.forEach(product => {
                renderProduct(product, newProductsContainer);
            });
            
            return;
        }
        
        // Limpar container e renderizar produtos (limitar a 8)
        newProductsContainer.innerHTML = '';
        products.slice(0, 8).forEach(product => {
            renderProduct(product, newProductsContainer);
        });
        
    } catch (error) {
        console.error('Erro ao carregar produtos recentes:', error);
        newProductsContainer.innerHTML = '<p>Não foi possível carregar os produtos recentes. Tente novamente mais tarde.</p>';
    }
}

// Função para gerar produtos de demonstração
function generateDemoProducts(count, featured = false) {
    const categories = [
        { id: 1, nome: 'Smartphones' },
        { id: 2, nome: 'Notebooks' },
        { id: 3, nome: 'Tablets' },
        { id: 4, nome: 'Acessórios' }
    ];
    
    const products = [];
    
    const productNames = [
        'Smartphone Galaxy S23', 'iPhone 14 Pro', 'Notebook Dell XPS', 'MacBook Pro M2',
        'Tablet Galaxy Tab S8', 'iPad Pro', 'Fone Bluetooth JBL', 'Mouse Gamer Logitech',
        'Smart TV Samsung 55"', 'Câmera Sony Alpha', 'Console PlayStation 5', 'Smartwatch Apple',
        'Echo Dot Alexa', 'Carregador Wireless', 'Caixa de Som Bluetooth', 'Teclado Mecânico'
    ];
    
    const productImages = [
        'https://images.pexels.com/photos/47261/pexels-photo-47261.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
        'https://images.pexels.com/photos/9555008/pexels-photo-9555008.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
        'https://images.pexels.com/photos/812264/pexels-photo-812264.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
        'https://images.pexels.com/photos/205421/pexels-photo-205421.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
        'https://images.pexels.com/photos/1334597/pexels-photo-1334597.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
        'https://images.pexels.com/photos/949587/pexels-photo-949587.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
        'https://images.pexels.com/photos/3394665/pexels-photo-3394665.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
        'https://images.pexels.com/photos/1649771/pexels-photo-1649771.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'
    ];
    
    for (let i = 0; i < count; i++) {
        const randomNameIndex = Math.floor(Math.random() * productNames.length);
        const randomCategoryIndex = Math.floor(Math.random() * categories.length);
        const randomImageIndex = Math.floor(Math.random() * productImages.length);
        
        products.push({
            id: i + 1,
            nome: productNames[randomNameIndex],
            descricao: `Descrição detalhada do produto ${productNames[randomNameIndex]}.`,
            preco: Math.floor(Math.random() * 5000) + 500,
            estoque: Math.floor(Math.random() * 100) + 10,
            imagem: productImages[randomImageIndex],
            destaque: featured,
            categoria: categories[randomCategoryIndex]
        });
    }
    
    return products;
}