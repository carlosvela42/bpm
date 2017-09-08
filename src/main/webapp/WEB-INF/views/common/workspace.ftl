<div id="workspaceSelection" class="btn-group clearfix pull-right workspaceSelectBox">
    <#if noWorkspace??>
        <#assign noWorkspaceClass="no-workspace">
        <#assign noWorkspaceStyle="disabled='disabled'">
    </#if>
    <button type="button" class="name btn btn-default ${noWorkspaceClass!''}" tabindex="-1" ${noWorkspaceStyle!''}>
        ${(workspaceName!"")?html}
    </button>
    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
            aria-expanded="false" tabindex="2" ${noWorkspaceStyle!''}>
        <span class="caret"></span>
        <span class="sr-only"></span>
    </button>
    <ul class="dropdown-menu">
        <#if !noWorkspace??>
            <#list workspaces as workspace>
              <#assign selected=(workspace.workspaceId == workspaceId)?then("selected","")>
                <li ${selected!""}><a data-value="${workspace.workspaceId}" data-name="${(workspace.workspaceName)?html}" href="#">
                  ${(workspace.workspaceName!"")?html}
                </a></li>
            </#list>
        </#if>
    </ul>
</div>