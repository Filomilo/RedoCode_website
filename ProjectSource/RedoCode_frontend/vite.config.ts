import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
function getFolder(id: string) {
  if (id === undefined) return ''
  const splits = id.split('/')
  return splits[splits.length - 2]
}

function getFileName(id: string) {
  if (id === undefined) return ''
  const splits = id.split('/')
  return splits[splits.length - 1].split('.')[0]
}
function getExtension(id: string) {
  if (id === undefined) return ''
  const splits = id.split('/')
  return splits[splits.length - 1].split('.')[1]
}

export default defineConfig({
  base: '',
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  esbuild: {
    drop: ['console', 'debugger'],
  },

  build: {
    target: "esnext",
    modulePreload: {
      resolveDependencies: () => [],
    },
    // minify: false,
    rollupOptions: {
      output: {
        manualChunks(id) {
          console.log('getFileName: ' + id)

          const folder = getFolder(id)
          const name = getFileName(id)
          const extension = getExtension(id)

          // console.log("folder: "+folder);
          // console.log("name: "+name);
          // console.log("extension: "+extension);
          if (id.includes('node_modules')) {
            if (id.includes('@vue')) return 'vendor/vendor-vue'
            if (id.includes('primevue')) {
              return 'vendor/primevue/vendor-primevue-' + folder
            }
            if (id.includes('chart')) return 'vendor/vendor-chart'

            return 'vendor/vendor'
          }

          if (folder === 'components') {
            return 'components'
          }

          if (folder === 'views') {
            return 'views/' + name
          }

          if (id.includes('src/components')) {
            return 'components'
          }
          if (id.includes('src/controllers')) {
            return 'controllers'
          }
          if (id.includes('src/types')) {
            return 'types'
          }
        },
      },
    },
  },
})
