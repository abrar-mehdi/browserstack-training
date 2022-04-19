import '@percy/cypress';

describe("Percy Assessment", () => {

    it('Compare production and stage websites', function() {
        
        cy.visit("https://www.browserstack.com/");
        cy.percySnapshot('Homepage', { widths: [375, 480, 720, 1280, 1440, 1920] });
        cy.visit("https://www.browserstack.com/pricing");
        cy.percySnapshot('Pricing', { widths: [375, 480, 720, 1280, 1440, 1920] });
        cy.visit("https://www.browserstack.com/integrations/automate");
        cy.percySnapshot('Automate', { widths: [375, 480, 720, 1280, 1440, 1920] });
        cy.visit("https://www.browserstack.com/docs");
        cy.percySnapshot('Docs', { widths: [375, 480, 720, 1280, 1440, 1920] });
        
        cy.visit("https://k8s.bsstag.com/")
        cy.percySnapshot('Homepage', { widths: [375, 480, 720, 1280, 1440, 1920] });
        cy.visit("https://k8s.bsstag.com/pricing")
        cy.percySnapshot('Pricing', { widths: [375, 480, 720, 1280, 1440, 1920] });
        cy.visit("https://k8s.bsstag.com/integrations/automate")
        cy.percySnapshot('Automate', { widths: [375, 480, 720, 1280, 1440, 1920] });
        cy.visit("https://k8s.bsstag.com/docs")
        cy.percySnapshot('Docs', { widths: [375, 480, 720, 1280, 1440, 1920] });
        
      });
})