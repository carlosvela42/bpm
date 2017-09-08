<div id="imageDisplayRight" class="board_template board-half" style="vertical-align:top;">
    <!-- Menu  -->
    <div id="MenuRoot" style=" width: 100%;  overflow: auto; height:150px; padding: 10px
    10px; border: 1px solid;">
        <div style="display: -webkit-box; width: 350px;">
        <#if templateCollection??>
            <#assign itemCount = 0>
            <#list templateCollection.templateBusinessDataEntityList as template>
                <#if template.listItem??>
                    <#list template.listItem as imageItem>
                        <div id="thumbId${itemCount}" class="imgThumbnail">
                            <img style="margin: 5px;" onclick="imageSelected( ${itemCount})"
                                 src="${rc.getContextPath()
                                 }/resources/images/${imageItem}"
                                 width="76px"
                                 height="100px">
                        </div>
                        <#assign itemCount++>
                    </#list>
                </#if>

            </#list>
        </#if>
        </div>
    </div>
    <!-- End menu -->
    <div class="clear_fix" style="height: 10px"></div>

    <div style="width: 100%; z-index: 0; padding: 10px 10px; border: 1px solid;">
        <span class="span_menu span_menu_title">${e.get('templateBusiness.formTypeNo')}</span>
        <div class="createline"></div>
        <table class="table_form">
            <tbody>
            <tr style="border-bottom:none;">
                <td class="text">
                    <span style="white-space: nowrap;" class="span_menu">${e.get('templateBusiness.formTypeNo')}：</span>
                </td>
                <td class="text">
                    <input type="text" id="txtNo" style="width: 45px; background-color: white; "
                           autocomplete="off"
                           value="${documentInfo???then(documentInfo.templateBusinessDataEntityList[0].formTypeCode!"","-1")}">
                </td>
                <td class="text">
                    <input type="text" disabled="disabled" id="txtVal" class="text_round_border"
                           value="${documentInfo???then
                           (documentInfo.templateBusinessDataEntityList[0].formTypeName!"","")}"">
                </td>
            </tr>
            </tbody>
        </table>
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
                            <td class="text"><span
                                class="span_menu">${e.get('templateBusiness.numberOfPhysicalDocuments')}:</span></td>
                            <td class="text">
                                <input id="txtActualDocNo" type="text" style="width: 45px;" value="${documentInfo???then
                                (documentInfo.templateBusinessDataEntityList[0].documentNumber!"","")}">
                            </td>
                        </tr>
                        <tr style="text-align: left; height: 30px; border-bottom:none;">
                            <td class="text"><span class="span_menu">${e.get('templateBusiness.personChargeCaseCode')}
                                ：</span></td>
                            <td class="text">
                                <input id="txtPIC" type="text" style="width: 260px;" value="${documentInfo???then
                                (documentInfo.templateBusinessDataEntityList[0].userChangeCode!"","")}">
                            </td>
                        </tr>
                        <tr style="height: 30px; border-bottom:none;">
                            <td class="text"><span
                                class="span_menu">${e.get('templateBusiness.toSendTheTransactionMatters')}:</span></td>
                            <td class="text">
                                <input type="text" style="width:100%; "
                                       value="${documentInfo???then
                                       (documentInfo.templateBusinessDataEntityList[0].applicationMatter!"","")}">
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
    // Select imgThumbnail
    function imageSelected(index) {
        $(".imgThumbnail").each(function () {
            $(this).css("background-color", "white");
        })

        $("#thumbId" + index).css("background-color", "blue");

        // Display image left panel
        $("#imgViewer").attr("src", $("#thumbId" + index).find("img").attr("src"));
    }
</script>