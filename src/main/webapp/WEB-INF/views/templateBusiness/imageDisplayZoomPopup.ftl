<div class="createline" style="margin-top: 30px;"></div>
<div id="displayMain" style="margin-top:20px; width: 100%; height:350px; padding: 10px 10px; border: 1px solid;">
    <img src="${rc.getContextPath()}/resources/images/imageViewer.png" width="100%" height="100%">
</div>
<!--   BUtton   -->
<table style="width: 100%; margin-top: 5px;">
    <tbody>
    <tr style="height: 30px; border-bottom:none;">
        <td class="text">
            <button align="left" class="btn btn-default btn_cancel_template">${e.get('templateBusiness.return')}</button>
        </td>
        <td class="text" align="right">
            <button class="btn btn-default btn_cancel_template">${e.get('templateBusiness.imageSwitching')}</button>
            <button class="btn btn-default btn_save_template " data-target="#addFieldModal">${e.get('templateBusiness.saveAnnotations')}
            </button>
        </td>
    </tr>
    </tbody>
</table>
