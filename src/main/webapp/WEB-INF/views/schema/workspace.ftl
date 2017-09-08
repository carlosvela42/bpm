<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>WorkSpace</title>
</head>
<body>
<form object="languages" method="get">
    <table id="ws">
        <thead>
        <tr>
            <td>WorkspaceName</td>
            <td>UserName</td>
        </tr>
        </thead>
        <tbody>
        <#if languages?? >
          <#list languages as language >
          <tr>
              <td><input type="text" value="${language.text}"/></td>
              <td><input type="text" value="${language.name}"/></td>
          </tr>
          </#list>
        </#if>
        </tbody>
    </table>
    <a id="displayWorkspace" href="${rc.getContextPath()}/loadListWorkspace">List Workspace<span class="fa arrow"></span></a>
</form>
</body>
</html>