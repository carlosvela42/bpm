<#assign contentFooter>
<@component.removePanel></@component.removePanel>
</#assign>

<#assign script>
  <script src="${rc.getContextPath()}/resources/js/process.js"></script>
</#assign>

<@standard.standardPage title=e.get('process.list') contentFooter=contentFooter displayWorkspace=true script=script imageLink="process">
<#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
    <form method="get" id="filter" action="">
        <input type="hidden" id="workspaceId" name="workspaceId" value="${workspaceId}">
    </form>
    <form action="${rc.getContextPath()}/process/" id="searchClientForm" object="searchClientForm" method="post">
    <table class="clientSearch table_list">
        <colgroup>
            <col style="width: 3%" />
            <col style="width: 3%" />
            <col style="width: 10%" />
            <col style="width: 20%" />
            <col style="width: 15%" />
            <col style="width: 25%" />
            <col style="width: 24%" />
        </colgroup>
        <thead>
        <tr class="table_header">
            <td class=""><input type="checkbox" class="deleteAllCheckBox" /></td>
            <td class="text_center">
                <a id="addButton" class="icon icon_add" href="${rc.getContextPath()}/process/addNew">
                </a>
            </td>
            <td>${e.get('process.id')}</td>
            <td>${e.get('process.name')}</td>
            <td>${e.get('process.version')}</td>
            <td>${e.get('process.description')}</td>
            <td>${e.get('process.savingType')}</td>
        </tr>
        <tr class="condition">
            <td colspan="3"><input id="searchColumns[0]" name="searchColumns[0]" value="${(searchClientForm.searchColumns[0]!"")?html}"
                    type="text" col="3" placeholder="search"  class="searchInput" /></td>
            <td><input id="searchColumns[1]" name="searchColumns[1]" value="${(searchClientForm.searchColumns[1]!"")?html}"
                    type="text" col="4" placeholder="search" class="searchInput" /></td>
            <td><input id="searchColumns[2]" name="searchColumns[2]" value="${(searchClientForm.searchColumns[2]!"")?html}"
                    type="text" col="5" placeholder="search" class="searchInput" /></td>
            <td><input id="searchColumns[3]" name="searchColumns[3]" value="${(searchClientForm.searchColumns[3]!"")?html}"
                    type="text" col="6" placeholder="search" class="searchInput" /></td>
            <td  style="border-left: transparent; border-right-style: solid; border-right-color: #B0AFB0;" ><input id="searchColumns[4]" name="searchColumns[4]" value="${(searchClientForm
            .searchColumns[4]!"")?html}"
                    type="text" col="7" placeholder="search" class="searchInput" /></td>
        </tr>
        </thead>
        <tbody id="processTbody" class="table_body">
          <#if processes??>
            <#list processes as process>
            <tr processId="${process.processId}">
                <td><input type="checkbox" class="deleteCheckBox" /></td>
                <td class="text_center"><a class="icon icon_edit" href="${rc.getContextPath()}/process/showDetail?processId=${process.processId}"></a></td>
                <td class="number">${process.processId}</td>
                <td class="text wrap"><#if process.processName??>${(process.processName)?html}</#if></td>
                <td class="number"><#if process.processVersion??>${process.processVersion?c}</#if></td>
                <td class="text wrap"><#if process.description??>${(process.description)?html}</#if></td>
                <td class="text">
                    <#if process.documentDataSavePath??>
                        ${documentSaveDataPaths[process.documentDataSavePath]!""}
                    </#if>
                </td>
            </tr>
            </#list>
          </#if>
        </tbody>
    </table>
    </form>
</div>
</@standard.standardPage>