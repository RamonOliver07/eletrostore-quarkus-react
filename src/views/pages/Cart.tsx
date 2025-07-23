import React, { useState, useEffect } from 'react';
import { Trash2, Plus, Minus } from 'lucide-react';
import { Link, useHistory } from 'react-router-dom';
import axios from 'axios';

// Interface para os itens básicos que guardamos no navegador
interface CartItemStorage {
  produtoId: number;
  quantidade: number;
}

// Interface para a resposta detalhada que o nosso backend envia
interface CartItemResponse {
  produtoId: number;
  nome: string;
  imagem: string;
  preco: number;
  quantidade: number;
  subtotal: number;
}

// Interface para o resumo completo do carrinho
interface CartSummary {
  itens: CartItemResponse[];
  subtotal: number;
  frete: number;
  total: number;
}

function Cart() {
  const [summary, setSummary] = useState<CartSummary | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const history = useHistory();

  // Função para buscar e calcular o carrinho no backend
  const fetchCartSummary = async () => {
    try {
      const cartItems: CartItemStorage[] = JSON.parse(localStorage.getItem('cart') || '[]');
      
      if (cartItems.length === 0) {
        setSummary(null);
        setLoading(false);
        return;
      }

      setLoading(true);
      // Faz a chamada POST para o nosso endpoint /api/carrinho/calcular
      const response = await axios.post<CartSummary>('/api/carrinho/calcular', cartItems);
      setSummary(response.data);
      setError(null);
    } catch (err) {
      console.error("Erro ao calcular o carrinho:", err);
      setError("Não foi possível carregar o carrinho. Verifique o stock dos produtos.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCartSummary();
  }, []);

  const updateQuantity = (id: number, change: number) => {
    const cartItems: CartItemStorage[] = JSON.parse(localStorage.getItem('cart') || '[]');
    const item = cartItems.find(i => i.produtoId === id);
    if (item) {
      item.quantidade = Math.max(1, item.quantidade + change);
      localStorage.setItem('cart', JSON.stringify(cartItems));
      fetchCartSummary(); // Recalcula o carrinho no backend
    }
  };

  const removeItem = (id: number) => {
    let cartItems: CartItemStorage[] = JSON.parse(localStorage.getItem('cart') || '[]');
    cartItems = cartItems.filter(item => item.produtoId !== id);
    localStorage.setItem('cart', JSON.stringify(cartItems));
    fetchCartSummary(); // Recalcula o carrinho no backend
  };
  
  const handleCheckout = async () => {
    const cartItems: CartItemStorage[] = JSON.parse(localStorage.getItem('cart') || '[]');
    const pedidoDTO = {
        itens: cartItems,
        metodoPagamento: 'CARTAO_DE_CREDITO' // Exemplo
    };

    try {
        await axios.post('/api/pedidos', pedidoDTO);
        alert('Pedido realizado com sucesso!');
        localStorage.removeItem('cart'); // Limpa o carrinho
        history.push('/pedidos'); // Redireciona para a página de pedidos (a ser criada)
    } catch (error) {
        alert('Falha ao finalizar o pedido. Verifique o console para mais detalhes.');
        console.error("Erro ao finalizar pedido:", error);
    }
  };

  if (loading) {
    return <div className="text-center py-16">A carregar carrinho...</div>;
  }

  if (error) {
    return <div className="text-center py-16 text-red-500">{error}</div>;
  }

  if (!summary || summary.itens.length === 0) {
    return (
      <div className="container mx-auto px-4 py-16">
        <div className="text-center">
          <h2 className="text-2xl font-bold mb-4">O seu carrinho está vazio</h2>
          <p className="text-gray-600 mb-8">Adicione alguns produtos para começar as suas compras.</p>
          <Link 
            to="/produtos" 
            className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors"
          >
            Continuar a Comprar
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Carrinho de Compras</h1>
      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2">
          <div className="bg-white rounded-lg shadow-md">
            {summary.itens.map(item => (
              <div key={item.produtoId} className="flex items-center p-6 border-b border-gray-200 last:border-0">
                <img 
                  src={item.imagem} 
                  alt={item.nome} 
                  className="w-24 h-24 object-cover rounded-md"
                  onError={(e) => { e.currentTarget.src = 'https://placehold.co/200x200/cccccc/ffffff?text=Imagem'; }}
                />
                
                <div className="flex-1 ml-6">
                  <h3 className="text-lg font-semibold">{item.nome}</h3>
                  <p className="text-gray-600">
                    {new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(item.preco)}
                  </p>
                  
                  <div className="flex items-center mt-4">
                    <button onClick={() => updateQuantity(item.produtoId, -1)} className="p-1 rounded-full hover:bg-gray-100"><Minus size={20} /></button>
                    <span className="mx-4 font-semibold">{item.quantidade}</span>
                    <button onClick={() => updateQuantity(item.produtoId, 1)} className="p-1 rounded-full hover:bg-gray-100"><Plus size={20} /></button>
                  </div>
                </div>
                
                <div className="text-right">
                  <p className="text-lg font-bold">
                    {new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(item.subtotal)}
                  </p>
                  <button onClick={() => removeItem(item.produtoId)} className="text-red-500 hover:text-red-700 mt-2"><Trash2 size={20} /></button>
                </div>
              </div>
            ))}
          </div>
        </div>
        
        <div className="lg:col-span-1">
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-bold mb-4">Resumo do Pedido</h2>
            <div className="space-y-3">
              <div className="flex justify-between">
                <span>Subtotal</span>
                <span>{new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(summary.subtotal)}</span>
              </div>
              <div className="flex justify-between">
                <span>Frete</span>
                <span>{summary.frete > 0 ? new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(summary.frete) : 'Grátis'}</span>
              </div>
              <div className="border-t pt-3">
                <div className="flex justify-between font-bold text-lg">
                  <span>Total</span>
                  <span>{new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(summary.total)}</span>
                </div>
              </div>
            </div>
            <button 
              className="w-full bg-blue-600 text-white py-3 rounded-lg mt-6 hover:bg-blue-700 transition-colors"
              onClick={handleCheckout}
            >
              Finalizar Compra
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Cart;
