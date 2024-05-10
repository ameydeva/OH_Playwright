package com.capita.one.housing.tests.playwright.POC;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.nio.file.Paths;
import java.util.Date;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

public class CRUD_PSH_MobileVisits_BVTTest {

	private static Playwright playwright;
	private static Browser browser;
	private static BrowserContext browserContext;
	private static Page page;

	@BeforeClass
	public  void setup() {
		boolean Headless = Boolean.parseBoolean("False");
		String browserName = "chromium";

		playwright = Playwright.create();

		switch (browserName.toLowerCase()) {
		case "chromium":
			browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Headless));
			break;

		case "chrome":
			browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(Headless));
			break;

		case "firefox":
			browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(Headless));
			break;

		case "safari":
			browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(Headless));
			break;

		default:
			System.out.println("Incorrect Browser Name");
			break;
		}

		Date date = new Date();
		String videoFile = date.toString().replace(":", "_").replace(" ", "_") + ".webm";
		String videoPath = System.getProperty("user.dir") + "/videos/" + System.currentTimeMillis() + videoFile;
		browserContext = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get(videoPath)).setRecordVideoSize(640, 480));
		// singleSignIn();
		/*
		 * browser.newContext(new Browser.NewContextOptions().setIsMobile(true));
		 * browser.newContext(new
		 * Browser.NewContextOptions().setColorScheme(ColorScheme.DARK));
		 */
		browserContext.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
		page = browserContext.newPage();
		page.setDefaultTimeout(90000);
		page.waitForLoadState(LoadState.DOMCONTENTLOADED);
		page.navigate("https://onehousingqa.capita-software.co.uk/#/login");

	}

	@AfterClass
	public  void tearDown() {
		playwright.close();
	}

	@Test(priority = 1)
	public  void housingLogin()

	{
		String classname = Thread.currentThread().getStackTrace()[2].getClassName().substring(13);
		int dotCount = classname.lastIndexOf(".");
		System.out.println("Execution of test '" + classname.substring(dotCount + 1) + "' is going on.");
		page.getByLabel("user name").click();
		page.getByLabel("user name").fill("asadar");
		page.getByLabel("password").click();
		page.getByLabel("password").fill("asadar@1991");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
		assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Search your information"))).isVisible();
	}

	@Test(priority = 2)
	public void housingNavigation() {
		page.getByTitle("Main menu").click();
		page.getByText("Housing", new Page.GetByTextOptions().setExact(true)).click();
		page.getByText("Private sector housing").click();
		page.getByText("Accounts").click();
		assertThat(page.getByText("Account management")).isVisible();
		page.getByText("Account management").click();
		assertThat(page.getByText("Advanced search")).isVisible();
	}

	@Test(priority = 3)
	public void pshSearch() {
		assertThat(page.getByText("Advanced search")).isVisible();
		page.getByText("Advanced search").click();
		assertThat(page.getByText("Status", new Page.GetByTextOptions().setExact(true))).isVisible();
		page.getByLabel("Status", new Page.GetByLabelOptions().setExact(true)).locator("span").first().click();
		page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("​Current").setExact(true)).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).nth(2).click();
		assertThat(page.locator(".k-master-row > td:nth-child(5)").first()).isVisible();
		page.locator(".k-master-row > td:nth-child(5)").first().click();
		page.locator("housing-generic-grid").getByText("Select").click();
	}

	@Test(priority = 4)
	public void crudeStopCode() {
		assertThat(page.locator("span").filter(new Locator.FilterOptions().setHasText("Options")).first()).isVisible();
	    page.getByText("Options").click();
	    page.getByText("Stop codes").click();
	    assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Stop code"))).isVisible(); 
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create")).click();
		page.locator("#k-grid3-r0c0 > .k-cell-inner > .k-link").click();
		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(""))).isVisible();
		page.getByLabel("Stop code", new Page.GetByLabelOptions().setExact(true)).getByLabel("", new Locator.GetByLabelOptions().setExact(true)).getByLabel("Select").click();
		page.getByText("1SC - Serv Charge Dispute").click();
		page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName("null Date calendar")).getByLabel("Date calendar").click();
		page.getByTitle("Monday, May 27,").locator("span").click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).click();
		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).first()).isVisible();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).first().click();
		page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName("null Date calendar")).getByLabel("Date calendar").click();
		page.getByTitle("Sunday, April 28,").locator("span").click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).click();
		page.getByText("× Updated successfully").click();
		assertThat(page.locator(".k-overlay")).isVisible();
		page.getByLabel("Created date Filter").getByLabel("Date calendar").click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Today")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).nth(1).click();
		assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes"))).isVisible();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes")).click();
		page.getByText("Close").click();
	}

}