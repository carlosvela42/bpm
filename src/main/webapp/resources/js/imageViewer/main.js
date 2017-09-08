function getResource(url) {
    return window.baseUrl + "/" + url;
}

function initImageViewer() {
    // Check Browser support SVG or not.
    // $("#imageDrawing").width(SCREEN_SIZE_DEFAULT);
    // $("#imageDrawing").height(SCREEN_SIZE_DEFAULT);
    if (SVG.supported) {
        iv = new ImageViewer();
        iv.svg = new SVG('imageDrawing');
        iv.svg.on("mousedown", onMouseDown);
        iv.svg.on("mousemove", onMouseMove);
        iv.svg.on("mouseup", onMouseUp);
        // Tool bars onClick event.

        for (var item in toolbars) {
            $(toolbars[item]).click(onClick);
        }

        for (var annoType in annoTypes) {
            $(annoTypes[annoType]).click(onClick);
        }

        iv.svg.svgId = "#" + iv.svg.node.id;
        iv.svgStartLeft = $(iv.svg.svgId).position().left;
        iv.svgStartTop = $(iv.svg.svgId).position().top;

        if (!String.prototype.startsWith) {
            String.prototype.startsWith = function (searchString, position) {
                position = position || 0;
                return this.indexOf(searchString, position) === position;
            };
        }

        if (!String.prototype.endsWith) {
            String.prototype.endsWith = function(searchString, position) {
                var subjectString = this.toString();
                if (typeof position !== 'number' || !isFinite(position) || Math.floor(position) !== position || position > subjectString.length) {
                    position = subjectString.length;
                }
                position -= searchString.length;
                var lastIndex = subjectString.indexOf(searchString, position);
                return lastIndex !== -1 && lastIndex === position;
            };
        }

        var row = earth.buildHtml("#color-template", {colors:colors});
        $('#home').append(row);

        var rowFill = earth.buildHtml("#fill-template", {colors:colors});
        $('#menu1').append(rowFill);

        var rowHighlight = earth.buildHtml("#highlight-template", {colors:colors});
        $('#menu2').append(rowHighlight);

        var rowFontSize = earth.buildHtml("#font-size-template", {fonts:fonts});
        $('#fontSize').append(rowFontSize);

        var rowPenColor = earth.buildHtml("#pen-color-template", {penColors:penColors});
        $('#color').append(rowPenColor);
        $('#fill').append(rowPenColor);
        $('#border').append(rowPenColor);

        var rowFontFamily = earth.buildHtml("#font-family-template", {fontFamily:family});
        $('#font').append(rowFontFamily);

        $(textExample.INPUTCOLOR1).change(function () {

            $(textExample.COLOR).val($(textExample.COLORSELECT).text());
        });

        $('#layerName').on('input',function () {
            if ($('#layerName').val() != "") {
            for (var k = 0; k < $('#newLayerBody tr').length; k++) {
                if($('#newLayerBody tr:eq('+k+') td:eq(0)').text().trim() == $('#layerName').val().trim()){
                    btnWhenAddLayer();
                    return;
                }
            }
            $(toolbars.BTN_ADDLAYER).css('pointer-events', 'auto');
            $(toolbars.BTN_REMOVELAYER).css('pointer-events', 'none');
            $(toolbars.BTN_RENAMELAYER).css('pointer-events', 'auto');
            $(toolbars.BTN_ACTIVELAYER).css('pointer-events', 'none');
                $(toolbars.BTN_DISPLAYLAYER).css('pointer-events', 'none');
        }});

        $('#zoomInput').keypress(function (e) {
            if (e.which == 13) {
                var valZoom = $('#zoomInput').val();
                if (valZoom < 10) currentScale = 10 / 100;
                else if (valZoom > 400) currentScale =4;
                else {
                    currentScale = valZoom / 100;

                }
                smartTransform(iv.svg.svgId, currentRotation);
            }
        });

        $('#contrastValue').bind('input',function () {
                var valContrast = $('#contrastValue').val();
                if (valContrast < -100) alert('small value');
                else if (valContrast > 100) alert('big value');
                else {
                    $('#controls1').val(valContrast);
                    changeGrayScale(valContrast,$('#brightnessValue').val(),$("#cbox1").is(':checked'));
                }
        });

        $('#brightnessValue').bind('input',function () {
                var valBrgightness = $('#brightnessValue').val();
                if (valBrgightness < -100) alert('small value');
                else if (valBrgightness > 100) alert('big value');
                else {
                    $('#controls').val(valBrgightness);
                    changeGrayScale($('#contrastValue').val(),valBrgightness,$("#cbox1").is(':checked'));
                }

        });

        // Load list of documents and display them on the image viewer screen.
        loadDocument(getResource("WS/getImagesByFolderItem?workspaceId=" + window.workspaceId + "&workItemId=" + window.workItemId + "&folderItemNo=" + window.folderItemNo + "&documentNo=" + window.documentNo + "&token=" + window.token), function (data, res) {
            if (res == result.SUCCESS) {
                HEIGHT_DEFAULT = $('body').height()-$('header').height();
                $('#imageDrawing').height(HEIGHT_DEFAULT);
                iv.documents = data.documentMap;
                iv.indexCurrent = data.currentDocument;
                changeDocument();
                disabledMode();
            }
        });
    } else {
        alert('SVG not supported');
        return;
    }
}

