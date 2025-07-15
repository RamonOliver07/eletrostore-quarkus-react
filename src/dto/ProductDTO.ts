export interface ProductDTO {
  id?: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  image: string;
  categoryId: number;
  featured: boolean;
}

export interface CategoryDTO {
  id: number;
  name: string;
  description?: string;
}