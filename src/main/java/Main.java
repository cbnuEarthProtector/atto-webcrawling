public class Main {

    public static void main(String[] args) {
//        JayeonsangjumCrawling webCrawling = new JayeonsangjumCrawling();
//        webCrawling.productWebCrawling("kitchen");

//        DrnoaCrawling drnoaCrawling = new DrnoaCrawling();
//        drnoaCrawling.productWebCrawling_OnlyBath();
        Toun28Crawling toun28Crawling = new Toun28Crawling();
        toun28Crawling.productWebCrawling("kitchen");
        toun28Crawling.productWebCrawling("cosmetic");
        toun28Crawling.productWebCrawling("bath");
    }
}
