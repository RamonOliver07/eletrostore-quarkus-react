import React from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import Header from './views/components/Header';
import Footer from './views/components/Footer';
import Home from './views/pages/Home';
import Products from './views/pages/Products';
import Categories from './views/pages/Categories';
import Cart from './views/pages/Cart';
import Login from './views/pages/Login';
import Register from './views/pages/Register';
import AdminProducts from './views/pages/admin/AdminProducts';
import { useAuthStore } from './store/authStore'; // <-- 1. Importa o authStore

function App() {
  // 2. Obtém os dados de autenticação
  const isAuthenticated = useAuthStore(state => state.isAuthenticated);
  const userRole = useAuthStore(state => state.user?.papel);

  return (
    <Router>
      <div className="min-h-screen bg-gray-50 flex flex-col">
        <Header />
        
        <main className="flex-grow">
          <Switch>
            {/* --- Rotas Públicas --- */}
            <Route exact path="/">
              <Home />
            </Route>
            <Route path="/produtos">
              <Products />
            </Route>
            <Route path="/categorias">
              <Categories />
            </Route>
            <Route path="/carrinho">
              <Cart />
            </Route>
            <Route path="/login">
              <Login />
            </Route>
            <Route path="/cadastro">
              <Register />
            </Route>
            
            {/* --- Rota Protegida para Administradores (Lógica Embutida) --- */}
            <Route path="/admin/produtos">
              {isAuthenticated && userRole === 'admin' ? (
                <AdminProducts />
              ) : (
                <Redirect to="/" />
              )}
            </Route>
            
          </Switch>
        </main>
        
        <Footer />
      </div>
    </Router>
  );
}

export default App;
