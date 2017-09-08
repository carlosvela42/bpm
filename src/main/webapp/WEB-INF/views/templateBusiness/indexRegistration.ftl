<link rel="stylesheet" media="screen"
      href="${rc.getContextPath()}/resources/css/templatebusiness.css" />
<#assign imageList>
  <@component.removePanel></@component.removePanel>
</#assign>

<@standard.standardPage title=e.get('templateBusiness.indexRegistrationScreen') contentFooter=contentFooter script=script>
<div class="board-wrapper">
  <#include "indexRegistrationLeft.ftl">
    <div class="board-split"></div>
  <#include "indexRegistrationRight.ftl">
</div>
<div class="clear_fix" style="height: 10px"></div>
  <#include "indexRegistrationButton.ftl">
</@standard.standardPage>