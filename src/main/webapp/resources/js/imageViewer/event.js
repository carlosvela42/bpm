function onClick(e) {
    var id = "#" + $(this).attr('id');
    console.log("onClick:id=" + id);
    processClick(id);
}

function onMouseMove(e) {
    if (e.x < 0 && e.y < 0) {
        onMouseUp(e);
    }
}

function onMouseDown(e) {
    if (waitMouseUpFlg) {
        return;
    }

    console.log("onMouseDown!");
    iv.drawStartX = e.x;
    iv.drawStartY = e.y;

    // unSelectMultipleSvg();
    if (iv.toolbarFlg == toolbars.BTN_SELECT) {
        if (waitMouseUpFlg) {
            selectMultipleSvg();
            iv.sMultipleSvg.draw('cancel');
            return;
        }
        createMultipleSelectRect(e);
        iv.drawFlg = setDrawFlgWhenClick();
        console.log("drawFlg=" + iv.drawFlg);
        if (iv.drawFlg) {
            unSelectMultipleSvg();
            iv.sMultipleSvg.front();
            console.log("waitMouseUpFlg=" + waitMouseUpFlg);
            iv.sMultipleSvg.draw(e);
        }
        iv.drawFlg = true;
    } else {
        unSelectMultipleSvg();
        // draw text then open text popup
        if (iv.settingAnno == "text") {
            iv.drawAnno = iv.svg.text("");
            iv.drawAnno.front();
            iv.drawAnno.draw(e);
            textPopup();
            // draw comment are draw image and draw text, then add text to class
            // to get, then get user login to set, then reset comment text area,
            // then open comment popup
        } else if (iv.settingAnno == "comment") {
            iv.drawAnno = iv.svg.image(getResource(commentImage)).attr('width', '48').attr('height', '48');
            iv.drawAnno.front();
            iv.drawAnno.draw(e);
            iv.drawAnno.attr("class", iv.layerActive);
            iv.drawAnno.width(48).height(48);
            iv.drawAnno = iv.svg.text(userInfo).attr("class", "newComment");
            iv.drawAnno.front();
            iv.drawAnno.draw(e);

            $(commentExample.COMMENTTEXTBOX).val(userInfo);

            $(commentExample.COMMENTTEXTAREA).val("");

            $(commentExample.COMMENTPOPUP).modal();
            iv.drawAnno.front();
            iv.drawAnno.draw(e);
            iv.drawAnno.y(iv.drawAnno.y() + 48);

        } else {
            iv.drawAnno = iv.settingAnno.clone();
            iv.drawAnno.front();
            iv.drawAnno.draw(e);
        }

        if (typeof iv.drawAnno.attr("class") == "undefined") {
            iv.drawAnno.attr("class", iv.layerActive);
        }
        else {
            iv.drawAnno.attr("class", iv.drawAnno.attr("class") + " " + iv.layerActive);
        }

    }
    waitMouseUpFlg = true;
}

// text popup when draw
function textPopup() {
    $(textExample.COMMENTTEXT).val('');
    $(textExample.COMMENTTEXT).css("font-family", propertiesType.fontFamily);
    $(textExample.COMMENTTEXT).css("fontSize", 18);
    $(textExample.COMMENTTEXT).css("font-style", propertiesType.fontStyle);
    $("#color").val(selectOptionByText("#color", propertiesType.penColor));
    $("#fill").val(selectOptionByText("#fill", 'Transparent'));
    $("#border").val(selectOptionByText("#border", 'Transparent'));
    $("#font").val(selectOptionByText("#font", propertiesType.fontFamily));
    $("#fontSize").val(selectOptionByText("#fontSize", propertiesType.fontSize));
    $("#fontStyle").val(selectOptionByText("#fontStyle", propertiesType.fontStyle));
    $("#myModalText").modal();
}

// get value of select option by text
function selectOptionByText(id, value) {
    return $(id + ' option').filter(function () {
        return $(this).html().toLowerCase() === value.toLowerCase();
    }).val();
}

function onMouseUp(e) {
    console.log("onMouseUp!");
    waitMouseUpFlg = false;

    if (iv.toolbarFlg == toolbars.BTN_SELECT) {
        if (iv.drawStartX == e.x && iv.drawStartY == e.y) {
            unSelectMultipleSvg();
            selectOneSvg();
        } else {
            selectMultipleSvg();
        }
        iv.sMultipleSvg.draw('cancel');
    } else if (iv.drawStartX == e.x && iv.drawStartY == e.y) {
        iv.drawAnno.draw('cancel');
    }
    else {
        iv.drawAnno.draw('stop', e);
        if (allAnnos.indexOf(iv.drawAnno.node.id) == -1) {
            allAnnos.push(iv.drawAnno.node.id);
        }
    }
}

function selectMultipleSvg() {
    if (selectedFlg) {
        return;
    }
    console.log("selectMultipleSvg!");
    // unSelectMultipleSvg();
    for (var i in allAnnos) {
        if (typeof $('#' + allAnnos[i]).attr('style') != "undefined") {
            if ($('#' + allAnnos[i]).attr('style').indexOf('visibility: hidden;') >= 0) {
            }
            else {
                var e = SVG.get(allAnnos[i]);
                if (hasIntersection(iv.sMultipleSvg, e)) {
                    if (e != null && (selectedAnnos.indexOf(allAnnos[i]) == -1)) {
                        // process with select text or image then select all comment
                        if (allAnnos[i].startsWith('SvgjsText')) {
                            selectedAnnos.push(allAnnos[i - 1]);
                        }

                        selectedAnnos.push(allAnnos[i]);
                        if (allAnnos[i].startsWith('SvgjsImage')) {
                            selectedAnnos.push(allAnnos[parseInt(i) + 1]);
                        }
                    }
                }
            }
        }
        else {
            var e = SVG.get(allAnnos[i]);
            if (hasIntersection(iv.sMultipleSvg, e)) {
                if (e != null && (selectedAnnos.indexOf(allAnnos[i]) == -1)) {
                    // process with select text or image then select all comment
                    if (allAnnos[i].startsWith('SvgjsText')) {
                        selectedAnnos.push(allAnnos[i - 1]);
                    }

                    selectedAnnos.push(allAnnos[i]);
                    if (allAnnos[i].startsWith('SvgjsImage')) {
                        selectedAnnos.push(allAnnos[parseInt(i) + 1]);
                    }
                }
            }
        }
    }
    displaySelectedAnnos();
    console.log("allAnnos.length=" + allAnnos.length);
    console.log("selectedAnnos.length=" + selectedAnnos.length);
}

