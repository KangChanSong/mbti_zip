package com.mbtizip.dummies;

import com.mbtizip.domain.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CategoryDummyRepository {

    private final EntityManager em;

    @Transactional
    public void save(String name){
        em.persist(Category.builder()
                .name(name
                ).build());
    }
    @Transactional
    public void insertCategoies(){

        String[] names = new String[]{
                "음악가","정치인","연예인","애니주인공","연기자", "개발자"
        };

        for(int i = 0 ; i < names.length ; i++){
            save(names[i]);
        }
    }

    public Category getCategory(){
        return (Category) em.createQuery("select c from Category c")
                .getResultList().get(0);
    }
}
