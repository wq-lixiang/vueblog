package world.lixiang.dao;

import org.apache.ibatis.annotations.Param;
import world.lixiang.entity.Blog;
import world.lixiang.entity.BlogTag;
import world.lixiang.entity.BlogType;
import world.lixiang.entity.Tag;

import java.util.List;

public interface BlogDao {
        List<Blog> findBlogs(@Param("page") Integer page ,@Param("rows") Integer rows, @Param("id") String id);

        Long total();

        List<Blog> findWhereBlogs(BlogType blogType);

        List<Blog> findWhereBlogsCount(BlogType blogType);

        void  deleteBlog(String id);

        void deleteBlogTag(String id);

        void insertBlog(Blog blog);

        void insertBlogTag(@Param("list") List<BlogTag> list);

        Blog selectOneBlog(String id);

        void updateBlog(Blog blog);

        List<Tag> selectTag(Integer id);

        List<Blog> selectAllType(@Param("page") Integer page ,@Param("rows") Integer rows, @Param("id") String id);

        Long  selectAllTotal(String id);

        List<Blog> selectAllTag(@Param("page") Integer page , @Param("rows") Integer rows, @Param("id") String id);

        Long selectAllTags(String id );

        Blog findOneBlog(String id);

        List<Blog> findOneRecommend();

        void updateViews(@Param("views") Integer views, @Param("id")Integer id);

        List<Blog> findAllPage(@Param("page") Integer page ,@Param("rows") Integer rows);

        Long countBlogs();

        Long findBlogsCount(String id);
}
