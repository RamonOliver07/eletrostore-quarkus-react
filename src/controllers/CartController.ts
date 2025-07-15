import type { CartItem, CartSummary } from '../models/Cart';

class CartController {
  private static BASE_URL = '/api/carrinho';

  static async calculateCart(items: CartItem[]): Promise<CartSummary> {
    const response = await fetch(`${this.BASE_URL}/calcular`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(items)
    });
    return response.json();
  }

  static async verifyStock(items: CartItem[]): Promise<boolean> {
    const response = await fetch(`${this.BASE_URL}/verificar-estoque`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(items)
    });
    const data = await response.json();
    return data.status === 'OK';
  }
}

export default CartController;