function btnWhenAddLayer(){
    $(toolbars.BTN_ADDLAYER).css('pointer-events', 'none');
    $(toolbars.BTN_REMOVELAYER).css('pointer-events', 'auto');
    $(toolbars.BTN_DISPLAYLAYER).css('pointer-events', 'auto');
    $(toolbars.BTN_RENAMELAYER).css('pointer-events', 'none');
    if($('#layerName').val()!=$('#layerActive').val())
    {
        $(toolbars.BTN_ACTIVELAYER).css('pointer-events', 'auto');
    }
    else $(toolbars.BTN_ACTIVELAYER).css('pointer-events', 'none');
}

function disabledMode(){
    if(window.location.href.slice(-7)=="&mode=1"){
        for (var item in hideMenu) {
        $(hideMenu[item]).css('display','none');
        }
    }
}

function changeDocument() {
    var values = Object.keys(iv.documents).map(function (e) {
        return iv.documents[e]
    })
    iv.currentDocument = values[iv.indexCurrent];
    window.documentNo = iv.currentDocument.documentNo;
    iv.documentType = iv.currentDocument.documentType;
    switch (iv.documentType) {
        case documentType.IMAGE:
            iv.documentPath = getResource("WS/getDocumentBinary?workspaceId=" + window.workspaceId + "&workItemId=" + window.workItemId + "&folderItemNo=" + window.folderItemNo + "&documentNo=" + window.documentNo + "&token=" + window.token);
            displayImage();
            break;
        case documentType.PDF:
            convertPdfToImage(getResource("WS/getDocumentBinary?workspaceId=" + window.workspaceId + "&workItemId=" + window.workItemId + "&folderItemNo=" + window.folderItemNo + "&documentNo=" + window.documentNo + "&token=" + window.token), iv.currentDocument.pageCount);
            break;
        case documentType.TIFF:
            convertTiffToImage(getResource("WS/getDocumentBinary?workspaceId=" + window.workspaceId + "&workItemId=" + window.workItemId + "&folderItemNo=" + window.folderItemNo + "&documentNo=" + window.documentNo + "&token=" + window.token), iv.currentDocument.pageCount);
            break;
    }
}

function displayImage() {
    if (iv.documentPath != null) {
        iv.svg.image(iv.documentPath).loaded(
            function (loader) {
                var size = getImageSizeDefault(loader.width, loader.height);
                iv.imgWidth = size[0];
                iv.imgHeight = size[1];
                // iv.imgWidth = loader.width;
                // iv.imgHeight = loader.height;
                this.width(iv.imgWidth);
                this.height(iv.imgHeight);
                iv.svg.width(iv.imgWidth);
                iv.svg.height(iv.imgHeight);
                // $(toolbars.BTN_ZOOMFP).trigger('click');
                iv.imgId = this.node.id;
                load();
                switchImage();
                iv.imgNode = this;

                zoomFullPage();
            }
        );
    }
}

