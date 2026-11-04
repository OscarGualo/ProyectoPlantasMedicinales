import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// /api se envía al backend Spring Boot: mismo origen para la cookie de sesión y el CSRF.
// HUERTOS_API permite apuntar al backend del perfil e2e (puerto 8081) desde Playwright.
const destinoApi = process.env.HUERTOS_API ?? 'http://localhost:8080'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': destinoApi,
    },
  },
})
