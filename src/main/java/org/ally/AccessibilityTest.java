package org.ally;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class AccessibilityTest {


    private WebDriver driver;

    private static final URL scriptUrl = AccessibilityTest.class.getResource("/axe.min.js");

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //Comment out website as may be required:
        //driver.get("https://www.ea.com/sports");
        //driver.get("https://www.amazon.com");
        driver.get("https://www.aliexpress.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAccessibility() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @Test
    public void testAccessibilityWithSkipFrames() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                .skipFrames()
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @Test
    public void testAccessibilityWithOptions() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                .options("{ rules: { 'accesskeys': { enabled: false } } }")
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @Test
    public void testAccessibilityWithSelector() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                .include("title")
                .include("p")
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @Test
    public void testAccessibilityWithIncludesAndExcludes() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                .include("body")
                .exclude("h1")
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @Test
    public void testAccessibilityWithWebElement() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                .analyze(driver.findElement(By.tagName("p")));

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @Test
    public void testAccessibilityWithShadowElement() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        JSONArray nodes = ((JSONObject)violations.get(0)).getJSONArray("nodes");
        JSONArray target = ((JSONObject)nodes.get(0)).getJSONArray("target");

        if (violations.length() == 1) {
            Assert.assertTrue(true, AXE.report(violations));
            //Assert.assertTrue (String.valueOf(target), "[[\"#upside-down\",\"ul\"]]");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));

        }
    }

    @Test
    public void testAccessibilityWithFewInclude() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                .include("div")
                .include("p")
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @Test
    public void testAccessibilityWithIncludesAndExcludesWithViolation() {
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                .include("body")
                .exclude("div")
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        JSONArray nodes = ((JSONObject)violations.get(0)).getJSONArray("nodes");
        JSONArray target = ((JSONObject)nodes.get(0)).getJSONArray("target");

        if (violations.length() == 0) {
            System.out.println("No errors");
        } else {
            AXE.writeResults("testAccessibility", responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }
}
