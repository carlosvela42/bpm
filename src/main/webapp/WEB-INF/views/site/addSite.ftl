<#assign contentFooter>
    <@component.detailUpdatePanel object="site" formId="siteForm"></@component.detailUpdatePanel>
</#assign>

<#assign script>
<script src="${rc.getContextPath()}/resources/js/site.js"></script>
</#assign>

<@standard.standardPage title=e.get("site.edit") contentFooter=contentFooter script=script imageLink="process">
<br>
	<#assign isPersisted = (siteForm.lastUpdateTime??)>
	<#assign formAction = isPersisted?then('updateOne', 'insertOne')>
	<#assign readonly = (formAction=='updateOne')?then('readonly',"")>

<form  id="siteForm" action="${rc.getContextPath()}/site/${formAction}" object="siteForm"
	method="post" class="">
	 <#include "../common/messages.ftl">  
	<input type="hidden" id="directoryIds" name="directoryIds" value="${(directoryIds!"")}">
	<input type="hidden" id="disableSaveButton" name="disableSaveButton" value="${(disableSaveButton???then(disableSaveButton?c,""))}">
	<div>
		<b id="message" style="color: red;"></b>
	</div>
 <div class="board-wrapper">
	 <div class="board board-half">
	   <table class="table_form" style="width: 50%">
		  <tr>
			<td width="100px">${e.get("site.id")}</td>
			<td>
			     <#if isPersisted>
			        ${siteForm.siteId!""}
                 </#if>
                 <input type="hidden" name="siteId" value="${siteForm.siteId!""}"/>
             </td>
		  </tr>

	   </table>
	<div><input type="hidden" name="lastUpdateTime" value="${siteForm.lastUpdateTime!""}"/></div>
	<br/>
	<label style="padding-left: 5px;font-weight: bold;margin-bottom: 15px;margin-top: 5px">${e.get("directory.list")}</label>

	   <table class="clientSearch table_list" >
           <colgroup>
               <col style="width: 3%" />
               <col style="width: 12%" />
               <col style="width: 22%" />
               <col style="width: 13%" />
               <col style="width: 25%" />
               <col style="width: 25%" />
           </colgroup>
	   <thead class="table_header">
			<tr style="border-bottom: 1pt solid #000;">

				<td colspan="2">${e.get("site.datadirectoryid")}</td>
				<td>${e.get("site.folderpath")}</td>
				<td>${e.get("site.createnew")}</td>
				<td>${e.get("site.secureddiskspace")} (MB)</td>
				<td>${e.get("site.diskcapacity")} (MB)</td>
			</tr>
		</thead>
		<tbody class="table_body" id="siteTbody">
		<#if siteForm??>
			<#if siteForm.directories??>
	                <#list siteForm.directories as directory>
	                <tr id="row${directory?index}">
	                     <td style="width: 3%"><input type="checkbox" class="deleteCheckBox"
													  id="chooseRow${directory?index}" name="ChooseRow" value="${directory.dataDirectoryId}" ${directory.checked?string("checked","") }></td>
	                     <td style="text-align: right;padding-right: 10px" id="dataDirectoryId${directory?index}"
							 value="${directory
						 .dataDirectoryId}">${directory
						 .dataDirectoryId}</td>
	                     <td id="folderPath${directory?index}" value="${directory.folderPath}">${directory.folderPath}</td>
	                     <td id="newCreateFile${directory?index}" value="${directory.newCreateFile}">

							 <#if directory.newCreateFile== 1>
						 Y
						 <#else>
							 N
						 </#if>
						 </td>
	                     <td id="reservedDiskVolSize${directory?index}" value="${directory.reservedDiskVolSize}">${directory.reservedDiskVolSize}</td>
	                     <td id="diskVolSize${directory?index}" value="${directory.diskVolSize}"->${directory.diskVolSize}</td>
	                </tr>
	                </#list>
	            </#if>
	      </#if>
	      </tbody>
        </table>		
    </div>
	</table>  
  </div>
</form>
</@standard.standardPage>
