<#assign contentFooter>
    <@component.detailUpdatePanel object="directory" formId="directoryForm"></@component.detailUpdatePanel>
</#assign>
<#assign script>
<script src="${rc.getContextPath()}/resources/js/directory.js"></script>
</#assign>
<@standard.standardPage title=e.get("directory.add") contentFooter=contentFooter script=script imageLink="process">
<#assign isPersisted = (directoryForm.lastUpdateTime??)>
<#assign formAction = isPersisted?then('updateOne', 'insertOne')>

<form id="directoryForm" action="${rc.getContextPath()}/directory/${formAction}" object="directoryForm" method="post"
      class="" >
    <#include "../common/messages.ftl">
    <div class="board-wrapper">
        <div class="board board-half">
            <table class="table_form">
                <tr>
                    <td width="50%">${e.get('directory.id')}</td>
                    <td>
                        <#if isPersisted>
                            ${directoryForm.dataDirectoryId!""}
                        </#if>
                         <input type="hidden" name="dataDirectoryId"
                                                           value="${directoryForm.dataDirectoryId!""}"/>
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('create.new.file')}</td>
                    <td><br />
                    <#if directoryForm.newCreateFile??>
                        <#if directoryForm.newCreateFile=="1">
                            <#assign enable="checked">
                        </#if>

                        <#if directoryForm.newCreateFile=="2">
                            <#assign disable="checked">
                        </#if>
                    </#if>
                    <input type="radio" name="enable" id='enable' value="1" ${enable!""} > <label class="permision font-bold">${e.get('give.permission')}</label>
                    <br /><br />
                    <input type="radio" name="disable" id='disable' value="2" ${disable!""} >
                        <label class="permision font-bold">${e.get('not.allow')}</label>
                    <input type="hidden" id="newCreateFile" name="newCreateFile" height="20px" width="150px"
                           style="text-align: left" value="${directoryForm.newCreateFile!""}"><br /><br />
                </td>
                </tr>
                <tr>
                    <td>${e.get('secured.disk.space')}</td>
                    <td>
                        <input type="text" name="reservedDiskVolSize" value="${(directoryForm.reservedDiskVolSize!"")?html}"/>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('disk.space')}</td>
                    <td>
                        <input type="text" id="txtDiskVolSize" name="diskVolSize" value="${directoryForm.diskVolSize!""}"
                               readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('folder.path')}</td>
                    <td>
                        <input type="text" id="txtFolderPath" name="folderPath" value="${(directoryForm.folderPath!"")?html}"/>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="lastUpdateTime" value="${directoryForm.lastUpdateTime!""}"/>
        </div>
        <div class="board-split"></div>
    </div>
</form>
</@standard.standardPage>