import type { Product } from '../../models/Product';
import { Eye, ShoppingCart } from 'lucide-react';

interface ProductCardProps {
  product: Product;
  onAddToCart: (productId: number) => void;
}

function ProductCard({ product, onAddToCart }: ProductCardProps) {
  const formattedPrice = new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  }).format(product.preco);

  return (
    <div className="bg-white rounded-xl shadow-md hover:shadow-lg transition-all transform hover:-translate-y-1">
      <div className="aspect-square bg-gray-100 rounded-t-xl overflow-hidden">
        <img
          src={product.imagem || 'https://via.placeholder.com/300x300?text=Produto'}
          alt={product.nome}
          className="w-full h-full object-cover"
        />
      </div>
      
      <div className="p-4">
        <span className="text-sm text-gray-500">{product.categoria.nome}</span>
        <h3 className="text-lg font-semibold mb-2">{product.nome}</h3>
        <div className="flex items-center justify-between mb-4">
          <span className="text-2xl font-bold text-blue-700">{formattedPrice}</span>
        </div>
        
        <div className="flex gap-2">
          <button
            onClick={() => onAddToCart(product.id)}
            className="flex-1 bg-blue-700 hover:bg-blue-800 text-white font-semibold py-2 px-4 rounded-lg transition-colors flex items-center justify-center gap-2"
          >
            <ShoppingCart size={20} />
            <span>Adicionar</span>
          </button>
          
          <a
            href={`/produto/${product.id}`}
            className="p-2 border border-blue-700 text-blue-700 hover:bg-blue-700 hover:text-white rounded-lg transition-colors"
          >
            <Eye size={20} />
          </a>
        </div>
      </div>
    </div>
  );
}

export default ProductCard;