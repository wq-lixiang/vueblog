package world.lixiang.vo;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object object;

    public  Result success(String msg , Object object){
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        result.setObject(object);
        return result;
    }

    public  Result success(String msg ){
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public  Result error(String msg ){
        Result result = new Result();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }

}
