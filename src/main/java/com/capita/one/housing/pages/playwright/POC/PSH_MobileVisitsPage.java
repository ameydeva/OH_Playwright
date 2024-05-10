package com.capita.one.housing.pages.playwright.POC;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class PSH_MobileVisitsPage {
	Playwright playwright = Playwright.create();
	Browser browser = playwright.chromium().launch();
	Page page = browser.newPage();

	public PSH_MobileVisitsPage() {

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean housingLogin(String userName, String password) {
		try {
			String classname = Thread.currentThread().getStackTrace()[2].getClassName().substring(13);
			int dotCount = classname.lastIndexOf(".");
			System.out.println("Execution of test '" + classname.substring(dotCount + 1) + "' is going on.");
		      page.getByLabel("user name").click();
		      page.getByLabel("user name").fill("asadar");
		      page.getByLabel("password").click();
		      page.getByLabel("password").fill("asadar@1991");
		      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
		      page.navigate("https://onehousingqa.capita-software.co.uk/#/dashboard");
		      assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Search your information"))).isVisible();
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean housingNavigation() {
		try {
			  page.getByTitle("Main menu").click();
		      page.getByText("Housing", new Page.GetByTextOptions().setExact(true)).click();
		      page.getByText("Private sector housing").click();
		      page.locator("li").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Accounts$"))).locator("span").click();
		      page.locator("li").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Account management$"))).click();
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean pshSearch() {
		try {
			  page.locator("https://onehousingqa.capita-software.co.uk/#/housing/psh/accountSearch").waitFor();
		      page.getByText("Advanced search").click();
		      page.getByText("Status", new Page.GetByTextOptions().setExact(true)).click();
		      page.getByLabel("Status", new Page.GetByLabelOptions().setExact(true)).getByLabel("Select").click();
		      page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("​Current").setExact(true)).click();
		      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).nth(2).click();
		      page.locator(".k-master-row > td:nth-child(5)").first().click();
		      page.getByText("Select").nth(2).click();
		      assertThat(page.locator("span").filter(new Locator.FilterOptions().setHasText("Account actions")).first()).isVisible();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean crudStopCode() {
		try {
			  page.locator("#tenancyOptions").getByLabel("Select").click();
		      page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("​Stop codes")).click();
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
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
