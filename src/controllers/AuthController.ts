import type { User } from '../models/User';

class AuthController {
  private static BASE_URL = '/api/auth';

  static async login(email: string, senha: string): Promise<{ token: string; user: User }> {
    const response = await fetch(`${this.BASE_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, senha })
    });

    if (!response.ok) {
      throw new Error('Credenciais inválidas');
    }

    const data = await response.json();
    return {
      token: data.token,
      user: {
        nome: data.nome,
        email: data.email,
        papel: data.papel
      }
    };
  }

  static logout(): void {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user_name');
    localStorage.removeItem('user_role');
  }

  static isAuthenticated(): boolean {
    return !!localStorage.getItem('auth_token');
  }
}

export default AuthController;