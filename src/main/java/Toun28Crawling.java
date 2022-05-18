import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Toun28Crawling {
    private WebDriver driver;
    private JavascriptExecutor javascriptExecutor;
    private Actions actions;
    private WebElement element;
    private static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static String WEB_DRIVER_PATH = "chromedriver.exe";

    private String mainPageUrl;
    private ArrayList<String> kitchenPageUrl = new ArrayList<>(); // 주방 관련 카테고리 페이지 저장
    private ArrayList<String> bathPageUrl = new ArrayList<>(); // 욕실 용품 관련 카테고리 페이지 저장하는 배열
    private ArrayList<String> cosmeticPageUrl = new ArrayList<>(); // 화장품 카테고리 페이지 저장

    private String brandName = "톤28";
    private ArrayList<String> productNames = new ArrayList<>();
    private ArrayList<Integer> productPrices = new ArrayList<>();
    private ArrayList<String> productUrls = new ArrayList<>();
    private ArrayList<String> productImages = new ArrayList<>();

    public Toun28Crawling() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 창 최대
        options.addArguments("headless"); // 창 숨김
        options.addArguments("--disable-popup-blocking"); // 팝업창 막기
        this.driver = new ChromeDriver(options);
        this.javascriptExecutor = (JavascriptExecutor)this.driver;
        this.actions = new Actions(this.driver);

        mainPageUrl = "https://www.toun28.com/"; // 톤28 페이지
        bathPageUrl.add("https://www.toun28.com/renew/product?category=21&sort="); // 머리감을거리
        bathPageUrl.add("https://www.toun28.com/renew/product?category=13&sort="); // 얼굴&몸감을거리
        bathPageUrl.add("https://www.toun28.com/renew/product?category=19&sort="); // 고체치약
        cosmeticPageUrl.add("https://www.toun28.com/renew/product?category=27&sort="); // 몸바를거리
        cosmeticPageUrl.add("https://www.toun28.com/renew/product?category=26&sort="); // 얼굴바를거리
        kitchenPageUrl.add("https://www.toun28.com/renew/product?category=14&sort="); // 주방씻을거리
    }

    public void productWebCrawling(String category) {
        ArrayList<String> crawlingPageUrls = new ArrayList<>();

        switch (category) {
            case "bath" -> {
                crawlingPageUrls.add(bathPageUrl.get(0));
                crawlingPageUrls.add(bathPageUrl.get(1));
                crawlingPageUrls.add(bathPageUrl.get(2));
            }
            case "cosmetic" -> {
                crawlingPageUrls.add(cosmeticPageUrl.get(0));
                crawlingPageUrls.add(cosmeticPageUrl.get(1));
            }
            case "kitchen" -> crawlingPageUrls.add(kitchenPageUrl.get(0));
        }

        for(String crawlingPageUrl : crawlingPageUrls)
        {
            this.driver.get(crawlingPageUrl);
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

            List<WebElement> productsItemElements = null; // 상품 정보를 저장하는 list

            for(int i = 0; i < 6; i++) {
                driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
                webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

                productsItemElements = this.driver.findElements(By.className("products-item"));
            }

            for (WebElement element : productsItemElements) {
                String price = element.findElement(By.className("p-price"))
                        .findElement(By.tagName("span")).getText();
                try {
                    price = price.replaceAll(",", "");
                    price = price.replaceAll("₩", "");
                    price = price.replaceAll("원", "");
                    productPrices.add(Integer.parseInt(price)); // 상품 가격 저장
                } catch (NumberFormatException e) { // 품절일 경우
                    productPrices.add(-1);
                }


                productNames.add(element.findElement((By.className("p-name"))).getText());

                productImages.add(element.findElement(By.tagName("img")).getAttribute("src"));

                productUrls.add(element.findElement(By.tagName("a")).getAttribute("href"));
            }
            productsItemElements.clear();
        }
    }

    public String getBrandName() { return brandName; }

    public ArrayList<String> getProductNames() { return productNames; }

    public ArrayList<Integer> getProductPrices() { return productPrices; }

    public ArrayList<String> getProductUrls() { return productUrls; }

    public ArrayList<String> getProductImages() { return productImages; }
}
