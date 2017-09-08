<script id="message-template" type="text/x-handlebars-template">
    <tr id="{{code}}">
        <td></td>
        <td>
            <img src="${rc.getContextPath()}/resources/images/global/bk_error.png" alt="xStra SERIES">
            <span data-code="{{code}}" class="error">{{code}}: {{message}}</span>
        </td>
    </tr>
</script>

<#if !(messages?? && (messages?size > 0))>
  <#assign hide = "hidden">
</#if>
<div id="messages" class="messageList ${hide!""}">
    <div class="title">
        <div style="padding-top:10px">
            <b style="font-weight: bold">${e.get("information")}</b>
        </div>
        <div class="line"> </div>
    </div>

    <table class="message-table" style="margin: 10px 20px;">
        <tbody>
        <#if messages?? && (messages?size > 0)>
          <#list messages as message>
          <tr>
            <#if message.isError()>
                <td></td>
                <td>
                    <img src="${rc.getContextPath()}/resources/images/global/bk_error.png" alt="xStra SERIES">
                    <span data-code="${(message.getCode()!"")}" class="error">${message.getContent()}</span>
                </td>
            <#elseif message.isInfo()>
                <td></td>
                <td>
                    <img src="${rc.getContextPath()}/resources/images/global/bk_information.png" alt="xStra SERIES">
                    <span data-code="${(message.getCode()!"")}" class="info">${message.getContent()}</span>
                </td>
            <#else>
              <#if message.isChecked()>
                <#assign checked="checked">
              <#else>
                <#assign checked="">
              </#if>
                <td>
                    <input type="checkbox" name="messages[${message?index}].isCheckedString" ${checked}  value='true'">
                    <input type="hidden" name="messages[${message?index}].code" value="${message.getCode()}">
                </td>
                <td>
                    <img src="${rc.getContextPath()}/resources/images/global/bk_warning.png" alt="xStra SERIES">
                    <span data-code="${(message.getCode()!"")}" class="warning">${message.getContent()}</span>
                </td>
            </#if>
          </tr>
          </#list>
        </#if>
        </tbody>
    </table>
</div>

