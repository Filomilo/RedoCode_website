import { defineConfig } from 'cypress'
import installLogsPrinter from 'cypress-terminal-report/src/installLogsPrinter';
export default defineConfig({
  e2e: {
    specPattern: 'src/__tests__/e2e/prod/**/*.{cy,spec}.{js,jsx,ts,tsx}',
    baseUrl: 'http://localhost',
    defaultCommandTimeout: 100000,
    supportFile: "src/__tests__/e2e/config/support.ts",
    experimentalMemoryManagement: true,
    video: true,
    screenshotOnRunFailure: true,
    setupNodeEvents(on, config) {
      installLogsPrinter(on,{
        logToFilesOnAfterRun: true,
        printLogsToConsole: 'always',
        printLogsToFile: 'always',
        outputRoot: config.projectRoot + '/logs/',
        outputTarget: {
          'out.json': 'json',
      }
      });
    }
  },
})
