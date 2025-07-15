import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
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
      <div className="min-h-screen bg-gray-50">
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/produtos" element={<Products />} />
          <Route path="/categorias" element={<Categories />} />
          <Route path="/sobre" element={<About />} />
          <Route path="/contato" element={<Contact />} />
          <Route path="/carrinho" element={<Cart />} />
          <Route path="/login" element={<Login />} />
          <Route path="/cadastro" element={<Register />} />
          <Route path="/admin/produtos" element={<AdminProducts />} />
        </Routes>
        <Footer />
      </div>
    </Router>
  );
}

export default App;