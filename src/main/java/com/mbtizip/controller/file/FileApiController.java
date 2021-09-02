package com.mbtizip.controller.file;

import com.mbtizip.controller.common.common.InteractionControllerHelper;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.service.file.FileService;
import com.mbtizip.service.file.store.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.mbtizip.controller.common.common.InteractionControllerHelper.handleTarget;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("file")
public class FileApiController {

    private final FileService fileService;

    @PostMapping("/api/v1/upload")
    public FileResponseDto upload(MultipartFile file){
        return new FileResponseDto(fileService.upload(file));
    }

    @PostMapping("/api/v1/upload/{target}/{targetId}")
    public BooleanResponseDto upload(@PathVariable("targetId") Long targetId,
                                     @PathVariable("target") String target,
                                     MultipartFile file){

        return new BooleanResponseDto(
                handleTarget(target,
                () -> fileService.saveFileWithPerson(targetId, file),
                () -> fileService.saveFileWithJob(targetId, file)));
    }

    @GetMapping(
            value = "/api/v1/get/{target}/{targetId}",
            produces = {"image/*"})
    public byte[] get(@PathVariable("target") String target ,
                      @PathVariable("targetId") Long targetId) throws IOException {

        return handleTarget(target,
                () -> fileService.loadFileByPerson(targetId),
                () -> fileService.loadFileByJob(targetId));
    }

    @DeleteMapping("/api/v1/delete/{target}/{targetId}")
    public BooleanResponseDto delete(@PathVariable("target") String target,
                                     @PathVariable("targetId") Long targetId){

        return new BooleanResponseDto(
                handleTarget(target,
                        () -> fileService.deleteFileByPerson(targetId),
                        () -> fileService.deleteFileByJob(targetId)));
    }

    @AllArgsConstructor
    @Data
    private static class FileResponseDto{
        private String fullName;
    }
}
