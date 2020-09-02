package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import world.lixiang.entity.Type;
import world.lixiang.service.TypeService;
import world.lixiang.vo.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("findPageType")
    public Map<String, Object> findPageType(Integer page , Integer rows){
        page = page == null ? 1 : page;
        rows = rows == null? 3 : rows;

        Map<String , Object> map = new HashMap<>();
        List<Type> types =  typeService.findPageType(page,rows);
        Long total = typeService.total();

        map.put("types",types);
        map.put("total",total);
        return map;
    }
    @PostMapping("addType")
    public Result addType(@RequestBody Type type){
        Result result = new Result();
        try{
            if(type.getId() ==null){
                typeService.addType(type);
                result = result.success("添加成功");
            }else{
                typeService.updateType(type);
                result = result.success("修改成功");
            }

        }catch (Exception e){
            result = result.error("添加失败");
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("deleteType")
    public Result deleteType(Integer id){
        Result result = new Result();
        try{
            typeService.deleteType(id);
            result = result.success("删除成功");
        }catch (Exception e){
            result = result.error("删除失败");
            e.printStackTrace();
        }
        return  result;
    }

    @GetMapping("findOneType")
    public Result findOneType(Integer id){
        Result result = new Result();
        Type oneType = typeService.findOneType(id);
        result =  result.success("查询成功",oneType);
        return result;
    }

    @GetMapping("findAllType")
    public List<Type> findAllType(){
        return typeService.findAllType();
    }
}
