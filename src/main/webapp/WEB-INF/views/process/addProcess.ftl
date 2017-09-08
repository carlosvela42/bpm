<#assign contentFooter>
    <@component.detailUpdatePanel object="process" formId="processForm"></@component.detailUpdatePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/process.js"></script>
</#assign>

<@standard.standardPage title=e.get("process.edit") contentFooter=contentFooter script=script imageLink="process">
<br>
    <#assign isPersisted = !isUpdate>
    <#assign formAction = isPersisted?then('updateOne', 'insertOne')>

<form id="processForm" action="${rc.getContextPath()}/process/${formAction}" object="processForm" method="post"
      class="" imageLink="process">
    <#include "../common/messages.ftl">
    <div class="board-wrapper">
        <div class="board board-half" style="height: 650px">
            <table class="table_form">
                <tr>
                    <td width="50%">${e.get('process.id')}</td>
                    <td>
                        <#if isPersisted>
                            ${process.processId!""}
                        </#if>
                        <input type="hidden" name="process.processId" value="${process.processId!""}"/>
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('process.name')}</td>
                    <td><input type="text" name="process.processName" value="${(process.processName!"")?html}"/></td>
                </tr>
                <tr>
                    <td>${e.get('process.version')}</td>
                    <td>
                        <#if process.processVersion??>
                            <#if process.processVersion==0>
                                <#assign processVersion="">
                            <#else>
                                <#assign processVersion=process.processVersion?c?html>
                            </#if>
                        <#else>
                            <#assign processVersion="">
                        </#if>
                        <input type="text" name="process.processVersion" maxlength="7" class="number"
                               value="${processVersion}"/></td>
                </tr>
                <tr>
                    <td>${e.get('process.description')}</td>
                    <td><input type="text" name="process.description" value="${(process.description!"")?html}"/></td>
                </tr>
                <tr>
                    <td>${e.get('process.definition')}</td>

                    <td>
                        <span class="btn btn-default btn_popup  btn-file btn_upload">
						    ${e.get('button.upload')}
						        <input type="file" id="fileUpload" disabled="disabled"/>
						</span>
                        <input type="button" id="fileDownload" disabled="disabled" class="btn btn-default btn_popup
                        btn_download" style="background-color: black" value=${e.get('button.download')}"></td>
                </tr>
            </table>
        </div>
        <div class="board-split"></div>
        <div class="board board-half">
            <#include "saveTarget.ftl">
        </div>
        <#--<div class="clearfix"></div>-->
    </div>
    <div><input type="hidden" name="workspaceId" value="${workspaceId!""}"/></div>

</form>
</@standard.standardPage>