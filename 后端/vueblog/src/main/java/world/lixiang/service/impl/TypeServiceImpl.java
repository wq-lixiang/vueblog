package world.lixiang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import world.lixiang.dao.TypeDao;
import world.lixiang.entity.Type;
import world.lixiang.service.TypeService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeDao typeDao;

    @Override
    public List<Type> findPageType(Integer page, Integer rows) {
        Integer start = (page - 1 ) * rows;
        List<Type> types =  typeDao.findPageType(start, rows);
        return types;
    }

    @Override
    public Long total() {
        return typeDao.total();
    }

    @Override
    public void addType(Type type) {
        typeDao.addType(type);
    }

    @Override
    public void deleteType(Integer id) {
        typeDao.deleteType(id);
    }

    @Override
    public void updateType(Type type) {
        typeDao.updateType(type);
    }

    @Override
    public Type findOneType(Integer id) {
        return typeDao.findOneType(id);
    }

    @Override
    @Cacheable(value = "index", key = "'findAllType'")
    public List<Type> findAllType() {
        return typeDao.findAllType();
    }
}