function getImageSizeDefault(imgWidth, imgHeight) {
    var tempSize = Math.max(parseInt(imgWidth), parseInt(imgHeight));
    if (tempSize > parseInt(HEIGHT_DEFAULT)) {
        var rate =  (parseInt(HEIGHT_DEFAULT) - getScrollBarWidth()) / tempSize;
        return [rate * parseInt(imgWidth), rate * parseInt(imgHeight)];
    }

    return [imgWidth, imgHeight];
}

function getImageSizeDefault1(imgWidth, imgHeight) {
    var intImgWidth = parseInt(imgWidth);
    var intImgHeight = parseInt(imgHeight);
    var intDefaultWidth = parseInt(WIDTH_DEFAULT);
    var intDefaultHeight = parseInt(HEIGHT_DEFAULT);
    var dW = (intDefaultWidth < intImgWidth) ? (intImgWidth - intDefaultWidth) : 0;
    var dH = (intDefaultHeight < intImgHeight) ? (intImgHeight - intDefaultHeight) : 0;

    var dMax = Math.max(dW, dH);

    return [intImgWidth - dMax, imgHeight - dMax];
}

function saveAnnoToLayer() {
    unSelectMultipleSvg();
    for (var j = 0; j < iv.currentDocument.layers.length; j++) {
        var annoValue = "<svg>";
        $('.'+iv.currentDocument.layers[j].layerName).each(function(){

            if ( $(this).css('display') == 'none')
            {
                $(this).remove();

            }
        });
            annoValue += $('<div>').append($('.' + iv.currentDocument.layers[j].layerName).clone()).html();

            if(iv.currentDocument.layers[j].action == null){
        iv.currentDocument.layers[j].action = 1;
            }
        iv.currentDocument.layers[j].annotations = annoValue + "</svg>";
    }
}

function callSaveApi(imageId) {
    saveAnnoToLayer();
    $(iv.svg.svgId).empty();
    resetLayer();
    saveDocument(getResource("WS/saveImage"), window.token, window.workspaceId, iv.currentDocument, function (data, res) {
        if (res == result.SUCCESS) {
            iv.indexCurrent = imageId - 1;
            changeDocument();
            disableSwapBtn();

        }
    });
}

function disableSwapBtn() {
    $('#next').removeClass("disabled");
    $('#last').removeClass("disabled");
    $('#first').removeClass("disabled");
    $('#previous').removeClass("disabled");
    if (iv.indexCurrent == 0) {
        $('#first').addClass('disabled');
        $('#previous').addClass('disabled');
    }
    if (iv.indexCurrent == iv.documentsLength-1) {
        $('#next').addClass('disabled');
        $('#last').addClass('disabled');
    }
}

function swapImage(imageId) {
    if (iv.indexCurrent != imageId - 1) {
        callSaveApi(imageId);
    }
}

function switchImage() {
    iv.documentsLength = Object.keys(iv.documents).length;
    var showIndex = iv.indexCurrent + 1;

    $('#chooseImage1').html('<input type="text" name="pageInput" id="pageInput" value="'+showIndex + '/' + iv.documentsLength + '"><ul id="chooseImage"></ul>');

        for (var j = 1; j <= iv.documentsLength; j++) {
            $('#chooseImage').append('<li onclick="swapImage(\'' + j + '\')"><a>' + j + '</a></li>');
        }

    $( "#chooseImage1" ).hover(
        function() {
            $( '#chooseImage' ).css( "display", "block" );
        }
    );

    $('#pageInput').on('click',function()
    {
        if($('#pageInput').val().indexOf('/')!=-1){
        $('#pageInput').val($('#pageInput').val().slice(0,$('#pageInput').val().indexOf('/')));
        }
    });

    $('#pageInput').keypress(function (e) {
        if (e.which == 13) {
            var valPage = $('#pageInput').val();
            if (valPage <= 0) alert('small value');
            else if (valPage > iv.documentsLength) alert('big value');
            else {
                swapImage(valPage);

            }
        }
    });
    disableSwapBtn();
}

