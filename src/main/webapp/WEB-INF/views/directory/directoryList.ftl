<#assign contentFooter>
<@component.removePanel></@component.removePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/directory.js"></script>
</#assign>

<@standard.standardPage title=e.get('directory.list') contentFooter=contentFooter script=script imageLink="process">
<#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
  <form action="${rc.getContextPath()}/directory/" id="searchClientForm" object="searchClientForm" method="post">
    <table class="clientSearch table_list">
        <colgroup>
            <col style="width: 3%" />
            <col style="width: 3%" />
            <col style="width: 13%" />
            <col style="width: 20%" />
            <col style="width: 15%" />
            <col style="width: 23%" />
            <col style="width: 23%" />
        </colgroup>
        <thead>
        <tr class="table_header">
            <td class=""><input type="checkbox" class="deleteAllCheckBox" /></td>
            <td class="text_center">
                <a id="addButton" class="icon icon_add" href="${rc.getContextPath()}/directory/addNew">
                </a>
            </td>
            <td>${e.get('directory.id')}</td>
            <td>${e.get('folder.path')}</td>
            <td>${e.get('create.new.file')}</td>
            <td>${e.get('secured.disk.space')}</td>
            <td>${e.get('disk.space')}</td>
        </tr>
        <tr class="condition">
            <td colspan="3"><input id="searchColumns[0]" name="searchColumns[0]" value="${(searchClientForm.searchColumns[0]!"")?html}"
                 col="3" placeholder="search" class="searchInput"/></td>
            <td><input id="searchColumns[1]" name="searchColumns[1]" value="${(searchClientForm.searchColumns[1]!"")?html}"
                 type="text" col="4" placeholder="search" class="searchInput"/></td>
            <td><input id="searchColumns[2]" name="searchColumns[2]" value="${(searchClientForm.searchColumns[2]!"")?html}"
                  type="text" col="5" placeholder="search" class="searchInput"/></td>
            <td><input id="searchColumns[3]" name="searchColumns[3]" value="${(searchClientForm.searchColumns[3]!"")?html}"
                  type="text" col="6" placeholder="search" class="searchInput"/></td>
            <td  style="border-left: transparent; border-right-style: solid; border-right-color: #B0AFB0;" >
                  <input id="searchColumns[4]" name="searchColumns[4]" value="${(searchClientForm.searchColumns[4]!"")?html}"
                    type="text" col="7" placeholder="search" class="searchInput"/></td>
        </tr>
        </thead>
        <tbody id="directoryTbody" class="table_body">
          <#if directorys??>
            <#list directorys as directory>
            <tr directoryId="${directory.dataDirectoryId}">
                <td><input type="checkbox" class="deleteCheckBox" /></td>
                <td class="text_center"><a class="icon icon_edit"
                                           href="${rc.getContextPath()}/directory/showDetail?dataDirectoryId=${directory.dataDirectoryId}"></a></td>
                <td class="number">${directory.dataDirectoryId}</td>
                <td class="text">${directory.folderPath!""}</td>
                <td class="text">
                	<#if directory.newCreateFile == 1>
                		Y
                	<#else>
                		N
                	</#if>
                </td>
                <td class="text">${directory.reservedDiskVolSize!""}</td>
                <td class="text">${directory.diskVolSize!""}</td>
            </tr>
            </#list>
          </#if>
        </tbody>
    </table>
    </form>
</div>
</@standard.standardPage>