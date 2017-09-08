<#assign contentFooter>
<@component.removePanel></@component.removePanel>
</#assign>


<#assign script>
<script src="${rc.getContextPath()}/resources/js/schedule.js"></script>
</#assign>

<@standard.standardPage title=e.get('schedule.title') contentFooter=contentFooter displayWorkspace=true script=script imageLink="process">
<#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
  <form method="get" id="filter" action="">
        <input type="hidden"  id="workspaceId" name="workspaceId" value="${workspaceId}">
    </form>
    <form action="${rc.getContextPath()}/schedule/" id="searchClientForm" object="searchClientForm" method="post">
    <table class="clientSearch table_list">
        <thead>
        <tr class="table_header">
            <td class="" style="width: 3%"><input type="checkbox" class="deleteAllCheckBox" /></td>
            <td class="text_center" style="width: 3%">
                <a id="addButton" class="icon icon_add" href="${rc.getContextPath()}/schedule/addNew">
                </a>
            </td>
            <td style="width: 10%">${e.get('schedule.id')}</td>
            <td style="width: 15%">${e.get('task.name')}</td>
            <td style="width: 24%">${e.get('schedule.startDateTime')}</td>
            <td style="width: 25%">${e.get('schedule.endDateTime')}</td>
            <td style="width: 25%">${e.get('schedule.nextRunDate')}</td>
        </tr>
        <tr class="condition">
            <td colspan="3"><input id="searchColumns[0]" name="searchColumns[0]" value="${(searchClientForm.searchColumns[0]!"")?html}"
                  type="text" col="3" placeholder="search" class="searchInput" /></td>
            <td><input id="searchColumns[1]" name="searchColumns[1]" value="${(searchClientForm.searchColumns[1]!"")?html}"
                  type="text" col="4" placeholder="search" class="searchInput" /></td>
            <td><input id="searchColumns[2]" name="searchColumns[2]" value="${(searchClientForm.searchColumns[2]!"")?html}"
                  type="text" col="5" placeholder="search" class="searchInput" /></td>
            <td  style="border-left: transparent; border-right-style: solid; border-right-color: #B0AFB0;" ><input id="searchColumns[3]" name="searchColumns[3]" value="${(searchClientForm
            .searchColumns[3]!"")?html}"
                  type="text" col="6" placeholder="search" class="searchInput" /></td>
            <td  style="border-left: transparent; border-right-style: solid; border-right-color: #B0AFB0;" ><input id="searchColumns[4]" name="searchColumns[4]" value="${(searchClientForm
            .searchColumns[4]!"")?html}"
                                                                                                                   type="text" col="7" placeholder="search" class="searchInput" /></td>
        </tr>
        </thead>
        <tbody id="scheduleTbody" class="table_body">
          <#if mgrSchedules??>
            <#list mgrSchedules as mgrSchedule>
            <tr scheduleId="${mgrSchedule.scheduleId}">
                <td><input type="checkbox" class="deleteCheckBox" /></td>
                <td class="text_center">
                    <a class="icon icon_edit" href="${rc.getContextPath()}/schedule/showDetail?scheduleId=${mgrSchedule.scheduleId}"></a>
                </td>
                <td class="text" style="text-align: right">${mgrSchedule.scheduleId!""}</td>
                <td class="text">${(mgrSchedule.task.taskName!"")?html}</td>
                <td class="text">${mgrSchedule.startTime!""}</td>
                <td class="text">${mgrSchedule.endTime!""}</td>
                <td class="text">${mgrSchedule.nextRunDate!""}</td>
            </tr>
            </#list>
          </#if>
        </tbody>
    </table>
    </form>
</div>
</@standard.standardPage>