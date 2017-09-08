<div style="float: left;">
    <a href="${rc.getContextPath()}/" class="button">Main Menu</a>
</div>
<div style="float: right;">
    <#if Session.userInfo??>
        <#assign userInfo =Session.userInfo>
        <label>${userInfo.userId}/${userInfo.userName}</label>
        <a href="${rc.getContextPath()}/logout" class="button">

        </a>
    </#if>
</div>