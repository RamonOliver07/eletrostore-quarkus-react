import axios from 'axios';
import { ProductDTO, CategoryDTO } from '../dto/ProductDTO';

const API_URL = '/api';

export class ProductDAO {
  static async getProducts(): Promise<ProductDTO[]> {
    const response = await axios.get(`${API_URL}/products`);
    return response.data;
  }

  static async getProduct(id: number): Promise<ProductDTO> {
    const response = await axios.get(`${API_URL}/products/${id}`);
    return response.data;
  }

  static async createProduct(product: ProductDTO): Promise<ProductDTO> {
    const response = await axios.post(`${API_URL}/products`, product);
    return response.data;
  }

  static async updateProduct(id: number, product: ProductDTO): Promise<ProductDTO> {
    const response = await axios.put(`${API_URL}/products/${id}`, product);
    return response.data;
  }

  static async deleteProduct(id: number): Promise<void> {
    await axios.delete(`${API_URL}/products/${id}`);
  }

  static async getCategories(): Promise<CategoryDTO[]> {
    const response = await axios.get(`${API_URL}/categories`);
    return response.data;
  }
}