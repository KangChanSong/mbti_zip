package com.mbtizip.controller.common.common;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

import static com.mbtizip.controller.common.TargetProperties.TARGET_JOB;
import static com.mbtizip.controller.common.TargetProperties.TARGET_PERSON;

@Slf4j
public class InteractionControllerHelper {
    public static final String TARGET_INVALID_ERROR_MESSAGE = "target 파라미터가 적합하지 않습니다. target = ";

    public static <T> T handleTarget(String target,
                               Supplier<T> personMethod,
                               Supplier<T> jobMethod ){
        T obj;
        if (target.equals(TARGET_PERSON)) obj = personMethod.get();
        else if (target.equals(TARGET_JOB)) obj = jobMethod.get();
        else throw new IllegalArgumentException(TARGET_INVALID_ERROR_MESSAGE + target);

        return obj;
    }
}
