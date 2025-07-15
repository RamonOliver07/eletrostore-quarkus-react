import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-refresh'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // Redireciona todas as requisições que começam com /api
      '/api': {
        target: 'http://localhost:8080', // O endereço do seu backend Quarkus
        changeOrigin: true,
        secure: false
      }
    }
  }
})
