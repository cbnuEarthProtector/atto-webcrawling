import database.Brand;
import database.BrandDao;
import database.Product;
import database.ProductDao;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        productDao.drop();
        productDao.createTable();
        BrandDao brandDao = new BrandDao();
        brandDao.drop();
        brandDao.createTable();
        int brandId = 0;
        ArrayList<Product> products;

        String[] categories = {"kitchen", "cosmetic", "bath"};

        Toun28Crawling toun28Crawling = new Toun28Crawling();
        Brand toun28 = Brand.builder()
                .id(brandId++)
                .name(toun28Crawling.getBrandName())
                .photoURL(null)
                .build();
        brandDao.insert(toun28);
        for (String category : categories) {
            toun28Crawling.productWebCrawling(category);
            products = toun28Crawling.getProducts();
            System.out.println(products.size());
            for (Product product : products) {
                productDao.insert(toun28.getId(), product);
            }
        }

        DrnoaCrawling drnoaCrawling = new DrnoaCrawling();
        Brand drnoa = Brand.builder()
                .id(brandId++)
                .name(drnoaCrawling.getBrandName())
                .photoURL(null)
                .build();
        brandDao.insert(drnoa);
        drnoaCrawling.productWebCrawling_OnlyBath();
        products = drnoaCrawling.getProducts();
        System.out.println(products.size());
        for (Product product : products) {
            productDao.insert(drnoa.getId(), product);
        }

        categories = new String[]{"kitchen", "laundry", "bath"};

        JayeonsangjumCrawling jayeonsangjumCrawling = new JayeonsangjumCrawling();
        Brand jayeonsangjum = Brand.builder()
                .id(brandId++)
                .name(jayeonsangjumCrawling.getBrandName())
                .photoURL(null)
                .build();
        brandDao.insert(jayeonsangjum);
        for (String category : categories) {
            jayeonsangjumCrawling.productWebCrawling(category);
            products = jayeonsangjumCrawling.getProducts();
            System.out.println(products.size());
            for (Product product : products) {
                productDao.insert(jayeonsangjum.getId(), product);
            }
        }
    }
}
