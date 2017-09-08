<#assign isDatabase=(process.documentDataSavePath == "1")/>

<div class="tab_head" style="border-bottom: 1px solid black">
    <table class="table_form">
        <td width="33%">${e.get('process.DocumentPath')}</td>
        <td>
            <div class="row" style="color: #0077E5" >
                <div class="col-md-6" style="font-weight: bold">
                    <input type="radio" name="process.documentDataSavePath"
                           class="documentDataSavePath"
                           value="1" ${(isDatabase)?string('checked', '')}
                           >${e.get('process.Database')}
                </div>
                <div class="col-md-6" style="font-weight: bold">
                    <input type="radio" name="process.documentDataSavePath" class="documentDataSavePath"
                           value="2" ${(isDatabase)?string('', 'checked')}
                    >${e.get('process.File')}
                </div>
            </div>
        </td>
    </table>
</div>

<div class="tab_content_wrapper">
    <div id="databaseArea" style="display: ${(isDatabase)?string('block', 'none')}">
        <table class="table_form">
            <tr>
                <td width="33%">${e.get('process.SchemaName')}</td>
                <td><input type="text" name="strageDb.schemaName" value="${(strageDb.schemaName!"")?html}"/></td>
            </tr>
            <tr>
                <td>${e.get('process.DBuser')}</td>
                <td><input type="text" name="strageDb.dbUser" value="${(strageDb.dbUser!"")?html}"/></td>
            </tr>
            <tr>
                <td>${e.get('process.DBpasssword')}</td>
                <td><input type="text" name="strageDb.dbPassword" value="${(strageDb.dbPassword!"")?html}"/></td>
            </tr>
            <tr>
                <td>${e.get('process.Owner')}</td>
                <td><input type="text" name="strageDb.owner" value="${(strageDb.owner!"")?html}"/></td>
            </tr>
            <tr>
                <td>${e.get('process.DBserver')}</td>
                <td><input type="text" name="strageDb.dbServer" value="${(strageDb.dbServer!"")?html}"/></td>
            </tr>
        </table>
    </div>

    <div id="fileArea" style="display: ${(isDatabase)?string('none', 'block')}">
        <table class="table_form">
            <tr>
                <td width="33%">${e.get('process.SiteID')}</td>
                <td>
                    <select id="siteId" name="strageFile.siteId">
                    <#if siteIds??>
                        <#list siteIds as siteId>
                            <#if strageFile.siteId?? && siteId == strageFile.siteId>
                                <option value="${siteId}" selected>${siteId}</option>
                            <#else>
                                <option value="${siteId}">${siteId}</option>
                            </#if>
                        </#list>
                    </#if>
                    </select>
                </td>
            </tr>
            <tr style="border-bottom: 0px">
                <td>${e.get('process.SiteManagement')} ${strageFile.siteManagementType} </td>
                <td style="padding-top: 15px; margin-top: 200px;">
                    <#if strageFile.siteManagementType??>
                        <#if "1" == strageFile.siteManagementType>
                            <#assign checked1="checked">
                        <#else>
                            <#assign checked2="checked">
                        </#if>
                    </#if>
                    <input type="radio" name="strageFile.siteManagementType" class="siteManagementMethod"
                           value="1" ${checked1!""}>${e.get('process.UntilFull')}<br><br>
                    <input type="radio" name="strageFile.siteManagementType" class="siteManagementMethod"
                           value="2" ${checked2!""}>${e.get('process.RoundRobin')}
                </td>
            </tr>
        </table>
    </div>
</div>

