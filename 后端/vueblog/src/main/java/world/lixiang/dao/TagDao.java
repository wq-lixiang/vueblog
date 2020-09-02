package world.lixiang.dao;

import org.apache.ibatis.annotations.Param;
import world.lixiang.entity.Tag;
import world.lixiang.entity.Type;

import java.util.List;

public interface TagDao {

    List<Tag> findPageTag(@Param("page") Integer page , @Param("rows") Integer rows);

    Long total();

    void addTag(Tag tag);

    void deleteTag(Integer id);

    void updateTag(Tag tag);

    Tag findOneTag(Integer id);

    List<Tag> findAllTag();
}