function selectOneSvg() {
    if (selectedFlg) {
        return;
    }
    console.log("selectOneSvg!");
    // unSelectMultipleSvg();
    for (var i in allAnnos) {
        if (typeof $('#' + allAnnos[i]).attr('style') != "undefined") {
            if ($('#' + allAnnos[i]).attr('style').indexOf('visibility: hidden;') >= 0) {
            }
            else {
                var e = SVG.get(allAnnos[i]);
                if (hasIntersection(iv.sMultipleSvg, e)) {
                    var index = selectedAnnos.indexOf(allAnnos[i]);
                    selectedAnnos = [];
                    if (index == -1) {
                        // process with select text object

                        if ($('#' + allAnnos[i]).attr('class').startsWith('textDraw')) {
                            selectedAnnos.push(allAnnos[i]);
                            selectedAnnos.push(allAnnos[parseInt(i) + 1]);
                            displaySelectedAnnos();
                            return;
                        }
                        // process with select image of comment
                        if (allAnnos[i].startsWith('SvgjsImage')) {
                            selectedAnnos.push(allAnnos[i]);
                            selectedAnnos.push(allAnnos[parseInt(i) + 1]);
                            displaySelectedAnnos();
                            return;
                        }
                    }
                    if (allAnnos[i].startsWith('SvgjsText')) {
                        selectedAnnos.push(allAnnos[parseInt(i) - 1]);
                        selectedAnnos.push(allAnnos[i]);
                        displaySelectedAnnos();
                        return;
                    }
                    selectedAnnos.push(allAnnos[i]);
                    displaySelectedAnnos();
                    return;

                }
            }
        } else {
            var e = SVG.get(allAnnos[i]);
            if (hasIntersection(iv.sMultipleSvg, e)) {
                var index = selectedAnnos.indexOf(allAnnos[i]);
                selectedAnnos = [];
                if (index == -1) {
                    // process with select text object

                    if ($('#' + allAnnos[i]).attr('class').startsWith('textDraw')) {
                        selectedAnnos.push(allAnnos[i]);
                        selectedAnnos.push(allAnnos[parseInt(i) + 1]);
                        displaySelectedAnnos();
                        return;
                    }
                    // process with select image of comment
                    if (allAnnos[i].startsWith('SvgjsImage')) {
                        selectedAnnos.push(allAnnos[i]);
                        selectedAnnos.push(allAnnos[parseInt(i) + 1]);
                        displaySelectedAnnos();
                        return;
                    }
                }
                if (allAnnos[i].startsWith('SvgjsText')) {
                    selectedAnnos.push(allAnnos[parseInt(i) - 1]);
                    selectedAnnos.push(allAnnos[i]);
                    displaySelectedAnnos();
                    return;
                }
                selectedAnnos.push(allAnnos[i]);
                displaySelectedAnnos();
                return;

            }
        }
    }

    console.log("allAnnos.length=" + allAnnos.length);
    console.log("selectedAnnos.length=" + selectedAnnos.length);
}

function displaySelectedAnnos() {
    if (selectedAnnos.length == 1) {
        var elementBounding = document.getElementById(selectedAnnos[0]).getBoundingClientRect();
        var imgBounding = document.getElementById(iv.imgId).getBoundingClientRect();
        var dx = 0;
        var dy = 0;
        var selectedAnno = $('#' + selectedAnnos[0]);
        if ((selectedAnno.attr('transform') != "") && elementBounding.width > 0 && elementBounding.height > 0) {
            if (currentRotation == 0) {
                dx = parseInt(elementBounding.left) - imgBounding.left;
                dy = parseInt(elementBounding.top) - imgBounding.top;
            } else if (currentRotation == 90 || currentRotation == -270) {
                dx = parseInt(elementBounding.top) - imgBounding.top;
                dy = imgBounding.right - parseInt(elementBounding.right);
            } else if (currentRotation == 180 || currentRotation == -180) {
                dx = imgBounding.right - parseInt(elementBounding.right);
                dy = imgBounding.bottom - parseInt(elementBounding.bottom);
            } else if (currentRotation == 270 || currentRotation == -90) {
                dx = imgBounding.bottom - parseInt(elementBounding.bottom);
                dy = parseInt(elementBounding.left) - imgBounding.left;
            }

            if (selectedAnnos[0].startsWith('SvgjsLine')) {
                var x1 = parseInt(selectedAnno.attr('x1'));
                var x2 = parseInt(selectedAnno.attr('x2'));
                var y1 = parseInt(selectedAnno.attr('y1'));
                var y2 = parseInt(selectedAnno.attr('y2'));
                var ex = x1 < x2 ? x1 : x2;
                var ey = y1 < y2 ? y1 : y2;

                selectedAnno.attr('x1', x1 + getDimensionByScale(dx, currentScale) - ex);
                selectedAnno.attr('y1', y1 + getDimensionByScale(dy, currentScale) - ey);
                selectedAnno.attr('x2', x2 + getDimensionByScale(dx, currentScale) - ex);
                selectedAnno.attr('y2', y2 + getDimensionByScale(dy, currentScale) - ey);
            } else {
                selectedAnno.attr('x', getDimensionByScale(dx, currentScale));
                selectedAnno.attr('y', getDimensionByScale(dy, currentScale));
            }

            selectedAnno.attr('transform', '');
        }

        var el = SVG.get(selectedAnnos[0]);
        el.show();
        el.selectize({
            deepSelect: true
        }).resize(true).draggable({
            minX: 0
            , minY: 0
            , maxX: iv.imgWidth
            , maxY: iv.imgHeight
        });
    } else if (selectedAnnos.length > 1) {
        iv.selectedGroup = iv.svg.group();
        for (var i in selectedAnnos) {
            var el = SVG.get(selectedAnnos[i]);
            el.show();
            el.selectize(false, {
                deepSelect: true
            }).resize(true).draggable(false);

            iv.selectedGroup.add(el);
        }
        iv.selectedGroup.selectize({
            deepSelect: true
        });

        var point = getMinXY(selectedAnnos);
        var minX = -point[0];
        var minY = -point[1];

        iv.selectedGroup.draggable({
            minX: minX
            , minY: minY
            , maxX: (minX + iv.imgWidth)
            , maxY: (minY + iv.imgHeight)
        });
        iv.selectedGroup.front();
    }

    console.log("allAnnos.lenth=" + allAnnos.length);
    console.log("selectedAnnos.lenth=" + selectedAnnos.length);

    var boundingRects = [
        ".svg_select_boundingRect"
        , ".svg_select_points_lt"
        , ".svg_select_points_rt"
        , ".svg_select_points_rb"
        , ".svg_select_points_lb"
        , ".svg_select_points_t"
        , ".svg_select_points_r"
        , ".svg_select_points_b"
        , ".svg_select_points_l"
        , ".svg_select_points_rot"
        , ".svg_select_points_point"
    ];

    for (var c in boundingRects) {
        $(boundingRects[c]).mousedown(function () {
            resetDrawFlg();
        });
    }

    selectedFlg = true;
}

