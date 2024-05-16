package com.allrounders.goalkeeper.service;

import com.allrounders.goalkeeper.dto.UploadFileDTO;
import com.allrounders.goalkeeper.dto.UploadResultDTO;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ImgService {

    public List<UploadResultDTO> uploadImg(UploadFileDTO uploadFileDTO, String uploadPath) {

        final List<UploadResultDTO> list = new ArrayList<>();

        uploadFileDTO.getFiles().forEach(multipartFile -> {

            String originalName = multipartFile.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();

            Path savePath = Paths.get(uploadPath, uuid+"_"+ originalName);

            boolean image = false;

            try {
                multipartFile.transferTo(savePath);

                if(Files.probeContentType(savePath).startsWith("image")){

                    image = true;

                    File thumbFile = new File(uploadPath, "s_" + uuid+"_"+ originalName);

                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200,200);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            list.add(UploadResultDTO.builder()
                    .uuid(uuid)
                    .fileName(originalName)
                    .img(image)
                    .build()
            );
        });
        return list;
    }
}
