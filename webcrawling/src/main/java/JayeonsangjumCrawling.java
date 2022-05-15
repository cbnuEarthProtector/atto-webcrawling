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

public class JayeonsangjumCrawling {
    private WebDriver driver;
    private JavascriptExecutor javascriptExecutor;
    private Actions actions;
    private WebElement element;
    private static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static String WEB_DRIVER_PATH = "chromedriver.exe";

    private String kitchenPageUrl = "http://onlyeco.co.kr/category/%EB%A6%AC%EB%B9%99/51/"; // 리빙-주방용품 카테고리 페이지
    private String laundryPageUrl = "http://onlyeco.co.kr/category/%EC%84%B8%ED%83%81%EC%9A%A9%ED%92%88/75/"; // 리빙-세탁용품 카테고리 페이지
    private String bathPageUrl = "http://onlyeco.co.kr/category/%EC%9A%95%EC%8B%A4%EC%9A%A9%ED%92%88/95/"; // 리빙-욕실용품 카테고리 페이지
    private ArrayList<String> productNames = new ArrayList<>();
    private ArrayList<Integer> productPrices = new ArrayList<>();
    private String brandName = "자연상점";

    public JayeonsangjumCrawling() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 창 최대
        options.addArguments("headless"); // 창 숨김
        options.addArguments("--disable-popup-blocking"); // 팝업창 막기
        this.driver = new ChromeDriver(options);
        this.javascriptExecutor = (JavascriptExecutor)this.driver;
        this.actions = new Actions(this.driver);
    }

    public void productWebCrawling(String category) {
        String url = "";
        switch (category) {
            case "kitchen" -> url = kitchenPageUrl;
            case "laundry" -> url = laundryPageUrl;
            case "bath" -> url = bathPageUrl;
        }

        this.driver.get(url);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

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
                productNames.add(element.findElement((By.className("name"))).getText());
                String price = element.findElement((By.className("xans-record-"))).getText();
                price = price.replaceAll(",", "");
                price = price.replaceAll("원", "");
                productPrices.add(Integer.parseInt(price));
            }
            descriptionElements.clear();

            try {
                this.driver.get(url + "?page=" + pageCnt); // 다음 페이지로 이동
                pageCnt++;
            } catch (Exception e)
            {
                break;
            }
        }
    }

    public String getBrandName() { return brandName; }

    public ArrayList<String> getProductNames() { return productNames; }
}
