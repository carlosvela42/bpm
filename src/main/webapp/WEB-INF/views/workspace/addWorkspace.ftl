<#assign contentFooter>
    <@component.detailUpdatePanel object="workspace" formId="workspaceForm"></@component.detailUpdatePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/workspace.js"></script>
</#assign>

<@standard.standardPage title=e.get("workspace.edit") contentFooter=contentFooter script=script imageLink="process">
<br>
    <#assign isPersisted = (workspaceForm.lastUpdateTime??)>
    <#assign formAction = isPersisted?then('updateOne', 'insertOne')>
<form id="workspaceForm" action="${rc.getContextPath()}/workspace/${formAction}" object="workspaceForm" method="post">
    <#include "../common/messages.ftl">
    <div class="board-wrapper">
        <div class="board board-half">
            <table class="table_form">
                <tr>
                    <td width="50%">
                    ${e.get('workspace.id')}
                    </td>
                    <td>
                        <#if isPersisted>
                            ${workspaceForm.workspaceId!""}
                            <input type="hidden" name="workspaceId"
                                   value="${workspaceForm.workspaceId!""}"/>
                        </#if>

                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('workspace.dbType')}</td>
                    <td>
                    ${workspaceForm.dbType!""}
                        <input type="hidden" name="dbType" value="${(workspaceForm.dbType!"")?html}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    ${e.get('workspace.workspaceName')}
                    </td>
                    <td>
                        <input type="text" name="workspaceName" value="${(workspaceForm.workspaceName!"")?html}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    ${e.get('workspace.schemaName')}
                    </td>
                    <td>
                        <input type="text" name="schemaName" value="${(workspaceForm.schemaName!"")?html}"/>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('workspace.dbUser')}</td>
                    <td><input type="text" name="dbUser" value="${(workspaceForm.dbUser!"")?html}"/></td>
                </tr>
                <tr>
                    <td>${e.get('user.changePassword')}</td>
                    <td>
                        <#assign checked = isPersisted?then("","checked")>
                        <input type="checkbox" name="changePassword" ${checked}>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('workspace.dbPassword')}</td>
                    <td>
                        <#if isPersisted>
                            <input type="password" name="dbPassword" value="**********"/>
                        <#else>
                            <input type="password" name="dbPassword" value=""/>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('workspace.owner')}</td>
                    <td>
                        <input type="text" name="owner" value="${(workspaceForm.owner!"")?html}"/>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('workspace.dbServer')}</td>
                    <td>
                        <input type="text" name="dbServer" value="${(workspaceForm.dbServer!"")?html}"/>
                    </td>
                </tr>
                <tr>
                    <input type="hidden" name="lastUpdateTime" value="${workspaceForm.lastUpdateTime!""}"/>
                </tr>
                <tr>
                </tr>
            </table>
        </div>
        <div class="board-split"></div>

    </div>
</form>
</@standard.standardPage>