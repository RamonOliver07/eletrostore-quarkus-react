interface User {
  id: number;
  nome: string;
  email: string;
  papel: string;
  telefone?: string;
  endereco?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
}

export type { User };