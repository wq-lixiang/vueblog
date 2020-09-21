package world.lixiang.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import world.lixiang.service.FileService;
import world.lixiang.utils.OssProperties;
import world.lixiang.vo.Result;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {

        //读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        if(!ossClient.doesBucketExist(bucketname)){
            ossClient.createBucket(bucketname);
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        //构建objectName：文件路径 avatar/2020/04/15/default.jpg
        String folder = new DateTime().toString("yyyy/MM/dd");
        String fileName = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = module +"/" + folder + "/" + fileName + fileExtension;

        // 上传文件流。
        ossClient.putObject(bucketname, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url
        //https://guli-file-191125.oss-cn-beijing.aliyuncs.com/avatar/default.jpg
        return "https://" + bucketname + "." + endpoint + "/" + key;
    }

    @Override
    public void removeFile(String url) {
        //读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);

        // 删除文件。
        //https://guli-file-191125.oss-cn-beijing.aliyuncs.com/
        String host = "https://" + bucketname + "." + endpoint + "/";
        String objectName = url.substring(host.length());
        ossClient.deleteObject(bucketname, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


}
