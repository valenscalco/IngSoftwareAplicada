describe('logout modal', () => {
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  // Caching session when logging in via API

  // test mio --final
  it('successfully logs out', () => {
    cy.session(username, () => {
      cy.request({
        method: 'POST',
        url: '/api/authenticate',
        body: { username, password, rememberMe: true },
      }).then(({ body }) => {
        window.localStorage.setItem('jhi-authenticationToken', '"' + body.id_token + '"');
      });
    });

    cy.visit('/');

    cy.get('[data-cy="logout"]').first().click({ force: true });

    cy.request({ url: '/api/account', failOnStatusCode: false }).its('status').should('eq', 401);

    //cy.get('.alert-link').click();

    /*cy.get(passwordLoginSelector).type(password);
    cy.get(submitLoginSelector).click();
    cy.wait('@authenticate').then(({ response }) => expect(response.statusCode).to.equal(200));
    cy.hash().should('eq', '');*/
  });
});
