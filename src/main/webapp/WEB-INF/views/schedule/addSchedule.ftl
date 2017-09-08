<#assign contentFooter>
    <@component.detailUpdatePanel object="schedule" formId="scheduleForm"></@component.detailUpdatePanel>
</#assign>
<#assign script>
<script src="${rc.getContextPath()}/resources/js/schedule.js"></script>
</#assign>
<@standard.standardPage title=e.get("schedule.add") contentFooter=contentFooter script=script imageLink="process">

<br>
    <#assign isPersisted = (mgrSchedule.lastUpdateTime??)>
    <#assign formAction = isPersisted?then('updateOne', 'insertOne')>

<form id="scheduleForm" action="${rc.getContextPath()}/schedule/${formAction}" object="scheduleForm" method="post"
      class="" imageLink="process">
    <#include "../common/messages.ftl">

    <div class="board-wrapper">
        <div class="board board-half" style="height: 650px">
            <table class="table_form">
                <tr>
                    <td width="50%">${e.get('schedule.id')}</td>
                    <td>
                        <#if isPersisted>
                            ${mgrSchedule.scheduleId!""}
                        </#if>
                        <input type="hidden" name="scheduleId"
                                                           value="${mgrSchedule.scheduleId!""}"/>
                   </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('schedule.hostname')}</td>
                    <td><input type="text" name="hostName" value="${(mgrSchedule.hostName!"")?html}"/></td>
                </tr>
                <tr>
                    <td>${e.get('process.service')}</td>
                    <td>
                        <select id="processIServiceName" name="processIServiceId">
                            <option value="" selected="selected"></option>
                            <#if mgrProcessServices??>
                                <#list mgrProcessServices as mgrProcessService>
                                    <#if mgrSchedule.processIServiceId??>
                                        <#assign selectedProcessService =((mgrSchedule.processIServiceId) == (mgrProcessService.processIServiceId?c))?then("selected","")>
                                    </#if>
                                    <option value="${mgrProcessService.processIServiceId}"
                                    ${selectedProcessService!""}>${(mgrProcessService.processIServiceName)?html}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('process.name')}</td>
                    <td>
                        <select id="processName" name="processId">
                            <option value="0" selected="selected"></option>
                            <#if mgrProcesses??>
                                <#list mgrProcesses as mgrProcess>
                                    <#if mgrSchedule.task.processId??>
                                        <#assign selectedProcess =(mgrSchedule.task.processId==(mgrProcess.processId?c))?then("selected","")>
                                    </#if>
                                    <option value="${mgrProcess.processId}" ${selectedProcess!""}>${(mgrProcess.processName)?html}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>${e.get('schedule.taskName')}</td>
                    <td>
                        <select id="taskName" name="taskId">
                            <option value="" selected="selected"></option>
                            <#if mgrTasks??>
                                <#list mgrTasks as mgrTask>
                                    <#if mgrSchedule.taskId??>
                                        <#assign selectedTask =(mgrSchedule.taskId==(mgrTask.taskId))?then("selected='selected'","")>
                                    </#if>
                                    <option value="${mgrTask.taskId}" ${selectedTask!""} data-processid="${mgrTask.processId}">${mgrTask.taskName}</option>
                                </#list>
                            </#if>
                        </select>
                        <select id="taskNameOrigin" style="display: none;">
                            <option value="" selected="selected"></option>
                            <#if mgrTasks??>
                                <#list mgrTasks as mgrTask>
                                    <#if mgrSchedule.taskId??>
                                        <#assign selectedTask =(mgrSchedule.taskId==(mgrTask.taskId))?then("selected='selected'","")>
                                    </#if>
                                    <option value="${mgrTask.taskId}" ${selectedTask!""} data-processid="${mgrTask.processId}">${mgrTask.taskName}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
        <div class="board-split"></div>
        <div class="board board-half">
            <table class="table_form">
                <tr>
                    <td width="50%">${e.get('schedule.enable.disable')}</td>
                    <td>
                        <#if mgrSchedule.enableDisable??>
                            <#if mgrSchedule.enableDisable=="1">
                                <#assign enable="checked">
                            </#if>

                            <#if mgrSchedule.enableDisable=="2">
                                <#assign disable="checked">
                            </#if>
                        </#if>
                        <div class="row">
                            <div class="col-md-6">
                                <input type="radio" name="enable" id="enable"
                                       value="1" ${enable!""}>${e.get('schedule.enable')}
                            </div>
                            <div class="col-md-6">
                                <input type="radio" name="disable" id="disable"
                                       value="2" ${disable!""}>${e.get('schedule.disable')}
                            </div>
                        </div>
                        <input type="hidden" id="enableDisable" name="enableDisable" height="20px" width="150px"
                               style="text-align: left" value="${mgrSchedule.enableDisable!""}">
                    </td>
                </tr>
                <tr>
                    <td width="50%">${e.get('schedule.startDateTime')}</td>
                    <td><input type="text" name="startTime" value="${(mgrSchedule.startTime!"")?html}"
                               placeholder="StartTime"/></td>
                </tr>
                <tr>
                    <td>${e.get('schedule.endDateTime')}</td>
                    <td><input type="text" name="endTime" value="${(mgrSchedule.endTime!"")?html}" placeholder="EndTime"/></td>
                </tr>
                <tr class="nextDateTimeSchedule">
                    <td>${e.get('schedule.executionInterval')}</td>
                    <td>
                        <input type="text" class="halfWidth" name="runIntervalDay"
                               value="${(mgrSchedule.runIntervalDay!"")?html}"/><label>${e.get('schedule.day')}</label>
                        <br>
                        <input type="text" class="halfWidth" name="runIntervalHour"
                               value="${(mgrSchedule.runIntervalHour!"")?html}"/><label>${e.get('schedule.hour')}</label>
                        <br>
                        <input type="text" class="halfWidth" name="runIntervalMinute"
                               value="${(mgrSchedule.runIntervalMinute!"")?html}"/><label>${e.get('schedule.minute')}</label>
                        <br>
                        <input type="text" class="halfWidth lastSecond" name="runIntervalSecond"
                               value="${(mgrSchedule.runIntervalSecond!"")?html}"/><label>${e.get('schedule.second')}</label>
                    </td>
                </tr>
                <tr class="nextDateTimeSchedule">
                    <td>${e.get('schedule.nextRunDate')}</td>
                    <td><label>${(mgrSchedule.nextRunDate!"")?html}</label></td>
                    <input type="hidden" name="nextRunDate"
                           value="${(mgrSchedule.nextRunDate!"")?html}"/>
                </tr>
            </table>
            <div><input type="hidden" name="lastUpdateTime" value="${(mgrSchedule.lastUpdateTime!"")?html}"/></div>
        </div>
    </div>
    <div><input type="hidden" name="workspaceId" value="${workspaceId!""}"/></div>
</form>
</@standard.standardPage>