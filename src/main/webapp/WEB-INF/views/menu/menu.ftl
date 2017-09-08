<#if Session.menuStructures??>
    <#assign menuStructures=Session.menuStructures>
    <div id="menu">
        <#list menuStructures as menuStructure>

            <ul>
                <li><a href="#">${menuStructure.getMenuParentName()}</a>
                    <ul class="sub-menu">
                        <#list menuStructure.mgrMenus as mgrMenu>
                            <li><a href="${rc.getContextPath()}/${mgrMenu.getMenuInformation().getUrl()}">${mgrMenu.getFunctionName()}</a></li>
                        </#list>
                    </ul>
                </li>

            </ul>
        </#list>
    </div>
</#if>