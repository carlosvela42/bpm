<script id="addRow" type="text/x-handlebars-template">
    <tr {{name}}Id="{{id}}">
        <td><input type="checkbox" class="deleteCheckBox"/></td>
        <td class="text_center">
            <a class="icon icon_edit" onclick="editMenuRow('{{name}}','{{id}}','{{accessRight}}');"></a>
        <td class="text">
            <span>{{id}}</span>
            <input type="hidden"
                   name="{{name}}AccessRights[{{index}}].{{name}}Id"
                   value="{{id}}"
            />
        </td>
        <td class="text">
            <span>{{accessRight}}<span>
            <input type="hidden"
                   name="{{name}}AccessRights[{{index}}].accessRightValue"
                   value="{{valueAccess}}"
            />
        </td>
    </tr>
</script>

<div class="modal fade" id="addFormuser" role="dialog" style="top: 300px">
    <select id="userSelectOrigin" style="display: none;">
    <#list mgrUsers as mgrUser>
        <option id="${mgrUser.userId}"
                value="${mgrUser.userId}">${mgrUser.userId!""}</option>
    </#list>
    </select>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="return cancel('user');">X</button>
                <span class="modal-title title_popup">
                <@spring.message code='mgrMenu.addAuthority'/>
                </span>
            </div>
            <input type="hidden" id="userEditItem" value="0">
            <input type="hidden" id="userIdOld" value="">
            <div class="modal-body">
                <table class="table_form">
                    <tr style="border-bottom: 0px">
                        <td width="40%">${e.get('user.id')}</td>
                        <td>
                            <select id="userSelect">
                            <#list mgrUsers as mgrUser>
                                <option id="${mgrUser.userId}"
                                        value="${mgrUser.userId}">${mgrUser.userId!""}</option>
                            </#list>
                            </select>
                            <label id="textuser"></label>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer modal-footer-popup">
                <button type="button" class="btn btn-default btn_cancel_popup btn_popup" data-dismiss="modal" onclick="return cancel('user');">
                ${e.get('button.cancel')}
                </button>
                <button align="center" colspan="3" class="btn btn-default btn_save_popup btn_popup" id="addRowUser"
                        onclick="editRowNew('user')">
                ${e.get('button.save')}
                </button>
            </div>
        </div>
    </div>
</div>


<#--Profile-->
<div class="modal fade" id="addFormprofile" role="dialog" style="top: 300px">
    <select id="profileSelectOrigin" style="display: none;">
    <#list mgrProfiles as mgrProfile>
        <option id="${mgrProfile.profileId}"
                value="${mgrProfile.profileId}">${mgrProfile.profileId!""}</option>
    </#list>
    </select>
    <div class="modal-dialog">
    <#--<div class="overlay" id="addFormprofile" role="dialog">-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="return cancel('profile');">
                    X
                </button>
                <span class="modal-title title_popup">
                <@spring.message code='mgrMenu.addAuthority'/>
                    </span>
            </div>
            <input type="hidden" id="profileEditItem" value="0">
            <input type="hidden" id="profileIdOld" value="">
            <div class="modal-body">
                <table class="table_form">
                    <tr style="border-bottom: 0px">
                        <td width="40%">${e.get('profile.id')}</td>
                        <td>
                            <select id="profileSelect">
                            <#list mgrProfiles as mgrProfile>
                                <option id="${mgrProfile.profileId}"
                                        value="${mgrProfile.profileId}">${mgrProfile.profileId!""}</option>
                            </#list>
                            </select>
                            <label id="textprofile"></label>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer modal-footer-popup">
                <button type="button" class="btn btn-default btn_cancel_popup btn_popup" data-dismiss="modal" onclick="return cancel('profile');">
                ${e.get('button.cancel')}
                </button>
                <button align="center" colspan="3" class="btn btn-default btn_save_popup btn_popup" id="addRowProfile"
                        onclick="editRowNew('profile')">
                ${e.get('button.save')}
                </button>
            </div>
        </div>
    </div>
</div>