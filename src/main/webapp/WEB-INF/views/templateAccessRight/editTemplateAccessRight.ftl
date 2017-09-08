<#assign contentFooter>
    <@component.detailUpdatePanel object="templateAccessRight" formId="templateAuthorityForm"></@component.detailUpdatePanel>
</#assign>
<#assign script>
<script src="${rc.getContextPath()}/resources/js/templateAuth.js"></script>
</#assign>
<@standard.standardPage title=e.get("template.authority.setting") contentFooter=contentFooter script=script imageLink="authority">

<form method="post" action="${rc.getContextPath()}/templateAccessRight/updateOne" id="templateAuthorityForm"
      object="templateAuthorityForm" >
    <#include "../common/messages.ftl">
    <div class="board-wrapper">
        <div class="board board-half" style="height:690px;vertical-align: top">
            <table class="table_form">
                <tr>
                    <td width="50%">${e.get('template.id')}</td>
                    <td>
                        <input type="hidden" name="templateId"
                               value="${templateAuthorityForm.templateId!""}" readonly>
                        ${templateAuthorityForm.templateId!""}
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('template.name')}</td>
                    <td>
                        <input type="hidden" name="templateName"
                               value="${templateAuthorityForm.templateName!""}" readonly>
                        ${templateAuthorityForm.templateName!""}
                    </td>
                </tr>
            </table>
        </div>
        <div class="board-split"></div>
        <div class="board board-half" style="padding: 0px">
            <div id="tabs">

                    <div class="panel-heading" style="padding: 0px">
                        <ul class="nav nav-tabs">
                            <li class="active" style="width: 47%; text-align: center; margin-top: 5px; margin-left: 2%;" id="tab1" >
                                <a data-toggle="tab" href="#tabs-user" tabindex="1"
                                   style="border-top-left-radius: 10px;border-top-right-radius: 10px;">${e
                                .get
                                ('access.user')}</a>
                            </li>
                            <li style="width: 47%; text-align: center; margin-top: 5px; margin-left: 2%;" id="tab2">
                                <a data-toggle="tab" href="#tabs-profile" tabindex="2" style="border-top-left-radius:
                                 10px;border-top-right-radius: 10px">${e.get('access.profile')}</a>
                            </li>
                        </ul>
                    </div>
                    <div class="panel-body">
                        <div class="tab-content">
                            <div id="tabs-user" class="tab-pane fade in active">
                                <button type="button" class="btn btn_remove" id="deleteButtonPopup"
                                        onclick="return proxyDelRow('user');">
                                    <@spring.message code='button.delete'/></button>
                                <div style="height: 10px;"></div>
                                <div style="overflow-y: auto;height: 570px">
                                <table class="clientSearch table_list userAccessRightTable">
                                    <thead>
                                    <tr class="table_header">
                                        <td style="width:5%"><input type="checkbox" name="userRightTop"
                                                                    class="deleteAllCheckBox"
                                                                    tabindex="4"></td>
                                        <td class="text_center" style="width:6%">

                                        <button type="button" class="icon btn_add" id="addUser"
                                                    data-target="#addFormuser"></button>
                                        </td>
                                        <td style="width: 40%">${e.get('user.id')}</td>
                                        <td>${e.get('accessRight.name')}</td>
                                    </tr>
                                    <tr class="condition">
                                        <td colspan="3"><input type="text" col="3" placeholder="search" class="searchInput">
                                        </td>
                                        <td><input type="text" col="4" placeholder="search" class="searchInput"></td>
                                    </tr>
                                    </thead>
                                    <#assign index=0>
                                    <#assign index=(templateAuthorityForm.userAccessRights??)?then(templateAuthorityForm.userAccessRights?size,0)>
                                    <tbody id="userTbody" class="table_body" index="${index}">
                                        <#if templateAuthorityForm.userAccessRights??>
                                            <#list templateAuthorityForm.userAccessRights as userAccessRight>
                                            <tr userId="${userAccessRight.userId}" index="${userAccessRight?index}">
                                                <td><input type="checkbox" class="deleteCheckBox"/></td>
                                                <td class="text_center">
                                                     <a class="icon icon_edit"
                                                           onclick="editRow('user','${userAccessRight.userId!""}',
                                                                   '${userAccessRight.accessRightValue!""}','1');">
                                                     </a>
                                                </td>
                                                <td class="text">
                                                    <span> ${userAccessRight.userId!""}</span>
                                                        <input type="hidden"
                                                               name="userAccessRights[${userAccessRight?index}].userId"
                                                               value="${userAccessRight.userId!""}"
                                                        />
                                                </td>
                                                <td class="text" >
                                                    <#if userAccessRight.accessRightValue??>
                                                        <#assign value=userAccessRight.accessRightValue>
                                                    </#if>
                                                    <span> ${value???then(accessRights[value?c]!"","")}</span>
                                                        <input type="hidden"
                                                               name="userAccessRights[${userAccessRight?index}].accessRightValue"
                                                               value="${value!""}"
                                                        />
                                                </td>
                                            </tr>
                                            </#list>
                                        </#if>
                                    </tbody>
                                </table>
                            </div>
                            </div>
                            <div id="tabs-profile" class="tab-pane fade">
                                <button type="button" class="btn btn_remove"
                                        id="deleteButtonPopup"  onclick="return proxyDelRow('profile');">
                                    <@spring.message code='button.delete'/></button>
                                <div style="height: 10px;"></div>
                                <table class="clientSearch table_list profileAccessRightTable">
                                    <thead>
                                    <tr class="table_header">

                                        <td style="width:5%"><input type="checkbox" name="userRightTop" class="deleteAllCheckBox"></td>


                                        <td style="width:6%">
                                            <button type="button" class="icon btn_add" id="addProfile"
                                                    data-target="#addFormprofile"></button>
                                        </td>
                                        <td style="width: 40%">${e.get('profile.id')}</td>
                                        <td>${e.get('accessRight.name')}</td>
                                    </tr>
                                    <tr class="condition">
                                        <td colspan="3"><input type="text" col="3" placeholder="search " class="searchInput">
                                        </td>
                                        <td><input type="text" col="4" placeholder="search " class="searchInput"></td>
                                    </tr>
                                    </thead>
                                    <#assign index1=0>
                                    <#assign index1=(templateAuthorityForm.profileAccessRights??)?then(templateAuthorityForm.profileAccessRights?size,0)>
                                    <tbody id="profileTbody" class="table_body" index="${index1}">

                                        <#if templateAuthorityForm.profileAccessRights??>
                                            <#list templateAuthorityForm.profileAccessRights as profileAccessRight>
                                            <tr profileId="${profileAccessRight.profileId}" index="${profileAccessRight?index}">
                                                <td><input type="checkbox" name="profileRight" class="deleteCheckBox">
                                                </td>
                                                <td class="text_center">
                                                     <span class="icon icon_edit focusable"
                                                           onclick="editRow('profile','${profileAccessRight.profileId!""}', '${profileAccessRight.accessRightValue!""}','1');">
                                                    </span>
                                                </td>
                                                <td type="text">
                                                <span>${profileAccessRight.profileId!""}</span><input type="hidden"
                                                        name="profileAccessRights[${profileAccessRight?index}].profileId"
                                                                       value="${profileAccessRight.profileId!""}"/>
                                                </td>
                                                <td type="text">
                                                    <#if profileAccessRight.accessRightValue??>
                                                        <#assign valueP=profileAccessRight.accessRightValue>
                                                    </#if>

                                                    <span>${valueP???then(accessRights[valueP?c]!"","")}</span>
                                                    <input
                                                        type="hidden"
                                                        name="profileAccessRights[${profileAccessRight?index}].accessRightValue" value="${valueP!""}"
                                                        readonly/>
                                                </td>
                                            </tr>
                                            </#list>
                                        </#if>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>

            </div>
        </div>
    </div>

</form>

<#include "settingPopup.ftl">
</@standard.standardPage>