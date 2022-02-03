package appiumAndroidBitrise;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestAndroid {
	AppiumDriver driver;
	private static By addPlant = By.id("add_plant");
	private static By add = By.id("fab");

	@BeforeMethod
	@BeforeTest
	public void setUp() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "Android");
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("platformVersion", "9");
		caps.setCapability("deviceName", "Android Emulator");
		caps.setCapability("app", System.getenv("BITRISE_APK_PATH"));
		driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
	}

	@Test
	public void add_plant_test() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		MobileElement plantList = (MobileElement) driver.findElementByAccessibilityId("Plant list");
		MobileElement myGarden = (MobileElement) driver.findElementByAccessibilityId("My garden");

		System.out.println("Ejecutando TEST");
		wait.until(ExpectedConditions.elementToBeClickable(plantList)).click();
		System.out.println("Click plant List");
		wait.until(ExpectedConditions.elementToBeClickable(myGarden)).click();
		System.out.println("Click mygarden");

		wait.until(ExpectedConditions.presenceOfElementLocated(addPlant)).click();
		List<MobileElement> listElements = driver.findElements(By.id("plant_item_title"));
		for (MobileElement el : listElements) {
			if (el.getText().equalsIgnoreCase("Avocado")) {
				el.click();
				break;
			}
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(add)).click();
		driver.navigate().back();
		driver.findElementByAccessibilityId("My garden").click();
		By plant_name = By.id("plant_name");
		System.out.println("EQUALLLS");
		wait.until(ExpectedConditions.presenceOfElementLocated(plant_name));
		AssertJUnit.assertTrue(driver.findElement(plant_name).getAttribute("text").equalsIgnoreCase("Avocado"));
		System.out.println("TEST EJECUTADO CORRECTAMENTE");
	}

	@AfterMethod
	@AfterTest
	public void tearDown() {
		if (null != driver) {
			driver.quit();
		}
	}
}
