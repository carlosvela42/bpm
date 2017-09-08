<nav class="global">
    <div class="navigation_btn"><img src="${rc.getContextPath()}/resources/images/global/btn.png" alt="btn" width="60"
                          height="60"
    /></div>
    <ul>

                   <li class="icon_01">
              <div class="name"><@spring.message code='imageViewer.tool'/></div>
              <ul>
                <li class="icon_01_01" id="select"><@spring.message code='tool.select'/></li>
                <li class="icon_01_02"><@spring.message code='tool.printing'/>
                  <ul>
                    <li id="print"><@spring.message code='printing.printAll'/></li>
                    <li id="print0"><@spring.message code='printing.printNot'/></li>
                  </ul>
                </li>
                <li class="icon_01_03" id="subImage"><@spring.message code='tool.subImageViewer'/></li>
              </ul>
            </li>
            <li class="icon_02">
              <div class="name"><@spring.message code='imageViewer.zoom'/></div>
              <ul>
                <li class="icon_02_01"><@spring.message code='imageViewer.zoom'/>
                  <ul>
                    <li id="zoomFullPage"><@spring.message code='zoom.zoomFull'/></li>
                    <li id="zoomFullWidth"><@spring.message code='zoom.zoomWidth'/></li>
                    <li id="zoom200"><@spring.message code='zoom.200'/></li>
                    <li id="zoom100"><@spring.message code='zoom.100'/></li>
                    <li id="zoom75"><@spring.message code='zoom.75'/></li>
                    <li id="zoom50"><@spring.message code='zoom.50'/></li>
                      <li class="inputHover"><input type="number" id="zoomInput" value="100"></li>
                  </ul>
                </li>
                <li class="icon_02_02" id="zoomin"><@spring.message code='zoom.zoomIn'/></li>
                <li class="icon_02_03" id="zoomout"><@spring.message code='zoom.zoomOut'/></li>
              </ul>
            </li>
            <li class="icon_03">
              <div class="name"><@spring.message code='imageViewer.annotation'/></div>
              <ul>
                <li class="icon_03_01"><@spring.message code='annotation.draw'/>
                  <ul>
                    <li id="line" class="icon_03_01_01"><a href="#"><@spring.message code='draw.line'/></a></li>
                    <li id="rectangle" class="icon_03_01_02"><a href="#"><@spring.message code='draw.rectangle'/></a></li>
                    <li id="text" class="icon_03_01_03"><a href="#"><@spring.message code='draw.text'/></a></li>
                    <li id="highlight" class="icon_03_01_04"><a href="#"><@spring.message code='draw.highlight'/></a></li>
                    <li id="comment" class="icon_03_01_05"><a href="#"><@spring.message code='draw.comment'/></a></li>
                  </ul>
                </li>
                <li class="link icon_03_02" id="properties"><a href="#"><@spring.message code='annotation.property'/></a></li>
                <li class="link icon_03_03" id="btnGrayscale"><a href="#"><@spring.message code='annotation.grayScale'/></a></li>
                <li class="link icon_03_04" id="btnLayer"><a href="#"><@spring.message code='annotation.layer'/></a></li>
              </ul>
            </li>
            <li class="icon_04">
              <div class="name"><@spring.message code='imageViewer.edit'/></div>
              <ul>
                <li class="icon_04_01" id="cut"><@spring.message code='edit.cut'/></li>
                <li class="icon_04_02" id="copy"><@spring.message code='edit.copy'/></li>
                <li class="icon_04_03" id="paste"><@spring.message code='edit.paste'/></li>
                <li class="icon_04_04" id="rotateC"><@spring.message code='edit.rotateCounter'/></li>
                <li class="icon_04_05" id="rotate"><@spring.message code='edit.rotate'/></li>
              </ul>
            </li>
            <li class="icon_05">
              <div class="name"><@spring.message code='imageViewer.display'/></div>
              <ul>
                <li class="icon_05_01" id="first"><@spring.message code='display.first'/></li>
                <li class="icon_05_02" id="previous"><@spring.message code='display.previous'/></li>
                <li class="icon_05_03" id="next"><@spring.message code='display.next'/></li>
                <li class="icon_05_04" id="last"><@spring.message code='display.last'/></li>
              </ul>
            </li>
            <li class="icon_06">
              <div class="name"><@spring.message code='imageViewer.page'/></div>
              <ul>
                <li id="chooseImage1" class="inputHover" style="padding-right: 70px">
                    <input type="text" name="pageInput"
                           id="pageInput">
                  <ul id="chooseImage">

                  </ul>
                </li>
              </ul>
            </li>
    </ul>
