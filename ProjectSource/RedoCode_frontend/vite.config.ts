import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import path from 'node:path'

// https://vitejs.dev/config/
export default defineConfig({
  base: '',
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  // esbuild: {
  //   drop: ['console', 'debugger'],
  // },
  
  build: {
    // minify: false,
    rollupOptions: {
      output: {
        manualChunks(id) {
          const splits=id.split("/")
          const name=splits[splits.length-1];
         
          if (id.includes('src/components')) {
    
          return `components/${name}`
          }
          if (id.includes('src/controllers')) {
            return  `controllers/${name}`
          }
          if (id.includes('src/types')) {
            return `types/${name}`
          }
          if (id.includes('node_modules')) {
            return `node_modules/${name}`
          }
          if (id.includes('node_modules')) {
            return `node_modules/${name}`
          }
          if (id.includes('views')) {
            return `views/${name}`
          }
          
        },
      },
    },
  },
})
