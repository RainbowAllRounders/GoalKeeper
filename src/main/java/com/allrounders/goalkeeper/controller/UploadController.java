package com.allrounders.goalkeeper.controller;

import com.allrounders.goalkeeper.dto.UploadFileDTO;
import com.allrounders.goalkeeper.dto.UploadResultDTO;
import com.allrounders.goalkeeper.service.ImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UploadController {

    @Value("${com.allrounders.upload.path}")
    private String uploadPath;

    private final ImgService imgService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){

        log.info(uploadFileDTO);

        if(uploadFileDTO.getFiles() != null){
            imgService.uploadImg(uploadFileDTO, uploadPath);
        }
        return null;
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable(name="fileName") String fileName){

        log.info("fileName = ", fileName);
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return  ResponseEntity.ok().headers(headers).body(resource);
    }
}
