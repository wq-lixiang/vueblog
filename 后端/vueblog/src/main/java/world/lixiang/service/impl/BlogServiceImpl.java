package world.lixiang.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import world.lixiang.dao.BlogDao;
import world.lixiang.entity.Blog;
import world.lixiang.entity.BlogTag;
import world.lixiang.entity.BlogType;
import world.lixiang.entity.Tag;
import world.lixiang.service.BlogService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogDao blogDao;

    @Override
    public List<Blog> findBlogs(Integer page, Integer rows ,String id) {
        Integer start  = (page - 1) * rows;
        return blogDao.findBlogs(start,rows,id);
    }

    @Override
    public Long total() {
        return blogDao.total();
    }

    @Override
    public List<Blog> findWhereBlogs(BlogType blogType) {
        blogType.setPage((blogType.getPage()-1) * blogType.getRows());
        return blogDao.findWhereBlogs(blogType);
    }

    @Override
    public List<Blog> findWhereBlogsCount(BlogType blogType) {
        return blogDao.findWhereBlogsCount(blogType);
    }

    @Override
    public void deleteBlog(String id) {
        blogDao.deleteBlog(id);
    }

    @Override
    public void deleteBlogTag(String id) {
        blogDao.deleteBlogTag(id);
    }

    @Override
    public void insertBlog(Blog blog) {
        blog.setCreate_time(new Date());
        blog.setUpdate_time(new Date());
        blog.setUser_id(Integer.parseInt(blog.getJwtId()));
        blog.setViews(0);
        blogDao.insertBlog(blog);
    }

    @Override
    public void insertBlogTag(List<BlogTag> list) {
        blogDao.insertBlogTag(list);
    }

    @Override
    public Blog selectOneBlog(String id) {
        return blogDao.selectOneBlog(id);
    }

    @Override
    public void updateBlog(Blog blog) {
        blog.setUpdate_time(new Date());
        blogDao.updateBlog(blog);
    }

    @Override
    public List<Tag> selectTag(Integer id) {
        return blogDao.selectTag(id);
    }

    @Override
    public List<Blog> selectAllType(Integer page, Integer rows, String id) {
        Integer start = (page - 1 ) * rows;

        return blogDao.selectAllType(start , rows , id);
    }

    @Override
    public Long selectAllTotal(String id) {
        return blogDao.selectAllTotal(id);
    }

    @Override
    public List<Blog> selectAllTag(Integer page, Integer rows, String id) {
        Integer start = (page -1) * rows;
        return blogDao.selectAllTag(start, rows, id);
    }

    @Override
    public Long selectAllTags(String id) {
        return blogDao.selectAllTags(id);
    }

    @Override
    public Blog findOneBlog(String id) {
        return blogDao.findOneBlog(id);
    }

    @Override
    public List<Blog> findOneRecommend() {
        return blogDao.findOneRecommend();
    }

    @Override
    public void updateViews(Integer views, Integer id) {
        blogDao.updateViews(views ,id);
    }

    @Override
    public List<Blog> findAllPage(Integer page , Integer rows) {
        Integer start = (page - 1) * rows;
        return blogDao.findAllPage(start , rows);
    }

    @Override
    public Long countBlogs() {
        return blogDao.countBlogs();
    }

    @Override
    public Long findBlogsCount(String id) {
        return blogDao.findBlogsCount(id);
    }


}
