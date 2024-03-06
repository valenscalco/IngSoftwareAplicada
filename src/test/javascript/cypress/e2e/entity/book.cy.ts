import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Book e2e test', () => {
  const bookPageUrl = '/book';
  const bookPageUrlPattern = new RegExp('/book(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bookSample = { isbn: 'magenta', name: 'Implementation ivory infrastructure', publishYear: 'alarm', copies: 12093 };

  let book;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/books+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/books').as('postEntityRequest');
    cy.intercept('DELETE', '/api/books/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (book) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/books/${book.id}`,
      }).then(() => {
        book = undefined;
      });
    }
  });

  it('Books menu should load Books page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('book');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Book').should('exist');
    cy.url().should('match', bookPageUrlPattern);
  });

  describe('Book page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bookPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Book page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/book/new$'));
        cy.getEntityCreateUpdateHeading('Book');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/books',
          body: bookSample,
        }).then(({ body }) => {
          book = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/books+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/books?page=0&size=20>; rel="last",<http://localhost/api/books?page=0&size=20>; rel="first"',
              },
              body: [book],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bookPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      // test mio --final
      it('id click should load details Book page', () => {
        cy.get(`a[href*="/book/${book.id}/view"]`).first().click();
        cy.getEntityDetailsHeading('book');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });

      it('detail button click should load details Book page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('book');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });

      it('edit button click should load edit Book page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Book');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });

      it.skip('edit button click should load edit Book page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Book');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });

      it('last delete button click should delete instance of Book', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('book').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);

        book = undefined;
      });
    });
  });

  describe('new Book page', () => {
    beforeEach(() => {
      cy.visit(`${bookPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Book');
    });

    it('should create an instance of Book', () => {
      cy.get(`[data-cy="isbn"]`).type('Union gold Fr').should('have.value', 'Union gold Fr');

      cy.get(`[data-cy="name"]`).type('Electronics extend').should('have.value', 'Electronics extend');

      cy.get(`[data-cy="publishYear"]`).type('Sausages Credit firewall').should('have.value', 'Sausages Credit firewall');

      cy.get(`[data-cy="copies"]`).type('26686').should('have.value', '26686');

      cy.setFieldImageAsBytesOfEntity('cover', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        book = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bookPageUrlPattern);
    });
  });
});
