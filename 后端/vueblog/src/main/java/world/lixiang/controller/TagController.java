package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import world.lixiang.entity.Tag;
import world.lixiang.entity.Type;
import world.lixiang.service.TagService;
import world.lixiang.vo.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("findPageTag")
    public Map<String, Object> findPageTag(Integer page , Integer rows){
        page = page == null ? 1 : page;
        rows = rows == null? 3 : rows;

        Map<String , Object> map = new HashMap<>();
        List<Tag> tags =  tagService.findPageTag(page,rows);
        Long total = tagService.total();

        map.put("tags",tags);
        map.put("total",total);
        return map;
    }

    @PostMapping("addTag")
    public Result addTag(@RequestBody Tag tag){
        Result result = new Result();
        try{
            if(tag.getId() ==null){
                tagService.addTag(tag);
                result = result.success("添加成功");
            }else{
                tagService.updateTag(tag);
                result = result.success("修改成功");
            }

        }catch (Exception e){
            result = result.error("添加失败");
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("deleteTag")
    public Result deleteTag(Integer id){
        Result result = new Result();
        try{
            tagService.deleteTag(id);
            result = result.success("删除成功");
        }catch (Exception e){
            result = result.error("删除失败");
            e.printStackTrace();
        }
        return  result;
    }


    @GetMapping("findOneTag")
    public Result findOneTag(Integer id){
        Result result = new Result();
        Tag oneTag = tagService.findOneTag(id);
        result =  result.success("查询成功",oneTag);
        return result;
    }

    @GetMapping("findAllTag")
    public List<Tag> findAllTag(){
        List<Tag> allTag = tagService.findAllTag();
        return allTag;
    }
}
