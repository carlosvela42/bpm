<#assign searchForm>
    <@component.searchColumnFormPanel object="" formId=""></@component.searchColumnFormPanel>
</#assign>

<#assign addSearchPopupFrom>
    <@component.searchFormPopup></@component.searchFormPopup>
</#assign>

<#assign addFrom>
    <@component.workItemSearch></@component.workItemSearch>
</#assign>
<#assign addPopupFrom>
    <@component.searchPopup></@component.searchPopup>
</#assign>
<#assign addSearchFrom>
    <@component.searchColumnsForm></@component.searchColumnsForm>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/evidentLog.js"></script>
</#assign>
<@standard.standardPage title=e.get('evidenceLog.log') displayWorkspace=true script=script imageLink="report">
<script id="evidenceLogRow" type="text/x-handlebars-template">
    {{#each strLogAccesses}}
    <tr>
        <td class="number">{{this.eventId}}</td>
        <td class="text">{{this.processTime}}</td>
        <td class="text">{{this.userId}}</td>
        <td class="number">{{this.workitemId}}</td>
        <td class="number">{{format this.historyNo}}</td>
        <td class="number">{{this.taskId}}</td>
    </tr>
    {{/each}}
</script>
    <#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
    <form method="get" id="filter" action="">
        <input type="hidden" name="workspaceId" value="${workspaceId}">
    </form>

    ${addSearchPopupFrom!""}
    ${searchForm!""}
    <div class="clearfix" style="height: 15px;"></div>
    <table class="clientSearch table_list">
        <colgroup>
            <col style="width: 16%" />
            <col style="width: 18%" />
            <col style="width: 20%" />
            <col style="width: 17%" />
            <col style="width: 12%" />
            <#--<col style="width: 10%" />-->
            <#--<col style="width: 14%" />-->
            <col style="width: 15%" />
        </colgroup>
        <thead>
        <tr class="table_header rowSearch" style="white-space: nowrap;">
            <td>
                <button type="button" id="btnFiltereventId"
                        onclick="openSearchCondition(this, 0,'NCHAR','eventId','${e.get('evidenceLog.eventId')}','false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('evidenceLog.eventId')}</div>
            </td>
            <td>
                 <button type="button" id="btnFilterprocessTime"
                         onclick="openSearchCondition(this, 1,'NCHAR','processTime','${e.get('evidenceLog.processTime')}','false','true');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('evidenceLog.processTime')}</div>
            </td>
            <td>
                 <button type="button" id="btnFilteruserId"
                         onclick="openSearchCondition(this, 2,'NCHAR','userId','${e.get('user.id')}','false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('user.id')}</div>
            </td>
            <td>
                 <button type="button" id="btnFilterworkitemId"
                         onclick="openSearchCondition(this, 3,'NCHAR','workitemId','${e.get('evidenceLog.workitemId')}','false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('evidenceLog.workitemId')}</div>
            </td>
            <td>
                 <button type="button" id="btnFilterhistoryNo"
                         onclick="openSearchCondition(this, 4,'NUMBER','historyNo','${e.get('evidenceLog.historyNo')}',
                                 'false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('evidenceLog.historyNo')}</div>
            </td>
            <#--<td>-->
                 <#--<button type="button"-->
                         <#--onclick="openSearchCondition(this, 5,'NCHAR','processId','${e.get('evidenceLog.processId')}','false','false');"-->
                        <#--class="icon btn_filter btn_left"-->
                        <#--data-target="#addFormSearchColumn"></button>-->
                <#--<div class="textLeft">${e.get('evidenceLog.processId')}</div>-->
            <#--</td>-->
            <#--<td>-->
                 <#--<button type="button"-->
                         <#--onclick="openSearchCondition(this, 6,'NCHAR','processVersion','${e.get('evidenceLog.processVersion')}','false','false');"-->
                        <#--class="icon btn_filter btn_left"-->
                        <#--data-target="#addFormSearchColumn"></button>-->
                <#--<div class="textLeft">${e.get('evidenceLog.processVersion')}</div>-->
            <#--</td>-->
            <td>
                 <button type="button" id="btnFiltertaskId"
                         onclick="openSearchCondition(this, 7,'NCHAR','taskId','${e.get('evidenceLog.taskId')}','false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('evidenceLog.taskId')}</div>
            </td>
        </tr>
        </thead>
        <tbody id="evidentTBody" class="table_body">
            <#if strLogAccesses??>
                <#list strLogAccesses as strLogAccess>
                <tr>
                    <td class="number">${strLogAccess.eventId!""}</td>
                    <td class="text">${strLogAccess.processTime!""}</td>
                    <td class="text">${strLogAccess.userId!""}</td>
                    <td class="number">${strLogAccess.workitemId!""}</td>
                    <td class="number">${strLogAccess.historyNo!""}</td>
                    <#--<td class="number">${strLogAccess.processId!""}</td>-->
                    <#--<td class="number">${strLogAccess.processVersion!""}</td>-->
                    <td class="number">${strLogAccess.taskId!""}</td>
                </tr>
                </#list>
            <#else>
                <#include "../common/noResult.ftl">
            </#if>
        </tbody>
    </table>
</div>
${addPopupFrom!""}
${addSearchFrom!""}
${addFrom!""}
</@standard.standardPage>