function getMinXY(annos) {
    var imgBounding = document.getElementById(iv.imgId).getBoundingClientRect();
    var minL = parseInt(imgBounding.right)
        , minT = parseInt(imgBounding.bottom)
        , minR = parseInt(imgBounding.left)
        , minB = parseInt(imgBounding.top);
    var dx, dy;
    if (currentRotation == 0) {
        for (var i in annos) {
            var elementBounding = document.getElementById(annos[i]).getBoundingClientRect();
            var cx = parseInt(elementBounding.left);
            var cy = parseInt(elementBounding.top);
            if (minL > cx) {
                minL = cx;
            }

            if (minT > cy) {
                minT = cy;
            }
        }
        dx = parseInt(minL) - imgBounding.left;
        dy = parseInt(minT) - imgBounding.top;

    }
    else if (currentRotation == 90 || currentRotation == -270) {
        for (var i in annos) {
            var elementBounding = document.getElementById(annos[i]).getBoundingClientRect();
            var cx = parseInt(elementBounding.top);
            var cy = parseInt(elementBounding.right);
            if (minT > cx) {
                minT = cx;
            }

            if (minR < cy) {
                minR = cy;
            }
        }
        dx = parseInt(minT) - imgBounding.top;
        dy = imgBounding.right - parseInt(minR);
    }
    else if (currentRotation == 180 || currentRotation == -180) {
        for (var i in annos) {
            var elementBounding = document.getElementById(annos[i]).getBoundingClientRect();
            var cx = parseInt(elementBounding.right);
            var cy = parseInt(elementBounding.bottom);
            if (minR < cx) {
                minR = cx;
            }

            if (minB < cy) {
                minB = cy;
            }
        }
        dx = imgBounding.right - parseInt(minR);
        dy = imgBounding.bottom - parseInt(minB);
    }
    else if (currentRotation == 270 || currentRotation == -90) {
        for (var i in annos) {
            var elementBounding = document.getElementById(annos[i]).getBoundingClientRect();
            var cx = parseInt(elementBounding.bottom);
            var cy = parseInt(elementBounding.left);
            if (minB < cx) {
                minB = cx;
            }

            if (minL > cy) {
                minL = cy;
            }
        }
        dx = imgBounding.bottom - parseInt(minB);
        dy = parseInt(minL) - imgBounding.left;
    }
    return [getDimensionByScale(dx, currentScale), getDimensionByScale(dy, currentScale)];
}

function getDimensionByScale(d, scale) {
    return parseInt((d * 100) / (parseInt(scale * 100)));
}

function unSelectMultipleSvg() {
    if (!selectedFlg) {
        return;
    }
    console.log("unSelectMultipleSvg!");

    if (selectedAnnos.length > 1) {
        for (var i in selectedAnnos) {
            var e = SVG.get(selectedAnnos[i]);
            e.selectize(false, {
                deepSelect: true
            }).draggable(false).resize(false);
        }
        iv.selectedGroup.selectize(false, {
            deepSelect: true
        });
        iv.selectedGroup.draggable(false, {});
        iv.selectedGroup.ungroup();

        iv.selectedGroup = iv.svg.group();
    } else if (selectedAnnos.length == 1) {
        var e = SVG.get(selectedAnnos[0]);
        e.selectize(false, {
            deepSelect: true
        }).resize(false).draggable(false);
    }
    selectedAnnos = [];
    console.log("allAnnos.lenth=" + allAnnos.length);
    console.log("selectedAnnos.lenth=" + selectedAnnos.length);
    selectedFlg = false;
    clickOnSelectedBound = false;
}

function onRotate(type) {
    console.log("onRotate!");
    currentRotation = currentRotation + (90 * type);
    smartTransform(iv.svg.svgId, currentRotation);

    if (Math.abs(currentRotation) == 360) {
        currentRotation = 0;
    }
}

function onDragMove(e, element) {
    console.log("onDragMove");
    console.log("e.detail.p.x:" + e.detail.p.x + ";e.detail.p.y:" + e.detail.p.y);
    element.move(e.detail.p.x, e.detail.p.y);
    var elementPos = document.getElementById(element.node.id).getBoundingClientRect();
    var imgPos = document.getElementById(iv.imgId).getBoundingClientRect();
    if ((elementPos.left < imgPos.left)
        || (elementPos.top < imgPos.top)
        || (elementPos.right > imgPos.right)
        || (elementPos.bottom > imgPos.bottom)) {
        e.preventDefault();
        return;
    }

    // element.move(e.detail.p.x, e.detail.p.y);

}

function processClick(id) {
    console.log("processClick:id=" + id);
    if (id == toolbars.BTN_SELECT || id == annoTypes.LINE || id == annoTypes.RECTANGLE || id == annoTypes.TEXT || id == annoTypes.HIGHLIGHT || id == annoTypes.COMMENT || id == annoTypes.TESTLINE) {
        iv.toolbarFlg = id;
        iv.drawFlg = true;
        if (id != toolbars.BTN_SELECT) {
            createSettingAnnotation(id);
        }
        else {
            $(iv.svg.svgId).css('cursor', 'auto');
        }
        return;
    } else {
        iv.drawFlg = false;
    }

    switch (id) {
        case toolbars.BTN_ZOOMFP:
            zoomFullPage();
            break;

        case toolbars.BTN_ZOOMFW:
            currentScale = ($('#imageDrawing').width() - getScrollBarWidth()) / iv.imgWidth;
            smartTransform(iv.svg.svgId, currentRotation);
            break;
        case toolbars.BTN_ZOOM200:
            currentScale = 2;
            smartTransform(iv.svg.svgId, currentRotation);
            break;
        case toolbars.BTN_ZOOM100:
            currentScale = 1;
            smartTransform(iv.svg.svgId, currentRotation);
            break;
        case toolbars.BTN_ZOOM75:
            currentScale = 0.75;
            smartTransform(iv.svg.svgId, currentRotation);
            break;
        case toolbars.BTN_ZOOM50:
            currentScale = 0.5;
            smartTransform(iv.svg.svgId, currentRotation);
            break;
        case toolbars.BTN_ZOOMIN:
            if (currentScale < 4) {
                currentScale = (Math.floor(currentScale * 10) / 10 + iv.zoomDefault).toFixed(1);
                smartTransform(iv.svg.svgId, currentRotation);
            }
            break;
        case toolbars.BTN_ZOOMOUT:
            if (currentScale > 0.1) {
                currentScale = (Math.ceil(currentScale * 10) / 10 + iv.panDefault).toFixed(1);
                smartTransform(iv.svg.svgId, currentRotation);
            }
            break;
        case toolbars.BTN_ROTATE_LEFT:
            onRotate(rotateType.LEFT);
            break;
        case toolbars.BTN_ROTATE_RIGHT:
            onRotate(rotateType.RIGHT);
            break;
        case toolbars.BTN_PRINTALL:
            printAll();
            break;
        case toolbars.BTN_PRINTIMAGE:
            printImage('<img src="' + iv.documentPath + '">');
            break;
        case toolbars.BTN_LAYER:
            layerPopup();
            break;
        case toolbars.BTN_COPY:
            onCopy();
            break;
        case toolbars.BTN_CUT:
            iv.onCut();
            break;
        case toolbars.BTN_PASTE:
            onPaste();
            break;
        case toolbars.BTN_PROPERTIES:
            properties();
            break;
        case toolbars.BTN_OKPROPERTIES:
            okProperties();
            break;
        case toolbars.BTN_CANCELPROPERTIES:
            cancelProperties();
            break;

        case toolbars.BTN_TEXTPROPERTIES:
            textProperties();
            break;
        case toolbars.BTN_COMMENTPROPERTIES:
            commentProperties();
            break;
        case toolbars.BTN_CANCELCOMMENTPROPERTIES:
            cancelCommentProperties();
            break;
        case toolbars.BTN_LAST:
            last();
            break;
        case toolbars.BTN_NEXT:
            next();
            break;
        case toolbars.BTN_PREVIOUS:
            previous();
            break;
        case toolbars.BTN_FIRST:
            first();
            break;
        case toolbars.BTN_ADDLAYER:
            addLayer();
            break;
        case toolbars.BTN_REMOVELAYER:
            removeLayer();
            break;
        case toolbars.BTN_RENAMELAYER:
            renameLayer();
            break;
        case toolbars.BTN_ACTIVELAYER:
            activeLayer();
            break;
        case toolbars.BTN_DISPLAYLAYER:
            displayLayer();
            break;
        case toolbars.BTN_OKLAYER:
            okLayer();
            break;
        case toolbars.BTN_OKLAYERDELETE:
            okLayerDelete();
            break;
        case toolbars.BTN_GRAYSCALE:
            grayScale($('#controls1').val(), $('#controls').val());
            break;
        case toolbars.BTN_OKGRAYSCALE:
            okGrayScale($('#controls1').val(), $('#controls').val(), $("#cbox1").val());
            break;
        case toolbars.BTN_CANCELGRAYSCALE:
            cancelGrayScale();
            break;
        case toolbars.BTN_CONTROLS:
        case toolbars.BTN_CONTROLS1:
        case toolbars.BTN_CBOX1:
            changeGrayScale($('#controls1').val(), $('#controls').val(), $("#cbox1").is(':checked'));
            break;
        case toolbars.BTN_SAVEIMAGE:
            saveImage();
            break;
        case toolbars.BTN_SUBIMAGE:
            subImage();
            break;
    }
}

