describe("BStack Demo Assessment", () => {

    it("Entering Username and password", () => {
        cy.visit("https://bstackdemo.com");
        cy.get("#signin").click();
        cy.get("#username").type("d").get("#react-select-2-option-0-0").click();
        cy.get("#password").type("t").get("#react-select-3-option-0-0").click();
        cy.get("Button").click();
        cy.get(".shelf-item__title").contains("iPhone 12").siblings().contains("Add to cart").click();
        cy.get(".buy-btn").click();
        cy.get("#firstNameInput").type("Abrar");
        cy.get("#lastNameInput").type("Mehdi");
        cy.get("#addressLine1Input").type("Hyderabad");
        cy.get("#provinceInput").type("Telangana");
        cy.get("#postCodeInput").type("500032");
        cy.get("#checkout-shipping-continue").click();
        cy.get("#confirmation-message").should("have.text", "Your Order has been successfully placed.");
    });

})