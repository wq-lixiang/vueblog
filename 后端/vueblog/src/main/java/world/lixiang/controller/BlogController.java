package world.lixiang.controller;

import org.apache.commons.io.FilenameUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import world.lixiang.dao.BlogDao;
import world.lixiang.entity.Blog;
import world.lixiang.entity.BlogTag;
import world.lixiang.entity.BlogType;
import world.lixiang.entity.Tag;
import world.lixiang.service.BlogService;
import world.lixiang.utils.OssUtils;
import world.lixiang.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;


    @GetMapping("findPageBlog")
    public Map<String,Object> findPageBlog(Integer page , Integer rows){
        page = page == null ? 1 :page;
        rows = rows == null ? 3 :rows;
        Map<String ,Object> map = new HashMap<>();
        List<Blog> blogs = blogService.findBlogs(page, rows);
        Long total = blogService.total();
        map.put("blogs",blogs);
        map.put("total",total);
        return map;
    }

    @PostMapping("findWhereBlog")
    public Map<String,Object> findWhereBlog(@RequestBody BlogType blogType){
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
            try{
                blogService.deleteBlog(id);
                blogService.deleteBlogTag(id);
                result = result.success("删除成功");
            }catch (Exception e){
                e.printStackTrace();
                result = result.error("删除失败");
            }

            return  result;

    }

    @RequestMapping("insertBlog")
    public Result insertBlog(MultipartFile pic  , Blog blog) throws IOException {
        Result result = new Result();
        try{
                if(blog.getId() == null){
                    if(pic !=null){
                        String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
                        String oldFileName =  pic.getOriginalFilename();
                        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"."+ extension;
                        String s = OssUtils.testUpload(oldFileName, newFileName);
                        blog.setFirst_picture(s);
                    }
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
                    if(pic !=null){
                        String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
                        String oldFileName =  pic.getOriginalFilename();
                        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +"."+ extension;
                        String s = OssUtils.testUpload(oldFileName, newFileName);
                        blog.setFirst_picture(s);
                    }
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

        }catch (Exception e){
            e.printStackTrace();
              result = result.error("添加失败");
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
