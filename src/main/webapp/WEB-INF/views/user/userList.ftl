<#assign contentFooter>
    <@component.removePanel></@component.removePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/user.js"></script>
</#assign>

<@standard.standardPage title=e.get('user.list') imageLink="user" contentFooter=contentFooter script=script>
<div>
    <#include "../common/messages.ftl"> </div>
<div class="board-wrapper">
    <div class="board board-half">

        <form action="${rc.getContextPath()}/user/" id="searchClientForm" object="searchClientForm" method="post">
            <div>

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
                            <a id="addButton" class="icon icon_add" href="${rc.getContextPath()}/user/addNew">
                            </a>
                        </td>
                        <td>${e.get('user.id')}</td>
                        <td>${e.get('user.name')}</td>
                    </tr>
                    <tr class="condition">
                        <td colspan="3"><input id="searchColumns[0]" name="searchColumns[0]"
                                               value="${(searchClientForm.searchColumns[0]!"")?html}"
                                               type="text" col="3" placeholder="search" class="searchInput"></td>
                        <td style="border-left: transparent; border-right-style: solid; border-right-color: #B0AFB0;">
                            <input id="searchColumns[1]" name="searchColumns[1]"
                                   value="${(searchClientForm.searchColumns[1]!"")?html}"
                                   type="text" col="4" placeholder="search" class="searchInput"></td>
                    </tr>
                    </thead>
                    <tbody id="userTbody" class="table_body" style="">
                        <#if mgrUsers??>
                            <#list mgrUsers as mgrUser>
                            <tr userId="${mgrUser.userId}">
                                <td><input type="checkbox" class="deleteCheckBox"/></td>
                                <td class="text_center text_icon"><a class="icon icon_edit editButton" href="${rc.getContextPath()}/user/showDetail?userId=${mgrUser.userId}"
                                                                     data-id="${mgrUser.userId}"></a>
                                </td>
                                <td class="text">${mgrUser.userId!""}</td>
                                <td class="text" style = "white-space:PRE">${(mgrUser.name!"")?html}</td>
                            </tr>
                            </#list>
                        <#else>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        </#if>
                    </tbody>
                </table>
            </div>
            <div>

            </div>
        </form>
    </div>
    <div class="board-split"></div>
    </div>

</@standard.standardPage>