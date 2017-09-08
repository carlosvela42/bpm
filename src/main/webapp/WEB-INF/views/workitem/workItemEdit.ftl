<#assign contentFooter>
    <@component.updateWorkItemPanel object="workItem" formId="workItemForm"></@component.updateWorkItemPanel>
</#assign>
<#assign script>
<script src="${rc.getContextPath()}/resources/js/lib/tiff.min.js"></script>
<script src="${rc.getContextPath()}/resources/js/lib/pdf.js"></script>
<script src="${rc.getContextPath()}/resources/js/lib/svg/svg.js"></script>
<script src="${rc.getContextPath()}/resources/js/lib/svg/svg.draw.js"></script>
<script src="${rc.getContextPath()}/resources/js/imageViewer/event.js"></script>
<script src="${rc.getContextPath()}/resources/js/workitem.js"></script>
<script src="${rc.getContextPath()}/resources/js/imageViewer/model.js"></script>
<script src="${rc.getContextPath()}/resources/js/imageViewer/main.js"></script>
<script src="${rc.getContextPath()}/resources/js/imageViewer/thumbernail.js"></script>

<link rel="stylesheet" media="screen" href="${rc.getContextPath()}/resources/css/workitem/style.css">
</#assign>

<@standard.standardPage title=e.get('dataEdit.title') script=script contentFooter=contentFooter imageLink="tool">
<div id="errorMessage"></div>
<div class="board-wrapper">
    <div class="board_template board-half board-half-left" id="div_treeview">
    <#-- START: Binding work item data -->
        <#if workItem??>
            <input type="hidden" id="workspaceId" value='${workItem.workspaceId???then(workItem.workspaceId,'')}'/>
            <input type="hidden" id="workItemId" value='${workItem.workitemId???then(workItem.workitemId,'')}'/>
            <input type="hidden" id="hdnSession" value='${token}'/>
            <#if enums??>
                <#assign AccessRightEnum = enums["co.jp.nej.earth.model.enums.AccessRight"] >
                <#assign TemplateType = enums["co.jp.nej.earth.model.enums.TemplateType"] >
            </#if>
            <div id="treeview5" class="treeview">
                <ul class="tree">
                    <#if workItem.dataProcess??>
                        <#if workItem.dataProcess.accessRight??>
                            <#assign accessRight="${workItem.dataProcess.accessRight.name()}">
                        </#if>
                        <#if workItem.dataProcess.processId??>
                            <#assign processId="${workItem.dataProcess.processId}">
                        </#if>
                        <li class="first-node"
                            processId="${processId!''}"
                            type="${TemplateType.PROCESS.value}" onload="showTemplate(this)"
                            accessRight="${accessRight???then(accessRight, AccessRightEnum.NONE)}"
                        >
                            <div class="node-selected label-link ${TemplateType.PROCESS.value}_${processId!''} ${(accessRight==AccessRightEnum.SO)?string('disabled','')}"
                                <#if accessRight == AccessRightEnum.RO || accessRight == AccessRightEnum.RW || accessRight == AccessRightEnum.FULL>
                                 onclick="showTemplate(this)"
                                </#if>
                                 type="${TemplateType.PROCESS.value}"
                                 accessRight="${accessRight???then(accessRight, AccessRightEnum.NONE)}"
                                 processId="${processId!''}"
                                <#if accessRight == AccessRightEnum.SO>
                                 style="cursor: no-drop"
                                </#if>
                            >
                                <span class="icon node-icon process"></span>
                                <#if workItem.dataProcess.mgrTemplate??>
                                    <label class="font-bold">
                                    ${workItem.dataProcess.mgrTemplate.templateName}
                                    </label>
                                </#if>
                            </div>

                        <#---- Binding workitem -->
                            <#if workItem.accessRight??>
                                <#assign accessRight="${workItem.accessRight.name()}">
                            </#if>
                            <ul>
                                <li id="li_workItem"
                                    type="${TemplateType.WORKITEM.value}"
                                    accessRight="${accessRight???then(accessRight, AccessRightEnum.NONE)}"
                                >
                                    <div class="label-link ${TemplateType.WORKITEM.value}_${workItem.workitemId} ${(accessRight==AccessRightEnum.SO)?string('disabled','')}"
                                        <#if accessRight == AccessRightEnum.RO || accessRight == AccessRightEnum.RW || accessRight == AccessRightEnum.FULL>
                                            onclick="showTemplate(this)"
                                        </#if>
                                         type="${TemplateType.WORKITEM.value}"
                                         accessRight="${accessRight???then(accessRight, AccessRightEnum.NONE)}"
                                        <#if accessRight == AccessRightEnum.SO>
                                         style="cursor: no-drop"
                                        </#if>
                                    >
                                        <span class="icon node-icon workItem"></span>
                                        <label class="font-bold">
                                            <#if workItem.mgrTemplate??>
                                            ${workItem.mgrTemplate.templateName}
                                            </#if>
                                        </label>
                                    </div>
                                <#-- START: Binding folder data -->
                                    <ul>
                                        <#if workItem.folderItems??>
                                            <#list workItem.folderItems as folderItem>
                                                <#assign accessRight=AccessRightEnum.NONE>
                                                <#if folderItem.accessRight??>
                                                    <#assign accessRight="${folderItem.accessRight.name()}">
                                                </#if>
                                                <li folderItemNo="${folderItem.folderItemNo!''}"
                                                    type="${TemplateType.FOLDERITEM.value}"
                                                    accessRight="${accessRight}"
                                                >
                                                    <div class="label-link ${TemplateType.FOLDERITEM.value}_${folderItem.folderItemNo!''} ${(accessRight==AccessRightEnum.SO)?string('disabled','')}"
                                                        <#if accessRight == AccessRightEnum.RO || accessRight == AccessRightEnum.RW || accessRight == AccessRightEnum.FULL>
                                                            onclick="showTemplate(this)"
                                                        </#if>
                                                         type="${TemplateType.FOLDERITEM.value}"
                                                         accessRight="${accessRight}"
                                                         folderItemNo="${folderItem.folderItemNo!''}"
                                                    >
                                                        <span class="icon folder"></span>
                                                        <label class="font-bold">
                                                            <#if folderItem.mgrTemplate??>
                                                            ${folderItem.mgrTemplate.templateName???then(folderItem.mgrTemplate.templateName,"")}
                                                            </#if>
                                                        </label>
                                                    </div>
                                                </li>
                                            </#list>
                                        </#if>
                                    </ul>
                                <#-- END: Binding folder data -->
                                </li>
                            </ul>
                        </li>
                    </#if>
                </ul>
            </div>
            <div id="document_area" class="document-area">
                <#if workItem.folderItems??>
                    <#list workItem.folderItems as folderItem>
                        <fieldset
                                id="folder${folderItem???then(folderItem.folderItemNo???then(folderItem.folderItemNo,''),'')}"
                                class="field-document-image hidden">
                            <#if folderItem.documents??>
                                <#list folderItem.documents as document>
                                    <div class="document-image document-image${document.documentNo???then(document.documentNo,'')}">
                                        <#if document.accessRight??>
                                            <#assign accessRight="${document.accessRight.name()}">
                                        </#if>
                                        <#if accessRight == AccessRightEnum.RO || accessRight == AccessRightEnum.RW || accessRight == AccessRightEnum.FULL>
                                            <div id="document_${folderItem.folderItemNo???then(folderItem.folderItemNo,'')
                                            }_${document.documentNo???then(document.documentNo,'')}" style="cursor: pointer;
                                    width:152px; height:152px;border: 1px solid #999999;">
                                                <input type="hidden" name="documentNo"
                                                       value="${document.documentNo???then(document.documentNo,'')}">
                                                <input type="hidden" name="documentType"
                                                       value="${document.documentType???then(document.documentType,'')}">
                                                <input type="hidden" name="pageCount"
                                                       value="${document.pageCount???then(document.pageCount,'0')}">
                                            </div>
                                        <#else>
                                            <img src="${rc.getContextPath()}/resources/images/workitem/thumberNailNone.png">
                                        </#if>
                                        <#assign
                                        folderItemNo = document.folderItemNo???then(document.folderItemNo,'')
                                        documentNo = document.documentNo???then(document.documentNo,'')
                                        >
                                        <#if document.mgrTemplate??>
                                            <#if document.mgrTemplate.templateName??>
                                                <label
                                                        class="document-link ${TemplateType.DOCUMENT.value}_${folderItemNo!''}${documentNo???then('_' +
                                                        documentNo,'')} ${(accessRight==AccessRightEnum.SO)?string('disabled','')}"
                                                        documentNo="${documentNo!''}"
                                                        folderItemNo="${folderItemNo!''}"
                                                        parentNode="${TemplateType.FOLDERITEM.value}_${folderItem.folderItemNo!''}"
                                                        type="${TemplateType.DOCUMENT.value}"
                                                    <#if accessRight == AccessRightEnum.RO || accessRight == AccessRightEnum.RW || accessRight == AccessRightEnum.FULL>
                                                        onclick="showTemplate(this)"
                                                    </#if>
                                                        accessRight="${accessRight???then(accessRight, AccessRightEnum.NONE)}"
                                                >
                                                    <#if accessRight == AccessRightEnum.RO || accessRight == AccessRightEnum.RW || accessRight == AccessRightEnum.FULL>
                                                        <a href="#">${document.mgrTemplate.templateName}</a>
                                                    <#else>
                                                    ${document.mgrTemplate.templateName}
                                                    </#if>
                                                </label>
                                            </#if>
                                        <#else>
                                            <label
                                                    class="document-link ${TemplateType.DOCUMENT.value}_${folderItemNo!''}${documentNo???then('_' +
                                                    documentNo,'')} ${(accessRight==AccessRightEnum.SO)?string('disabled','')}"
                                                    documentNo="${documentNo!''}"
                                                    folderItemNo="${folderItemNo!''}"
                                                    parentNode="${TemplateType.FOLDERITEM.value}_${folderItem.folderItemNo!''}"
                                                    type="${TemplateType.DOCUMENT.value}"
                                                <#if accessRight == AccessRightEnum.RO || accessRight == AccessRightEnum.RW || accessRight == AccessRightEnum.FULL>
                                                    onclick="showTemplate(this)"
                                                </#if>
                                                    accessRight="${accessRight???then(accessRight, AccessRightEnum.NONE)}"
                                            >
                                                <a tabindex="2" href="#">&nbsp;</a>
                                            </label>
                                        </#if>
                                    </div>
                                </#list>
                            </#if>
                        </fieldset>
                    </#list>
                </#if>
            </div>
        </#if>
    <#-- /END: Binding work item data -->
    </div>
    <div class="board-split"></div>
    <div class="board-half-right">
        <div class="template-area-fieldSet">
            <fieldset id="template_area_fieldSet">
                <div id="template_area"></div>
            </fieldset>
        </div>
        <div class="task-list-area">
            <#if tasks??>
                <div id="task_list" class="task-list">
                    <div class="col-md-4">
                        <label for="taskList" class="font-bold">${e.get('task.name')}</label>
                    </div>
                    <div class="col-md-8" style="padding: 0;">
                        <div class="form-group">
                            <select id="taskList" name="taskList" class="form-control">
                                <#list tasks as task>
                                    <#if task.taskId??>
                                        <#assign selectedtask = (task.taskId==(currentTaskId???then(currentTaskId,"")))?then("selected","")>
                                        <option value="${task.taskId}" ${selectedtask}>
                                        ${task.taskName}
                                        </option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                    </div>
                </div>
            </#if>
        </div>
    </div>
</div>
<#--Modal-->
</@standard.standardPage>