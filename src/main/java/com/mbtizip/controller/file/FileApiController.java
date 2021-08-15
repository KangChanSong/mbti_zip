package com.mbtizip.controller.file;

import com.google.common.net.MediaType;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("file")
public class FileApiController {

    private final FileService fileService;

    @PostMapping("/api/v1/upload/{personId}")
    public BooleanResponseDto upload(@PathVariable("personId") Long personId,
                                     MultipartFile file){
        Boolean isSuccess = fileService.saveFile(personId, file);
        return new BooleanResponseDto(isSuccess);
    }

    @GetMapping(
            value = "/api/v1/get/{personId}",
            produces = "image/jpeg"
    )
    public byte[] get(@PathVariable("personId") Long personId){

        InputStream inputStream = fileService.loadFile(personId);
        return null;
    }
}
