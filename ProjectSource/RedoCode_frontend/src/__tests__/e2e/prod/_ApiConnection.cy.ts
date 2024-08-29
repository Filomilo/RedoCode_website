import Healthcheck from './helpers/HealthCheck'

describe.skip('connection to api test', () => {
  it('passes', () => {
    Healthcheck.healthCheckApi()
  })
})
