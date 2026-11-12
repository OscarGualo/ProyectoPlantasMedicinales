import { defineConfig, devices } from '@playwright/test'

/**
 * Pruebas en navegador de los casos del doc 04 (frontend/e2e). Levanta su propio backend con el perfil e2e
 * (puerto 8081, base huertos_e2e, reloj fijo 31/10/2026 12:00) y su propio Vite (puerto 5174), sin chocar con los
 * servidores de desarrollo. Requiere Docker con PostgreSQL en marcha (docker compose up -d).
 */
const windows = process.platform === 'win32'
const API = 'http://localhost:8081'

export default defineConfig({
  testDir: './e2e',
  fullyParallel: false, // todas las pruebas comparten la misma base restaurada
  workers: 1,
  retries: process.env.CI ? 1 : 0,
  reporter: [['list'], ['html', { open: 'never' }]],
  use: {
    baseURL: 'http://localhost:5174',
    locale: 'es-EC',
    timezoneId: 'America/Guayaquil',
    trace: 'retain-on-failure',
    screenshot: 'only-on-failure',
  },
  projects: [
    {
      name: 'escritorio',
      use: {
        ...devices['Desktop Chrome'],
        // WebGL por software para la escena 3D en el navegador sin pantalla.
        launchOptions: { args: ['--enable-unsafe-swiftshader', '--use-angle=swiftshader'] },
      },
    },
  ],
  webServer: [
    {
      command: windows
        ? '.\\mvnw.cmd -q spring-boot:run -Dspring-boot.run.profiles=e2e'
        : './mvnw -q spring-boot:run -Dspring-boot.run.profiles=e2e',
      cwd: '../backend',
      url: `${API}/api/sesion`,
      reuseExistingServer: !process.env.CI,
      timeout: 240_000,
    },
    {
      command: 'node node_modules/vite/bin/vite.js --port 5174 --strictPort',
      url: 'http://localhost:5174',
      env: { HUERTOS_API: API },
      reuseExistingServer: !process.env.CI,
      timeout: 60_000,
    },
  ],
})
