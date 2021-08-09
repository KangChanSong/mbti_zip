package com.mbtizip.repository.common;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

/**
 * 여러 리포지토리에서 공통으로 쓰일 수 있는 메서드를 모아놓은 클래스
 */
public class CommonRepository {

    public static Object findAllByObject(EntityManager em, Class returnType ,Class matchType , Long id){
        String from = returnType.getSimpleName();
        String where = matchType.getSimpleName().toLowerCase(Locale.ROOT) + ".";

        return em.createQuery(
                "select o from " + from + " o" +
                " where o." + where + "id =: id")
                .setParameter("id", id)
                .getResultList();
    }
}
