<#if type??>
<input type="hidden" id="type" value='${type}'/>
</#if>
<#if workspaceId??>
<input type="hidden" id="workspaceId" value='${workspaceId}'/>
</#if>
<#if processId??>
<input type="hidden" id="processId" value='${processId}'/>
</#if>
<#if workItemId??>
<input type="hidden" id="workItemId" value='${workItemId}'/>
    <#if processId??>
        <input type="hidden" id="currentNode" value="${type}_${processId!''}"/>
    <#elseif layerNo?? && documentNo?? && folderItemNo??>
        <input type="hidden" id="currentNode" value="${type}_${folderItemNo}${'_' + documentNo}${'_' + layerNo}"/>
    <#elseif documentNo?? && folderItemNo??>
        <input type="hidden" id="currentNode" value="${type}_${folderItemNo}${'_' + documentNo}"/>
    <#elseif folderItemNo??>
        <input type="hidden" id="currentNode" value="${type}_${folderItemNo}"/>
    <#else>
        <input type="hidden" id="currentNode" value="${type}_${workItemId}"/>
    </#if>
</#if>
<#if folderItemNo??>
<input type="hidden" id="folderItemNo" value='${folderItemNo}'/>
</#if>
<#if documentNo??>
<input type="hidden" id="documentNo" value='${documentNo}'/>
</#if>
<#if layerNo??>
<input type="hidden" id="layerNo" value='${layerNo}'/>
</#if>
<#if ownerId??>
<input type="hidden" id="ownerId" value='${ownerId}'/>
</#if>
<#if currentTemplateId??>
<input type="hidden" id="currentTemplateId" value='${currentTemplateId}'/>
</#if>
<input type="hidden" id="accessRight" value=''/>
<#if templateList??>
<div class="row" style="margin:10px">
    <div class="col-md-4">
        <label for="templateList" style="line-height: 0px;" class="font-bold">${e.get('template.default')}</label>
    </div>
    <div class="col-md-8" style="padding: 0;">
        <div class="form-group">
            <select id="templateList" name="templateList" class="form-control" onchange="setTemplateField()">
                <option value="">  </option>
                <#list templateList as template>
                    <#if template.templateId??>
                    <#-- START: Binding work item data -->
                        <option value="${template.templateId}"
                            <#if template.templateField??>
                                templateField='${template.templateField}'
                            </#if>
                            <#if template.templateTableName??>
                                templateTableName='${template.templateTableName}'
                            </#if>
                            <#if template.templateType??>
                                templateType='${template.templateType}'
                            </#if>
                        >
                        ${template.templateName}
                        </option>
                    </#if>
                </#list>
            </select>
        </div>
    </div>
</div>
</#if>

<div class="row" style="margin:10px" id="div_template">
    <#include "templateField.ftl">
</div>