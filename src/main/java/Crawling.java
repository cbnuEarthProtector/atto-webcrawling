import database.Product;

import java.util.List;

public interface Crawling {
    List<Product> productWebCrawling(String category);

    String getBrandName();
}
