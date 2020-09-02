package world.lixiang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    private static final String TOKEN  = "sadnaidhwaoldjhopdjwopda";

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String getToken(Map<String , Object> map){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,7);  //默认设置过期时间是7天

        //创建JWT builder
        JWTCreator.Builder builder =  JWT.create();
        //payload
        map.forEach((k,v)->{
            builder.withClaim(k, (Boolean) v);
        });
        String sing =  builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(TOKEN));
        return sing;
    }

    /**
     * 验证token 合法性
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取token信息的方法
     */

    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
        return verify;
    }
}
