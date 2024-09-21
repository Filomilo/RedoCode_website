
namespace StatsPanel {
 
    export function statsPanelShouldNotBeVisilbe()
    {
        cy.get("#NoLanugageData").should('be.visible');
    }

}
export default StatsPanel
