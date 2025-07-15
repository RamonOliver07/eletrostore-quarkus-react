import axios from 'axios';
import { LoginDTO, RegisterDTO, UserDTO } from '../dto/UserDTO';

const API_URL = '/api';

export class UserDAO {
  static async login(credentials: LoginDTO): Promise<UserDTO> {
    const response = await axios.post(`${API_URL}/auth/login`, credentials);
    return response.data;
  }

  static async register(userData: RegisterDTO): Promise<UserDTO> {
    const response = await axios.post(`${API_URL}/auth/register`, userData);
    return response.data;
  }

  static async getCurrentUser(): Promise<UserDTO | null> {
    try {
      const response = await axios.get(`${API_URL}/auth/me`);
      return response.data;
    } catch {
      return null;
    }
  }
}