<#assign contentFooter>
    <@component.removePanel></@component.removePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/workspace.js"></script>
</#assign>

<@standard.standardPage title=e.get('workspace.list') contentFooter=contentFooter script=script imageLink="process">
    <#include "../common/messages.ftl">
<div class="board-wrapper">
    <div class="board board-half">
        <form action="${rc.getContextPath()}/workspace/" id="searchClientForm" object="searchClientForm" method="post">
        <table class="clientSearch table_list">
            <colgroup>
                <col style="width: 5%" />
                <col style="width: 5%" />
                <col style="width: 45%" />
                <col style="width: 45%" />
            </colgroup>
            <thead>
                <tr class="table_header">
                    <td class=""><input type="checkbox" class="deleteAllCheckBox"/></td>
                    <td class="text_center">
                        <a id="addButton" class="icon icon_add" href="${rc.getContextPath()}/workspace/addNew">
                        </a>
                    </td>
                    <td>${e.get('workspace.id')}</td>
                    <td>${e.get('workspace.name')}</td>
                </tr>
                <tr class="condition">
                    <td colspan="3"><input id="searchColumns[0]" name="searchColumns[0]" value="${(searchClientForm.searchColumns[0]!"")?html}"
                         type="text" col="3" placeholder="search"  class="searchInput"/></td>
                    <td  style="border-left: transparent; border-right-style: solid; border-right-color: #B0AFB0;" ><input id="searchColumns[1]" name="searchColumns[1]" value="${(searchClientForm
                    .searchColumns[1]!"")?html}"
                         type="text" col="4" placeholder="search"  class="searchInput"/></td>
                </tr>
            </thead>
            <tbody id="scheduleTbody" class="table_body">
                <#if mgrWorkspaces??>
                    <#list mgrWorkspaces as mgrWorkspace>
                    <tr workspaceId="${mgrWorkspace.workspaceId}">
                        <td><input type="checkbox" class="deleteCheckBox" /></td>
                        <td class="text_center">
                            <a class="icon icon_edit"  href="${rc.getContextPath()}/workspace/showDetail?workspaceId=${mgrWorkspace.workspaceId}"></a>
                        </td>
                        <td class="text">${mgrWorkspace.workspaceId!""}</td>
                        <td class="text">${(mgrWorkspace.workspaceName!"")?html}</td>
                    </tr>
                    </#list>
                </#if>
            </tbody>
        </table>
        </form>
    </div>
    <div class="board-split"></div>
</div>
</@standard.standardPage>
