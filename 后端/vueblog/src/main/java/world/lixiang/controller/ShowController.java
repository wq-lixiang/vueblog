package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import world.lixiang.entity.Blog;
import world.lixiang.entity.Tag;
import world.lixiang.service.BlogService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("show")
public class ShowController {
    @Autowired
    BlogService blogService;

    @GetMapping("selectOneBlog")
    public Blog selectOneBlog(String id){
        Blog blog = blogService.findOneBlog(id);
        System.out.println(blog);
        Integer views = blog.getViews() + 1 ;
        blogService.updateViews(views , Integer.parseInt(id));
        List<Tag> tags = blogService.selectTag(Integer.parseInt(id));
        blog.setTag(tags);
        return blog;
    }

    @GetMapping("selectOne")
    public List<Blog> selectOne(){
        List<Blog> oneRecommend = blogService.findOneRecommend();
        return oneRecommend;
    }

    @GetMapping("findAllBlog")
    public Map<String , Object> findAllBlog(Integer page , Integer rows){
        page = page == null ? 1 : page;
        rows = rows == null ? 3 : rows;
        Map<String , Object> map = new HashMap<>();
        List<Blog> blogs =  blogService.findAllPage(page , rows);
        Long total =  blogService.countBlogs();
        map.put("blogs" , blogs);
        map.put("total", total);

        return map;
    }
}
