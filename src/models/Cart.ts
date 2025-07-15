interface CartItem {
  produtoId: number;
  quantidade: number;
}

interface CartSummary {
  itens: CartItemResponse[];
  subtotal: number;
  frete: number;
  total: number;
}

interface CartItemResponse {
  produtoId: number;
  nome: string;
  imagem: string;
  preco: number;
  quantidade: number;
  subtotal: number;
}

export type { CartItem, CartSummary, CartItemResponse };