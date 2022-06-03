import database.Brand;
import database.BrandDao;
import database.Product;
import database.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        productDao.drop();
        productDao.createTable();
        BrandDao brandDao = new BrandDao();
        brandDao.drop();
        brandDao.createTable();

        String[] categories = {"kitchen", "cosmetic", "laundry", "bath"};

        List<Crawling> crawlingList = new ArrayList<>();
        crawlingList.add(new Toun28Crawling());
        crawlingList.add(new DrnoaCrawling());
        crawlingList.add(new JayeonsangjumCrawling());

        for (Crawling crawling : crawlingList) {
            Brand crawlingBrand = Brand.builder()
                    .name(crawling.getBrandName())
                    .photoURL(null)
                    .build();
            brandDao.insert(crawlingBrand);
            crawlingBrand.setId(brandDao.findByName(crawlingBrand.getName()));

            for (String category : categories) {
                List<Product> products = crawling.productWebCrawling(category);
                System.out.println(crawlingBrand.getName() + " " + category + " " + products.size() + "개");

                for (Product product : products) {
                    productDao.insert(crawlingBrand.getId(), product);
                }
            }
        }
    }
}
