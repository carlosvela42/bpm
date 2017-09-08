<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title>Earth</title>
    <meta name="author" content="">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <!-- CSS -->
    <link rel="stylesheet" media="screen"
          href="${rc.getContextPath()}/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${rc.getContextPath()}/resources/css/styleImageViewer.css">
    <link rel="stylesheet" media="screen"
          href="${rc.getContextPath()}/resources/css/sass_compiled/components.css"/>
    <link rel="stylesheet" media="screen"
          href="${rc.getContextPath()}/resources/css/style_nev.css"/>
    <link rel="shortcut icon" href="${rc.getContextPath()}/resources/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet"
          href="${rc.getContextPath()}/resources/css/imageviewer.css">
    <link rel="stylesheet"
          href="${rc.getContextPath()}/resources/js/lib/svg/svg.select.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/resources/css/jquery-ui.css">
    <link rel="stylesheet"
          href="${rc.getContextPath()}/resources/css/jquery.contextMenu.css">

    <script type="text/javascript" src="${rc.getContextPath()}/resources/js/lib/jquery.min.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/bootstrap.min.js"></script>

    <script src="${rc.getContextPath()}/resources/js/lib/jquery.query-object.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/handlebars-v4.0.10.js"></script>
    <script src="${rc.getContextPath()}/resources/js/commonImageViewer.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/scrollbarWidth.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/decimalAdjust.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/tiff.min.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/resources/js/lib/pdf.js"></script>
    <script src="${rc.getContextPath()}/resources/js/jquery-ui.min.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/jquery.contextMenu.js"></script>
    
    <!-- SVG.js Plugins -->
    <script src="${rc.getContextPath()}/resources/js/lib/svg/svg.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/svg.draw.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/svg.select.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/svg.resize.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/svg.filter.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/rectable.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/lineable.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/ellipse.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/circle.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/text.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/svg.panzoom.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/svg/svg.draggable.js"></script>

    <!-- Earth Development -->

    <script> window.baseUrl = "${rc.getContextPath()}" </script>
    <#if imageViewer??>
    <script> window.workspaceId = "${imageViewer.workspaceId}" </script>
    <script> window.workItemId = "${imageViewer.workItemId}" </script>
    <script> window.folderItemNo = "${imageViewer.folderItemNo}" </script>
    <script> window.documentNo = "${imageViewer.documentNo}" </script>
    <script> window.token = "${imageViewer.token}" </script>
    </#if>
    <script> window.userId = "<#if Session.userInfo??>${Session.userInfo.userId}</#if>" </script><!-- userName -->
    <script src="${rc.getContextPath()}/resources/js/imageViewer/model.js"></script>
    <script src="${rc.getContextPath()}/resources/js/imageViewer/service.js"></script>
    <script src="${rc.getContextPath()}/resources/js/imageViewer/event.js"></script>
    <script src="${rc.getContextPath()}/resources/js/imageViewer/cutButton.js"></script>
    <script src="${rc.getContextPath()}/resources/js/imageViewer/copyButton.js"></script>
    <script src="${rc.getContextPath()}/resources/js/imageViewer/pasteButton.js"></script>
    <script src="${rc.getContextPath()}/resources/js/imageViewer/shortcutKey.js"></script>
    <script src="${rc.getContextPath()}/resources/js/imageViewer/main.js"></script>


</head>

<body>

<header id="header" role="banner">
    <div class="inner">
    <#include "imageViewerNavigation.ftl">
        <h1>
            <img src="${rc.getContextPath()}/resources/images/global/logo.png"
                 alt="xStra SERIES" width="184" height="30"/>
        </h1>
        <p class="iv"><img src="${rc.getContextPath()}/resources/images/iv.png" alt="iv"><span class="ivSpan">
        <@spring.message code='imageViewer.name'/></span></p>

        <div class="info">
            <div class="logout"><a id="saveImage"><img src="${rc.getContextPath()}/resources/images/back.png" alt="back"
                                                       width="160" height="30" style="cursor: pointer"></a></div>
        </div><!-- .info -->

    </div><!-- .inner -->

</header><!-- #header -->

<div id="content" style="padding-bottom: 0px">
    <div class="content_main">
        <div id="imageDrawing" class="divContent"></div>
    </div>
</div>
<script>
    initImageViewer();
</script>
<script src="${rc.getContextPath()}/resources/js/imageViewer/rightClick.js"></script>
</body>

</html>
