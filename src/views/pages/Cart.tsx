import { useState, useEffect } from 'react';
import { Trash2, Plus, Minus } from 'lucide-react';
import { Link } from 'react-router-dom';

interface CartItem {
  id: number;
  name: string;
  price: number;
  image: string;
  quantity: number;
}

function Cart() {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [total, setTotal] = useState(0);

  useEffect(() => {
    // Load cart items from localStorage
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      setCartItems(JSON.parse(savedCart));
    }
  }, []);

  useEffect(() => {
    // Calculate total whenever cart items change
    const newTotal = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    setTotal(newTotal);
    
    // Save to localStorage
    localStorage.setItem('cart', JSON.stringify(cartItems));
  }, [cartItems]);

  const updateQuantity = (id: number, change: number) => {
    setCartItems(prevItems => 
      prevItems.map(item => 
        item.id === id 
          ? { ...item, quantity: Math.max(1, item.quantity + change) }
          : item
      )
    );
  };

  const removeItem = (id: number) => {
    setCartItems(prevItems => prevItems.filter(item => item.id !== id));
  };

  if (cartItems.length === 0) {
    return (
      <div className="container mx-auto px-4 py-16">
        <div className="text-center">
          <h2 className="text-2xl font-bold mb-4">Seu carrinho está vazio</h2>
          <p className="text-gray-600 mb-8">Adicione alguns produtos para começar suas compras</p>
          <Link 
            to="/produtos" 
            className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors"
          >
            Continuar Comprando
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
            {cartItems.map(item => (
              <div key={item.id} className="flex items-center p-6 border-b border-gray-200 last:border-0">
                <img 
                  src={item.image} 
                  alt={item.name} 
                  className="w-24 h-24 object-cover rounded-md"
                />
                
                <div className="flex-1 ml-6">
                  <h3 className="text-lg font-semibold">{item.name}</h3>
                  <p className="text-gray-600">
                    {new Intl.NumberFormat('pt-BR', {
                      style: 'currency',
                      currency: 'BRL'
                    }).format(item.price)}
                  </p>
                  
                  <div className="flex items-center mt-4">
                    <button
                      onClick={() => updateQuantity(item.id, -1)}
                      className="p-1 rounded-full hover:bg-gray-100"
                    >
                      <Minus size={20} />
                    </button>
                    
                    <span className="mx-4 font-semibold">{item.quantity}</span>
                    
                    <button
                      onClick={() => updateQuantity(item.id, 1)}
                      className="p-1 rounded-full hover:bg-gray-100"
                    >
                      <Plus size={20} />
                    </button>
                  </div>
                </div>
                
                <div className="text-right">
                  <p className="text-lg font-bold">
                    {new Intl.NumberFormat('pt-BR', {
                      style: 'currency',
                      currency: 'BRL'
                    }).format(item.price * item.quantity)}
                  </p>
                  
                  <button
                    onClick={() => removeItem(item.id)}
                    className="text-red-500 hover:text-red-700 mt-2"
                  >
                    <Trash2 size={20} />
                  </button>
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
                <span>
                  {new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL'
                  }).format(total)}
                </span>
              </div>
              
              <div className="flex justify-between">
                <span>Frete</span>
                <span>Grátis</span>
              </div>
              
              <div className="border-t pt-3">
                <div className="flex justify-between font-bold text-lg">
                  <span>Total</span>
                  <span>
                    {new Intl.NumberFormat('pt-BR', {
                      style: 'currency',
                      currency: 'BRL'
                    }).format(total)}
                  </span>
                </div>
              </div>
            </div>
            
            <button 
              className="w-full bg-blue-600 text-white py-3 rounded-lg mt-6 hover:bg-blue-700 transition-colors"
              onClick={() => alert('Implementar checkout')}
            >
              Finalizar Compra
            </button>
            
            <Link 
              to="/produtos"
              className="block text-center text-blue-600 hover:text-blue-800 mt-4"
            >
              Continuar Comprando
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Cart;