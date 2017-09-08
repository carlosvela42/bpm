<span class="span_label">${e.get('templateBusiness.imageChange')}</span>
<div class="createline"></div>
<div class="div_round_border">
    <span class="span_label">${e.get('templateBusiness.formTypeNo')}</span>
    <div style="margin: 5px;">
        <span>${e.get('templateBusiness.selectAll')}</span>
        <span style="margin-left: 5px;">${e.get('templateBusiness.releaseAll')}</span>
    </div>
    <div style="height:130px; padding: 10px 10px; border: 1px solid; overflow:auto; display: block;">
        <div id="listcheckbox">
            <ul id="listcheckboxitem">
            <#if popupData??>
                <#list popupData.templateBusinessDataEntityList as item >
                    <#if item.listItem??>
                        <#list item.listItem as it>
                            <li>
                                <input type="checkbox" data_name="${it?index}" value="${it} "/>
                            </li>
                        </#list>
                    </#if>
                </#list>
            </#if>
            </ul>
        </div>
    </div>
</div>

<div>
    <span class="span_label">${e.get('templateBusiness.deliveryUnit')}</span>

    <table class="clientSearch table_list_template_business">
        <thead>
        <tr class="table_header">
            <td class=""><input type="checkbox" class="deleteAllCheckBox"/></td>
            <td style="width: 50px;">${e.get('templateBusiness.repeated')}</td>
            <td>${e.get('templateBusiness.caseManager')}</td>
            <td>${e.get('templateBusiness.scanDateAndTime')}</td>
            <td>Unknown</td>
            <td>${e.get('templateBusiness.status')}</td>
            <td>${e.get('templateBusiness.pageNumber')}</td>
        </tr>
        </thead>
        <tbody id="popupTbody" class="table_body">
        <#if popupTableData??>
            <#list popupTableData.templateBusinessDataEntityList as item>
            <tr selectedId="${item?index}">
                <td>
                    <input type="checkbox" class="deleteCheckBox"/>
                </td>
                <td class="text_center" width="50px;">${item?index}</td>
                <td class="text_center">${item.templateName}</td>
                <td class="text_center">${item.createdDate}</td>
                <td class="text_center">${item.unknowName}</td>
                <td class="text_center">${item.status}</td>
                <td class="text_center">${item.pageNumber}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<div class="div_round_border">
    <span class="span_label">${e.get('templateBusiness.annotation')}</span>
    <table style="width: 100%; height: 30px;">
        <tr id="checkItem">
            <td><input type="checkbox" value="${e.get('templateBusiness.there')}" class="deleteCheckBox"
                       data_name="1"/></td>
            <td><input type="checkbox" value="${e.get('templateBusiness.none')}" class="deleteCheckBox" data_name="2" checked="checked"/></td>
        </tr>
    </table>
</div>

<div style="margin-top: 5px; text-align: center;">
    <button class="btn btn-default btn_cancel_template">${e.get('templateBusiness.refineSearch')}</button>
    <button align="center" class="btn btn-default btn_save_template" id="btnSave">${e.get('templateBusiness.imageChange')}</button>
</div>

<script>
    $(function () {
        $("#btnSave").click(function () {

            var selectedImgs = [];
            var selectedTableIds = [];

            $('#listcheckboxitem > li').each(function () {
                if ($(this).find('input[type="checkbox"]').prop('checked')) {
//                    console.log($(this).find('input[type="checkbox"]').val());
                    selectedImgs.push($(this).find('input[type="checkbox"]').attr("data_name"));
                }
            });
            //console.log(selectedImgs);

            // Get selected tableId
            $('#popupTbody > tr').each(function () {
                if ($(this).find('.deleteCheckBox').prop('checked')) {
                    selectedTableIds.push($(this).attr('selectedId'));
                }
            });

            // Get checkbox status
            var checkItems = [];
            $('#checkItem > td').each(function () {
                if ($(this).find('.deleteCheckBox').prop('checked')) {
                    console.log($(this).find('input[type="checkbox"]').attr("data_name"));
                    checkItems.push($(this).find('input[type="checkbox"]').attr("data_name"));
                }
            });

            // Call to controller
            if (selectedImgs.length > 0 || selectedTableIds.length > 0 || checkItems.length > 0) {
                $.form(window.baseUrl + "/templateBusiness/insertOne", {
                    "tempId": selectedImgs == null ? "" : selectedImgs.toString(),
                    "selectedTableIds": selectedTableIds == null ? "" : selectedTableIds.toString(),
                    "checkedIds": checkItems == null ? "" : checkItems.toString()
                }).submit();
            } else {
               alert("You don't have choice!!!!!");
            }
            loaddata();
        })

        function loaddata() {
            $(this).attr("type2").style.display = "block";
        }

    })
</script>