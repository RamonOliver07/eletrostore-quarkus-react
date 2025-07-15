export interface UserDTO {
  id?: number;
  email: string;
  name: string;
  role: 'admin' | 'user';
  token?: string;
}

export interface LoginDTO {
  email: string;
  password: string;
}

export interface RegisterDTO extends LoginDTO {
  name: string;
  confirmPassword: string;
}