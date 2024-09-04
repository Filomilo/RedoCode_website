namespace SolutionsList{


    export function shouldTitleBe(should: string){
        cy.get('#solutions-exercise-title').should("be.visible")
        cy.get('#solutions-exercise-title').contains(should)
    }
    export function shouldDescBe(should: string){
        
        cy.get('#solutions-exercise-desc').contains(should)
    }

    export function solutionListClick(index: number)
    {
        cy.get( '#solution-list-item-'+index).click();
       
    }

    export function solutionItemListShouldBeSelected(index: number)
    {
        cy.get( '#solution-list-item-'+index).should("have.class","Selected")
       
    }

    export function shouldUsernameCommentBe(index: number,nick: string){
        cy.get( '#comment-nick-'+index).contains(nick);

    }
    export function shouldContentCommentBe(index: number,nick: string){
        cy.get( '#comment-content-'+index).contains(nick);

    }

    export function postComment(content: string){
        cy.get("#comment-input").click().type(content);
        cy.get("#comment-post").click();
        }

}

export default  SolutionsList;