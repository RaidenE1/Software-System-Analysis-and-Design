package se.buaa.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import se.buaa.Entity.Blob2;
import se.buaa.Entity.Response.Result;
import se.buaa.Repository.BlobRepository;
@Controller
public class UploadController {
    @Autowired
    BlobRepository blobRepository;

    @RequestMapping("/user/upload")
    @ResponseBody
    public Result upload(@RequestParam MultipartFile file) {
        if (file.getSize() > 1024 * 1024 * 10) {
            // Bigger than 10MB
            return Result.Error("201","文件过大");
        }
        try{
            Blob2 blob = new Blob2();
            blob.data = file.getBytes();
            blobRepository.save(blob);
            UploadRes data = new UploadRes();
            data.url = "http://124.70.63.71:8082/getImg?imgID="+blob.imgID;
            return Result.Success(data);
        }catch (Exception e){
            return Result.Error("201","图片上传错误");
        }
    }
    @RequestMapping(value = "/getImg",produces = "image/jpeg")
    @ResponseBody
    public byte[] getImg(@RequestParam int imgID) {
        getImgRes data = new getImgRes();
        data.img = blobRepository.findBlob2ByImgID(imgID).data;
        return data.img;
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(data.img);
    }

    public class getImgRes{
        public byte[] img;
    }
    public class UploadRes{
        public String url;
    }
}

