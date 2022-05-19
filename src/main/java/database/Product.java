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
    private Integer brandId;
    private Integer price;
    private String siteURL;
    private String photoURL;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brandId=" + brandId +
                ", price=" + price +
                ", siteURL='" + siteURL + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}
