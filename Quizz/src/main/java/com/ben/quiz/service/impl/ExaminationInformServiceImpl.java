package com.ben.quiz.service.impl;

import com.ben.quiz.domain.common.constant.CodeConst;
import com.ben.quiz.domain.common.constant.SequenceConst;
import com.ben.quiz.domain.common.exception.QuizException;
import com.ben.quiz.domain.dto.request.CreateListTestsReq;
import com.ben.quiz.domain.dto.request.ExaminationInformationSaveReq;
import com.ben.quiz.domain.dto.request.ExaminationInformationSearchReq;
import com.ben.quiz.domain.dto.request.PagingReq;
import com.ben.quiz.domain.dto.result.ExaminationInformationDetailDto;
import com.ben.quiz.domain.dto.result.ExaminationInformationDto;
import com.ben.quiz.domain.dto.result.TestInformationDetailDto;
import com.ben.quiz.domain.model.*;
import com.ben.quiz.domain.repository.interfaces.*;
import com.ben.quiz.service.interfaces.ExaminationInformService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Transactional
@Service("examinationService")
public class ExaminationInformServiceImpl implements ExaminationInformService {

    private ModelMapper modelMapper;
    private UtilRepository utilRepository;
    private UserRepository userRepository;
    private ExaminationInformRepository examinationInformRepository;
    private RoomInformRepository roomInformRepository;
    private SubjectInformRepository subjectInformRepository;
    private TeacherInformRepository teacherInformRepository;
    private RateOfDifficultyRepository rateOfDifficultyRepository;
    private StudentInformRepository studentInformRepository;
    private TestInformRepository testInformRepository;
    private final TestInformationDetailRepository testInformationDetailRepository ;


