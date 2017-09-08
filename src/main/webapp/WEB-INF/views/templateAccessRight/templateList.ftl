<#assign script>
  <script src="${rc.getContextPath()}/resources/js/templateAuth.js"></script>
</#assign>
<@standard.standardPage title=e.get('template.title') contentFooter=contentFooter displayWorkspace=true imageLink="authority" script=script>
<#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
    <form method="get" id="filter" action="">
        <input type="hidden" name="workspaceId" value="${workspaceId}">
    </form>
    <form action="${rc.getContextPath()}/templateAccessRight/" id="searchClientForm" object="searchClientForm" method="post">
    <table class="clientSearch table_list">
        <colgroup>
            <col style="width: 3%" />
            <col style="width: 17%" />
            <col style="width: 20%" />
            <col style="width: 30%" />
            <col style="width: 30%" />
        </colgroup>
        <thead>
            <tr class="table_header">
                <td colspan="2">${e.get('template.id')}</td>
                <td>${e.get('template.type')}</td>
                <td>${e.get('template.name')}</td>
                <td>${e.get('template.tableName')}</td>
            </tr>
            <tr class="condition">
                <td colspan="2"><input id="searchColumns[0]" name="searchColumns[0]"
                                                   value="${(searchClientForm
                .searchColumns[0]!"")?html}"
                        type="text" col="2" placeholder="search" class="searchInput"/></td>
                <td><input id="searchColumns[1]" name="searchColumns[1]" value="${(searchClientForm
                .searchColumns[1]!"")?html}"
                        type="text" col="3" placeholder="search" class="searchInput"/></td>
                <td><input id="searchColumns[2]" name="searchColumns[2]" value="${(searchClientForm
                .searchColumns[2]!"")?html}"
                        type="text" col="4" placeholder="search" class="searchInput"/></td>
                <td><input id="searchColumns[3]" name="searchColumns[3]" value="${(searchClientForm
                .searchColumns[3]!"")?html}" type="text" col="5" placeholder="search" class="searchInput"/></td>
            </tr>
        </thead>
        <tbody class="table_body">
            <#if mgrTemplates?? && workspaceId??> <#list mgrTemplates
            as mgrTemplate>
            <tr processId="${mgrTemplate.templateId}">
                <td class="text_center">
                    <a class="icon icon_edit"
                       href="${rc.getContextPath()}/templateAccessRight/showDetail?templateId=${mgrTemplate.templateId}&workspaceId=${workspaceId}"></a>
                </td>
                <td class="text">${mgrTemplate.templateId}</td>
                <td class="text">${mgrTemplate.templateType!""}</td>
                <td class="text">${(mgrTemplate.templateName!"")?html}</td>
                <td class="text">${mgrTemplate.templateTableName}</td>
            </tr>
            </#list> </#if>
        </tbody>
    </table>
    </form>
</div>
</@standard.standardPage>