function zoomFullPage() {
    if (($('#imageDrawing').width() - getScrollBarWidth()) / iv.imgWidth > ($('#imageDrawing').height() - getScrollBarWidth()) / iv.imgHeight) {
        currentScale = ($('#imageDrawing').height() - getScrollBarWidth()) / iv.imgHeight;
    }
    else {
        currentScale = ($('#imageDrawing').width() - getScrollBarWidth()) / iv.imgWidth;
    }
    smartTransform(iv.svg.svgId, currentRotation);
}

function subImage() {
    window.open(window.location.href + "&mode=1");
}

function saveImage() {
    saveAnnoToLayer();
    saveDocument(getResource("WS/saveImage"), window.token, window.workspaceId, iv.currentDocument, function (data, res) {
        if (res === result.SUCCESS) {
            postImage(
                saveAndCloseImageViewer(getResource("WS/saveAndCloseImageViewer"),
                    window.token, window.workspaceId, window.workItemId, window.folderItemNo, function (data, res) {
                        if (res === result.SUCCESS) {
                            window.close();
                        }
                    }));
        }
        else {
            alert('Save unsuccessful!');
        }
    });
}

function cancelGrayScale() {
    changeGrayScale(tmpContrast, tmpBrightness, tmpCheckGrayscale);
}

function okGrayScale(cons, bri, isEffected) {
    tmpContrast = cons;
    tmpBrightness = bri;
    tmpCheckGrayscale = isEffected;
}

function changeGrayScale(cons, bri, isEffected) {
    grayScale(cons, bri, isEffected);
    changeIEGrayScale(cons, bri, isEffected);
    // CuongTM: This code work in chrome, and is kept for reference.
    //     if (isEffected) {
    //         setAttribute(iv.svg.node.id, "filter", "brightness(" + (parseInt(bri) + 100) + "%) contrast("+
    //             (parseInt(cons) + 100) + "%)");
    //     } else {
    //         setAttribute(iv.svg.node.id, "filter", "brightness(100%) contrast(100%)");
    //     }
}

function grayScale(cons, bri, isEffected) {
    $('#controls1').val(cons);
    $('#controls').val(bri);
    $('#contrastValue').val(cons);
    $('#brightnessValue').val(bri);
    $("#cbox1").prop('checked', isEffected);
    $("#myModal2").modal();
}

function changeIEGrayScale(cons, bri, isEffected) {
    // refrence: https://docs.rainmeter.net/tips/colormatrix-guide/
    var imageNode = iv.imgNode;
    imageNode.unfilter(true);
    if (isEffected) {
        var b = bri / 100.0;
        console.log("brightness : " + b);
        var c = cons / 100.0;
        console.log("cons : " + c);
        var t = (1.0 - c);

        var contrast = [t, 0, 0, 0, 0,
            0, t, 0, 0, 0,
            0, 0, t, 0, 0,
            0, 0, 0, t, 0];

        var brighness = [1, 0, 0, 0, b,
            0, 1, 0, 0, b,
            0, 0, 1, 0, b,
            0, 0, 0, 1, 0];

        imageNode.filter(function (add) {
            add.colorMatrix('matrix',
                mutiply(contrast, brighness));
        });
    }
}

function mutiply(m1, m2) {
    var col = [];
    var i;
    var j;
    var k;

    for (i = 0; i < 4; i++) {
        for (j = 0; j < 5; j++) {
            col[j] = m1[j + (i * 5)];
        }
        for (j = 0; j < 5; j++) {
            var val = 0;
            for (k = 0; k < 4; k++) {
                val += m2[j + (k * 5)] * col[k];
            }
            m1[j + (i * 5)] = val;
        }
    }
    return m1;
}


function okLayerDelete() {
    deleteLayerFlag = false;
    removeLayer();
}

