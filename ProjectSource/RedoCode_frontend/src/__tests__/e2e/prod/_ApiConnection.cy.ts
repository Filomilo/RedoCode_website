describe('connection to api test', () => {
  it('passes', () => {
    cy.origin('http://localhost:8080', () => {
      cy.visit('public/healthcheck/hello')
      cy.contains('hello')
    })

  })
})

