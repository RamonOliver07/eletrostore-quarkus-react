import type { Product, Category } from '../models/Product';

class ProductController {
  private static BASE_URL = 'http://localhost:8080/api/produtos';

  static async getProducts(): Promise<Product[]> {
    const response = await fetch(this.BASE_URL);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  }

  static async getFeaturedProducts(): Promise<Product[]> {
    const response = await fetch(`${this.BASE_URL}/destaques`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  }

  static async getProductsByCategory(categoryId: number): Promise<Product[]> {
    const response = await fetch(`${this.BASE_URL}/categoria/${categoryId}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  }

  static async searchProducts(query: string): Promise<Product[]> {
    const response = await fetch(`${this.BASE_URL}/busca?nome=${encodeURIComponent(query)}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  }

  static async getProduct(id: number): Promise<Product> {
    const response = await fetch(`${this.BASE_URL}/${id}`);
    if (!response.ok) {
      throw new Error('Produto não encontrado');
    }
    return response.json();
  }
}

export default ProductController;