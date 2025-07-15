interface Product {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  estoque: number;
  imagem: string;
  marca: string;
  modelo: string;
  destaque: boolean;
  categoria: Category;
}

interface Category {
  id: number;
  nome: string;
  descricao?: string;
  icone?: string;
}

export type { Product, Category };