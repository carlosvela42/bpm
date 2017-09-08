<!-- The Modal Popup form -->
<div id="myModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span id="closefrom">X</span>
        <div id="type1">
        <#include "imageChangePopup.ftl">
        </div>
        <div id="type2">
        <#include "imageSearchingCoditionPopup.ftl">
        </div>
        <div id="type3">
        <#include "imageDisplayZoomPopup.ftl">
        </div>
    </div>
</div>

<div id="imageDisplayRight" class="board_template board-half" style="vertical-align:top;">
    <!-- Menu  -->
    <div id="MenuRoot" style="width: 100%; height:350px; padding: 10px 10px; border: 1px solid;">
        <span class="span_menu span_menu_title">${e.get('templateBusiness.receiptNumber')}:　12-34567890-1-23</span>
        <div class="createline"></div>
        <ul class="nav nav-tabs" style="height:40px; background-color: gray; border: 1px solid;">
            <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
            <li><a data-toggle="tab" href="#menu1">Menu 1</a></li>
            <li><a data-toggle="tab" href="#menu2">Menu 2</a></li>
            <li><a data-toggle="tab" href="#menu3">Menu 3</a></li>
        </ul>
        <div class="tab-content" style="height: 60%; border: 1px solid;">
            <div id="home" class="tab-pane fade in active" style="height: 98%; width: 100%;">
                <div style="padding:4px; height:100%;  width: 100%;">
                    <table class="clientSearch table_list fixed_headers" style="margin-top: 3px;">
                        <thead>
                        <tr>
                            <td class="text_center"><span class="span_label">Created Date</span></td>
                            <td class="text_center filterable-cell" style="width: 100%;"><span class="span_label">Images</span>
                            </td>
                            <td class="text_center"><span class="span_label">Page Numbe</span>r</td>
                        </tr>
                        </thead>
                        <tbody style="height: 150px;">
                        <#if templateCollection??>
                            <#assign itemCount = 0>
                            <#list templateCollection.templateBusinessDataEntityList as template>
                            <tr>
                                <td class="text_center">
                                ${template.createdDate}
                                </td>
                                <td class="filterable-cell">
                                    <div class="scrollmenu">
                                        <#if template.listItem??>
                                            <#list template.listItem as imageItem>
                                                <div id="thumbId${itemCount}" class="imgThumbnail">
                                                    <img onclick="imageSelected( ${itemCount})"
                                                         src="${rc.getContextPath()
                                                         }/resources/images/${imageItem}"
                                                         width="100%"
                                                         height="100%">
                                                </div>
                                                <#assign itemCount++>
                                            </#list>
                                        </#if>
                                    </div>
                                </td>
                                <td class="text_center">${template.pageNumber}</td>
                            </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="menu1" class="tab-pane fade">
                <!--  <h3>Menu 1</h3>
                 <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p> -->
            </div>
            <div id="menu2" class="tab-pane fade">
                <!--  <h3>Menu 2</h3>
                 <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p> -->
            </div>
            <div id="menu3" class="tab-pane fade">
                <!--  <h3>Menu 3</h3>
                 <p>Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p> -->
            </div>
        </div>

        <!--   BUtton   -->
        <table style="width: 100%; margin-top: 5px;">
            <tbody>
            <tr style="height: 30px; border-bottom:none;">
                <td class="text">
                    <button align="left" class="btn btn-default btn_cancel_template" id="popType3">${e.get
                    ('templateBusiness.refineSearch')}</button>
                </td>
                <td class="text" align="right">
                    <button align="center" class="btn btn-default btn_save_template" id="popType1">${e.get
                    ('templateBusiness.imageChange')}</button>
                    <button align="center" class="btn btn-default btn_cancel_template" id="popType2"
                            data-target="#addFieldModal">${e.get('templateBusiness.upload')}</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <!-- End menu -->
    <div class="clear_fix" style="height: 10px"></div>

    <div style="width: 100%; z-index: 0; padding: 10px 10px; border: 1px solid;">
        <span class="span_menu span_menu_title">${e.get('templateBusiness.formTypeNo')}</span>
        <div class="createline"></div>
        <table class="table_form">
            <tbody>
            <tr style="height: 30px; border-bottom:none;">
                <td class="text">
                    <span style="white-space: nowrap;" class="span_menu">${e.get('templateBusiness.formTypeNo')}：</span>
                </td>
                <td class="text">
                             <span>
                               <input type="text" id="txtNo" style="width: 45px; background-color: white; "
                                      autocomplete="off"
                                      value="${documentInfo???then(documentInfo.templateBusinessDataEntityList[0].formTypeCode!"","-1")}">
                            </span>
                </td>
                <td class="text">
                            <span>
                                <input type="text" disabled="disabled" runat="server" id="txtVal" style="-moz-border-radius: 7px; -webkit-border-radius: 7px; border-radius:
                                       7px; width: 316px; margin-left: 5px;" value="${documentInfo???then
                                (documentInfo.templateBusinessDataEntityList[0].formTypeName!"","")}"">
                            </span>
                </td>
            </tr>
            </tbody>
        </table>

        <div style="margin-top: 5px; text-align: right;">
            <button align="center" colspan="2" class="btn btn-default btn_cancel_template" >${e.get('templateBusiness.updateFormType')}
            </button>
            <button align="center" colspan="2" class="btn btn-default btn_cancel_template" >${e.get('templateBusiness.thumbnailRereading')}
            </button>
        </div>
    </div>

    <div class="clear_fix" style="height: 10px"></div>

    <div style="border: 1px solid; width: 100%; margin: auto; z-index: 0;
            background-color: white; padding: 10px;">
        <span class="span_menu span_menu_title">${e.get('templateBusiness.caseCommonInformation')}：</span>
        <div class="createline"></div>
        <table>
            <tbody>
            <tr style="height: 30px; border-bottom:none;">
                <td class="text">
                    <table id="tblWpData" style="width: 100%; padding-top: 5px;">
                        <tbody>
                        <tr style="text-align: left; height: 30px; border-bottom:none;">
                            <td class="text">
                                <span class="span_menu">${e.get('templateBusiness.receiptNumber')}：</span>
                            </td>
                            <td class="text">
                                <input id="txtReceiptNo" type="text" style="width: 260px;" value="${documentInfo???then
                                (documentInfo.templateBusinessDataEntityList[0].receiptNumber!"","")}">
                            </td>
                        </tr>
                        <tr style="text-align: left; height: 30px; border-bottom:none;">
                            <td class="text"><span class="span_menu">${e.get('templateBusiness.bookClass')}：</span></td>
                            <td class="text">
                                <input id="txtDocSenderClass" type="text" tabindex="3"
                                       style="width: 45px;" value="${documentInfo???then
                                (documentInfo.templateBusinessDataEntityList[0].documentType!"","")}">
                            </td>
                        </tr>
                        <tr style="text-align: left; height: 30px; border-bottom:none;">
                            <td class="text"><span class="span_menu">${e.get('templateBusiness.numberOfPhysicalDocuments')}:</span></td>
                            <td class="text">
                                <input id="txtActualDocNo" type="text" style="width: 45px;" value="${documentInfo???then
                                (documentInfo.templateBusinessDataEntityList[0].documentNumber!"","")}">
                            </td>
                        </tr>
                        <tr style="text-align: left; height: 30px; border-bottom:none;">
                            <td class="text"><span class="span_menu">${e.get('templateBusiness.personChargeCaseCode')}：</span></td>
                            <td class="text">
                                <input id="txtPIC" type="text" style="width: 260px;" value="${documentInfo???then
                                (documentInfo.templateBusinessDataEntityList[0].userChangeCode!"","")}">
                            </td>
                        </tr>
                        <tr style="height: 30px; border-bottom:none;">
                            <td class="text"><span class="span_menu" style="vertical-align: 20px;">${e.get
                            ('templateBusiness.toSendTheTransactionMatters')}：</span></td>
                            <td class="text">
                                <input type="text"  style="width:100%; height:40px; "
                                       value="${documentInfo???then
                                       (documentInfo.templateBusinessDataEntityList[0].applicationMatter!"","")}">
                            </td>
                        </tr>
                        <tr style="text-align: left; height: 30px; border-bottom:none;">
                            <td class="text"><span class="span_menu" style="vertical-align: 20px;">${e.get('templateBusiness.status')}:</span></td>
                            <td colspan="2" style="border-width: 0px; line-height: 20px;">
                                <select style="width: 200px;">
                                    <#if comboxStatus??>
                                        <#list comboxStatus.templateBusinessDataEntityList as item>
                                            <option value="${item.statusId}">${item.statusName}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</div>

