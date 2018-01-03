var ExaminationDetail = function () {

    let template7 ={
        compileList : Template7.compile($("#template-table-student").html()),
        compileQuestion : Template7.compile($("#template-table-question").html()),
        confirmAdd : Template7.compile($("#template-confirm-adding-student").html()),
        viewTests : Template7.compile($("#template-tests-content").html())
    };
    let url = {
        urlDeleteQuestion : contextPath + "/question/delete",
        urlDeleteDetailExamination : contextPath + "examination/deleteDetail/ID",
        urlFindOneExamination : contextPath + "/examination/find/ID" + iExaminationInformationPk,
        urlFindDetailExamination : contextPath + "/examination/findDetailExamination/"+ iExaminationInformationPk,
        urlCountDetailExamination : contextPath +"/examination/countDetailExamination/"+ iExaminationInformationPk,
        urlCountQuestion : contextPath +"/question/countBySubjectID/",
        urlFindQuestion : contextPath +"/question/findBySubjectID/",
        urlFindDifficulty : contextPath +"/difficulty/find/ID",
        urlCountTest : contextPath + "/tests/countByTestID",
        urlViewTest : contextPath + "/tests/findByTestID"
    };
    let iSubjectInformationPk = 0;
    let iRateOfDifficultyPk = 0 ;

    let page = {
        currentPage : 1,
        rowCount : 0,
        rowPerPage : rowPerPage,
        pageCount: 0
    };

    let pageX = {
        currentPage : 1,
        rowCount : 0,
        rowPerPage : rowPerPage,
        pageCount: 0
    };

    let pageV = {
        currentPage : 1,
        rowCount : 0,
        rowPerPage : rowPerPage,
        pageCount: 0
    };
    let currentDetail ={
        numStudent : 0,
        numQuestionLv1 : 0,
        numQuestionLv2 : 0,
        numQuestionLv3 : 0,
        numQuestionLv4 : 0
    };

    let examination = {
        numQuestionLv1 : 0,
        numQuestionLv2 : 0,
        numQuestionLv3 : 0,
        numQuestionLv4 : 0
    };

    function init(){
        validUtil.autoValidation("confirm-value",ExaminationDetail.submit);
        loadOldExamination();
        loadOldDetailExamination();
    }

    function countQuestionFollowingDifficulty(res) {
        console.log("aaa");
        let data = $.map(res, function (value) {
            return [value];
        });
        for (let i = 0; i < data.length; i++) {
            if (data[i].iQuestionInformationLevel == 1) {
                currentDetail.numQuestionLv1 += 1;
            }
            if (data[i].iQuestionInformationLevel == 2) {
                currentDetail.numQuestionLv2 += 1;
            }
            if (data[i].iQuestionInformationLevel == 3) {
                currentDetail.numQuestionLv3 += 1;
            }
            if (data[i].iQuestionInformationLevel == 4) {
                currentDetail.numQuestionLv4 += 1;
            }
        }
        $("#btnAddStudent").click(function () {
            showConfirmAdd();
        });
    }
    function showConfirmAdd() {

        $("#confirm-adding-student").modal("show");
        $("#confirm-adding-student").html(template7.confirmAdd(currentDetail));
        $("#numberStudent").change(function () {
            calculatorNumberQuestion();
        });
        $("#numberPercent").change(function () {
            calculatorNumberQuestion();
        });

        $("#btnRepageAddStudent").click(function () {
            validUtil.autoValidation("confirm-value",ExaminationDetail.submit);
        });

    }
    function submit() {
        if((parseInt($("#numQuestionLv1").html())< parseInt(currentDetail.numQuestionLv1))
            && (parseInt($("#numQuestionLv2").html())< parseInt(currentDetail.numQuestionLv2))
            && (parseInt($("#numQuestionLv3").html())<parseInt(currentDetail.numQuestionLv3))
            && (parseInt($("#numQuestionLv4").html())< parseInt(currentDetail.numQuestionLv4))
        ){

            redirectPage("admin","a101_4",{iExaminationInformationPk :iExaminationInformationPk,
                numberStudent: $("#numberStudent").val(), percentMatch:$("#numberPercent").val()});
        }else
            $("#messErorr").html("<p>Số lượng câu hỏi của môn học không đủ.</p> </br>" +"<p>Tham khảo một số cách sau.</p>"+
                "<ul>" +
                "<li>Thêm mới câu hỏi</li>" +
                "<li>Thay đổi độ khó đề thi</li>" +
                "<li>Tăng độ trùng câu hỏi hoặc giảm số sinh viên xuống</li>" +
                "</ul>");
    }
    function calculatorNumberQuestion() {
        let studentNumber = parseInt($("#numberStudent").val()) + currentDetail.numStudent ;
        console.log(studentNumber);
        let numberQuestionLv1ForOneTests = examination.numQuestionLv1;
        let numberQuestionLv2ForOneTests = examination.numQuestionLv2;
        let numberQuestionLv3ForOneTests = examination.numQuestionLv3;
        let numberQuestionLv4ForOneTests = examination.numQuestionLv4;

        let percentMatch = $("#numberPercent").val();

        $("#numQuestionLv1").html(calculatorNumberQuestionForExamination(studentNumber,
            numberQuestionLv1ForOneTests,
            percentMatch));
        $("#numQuestionLv2").html(calculatorNumberQuestionForExamination(studentNumber,
            numberQuestionLv2ForOneTests,
            percentMatch));
        $("#numQuestionLv3").html(calculatorNumberQuestionForExamination(studentNumber,
            numberQuestionLv3ForOneTests,
            percentMatch));
        $("#numQuestionLv4").html(calculatorNumberQuestionForExamination(studentNumber,
            numberQuestionLv4ForOneTests,
            percentMatch));

    }



    function loadDifficulty() {
        if(iRateOfDifficultyPk == null){
            display("Có lỗi xảy ra với dữ liệu của kỳ thi. Kiểm tra lại trường đánh giá độ khó.");
            return
        }
        executeGetNew(contextPath +"/difficulty/find/ID" +iRateOfDifficultyPk,findSuccessDifficulty,findErrorDifficulty);

    }
    function findSuccessDifficulty(res) {
        examination.numQuestionLv1 = res.iNumQuestionLevel1;
        examination.numQuestionLv2 = res.iNumQuestionLevel2;
        examination.numQuestionLv3 = res.iNumQuestionLevel3;
        examination.numQuestionLv4 = res.iNumQuestionLevel4;
    }
    
    function findErrorDifficulty(err) {
        display(err.responseText);
    }





    function loadOldExamination() {
        executeGetNew(url.urlFindOneExamination,findSuccess, findError);
    }

    function findSuccess(res) {
        console.log(res);
        iSubjectInformationPk =res.iSubjectInformationPk ;
        iRateOfDifficultyPk = res.iRateOfDifficultyPk;
        $("#strExaminationInformationCode").val(res.strExaminationInformationCode);
        $("#dtExaminationDay").val(reFormatDateTime(res.dtExaminationDay));
        if(new Date($("#dtExaminationDay").val()) < new Date()){
            $("#btnAddStudent").removeClass("btn-blue");
            $("#btnAddStudent").attr('disabled','disabled');
            $("#preventAddStudent").html("Kỳ thi đã được diễn ra.");
        }
        loadQuestion();
        loadDifficulty();
        executeGetNew(url.urlFindQuestion+iSubjectInformationPk,countQuestionFollowingDifficulty,display);
    }
    function findError(err) {
        display(err.responseText);
    }

    
    function loadOldDetailExamination() {
      countDetailExamination();
    }
    function countDetailExamination() {
        executeGetNew(url.urlCountDetailExamination,countSuccess, countError);
    }

    function countError() {
        display("Hãy xem lại kết nối hoặc thử tải lại trang !")
    }
    function countSuccess(res) {
        if(res > 0){
            currentDetail.numStudent =res ;
            page.rowCount = res;
            page.pageCount = Math.ceil(page.rowCount / page.rowPerPage);
            changePage();
        }else {
            clearData();
        }
    }

    function changePage(pg) {
        let inputSearch ={};
        if(typeof pg =="undefined"){
            page.currentPage = 1;
        }else
            page.currentPage=pg;
        inputSearch["page"] = page.currentPage ;
        inputSearch["rowPerPage"] = page.rowPerPage ;
        executeGetNew(url.urlFindDetailExamination +"?"+ paramEncode(inputSearch),loadSuccess,loadError);
    }
    function loadSuccess(res) {
        $("#table-student-content").empty();
        let data = $.map(res, function (value) {
            return [value];
        });
        for (let i = 0; i < data.length; i++) {
            data[i]["index"] = (((page.currentPage - 1) * page.rowPerPage) + i + 1).toString();
            $("#table-student-content").append(template7.compileList(data[i]));
        }
        countIndexDetailExamination();
    }

    function countIndexDetailExamination() {
        countIndex(page.rowCount, page.rowPerPage, page.currentPage, 'txtPageCount', 'txtPageNavigator',"ExaminationDetail.changePage");
    }
    function loadError(err){
        display(err.responseText);
    }

    function clearData() {
        $("#table-student-content").html("<tr><td colspan='2'>"+noData+"</td></tr>");
        $("#txtPageCount").empty();
        $("#txtPageNavigator").empty();
    }


    function loadQuestion() {
        if(iSubjectInformationPk==null){
            clearDataQuestion();
            return
        }
        executeGetNew(url.urlCountQuestion+iSubjectInformationPk,countSuccessQuestion,countError);
    }


    function countSuccessQuestion(res) {
        if(res > 0){
            pageX.rowCount = res;
            pageX.pageCount = Math.ceil(pageX.rowCount / pageX.rowPerPage);
            changePageX();
        }else {
            clearDataQuestion();
        }
    }

    function changePageX(pg) {
        let inputSearch ={};
        if(typeof pg =="undefined"){
            pageX.currentPage = 1;
        }else pageX.currentPage =pg;
        inputSearch["page"] = pageX.currentPage ;
        inputSearch["rowPerPage"] = pageX.rowPerPage ;
        executeGetNew(url.urlFindQuestion+iSubjectInformationPk +"?"+ paramEncode(inputSearch),loadSuccessQuestion,loadErrorQuestion);
    }

    function loadSuccessQuestion(res) {
        $("#table-question").empty();
        let data = $.map(res, function (value) {
            return [value];
        });
        for (let i = 0; i < data.length; i++) {
            data[i]["index"] = (((pageX.currentPage - 1) * pageX.rowPerPage) + i + 1).toString();
            $("#table-question").append(template7.compileQuestion(data[i]));
        }
        countIndexQuestion();
    }

    function countIndexQuestion() {
        countIndex(pageX.rowCount, pageX.rowPerPage, pageX.currentPage, 'txtPageCountX', 'txtPageNavigatorX',"ExaminationDetail.changePageX");
    }
    function loadErrorQuestion(err) {
        display(err.responseText);
    }

    function clearDataQuestion() {
        $("#table-question").html("<tr><td colspan='2'>"+noData+"</td></tr>");
        $("#txtPageCountX").empty();
        $("#txtPageNavigatorX").empty();
    }

    let iTestInformationPkDetail ;
    function OpenTest(iTestInformationPk) {
        $("#modal-view-tests").modal("show");
        iTestInformationPkDetail = iTestInformationPk;
        countTest(iTestInformationPk);
    }

    function countTest(iTestInformationPk){
        let data = {
            iTestInformationPk :iTestInformationPk
        };
        executeGetNew(url.urlCountTest + "?" + paramEncode(data),countTestSuccess, display)
    }
    function countTestSuccess(res) {
        if(res > 0){
            pageV.rowCount = res;
            pageV.pageCount = Math.ceil(pageV.rowCount / pageV.rowPerPage);
            changePageV();
        }else clearDataTests();
    }

    function changePageV(pg) {
        let inputSearch ={};
        if(typeof pg =="undefined"){
            pageV.currentPage = 1;
        }else pageV.currentPage =pg;
        inputSearch["page"] = pageV.currentPage ;
        inputSearch["rowPerPage"] = pageV.rowPerPage ;
        inputSearch["iTestInformationPk"] =iTestInformationPkDetail;
        executeGetNew(url.urlViewTest +"?"+ paramEncode(inputSearch),viewSuccess,display);
    }

    function viewSuccess(res) {
        $("#test-content").empty();
        let data = $.map(res, function (value) {
            return [value];
        });
        for (let i = 0; i < data.length; i++) {
            data[i]["index"] = (((pageV.currentPage - 1) * pageV.rowPerPage) + i + 1).toString();
            $("#test-content").append(template7.viewTests(data[i]));
        }
        countIndexTests();
    }

    function countIndexTests() {
        countIndex(pageV.rowCount, pageV.rowPerPage, pageV.currentPage, 'txtPageCountV', 'txtPageNavigatorV',"ExaminationDetail.changePageV");
    }
    function clearDataTests() {
        $("#test-content").html("<tr><td colspan='2'>"+noData+"</td></tr>");
        $("#txtPageCountV").empty();
        $("#txtPageNavigatorV").empty();
    }
    return{
        init: init,
        changePage:changePage,
        changePageX:changePageX,
        changePageV : changePageV,
        submit:submit,
        OpenTest : OpenTest
    };
}();



$(document).ready(function () {
   ExaminationDetail.init() ;
});