package database;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Integer id;
    private String name;
    private String category;

//    private Integer brandId;

    private Integer price;
    private String siteURL;
    private String photoURL;
}
