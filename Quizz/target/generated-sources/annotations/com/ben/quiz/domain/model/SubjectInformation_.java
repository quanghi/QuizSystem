package com.ben.quiz.domain.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SubjectInformation.class)
public abstract class SubjectInformation_ extends com.ben.quiz.domain.model.BaseEntity_ {

	public static volatile SingularAttribute<SubjectInformation, Integer> iSubjectInformationPk;
	public static volatile CollectionAttribute<SubjectInformation, ExaminationList> examinationListsByISubjectInformationPk;
	public static volatile SingularAttribute<SubjectInformation, Integer> iSubjectInformationPkEk;
	public static volatile SingularAttribute<SubjectInformation, String> strSubjectInformationName;
	public static volatile CollectionAttribute<SubjectInformation, QuestionInformation> questionInformationsByISubjectInformationPk;
	public static volatile SingularAttribute<SubjectInformation, Integer> iSubjectInformationCreditNum;

}