<script>
    // Get the modal
    var modal = $(this).attr("myModal");
    /* document.getElementById('myModal'); */

    var showType1 = $(this).attr("type1");
    var showType2 = $(this).attr("type2");
    var showType3 = $(this).attr("type3");

    // Get the button that opens the modal
    var btn = $(this).attr("popType1");//document.getElementById("myBtn");
    var btn2 = $(this).attr("popType2");
    var btn3 = $(this).attr("popType3");

    // Get the <span> element that closes the modal
    var close = $("#closefrom");//$(this).attr("closefrom");// document
    // .getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btn.onclick = function () {
        modal.style.display = "block";
        showType1.style.display = "block";
        showType2.style.display = "none";
        showType3.style.display = "none";
    }

    btn2.onclick = function () {
        modal.style.display = "block";
        showType2.style.display = "block";
        showType1.style.display = "none";
        showType3.style.display = "none";
    }

    btn3.onclick = function () {
        modal.style.display = "block";
        showType3.style.display = "block";
        showType1.style.display = "none";
        showType2.style.display = "none";
    }

    // When the user clicks on <span> (x), close the modal
    close.click(function () {
        modal.style.display = "none";
        showType1.style.display = "none";
        showType2.style.display = "none";
        showType3.style.display = "none";
    });
    function closed() {
        modal.style.display = "none";
        showType1.style.display = "none";
        showType2.style.display = "none";
        showType3.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
            showType1.style.display = "none";
            showType2.style.display = "none";
            showType3.style.display = "none";
        }
    }

    // Select imgThumbnail
    function imageSelected(index) {
        $(".imgThumbnail").each(function () {
            $(this).css("background-color", "white");
        })

        $("#thumbId" + index).css("background-color", "blue");

        // Display image left panel
        $("#imgViewer").attr("src", $("#thumbId" + index).find("img").attr("src"));
    }

    // Get dataform
</script>
