import axios from 'axios';

// Interface para a resposta que esperamos do nosso backend
interface AuthResponse {
  token: string;
  nome: string;
  email: string;
  papel: string;
}

const AuthController = {
  // A função de login agora faz a chamada de API real
  login: async (email: string, password: string): Promise<AuthResponse> => {
    try {
      const response = await axios.post<AuthResponse>('/api/auth/login', {
        email: email,
        senha: password,
      });
      return response.data;
    } catch (error) {
      console.error('Erro no AuthController.login:', error);
      throw new Error('Credenciais inválidas');
    }
  },

  // A função de logout continua a mesma
  logout: () => {
    // A lógica de limpar o estado está no authStore,
    // então aqui apenas removemos os itens do localStorage se necessário.
    localStorage.removeItem('user_name');
    localStorage.removeItem('user_role');
    localStorage.removeItem('user_token'); // Limpa qualquer token antigo
  },

  // A função de verificação de autenticação continua a mesma
  isAuthenticated: (): boolean => {
    // A lógica real agora está no authStore, mas podemos manter uma verificação simples aqui
    return !!localStorage.getItem('user_token');
  },
};

export default AuthController;
