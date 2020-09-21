package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import world.lixiang.entity.Blog;
import world.lixiang.entity.BlogTag;
import world.lixiang.entity.BlogType;
import world.lixiang.entity.Tag;
import world.lixiang.service.BlogService;
import world.lixiang.service.FileService;
import world.lixiang.utils.JwtInfo;
import world.lixiang.utils.JwtUtils;
import world.lixiang.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private FileService fileService;

    @GetMapping("findPageBlog")
    public Map<String,Object> findPageBlog(Integer page , Integer rows , HttpServletRequest request){
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        String id =  jwtInfo.getId();
        page = page == null ? 1 :page;
        rows = rows == null ? 3 :rows;
        Map<String ,Object> map = new HashMap<>();
        List<Blog> blogs = blogService.findBlogs(page, rows, id);
        Long total =  blogService.findBlogsCount(id);
        map.put("blogs",blogs);
        map.put("total",total);
        return map;
    }

    @PostMapping("findWhereBlog")
    public Map<String,Object> findWhereBlog(@RequestBody BlogType blogType ){
        System.out.println(blogType);
        if(blogType.getId() == ""){
            blogType.setId(null);
        }
        if(blogType.getDescription() == ""){
            blogType.setDescription(null);
        }
        Map<String,Object> map = new HashMap<>();
        List<Blog> Blogs = blogService.findWhereBlogs(blogType);
        List<Blog> totals = blogService.findWhereBlogsCount(blogType);
        int total = totals.size();
        map.put("Blogs",Blogs);
        map.put("total" ,total);
        return map;
    }

    @GetMapping("deleteBlog")
    public Result deleteBlog(String id){
            Result result = new Result();
        Blog blog = blogService.selectOneBlog(id);
        String first_picture = blog.getFirst_picture();
        try{
                if(!StringUtils.isEmpty(first_picture)){
                    fileService.removeFile(first_picture);
                }
                blogService.deleteBlog(id);
                blogService.deleteBlogTag(id);
                result = result.success("删除成功");
            }catch (Exception e){
                e.printStackTrace();
                result = result.error("删除失败");
            }

            return  result;

    }

    @PostMapping("insertBlog")
    public Result insertBlog(@RequestBody Blog blog ) throws IOException {
        System.out.println(blog);
        Result result = new Result();
        try{
                if(StringUtils.isEmpty(blog.getDescription()) || StringUtils.isEmpty(blog.getContent()) ||
                    StringUtils.isEmpty(blog.getFirst_picture()) || StringUtils.isEmpty(blog.getType_id()) ||
                    StringUtils.isEmpty(blog.getTags())){
                    throw  new RuntimeException("信息没有填写完整");
                }

                if(blog.getId() == null){
                    blogService.insertBlog(blog);
                    Integer id = blog.getId();
                    List<Integer> tags = blog.getTags();
                    List<BlogTag> list =  new LinkedList<>();
                    for(int i = 0; i < tags.size(); i++){
                        BlogTag blogTag = new BlogTag();
                        blogTag.setBlog_id(id);
                        blogTag.setTag_id(tags.get(i));
                        list.add(blogTag);
                    }
                    blogService.insertBlogTag(list);
                    result = result.success("添加成功");
                }else{
                    blogService.updateBlog(blog);
                    blogService.deleteBlogTag(blog.getId().toString());
                    List<Integer> tags = blog.getTags();
                    List<BlogTag> list =  new LinkedList<>();
                    for(int i = 0; i < tags.size(); i++){
                        BlogTag blogTag = new BlogTag();
                        blogTag.setBlog_id(blog.getId());
                        blogTag.setTag_id(tags.get(i));
                        list.add(blogTag);
                    }

                    blogService.insertBlogTag(list);
                    result = result.success("修改成功");
                }

        }catch (RuntimeException e){
            e.printStackTrace();
            result = result.error("信息没有填写完整");
        }
        return result;
    }

    @GetMapping("selectOneBlog")
    public Map<String , Object> selectOneBlog(String id){
        Map<String,Object> map = new HashMap<>();
        Blog blog = blogService.selectOneBlog(id);
        Integer id1 = blog.getId();
        List<Tag> tags = blogService.selectTag(id1);
        List<Integer> list = new  LinkedList<>();
        for(int i= 0 ; i < tags.size();i++){
            Tag tag = tags.get(i);
            Integer id2 = tag.getId();
            list.add(id2);
        }
        map.put("blog",blog);
        map.put("list",list);
        return map;
    }

    @GetMapping("selectAllType")
    public Map<String , Object> selectAllType(Integer page , Integer rows , String id ){
            Map<String , Object> map = new HashMap<>();
            page = page == null ? 1 : page;
            rows = rows == null ? 3 : rows;
            List<Blog> blogs = blogService.selectAllType(page, rows, id);
            System.out.println(blogs);
            Long total = blogService.selectAllTotal(id);
            System.out.println(total);
            map.put("blogs" , blogs);
            map.put("total" , total);

            return  map;
    }

    @GetMapping("selectAllTag")
    public Map<String , Object> selectAllTag(Integer page , Integer rows , String id ){
        System.out.println("page = " + page + "rows = " + rows + "id=" + id);
        Map<String , Object> map = new HashMap<>();
        page = page == null ? 1 : page;
        rows = rows == null ? 3 : rows;
        List<Blog> blogs = blogService.selectAllTag(page, rows, id);
        System.out.println(blogs);
        Long total = blogService.selectAllTags(id);
        System.out.println(total);
        map.put("blogs" , blogs);
        map.put("total" , total);
        return  map;
    }


}
