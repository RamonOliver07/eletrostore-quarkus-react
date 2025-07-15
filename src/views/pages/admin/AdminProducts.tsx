import { useState, useEffect } from 'react';
import { Plus, Pencil, Trash2, X, Save, Upload } from 'lucide-react';

interface Product {
  id: number;
  name: string;
  price: number;
  image: string;
  description: string;
}

function AdminProducts() {
  const [products, setProducts] = useState<Product[]>([]);
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    // Load products from localStorage
    const savedProducts = localStorage.getItem('adminProducts');
    if (savedProducts) {
      setProducts(JSON.parse(savedProducts));
    } else {
      // Demo products if none exist
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
        }
      ];
      setProducts(demoProducts);
      localStorage.setItem('adminProducts', JSON.stringify(demoProducts));
    }
  }, []);

  const saveProducts = (newProducts: Product[]) => {
    setProducts(newProducts);
    localStorage.setItem('adminProducts', JSON.stringify(newProducts));
  };

  const handleEdit = (product: Product) => {
    setEditingProduct({ ...product });
    setIsModalOpen(true);
  };

  const handleDelete = (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir este produto?')) {
      const newProducts = products.filter(p => p.id !== id);
      saveProducts(newProducts);
    }
  };

  const handleSave = (e: React.FormEvent) => {
    e.preventDefault();
    if (!editingProduct) return;

    const newProducts = editingProduct.id
      ? products.map(p => p.id === editingProduct.id ? editingProduct : p)
      : [...products, { ...editingProduct, id: Date.now() }];

    saveProducts(newProducts);
    setIsModalOpen(false);
    setEditingProduct(null);
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!editingProduct) return;

    const file = e.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setEditingProduct(prev => prev ? { ...prev, image: reader.result as string } : null);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">Gerenciar Produtos</h1>
        <button
          onClick={() => {
            setEditingProduct({
              id: 0,
              name: '',
              price: 0,
              image: '',
              description: ''
            });
            setIsModalOpen(true);
          }}
          className="bg-blue-600 text-white px-4 py-2 rounded-lg flex items-center gap-2 hover:bg-blue-700"
        >
          <Plus size={20} />
          Novo Produto
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {products.map(product => (
          <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden">
            <div className="aspect-square overflow-hidden">
              <img
                src={product.image}
                alt={product.name}
                className="w-full h-full object-cover"
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
                    onClick={() => handleEdit(product)}
                    className="text-blue-600 hover:text-blue-800"
                  >
                    <Pencil size={20} />
                  </button>
                  <button
                    onClick={() => handleDelete(product.id)}
                    className="text-red-600 hover:text-red-800"
                  >
                    <Trash2 size={20} />
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Modal de Edição */}
      {isModalOpen && editingProduct && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-lg max-w-2xl w-full p-6">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-2xl font-bold">
                {editingProduct.id ? 'Editar Produto' : 'Novo Produto'}
              </h2>
              <button
                onClick={() => setIsModalOpen(false)}
                className="text-gray-500 hover:text-gray-700"
              >
                <X size={24} />
              </button>
            </div>

            <form onSubmit={handleSave} className="space-y-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Nome do Produto
                </label>
                <input
                  type="text"
                  value={editingProduct.name}
                  onChange={e => setEditingProduct({ ...editingProduct, name: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Descrição
                </label>
                <textarea
                  value={editingProduct.description}
                  onChange={e => setEditingProduct({ ...editingProduct, description: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  rows={3}
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Preço
                </label>
                <input
                  type="number"
                  value={editingProduct.price}
                  onChange={e => setEditingProduct({ ...editingProduct, price: parseFloat(e.target.value) })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  step="0.01"
                  min="0"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Imagem
                </label>
                <div className="flex items-center gap-4">
                  <input
                    type="text"
                    value={editingProduct.image}
                    onChange={e => setEditingProduct({ ...editingProduct, image: e.target.value })}
                    className="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="URL da imagem"
                  />
                  <div className="relative">
                    <input
                      type="file"
                      onChange={handleImageChange}
                      accept="image/*"
                      className="hidden"
                      id="image-upload"
                    />
                    <label
                      htmlFor="image-upload"
                      className="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg cursor-pointer flex items-center gap-2 hover:bg-gray-200"
                    >
                      <Upload size={20} />
                      Upload
                    </label>
                  </div>
                </div>
                {editingProduct.image && (
                  <img
                    src={editingProduct.image}
                    alt="Preview"
                    className="mt-4 h-40 object-contain"
                  />
                )}
              </div>

              <div className="flex justify-end gap-4">
                <button
                  type="button"
                  onClick={() => setIsModalOpen(false)}
                  className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center gap-2"
                >
                  <Save size={20} />
                  Salvar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default AdminProducts;