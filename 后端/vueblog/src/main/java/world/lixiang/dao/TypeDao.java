package world.lixiang.dao;

import org.apache.ibatis.annotations.Param;
import world.lixiang.entity.Type;

import java.util.List;

public interface TypeDao {
    List<Type> findPageType(@Param("page") Integer page , @Param("rows") Integer rows);

    Long total();

    void addType(Type type);

    void deleteType(Integer id);

    void updateType(Type type);

    Type findOneType(Integer id);

    List<Type> findAllType();
}
