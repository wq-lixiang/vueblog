package world.lixiang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private Integer id;
    private String description;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date update_time;

    private String flag;
    private String first_picture;
    private Integer views;
    private Integer type_id;
    private Integer user_id;
    private Boolean recommend;


    private Type type;
    private List<Integer> tags;

    private List<Tag> tag;


}
