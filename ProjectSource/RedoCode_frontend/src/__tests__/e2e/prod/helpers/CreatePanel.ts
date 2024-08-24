
namespace CreatePanel{


    export namespace infoSetup{

            export function clearTitle()
            {
                cy.get('#Exercise-title-input').click()
                cy.get('#Exercise-title-input').clear()
            }
        
            export function clearDescription(){
    cy.get('#Exercise-description-input').click()
    cy.get('#Exercise-description-input').clear()
            }

            export function clearAll()
            {
                clearTitle();
                clearDescription();
            }
        export function setTitle(title: string){
            clearTitle();
            cy.get('#Exercise-title-input').click()
            cy.get('#Exercise-title-input').type(title)
        }
        export function setDescription(description: string){
            clearDescription();
            cy.get('#Exercise-description-input').click()
            cy.get('#Exercise-description-input').type(description)
        }
    }
    

    export namespace TestConfig{

        export enum TYPES{
            STRING, INT, FLOAT
        }
        export enum SIZE{
            SINGLE, ARRAY, DOUBLE_ARRAY
        }

        export interface StringInputOptions{
            UpperCase: boolean,
            LowerCase: boolean,
            Number: boolean,
            SpecialChar: boolean,
            breakCharacter:boolean,
            spaceCharacter: boolean
        }
    

        export function selectLanguages(langauges: string[])
        {
            cy.get('#language-selection').click()
            langauges.forEach(x=>{
                cy.get('span').contains(x).click()
            })
        }


        export function isTestPanelActive(state: boolean)
        {
            cy.get('#ContentConatiner span')
            .contains('Setup')
            .closest('.p-tabview-nav-link')
            .should('have.attr', 'aria-disabled', state?'false':'true')
        }

        export function switchToTestConfig(){
            cy.get('#ContentConatiner span')
            .contains('Setup')
            .closest('.p-tabview-nav-link')
            .click();
        }


        export function setExecutionTime(amount: number){
            cy.get('#ms-number-input > input').click()
            cy.get('#ms-number-input > input').clear().type(amount.toString())
        }
        export function setMinutesTime(amount: number){
            cy.get('#minute-number-input > input').click()
            cy.get('#minute-number-input > input').clear().type(amount.toString())
        }
        export function setHoursTime(amount: number){
            cy.get('#hour-number-input > input').click()
            cy.get('#hour-number-input > input').clear().type(amount.toString())
        }
        export function setRam(amount: number){
            cy.get('#ram-number-input > input').click()
            cy.get('#ram-number-input > input').clear().type(amount.toString())
        }

        export function setInputType(type: TYPES, size: SIZE)
        {
            switch(type)
            {
                case TYPES.STRING:  cy.get('#radio-input-string').click(); break;
                case TYPES.INT: cy.get('#radio-input-int').click(); break;
                case TYPES.FLOAT:  cy.get('#radio-input-float').click(); break;
            }

            switch(size)
            {
                case SIZE.SINGLE:  cy.get('#radio-input-single').click(); break;
                case SIZE.ARRAY:   cy.get('#radio-input-array').click(); break;
                case SIZE.DOUBLE_ARRAY:  cy.get('#radio-input-2d-array').click(); break;
            }
        }
        
        export function setOutputType(type: TYPES, size: SIZE)
        {
            switch(type)
            {
                case TYPES.STRING:  cy.get('#radio-output-string').click(); break;
                case TYPES.INT: cy.get('#radio-output-int').click(); break;
                case TYPES.FLOAT:  cy.get('#radio-output-float').click(); break;
            }

            switch(size)
            {
                case SIZE.SINGLE:  cy.get('#radio-output-single').click(); break;
                case SIZE.ARRAY:   cy.get('#radio-output-array').click(); break;
                case SIZE.DOUBLE_ARRAY:  cy.get('#radio-output-2d-array').click(); break;
            }
        }




        export function createTests(tests:any[])
        {
                for (let index = 0; index < tests.length; index++) {
      cy.get('#add-exercise-button').click()
      cy.get('#test-input-' + index + '-input').click()
      cy.get('#test-input-' + index + '-input').clear()
      cy.get('#test-input-' + index + '-input').type(
        tests[index].input
      )
      cy.get('#test-input-' + index + '-output').click()
      cy.get('#test-input-' + index + '-output').clear()
      cy.get('#test-input-' + index + '-output').type(
        tests[index].output
      )
    }
        }


        export function setAmountOfAutoTests(amount: number)
        {
         cy.get('#amount-of-auto-test-input > input').click()
         cy.get('#amount-of-auto-test-input > input').clear().type(amount.toString())
        }

        export function setLengthMin(amount: number)
        {
            cy.get('#string-range-low-input > input').click()
            cy.get('#string-range-low-input > input').clear()
            cy.get('#string-range-low-input > input').type(amount.toString())
        }
        export function setLengthMax(amount: number)
        {
            cy.get('#string-range-up-input > input').click()
            cy.get('#string-range-up-input > input').clear()
            cy.get('#string-range-up-input > input').type(amount.toString())
        }

        export function setStringInputOptions(options: StringInputOptions)
        {
            if(options.LowerCase){
                cy.get('#lower-case-checkbox > input').check()
                cy.get('#lower-case-checkbox > input').should('be.checked')
            }
            else{
                cy.get('#lower-case-checkbox > input').uncheck()
                cy.get('#lower-case-checkbox > input').should('not.be.checked')
            }

            if(options.Number){
                cy.get('#number-checkbox > input').check()
                cy.get('#number-checkbox > input').should('be.checked')
            }
            else{
                cy.get('#number-checkbox > input').uncheck()
                cy.get('#number-checkbox > input').should('not.be.checked')
            }

            if(options.SpecialChar){
                cy.get('#special-char-checkbox > input').check()
                cy.get('#special-char-checkbox > input').should('be.checked')
            }
            else{
                cy.get('#special-char-checkbox > input').uncheck()
                cy.get('#special-char-checkbox > input').should('not.be.checked')
            }
            
            if(options.UpperCase){
                cy.get('#upper-case-checkbox > input').check()
                cy.get('#upper-case-checkbox > input').should('be.checked')
            }
            else{
                cy.get('#upper-case-checkbox > input').uncheck()
                cy.get('#upper-case-checkbox > input').should('not.be.checked')
            }
            if(options.breakCharacter){
                cy.get('#character-breaks-checkbox > input').check()
                cy.get('#character-breaks-checkbox > input').should('be.checked')
            }
            else{
                cy.get('#character-breaks-checkbox > input').uncheck()
                cy.get('#character-breaks-checkbox > input').should('not.be.checked')
            }

            if(options.spaceCharacter){
                cy.get('#space-char-checkbox > input').check()
                cy.get('#space-char-checkbox > input').should('be.checked')
            }
            else{
                cy.get('#space-char-checkbox > input').uncheck()
                cy.get('#space-char-checkbox > input').should('not.be.checked')
            }
        }

        }


        export namespace SolutionPanel{
            export function isSolutionPanelActive(state: boolean)
            {
                cy.get('#ContentConatiner span')
                .contains('Solution')
                .closest('.p-tabview-nav-link')
                .should('have.attr', 'aria-disabled', state?'false':'true')
            }
            export function switchToTestSolution(){
                cy.get('#ContentConatiner span')
                .contains('Solution')
                .closest('.p-tabview-nav-link')
                .click();
            }
    
    
        }


    }




export default CreatePanel;