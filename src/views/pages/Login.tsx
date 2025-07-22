import React, { useState } from 'react';
import { useHistory, Link } from 'react-router-dom';
import { useAuthStore } from '../../store/authStore';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const history = useHistory();
  const login = useAuthStore(state => state.login);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    
    try {
      // Agora a chamada real para o backend é executada
      await login(email, password);
      history.push('/'); // Redireciona para a home após o login
    } catch (err) {
      setError('E-mail ou senha inválidos.');
      console.error("Falha no login:", err); // Adiciona um log para depuração
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8 bg-gray-800 p-8 rounded-xl shadow-2xl">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-white">
            Aceda à sua conta
          </h2>
        </div>
        
        {error && (
          <div className="bg-red-500 text-white p-3 rounded-md text-sm">
            {error}
          </div>
        )}
        
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div className="rounded-md shadow-sm -space-y-px">
            <div>
              <label htmlFor="email" className="sr-only">Endereço de e-mail</label>
              <input
                id="email"
                name="email"
                type="email"
                required
                className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-700 placeholder-gray-500 text-white bg-gray-700 rounded-t-md focus:outline-none focus:ring-blue-500 focus:border-blue-500 focus:z-10 sm:text-sm"
                placeholder="Endereço de e-mail"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            <div>
              <label htmlFor="password" className="sr-only">Senha</label>
              <input
                id="password"
                name="password"
                type="password"
                required
                className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-700 placeholder-gray-500 text-white bg-gray-700 rounded-b-md focus:outline-none focus:ring-blue-500 focus:border-blue-500 focus:z-10 sm:text-sm"
                placeholder="Senha"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Entrar
            </button>
          </div>
        </form>

        <div className="text-sm text-center">
          <span className="text-gray-400">Não tem uma conta? </span>
          <Link to="/cadastro" className="font-medium text-blue-400 hover:text-blue-300">
            Cadastre-se
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Login;