function okLayer() {
    iv.cDocId = $("#newLayerBody tr").length;
    for (var j = 0; j < iv.cDocId; j++) {

        if (typeof iv.currentDocument.layers[j] == "undefined") {
            iv.currentDocument.layers[j] = $.extend(true, {}, iv.currentDocument.layers[j - 1]);
            iv.currentDocument.layers[j].annotations = "";
            iv.currentDocument.layers[j].layerNo = null;
            iv.currentDocument.layers[j].layerName = $("#newLayerBody tr:eq(" + j + ") td:eq(0)")
                .text().trim();
            iv.currentDocument.layers[j].action = 0;
        }

        if ($("#newLayerBody tr:eq(" + j + ") td:eq(0)")
                .text().trim() == "") {
            //$('.'+iv.currentDocument.layers[j].layerName).remove();
            $('.' + iv.currentDocument.layers[j].layerName).hide();
            iv.currentDocument.layers[j].action = 2;
            //iv.currentDocument.layers.splice(j, 1);
            //$("#newLayerBody tr:eq(" + j + ") td:eq(0)").parent().remove();
            $("#newLayerBody tr:eq(" + j + ") td:eq(0)").parent().hide();
            //j--;
            //iv.cDocId--;
        }
        else {
            //rename class of annotation by layer name
            $('.' + iv.currentDocument.layers[j].layerName).each(function () {
                $(this).attr("class", $("#newLayerBody tr:eq(" + j + ") td:eq(0)")
                    .text().trim());
            });

            iv.currentDocument.layers[j].layerName = $("#newLayerBody tr:eq(" + j + ") td:eq(0)")
                .text().trim();
            if (iv.currentDocument.layers[j].action != 0) {
                iv.currentDocument.layers[j].action = 1;
            }


            iv.currentDocument.layers[j].ownerId = $("#newLayerBody tr:eq(" + j + ") td:eq(1)")
                .text().trim();
            iv.currentDocument.layers[j].layerDisplay = $("#newLayerBody tr:eq(" + j + ") td:eq(2) input").is(
                ':checked');
            iv.currentDocument.layers[j].lastUpdateTime = dateReformat($("#newLayerBody tr:eq(" + j + ") td:eq(4)").text().trim());
            iv.currentDocument.layers[j].insertDateTime = dateReformat($("#newLayerBody tr:eq(" + j + ") td:eq(5)").text().trim());
            if ($("#newLayerBody tr:eq(" + j + ") td:eq(6) input").is(
                    ':checked')) {
                iv.layerActive = iv.currentDocument.layers[j].layerName;
            }
            if ($("#newLayerBody tr:eq(" + j + ") td:eq(2) input").is(
                    ':checked')) {

                $('.' + iv.currentDocument.layers[j].layerName).each(function () {

                    if ($(this).parent().css("visibility") == "hidden") {

                    } else {
                        $(this).css("visibility", "visible");
                    }
                });
            } else {
                $('.' + iv.currentDocument.layers[j].layerName).css("visibility", "hidden");
            }
        }
    }
    unSelectMultipleSvg();
}

function displayLayer() {

    $(
        "input[type=checkbox][name=display][value="
        + $('#layerName').val() + "]").trigger(
        "click");


}

function activeLayer() {


    $('#layerActive').val($('#layerName').val());
    $(
        "input[type=radio][name=active][value="
        + $('#layerName').val() + "]").prop(
        "checked", true).trigger("click");


}

function renameLayer() {

    var countRow = $("#newLayerBody tr").length;
    $(
        "input[type=radio][name=active][value="
        + $('#layerActive').val() + "]").attr(
        'value', $('#layerName').val());

    for (var j = 1; j <= countRow; j++) {
        if ($('#layerActive').val() == $('#layer' + j).text().trim()) {
            $('#layer' + j).replaceWith('<div id="layer' + j + '"><input type="radio" name="radioLayerName"' +
                ' value="' + $('#layerName').val() + '"' +
                ' style="margin-top: 0px" checked>' + $('#layerName').val() + '</div>');

            return $('#layerActive').val($('#layerName').val());


        }
    }

}

function removeLayer() {
    var countUser = 0;
    var countRow = $("#newLayerBody tr").length;
    //count layer by current user
    for (var j = 1; j <= countRow; j++) {
        if ($('#layer' + j).parent().next('td').text().trim() == userInfo && $('#layer' + j).text().trim() != "") {
            countUser++;
        }
    }
    //if only a layer then don't delete
    if (countUser > 1) {
        if ($('.' + $('#layerName').val()).length > 0 && deleteLayerFlag) {
            deleteLayerFlag = true;
            $('#myModalConfirm').modal();
        }
        else {
            deleteLayerFlag = true;
            for (var j = 1; j <= countRow; j++) {
                if ($('#layerName').val() == $('#layer' + j).text().trim()) {

                    $('#layer' + j).text("");
                    $('#layer' + j).parent().parent().hide();
                    if ($('input[name=active]:checked').val() == $(
                            '#layerName').val()) {
                        for (var k = 0; k <= countRow; k++) {
                            if ($("#newLayerBody tr:eq(" + k + ")").css('display') != 'none') {
                                $('#layerName').val($('#layer' + (k + 1)).text().trim());
                                return $('#activeLayer').trigger('click');
                            }
                        }
                    }
                }

            }
        }
    }
    else {
        alert('Must have layer');
    }
}

function addLayer() {
    btnWhenAddLayer();
    var countRow = $("#newLayerBody tr").length;
    var d = new Date();
    var currentDate = dateLayer(d);
    addNewLayer(countRow + 1, $('#layerName').val(),
        userInfo, true, currentDate, currentDate, false, templateLayerName);
    $('input[name=active]').change(function () {
        $('#layerActive').val($('input[name=active]:checked').val());
    });
    activeLayer();

}

function resetLayer() {
    // reset popup layer
    $("#newLayerBody").find("tr").remove();
}

//open layer popup
function layerPopup() {
    resetLayer();
    // re-add popup layer
    for (var k = 0; k < iv.cDocId; k++) {
        if (iv.currentDocument.layers[k].mgrTemplate != null) {
            templateLayerName = iv.currentDocument.layers[k].mgrTemplate.templateName;
        }
        addNewLayer(k + 1, iv.currentDocument.layers[k].layerName, iv.currentDocument.layers[k].ownerId, iv.currentDocument.layers[k].layerDisplay, iv.currentDocument.layers[k].lastUpdateTime, iv.currentDocument.layers[k].insertDateTime, iv.layerActive, templateLayerName);
        if (iv.currentDocument.layers[k].action == 2) {
            $('#layer' + (k + 1)).text("");
            $("#newLayerBody tr:eq(" + k + ") td:eq(0)").parent().hide();
        }
    }
    // reset layer name
    $('#layerName').val($('input[name=active]:checked').val());
    $('#layerActive').val($('input[name=active]:checked').val());
    $(toolbars.BTN_ADDLAYER).css('pointer-events', 'none');
    $(toolbars.BTN_REMOVELAYER).css('pointer-events', 'none');
    $(toolbars.BTN_RENAMELAYER).css('pointer-events', 'none');
    $(toolbars.BTN_ACTIVELAYER).css('pointer-events', 'none');
    $(toolbars.BTN_DISPLAYLAYER).css('pointer-events', 'none');
    $('input[name=active]').change(function () {
        $('#layerActive').val($('input[name=active]:checked').val());
    });
    $("#myModal3").modal();
}

function first() {
    callSaveApi(1);
}

function next() {
    callSaveApi(iv.indexCurrent + 2);
}

