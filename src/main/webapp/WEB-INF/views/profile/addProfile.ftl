<#assign contentFooter>
    <@component.detailUpdatePanel object="profile" formId="profileForm"></@component.detailUpdatePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/profile.js"></script>
</#assign>

<@standard.standardPage title=e.get("profile.edit") imageLink="user" contentFooter=contentFooter script=script>
<br>
    <#assign isPersisted = (mgrProfile.lastUpdateTime??)>
    <#assign formAction = isPersisted?then('updateOne', 'insertOne')>
    <#assign readonly = (formAction=='updateOne')?then('readonly',"")>

<form id="profileForm" action="${rc.getContextPath()}/profile/${formAction}" object="profileForm" method="post"
      class="">

    <#include "../common/messages.ftl">
    <div class="board-wrapper">
        <div class="board board-half">
            <table class="table_form">
                <tr>
                    <td width="50%">${e.get('profile.id')}</td>
                    <td>
                        <input type="text" name="profileId" value="${(mgrProfile.profileId!"")?html}" ${readonly!""}>
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('profile.description')}</td>
                    <td>
                        <input type="text" name="description" value="${(mgrProfile.description!"")?html}">
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('profile.ldapIdentifier')}</td>
                    <td>
                        <input type="text" name="ldapIdentifier" value="${(mgrProfile.ldapIdentifier!"")?html}">
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('user.list')}</td>
                    <td>

                    </td>
                </tr>
            </table>
            <div><input type="hidden" name="lastUpdateTime" value="${mgrProfile.lastUpdateTime!""}"/></div>
            <div class="profile-scroll">
                <table class="clientSearch table_list">
                    <thead>
                    <tr class="table_header profile-border">
                        <td class=""><input type="checkbox" class="deleteAllCheckBox"/></td>
                        <td class="text profile-width">${e.get('user.id')}</td>
                        <td class="text">${e.get('user.description')}</td>
                    </tr>
                    </thead>
                    <tbody id="userTbody" class="table_body">
                        <#if usersProfiles??>
                            <#list usersProfiles as usersProfile>
                            <tr userId="${usersProfile.userId}">
                                <td>
                                    <input type="checkbox" class="deleteCheckBox"
                                           name="usersProfiles[${usersProfile?index}].userIdChoose"
                                        ${usersProfile.userIdChoose???string("checked","")}
                                           value="${usersProfile.userIdChoose!""}"/>
                                </td>
                                <td class="text">
                                ${usersProfile.userId!""}
                                    <input type="hidden" name="usersProfiles[${usersProfile?index}].userId"
                                           value="${usersProfile.userId!""}">
                                </td>
                                <td class="text">
                                ${(usersProfile.name!"")?html}
                                    <input type="hidden" name="usersProfiles[${usersProfile?index}].name"
                                           value="${(usersProfile.name!"")?html}">

                                </td>
                            </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="board-split"></div>

    </div>
    <#--<input type="hidden" id="userIds" name="userIds" value="${strUserId!""}">-->
</form>
</@standard.standardPage>