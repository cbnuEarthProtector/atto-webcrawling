import database.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DrnoaCrawling implements Crawling  {
    private final WebDriver driver;
    private final JavascriptExecutor javascriptExecutor;
    private final Actions actions;
    private WebElement element;
    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "chromedriver.exe";

    private final String mainPageUrl;
    private final ArrayList<String> kitchenPageUrl = new ArrayList<>(); // 주방 관련 카테고리 페이지 저장
    private final ArrayList<String> bathPageUrl = new ArrayList<>(); // 욕실 용품 관련 카테고리 페이지 저장하는 배열
    private final ArrayList<String> cosmeticPageUrl = new ArrayList<>(); // 화장품 카테고리 페이지 저장
    private final ArrayList<String> fashionPageUrl = new ArrayList<>(); // 패션 관련 카테고리 페이지 저장

    private final String brandName = "닥터노아";

    public DrnoaCrawling() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 창 최대
        options.addArguments("headless"); // 창 숨김
        options.addArguments("--disable-popup-blocking"); // 팝업창 막기
        this.driver = new ChromeDriver(options);
        this.javascriptExecutor = (JavascriptExecutor) this.driver;
        this.actions = new Actions(this.driver);

        mainPageUrl = "https://www.doctornoah.net/SHOP"; // 닥터노아 페이지
        bathPageUrl.add("https://www.doctornoah.net/toothbrush"); // 칫솔 카테고리 페이지
        bathPageUrl.add("https://www.doctornoah.net/toothpaste"); // 치약 카테고리 페이지
        bathPageUrl.add("https://www.doctornoah.net/oralcareproduct"); // 구강 관리 카테고리 페이지
    }

    @Override
    public List<Product> productWebCrawling(String category) {
        List<Product> products = new ArrayList<>();

        ArrayList<String> crawlingPageUrls = new ArrayList<>();
        switch (category) {
            case "bath" -> {
                crawlingPageUrls.add(bathPageUrl.get(0));
                crawlingPageUrls.add(bathPageUrl.get(1));
            }
            default -> crawlingPageUrls = null;
        }
        if(crawlingPageUrls == null) return products;

        for (String crawlingPageUrl : crawlingPageUrls) {
            this.driver.get(crawlingPageUrl);
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

            List<WebElement> shopItemElements = null; // 상품 정보를 저장하는 list

            for (int i = 0; i < 6; i++) {
                driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
                webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // wait for 2 secs

                shopItemElements = this.driver.findElements(By.className("shop-item"));
            }

            for (WebElement element : shopItemElements) {
                Product product = new Product();
                product.setCategory(category);

                String price = element.findElement(By.className("item-pay-detail"))
                        .findElement(By.className("pay")).getText();
                price = price.replaceAll(",", "");
                price = price.replaceAll("₩", "");
                price = price.replaceAll("원", "");
                product.setPrice(Integer.parseInt(price)); // 상품 가격 저장
                product.setName(element.findElement((By.className("item-detail")))
                        .findElement(By.tagName("a")).getText());
                product.setPhotoURL(element.findElement(By.tagName("img")).getAttribute("src"));
                product.setSiteURL(element.findElement(By.className("item-detail"))
                        .findElement(By.tagName("a")).getAttribute("href"));

                products.add(product);
            }
        }
        return products;
    }

    public String getBrandName() {
        return brandName;
    }
}
