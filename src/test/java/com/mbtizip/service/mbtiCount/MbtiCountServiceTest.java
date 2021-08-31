package com.mbtizip.service.mbtiCount;

import com.mbtizip.common.enums.TestJobEnum;
import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.repository.test.TestJobRepository;
import org.hibernate.cache.spi.support.NaturalIdReadOnlyAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import javax.print.attribute.standard.JobKOctets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.mbtizip.common.enums.TestJobEnum.JOB_TITLE;
import static com.mbtizip.common.enums.TestJobEnum.JOB_WRITER;
import static com.mbtizip.common.util.TestEntityGenerator.*;
import static com.mbtizip.domain.mbti.MbtiEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MbtiCountServiceTest {

    MbtiCountService mbtiCountService;

    @Mock
    MbtiCountRepository mockMbtiCountRepository;

    @Mock
    MbtiRepository mockMbtiRepository;

    // 테스트 대역을 써보려고 했다가 실패함
    @BeforeEach
    public void setUp(){
        mbtiCountService = new MbtiCountServiceImpl( mockMbtiCountRepository,mockMbtiRepository);
    }


    @DisplayName("직업 득표율의 수에 따라 결과가 달라져야 함 (0, 1, 2+)")
    @Test
    public void 직업_투표수_반영(){

        jobResultNumberTest(0, NONE);
        jobResultNumberTest(1, ENTP);
        jobResultNumberTest(2, DRAW);
    }

    @DisplayName("인물 득표율의 수에 따라 결과가 달라져야 함 (0, 1, 2+)")
    @Test
    public void 인물_투표수_반영(){

        personResultNumberTest(0 ,NONE);
        personResultNumberTest(1, ENTP);
        personResultNumberTest(2, DRAW);
    }

    @DisplayName("NONE 이나 DRAW에게 투표했을 시 예외가 던저지는 테스트")
    @Test
    public void 잘못된_투표(){

        //given
        Job job = createJob();
        Mbti none = createMbti(NONE);
        Mbti draw = createMbti(DRAW);
        //then
        assertThrows(IllegalArgumentException.class , () -> mbtiCountService.vote(none, job));
        assertThrows(IllegalArgumentException.class , () -> mbtiCountService.vote(draw, job));

    }

    @DisplayName("직업 투표를 취소하는 테스트")
    @Test
    public void 직업_투표_취소(){

        //given
        Job job = createJob();
        //when
        commonCancleVote(job);
        //then
        assertEquals(job.getMbti().getName(), NONE);

    }
    
    @DisplayName("인물 투표를 취소하는 테스트")
    @Test
    public void 인물_투표_취소(){

        //given
        Person person = createPerson();
        //when
        commonCancleVote(person);
        //then
        assertEquals(person.getMbti().getName(), NONE);
    }



    /**
     * MbtiCount 에 직업에 대한 MBTI 가 아예 존재하지 않으면 득표수 0 으로 치환
     */
    @DisplayName("한 직업의 모든 MBTI 의 투표수를 가져오는 테스트")
    @Test
    public void 직업_목록_조회(){
        //given
        Long jobId = createJob().getId();
        OngoingStubbing stubbing = when(mockMbtiCountRepository.findAllByJob(jobId));
        Supplier supplier = () -> mbtiCountService.getVotesByJob(jobId);

        //then
        getListTest(stubbing, supplier);

    }
    
    @DisplayName("한 인물의 모든 MBTI의 투표수를 가져오는 테스트")
    @Test
    public void 인물_목록_조회(){
        //given
        Long personId = createPerson().getId();
        OngoingStubbing stubbing = when(mockMbtiCountRepository.findAllByPerson(personId));
        Supplier supplier = () -> mbtiCountService.getVotesByPerson(personId);

        //then
        // 코드는 보기에 깔끔해졋으나 가독성이 안좋아진게 아닌지
        getListTest(stubbing, supplier);
    }

    //== private 메서드 ==//

    private void getListTest(OngoingStubbing stubbing, Supplier supplier){
        //given
        MbtiCount infp = createMbtiCount(INFP);
        MbtiCount entp = createMbtiCount(ENTP);
        MbtiCount intj = createMbtiCount(INTJ);
        intj.updateCount(true);

        List<MbtiCount> mbtiCounts = new ArrayList<>();
        mbtiCounts.add(infp);
        mbtiCounts.add(entp);
        mbtiCounts.add(intj);

        //when
        stubbing.thenReturn(mbtiCounts);

         List<MbtiCount> result = (List<MbtiCount>) supplier.get();

        //then
    }

    private void jobResultNumberTest(int resultNumber, MbtiEnum resultMbtiName){

        //given
        Mbti mbti = createMbti(INFP);
        Job job = createJob();
        //when
        commonResultNumberTest(resultNumber, resultMbtiName, job,
                when(mockMbtiCountRepository.findMaxByJob(job)));
        //then
        assertEquals(job.getMbti().getName(), resultMbtiName);
    }

    private void personResultNumberTest(int resultNumber, MbtiEnum resultMbtiName){
        //given
        Person person = createPerson();
        //when
        commonResultNumberTest(resultNumber, resultMbtiName, person,
                when(mockMbtiCountRepository.findMaxByPerson(person))
        );
        //then
        assertEquals(person.getMbti().getName(), resultMbtiName);
    }

    private void commonResultNumberTest(int resultNumber, MbtiEnum resultMbtiName, Object obj,
                                        OngoingStubbing stubbing){
        //when
        Mbti mbti = createMbti(INFP);
        List<MbtiCount> mbtiCounts = new ArrayList<>();

        if(resultNumber == 1){
            mbtiCounts.add(MbtiCount.builder()
                    .mbti(createMbti(resultMbtiName)).build());
            resultMbtiName = INFP;
        } else {
            IntStream.range(0, resultNumber).forEach(i -> mbtiCounts.add(MbtiCount.builder().build()));
        }

        stubbing.thenReturn(mbtiCounts);

        lenient().when(mockMbtiRepository.findByName(resultMbtiName))
                .thenReturn(createMbti(resultMbtiName));

        mbtiCountService.vote(mbti, obj);
    }

    private void commonCancleVote(Object obj){
        Mbti mbti = createMbti(INFP);
        when(mockMbtiRepository.findByName(NONE))
                .thenReturn(Mbti.builder().name(NONE).build());

        mbtiCountService.cancelVote(mbti, obj);
    }


}
