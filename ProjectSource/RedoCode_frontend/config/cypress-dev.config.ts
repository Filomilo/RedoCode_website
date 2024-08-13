import { defineConfig } from 'cypress'

export default defineConfig({
  e2e: {
    specPattern: 'src/__tests__/e2e/dev/**/*.{cy,spec}.{js,jsx,ts,tsx}',
    baseUrl: 'http://localhost:4173',
    supportFile: false,
  },
}


)
