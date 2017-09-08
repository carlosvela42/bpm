DEFAULT_SIZE_THUMB=150;
function init(parentId, params, docType, page, callback) {
	if (SVG.supported) {
		var url = window.baseUrl + '/WS/getAnnotationsByDocument?' + params;
		if (page == null || page == '') {
			return;
		}

		$.ajax({
			url : url,
			async : true,
			dataType : 'jsonp',
			type : "GET",
			// cache : true,
			success : function(annotations, textStatus, xhr) {
				var thumb = new SVG(parentId);
                var thumbId = '#'+ thumb.node.id;
                $(thumbId).attr("width", DEFAULT_SIZE_THUMB);
                $(thumbId).attr("height", DEFAULT_SIZE_THUMB);
				displayImage(thumb, annotations, params, docType, page, function() {
                    $('#' + thumb.node.id).dblclick(function () {
                        var imageViewerUrl = window.baseUrl + '/imageviewer/svgImageViewer?' + params;
                        // var win = window.open(imageViewerUrl, '_blank');
                        open_window(imageViewerUrl,"Image viewer", screen.availWidth, screen.height);
                    });

                    if (typeof callback === "function") {
                        callback();
                    }
				});
			},
			error : function(xhr, textStatus, errorThrown) {
				console.log("load thumbernail of document("
						+ params + ") error!");
			}
		});
	} else {
		alert('SVG not supported');
		return;
	}
}

function open_window(url, title, width, height) {
    // screen.width means Desktop Width
    // screen.height means Desktop Height

    var center_left = (screen.width / 2) - (width / 2);
    var center_top = (screen.height / 2) - (height / 2);

    var my_window = window.open(url, title, "scrollbars=1, width=" + width + ", height=" + height + ", left=" + center_left + ", top=" + center_top);
    // my_window.focus();
}

function displayImage(thumb, annotations, params, docType, page, callback) {
    var imageUrl = window.baseUrl + '/WS/getDocumentBinary?' + params;
    switch (docType) {
        case documentType.IMAGE:
            thumb.image(imageUrl).loaded(
                function (loader) {
                    this.size(loader.width, loader.height);
                    displayAnnotations(thumb, annotations);
                    setThumbnailScale(thumb, loader.width, loader.height);
                }
            );
            break;
        case documentType.PDF:
            convertPdfToImage(thumb, imageUrl, parseInt(page), function (data) {
                thumb.image(data).loaded(
                    function (loader) {
                        this.size(loader.width, loader.height);
                        displayAnnotations(thumb, annotations);
                        setThumbnailScale(thumb, loader.width, loader.height);
                    }
                );
            });
            break;
        case documentType.TIFF:
            convertTiffToImage(thumb, imageUrl, page, function (data) {
                thumb.image(data).loaded(
                    function (loader) {
                        this.size(loader.width, loader.height);
                        displayAnnotations(thumb, annotations);
                        setThumbnailScale(thumb, loader.width, loader.height);
                    }
                );
            });
            break;
    }
    return callback();
}

function displayAnnotations(thumb, annotations) {
    if (annotations.thumbernail !== null && annotations.thumbernail !== 'null') {
        $("#"+ thumb.node.id).append(annotations.thumbernail);
    }
}
function setThumbnailScale(thumb, width, height) {
    var thumbId = '#'+ thumb.node.id;
    $(thumbId).attr("width", width);
    $(thumbId).attr("height", height);

    // Set scale.
    var scaleX = Math.floor((DEFAULT_SIZE_THUMB / width) * 100000) / 100000;
    var scaleY = Math.floor((DEFAULT_SIZE_THUMB / height) * 100000) / 100000;

    var transform = getTransformXY(0, 0, 0, scaleX, scaleY);
    setTransform(thumbId, transform);
}

function convertTiffToImage(thumb, imgUrl, page, callback) {
	$(function() {
		Tiff.initialize({
			TOTAL_MEMORY : 16777216 * 10
		});
		var xhr = new XMLHttpRequest();
		xhr.open('GET', imgUrl);
		xhr.responseType = 'arraybuffer';
		xhr.onload = function(e) {
			var buffer = xhr.response;
			var tiff = new Tiff({
				buffer : buffer
			});
			console.log(tiff);
			tiff.setDirectory(page - 1);
			var canvas = tiff.toCanvas();
			var result = canvas.toDataURL("image/png");
			return callback(result);
		};
		xhr.send();
	});
}

function convertPdfToImage(thumb, imgUrl, page, callback) {
    $('#' + thumb.node.id).append('<canvas id="the-canvas" ></canvas>');
    // The workerSrc property shall be specified.
    PDFJS.workerSrc = getResource('resources/js/lib/pdf.worker.js');
    var pdfDoc = null
        , pageNum = page
        , pageRendering = false
        , pageNumPending = null
        , scale = 0.8
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
                result = canvas.toDataURL("image/png");
                canvas.style.display = "none";
                return callback (result);

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
    PDFJS.getDocument(imgUrl).then(function (pdfDoc_) {
        pdfDoc = pdfDoc_;
        renderPage(pageNum);
    });
}