</nav>

<!-- Modal -->
<div class="modal fade" id="myModalText" role="dialog" z-index='302' data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header imageViewerPopup">

                <h4 class="modal-title"><@spring.message code='imageViewer.textProperty'/></h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <textarea class="form-control" rows="5" id="commentText" placeholder="テキスト"></textarea>
                </div>
                <div class="form-group">
                    <div class="col-md-4">
                    <@spring.message code='textProperty.lineColor'/></div><div class="col-md-8"><select id="color"></select></div>
                </div>
                <div class="form-group"><div class="col-md-4">
                <@spring.message code='textProperty.fill'/></div><div class="col-md-8"><select id="fill"><option
                            value="rgba(0, 0, 0, 0)">Transparent</option>
                        </select></div>
                </div>
                <div class="form-group"><div class="col-md-4">
                <@spring.message code='textProperty.border'/></div><div class="col-md-8"><select id="border"><option
                            value="1px solid rgba(0, 0, 0, 0)">Transparent</option>
                        </select></div>
                </div>
                <div class="form-group"><div class="col-md-4">
                <@spring.message code='textProperty.font'/></div><div class="col-md-8"><select id="font"></select></div>
                </div>
                <div class="form-group"><div class="col-md-4">
                <@spring.message code='textProperty.size'/></div><div class="col-md-8"><select
                        id="fontSize"></select></div>
                </div>
                <div class="form-group"><div class="col-md-4">
                <@spring.message code='textProperty.style'/></div><div class="col-md-8"><select id="fontStyle"><option
                        value="normal">Regular</option>
                        <option value="bold">Bold</option>
                        <option value="italic">Italic</option>
                        <option value="boldItalic">Bold Italic</option></select></div>
                </div>
            </div>
            <div class="modalTextProperties">
                <button type="button" class="btn btn-default btn_cancel_imageViewer btn_popup" data-dismiss="modal"
                        id="cancelTextProperties"><@spring.message code='button.cancel'/></button>
                <button type="button" class="btn btn-default btn_save_imageViewer btn_popup" data-dismiss="modal"
                        id="textProperties"><@spring.message code='button.confirm'/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModalComment" role="dialog" z-index='302' data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header imageViewerPopup">

                <h4 class="modal-title"><@spring.message code='imageViewer.commentProperty'/></h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <textarea class="form-control" rows="5" id="commentTxtArea" placeholder="テキスト"></textarea>
                </div>
                <div class="form-group">
                <@spring.message code='commentProperty.label'/><input type="text" id="tbComment" value="" style="width: 60%;float: right">
                </div>
            </div>
            <div class="modalCommentProperties">

                <button type="button" class="btn btn-default btn_cancel_imageViewer btn_popup" data-dismiss="modal"
                        id="cancelCommentProperties"><@spring.message code='button.cancel'/></button>
                <button type="button" class="btn btn-default btn_save_imageViewer btn_popup" data-dismiss="modal"
                        id="commentProperties"><@spring.message code='button.confirm'/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModalLine" role="dialog" z-index='302' data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header imageViewerPopup">

                <h4 class="modal-title"><@spring.message code='imageViewer.annotationProperty'/></h4>
            </div>
            <div class="modal-body">
                <div class="col-md-9">
                <@spring.message code='annotationProperty.color'/>
                    <ul class="nav nav-tabs nav-tabs-iv" style="">
                        <li><a data-toggle="tab" href="#home" id="li1"><@spring.message code='draw.line'/></a></li>
                        <li><a data-toggle="tab" href="#menu1" id="li2" style="margin-left: 50px">
                        <@spring.message code='draw.rectangle'/></a></li>
                        <li><a data-toggle="tab" href="#menu2" id="li3" style="margin-left: 50px">
                        <@spring.message code='draw.highlight'/>
                        </a></li>
                    </ul>
                    <div class="tab-content">
                        <div id="home" class="tab-pane fade in active">

                        </div>
                        <div id="menu1" class="tab-pane fade">

                        </div>
                        <div id="menu2" class="tab-pane fade">

                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                <@spring.message code='annotationProperty.lineSize'/><br /> <input class="numberProperties" type="number"
                                                                            min="1" max="100" value="1"
                        id="width">

                </div>

            </div>
            <div class="modalProperties">
                <button type="button" class="btn btn-default btn_cancel_imageViewer btn_popup" data-dismiss="modal"
                        id="cancelProperties"><@spring.message code='button.cancel'/></button>
                <button type="button" class="btn btn-default btn_save_imageViewer btn_popup" data-dismiss="modal"
                        id="okProperties"><@spring.message code='button.confirm'/></button>
            </div>

        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal2" role="dialog" z-index='302' data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header imageViewerPopup">

                <h4 class="modal-title imageViewerTitle"><@spring.message code='annotation.grayScale'/></h4>
            </div>
            <div class="modal-body">
                <div>
                    <label class="imageViewerLabel"><@spring.message code='grayScale.contrast'/></label> <input type="text"
                                                                                               name="contrastValue"
                        id="contrastValue" style="float: right; text-align: center;" class="imageViewerInput">
                    <input type="range" id="controls1" min="-100" max="100" class="rangeImageViewer">
                </div>
                <div>
                    <label class="imageViewerLabel"><@spring.message code='grayScale.brightness'/></label> <input type="text"
                                                                                                         name="brightnessValue"
                        id="brightnessValue" style="float: right; text-align: center;" class="imageViewerInput">
                    <input type="range" id="controls" min="-100" max="100" class="rangeImageViewer">
                </div>
            </div>
            <div class="modal-footer" style="border: none">
                <label style="display: block; text-align: left;" class="imageViewerLabel"><input
                    type="checkbox" id="cbox1" value="grayscale"> <@spring.message code='annotation.grayScale'/></label>
                <button type="button" class="btn btn-default btn_cancel_imageViewer btn_popup" data-dismiss="modal"
                        id="cancelGrayscale"><@spring.message code='button.cancel'/></button>
                <button type="button" class="btn btn-default btn_save_imageViewer btn_popup" data-dismiss="modal"
                    id="okGrayscale" style="margin-top: 0px"><@spring.message code='button.confirm'/></button>

            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal3" role="dialog" z-index='302' style="top: 300px" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog content_main">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header imageViewerPopup">

                <h4 class="modal-title imageViewerTitle"><@spring.message code='imageViewer.layerPopup'/></h4>
            </div>
            <div class="modal-body" id="layerBody">
                <div class="form-inline">
                    <div class="form-group ">
                    <@spring.message code='layer.name'/>
                            <div class="form-group"><input type="text" id="layerName"
                                   name="layerName" class="form-control" style="width: 150px"></div>



                    <@spring.message code='layer.active'/><div class="form-group"><input type="text"
                                id="layerActive" name="layerActive" class="form-control" style="background-color:
                                #fff;width: 150px"
                                disabled></div>
                        <button type="button" class="btn btn-default btn_imageViewer btn_popup"
                                id="addLayer"><@spring.message code='layer.add'/></button>
                        <button type="button" class="btn btn-default btn_imageViewer btn_popup"
                                id="removeLayer"><@spring.message code='layer.remove'/></button>
                        <button type="button" class="btn btn-default btn_imageViewer btn_popup"
                                id="renameLayer"><@spring.message code='layer.rename'/></button>
                        <button type="button" class="btn btn-default btn_imageViewer btn_popup"
                                id="activeLayer"><@spring.message code='layer.activation'/></button>
                        <button type="button" class="btn btn-default btn_imageViewer btn_popup"
                                id="displayLayer"><@spring.message code='layer.display'/></button>

                    </div>
                </div>

                    <table class="table table-bordered tableImageViewer">
                        <thead>
                        <tr>
                            <th style="background-color: #F2F2F2;text-align: center"><@spring.message code='layer.name'/></th>
                            <th style="background-color: #D9D9D9;text-align: center""><@spring.message code='layer.user'/></th>
                            <th style="background-color: #F2F2F2;text-align: center""><@spring.message code='layer.display'/></th>
                            <th style="background-color: #D9D9D9;text-align: center"">
                            <@spring.message code='layer.item'/></th>
                            <th style="background-color: #F2F2F2;text-align: center"">
                            <@spring.message code='layer.modified'/></th>
                            <th style="background-color: #D9D9D9;text-align: center""><@spring.message code='layer.created'/></th></th>
                            <th style="background-color: #F2F2F2;text-align: center""><@spring.message code='layer.activation'/></th>
                            <th style="background-color: #D9D9D9;text-align: center""><@spring.message code='layer.templateName'/></th>
                        </tr>
                        </thead>
                        <tbody id="newLayerBody">

                        </tbody>
                    </table>
                <div class=" text-center">
                <button type="button" class="btn btn-default btn_cancel_imageViewer btn_popup"
                        data-dismiss="modal"><@spring.message code='button.cancel'/></button>
                <button type="button" class="btn btn-default btn_save_imageViewer btn_popup" data-dismiss="modal"
                        id="okLayer"><@spring.message code='button.confirm'/></button></div>
            </div>

        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModalConfirm" role="dialog" z-index='302' data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header imageViewerPopup">

                <h4 class="modal-title imageViewerTitle">Delete layer?</h4>
            </div>
            <div class="modal-body" id="layerBody">
                <div class="form-inline">
                    <div class="form-group ">

                        Layer have items. Delete anyway?

                    </div>
                </div>


                <div class=" text-center">
                    <button type="button" class="btn btn-default btn_cancel_imageViewer btn_popup"
                            data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-default btn_save_imageViewer btn_popup" data-dismiss="modal"
                            id="okLayerDelete">Yes</button></div>
            </div>

        </div>
    </div>
