package database;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    private Integer id;
    private String name;
    private String photoURL;
    private Boolean isBookmarked;
}