    @Autowired
    public ExaminationInformServiceImpl(ModelMapper modelMapper,
                                        UtilRepository utilRepository,
                                        UserRepository userRepository,
                                        ExaminationInformRepository examinationInformRepository,
                                        RoomInformRepository roomInformRepository,
                                        SubjectInformRepository subjectInformRepository,
                                        TeacherInformRepository teacherInformRepository,
                                        RateOfDifficultyRepository rateOfDifficultyRepository,
                                        StudentInformRepository studentInformRepository,
                                        TestInformRepository testInformRepository, TestInformationDetailRepository testInformationDetailRepository) {
        this.modelMapper = modelMapper;
        this.utilRepository = utilRepository;
        this.userRepository = userRepository;
        this.examinationInformRepository =examinationInformRepository;
        this.roomInformRepository = roomInformRepository;
        this.subjectInformRepository = subjectInformRepository;
        this.teacherInformRepository = teacherInformRepository;
        this.rateOfDifficultyRepository = rateOfDifficultyRepository;
        this.studentInformRepository = studentInformRepository;
        this.testInformRepository = testInformRepository;
        this.testInformationDetailRepository = testInformationDetailRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExaminationInformationDto> search(ExaminationInformationSearchReq searchReq, PagingReq pagingReq) throws QuizException {
        return examinationInformRepository.search(searchReq,pagingReq);
    }

    @Transactional(readOnly = true)
    @Override
    public long count(ExaminationInformationSearchReq searchReq) throws QuizException {
        return examinationInformRepository.count(searchReq);
    }

    @Transactional(readOnly = true)
    @Override
    public ExaminationInformationDto findByID(Integer iExaminationInformationPk) throws QuizException {
        return examinationInformRepository.findByID(iExaminationInformationPk);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExaminationInformationDto> findByDifficultyPk(Integer iRateOfDifficultyPk) throws QuizException {
        List<ExaminationInformationDto> examinationInformationDtos = new ArrayList<>();
        RateOfDifficulty rateOfDifficulty =  rateOfDifficultyRepository.findOne(RateOfDifficulty.class,iRateOfDifficultyPk);
        List<ExaminationInformation> examinationInformations = (List<ExaminationInformation>)
                rateOfDifficulty.getExaminationInformationsByIRateOfDifficultyPk();
        for (ExaminationInformation examinationInformation:examinationInformations) {
            examinationInformationDtos.add(convertToExaminationInformDtoFromDifficulty(examinationInformation,rateOfDifficulty));
        }
        return examinationInformationDtos;
    }
    private ExaminationInformationDto convertToExaminationInformDtoFromDifficulty(ExaminationInformation examinationInformation,
                                                                 RateOfDifficulty rateOfDifficulty){
        ExaminationInformationDto examinationInformationDto =new ExaminationInformationDto();
        modelMapper.map(examinationInformation,examinationInformationDto);
        examinationInformation.setRateOfDifficultyByIRateOfDifficultyPk(rateOfDifficulty);
        modelMapper.map(examinationInformation,examinationInformationDto);
        return examinationInformationDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExaminationInformationDto> findBySubjectPk(Integer iSubjectInformationPk) throws QuizException {
        List<ExaminationInformationDto> examinationInformationDtos = new ArrayList<>();
        SubjectInformation subjectInformation =  subjectInformRepository.findOne(SubjectInformation.class,iSubjectInformationPk);
        List<ExaminationInformation> examinationInformations = (List<ExaminationInformation>)
                subjectInformation.getExaminationInformationsByISubjectInformationPk();
        for (ExaminationInformation examinationInformation:examinationInformations) {
            examinationInformationDtos.add(convertToExaminationInformDtoFromSubject(examinationInformation,subjectInformation));
        }
        return examinationInformationDtos;
    }
    private ExaminationInformationDto convertToExaminationInformDtoFromSubject(ExaminationInformation examinationInformation,
                                                                           SubjectInformation subjectInformation){
        ExaminationInformationDto examinationInformationDto =new ExaminationInformationDto();
        modelMapper.map(examinationInformation,examinationInformationDto);
        examinationInformation.setSubjectInformationByISubjectInformationPk(subjectInformation);
        modelMapper.map(examinationInformation,examinationInformationDto);
        return examinationInformationDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ExaminationInformationDto> findByTeacherPk(Integer iTeacherInformationPk) throws QuizException {
        List<ExaminationInformationDto> examinationInformationDtos = new ArrayList<>();
        TeacherInformation teacherInformation =  teacherInformRepository.findOne(TeacherInformation.class,iTeacherInformationPk);
        List<ExaminationInformation> examinationInformations = (List<ExaminationInformation>)
                teacherInformation.getExaminationInformationsByITeacherInformationPk();
        for (ExaminationInformation examinationInformation:examinationInformations) {
            examinationInformationDtos.add(convertToExaminationInformDtoFromTeacher(examinationInformation,teacherInformation));
        }
        return examinationInformationDtos;
    }
    private ExaminationInformationDto convertToExaminationInformDtoFromTeacher(ExaminationInformation examinationInformation,
                                                                               TeacherInformation teacherInformation){
        ExaminationInformationDto examinationInformationDto =new ExaminationInformationDto();
        modelMapper.map(examinationInformation,examinationInformationDto);
        examinationInformation.setTeacherInformationByITeacherInformationPk(teacherInformation);
        modelMapper.map(examinationInformation,examinationInformationDto);
        return examinationInformationDto;
    }


    @Transactional(readOnly = true)
    @Override
    public List<ExaminationInformationDto> findByRoomPk(Integer iExaminationRoomPk) throws QuizException {
        List<ExaminationInformationDto> examinationInformationDtos = new ArrayList<>();
        ExaminationRoom examinationRoom =  roomInformRepository.findOne(ExaminationRoom.class,iExaminationRoomPk);
        List<ExaminationInformation> examinationInformations = (List<ExaminationInformation>)
                examinationRoom.getExaminationInformationsByIExaminationRoomPk();
        for (ExaminationInformation examinationInformation:examinationInformations) {
            examinationInformationDtos.add(convertToExaminationInformDtoFromRoom(examinationInformation,examinationRoom));
        }
        return examinationInformationDtos;
    }
    private ExaminationInformationDto convertToExaminationInformDtoFromRoom(ExaminationInformation examinationInformation,
                                                                               ExaminationRoom examinationRoom){
        ExaminationInformationDto examinationInformationDto =new ExaminationInformationDto();
        modelMapper.map(examinationInformation,examinationInformationDto);
        examinationInformation.setExaminationRoomByIExaminationRoomPk(examinationRoom);
        modelMapper.map(examinationInformation,examinationInformationDto);
        return examinationInformationDto;
    }

    @Override
    public ExaminationInformation create(ExaminationInformationSaveReq saveReq) throws QuizException {
        saveReq.setiExaminationInformationPk(
                utilRepository.findSequenceNextval(SequenceConst.EXAMINATION_INFORMATION_SEQ).intValue());

        saveReq.setiExaminationInformationPkEk(saveReq.getiExaminationInformationPk());
        ExaminationInformation examinationInformation = modelMapper.map(saveReq ,ExaminationInformation.class);
        return examinationInformRepository.add(examinationInformation);
    }

    @Override
    public ExaminationInformation update(ExaminationInformationSaveReq saveReq) throws QuizException {
        ExaminationInformation examinationInformation = new ExaminationInformation();
        ExaminationInformationDto examinationInformationDto = examinationInformRepository.findByID(
                saveReq.getiExaminationInformationPk());

        modelMapper.map(saveReq,examinationInformation);
        modelMapper.map(examinationInformationDto,examinationInformation);
        
        examinationInformation.setiExaminationInformationPkEk(examinationInformation.getiExaminationInformationPk());
        return examinationInformRepository.save(examinationInformation);
    }
    @Transactional(rollbackFor = QuizException.class)
    @Override
    public void createStudentAndTest(CreateListTestsReq saveReq) throws QuizException {
        List<Integer> idStudent =saveReq.getiStudentInformationPk();
        List<List<Integer>> idQuestion = saveReq.getiQuestionInformationPk();
        for (int index=0; index < idStudent.size() ; index++){
            DeleteTest(idStudent.get(index),saveReq.getiExaminationInformationPk());
            TestInformation testInformation = testInformRepository.add(generateTestInformation(idStudent.get(index),
                    saveReq.getiExaminationInformationPk()));
            if(saveReq.getiQuestionInformationPk().size() ==0){
                throw new QuizException(1,"null");
            }
            generateTestInformationDetail(idQuestion.get(index),testInformation.getiTestInformationPk());
        }
    }
    private void generateTestInformationDetail(List<Integer> iQuestionInformations,Integer iTestInformationPk ){

        for (Integer iQuestionInformation:iQuestionInformations
             ) {
            TestInformationDetail testInformationDetail = new TestInformationDetail();
            testInformationDetail.setiQuestionInformationPk(iQuestionInformation);
            testInformationDetail.setiTestInformationPk(iTestInformationPk);
            testInformationDetail.setiTestInformationDetailPk(utilRepository.findSequenceNextval(SequenceConst.TEST_INFORMATION_DETAIL_SEQ).intValue());
            testInformationDetail.setiTestDetailInformationPkEk(testInformationDetail.getiTestInformationDetailPk());
            testInformationDetailRepository.add(testInformationDetail);
        }
    }
    private void DeleteTest(Integer iStudentInformationPk,Integer iExaminationInformationPk) throws QuizException {
        try {
            ExaminationInformationDetailDto result = testInformRepository.findByStudentPkAndExaminationPk(iStudentInformationPk, iExaminationInformationPk);

            DeleteDetailTest(result.getiTestInformationPk());
            testInformRepository.delete(TestInformation.class,result.getiTestInformationPk());
        }catch (QuizException e){
            if(e.getErrorCode() != CodeConst.ErrorCode.Err_Deleted_Record){
                throw new QuizException(e.getErrorCode(),"Error system");
            }
        }
    }

    private void DeleteDetailTest(Integer iTestInformationPk) throws QuizException {
       List<TestInformationDetailDto> results =
               testInformationDetailRepository.findByTestInformationPk(iTestInformationPk,new PagingReq());
        for (TestInformationDetailDto result: results
             ) {
            testInformationDetailRepository.delete(TestInformationDetail.class,result.getiTestInformationDetailPk());
        }
    }

    private TestInformation generateTestInformation(Integer iStudentInformationPk,Integer iExaminationInformationPk) throws QuizException {
        TestInformation testInformation = new TestInformation();
        testInformation.setiTestInformationPk(
                utilRepository.findSequenceNextval(SequenceConst.TEST_INFORMATION_SEQ).intValue());

        testInformation.setiTestInformationPkEk(testInformation.getiTestInformationPk());

        testInformation.setiExaminationInformationPk(iExaminationInformationPk);

        testInformation.setiStudentInformationPk(iStudentInformationPk);

        testInformation.setStudentInformationByIStudentInformationPk(studentInformRepository.findOne(StudentInformation.class,iStudentInformationPk));

        testInformation.setExaminationInformationByIExaminationInformationPk(
                examinationInformRepository.findOne(ExaminationInformation.class,
                        iExaminationInformationPk));
        return testInformation ;
    }
    @Transactional
    @Override
    public void delete(Integer iExaminationInformationPk) throws QuizException {
        ExaminationInformation examinationInformation = modelMapper.map(examinationInformRepository.findByID(
                iExaminationInformationPk),ExaminationInformation.class);

        examinationInformation.setiExaminationInformationPkEk(null);
        examinationInformRepository.save(examinationInformation);
    }

    @Override
    public List<ExaminationInformationDetailDto> findDetailExamination(Integer iExaminationInformationPk, PagingReq pagingReq) throws QuizException {
        return testInformRepository.findTestByExaminationID(iExaminationInformationPk,pagingReq);
    }

    @Override
    public Long countDetailExamination(Integer iExaminationInformationPk) throws QuizException {
        return testInformRepository.countTestByExaminationID(iExaminationInformationPk);
    }
}
