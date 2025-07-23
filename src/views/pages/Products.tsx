import React, { useState, useEffect } from 'react';
import { ShoppingCart, Eye } from 'lucide-react';
import axios from 'axios'; // Importa o axios para fazer chamadas de API

// A interface agora corresponde ao nosso ProdutoDTO do backend
interface Product {
  id: number;
  nome: string;
  preco: number;
  imagem: string;
  descricao: string;
}

function Products() {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Função para buscar os produtos da nossa API Quarkus
    const fetchProducts = async () => {
      try {
        setLoading(true);
        // Faz a chamada GET para o endpoint que já refatorámos
        const response = await axios.get('/api/produtos');
        setProducts(response.data);
        setError(null);
      } catch (err) {
        console.error("Erro ao buscar produtos:", err);
        setError("Não foi possível carregar os produtos.");
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const addToCart = (product: Product) => {
    // A lógica do carrinho pode ser melhorada depois
    alert(`Produto "${product.nome}" adicionado ao carrinho!`);
  };

  if (loading) {
    return <div className="text-center py-12">A carregar produtos...</div>;
  }

  if (error) {
    return <div className="text-center py-12 text-red-500">{error}</div>;
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Nossos Produtos</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {products.map(product => (
          <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden group flex flex-col">
            {/* --- CORREÇÃO DE ESTILO AQUI --- */}
            <div className="relative w-full h-64 overflow-hidden">
              <img 
                src={product.imagem} 
                alt={product.nome}
                className="absolute top-0 left-0 w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                // Adiciona um fallback para imagens quebradas
                onError={(e) => { e.currentTarget.src = 'https://placehold.co/400x400/cccccc/ffffff?text=Imagem+Indisponível'; }}
              />
            </div>
            
            <div className="p-4 flex flex-col flex-grow">
              <h2 className="text-xl font-semibold mb-2 truncate">{product.nome}</h2>
              <p className="text-gray-600 mb-4 flex-grow">{product.descricao}</p>
              
              <div className="flex items-center justify-between mt-auto">
                <span className="text-2xl font-bold text-blue-600">
                  {new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL'
                  }).format(product.preco)}
                </span>
                
                <div className="flex gap-2">
                  <button
                    onClick={() => addToCart(product)}
                    className="bg-blue-600 text-white p-2 rounded-lg hover:bg-blue-700 transition-colors"
                  >
                    <ShoppingCart size={20} />
                  </button>
                  
                  <button className="border border-blue-600 text-blue-600 p-2 rounded-lg hover:bg-blue-50 transition-colors">
                    <Eye size={20} />
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Products;
