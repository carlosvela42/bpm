<span class="span_label">${e.get('templateBusiness.imageChange')}</span>
<div class="createline"></div>
<table>
    <tr>
        <td style="width: 150px; "> 　
            <span class="span_label">${e.get('templateBusiness.distributionSourceReceptonNumber')}</span>
        </td>
        <td>
            <input type="text" disabled="disabled"  calass="text_round_border" value=" 12-34567890-1-23">
        </td>
    </tr>
    <tr>
        <td style="padding-top: 10px; width: 150px;"> 　
            <span class="span_label">${e.get('templateBusiness.distributionImage')}</span>
        </td>
        <td>
            <img style="padding-top: 10px; padding-bottom: 10px;" src="/Earth/resources/images/imageViewer.png"
                 width="100%" height="250px">
        </td>
    </tr>
    <tr>
        <td style="width: 150px; "> 　
            <span class="span_label">${e.get('templateBusiness.distributionDestinationReceptionNumber')}</span>
        </td>
        <td>
            <input type="text" calass="text_round_border">
        </td>
    </tr>
</table>
<div style="text-align: right; margin-top: 5px;">
    <button class="btn btn-default btn_cancel_template">${e.get('templateBusiness.refineSearch')}</button>
    <button align="center" class="btn btn-default btn_save_template">${e.get('templateBusiness.imageChange')}</button>
</div>