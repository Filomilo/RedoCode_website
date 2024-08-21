namespace Healthcheck{
    export function healthCheckApi(){
        cy.origin('http://localhost:8080', () => {
            cy.visit('public/healthcheck/hello')
            cy.contains('hello')
    })
    }
}

export default Healthcheck;