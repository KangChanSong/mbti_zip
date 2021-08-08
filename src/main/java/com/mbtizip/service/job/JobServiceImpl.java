package com.mbtizip.service.job;

import com.mbtizip.repository.job.JobRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobServiceImpl {

    private final JobRepositoryImpl jobRepository;
}
