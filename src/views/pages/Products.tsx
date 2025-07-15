import { useState, useEffect } from 'react';
import { ShoppingCart, Eye } from 'lucide-react';

interface Product {
  id: number;
  name: string;
  price: number;
  image: string;
  description: string;
}

function Products() {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    // Simulated products data
    const demoProducts = [
      {
        id: 1,
        name: "Smartphone Galaxy S23",
        price: 4999.99,
        image: "https://images.pexels.com/photos/47261/pexels-photo-47261.jpeg",
        description: "O mais recente smartphone Samsung com câmera incrível"
      },
      {
        id: 2,
        name: "MacBook Pro M2",
        price: 9999.99,
        image: "https://images.pexels.com/photos/812264/pexels-photo-812264.jpeg",
        description: "Notebook potente com o novo chip M2"
      },
      {
        id: 3,
        name: "Fones Bluetooth",
        price: 299.99,
        image: "https://images.pexels.com/photos/3394665/pexels-photo-3394665.jpeg",
        description: "Fones sem fio com qualidade excepcional"
      },
      {
        id: 4,
        name: "Smart TV 55\"",
        price: 3499.99,
        image: "https://images.pexels.com/photos/6782581/pexels-photo-6782581.jpeg",
        description: "TV 4K com tecnologia Smart"
      }
    ];

    setProducts(demoProducts);
  }, []);

  const addToCart = (product: Product) => {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    
    const existingItem = cart.find((item: any) => item.id === product.id);
    
    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      cart.push({
        id: product.id,
        name: product.name,
        price: product.price,
        image: product.image,
        quantity: 1
      });
    }
    
    localStorage.setItem('cart', JSON.stringify(cart));
    alert('Produto adicionado ao carrinho!');
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Nossos Produtos</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {products.map(product => (
          <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden">
            <div className="aspect-square overflow-hidden">
              <img 
                src={product.image} 
                alt={product.name}
                className="w-full h-full object-cover hover:scale-105 transition-transform"
              />
            </div>
            
            <div className="p-4">
              <h2 className="text-xl font-semibold mb-2">{product.name}</h2>
              <p className="text-gray-600 mb-4">{product.description}</p>
              
              <div className="flex items-center justify-between">
                <span className="text-2xl font-bold text-blue-600">
                  {new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL'
                  }).format(product.price)}
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