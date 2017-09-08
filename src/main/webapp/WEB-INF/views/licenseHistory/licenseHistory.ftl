<#assign searchForm>
    <@component.searchColumnFormPanel object="" formId=""></@component.searchColumnFormPanel>
</#assign>

<#assign addSearchPopupFrom>
    <@component.searchFormPopup></@component.searchFormPopup>
</#assign>

<#assign addFrom>
    <@component.workItemSearch></@component.workItemSearch>
</#assign>
<#assign addPopupFrom>
    <@component.searchPopup></@component.searchPopup>
</#assign>
<#assign addSearchFrom>
    <@component.searchColumnsForm></@component.searchColumnsForm>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/licenseHistory.js"></script>
</#assign>
<@standard.standardPage title=e.get('license.title') script=script imageLink="report">
<script id="licenseHistoryRow" type="text/x-handlebars-template ">
    {{#each strCals}}
    <tr>
        <td class="text">{{this.division}}</td>
        <td class="text processTime">{{this.processTime}}</td>
        <td class="text">{{this.profileId}}</td>
        <td class="number">{{format this.availableLicenseCount}}</td>
        <td class="number">{{format this.useLicenseCount}}</td>
    </tr>
    {{/each}}
</script>
    <#include "../common/messages.ftl">
<div class="board-wrapper board-full board-border">
    <div style="clear: both;"></div>
    ${addSearchPopupFrom}
    ${searchForm}
        <div class="clearfix" style="height: 15px"></div>
    <table class="clientSearch table_list">
        <colgroup>
            <col style="width: 19%" />
            <col style="width: 24%" />
            <col style="width: 19%" />
            <col style="width: 19%" />
            <col style="width: 19%" />
        </colgroup>
        <thead class="table_header">
        <tr class="rowSearch">
            <td>
                <button type="button" id="btnFiltercodeValue"
                        onclick="openSearchCondition(this, 0,'NCHAR','codeValue','${e.get('license.division')}',
                            'false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('license.division')}</div>
            </td>
            <td>
                <button type="button" id="btnFilterprocessTime"
                        onclick="openSearchCondition(this, 1,'NCHAR','processTime','${e.get('license.processTime')}',
                            'false','true');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('license.processTime')}</div>
            </td>
            <td>
                <button type="button" id="btnFilterprofileId"
                        onclick="openSearchCondition(this, 2,'NCHAR','profileId','${e.get('license.profileId')}',
                            'false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('license.profileId')}</div>
            </td>
            <td>
                <button type="button" id="btnFilteravailableLicenseCount"
                        onclick="openSearchCondition(this, 3,'NUMBER','availableLicenseCount','${e.get('license.numOfAvailableLicense')}','false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('license.numOfAvailableLicense')}</div>
            </td>
            <td>
                <button type="button" id="btnFilteruseLicenseCount"
                        onclick="openSearchCondition(this, 4,'NUMBER','useLicenseCount','${e.get('license.numOfUsedLicense')}','false','false');"
                        class="icon btn_filter btn_left"
                        data-target="#addFormSearchColumn"></button>
                <div class="textLeft">${e.get('license.numOfUsedLicense')}</div>
            </td>
        </tr>
        </thead>
        <tbody id="licenseHistoryTBody"  class="table_body">
            <#if strCals?? && strCals?has_content>
                <#list strCals as strCal>
                <tr>
                    <td class="text">${strCal.division!""}</td>
                    <td class="text processTime">${strCal.processTime!""}</td>
                    <td class="text">${strCal.profileId!""}</td>
                    <td class="number">${strCal.availableLicenseCount!""}</td>
                    <td class="number">${strCal.useLicenseCount!""}</td>
                </tr>
                </#list>
            <#else>
                <#include "../common/noResult.ftl">
            </#if>
        </tbody>
    </table>
</div>

${addPopupFrom}
${addSearchFrom}
${addFrom}
</@standard.standardPage>