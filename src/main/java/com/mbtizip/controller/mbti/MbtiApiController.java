package com.mbtizip.controller.mbti;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.dto.MbtiListDto;
import com.mbtizip.repository.mbti.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mbti")
@RequiredArgsConstructor
public class MbtiApiController {

    private final MbtiRepository mbtiRepository;

    @GetMapping("/api/v1/list")
    public MbtiListDto getList(){
        List<Mbti> mbtis = mbtiRepository.findAll();
        return MbtiListDto.toDto(mbtis);
    }
}
