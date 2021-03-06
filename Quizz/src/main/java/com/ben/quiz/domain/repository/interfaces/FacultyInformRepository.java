package com.ben.quiz.domain.repository.interfaces;

import com.ben.quiz.domain.common.exception.QuizException;
import com.ben.quiz.domain.dto.request.FacultyInformSearchReq;
import com.ben.quiz.domain.dto.request.FacultyInformationSaveReq;
import com.ben.quiz.domain.dto.request.PagingReq;
import com.ben.quiz.domain.dto.result.FacultyInformDto;
import com.ben.quiz.domain.model.FacultyInformation;

import java.util.List;

public interface FacultyInformRepository extends BaseRepository {
    List<FacultyInformDto> search(FacultyInformSearchReq searchReq,
                                  PagingReq pagingReq) throws QuizException;
    long count(FacultyInformSearchReq searchReq)throws QuizException;

    FacultyInformation findByID(Integer iFacultyInformationPk)throws QuizException;

    FacultyInformation create(FacultyInformationSaveReq saveReq) throws QuizException ;

    FacultyInformation update(FacultyInformationSaveReq saveReq) throws QuizException ;

    void delete( Integer iFacultyInformationPk)throws QuizException ;
}
