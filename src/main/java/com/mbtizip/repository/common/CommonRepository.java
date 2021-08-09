package com.mbtizip.repository.common;

import com.mbtizip.domain.mbti.Mbti;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;
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

    /**
     * 엔티티의 like 증감 메소드의 이름이 modifyLikes 이고, Boolean 타입을 파라미터로 받아야함
     */
    public static void modifyLikes(EntityManager em , Class entityType , Long id , Boolean isIncrease){

        try {
            Object findObj = em.find(entityType, id);
            Method method = findObj.getClass().getMethod("modifyLikes", Boolean.class);
            method.invoke(findObj, isIncrease);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 엔티티의 MBTI 변경 메소드의 이름이 changeMbti 이어야 하고 파라미터 타입이 Mbti 이어야 함
     */
    public static void changeMbti(EntityManager em, Class entityType, Long id , Mbti mbti) {

        try {
            Object findObj = em.find(entityType, id);
            Method method = findObj.getClass().getMethod("changeMbti", Mbti.class);
            method.invoke(findObj, mbti);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void methodInvoker(){
        try {
            Object findObj = em.find(entityType, id);
            Method method = findObj.getClass().getMethod("changeMbti", Mbti.class);
            method.invoke(findObj, mbti);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
