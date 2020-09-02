package world.lixiang.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OssUtils {

    public static String testUpload(String oldFileName, String newFileName) throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4GAUGrN9B9uLhkS3BnU1";
        String accessKeySecret = "XZ7R515pbYHfPutSRsbqxTK2zi5Yjt";
        String bucketName = "wuqing-zero";
// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。


// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        InputStream inputStream = new FileInputStream("D:\\image\\" + oldFileName);
        ossClient.putObject(bucketName, newFileName, inputStream);
// 关闭OSSClient。
        ossClient.shutdown();
        System.out.println("成功");

        return "https://wuqing-zero.oss-cn-beijing.aliyuncs.com/" + newFileName;
    }
}
