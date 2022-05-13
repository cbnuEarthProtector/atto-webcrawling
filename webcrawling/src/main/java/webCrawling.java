//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebCrawling {
    private WebDriver driver;
    private JavascriptExecutor javascriptExecutor;
    private Actions actions;
    private WebElement element;
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "chromedriver.exe";

    public String url = "http://onlyeco.co.kr/category/%EB%A6%AC%EB%B9%99/50/"; // 자연상점-리빙 카테고리 페이지
    public ArrayList<String> productLines = new ArrayList<>();

    public WebCrawling() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 창 최대
//        options.addArguments("headless"); // 창 숨김
        options.addArguments("--disable-popup-blocking"); // 팝업창 막기
        this.driver = new ChromeDriver(options);
        this.javascriptExecutor = (JavascriptExecutor)this.driver;
        this.actions = new Actions(this.driver);
        this.driver.get(url);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs
//        this.driver.findElement(By.xpath("//*[@id=\"slideCateList\"]/ul/li[4]/a")).click(); // 리빙 카테고리 페이지로 이동
//        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

        List<WebElement> descriptionElements = this.driver.findElements(By.className("description")); // 판매 품목을 저장하는 list
        boolean productsLessThan18 = false; // 현재 페이지의 상품 개수가 18개 이하인지 체크
        int pageCnt = 2; // 현재 상품 판매 페이지 카운트
        
        while(true) {
            for(int i = 0; i < 6; i++) {
                driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
                webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

                try {
                    for(int j = 0; j < 18; j++) { // 한 페이지에 최대 18개의 제품 표시
                        descriptionElements.add(this.driver.findElements(By.className("description")).get(j));
                    }
                } catch (Exception e) {
                    productsLessThan18 = true;
                }
            }
            if(productsLessThan18) break; // 상품 개수가 18개 미만이면 해당 페이지가 마지막 페이지임

            for (WebElement element : descriptionElements) {
                productLines.add(element.findElement((By.className("name"))).getText());
                //System.out.println(element.findElement((By.className("name"))).getText());
            }
            descriptionElements.clear();

            try {
                //System.out.println(url + "?page=" + pageCnt);
                this.driver.get(url + "?page=" + pageCnt); // 다음 페이지로 이동
                pageCnt++;
            } catch (Exception e)
            {
                break;
            }

        }
    }
}
