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

  describe('Try Functions', () => {
    //Antes de cada prueba dentro del conjunto (describe), intercepta cualquier solicitud POST a '/api/account' y le asigna
    // un alias llamado 'createRequest'. Esto se hace para poder esperar a esta solicitud más adelante y verificar su respuesta.
    beforeEach(() => {
      cy.intercept('POST', '/api/publishers').as('postEntityRequest');
      cy.intercept('DELETE', '/api/publishers/*').as('deleteEntityRequest');
    });

    beforeEach(() => {
      cy.login('admin', 'admin');
      cy.visit('');
    });

    it('Create new Publisher', () => {
      cy.clickOnEntityMenuItem('publisher');
      cy.get(entityCreateButtonSelector).click();
      cy.get(`[data-cy="name"]`).type('Fangs').should('have.value', 'Fangs');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => expect(response.statusCode).to.equal(201));
    });

    it('Delete Last Publisher', () => {
      cy.clickOnEntityMenuItem('publisher');
      cy.get(entityDeleteButtonSelector).last().click();
      cy.get(entityConfirmDeleteButtonSelector).click();

      cy.wait('@deleteEntityRequest').then(({ response }) => expect(response.statusCode).to.equal(204));
    });
  });
});
