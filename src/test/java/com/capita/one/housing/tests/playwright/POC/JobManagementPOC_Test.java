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

public class JobManagementPOC_Test {

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
		browserContext.setDefaultTimeout(120000);
		page = browserContext.newPage();
		page.waitForLoadState(LoadState.DOMCONTENTLOADED);
		page.navigate("https://onehousingtest.capita-software.co.uk/#/login");

	}

	@AfterClass
	public  void tearDown() {
		traceViewer();
		playwright.close();
	}

	
	
	
	
	// Method For generating Random References
	private static final String ALPHA ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static String getRandomString(int length) {
		StringBuffer sb = new StringBuffer(length);
		for (int i=0; i<length; i++) {
			int ndx = (int)(Math.random()*ALPHA.length());
			sb.append(ALPHA.charAt(ndx));
		}
		return sb.toString();
	}
	
	// Playwright feature Traceviewer recording
	public void traceViewer() {
		Date date = new Date();
		String traceViewerFile = date.toString().replace(":", "_").replace(" ", "_") + ".zip";
		String tracePath = System.getProperty("user.dir") + "/traceViewer/" + System.currentTimeMillis()+ traceViewerFile;
		page.context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));
	}
	
	public String getJobNUmber() {
	     return page.locator("//td[@id='k-grid9-r2c1']").textContent();
	}
	
	
	
	
	
	
	// TestNG Test Annotations priority wise test execution 
	
	
	
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
	}

	@Test(priority = 2)
	public void housingNavigation() {
		  page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Search your information")).waitFor();
	      page.getByTitle("Main menu").click();
	      page.getByText("Assets", new Page.GetByTextOptions().setExact(true)).click();
	      page.getByText("Job Processing").click();
	      page.getByText("Job management").click();
	}

	@Test(priority = 3)
	public void createLocation() {
	
		  page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Location search")).waitFor();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create")).click();
	      assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Create address"))).isVisible();
	      String randomRef = getRandomString(6);
	      page.getByLabel("Reference").click();
	      page.getByLabel("Reference").fill(randomRef);
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Street Search")).click();
	      page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName("Chesters Park Drive").setExact(true)).click();
	      page.getByLabel("Place type").locator("span").first().click();
	      page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("BLOCK - Block")).click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
	      page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("View address details")).waitFor();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create").setExact(true)).click();
	      page.locator("#MGTAREA span").first().click();
	      page.getByText("CHES - Cheshire").click();
	      page.locator("#ACCESS_GROUP span").first().click();
	      page.getByText("HSG - Housing General").click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Date calendar")).click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Today")).click();
	      
	      page.getByText("Property details").click();
	      page.locator("#LOCATION_TYPE span").first().click();
	      page.getByText("BUNG - Bungalow").click();
	      page.locator("#AREA span").first().click();
	      page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("ALEX - Alexandria")).click();
	      page.locator("span").filter(new Locator.FilterOptions().setHasText("Rent management information")).first().click();
	      page.locator("span").filter(new Locator.FilterOptions().setHasText("Repairs and maintenance")).first().click();
	      page.locator("span").filter(new Locator.FilterOptions().setHasText("Financial details")).first().click();
	      page.locator("span").filter(new Locator.FilterOptions().setHasText("Attributes")).first().click();
	      page.getByLabel("select attribute").click();
	      page.getByLabel("ok").click();
	      page.getByRole(AriaRole.SPINBUTTON).click();
	      page.getByRole(AriaRole.SPINBUTTON).fill("1");
	      page.getByLabel("ok").click();
	      page.getByLabel("ok").click();
	      page.getByLabel("save").click();
	      page.getByRole(AriaRole.TREEITEM, new Page.GetByRoleOptions().setName("Allocate officers to location")).locator("kendo-svgicon").click();
	      page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName("Former arrears officer ")).getByRole(AriaRole.BUTTON).click();
	      page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(" ")).getByRole(AriaRole.COMBOBOX).locator("span").first().click();
	      page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("- Miss M White")).click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
	    
	}

	@Test(priority = 4)
	public void createJob() {
		  page.locator("span").filter(new Locator.FilterOptions().setHasText("Options")).first().waitFor();
		  page.locator("span").filter(new Locator.FilterOptions().setHasText("Options")).first().click();
		  page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Jobs")).click();
		  page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log job")).waitFor();
		  page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log job")).click();
		  page.getByText("Assess").waitFor();
		  page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Continue")).click();
		  assertThat(page.getByText("Contract", new Page.GetByTextOptions().setExact(true))).isVisible();
	      page.getByLabel("Data table").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Search")).click();
	      page.getByPlaceholder("Enter element code or").click();
	      page.getByPlaceholder("Enter element code or").fill("1");
	     
	      /** page.getByLabel("Select SOR element").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("search")).click(); **/
	      page.locator("//button[@title='Search']/span[contains(text(),'Search')]").click(); 
	      
	      assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("SOR element search results"))).isVisible();
	      page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName("101103")).click();
	      
	      /** page.getByLabel("select", new Page.GetByLabelOptions()).click(); **/
	      page.locator("//button[contains(@class,'float-right primary-btn')]/span").click(); 
	      
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Continue")).click();
	      assertThat(page.locator("#k-panelbar-2-item-default-13").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Search"))).isVisible();
	      page.locator("#k-panelbar-2-item-default-13").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Search")).click();
	    
	      /**page.locator("#k-29560946-2cd8-484f-aa4a-4d51ae4a5a7e > .k-grid-container > .k-grid-content > .k-grid-table-wrap > .k-grid-table > .k-table-tbody > tr > td").first().click();**/
	      page.locator("(//tr[@data-kendo-grid-item-index='0'])[3]").click(); 
	      
	      page.getByText("Select").click();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Continue")).click();
	      
	      assertThat(page.locator("#k-panelbar-2-item-default-14").getByText("Contractor")).isVisible();
	      assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Continue"))).isVisible();
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Continue")).click();
	 
	      /**   assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"))).isVisible();
	     
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();**/
	      page.locator("//button[contains(@class,'float-right mt-3') and (@title='Save')]").click(); 

	     page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log job")).waitFor();
	    String jobNumber =  getJobNUmber();
	     System.out.println(jobNumber);
	     page.getByRole(AriaRole.GRIDCELL, new Page.GetByRoleOptions().setName(jobNumber)).click();
	}  
	 
	
	     
	@Test(priority = 5)
	public void workTicketManagement() {
		page.getByRole(AriaRole.FORM).locator("div").filter(new Locator.FilterOptions().setHasText("Job actions")).nth(1).isVisible();
		page.locator("span").filter(new Locator.FilterOptions().setHasText("Job actions")).first().click();
		Page page1 = page.waitForPopup(() -> {
			page.getByText("Works ticket management").click();
		});
		page1.getByLabel("Select Row").check();
		page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Operatives allocation")).click();
		page1.getByLabel("Select Row").check();
		page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Allocate operative")).click();
		page1.getByLabel("Select All Rows").check();
	}

}