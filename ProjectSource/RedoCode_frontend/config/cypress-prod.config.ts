import { defineConfig } from 'cypress'

export default defineConfig({
  e2e: {
    specPattern: 'src/__tests__/e2e/prod/**/*.{cy,spec}.{js,jsx,ts,tsx}',
    baseUrl: 'http://localhost',
    defaultCommandTimeout: 1000000,
    supportFile: false,
  },
}


)
