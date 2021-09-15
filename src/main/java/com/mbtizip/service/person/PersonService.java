package com.mbtizip.service.person;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.domain.candidate.person.dto.PersonGetDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface PersonService {

    String NO_CATEGORY = "없음";

    Boolean registerWithCategory(Person person, Long categoryId);
    Person getById(Long saveId);

    List<PersonGetDto> findAll(Page page, OrderSpecifier sort, BooleanExpression keyword);
    List<PersonGetDto> findAllWithMbti(Page page, OrderSpecifier sort, Long mbtiId);

    Boolean delete(Long id, String password);

    Boolean vote(Long personId, Long mbtiId);
    Boolean cancelVote(Long personId, Long mbtiId);

    Boolean like(Long personId);
    Boolean cancelLike(Long personId);

    Boolean increaseView(Long personId);
    Long getTotalCount();
    Boolean checkIfExists(String name);
}
