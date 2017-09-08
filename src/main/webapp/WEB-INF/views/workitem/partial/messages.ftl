<div id="messages">
  <#if messages??>
      <ul class="list-group">
        <#list messages as message>
            <li class="list-group-item list-group-item-danger" data-code="${(message.getCode()!"")}">
            ${message.getContent()}
            </li>
        </#list>
      </ul>
  </#if>
</div>
