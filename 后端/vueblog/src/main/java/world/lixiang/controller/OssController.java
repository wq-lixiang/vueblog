package world.lixiang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import world.lixiang.service.FileService;
import world.lixiang.vo.Result;

import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping("oss")
public class OssController {

    @Autowired
    private FileService fileService;
    @PostMapping("upload")
    public Result upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("module") String module) throws IOException {
        Result result = new Result();

        try {

            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFilename);

            result = result.success("上传成功" , uploadUrl);
        } catch (Exception e) {
            result = result.error("上传失败");
        }

        return result;
    }

    @DeleteMapping("remove")
    public Result removeFile(
            @RequestBody String url){
        fileService.removeFile(url);
        Result result = new Result();
        return result.success("文件删除成功");
    }
}
