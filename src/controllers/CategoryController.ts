import type { Category } from '../models/Product';

class CategoryController {
  private static BASE_URL = '/api/categorias';

  static async getCategories(): Promise<Category[]> {
    const response = await fetch(this.BASE_URL);
    return response.json();
  }

  static async getCategory(id: number): Promise<Category> {
    const response = await fetch(`${this.BASE_URL}/${id}`);
    if (!response.ok) {
      throw new Error('Categoria não encontrada');
    }
    return response.json();
  }
}

export default CategoryController;