package com.mbtizip.other;

import com.mbtizip.domain.job.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class JpqlTest {

    @Autowired
    private EntityManager em;

    @Test
    public void SELECT_MULTIPLE_ENTITY(){

        //given
        Job job = (Job) em.createQuery("select j from Job j").getResultList().get(0);
        List<Object[]> resultList = (List<Object[]>) em.createQuery("select mc , m from MbtiCount mc " +
                "join fetch Mbti m on mc.mbti.id = m.id " +
                "where mc.job.id = :id")
                .setParameter("id", job.getId())
                .getResultList();
        //when
        resultList.forEach(o-> {
            System.out.println("==============================");
            for(int i = 0 ; i < o.length ; i++){
                System.out.println(o[i].toString());
            }
        });
        //then
    }
}