//render new line of popup layer to html append -> can refactor
function addNewLayer(countLayer, layerName, layerOwner, layerDisplay, layerModified, layerCreated, layerActive,templateName) {
    //calculate layer items by layer
    layerItems = $("." + layerName).length;
    var itemCount = 0,doubleItem = 0;
    $('.'+layerName).each(function(){
        if(typeof $(this).attr('style') != "undefined"){
        if($(this).attr('style').indexOf('display: none;') >= 0||$(this).attr('id').startsWith('SvgjsText')){
            itemCount++;
        }
        }
        else if($(this).attr('id').startsWith('SvgjsText')){
            itemCount++;
        }
    })
    layerItems -= itemCount;

    layerModified = layerModified.replace(/\s/g,'');
    layerCreated = layerCreated.replace(/\s/g,'');
    var outputModified = layerModified.substr(0, 4) + "/" + layerModified.substr(4, 2) + "/" + layerModified.substr(6, 2) + " " + layerModified.substr(8, 2) + ":" + layerModified.substr(10, 2);
    var outputCreated = layerCreated.substr(0, 4) + "/" + layerCreated.substr(4, 2) + "/" + layerCreated.substr(6, 2) + " " + layerCreated.substr(8, 2) + ":" + layerCreated.substr(10, 2);
    var checkActive = false;
    if(layerActive == layerName){
        checkActive = true;
    }
    var row = earth.buildHtml("#layer-row-template", {
        countLayer: countLayer, layerName: layerName, layerOwner: layerOwner,
        layerDisplay: layerDisplay, layerItems: layerItems, outputModified: outputModified,
        outputCreated: outputCreated, layerActive: checkActive, templateName: templateName
    });
    $('#newLayerBody').append(row);
    $("input:checkbox[name='active']").on('click', function() {
        // in the handler, 'this' refers to the box clicked on
        var $box = $(this);
        if ($box.is(":checked")) {
            // the name of the box is retrieved using the .attr() method
            // as it is assumed and expected to be immutable
            var group = "input:checkbox[name='active']";
            // the checked state of the group/box on the other hand will change
            // and the current value is retrieved using .prop() method
            $(group).prop("checked", false);
            $box.prop("checked", true);
        } else {
            $box.prop("checked", false);
        }
    });

    $('input[type=radio][name=radioLayerName]').change(function() {
        $('#layerName').val(this.value);
    });
}

//load layer to web service
function load() {
    //var cDOcId: layer Length
    iv.cDocId = iv.currentDocument.layers.length;

    for (var k = 0; k < iv.cDocId; k++) {
        iv.currentDocument.layers[k].layerDisplay = true;
        var layer = iv.currentDocument.layers[k];

        //if user login not layer owner then future create new layer
        if (layer.ownerId == userInfo) {
            if (!iv.existUser) {
            iv.existUser = true;
            iv.layerActive = layer.layerName;}
        }
        if(layer.mgrTemplate!=null){
        templateLayerName = layer.mgrTemplate.templateName;}
        addNewLayer(k + 1, layer.layerName, layer.ownerId, layer.layerDisplay, layer.lastUpdateTime, layer.insertDateTime, iv.layerActive, templateLayerName);

        //process json data: field annotation of layer in DB
        if (layer.annotations != "<svg></svg>") {
            $(iv.svg.svgId).append(layer.annotations);

            //read xml string, add id to allAnnos object -> duplicate with id auto generation
            var j = 0;
            do {
                parser=new DOMParser();
                xmlDoc=parser.parseFromString(layer.annotations,"text/xml");
                if (xmlDoc.getElementsByTagName("svg")[0].childNodes[j].nodeValue==null) {
                $title = xmlDoc.getElementsByTagName("svg")[0].childNodes[j].attributes["id"].value;

                if (allAnnos.indexOf($title) == -1) {
                    allAnnos.push($title);
                    if(parseInt($title.slice(-4))>=SVG.did)
                    SVG.did = parseInt($title.slice(-4))+1;
                }
                }
                j++;
            } while (typeof xmlDoc.getElementsByTagName("svg")[0].childNodes[j] != "undefined")
        }
    }

    //if user login is not layer, create layer
    if (!iv.existUser) {
        var j = 0;
        do {
            //default layer name is DEF + id auto increament, if layer name exist then auto increament to create
            var tmpLayerName = "DEFAULT-" + j;
            var existName = false;
            for (var k = 0; k < iv.cDocId; k++) {
                if (iv.currentDocument.layers[k].layerName == tmpLayerName) {
                    k = iv.cDocId;
                    existName = true;
                }
            }
            j++;

            //if layer name is not exist then create, add j = -1 to end loop
            if (!existName) {
                var d = new Date();

                var currentDate = dateLayer(d);

                addNewLayer(iv.cDocId + 1, tmpLayerName, userInfo, true, currentDate, currentDate, tmpLayerName,templateLayerName);

                okLayer();

                iv.layerActive = tmpLayerName;
                j = -1;
            }
        } while (j != -1);
    }

}

