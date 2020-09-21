package world.lixiang.service;

import org.apache.ibatis.annotations.Param;
import world.lixiang.entity.Blog;
import world.lixiang.entity.BlogTag;
import world.lixiang.entity.BlogType;
import world.lixiang.entity.Tag;

import java.util.List;

public interface BlogService {
    List<Blog> findBlogs(Integer page ,Integer rows , String id);

    Long total();

    List<Blog> findWhereBlogs(BlogType blogType);

    List<Blog> findWhereBlogsCount(BlogType blogType);

    void  deleteBlog(String id);

    void deleteBlogTag(String id);

    void insertBlog(Blog blog);

    void insertBlogTag( List<BlogTag> list);

    Blog selectOneBlog(String id);

    void updateBlog(Blog blog);

    List<Tag> selectTag(Integer id);

    List<Blog> selectAllType( Integer page ,Integer rows, String id);

    Long  selectAllTotal(String id);


    List<Blog> selectAllTag(@Param("page") Integer page , @Param("rows") Integer rows, @Param("id") String id);

    Long selectAllTags(String id );

    Blog findOneBlog(String id);

    List<Blog> findOneRecommend();

    void updateViews( Integer views, Integer id);

    List<Blog> findAllPage(Integer page , Integer rows);

    Long countBlogs();

    Long findBlogsCount(String id);
}
