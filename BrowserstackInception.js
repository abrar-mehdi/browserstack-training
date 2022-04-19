import { Selector } from 'testcafe';

fixture`Browserstack Inception Assignment`.page`https://www.browserstack.com`;

test('Visiting Browserstack Website', async t => {
    await t.click(".sign-in-link");
    await t.typeText("#user_email_login", "*********");
    await t.typeText("#user_password", "**********");
    await t.click("#user_submit");
    await t.click(Selector(".browser-version-list__element").withAttribute("data-browser-type","firefox")
    .withAttribute("data-row-index", "0"));
    await t.wait(20000);
});