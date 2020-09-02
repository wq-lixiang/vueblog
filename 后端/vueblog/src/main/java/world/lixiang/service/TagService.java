package world.lixiang.service;

import org.apache.ibatis.annotations.Param;
import world.lixiang.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> findPageTag( Integer page ,  Integer rows);

    Long total();

    void addTag(Tag tag);

    void deleteTag(Integer id);

    void updateTag(Tag tag);

    Tag findOneTag(Integer id);


    List<Tag> findAllTag();
}
