import React from 'react';
import { Facebook, Instagram, Twitter, Youtube } from 'lucide-react';

function Footer() {
  return (
    <footer className="bg-gray-900 text-gray-300 py-12">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <h3 className="text-xl font-bold mb-4">
              <span className="text-blue-500">Eletro</span>
              <span className="text-orange-500">Store</span>
            </h3>
            <p className="text-sm">
              A sua loja de eletrônicos favorita com os melhores preços do mercado.
            </p>
          </div>
          
          <div>
            <h4 className="font-semibold mb-4">Links Rápidos</h4>
            <ul className="space-y-2">
              <li><a href="/" className="hover:text-white">Home</a></li>
              <li><a href="/produtos" className="hover:text-white">Produtos</a></li>
              <li><a href="/categorias" className="hover:text-white">Categorias</a></li>
              <li><a href="/sobre" className="hover:text-white">Sobre Nós</a></li>
              <li><a href="/contato" className="hover:text-white">Contato</a></li>
            </ul>
          </div>
          
          <div>
            <h4 className="font-semibold mb-4">Categorias</h4>
            <ul className="space-y-2">
              <li><a href="/produtos?categoria=smartphones" className="hover:text-white">Smartphones</a></li>
              <li><a href="/produtos?categoria=notebooks" className="hover:text-white">Notebooks</a></li>
              <li><a href="/produtos?categoria=tablets" className="hover:text-white">Tablets</a></li>
              <li><a href="/produtos?categoria=acessorios" className="hover:text-white">Acessórios</a></li>
            </ul>
          </div>
          
          <div>
            <h4 className="font-semibold mb-4">Contato</h4>
            <ul className="space-y-2 text-sm">
              <li>Av. Tecnologia, 1500 - São Paulo, SP</li>
              <li>(11) 3456-7890</li>
              <li>contato@eletrostore.com.br</li>
            </ul>
            <div className="flex space-x-4 mt-4">
              <a href="#" className="hover:text-white"><Facebook size={20} /></a>
              <a href="#" className="hover:text-white"><Instagram size={20} /></a>
              <a href="#" className="hover:text-white"><Twitter size={20} /></a>
              <a href="#" className="hover:text-white"><Youtube size={20} /></a>
            </div>
          </div>
        </div>
        
        <div className="border-t border-gray-800 mt-8 pt-8 flex flex-col md:flex-row justify-between items-center">
          <p className="text-sm">© 2025 EletroStore - Todos os direitos reservados</p>
          <div className="flex space-x-4 mt-4 md:mt-0">
            <span className="text-sm">Formas de Pagamento</span>
            {/* Payment method icons would go here */}
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;