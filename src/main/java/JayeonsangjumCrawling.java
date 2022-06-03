//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import database.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class JayeonsangjumCrawling implements Crawling {
    private final WebDriver driver;
    private final JavascriptExecutor javascriptExecutor;
    private final Actions actions;
    private WebElement element;
    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "chromedriver.exe";

    private final String mainPageUrl;
    private final String kitchenPageUrl;
    private final String laundryPageUrl;
    private final String bathPageUrl;

    private final String brandName = "자연상점";

    public JayeonsangjumCrawling() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 창 최대
        options.addArguments("headless"); // 창 숨김
        options.addArguments("--disable-popup-blocking"); // 팝업창 막기
        this.driver = new ChromeDriver(options);
        this.javascriptExecutor = (JavascriptExecutor) this.driver;
        this.actions = new Actions(this.driver);

        mainPageUrl = "http://onlyeco.co.kr/"; // 자연상점 페이지
        kitchenPageUrl = "http://onlyeco.co.kr/category/%EB%A6%AC%EB%B9%99/51/"; // 리빙-주방용품 카테고리 페이지
        laundryPageUrl = "http://onlyeco.co.kr/category/%EC%84%B8%ED%83%81%EC%9A%A9%ED%92%88/75/"; // 리빙-세탁용품 카테고리 페이지
        bathPageUrl = "http://onlyeco.co.kr/category/%EC%9A%95%EC%8B%A4%EC%9A%A9%ED%92%88/95/"; // 리빙-욕실용품 카테고리 페이지
    }

    public List<Product> productWebCrawling(String category) {
        List<Product> products = new ArrayList<>();

        String crawlingPageUrl = "";
        switch (category) {
            case "kitchen" -> crawlingPageUrl = kitchenPageUrl;
            case "laundry" -> crawlingPageUrl = laundryPageUrl;
            case "bath" -> crawlingPageUrl = bathPageUrl;
            default -> crawlingPageUrl = null;
        }
        if (crawlingPageUrl == null) return products;

        this.driver.get(crawlingPageUrl);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

        List<WebElement> descriptionElements = null; // 판매 품목을 저장하는 list
        List<WebElement> prdImgElements = null; // 상품 이미지를 저장하는 list
        int pageCnt = 2; // 다음 상품 판매 페이지 카운트

        while (true) {
            for (int i = 0; i < 6; i++) {
                driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
                webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

                try {
                    // 한 페이지에 최대 18개의 제품 표시
                    descriptionElements = this.driver.findElements(By.className("description"));
                    prdImgElements = this.driver.findElements(By.className("prdImg"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (descriptionElements.isEmpty()) break; // 마지막 페이지까지 상품 검토 완료

            for (int i = 0; i < descriptionElements.size(); i++) {
                Product product = new Product();
                product.setCategory(category);

                WebElement descriptionElement = descriptionElements.get(i);
                WebElement prdImgElement = prdImgElements.get(i);

                String price = descriptionElement.findElement((By.className("xans-record-"))).getText();
                price = price.replaceAll(",", "");
                price = price.replaceAll("₩", "");
                price = price.replaceAll("원", "");
                product.setPrice(Integer.parseInt(price)); // 상품 가격 저장
                product.setName(descriptionElement.findElement(By.className("name")).getText());
                product.setSiteURL(descriptionElement.findElement((By.className("name"))).
                        findElement(By.tagName("a")).getAttribute("href")); // 상품 판매 crawlingPageUrl 저장
                product.setPhotoURL(prdImgElement.findElement(By.tagName("img")).getAttribute("src"));

                products.add(product);
            }

            try {
                this.driver.get(crawlingPageUrl + "?page=" + pageCnt); // 다음 페이지로 이동
                pageCnt++;
            } catch (Exception e) {
                break;
            }
        }
        return products;
    }

    public String getBrandName() {
        return brandName;
    }
}
