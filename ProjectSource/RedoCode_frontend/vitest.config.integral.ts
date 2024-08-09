import { defineConfig } from 'vitest/config';
import { fileURLToPath } from 'url';

import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import { resolve } from 'path';
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
    },
  },
  
  test: {
    environment: 'jsdom',
    include: ['src/__tests__/integral/**/*.spec.ts'], 
    exclude: ['e2e/*'],
    root: fileURLToPath(new URL('./src', import.meta.url))
  },
});

