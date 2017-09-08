<#assign script>
  <script src="${rc.getContextPath()}/resources/js/menuAuth.js"></script>
</#assign>
<@standard.standardPage title=e.get('mgrMenu.list') script=script imageLink="authority">
<#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
    <form action="${rc.getContextPath()}/site/" id="searchClientForm" object="searchClientForm" method="post">
    <table class="clientSearch table_list" id="listMenu">
        <colgroup>
            <col style="width: 3%" />
            <col style="width: 17%" />
            <col style="width: 30%" />
            <col style="width: 20%" />
            <col style="width: 30%" />
        </colgroup>
        <thead>
        <tr class="table_header">
            <td colspan="2">${e.get('mgrMenu.functionId')}</td>
            <td>${e.get('mgrMenu.functionName')}</td>
            <td>${e.get('mgrMenu.functionCategoryId')}</td>
            <td>${e.get('mgrMenu.functionCategoryName')}</td>
        </tr>
        <tr class="condition">
            <td colspan="2"><input type="text" col="2" placeholder="search " class="searchInput"
                   id="searchColumns[0]" name="searchColumns[0]" value="${searchClientForm.searchColumns[0]!""}"/></td>
            <td><input type="text" col="3" placeholder="search " class="searchInput"
                   id="searchColumns[1]" name="searchColumns[1]" value="${searchClientForm.searchColumns[1]!""}"/></td>
            <td><input type="text" col="4" placeholder="search " class="searchInput"
                   id="searchColumns[2]" name="searchColumns[2]" value="${searchClientForm.searchColumns[2]!""}"/></td>
            <td  style="border-left: transparent; border-right-style: solid; border-right-color: #B0AFB0;" ><input type="text" col="5" placeholder="search "  class="searchInput"
                   id="searchColumns[3]" name="searchColumns[3]" value="${searchClientForm.searchColumns[3]!""}"/></td>
        </tr>
        </thead>
        <tbody id="menuTbody" class="table_body">
            <#if mgrMenus??>
                <#list mgrMenus as mgrMenu>
                <tr functionId="${mgrMenu.functionId}">
                    <td class="text_center" style="width: 3%;">
                        <a class="icon icon_edit"
                           href="${rc.getContextPath()}/menuAccessRight/showDetail?functionId=${mgrMenu.functionId}">
                        </a>
                    </td>
                    <td class="number">${mgrMenu.functionId!""}</td>
                    <td class="text">${mgrMenu.functionName!""}</td>
                    <td class="number">${mgrMenu.functionCategoryId!""}</td>
                    <td class="text">${mgrMenu.functionCategoryName!""}</td>
                </tr>
                </#list>
            </#if>
        </tbody>
    </table>
    </form>
</div>
</@standard.standardPage>