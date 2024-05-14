import { passwordLoginSelector, submitLoginSelector, usernameLoginSelector } from '../../support/commands';

import {
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Limpiar SessionStorage', () => {
  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
  });

  describe('Login Incorrecto', () => {
    before(() => {
      cy.visit('');
    });

    it('Failed signing in message', () => {
      cy.clickOnLoginItem();
      cy.get(usernameLoginSelector).click().type('admi');
      cy.get(passwordLoginSelector).type('admin');
      cy.get(submitLoginSelector).click();
      cy.contains('Failed to sign in! Please check your credentials and try again.').should('be.visible');
    });

    describe('Login Correcto', () => {
      before(() => {
        cy.visit('');
      });

      it('Logging in message', () => {
        cy.clickOnLoginItem();
        cy.get(usernameLoginSelector).click().type('admin');
        cy.get(passwordLoginSelector).type('admin');
        cy.get(submitLoginSelector).click();
        cy.contains('You are logged in as user "admin".').should('be.visible');
      });
    });
  });
});
