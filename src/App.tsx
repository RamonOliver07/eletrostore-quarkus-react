import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Header from './views/components/Header';
import Footer from './views/components/Footer';
import Home from './views/pages/Home';
import Products from './views/pages/Products';
import Categories from './views/pages/Categories';
import About from './views/pages/About';
import Contact from './views/pages/Contact';
import Cart from './views/pages/Cart';
import Login from './views/pages/Login';
import Register from './views/pages/Register';
import AdminProducts from './views/pages/admin/AdminProducts';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50 flex flex-col">
        <Header />
        
        {/* O main flex-grow garante que o footer fique no final da página */}
        <main className="flex-grow">
          <Switch>
            <Route exact path="/">
              <Home />
            </Route>
            <Route path="/produtos">
              <Products />
            </Route>
            <Route path="/categorias">
              <Categories />
            </Route>
            <Route path="/sobre">
              <About />
            </Route>
            <Route path="/contato">
              <Contact />
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
            <Route path="/admin/produtos">
              <AdminProducts />
            </Route>
          </Switch>
        </main>
        
        <Footer />
      </div>
    </Router>
  );
}

export default App;