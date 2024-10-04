namespace ResultPage {
  export function shouldBeVisible() {
    cy.get('#code_rating_panel', { timeout: 15000 }).should('be.visible')
  }

  export namespace ResultPanel {
    export function ExecutionTimeShouldEqual(val: number) {
      cy.get('#execution-time-donout > div > label')
        .invoke('text')
        .then(x => {
          const value = parseFloat(x.slice(0, -2))
          expect(value).to.eq(val)
        })
    }

    export function ExecutionTimeShouldBeLess(val: number) {
      cy.get('#execution-time-donout > div > label')
        .invoke('text')
        .then(x => {
          const value = parseFloat(x.slice(0, -2))
          expect(value).to.lessThan(val)
        })
    }
    export function ExecutionTimeShouldBeGreater(val: number) {
      cy.get('#execution-time-donout > div > label')
        .invoke('text')
        .then(x => {
          const value = parseFloat(x.slice(0, -2))
          expect(value).to.greaterThan(val)
        })
    }
    export function SolutionBetterProcetShouldBE(val: number) {
      cy.get('#Better-than-donout > div > label')
        .invoke('text')
        .then(x => {
          const value = parseFloat(x.slice(0, -1))
          expect(value).to.equal(val)
        })
    }

    export function SolutionBetterProcetShouldGreater(val: number) {
      cy.get('#Better-than-donout > div > label')
        .invoke('text')
        .then(x => {
          const value = parseFloat(x.slice(0, -1))
          expect(value).to.greaterThan(val)
        })
    }

    export function resulrRankShouldBe(val: number) {
      cy.get('#result-code-rate')
        .invoke('text')
        .then(x => {
          const value = parseFloat(x)
          expect(value).to.equal(val)
        })
    }

    export function shouldBeVisible() {}
  }

  export namespace Rating {
    export function clickRating(index: number) {
      // cy.document().then((doc) => {
      //   const htmlContent = doc.documentElement.outerHTML;
      //   cy.log(htmlContent)
      //   cy.writeFile('cypress/results/snapshot.html', htmlContent);
      // });

      cy.get('#Result-rate-' + index).should('be.visible');
      cy.get('#Result-rate-' + index).should('be.visible').click()
    }

    export function selectedRating(label: string) {
      // cy.document().then((doc) => {
      //   const htmlContent = doc.documentElement.outerHTML;
      //   cy.log(htmlContent)
      //  cy.writeFile('cypress/results/snapshot.html', htmlContent);
      // });
      cy.get('#Result-rate-selection').should('be.visible');
      cy.get('#Result-rate-selection').should('be.visible').contains(label)
    }

    export function clickRate() {
      cy.get('#save-rate-click').click()
    }
  }
}

export default ResultPage
