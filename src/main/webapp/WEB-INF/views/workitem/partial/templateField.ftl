<#if fields??>
<table class="clientSearch table_list">
    <thead>
    <tr class="table_header">
        <td style="width: 120px;word-wrap: break-word;">${e.get('field.name')}</td>
        <td style="width: 120px;word-wrap: break-word;">${e.get('field.description')}</td>
        <td>${e.get('field.settingValue')}</td>
    </tr>
    </thead>
    <tbody id="processTbody">
        <#list fields as field>
            <#assign
                isRequired = field.required???then(field.required?string("required",""),"")
                isEncrypted = field.encrypted???then(field.encrypted?string("encrypted",""),"")
            >
        <tr class="${isRequired!''}">
            <td class="padding10">
                <#if field.name??>
                    <label class="control-label">
                    ${(field.name???then(field.name,''))?html}
                    </label>
                </#if>
                <input type="hidden" name="fieldName" value="${field.name???then(field.name,'')}">
            </td>
            <td class="padding10">
            ${field.description???then(field.description,'')}
            </td>
            <td style="padding: 0;">
                <input type="text"
                       name="fieldValue"
                       class="fieldValue form-control ${isRequired!''}  ${isEncrypted!''} ${field.name!''}"
                       value="${(mapTemplate???then(mapTemplate[field.name]???then(mapTemplate[field.name],''),''))?html}"
                        <#if field.size??>
                           size="${field.size}" maxlength="${field.size}"
                        </#if>
                       style="border-radius: 0px; background-color: transparent;"/>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</#if>
