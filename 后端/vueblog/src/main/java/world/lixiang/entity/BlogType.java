package world.lixiang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogType {
    private Integer page;
    private Integer rows;
    private String description;
    private String jwtId;
    private String id;
}