</div>
<script id="layer-row-template" type="text/x-handlebars-template">
    <tr>
        <td>
            <div id="layer{{countLayer}}">
                <input type="radio" name="radioLayerName" value="{{layerName}}" style="margin-top:
                0px" {{#if layerActive}} checked{{/if}}/>{{layerName}}</div>
        </td>
        <td>{{layerOwner}}</td>
        <td style="text-align: center;">
            <input type="checkbox" name="display" value="{{layerName}}" {{#if layerDisplay}} checked{{/if}}/>
        </td>
        <td style="text-align: right;">{{layerItems}}</td>
        <td>{{outputModified}}</td>
        <td>{{outputCreated}}</td>
        <td style="text-align: center;">
            <input type="radio" name="active" value="{{layerName}}" {{#if layerActive}} checked{{/if}}/>
        </td>
        <td>{{templateName}}</td>
    </tr>

</script>
<script id="color-template" type="text/x-handlebars-template">
    {{#each colors}}
<input type="radio" name="color" id="{{this}}" value="{{this}}"/>
<label for="{{this}}" style="margin-right: 0px"><span class="c{{this}}"></span></label>
    {{/each}}
</script>
<script id="fill-template" type="text/x-handlebars-template">
    {{#each colors}}
    <input type="radio" name="fill" id="1{{this}}" value="{{this}}"/>
    <label for="1{{this}}" style="margin-right: 0px"><span class="c{{this}}"></span></label>
    {{/each}}
</script>
<script id="highlight-template" type="text/x-handlebars-template">
    {{#each colors}}
    <input type="radio" name="highlight" id="2{{this}}" value="{{this}}"/>
    <label for="2{{this}}" style="margin-right: 0px"><span class="c{{this}}"></span></label>
    {{/each}}
</script>
<script id="font-size-template" type="text/x-handlebars-template">
    {{#each fonts}}
    <option value="{{this}}px">{{this}}</option>
    {{/each}}
</script>
<script id="pen-color-template" type="text/x-handlebars-template">
    {{#each penColors}}
    <option value="{{this}}">{{this}}</option>
    {{/each}}
</script>
<script id="font-family-template" type="text/x-handlebars-template">
    {{#each fontFamily}}
    <option value="{{this}}">{{this}}</option>
    {{/each}}
</script>
