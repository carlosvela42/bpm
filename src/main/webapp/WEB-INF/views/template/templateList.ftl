<#assign contentFooter>
    <@component.removePanel></@component.removePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/template.js"></script>
</#assign>

<@standard.standardPage title=e.get('template.title') contentFooter=contentFooter displayWorkspace=true script=script imageLink="data">
<#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
    <form method="get" id="filter" action="">
        <input type="hidden"  id="templateType" name="templateType" value="${templateType!""}">
        <input type="hidden"  id="workspaceId" name="workspaceId" value="${workspaceId}">
    </form>
    <form action="${rc.getContextPath()}/template/" id="searchClientForm" object="searchClientForm" method="post">
    <table class="clientSearch table_list">
        <colgroup>
            <col style="width: 3%" />
            <col style="width: 3%" />
            <col style="width: 15%" />
            <col style="width: 40%" />
            <col style="width: 39%" />
        </colgroup>
        <thead>
            <tr class="table_header">
                <td class=""><input type="checkbox" class="deleteAllCheckBox" /></td>
                <td class="text_center">
                    <a id="addButton" class="icon icon_add" href="${rc.getContextPath()}/template/addNew">
                    </a>
                </td>
                <td>${e.get('template.id')}</td>
                <td>${e.get('template.name')}</td>
                <td>${e.get('template.tableName')}</td>
            </tr>
            <tr class="condition">
                <td colspan="3"><input id="searchColumns[0]" name="searchColumns[0]" value="${(searchClientForm.searchColumns[0]!"")?html}"
                        type="text" col="3" placeholder="search" class="searchInput"/></td>
                <td><input id="searchColumns[1]" name="searchColumns[1]" value="${(searchClientForm.searchColumns[1]!"")?html}"
                        type="text" col="4" placeholder="search" class="searchInput"/></td>
                <td><input id="searchColumns[2]" name="searchColumns[2]" value="${(searchClientForm.searchColumns[2]!"")?html}"
                        type="text" col="5" placeholder="search" class="searchInput"/></td>
            </tr>
        </thead>
        <tbody id="templateTbody" class="table_body">
            <#if mgrTemplates??>
                <#list mgrTemplates as mgrTemplate>
                <tr templateId="${mgrTemplate.templateId}">
                    <td><input type="checkbox" class="deleteCheckBox" /></td>
                    <td class="text_center">
                        <a class="icon icon_edit" href="${rc.getContextPath()}/template/showDetail?templateIds=${mgrTemplate.templateId}">
                        </a>
                    </td>
                    <td class="text">${mgrTemplate.templateId!""}</td>
                    <td class="text">${(mgrTemplate.templateName!"")?html}</td>
                    <td class="text">${mgrTemplate.templateTableName!""}</td>
                </tr>
                </#list>
            </#if>
        </tbody>
    </table>
    </form>
</div>
</form>
</@standard.standardPage>
