import database.Product;
import database.ProductDao;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
//        JayeonsangjumCrawling webCrawling = new JayeonsangjumCrawling();
//        webCrawling.productWebCrawling("kitchen");

//        DrnoaCrawling drnoaCrawling = new DrnoaCrawling();
//        drnoaCrawling.productWebCrawling_OnlyBath();
        Toun28Crawling toun28Crawling = new Toun28Crawling();

        String[] categories = {"kitchen", "cosmetic", "bath"};

        ProductDao productDao = new ProductDao();
        productDao.createTable();
        productDao.reset();

        for (String category : categories) {
            toun28Crawling.productWebCrawling(category);
            ArrayList<Product> products = toun28Crawling.getProducts();
            System.out.println(products.size());
            for (Product product : products) {
                productDao.insert(product);
            }
        }
    }
}
