describe('template spec', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
  })
    const email="sunny@mail.com";
    const password="Password+123";

    const title="Cesar cipher";
    const description="move every letter in alphabet by 7 so a -> d and z - g, lower case and upper case letters should be handled"
  
    const cppSolution="#include <iostream>\n" +
    "#include <string>\n" +
    "\n" +
    "std::string solution(std::string input) {\n" +
    "    std::string result = \"\";\n" +
    "    int shift = 7;\n" +
    "\n" +
    "    for (char c : input) {\n" +
    "        if (isalpha(c)) { \n" +
    "            char base = islower(c) ? 'a' : 'A'; \n" +
    "            result += (c - base + shift) % 26 + base;\n" +
    "        } else {\n" +
    "            result += c;\n" +
    "        }\n" +
    "    }\n" +
    "    return result;\n" +
    "}";

    const inputsAndOutputs=[
      {
        input: "A",
        output: "H"
      },
      {
        input: "a",
        output: "h"
      },
      {
        input: "The quick brown fox jumps over a lazy dog",
        output: "Aol xbpjr iyvdu mve qbtwz vcly h shgf kvn"
      },
      {
        input: "The five boxing wizards jump quickly",
        output: "Aol mpcl ivepun dpghykz qbtw xbpjrsf"
      }
    ]


    cy.visit('/');
    cy.get("#login-email").clear().type(email)
    cy.get("#login-password").clear().type(password)
    cy.get('#login').click();
    cy.wait(1000);
    cy.get('#switch-exercises').click();
    cy.get("#Create-button").click();
    cy.get("#Exercise-title-input").click().type(title);
    cy.get("#Exercise-description-input").click().type(description);
    cy.get("#pv_id_5_1_header_action").click()
    cy.get("#language-selection").click();
    cy.get("#language-selection_0").click();
    cy.get("#language-selection_1").click();
    cy.get("#ms-number-input").click().type("222");
    cy.get("#hour-number-input").click().type("1");
    cy.get("#minute-number-input").click().type("22");
    cy.get("#ram-number-input").click().type("512");
    cy.get("#radio-input-string").click();
    cy.get("#radio-output-string").click();

    for (let index = 0; index < inputsAndOutputs.length; index++) {
      cy.get("#test-input-"+index+"-input").click().type(inputsAndOutputs[index].input);
      cy.get("#test-input-"+index+"-output").click().type(inputsAndOutputs[index].output);
      if(index+1< inputsAndOutputs.length)
      cy.get("#add-exercise-button").click();
    }
    cy.get("#amount-of-auto-test-input").click().type("6")
    cy.get("#string-range-low-input").click().clear().type("1")
    cy.get("#string-range-up-input").click().clear().type("20")
    cy.get("#number-checkbox").click();
    cy.get("#special-char-checkbox").click();
    cy.get("#character-breaks-checkbox").click();
    cy.get("#pv_id_5_2_header_action").click();
    cy.get("#coderunner-dropdown").click();
    cy.get("#coderunner-dropdown_0").click();
    cy.get("#connect-button").click();
    cy.get('#coderunner-editor-panel > div > div > div.overflow-guard > div.monaco-scrollable-element.editor-scrollable.vs-dark')
    .type(cppSolution)
    cy.get("#coderunner-run-button").click();

  })
})