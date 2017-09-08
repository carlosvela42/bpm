<#--<#assign contentFooter>-->
    <#--<@component.updateWorkItemPanel object="workItem" formId="workItemForm"></@component.updateWorkItemPanel>-->
<#--</#assign>-->

<#assign script>
<link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/resources/css/pretty-json.css" />
<script type="text/javascript" src="${rc.getContextPath()}/resources/js/lib/underscore-min.js" ></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/js/lib/backbone-min.js" ></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/js/lib/pretty-json-min.js" ></script>
<style>
  .full-width-scroll {
  width: 100%;
  overflow-y: hidden;
  height: auto !important;
  }
    body {
        overflow: auto;
    }
</style>
<script>
  window.sessionJson = JSON.parse('${sessionJson}');

  $(function () {
    var node = new PrettyJSON.view.Node({
    el:$('#elem'),
    data: window.sessionJson
    });

      node.expandAll();
  })
</script>
</#assign>

<@standard.noFooterLayout title=e.get('Session') script=script imageLink="tool">
<div id="elem">

</div>
</@standard.noFooterLayout>