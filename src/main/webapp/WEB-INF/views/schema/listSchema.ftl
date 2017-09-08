<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>List Schema</title>
</head>
<body>
<h1>CREATE WORKSPACE</h1>

<form object="workspaces" method="get">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <table id="ws">
        <thead>
        <tr>
            <td>WorkspaceName</td>
            <td>UserName</td>
            <td>Pass</td>
        </tr>
        </thead>
        <tbody>
        <#if workspaces?? >
          <#list workspaces as workspace >
          <tr>
              <td><input type="text" value="${workspace.workspaceName}"/></td>
              <td><input type="text" value="${workspace.userName}"/></td>
              <td><input type="text" value="${workspace.passWord}"/></td>
              <td><a href="${rc.getContextPath()}/viewSchema?workspaceName=${workspace.workspaceName}&userName=${workspace.userName}&passWord=${workspace.passWord}">Edit</a></td>
          </tr>
          </#list>
        </#if>
        </tbody>
    </table>
    <a id="displayWorkspace" href="${rc.getContextPath()}/displayWorkspace">Display Workspace<span class="fa arrow"></span></a>
</form>
</body>
</html>