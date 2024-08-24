import Healthcheck from "./helpers/HealthCheck"

describe('connection to api test', () => {
  it('passes', () => {
    Healthcheck.healthCheckApi()
    })

  })

