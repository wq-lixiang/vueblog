package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import world.lixiang.entity.Blog;
import world.lixiang.entity.Tag;
import world.lixiang.service.BlogService;

import java.util.List;

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
}
