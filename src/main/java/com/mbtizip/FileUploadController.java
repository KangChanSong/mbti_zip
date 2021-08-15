package com.mbtizip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class FileUploadController {

    @GetMapping("/file")
    public String fileUploadForm(){
        return "/fileupload";
    }

    @PostMapping("/file")
    public String fileUpload(@RequestParam MultipartFile file, RedirectAttributes attributes){
        log.info(file.getName());
        log.info(file.getOriginalFilename());

        String message = file.getOriginalFilename() + " is uploaded";

        attributes.addFlashAttribute("massage", message );

        return "redirect:/file";
    }
}