function previous() {
    callSaveApi(iv.indexCurrent);
}

function last() {
    callSaveApi(iv.documentsLength);
}

function loadBefore() {
    // luu annotation voi anh truoc->xoa trang object, reset page
    $('#chooseImage').empty();
    $("#svg").empty();
}

// print include annotation
function printAll() {
    // reset rotate, scale, select before print
    var tmpScale = currentScale;
    var tmpRotate = currentRotation;
    currentRotation = 0;
    currentScale = 1;
    smartTransform(iv.svg.svgId, 0);
    unSelectMultipleSvg();

    // print then return rotate, scale
    printImage(document.getElementById("imageDrawing").innerHTML);
    currentRotation = tmpRotate;
    currentScale = tmpScale;
    smartTransform(iv.svg.svgId, currentRotation);
}

function printImage(image) {
    var windowContent = '<!DOCTYPE html>';
    windowContent += '<html>'
    windowContent += '<head><title>Print</title></head>';
    windowContent += '<body>'
    windowContent += image;
    windowContent += '</body>';
    windowContent += '</html>';
    var printWin = window.open('', '');
    printWin.document.open();
    printWin.document.write(windowContent);
    printWin.document.close();
    printWin.focus();
    printWin.print();
    printWin.close();
}

// button cancel of comment popup
// remove comment if cancel create new
function cancelCommentProperties() {
    if (selectedAnnos.length == 0) {
        $("#" + iv.drawAnno.node.id).remove();
        $('#SvgjsImage' + +(parseInt(iv.drawAnno.node.id.substr(-4)) - 1)).remove();
    }
}

// button ok of comment popup
function commentProperties() {
    // create new
    if (selectedAnnos.length == 0) {
        $('.' + commentExample.COMMENTCLASS).children().text($("#tbComment").val());
        $('.' + commentExample.COMMENTCLASS).attr('value', $('#commentTxtArea').val());

        // remove tmpClass and push to allAnnos object
        $('.' + commentExample.COMMENTCLASS).attr("class", iv.layerActive);

        if (allAnnos.indexOf(iv.drawAnno.node.id) == -1) {
            allAnnos.push(iv.drawAnno.node.id.replace("Text", "Image").slice(0, 10) + (parseInt(iv.drawAnno.node.id.slice(9) - 1)));
            allAnnos.push(iv.drawAnno.node.id);
        }
    }// exist comment
    else {
        $('#' + selectedAnnos[1]).attr('value', $("#commentTxtArea").val());
        $('#' + selectedAnnos[1]).children().text($("#tbComment").val());
    }
}

// button ok of text popup
function textProperties() {
    // create new
    if (selectedAnnos.length == 0) {
        // properties of text
        $('#' + iv.drawAnno.node.id).children().text($(textExample.COMMENTTEXT).val());
        $("#" + iv.drawAnno.node.id).attr("fill", $('#color').find(":selected").text().trim());
        $("#" + iv.drawAnno.node.id).attr("font-family", $('#font').find(":selected").text().trim());

        $("#" + iv.drawAnno.node.id).attr("font-size", $('#fontSize').find(":selected").text().trim());
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Regular") {
            $("#" + iv.drawAnno.node.id).attr("font-style", "");
            $("#" + iv.drawAnno.node.id).attr("font-weight", "");
        }
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Bold") {
            $("#" + iv.drawAnno.node.id).attr("font-style", "");
            $("#" + iv.drawAnno.node.id).attr("font-weight", "bold");
        }
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Italic") {
            $("#" + iv.drawAnno.node.id).attr("font-style", "italic");
            $("#" + iv.drawAnno.node.id).attr("font-weight", "");
        }
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Bold Italic") {
            $("#" + iv.drawAnno.node.id).attr("font-style", "italic");
            $("#" + iv.drawAnno.node.id).attr("font-weight", "bold");
        }

        // create rect around
        var ctx = document.getElementById(iv.svg.node.id)
            , textElm = ctx.getElementById(iv.drawAnno.node.id)
            , SVGRect = textElm.getBBox();
        if (allAnnos.indexOf(iv.drawAnno.node.id) == -1) {
            allAnnos.push("SvgjsRect" + SVG.did);
            allAnnos.push(iv.drawAnno.node.id);
        }
        var rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
        rect.setAttribute("id", "SvgjsRect" + SVG.did);
        SVG.did++;
        rect.setAttribute("class", "textDraw " + iv.layerActive);
        rect.setAttribute("x", SVGRect.x);
        rect.setAttribute("y", SVGRect.y);
        rect.setAttribute("width", SVGRect.width);
        rect.setAttribute("height", SVGRect.height);
        rect.setAttribute("fill", $('#fill').find(":selected").text().trim());
        rect.setAttribute("stroke", $('#border').find(":selected").text().trim());
        ctx.insertBefore(rect, textElm);
    }// exist text
    else {
        // properties of text
        $('#' + selectedAnnos[1]).children().text($(textExample.COMMENTTEXT).val());
        $('#' + selectedAnnos[1]).attr("fill", $('#color').find(":selected").text().trim());
        $('#' + selectedAnnos[1]).attr("font-family", $('#font').find(":selected").text().trim());

        $('#' + selectedAnnos[1]).attr("font-size", $('#fontSize').find(":selected").text().trim());
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Regular") {
            $('#' + selectedAnnos[1]).attr("font-style", "");
            $('#' + selectedAnnos[1]).attr("font-weight", "");
        }
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Bold") {
            $('#' + selectedAnnos[1]).attr("font-style", "");
            $('#' + selectedAnnos[1]).attr("font-weight", "bold");
        }
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Italic") {
            $('#' + selectedAnnos[1]).attr("font-style", "italic");
            $('#' + selectedAnnos[1]).attr("font-weight", "");
        }
        if ($(textExample.FONTSTYLEID).find(":selected").text().trim() == "Bold Italic") {
            $('#' + selectedAnnos[1]).attr("font-style", "italic");
            $('#' + selectedAnnos[1]).attr("font-weight", "bold");
        }

        // set properties of exist rect around
        $('#' + selectedAnnos[0]).attr("fill", $('#fill').find(":selected").text().trim());
        $('#' + selectedAnnos[0]).attr("stroke", $('#border').find(":selected").text().trim());
        // create rect around
        var ctx = document.getElementById(iv.svg.node.id)
            , textElm = ctx.getElementById(selectedAnnos[1])
            , SVGRect = textElm.getBBox();

        $('#' + selectedAnnos[0]).attr("x", SVGRect.x);
        $('#' + selectedAnnos[0]).attr("y", SVGRect.y);
        $('#' + selectedAnnos[0]).attr("width", SVGRect.width);
        $('#' + selectedAnnos[0]).attr("height", SVGRect.height);
        unSelectMultipleSvg();
    }
}

function cancelProperties() {

}

