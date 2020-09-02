package world.lixiang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.lixiang.dao.TagDao;
import world.lixiang.entity.Tag;
import world.lixiang.service.TagService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    TagDao tagDao;

    @Override
    public List<Tag> findPageTag(Integer page, Integer rows) {
        Integer start = (page - 1 ) * rows;
        List<Tag> pageTag = tagDao.findPageTag(start, rows);
        return pageTag;
    }

    @Override
    public Long total() {
        return tagDao.total();
    }

    @Override
    public void addTag(Tag tag) {
        tagDao.addTag(tag);
    }

    @Override
    public void deleteTag(Integer id) {
        tagDao.deleteTag(id);
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.updateTag(tag);
    }

    @Override
    public Tag findOneTag(Integer id) {
        return tagDao.findOneTag(id);
    }

    @Override
    public List<Tag> findAllTag() {
        return tagDao.findAllTag();
    }
}
