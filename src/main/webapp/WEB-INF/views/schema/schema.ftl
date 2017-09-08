<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WorkSpace</title>
</head>
<body>
<h1>CREATE WORKSPACE</h1>

<form action="${rc.getContextPath()}/createWorkspace" object="workspace" method="get">
    <table>
        <tr>
            <td><input type="text" value="Workspace Name" disabled="disabled"/></td>
            <td><input type="text" name="workspaceName" value="TEMPLATE"/></td>
        </tr>
        <tr>
            <td>UserName: <input id="userName" type="text" name="userName"/></td>
            <td>PassWord: <input id="passWord" type="text" name="passWord"/></td>
            <td><input type="submit" value="Add" id="addRow"/></td>
        </tr>
    </table>
    <a id="displayWorkspace" href="${rc.getContextPath()}/loadListWorkspace">List Workspace<span class="fa arrow"></span></a>
</form>
</body>
</html>