// button ok of default popup
function okProperties() {
    var tmpPen = $(textExample.INPUTCOLOR).val();
    var tmpFill = $(textExample.INPUTFILL).val();
    var tmpHighlight = $(textExample.INPUTHIGHLIGHT).val();
    // not select, set properties variable
    if (selectedAnnos.length == 0) {
        propertiesType.penColor = "#" + tmpPen;
        propertiesType.fillColor = "#" + tmpFill;
        propertiesType.highlightColor = "#" + tmpHighlight;
        if (tmpPen == "none") {
            propertiesType.penColor = "none";
        }
        if (tmpFill == "none") {
            propertiesType.fillColor = "none";
        }
        if (tmpHighlight == "none") {
            propertiesType.highlightColor = "none";
        }
        propertiesType.lineSize = $(textExample.WIDTH).val();
    } else {
        for (var j = 0; j < selectedAnnos.length; j++) {
            // properties of line
            if ($('#' + selectedAnnos[j]).prop("tagName") == "line") {
                $('#' + selectedAnnos[j]).attr("stroke-width", $(textExample.WIDTH).val());
                if (tmpPen == 'none') {
                    $('#' + selectedAnnos[j]).attr("stroke", "none");
                }
                else {
                    $('#' + selectedAnnos[j]).attr("stroke", "#" + tmpPen);
                }
            } else if ($('#' + selectedAnnos[j]).prop("tagName") == "rect") {
                // properties of highlight
                if ($('#' + selectedAnnos[j]).attr("fill-opacity") == "0.4") {
                    if (tmpHighlight == 'none') {
                        $('#' + selectedAnnos[j]).attr("fill", "none");
                    } else {
                        $('#' + selectedAnnos[j]).attr("fill", "#" + tmpHighlight);
                    }
                }// properties of rect
                else {
                    if (tmpPen == 'none') {
                        $('#' + selectedAnnos[j]).attr("stroke", "none");
                    } else {
                        $('#' + selectedAnnos[j]).attr("stroke", "#" + tmpPen);
                    }
                    if (tmpFill == 'none') {
                        $('#' + selectedAnnos[j]).attr("fill", "none");
                    } else {
                        $('#' + selectedAnnos[j]).attr("fill", "#" + tmpFill);
                    }
                    $('#' + selectedAnnos[j]).attr("stroke-width", $(textExample.WIDTH).val());
                }
            }
        }
    }
}

// default properties popup
function modalLineProperties() {
    $(propertiesType.penColor).prop("checked", true);
    $("#1" + propertiesType.fillColor.substr(1)).prop("checked", true);
    $("#2" + propertiesType.highlightColor.substr(1)).prop("checked", true);
    $(textExample.WIDTH).val(propertiesType.lineSize);
    $("#myModalLine").modal();
}

// button properties of toolbar, controller to correct popup
function properties() {
    switch (selectedAnnos.length) {
        case 1:
            // disable
            if (selectedAnnos[0].startsWith('SvgjsLine')) {
                $('#li2').parent().addClass('disabled');
                $('#li3').parent().addClass('disabled');
                $('#li1').parent().removeClass('disabled');
                $('#width').removeClass('disabled');
                $('#li1').trigger('click');
            }
            else if (selectedAnnos[0].startsWith('SvgjsRect')) {
                if ($('#' + selectedAnnos).attr("fill-opacity") == "0.4") {
                    $('#li1').parent().addClass('disabled');
                    $('#li2').parent().addClass('disabled');
                    $('#li3').parent().removeClass('disabled');
                    $('#width').addClass('disabled');
                    $('#li3').trigger('click');
                }// properties of rect
                else {
                    $('#li1').parent().removeClass('disabled');
                    $('#li2').parent().removeClass('disabled');
                    $('#li1').trigger('click');
                    $('#li3').parent().addClass('disabled');
                    $('#width').removeClass('disabled');
                }
            }
            var tmpStroke = $('#' + selectedAnnos).attr("stroke");
            if (tmpStroke == "none") {
                tmpStroke = "#none";
            }
            var tmpFill = $('#' + selectedAnnos).attr("fill");
            if (tmpFill == "none") {
                tmpFill = "#none";
            }
            $(tmpStroke).prop("checked", true);
            $(textExample.WIDTH).val($('#' + selectedAnnos).attr("stroke-width"));
            if (typeof tmpFill != "undefined") {
                $("#1" + tmpFill.substr(1)).prop("checked", true);
                $("#2" + tmpFill.substr(1)).prop("checked", true);
            }

            $("#myModalLine").modal();
            break;
        case 2:
            //cut xong paste thi rect lai trc text
            if ($('#' + selectedAnnos).attr('class').startsWith('textDraw')) {
                var textId = "#" + selectedAnnos[1];
                $(textExample.COMMENTTEXT).val($(textId).text().trim());
                $(textExample.COMMENTTEXT).css("font-family", $(textId).attr("font-family"));
                $("#color").val(selectOptionByText("#color", $(textId).attr("fill")));
                $("#fill").val(selectOptionByText("#fill", $("#" + selectedAnnos).attr("fill")));
                $("#border").val(selectOptionByText("#border", $("#" + selectedAnnos).attr("stroke")));
                $("#font").val(selectOptionByText("#font", $(textId).attr("font-family")));
                $("#fontSize").val(selectOptionByText("#fontSize", $(textId).attr("font-size")));
                if ($(textId).attr("font-style") == "italic") {
                    if ($(textId).attr("font-weight") == "bold") {
                        $("#fontStyle").val("boldItalic");
                        $(textExample.COMMENTTEXT).css("font-style", "italic");
                        $(textExample.COMMENTTEXT).css("font-weight", "bold");
                    } else {
                        $("#fontStyle").val("italic");
                        $(textExample.COMMENTTEXT).css("font-style", "italic");
                        $(textExample.COMMENTTEXT).css("font-weight", "");
                    }
                } else {
                    if ($(textId).attr("font-weight") == "bold") {
                        $("#fontStyle").val("bold");
                        $(textExample.COMMENTTEXT).css("font-weight", "bold");
                        $(textExample.COMMENTTEXT).css("font-style", "");
                    } else {
                        $("#fontStyle").val("normal");
                        $(textExample.COMMENTTEXT).css("font-style", "");
                        $(textExample.COMMENTTEXT).css("font-weight", "");
                    }
                }
                $("#myModalText").modal();
                break;
            }
            else if ($('#' + selectedAnnos).attr('id').startsWith('SvgjsImage')) {
                $("#commentTxtArea").val($('#' + selectedAnnos[1]).attr('value'));
                $("#tbComment").val($('#' + selectedAnnos[1])[0].textContent);
                $("#myModalComment").modal();
                break;
            }
            modalLineProperties();
            break;
        default:
            $('#li1').parent().removeClass('disabled');
            $('#li2').parent().removeClass('disabled');
            $('#li3').parent().removeClass('disabled');
            $('#width').removeClass('disabled');
            $('#li1').trigger('click');
            modalLineProperties();
            break;
    }
}

