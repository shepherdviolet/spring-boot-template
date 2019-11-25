package template.demo.fileupload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传示例
 *
 * @author S.Violet
 */
@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${demo.fileupload.uploadpath}")
    private String fileUploadPath;

    /**
     * 获取文件上传页面
     * http://localhost:8080/fileupload/html
     */
    @RequestMapping("/html")
    public String html() {
        return "demo/fileupload/fileUpload";
    }

    /**
     * 文件上传逻辑
     * http://localhost:8080/fileupload/upload
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

//        //转为Multipart请求, 之前需要类型判断
//        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//        //所有文件
//        Map<String, MultipartFile> multipartFileMap = multiRequest.getFileMap();
//        //某个文件的头信息
//        HttpHeaders fileHeaders = multiRequest.getMultipartHeaders("file");
//        //所有参数
//        Map<String, String[]> multipartParamMap = multiRequest.getParameterMap();

//        String filePath = request.getSession().getServletContext().getRealPath("upload/");

        File targetDir = new File(fileUploadPath);
        if (!targetDir.exists()) {
            if (!targetDir.mkdirs()) {
                throw new RuntimeException("Upload dir mkdirs failed, path:" + fileUploadPath);
            }
        }

        if (targetDir.isFile()) {
            throw new RuntimeException("Upload dir is a file, path:" + fileUploadPath);
        }

        File targetFile = new File(fileUploadPath + File.separator + file.getOriginalFilename());

        if (targetFile.exists()) {
            throw new RuntimeException("Upload path exists, path:" + targetFile.getAbsolutePath());
        }

        if (targetFile.isDirectory()) {
            throw new RuntimeException("Upload path is a directory, path:" + targetFile.getAbsolutePath());
        }

        try {
            logger.info("File transfer to " + targetFile.getAbsolutePath());
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "upload success";
    }

}