function dateReformat(d) {
    return d.replace(" ", "").replace("/", "").replace("/", "").replace(":", "")+"00000";
}

function dateLayer(d) {
    var month = d.getMonth()+1;
    var day = d.getDate();
    var hour = d.getHours();
    var minute = d.getMinutes();
    var second = d.getSeconds();
    var minisecond = d.getMilliseconds();

    return d.getFullYear() +
        (month<10 ? '0' : '') + month +
        (day<10 ? '0' : '') + day +
        (hour<10 ? '0' : '') + hour +
        (minute<10 ? '0' : '') + minute +
        (second<10 ? '0' : '') + second +
        (minisecond<10 ? '00' : (minisecond<100 ? '0' : '')) + minisecond;
}

function convertPdfToImage(docPath,pageCount) {
    var url = docPath;
    $('body').append('<canvas id="the-canvas" ></canvas>');
    // The workerSrc property shall be specified.
    PDFJS.workerSrc = getResource('resources/js/lib/pdf.worker.js');
    var pdfDoc = null
        , pageNum = pageCount
        , pageRendering = false
        , pageNumPending = null
        , scale = 1
        , canvas = document.getElementById('the-canvas')
        , ctx = canvas.getContext('2d');

    /**
     * Get page info from document, resize canvas accordingly, and render page.
     *
     * @param num
     *            Page number.
     */
    function renderPage(num) {
        pageRendering = true;
        // Using promise to fetch the page
        pdfDoc.getPage(num).then(function (page) {
            var viewport = page.getViewport(scale);
            canvas.height = viewport.height;
            canvas.width = viewport.width;

            // Render PDF page into ca0nvas context
            var renderContext = {
                canvasContext: ctx,
                viewport: viewport
            };
            var renderTask = page.render(renderContext);
            // Wait for rendering to finish
            renderTask.promise.then(function () {
                iv.documentPath = canvas.toDataURL("image/png");
                canvas.style.display = "none";
                return displayImage();

                pageRendering = false;
                if (pageNumPending !== null) {
                    // New page rendering is pending
                    renderPage(pageNumPending);
                    pageNumPending = null;
                }
            });
        });
    }

    /**
     * If another page rendering in progress, waits until the rendering is
     * finised. Otherwise, executes rendering immediately.
     */
    function queueRenderPage(num) {
        if (pageRendering) {
            pageNumPending = num;
        } else {
            renderPage(num);
        }
    }

    /**
     * Asynchronously downloads PDF.
     */
    PDFJS.getDocument(url).then(function (pdfDoc_) {
        pdfDoc = pdfDoc_;
        renderPage(pageNum);
    });

}

function convertTiffToImage(docPath,pageCount) {
    $(function () {
        Tiff.initialize({
            TOTAL_MEMORY: 16777216 * 10
        });
        var xhr = new XMLHttpRequest();
        xhr.open('GET', docPath);
        xhr.responseType = 'arraybuffer';
        xhr.onload = function (e) {
            var buffer = xhr.response;
            var tiff = new Tiff({
                buffer: buffer
            });
            tiff.setDirectory(pageCount - 1);
            var canvas = tiff.toCanvas();
            iv.documentPath = canvas.toDataURL("image/png");
            return displayImage();
        }
        ;
        xhr.send();
    });
}
