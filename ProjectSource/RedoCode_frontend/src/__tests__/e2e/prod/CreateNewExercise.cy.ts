import CodeRunnerInput from "./helpers/CodeRunnerInput"

describe('Create new exercise', () => {
  it('passes', () => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    })
    const email = 'sunny@mail.com'
    const password = 'Password+123'

    const title = 'Cesar cipher'
    const description =
      'move every letter in alphabet by 7 so a -> d and z - g, lower case and upper case letters should be handled'



    // for (let index = 0; index < 100; index++) {
    //   backspaces+="{delete}";

    // }

    const cppSolution =
      '#include <iostream>\n' +
      '#include <string>\n' +
      '\n' +
      'std::string solution(std::string input) {\n' +
      '    std::string result = "";\n' +
      '    int shift = 7;\n' +
      '\n' +
      '    for (char c : input) {\n' +
      '        if (isalpha(c)) { \n' +
      "            char base = islower(c) ? 'a' : 'A'; \n" +
      '            result += (c - base + shift) % 26 + base;\n' +
      '        } else {\n' +
      '            result += c;\n' +
      '        }\n' +
      '    }\n' +
      '    return result;\n' +
      '}'

    const jsSolution =
      'function solution(input) {\n' +
      '    let result = "";\n' +
      '    const shift = 7;\n' +
      '\n' +
      '    for (let i = 0; i < input.length; i++) {\n' +
      '        let c = input[i];\n' +
      '        if (/[a-zA-Z]/.test(c)) {\n' +
      "            let base = c >= 'a' && c <= 'z' ? 'a'.charCodeAt(0) : 'A'.charCodeAt(0);\n" +
      '            result += String.fromCharCode((c.charCodeAt(0) - base + shift) % 26 + base);\n' +
      '        } else {\n' +
      '            result += c;\n' +
      '        }\n' +
      '    }\n' +
      '\n' +
      '    return result;\n' +
      '}\n' +
      '\n' +
      '// Example usage:\n' +
      'const input = "Hello, World!";\n' +
      'const output = solution(input);\n' +
      'console.log(output); // Outputs: "Olssv, Dvysk!"'

    const midpoint = Math.ceil(jsSolution.length / 2)

    const JSfirstHalf = jsSolution.slice(0, midpoint)
    const JSsecondHalf = jsSolution.slice(midpoint)

    const inputsAndOutputs = [
      {
        input: 'A',
        output: 'H',
      },
      {
        input: 'a',
        output: 'h',
      },
      {
        input: 'The quick brown fox jumps over a lazy dog',
        output: 'Aol xbpjr iyvdu mve qbtwz vcly h shgf kvn',
      },
      {
        input: 'The five boxing wizards jump quickly',
        output: 'Aol mpcl ivepun dpghykz qbtw xbpjrsf',
      },
    ]

    cy.visit('/')
    cy.get('#login-email').clear()
    cy.get('#login-email').type(email)
    cy.get('#login-password').clear()
    cy.get('#login-password').type(password)
    cy.get('#login').click()
    cy.get('.p-toast-detail')
      .contains('Succesfully logged in')
      .should('be.visible')
    cy.get('.p-toast-detail').should('not.exist')
    cy.get('#switch-exercises').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Exercises')
    cy.get('#Create-button').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Create')
    cy.get('.p-toast-detail')
      .contains('successfully connected')
      .should('be.visible')
    cy.get('.p-toast-detail').should('not.exist')
    cy.get('#Exercise-title-input').click()
    cy.get('#Exercise-title-input').clear()
    cy.get('#Exercise-description-input').click()
    cy.get('#Exercise-description-input').clear()
    cy.get('#ContentConatiner span')
      .contains('Setup')
      .closest('.p-tabview-nav-link')
      .should('have.attr', 'aria-disabled', 'true')
    cy.get('#Exercise-title-input').click()
    cy.get('#Exercise-title-input').type(title)
    cy.get('#Exercise-description-input').click()
    cy.get('#Exercise-description-input').clear()
    cy.get('#Exercise-description-input').type(description)
    cy.get('#ContentConatiner span')
      .contains('Setup')
      .closest('.p-tabview-nav-link')
      .should('have.attr', 'aria-disabled', 'false')
      .click()

    //exercise test panel setup
    cy.get('#language-selection').click()
    cy.get('#language-selection_0').click()
    cy.get('#language-selection_1').click()
    cy.get('#ms-number-input').click()
    cy.get('#ms-number-input').type('222')
    cy.get('#hour-number-input').click()
    cy.get('#hour-number-input').type('1')
    cy.get('#minute-number-input').click()
    cy.get('#minute-number-input').type('22')
    cy.get('#ram-number-input').click()
    cy.get('#ram-number-input').type('512')
    cy.get('#radio-input-string').click()
    cy.get('#radio-output-string').click()
    for (let index = 0; index < inputsAndOutputs.length; index++) {
      cy.get('#add-exercise-button').click()
      cy.get('#test-input-' + index + '-input').click()
      cy.get('#test-input-' + index + '-input').clear()
      cy.get('#test-input-' + index + '-input').type(
        inputsAndOutputs[index].input
      )
      cy.get('#test-input-' + index + '-output').click()
      cy.get('#test-input-' + index + '-output').clear()
      cy.get('#test-input-' + index + '-output').type(
        inputsAndOutputs[index].output
      )
    }
    cy.get('#amount-of-auto-test-input > input').click()
    cy.get('#amount-of-auto-test-input > input').type('6')
    cy.get('#string-range-low-input > input').click()
    cy.get('#string-range-low-input > input').clear()
    cy.get('#string-range-low-input > input').type('1')
    cy.get('#string-range-up-input > input').click()
    cy.get('#string-range-up-input > input').clear()
    cy.get('#string-range-up-input > input').type('20')
    cy.get('#number-checkbox').click()
    cy.get('#special-char-checkbox').click()
    cy.get('#character-breaks-checkbox').click()
    cy.get('#ContentConatiner span')
      .contains('Solution')
      .closest('.p-tabview-nav-link')
      .should('have.attr', 'aria-disabled', 'false')
      .click()
    cy.get('#coderunner-dropdown').click()
    cy.get('.p-dropdown-item').contains('js').click()
    cy.get('#connect-button').click()

    // js first half
    cy.get('#coderunner-loading-dialog').should('not.exist')
    // eslint-disable-next-line cypress/no-unnecessary-waiting
    cy.wait(200)
    cy.get('#code-preview')
      .invoke('text')
      .should('contain', 'function solution(x){')
      CodeRunnerInput.clearCodeRunner();
      CodeRunnerInput.inputToCodeRunner(JSfirstHalf)

    
    cy.get('#coderunner-run-button').click()
    cy.get(
      '#TestResultCard' + '0' + ' > div.testValidationSection.wrong'
    ).should('have.text', 'Failed')
    for (let index = 1; index < inputsAndOutputs.length; index++) {
      cy.get('#TestResultCard' + index + ' > div.testValidationSection').should(
        'be.empty'
      )
    }

    cy.get('#coderunner-langage-dropdown').click()
    cy.get('.p-dropdown-item').contains('cpp').click()
    cy.get('.p-button').contains('span', 'Change').click()

    // cpp run
    cy.get('#coderunner-loading-dialog').should('not.exist')
    // eslint-disable-next-line cypress/no-unnecessary-waiting
    cy.wait(200)
    cy.get('#code-preview')
      .invoke('text')
      .should('contain', 'std::string solution(std::string x)')

      CodeRunnerInput.clearCodeRunner();
      CodeRunnerInput.inputToCodeRunner(cppSolution);
    
    // cy.get(codeEditorSequance).type(cppSolution, { force: true });
    cy.get('#coderunner-run-button').click()

    // cy.wait(5000);
    for (let index = 0; index < inputsAndOutputs.length; index++) {
      cy.get('#TestResultCard' + index)
        .contains('span', 'Result')
        .click()
      cy.get('#tab-result-expected-container-' + index).contains(
        'expeteced: "' + inputsAndOutputs[index].output + '"'
      )
      cy.get('#tab-result-achived-container-' + index).contains(
        'achived: "' + inputsAndOutputs[index].output + '"'
      )
    }

    cy.get('#coderunner-langage-dropdown').click()
    cy.get('.p-dropdown-item').contains('js').click()
    cy.get('.p-button').contains('span', 'Change').click()
    cy.get('#coderunner-loading-dialog').should('not.exist')
    // eslint-disable-next-line cypress/no-unnecessary-waiting
    CodeRunnerInput.moveToEndOfCodeRunner();
    CodeRunnerInput.inputToCodeRunner(JSsecondHalf);
    cy.get('#coderunner-run-button').click()

    for (let index = 0; index < inputsAndOutputs.length; index++) {
      cy.get('#TestResultCard' + index)
        .contains('span', 'Result')
        .click()
      cy.get('#tab-result-expected-container-' + index).contains(
        'expeteced: "' + inputsAndOutputs[index].output + '"'
      )
      cy.get('#tab-result-achived-container-' + index).contains(
        'achived: "' + inputsAndOutputs[index].output + '"'
      )
    }

    cy.get('#coderunner-submit-button').click()
    cy.get(
      'html > div.floatWindowContainer > div > div > div:nth-child(6) > div.p-timeline-event-content > h2'
    ).contains('saved to database')
    cy.get('button').contains('Close').click()
    cy.get('#switch-exercises').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/Exercises')
    cy.get('tbody').find('td').contains(title)
  })
})
