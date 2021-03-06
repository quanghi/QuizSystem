<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false"%>
<%@ page isELIgnored="false"%>

<h1 class="heading-1 margin-bottom background"><span>Danh sách thông tin khoa</span></h1>
<section class="form-search form-style">
    <p class="caption btn-box-toggle">
        <i class="fa fa-chevron-circle-right" aria-hidden="true"></i>
        Nhập để tìm kiếm
    </p>

    <div class="box-toggle row">
        <div class="col-xs-12 clear-padding">
            <div class="col-xs-12 col-sm-3 col-lg-2">
                <label>Mã khoa(tên viết tắt)</label>
                <input class="form-control" type="text" id="strFacultyInformationShortName"/>
            </div>

            <div class="col-xs-12 col-sm-3 col-lg-2 mt-xs">
                <label>Tên khoa đầy đủ </label>
                <input class="form-control" type="text" id="strFacultyInformationFullName" />
            </div>

        </div>
        <div class="col-xs-12 col-sm-2 col-button float-r mt">
            <button class="btn btn-search btn-green w100p btn-fa" id="btnSearch"><i class="fa fa-search" aria-hidden="true"></i>Tìm kiếm</button>
        </div>
    </div>
</section><!-- /.form-search -->

<div class="row row-small layout mt20">
    <div class="col-xs-12 col-sm-inline padding-small">
        <button class="btn btn-blue btn-reponsive btn-medium float-l" onclick="redirectPage('admin','a109_2');">Thêm mới</button>
    </div>

    <p id="txtPageCount" class="float-r txt-small mt15 mr5"></p>
    <div class="clearfix"></div>
</div>

<section class="box-list box-shadow-radius">
    <div class="scrollx-md">
        <table class="table-border table-center table-lg">
            <colgroup>
                <col class="col-number" />
                <col />
                <col />
                <col />
                <col class="col-button" />
            </colgroup>
            <tr>
                <th class="first-child">STT</th>
                <th><a>Mã khoa </a></th>
                <th><a>Mã khoa (tên viết tắt) </a></th>
                <th><a>Tên đầy đủ</a></th>
                <th class="last-child"></th>
            </tr>

            <tbody id="table-content" >

            </tbody>
        </table>
    </div><!-- /.scroll -->
</section><!-- /.list-search -->

<nav aria-label="Page navigation">
    <ul id="txtPageNavigator" class="pagination float-r"></ul>
    <div class="clearfix"></div>
</nav>

<div id="modal-delete" class="modal-confirm modal modal-style-1 fade" role="dialog">
</div><!-- /.modal -->

<script type="text/template7" id="template-delete-faculty-list">
    <div class="modal-dialog box-medium">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title heading-1">
                    <i class="fa fa-trash mr10" aria-hidden="true"></i>Bạn có thực sự muốn xóa ?
                </h4>
            </div>

            <div class="modal-body">
                <h4 class="heading-4 txt-center mb15">Thông tin khoa</h4>

                <div class="box-shadow-radius overflow-h">

                    <div class="scrollx-xs">
                        <table class="table-border-2 table-short table-xs th-nomarl">
                            <colgroup>
                                <col class="col-xs-5 col-sm-4" />
                            </colgroup>

                            <tr>
                                <th>Mã khoa</th>
                                <td>{{iFacultyInformationPk}}</td>
                            </tr>
                            <tr>
                                <th>Mã khoa(tên viết tắt)</th>
                                <td>{{strFacultyInformationShortName}}</td>
                            </tr>

                            <tr>
                                <th>Tên đầy đủ </th>
                                <td>{{strFacultyInformationFullName}}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <div class="btn-modal">
                    <button class="btn btn-delete btn-confirm btn-medium btn-fa" onclick="FacultyList.deleteOne('{{iFacultyInformationPk}}')" >
                        Xóa
                    </button>
                </div>
                <div class="btn-modal">
                    <button type="button" class="btn btn-gray btn-medium" data-dismiss="modal">Hủy bỏ</button>
                </div>
            </div>
        </div>
    </div>
</script>


<script type="text/template7" id="table-template-faculty-list">
    <tr>
        <td class="txt-center">{{index}}</td>

        <td>
            {{iFacultyInformationPk}}
        </td>

        <td>
            {{strFacultyInformationShortName}}
        </td>
        <td>{{strFacultyInformationFullName}}</td>

        <td class="txt-center">
            <button class="btn btn-update btn-small"
                    onclick="redirectPage('admin','a109_1','{{iFacultyInformationPk}}');" >Sửa</button><br/>
            <button class="btn btn-delete btn-small mt5" onclick="FacultyList.showDelete('{{iFacultyInformationPk}}');">Xóa</button>
        </td>
    </tr>
</script>

<script src="${pageContext.request.contextPath}/js/admin/faculty/faculty_list.js"></script>