// Create new annotation by selecting type of annotation on the screen.
function createSettingAnnotation(annoType) {

    switch (annoType) {
        case annoTypes.LINE:
            $(iv.svg.svgId).css('cursor', 'auto');
            iv.settingAnno = iv.svg.line().stroke(propertiesType.penColor).stroke({
                width: propertiesType.lineSize
            });
            break;
        case annoTypes.RECTANGLE:
            $(iv.svg.svgId).css('cursor', 'auto');
            iv.settingAnno = iv.svg.rect().stroke(propertiesType.penColor).fill(propertiesType.fillColor).stroke({
                width: propertiesType.lineSize
            });
            break;
        case annoTypes.TEXT:
            $(iv.svg.svgId).css('cursor', 'auto');
            iv.settingAnno = "text";
            break;
        case annoTypes.HIGHLIGHT:
            $(iv.svg.svgId).css('cursor', 'url(' + getResource(cursorImage) + '), auto');
            iv.settingAnno = iv.svg.rect().fill(propertiesType.highlightColor).fill({
                opacity: 0.4
            });
            break;
        case annoTypes.COMMENT:
            $(iv.svg.svgId).css('cursor', 'auto');
            iv.settingAnno = "comment";
            break;
    }

    // not set if text and comment
    if (iv.settingAnno != "text" && iv.settingAnno != "comment") {
        setAttribute(iv.settingAnno.node.id, "pointer-events", "all");
    }
}

// Process dynamically transform, automatically translate to start position
// after rotating.
function smartTransform(svgId, rotate) {
    var transform = getTransform(0, 0, rotate, currentScale);
    setTransform(svgId, transform);

    var cTop = $(svgId).position().top;
    var cLeft = $(svgId).position().left;

    var cTranslateLeft = iv.svgStartLeft - cLeft - $('#imageDrawing').scrollLeft();
    var cTranslateTop = iv.svgStartTop - cTop - $('#imageDrawing').scrollTop();

    var transform = getTransform(cTranslateTop, cTranslateLeft, rotate, currentScale);
    setTransform(svgId, transform);
    $('#zoomInput').val(Math.floor(currentScale * 100));
}

// Check type of browser is IE or not.
function isMsiBrowser() {
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    // In case Browser is Internet explorer.
    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        return true;
    }
    return false;
}

// Create new transform, including: translate, rotate, scale.
function getTransform(translateTop, translateLeft, rotate, scale) {
    if (isMsiBrowser()) {
        return ( "transform",
        "translate(" + translateLeft + "px," + translateTop + "px)" + " scale(" + scale + "," + scale + ")" + " rotate(" + rotate + "deg)");
    } else {
        return ( "translate(" + translateLeft + "," + translateTop + ")" + " scale(" + scale + "," + scale + ")" + " rotate(" + rotate + ")");
    }
}

// Create new transform, including: translate, rotate, scaleX, scaleY.
function getTransformXY(translateTop, translateLeft, rotate, scaleX, scaleY) {
    if (isMsiBrowser()) {
        return ( "transform",
        "translate(" + translateLeft + "px," + translateTop + "px)" + " scale(" + scaleX + "," + scaleY + ")" + " rotate(" + rotate + "deg)");
    } else {
        return ( "translate(" + translateLeft + "," + translateTop + ")" + " scale(" + scaleX + "," + scaleY + ")" + " rotate(" + rotate + ")");
    }
}

// Dynamically set transform for a svg element by id.
function setTransform(svgId, transform) {
    if (isMsiBrowser()) {
        $(svgId).css("transform-origin", "top left");
        $(svgId).css("transform", transform);
    } else {
        $(svgId).attr("transform-origin", "top left");
        $(svgId).attr("transform", transform);
    }
}

// This method to check intersection between a rectangle and a svg element.
function hasIntersection(tmpRect, svg) {
    if (svg == null || svg == "undefined") {
        return false;
    }
    var el = document.getElementById(svg.node.id);
    var childNodes = el.childNodes;
    var check = false;
    if (childNodes.length == 0) {
        return checkInterscetion(tmpRect, el);
    } else {
        for (var c in childNodes) {
            if (childNodes[c] instanceof SVGElement) {
                if (childNodes[c].id.startsWith('SvgjsTspan')) {
                    check = checkInterscetion(tmpRect, el);
                } else {
                    check = checkInterscetion(tmpRect, childNodes[c]);
                }
                if (check) {
                    return check;
                }
            }

        }
    }
    return check;
}

function checkInterscetion(tmpRect, el) {
    var svgRoot = el.farthestViewportElement;
    if (svgRoot == null) {
        return false;
    }
    var rect = svgRoot.createSVGRect();
    rect.x = tmpRect.x();
    rect.y = tmpRect.y();
    rect.height = tmpRect.height();
    rect.width = tmpRect.width();
    return svgRoot.checkIntersection(el, rect);
}

// This method to update drawFlg when click on the screen.
// In case point click in selected Annotations area(in selectedAnnos) => drawFlg
// = false, this means preparing for selecting, dragging or resizing.
// In case point click out of selected Annotations area(not in selectedAnnos) =>
// drawFlg = true, this means preparing for drawing.
function setDrawFlgWhenClick() {
    console.log("setDrawFlgWhenClick");
    for (var i in selectedAnnos) {
        var e = SVG.get(selectedAnnos[i]);
        if (hasIntersection(iv.sMultipleSvg, e)) {
            return false;
        }
    }

    if (clickOnSelectedBound) {
        return false;
    }
    return true;
}

// Dynamically set attribute for an element by id.
function setAttribute(id, attribute, value) {
    if (isMsiBrowser()) {
        $("#" + id).css(attribute, "" + value);
    } else {
        $("#" + id).attr(attribute, "" + value);
    }
}

// Clone tArr from sArr.
function cloneArray(tArr, sArr) {
    for (var i in sArr) {
        var e = SVG.get(sArr[i]).clone();
        e.hide();
        if (tArr.indexOf(e.node.id) == -1) {
            tArr.push(e.node.id);
        }
    }
}

// Copy tArr from sArr.
function copyArray(tArr, sArr) {
    for (var i in sArr) {
        if (tArr.indexOf(sArr[i]) == -1) {
            tArr.push(sArr[i]);
        }
    }
}

// Draw an rectangle to select multiple svg elements on imageviewer screen.
function createMultipleSelectRect(e) {
    iv.sMultipleSvg = iv.svg.rect().fill('none').stroke({
        width: 2
    });
    iv.sMultipleSvg.x(e.x);
    iv.sMultipleSvg.y(e.y);
}

// Clear all the contents in clipboard.
function resetClipBoard() {
    clipBoardAnnos = [];
}

function resetDrawFlg() {
    console.log("resetDrawFlg");
    clickOnSelectedBound = true;
}
