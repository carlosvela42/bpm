<link rel="stylesheet" media="screen"
      href="${rc.getContextPath()}/resources/css/templatebusiness.css" />
<#assign imageList>
    <@component.removePanel></@component.removePanel>
</#assign>

<@standard.standardPage title=e.get('templateBusiness.indexWaitSearchScreen') contentFooter=contentFooter script=script>
<div class="board-wrapper">
    <#include "imageDisplayLeft.ftl">
    <div class="board-split"></div>
    <#include "imageDisplayRight.ftl">
</div>
<div class="clear_fix" style="height: 10px"></div>
    <#include "imageDisplayButton.ftl">
</@standard.standardPage>