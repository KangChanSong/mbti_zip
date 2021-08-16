package com.mbtizip.controller.file;

import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            produces = "image/jpeg")
    public byte[] get(@PathVariable("personId") Long personId) throws IOException {

        return fileService.loadFile(personId);
    }

    @DeleteMapping("/api/v1/delete/{personId}")
    public BooleanResponseDto delete(@PathVariable("personIdq") Long personId){

        Boolean isSuccess = fileService.deleteFile(personId);
        return new BooleanResponseDto(isSuccess);
    }
}
