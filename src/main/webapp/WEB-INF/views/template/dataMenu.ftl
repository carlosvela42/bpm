<@standard.standardPage title="TEMPLATELIST" imageLink="data">
<form object="templateTypes" method="POST">
	<table>
		<tr>
			<th>データモデルメニュー</th>
		</tr>
		<#if templateTypes??> 
            <#list templateTypes as templateType>
             <tr>
                 <td><a id="templateType${templateType?index}"
                        href="${rc.getContextPath()}/template?templateType=${templateType}">${templateType}</a></td>
             </tr>
             </#list>
         </#if>
    </table>
</form>
</@standard.standardPage>
