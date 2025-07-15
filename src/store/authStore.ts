import  create  from 'zustand';
import { UserDTO } from '../dto/UserDTO';
import { UserDAO } from '../dao/UserDAO';

interface AuthState {
  user: UserDTO | null;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  checkAuth: () => Promise<void>;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  isLoading: true,
  
  login: async (email: string, password: string) => {
    try {
      const user = await UserDAO.login({ email, password });
      localStorage.setItem('token', user.token!);
      set({ user });
    } catch (error) {
      throw new Error('Invalid credentials');
    }
  },
  
  logout: () => {
    localStorage.removeItem('token');
    set({ user: null });
  },
  
  checkAuth: async () => {
    try {
      set({ isLoading: true });
      const user = await UserDAO.getCurrentUser();
      set({ user, isLoading: false });
    } catch {
      set({ user: null, isLoading: false });
    }
  },
}));