
import React from 'react';
import { ShoppingCart, User, Search, Menu } from 'lucide-react';
import { useState } from 'react';
import { Link, useHistory } from "react-router-dom";
import AuthController from '../../controllers/AuthController';

function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const history = useHistory();
  const isAuthenticated = AuthController.isAuthenticated();
  const userName = localStorage.getItem('user_name');

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      history.push(`/produtos?busca=${encodeURIComponent(searchQuery.trim())}`);
    }
  };

  const handleLogout = () => {
    AuthController.logout();
    history.push('/');
  };

  return (
    <header className="bg-white shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex-shrink-0 flex items-center">
            <Link to="/" className="text-2xl font-bold">
              <span className="text-blue-700">Eletro</span>
              <span className="text-orange-500">Store</span>
            </Link>
          </div>

          <div className="hidden md:block flex-1 max-w-lg mx-8">
            <form onSubmit={handleSearch} className="relative">
              <input
                type="text"
                placeholder="O que você está procurando?"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-4 pr-10 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
              <button type="submit" className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600">
                <Search size={20} />
              </button>
            </form>
          </div>

          <nav className="hidden md:flex items-center space-x-8">
            <Link to="/" className="text-gray-700 hover:text-blue-700">Home</Link>
            <Link to="/produtos" className="text-gray-700 hover:text-blue-700">Produtos</Link>
            <Link to="/categorias" className="text-gray-700 hover:text-blue-700">Categorias</Link>
            <Link to="/contato" className="text-gray-700 hover:text-blue-700">Contato</Link>
          </nav>

          <div className="flex items-center space-x-6">
            <Link to="/carrinho" className="text-gray-700 hover:text-blue-700 relative">
              <ShoppingCart size={24} />
              <span className="absolute -top-2 -right-2 bg-orange-500 text-white text-xs font-bold rounded-full w-5 h-5 flex items-center justify-center">
                3
              </span>
            </Link>
            {isAuthenticated ? (
              <div className="relative">
                <button
                  onClick={() => setIsMenuOpen(!isMenuOpen)}
                  className="text-gray-700 hover:text-blue-700 flex items-center space-x-2"
                >
                  <User size={24} />
                  <span className="hidden md:inline">{userName}</span>
                </button>
                {isMenuOpen && (
                  <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1">
                    <Link to="/perfil" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Meu Perfil</Link>
                    <Link to="/pedidos" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Meus Pedidos</Link>
                    <button
                      onClick={handleLogout}
                      className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      Sair
                    </button>
                  </div>
                )}
              </div>
            ) : (
              <Link to="/login" className="text-gray-700 hover:text-blue-700 flex items-center space-x-2">
                <User size={24} />
                <span className="hidden md:inline">Entrar</span>
              </Link>
            )}
            <button
              className="md:hidden text-gray-700 hover:text-blue-700"
              onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
              <Menu size={24} />
            </button>
          </div>
        </div>

        {isMenuOpen && (
          <div className="md:hidden py-4">
            <form onSubmit={handleSearch} className="mb-4">
              <div className="relative">
                <input
                  type="text"
                  placeholder="O que você está procurando?"
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className="w-full pl-4 pr-10 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
                <button type="submit" className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600">
                  <Search size={20} />
                </button>
              </div>
            </form>
            <nav className="flex flex-col space-y-4">
              <Link to="/" className="text-gray-700 hover:text-blue-700">Home</Link>
              <Link to="/produtos" className="text-gray-700 hover:text-blue-700">Produtos</Link>
              <Link to="/categorias" className="text-gray-700 hover:text-blue-700">Categorias</Link>
              <Link to="/contato" className="text-gray-700 hover:text-blue-700">Contato</Link>
            </nav>
          </div>
        )}
      </div>
    </header>
  );
}

export default Header;