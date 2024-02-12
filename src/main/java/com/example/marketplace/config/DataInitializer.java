package com.example.marketplace.config;

import com.example.marketplace.enums.PaymentMethod;
import com.example.marketplace.models.*;
import com.example.marketplace.services.cart.CartService;
import com.example.marketplace.services.category.CategoryService;
import com.example.marketplace.services.order.OrderService;
import com.example.marketplace.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.*;

@Configuration
public class DataInitializer {

    private final Random random = new Random();
    private final Map<String, List<String>> categoryNames = new HashMap<>();
    private final Map<String, String> productPhotos = new HashMap<>();

    {
        categoryNames.put("Games", generateGames());
        categoryNames.put("Books", List.of("Harry Potter", "Lord of the Rings", "Game of Thrones"));
        categoryNames.put("Electronics", List.of("Smartphone", "Laptop", "Headphones", "IPhone", "MacBook", "TV", "Apple Vision Pro", "AirPods"));
        categoryNames.put("Movies", List.of("GoodFellas", "Pulp Fiction", "The Shawshank Redemption", "The Godfather", "The Godfather II", "The Godfather III", "Fight Club", "Django", "12 Angry Man", "Seven", "Inception"));

        productPhotos.put("Fifa 10", "https://wikiwandv2-19431.kxcdn.com/_next/image?url=https://upload.wikimedia.org/wikipedia/en/2/24/FIFA_10_Cover.jpg&w=640&q=50");
        productPhotos.put("Fifa 11", "https://upload.wikimedia.org/wikipedia/en/f/f5/Fifa11_Game_Cover.jpg");
        productPhotos.put("Fifa 16", "https://upload.wikimedia.org/wikipedia/en/2/27/FIFA_16_cover.jpg");
        productPhotos.put("Fifa 20", "https://img.redbull.com/images/c_crop,w_1600,h_800,x_0,y_0,f_auto,q_auto/c_scale,w_1200/redbullcom/2019/08/09/285dc5ad-b2b8-4add-a8f6-31e2dbe6fea7/fifa-20");

        productPhotos.put("Harry Potter", "https://kids.scholastic.com/content/kids64/en/books/harry-potter/_jcr_content/root/responsivegrid/responsivegrid_1333535796/responsivegrid_19882/image_copy_959293594.coreimg.100.1285.png/1693924396564/4-hp-grandpre-refresh-gobletfire-sm.png");
        productPhotos.put("Lord of the Rings", "https://www.shutterstock.com/image-photo/bucharest-romania-september-20-2016-260nw-496301440.jpg");
        productPhotos.put("Game of Thrones", "https://media.gq-magazine.co.uk/photos/5d13a572eef9219e43a000c1/16:9/w_2560%2Cc_limit/books-hp-gq-26mar18_b.jpg");

        productPhotos.put("Smartphone", "https://i.insider.com/649d917dca78800019708f28?width=1136&format=jpeg");
        productPhotos.put("Laptop", "https://p3-ofp.static.pub/ShareResource/thinkpad/Imgs/lenovo-thinkpad-brand-z-series.90d77bcbf3f88502.jpg");
        productPhotos.put("IPhone", "https://www.apple.com/newsroom/images/2023/09/apple-debuts-iphone-15-and-iphone-15-plus/article/Apple-iPhone-15-lineup-color-lineup-230912_big.jpg.large.jpg");
        productPhotos.put("MacBook", "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/refurb-mbp14-m2-silver-202303?wid=2000&hei=1536&fmt=jpeg&qlt=95&.v=1680103614090");
        productPhotos.put("Apple Vision Pro", "https://www.apple.com/newsroom/images/media/Apple-WWCD23-Vision-Pro-glass-230605_big.jpg.large.jpg");
        productPhotos.put("AirPods", "https://www.apple.com/v/airpods-pro/d/images/meta/og__ch3csr9zmviq_overview.png");

        productPhotos.put("GoodFellas", "https://image.tmdb.org/t/p/original/wGagGF57oSC44WsSPSG1TVEwSRn.jpg");
        productPhotos.put("Pulp Fiction", "https://image.tmdb.org/t/p/original/6XF9cO0yS5SdjPmUEggqbOoLFTT.jpg");
        productPhotos.put("The Shawshank Redemption", "https://media.vanityfair.com/photos/541c84101019a3955fea0c49/master/pass/s-vfh-shawshank-redemption-20th-anniversary.jpg");
        productPhotos.put("The Godfather", "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
        productPhotos.put("The Godfather II", "https://m.media-amazon.com/images/M/MV5BMWMwMGQzZTItY2JlNC00OWZiLWIyMDctNDk2ZDQ2YjRjMWQ0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UY1200_CR106,0,630,1200_AL_.jpg");
        productPhotos.put("The Godfather III", "https://m.media-amazon.com/images/M/MV5BNWFlYWY2YjYtNjdhNi00MzVlLTg2MTMtMWExNzg4NmM5NmEzXkEyXkFqcGdeQXVyMDk5Mzc5MQ@@._V1_FMjpg_UX1000_.jpg");
        productPhotos.put("Fight Club", "https://media.newyorker.com/photos/5dbafcc91b4a6700085a7a9b/master/w_2560%2Cc_limit/Baker-FightClub.jpg");
        productPhotos.put("Django", "https://fwcdn.pl/fpo/05/41/620541/7504936.3.jpg");
        productPhotos.put("12 Angry Man", "https://m.media-amazon.com/images/M/MV5BNGRlZjVhNWMtOTUxYi00MTYxLWEzOWUtMTM1NDc3ZWRjMDZjXkEyXkFqcGdeQWRpZWdtb25n._V1_.jpg");
        productPhotos.put("Seven", "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p17198_v_h8_aj.jpg");
        productPhotos.put("Inception", "https://c4.wallpaperflare.com/wallpaper/574/531/642/2010-inception-movie-inception-poster-wallpaper-preview.jpg");
    }

    @Bean
    public CommandLineRunner dataInitializerRunner(
            @Autowired CategoryService categoryService,
            @Autowired ProductService productService,
            @Autowired CartService cartService,
            @Autowired OrderService orderService
            ) {
        return args -> {
            for (String categoryName : categoryNames.keySet()) {
                Category category = new Category();
                category.setName(categoryName);
                category.setDescription("Desc");
                category = categoryService.addCategory(category);

                for (int i = 0; i < categoryNames.get(categoryName).size(); i++) {
                    String productName = categoryNames.get(categoryName).get(i);
                    Product product = new Product();

                    if (productPhotos.containsKey(productName)) {
                        product.setPhotoUrl(new URL(productPhotos.get(productName)));
                    }

                    product.setName(productName);
                    product.setDescription(generateProductDescription(categoryName, i));
                    product.setPrice(generateRandomPrice());
                    product.setQuantity(generateRandomQuantity());
                    product.setCategories(Set.of(category));
                    productService.addProduct(product);
                }
            }

            for (int i = 0; i < 20; i++) {
                for (int j = 1; j < 4; j++) {
                    CartProduct cartProduct = new CartProduct();
                    Product product = new Product();
                    product.setId((long) i + j);
                    cartProduct.setQuantity(1);
                    cartProduct.setProduct(product);

                    try {
                        cartService.addProductToCart(cartProduct, 1L);
                    } catch (RuntimeException e) {
                        System.out.println("DataInitializer Error " + e.getMessage());
                    }
                }

                try {
                    Address address = new Address();
                    address.setUserId(1L);
                    address.setAddress("Jana Bazynskiego 8");
                    address.setCity("Gdansk");
                    address.setCountry("Poland");
                    address.setState("Pomorze");
                    address.setZipCode("80-414");
                    cartService.saveAddressToCart(1L, address);

                    Payment payment = new Payment();
                    payment.setPaymentMethod(PaymentMethod.CARD);
                    cartService.savePaymentToCart(1L, payment);

                    orderService.placeOrder(1L);
                } catch (RuntimeException e) {
                    System.out.println("DataInitializer Error " + e.getMessage());
                }
            }
        };
    }

    private String generateProductDescription(String categoryName, int index) {
        return "%s %s Description".formatted(categoryName, index);
    }

    private int generateRandomQuantity() {
        return random.nextInt(100) + 1;
    }

    private double generateRandomPrice() {
        double price = 20.0 + (random.nextDouble() * (200.0 - 20.0));
        return Math.round(price * 100.0) / 100.0;
    }

    private List<String> generateGames() {
        List<String> games = new ArrayList<>();
        for (int i = 10; i < 24; i++) {
           games.add("Fifa %s".formatted(i));
        }
        return games;
    }
}
