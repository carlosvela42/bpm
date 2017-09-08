<#assign addFrom>
    <@component.workItemSearch></@component.workItemSearch>
</#assign>
<#assign addPopupFrom>
    <@component.searchPopup tabIndex=200></@component.searchPopup>
</#assign>

<#assign addSearchPopupFrom>
    <@component.searchFormPopup></@component.searchFormPopup>
</#assign>

<#assign contentFooter>
    <@component.unLockPanel></@component.unLockPanel>
</#assign>

<#assign addSearchFrom>
    <@component.searchColumnsForm></@component.searchColumnsForm>
</#assign>

<#assign searchFormField>
    <@component.searchColumnFormPanel object="" formId=""></@component.searchColumnFormPanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/workItemList.js"></script>
</#assign>

<@standard.standardPage title=e.get('workItem.titleList') contentFooter=contentFooter displayWorkspace=true
script=script imageLink="tool">
    <#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border" style="height: 650px">
    <form method="get" id="filter" action="">
        <input type="hidden" id="workspaceId" name="workspaceId" value="${workspaceId}">
    </form>
<#--div searchForm-->
    <div class="modal fade searchPopup" id="addFormSearch" role="dialog">

        <div class="modal-dialog content_main">
            <div class="modal-content board-wrapper board-full" style="padding: 0px">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">X</button>
                    <span class="modal-title title_popup">
                    ${e.get('workItem.detailSearch')}
                    </span>
                </div>
                <#assign i=templateTypes?size>
                <#if i==0>
                    <div style="padding-left: 15px;">
                        <br>
                        <b style="color: red;">テンプレートタイプ情報は0件取得できる。</b>
                        <br>
                    </div>
                </#if>
                <div class="modal-body">
                    <div id="modal-messages"></div>
                    <table style="width: 100%;" id="searchTable">
                        <tr>
                            <td class="text" style="width: 20%;vertical-align: middle;font-weight: bold">${e.get('template.type')}</td>
                            <td class="text" style="width: 25%;vertical-align: middle;"><select id="templateType">
                                <#list templateTypes?keys as key>
                                    <#assign selected=searchByColumnsForm???then( searchByColumnsForm.templateType???then((key==searchByColumnsForm.templateType)?then("selected",""),""),"")>
                                    <option value="${key}" ${selected!""}>
                                        ${templateTypes[key]}
                                    </option>
                                </#list>
                            </select></td>
                            <td style="width: 5%;"></td>
                            <td class="text" style="width: 25%;vertical-align: middle;">${e.get('template.name')}</td>

                                <td class="text" style="width: 25%;vertical-align: middle;" id="selectTemplateName">
                                    <select id="templateId">

                                    <option selected="selected"></option>
                            <#if searchForms??>
                                    <#list searchForms as searchForm>
                                        <#assign templateIdSelected=searchByColumnsForm???then( searchByColumnsForm.templateId???then((searchForm.templateId==searchByColumnsForm.templateId)?then("selected",""),""),"")>

                                        <option
                                            value="${searchForm.templateId}"${templateIdSelected!""}>${searchForm.templateName}
                                        </option>
                                    </#list>
                            </#if>
                                </select></td>


                        </tr>
                        <tr>
                            <td colspan="5">
                            ${searchFormField}
                            </td>
                        </tr>
                    </table>


                    <br>
                    <form method="post" id="searchForm">
                        <div id="searchListDiv">
                            <table class="search">
                                <#if searchForm??> <#if searchForm.columns?exists>
                                    <tr>
                                        <#list searchForm.columns as column>
                                            <th><b>${column.name?upper_case}</b></th>
                                        </#list>
                                    </tr>
                                    <#if searchForm.rows?exists> <#list searchForm.rows as row>
                                        <tr>
                                            <#list row.columns as column>
                                                <td>${column.value}</td>
                                            </#list>
                                        </tr>
                                    </#list> </#if> </#if> </#if>
                            </table>
                        </div>
                    </form>
                    <form method="post" id="deleteListForm" object="deleteListForm">
                        <input type="hidden" id="workspaceId" name="workspaceId" value="${workspaceId}">
                        <input type="hidden" id="workItemId" name="listIds[0]" value="">
                    </form>
                </div>
                <div class="modal-footer modal-footer-popup" style="border: none">
                    <button type="button" class="btn btn-default btn_cancel_popup btn_popup" id="btnCancel">
                    ${e.get('button.cancel')}
                    </button>
                    <button type="button" class="btn btn-default btn_save_popup btn_popup" id="btnReflect">
                    ${e.get('button.reflect')}
                    </button>
                </div>
            </div>

        </div>
    </div>

    <br>

<#--Div search by column-->
    ${addSearchPopupFrom}
    <br>
    <button class="btn searchBtn icon_search" id="btnSearchForm" data-target="#addFormSearch" style="padding: 0px">
        <#--<img src="${rc.getContextPath()}/resources/images/search.png">-->
        <label style="width: 15px;"></label>
    ${e.get('button.searchAdv')}

    </button>
    <br>
    <div class="clearfix" style="height: 20px;"></div>
    <form method="post" id="workItemForm" object="workItems" action="${rc.getContextPath()}/workItem/">
        <table class="clientSearch table_list" id="directorysTable">
            <colgroup>
                <col style="width: 3%" />
                <col style="width: 3%" />
                <col style="width: 24%" />
                <col style="width: 25%" />
                <col style="width: 15%" />
                <col style="width: 15%" />
                <col style="width: 15%" />
            </colgroup>
            <thead>
            <tr class="table_header">
                <td rowspan="2"><input type="checkbox" class="deleteAllCheckBox"/></td>
                <td rowspan="2" colspan="2">${e.get('workItem.id')}</td>
                <td rowspan="2">${e.get('workItem.lockStatus')}</td>
                <td colspan="3">${e.get('workItem')}</td>
            </tr>
            <tr class="table_header">
                <td>${e.get('task.name')}</td>
                <td>${e.get('template.id')}</td>
                <td>${e.get('template.name')}</td>
            </tr>
            </thead>
            <tbody id="workItemTBody" class="table_body">
                <#if workItems??>
                    <#list workItems as workItem>
                    <tr eventId="${workItem.eventId!""}">
                        <td><input type="checkbox" class="deleteCheckBox"/></td>
                        <td class="text_center">
                            <a class="icon icon_edit" workItem="${workItem.workitemId!""}"
                               href="${rc.getContextPath()}/workItem/showDetail?workItemId=${workItem.workitemId!""}">
                            </a>
                        </td>
                        <td class="number">${workItem.workitemId!""}</td>
                        <td class="number">${workItem.statusLock!""}</td>
                        <td class="text">${workItem.taskName!""}</td>
                        <td class="text">${workItem.templateId!""}</td>
                        <td class="text">${workItem.templateName!""}</td>
                    </tr>
                    </#list>
                </#if>
            </tbody>
        </table>
        <br>
    </form>

${addPopupFrom}
${addSearchFrom}
${addFrom}
</div>

</@standard.standardPage>
