<#assign contentFooter>
    <@component.detailUpdatePanel object="user" formId="userForm"></@component.detailUpdatePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/user.js"></script>
</#assign>

<@standard.standardPage title=e.get("user.edit") imageLink="user" contentFooter=contentFooter script=script>
<br>
    <#assign isPersisted = (user.lastUpdateTime??)>
    <#assign formAction = isPersisted?then('updateOne', 'insertOne')>
<form id="userForm" action="${rc.getContextPath()}/user/${formAction}" object="userForm" method="post" class="">
    <#include "../common/messages.ftl">
    <div class="board-wrapper">
        <div class="board board-half height-full">
            <table class="table_form">
                <tr>
                    <td width="40%">${e.get('user.id')}</td>
                    <td style="word-break: break-all">
                        <#if isPersisted>
                            <label>${user.userId!""}</label>
                            <input type="hidden" name="userId" value="${user.userId!""}">
                        <#else>
                            <input type="text" name="userId" value="${(user.userId!"")?html}">
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td width="40%">${e.get('user.name')}</td>
                    <td>
                        <input type="text" id="txtName" name="name" height="20px" width="150px" style="text-align: left"
                               value="${(user.name!"")?html}">
                    </td>
                </tr>
                <tr>
                    <td width="40%">${e.get('user.changePassword')}</td>
                    <td>
                    <#assign checked = (user.changePassword?then("checked",""))>
                    <input type="checkbox" name="changePassword" ${checked}>
                    </td>
                </tr>
                <tr>
                    <td width="40%">${e.get('user.password')}</td>
                    <td>
                        <input type="password" name="password" disabled="disabled"">
                    </td>
                </tr>
                <tr>
                    <td width="40%">${e.get('user.confirmPassword')}</td>
                    <td>
                        <input type="password" name="confirmPassword" disabled="disabled">
                    </td>
                </tr>
            </table>
            <div><input type="hidden" name="lastUpdateTime" value="${user.lastUpdateTime!""}"/></div>
        </div>
        <div class="board-split"></div>
        <div class="board board-half" style="padding-top: 30px; vertical-align: top;">
        <div class="font-bold">${e.get('profile')}</div>
        <br>
            <table class="clientSearch table_list">
                <thead>
                <tr class="table_header">
                    <td width="50%">${e.get('profile.id')}</td>
                    <td width="50%">${e.get('profile.description')}</td>
                </tr>
                <tr class="condition">
                    <td width="50%"><input type="text" col="1" placeholder="search" class="searchInput" ></td>
                    <td  width="50%"><input type="text" col="2" placeholder="search" class="searchInput" ></td>
                </tr>
                </thead>
                <tbody id="userTbody" class="table_body">
                    <#if mgrProfiles??>
                        <#list mgrProfiles as mgrProfile>
                        <tr profileId="${mgrProfile.profileId}">
                            <td class="text">${mgrProfile.profileId!""}</td>
                            <td class="text">${(mgrProfile.description!"")?html}</td>
                        </tr>
                        </#list>
                    </#if>
                </tbody>
            </table>
        </div>
    </div>
</form>
</@standard.standardPage>