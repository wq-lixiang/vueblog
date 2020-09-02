package world.lixiang.service;

import org.apache.ibatis.annotations.Param;
import world.lixiang.entity.Type;

import java.util.List;

public interface TypeService {
    List<Type> findPageType( Integer page , Integer rows);

    Long total();

    void addType(Type type);

    void deleteType(Integer id);

    void updateType(Type type);

    Type findOneType(Integer id);

    List<Type> findAllType();
}
