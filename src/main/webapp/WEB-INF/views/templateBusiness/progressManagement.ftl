<link rel="stylesheet" media="screen"
      href="${rc.getContextPath()}/resources/css/templatebusiness.css" />

<@standard.standardPage title=e.get('templateBusiness.processManagementScreen') contentFooter=contentFooter script=script>

<div class="board-wrapper">
    <div class="board board-half">
        <#include "../common/messages.ftl">
        <span class="span_lable">${e.get('templateBusiness.aggregateResults')}</span>
        <div class="createline"></div>
        <div style="width: 500px; margin-bottom: 15px;">
            <table class="clientSearch table_list_template_business">
                <thead>
                <tr class="table_header">
                    <td style="width: 200px;" class="text_center">${e.get('templateBusiness.index')}</td>
                    <td style="width: 200px;" class="text_center">${e.get('templateBusiness.caseManager')}</td>
                    <td style="width: 200px;" class="text_center">${e.get('templateBusiness.scanDateAndTime')}</td>
                </tr>
                </thead>
                <tbody id="profileTbody" class="table_body">
                <tr>
                    <td class="text_center">5</td>
                    <td class="text_center">4</td>
                    <td class="text_center">1</td>
                </tr>
                </tbody>
            </table>
        </div>
        <span class="span_lable">${e.get('templateBusiness.indexWaitSearchScreen')}</span>
        <div class="createline"></div>
        <div style="width: 800px; max-height:150px; overflow: auto; margin-bottom: 15px;">
            <table class="clientSearch table_list_template_business">
                <thead>
                <tr class="table_header">
                    <td class="text_center">${e.get('templateBusiness.index')}</td>
                    <td class="text_center">${e.get('templateBusiness.caseManager')}</td>
                    <td class="text_center">${e.get('templateBusiness.scanDateAndTime')}</td>
                    <td style="width: 100px;" class="text_center">${e.get('templateBusiness.pageNumber')}</td>
                </tr>
                <tr class="condition">
                    <td><input class="icon icon_search" type="text" onkeyup="filter()" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" onkeyup="filter()" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" onkeyup="filter()" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" onkeyup="filter()" placeholder="search..."></td>
                </tr>
                </thead>
                <tbody id="profileTbody" class="table_body" style="overflow: auto;height: 100px;">
                <tr>
                    <td class="text_center">1</td>
                    <td class="text_center">48G7-PC-02YBPM01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">10</td>
                </tr>
                <tr>
                    <td class="text_center">2</td>
                    <td class="text_center">48G7-PC-02YBPM01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">15</td>
                </tr>
                <tr>
                    <td class="text_center">3</td>
                    <td class="text_center">48G7-PC-02YBPM01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">8</td>
                </tr>
                <tr>
                    <td class="text_center">4</td>
                    <td class="text_center">48G7-PC-02YBPM01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">14</td>
                </tr>
                <tr>
                    <td class="text_center">5</td>
                    <td class="text_center">48G7-PC-02YBPM01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">19</td>
                </tr>
                </tbody>
            </table>
        </div>

        <span class="span_lable">${e.get('templateBusiness.imageSearchScreen')}</span>
        <div class="createline"></div>
        <div style="max-height:150px; overflow: auto;">
            <table class="clientSearch table_list_template_business">
                <thead>
                <tr class="table_header">
                    <td class="text_center">${e.get('templateBusiness.index')}</td>
                    <td class="text_center">${e.get('templateBusiness.receiptNumber')}</td>
                    <td style="width: 100px;" class="text_center">${e.get('templateBusiness.repeated')}</td>
                    <td class="text_center">${e.get('templateBusiness.caseManager')}</td>
                    <td class="text_center">${e.get('templateBusiness.scanDateAndTime')}</td>
                    <td class="text_center">${e.get('templateBusiness.bookClass')}</td>
                    <td style="width: 100px;" class="text_center">${e.get('templateBusiness.status')}</td>
                    <td style="width: 100px;" class="text_center">${e.get('templateBusiness.pageIndex')}</td>
                </tr>
                <tr class="condition">
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                    <td><input class="icon icon_search" type="text" placeholder="search..."></td>
                </tr>
                </thead>
                <tbody id="profileTbody" class="table_body">
                <tr>
                    <td class="text_center">1</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">1</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">10</td>
                </tr>
                <tr>
                    <td class="text_center">2</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">2</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">8</td>
                </tr>
                <tr>
                    <td class="text_center">3</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">3</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">9</td>
                </tr>
                <tr>
                    <td class="text_center">4</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">1</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">8</td>
                </tr>
                <tr>
                    <td class="text_center">5</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">1</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">7</td>
                </tr>
                <tr>
                    <td class="text_center">6</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">1</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">11</td>
                </tr>
                <tr>
                    <td class="text_center">7</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">1</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">12</td>
                </tr>
                <tr>
                    <td class="text_center">8</td>
                    <td class="text_center">12-34567890-1-23</td>
                    <td class="text_center">1</td>
                    <td class="text_center">nissho01</td>
                    <td class="text_center">2017/04/01 10:15:02</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">案件</td>
                    <td class="text_center">9</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</@standard.standardPage>