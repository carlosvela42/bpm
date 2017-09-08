<#assign contentFooter>
<@component.removePanel></@component.removePanel>
</#assign>

<#assign script>
  <script src="${rc.getContextPath()}/resources/js/profile.js"></script>
</#assign>

<@standard.standardPage title=e.get('profile.list') imageLink="user" contentFooter=contentFooter displayWorkspace=false
script=script>
     <#include "../common/messages.ftl">
<div class="board-wrapper">
    <div class="board board-half">
    <form action="${rc.getContextPath()}/profile/" id="searchClientForm" object="searchClientForm"  method="post">
            <table class="clientSearch table_list" >
                <colgroup>
                    <col style="width: 5%" />
                    <col style="width: 5%" />
                    <col style="width: 45%" />
                    <col style="width: 45%" />
                </colgroup>
               <thead>               
                    <tr class="table_header">
                        <td class=""><input type="checkbox" class="deleteAllCheckBox"/></td>
                        <td class="text_center">
                            <a href="${rc.getContextPath()}/profile/addNew" class="icon icon_add"></a>
                        </td>
                        <td>${e.get('profile.id')}</td>
                        <td>${e.get('profile.description')}</td>
                    </tr>
                    <tr class="condition" >
                        <td  colspan="3">
                            <input id="searchColumns[0]" name="searchColumns[0]"
                                   value="${(searchClientForm.searchColumns[0]!"")?html}"
                                 type="text" placeholder="search" class="searchInput" col="3"></td>
                        <td>
                            <input id="searchColumns[1]" name="searchColumns[1]"
                                   value="${(searchClientForm.searchColumns[1]!"")?html}"
                                 type="text" placeholder="search" class="searchInput" col="4">
                        </td>
                    </tr>
                </thead>
                <tbody id="profileTbody" class="table_body">
                <#if mgrProfiles??>
                    <#list mgrProfiles as mgrProfile>
                        <tr profileId="${mgrProfile.profileId}">
                             <td><input type="checkbox" class="deleteCheckBox" /></td>
                             <td class="text_center text_icon">
                                 <a class="icon icon_edit" href="${rc.getContextPath()}/profile/showDetail?profileId=${mgrProfile.profileId}"></a>
                             </td>
                            <td class="text">${mgrProfile.profileId!""}</a></td>
                            <td class="text"  colspan="2">${(mgrProfile.description!"")?html}</td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td  colspan="2"></td>
                    </tr>
                </#if>
              </tbody>
            </table>           
            </form>
    </div>
     <div class="board-split"></div>
</div>
  </@standard.standardPage>
