<#assign contentFooter>
    <@component.detailUpdatePanel object="menuAccessRight" formId="menuAuthorityForm"></@component.detailUpdatePanel>
</#assign>
<#assign script>
<script src="${rc.getContextPath()}/resources/js/menuAuth.js"></script>
</#assign>

<@standard.standardPage title=e.get("mgrMenu.authority") contentFooter=contentFooter script=script imageLink="authority">

<form method="post" action="${rc.getContextPath()}/menuAccessRight/updateMenuAccessRight" id="menuAuthorityForm"
      object="menuAuthorityForm">
    <#include "../common/messages.ftl">
    <div class="board-wrapper">
        <div class="board board-half" style="vertical-align: top;height: 690px">
            <table class="table_form">
                <tr>
                    <td width="50%">${e.get('mgrMenu.functionId')}</td>
                    <td>
                        ${menuAuthorityForm.functionId!""}
                        <input type="hidden" name="functionId" value="${menuAuthorityForm.functionId!""}" >
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('mgrMenu.functionName')}</td>
                    <td>
                        ${menuAuthorityForm.functionName!""}
                        <input type="hidden" name="functionName" height="20px" width="150px" style="text-align: left"
                               value="${menuAuthorityForm.functionName!""}">
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('mgrMenu.functionCategoryId')}</td>
                    <td>
                        ${menuAuthorityForm.functionCategoryId!""}
                        <input type="hidden" name="functionCategoryId"
                               value="${menuAuthorityForm.functionCategoryId!""}" >
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('mgrMenu.functionCategoryName')}</td>
                    <td>
                        ${menuAuthorityForm.functionCategoryName!""}
                        <input type="hidden" name="functionCategoryName"
                               value="${menuAuthorityForm.functionCategoryName!""}" >
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
                                ('access.user')
}</a>
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
                                        onclick="return delRow('user');" tabindex="3">
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
                                                    data-target="#addFormuser" tabindex="5"></button>
                                        </td>
                                        <td style="width: 40%">${e.get('user.id')}</td>
                                        <td>${e.get('accessRight.name')}</td>
                                    </tr>
                                    <tr class="condition">
                                        <td colspan="3"><input type="text" col="3" placeholder="search " tabindex="6"  class="searchInput">
                                        </td>
                                        <td><input type="text" col="4" placeholder="search " tabindex="7"  class="searchInput"></td>
                                    </tr>
                                    </thead>
                                    <#assign index=(menuAuthorityForm.userAccessRights??)?then
                                    (menuAuthorityForm.userAccessRights?size,0)>
                                    <tbody id="userTbody" class="table_body" index="${index}">
                                        <#if menuAuthorityForm.userAccessRights??>
                                            <#list menuAuthorityForm.userAccessRights as userAccessRight>
                                            <tr userId="${userAccessRight.userId}" index="${userAccessRight?index}">
                                                <td><input type="checkbox" class="deleteCheckBox" tabindex="8"/></td>
                                                <td class="text_center">
                                                    <a class="icon icon_edit"
                                                          onclick="editMenuRow('user','${userAccessRight.userId!""}',
                                                                  null)"
                                                          tabindex="9"></a>
                                                </td>
                                                <td class="text">
                                                    <span> ${userAccessRight.userId!""}</span>
                                                    <input type="hidden"
                                                           name="userAccessRights[${userAccessRight?index}].userId"
                                                           value="${userAccessRight.userId!""}"
                                                    />
                                                </td>
                                                <td class="text">
                                                    <#if userAccessRight.accessRightValue??>
                                                        <#assign value=userAccessRight.accessRightValue>
                                                    </#if>
                                                    <span>${value???then(templateAuthoritySection[value?c]!"","")}</span>
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
                                        id="deleteButtonPopup" onclick="return delRow('profile');">
                                    <@spring.message code='button.delete'/></button>
                                <div style="height: 10px;"></div>
                                <table class="clientSearch table_list profileAccessRightTable">
                                    <thead>
                                    <tr class="table_header">
                                        <td style="width:5%"><input type="checkbox" name="userRightTop" class="deleteAllCheckBox"></td>
                                        <td class="text_center" style="width:6%">

                                            <button type="button" class="icon btn_add" id="addProfile"
                                                    data-target="#addFormprofile"></button>

                                        </td>
                                        <td style="width: 40%">${e.get('profile.id')}</td>
                                        <td>${e.get('accessRight.name')}</td>
                                    </tr>
                                    <tr class="condition">
                                        <td colspan="3"><input type="text" col="3" placeholder="search "  class="searchInput">
                                        </td>
                                        <td><input type="text" col="4" placeholder="search "  class="searchInput"></td>
                                    </tr>
                                    </thead>
                                    <#assign index1=(menuAuthorityForm.profileAccessRights??)?then(menuAuthorityForm.profileAccessRights?size,0)>
                                    <tbody id="profileTbody" class="table_body" index="${index1}">

                                        <#if menuAuthorityForm.profileAccessRights??>
                                            <#list menuAuthorityForm.profileAccessRights as profileAccessRight>
                                            <tr profileId="${profileAccessRight.profileId}" index="${profileAccessRight?index}">
                                                <td><input type="checkbox" name="profileRight" class="deleteCheckBox">
                                                </td>
                                                <td class="text_center">
                                                    <a class="icon icon_edit"
                                                       onclick="editMenuRow('profile','${profileAccessRight
                                                       .profileId!""}', null);">
                                                    </a>
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
                                                    <span>
                                                    ${valueP???then(templateAuthoritySection[valueP?c]!"","")}
                                                    </span>
                                                    <input
                                                        type="hidden"
                                                        name="profileAccessRights[${profileAccessRight?index}].accessRightValue"
                                                        value="${valueP!""}"
                                                        readonly/></td>
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
<input type="hidden" id="accessRightName" value="${templateAuthoritySection["3"]}">
<input type="hidden" id="accessRightValue" value="3">
</form>
  <#include "settingPopup.ftl">
</@standard.standardPage>
