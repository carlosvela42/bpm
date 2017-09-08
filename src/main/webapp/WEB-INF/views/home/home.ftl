<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title>Earth</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" media="screen"
          href="${rc.getContextPath()}/resources/css/bootstrap.min.css" />
    <link rel="stylesheet" media="screen"
          href="${rc.getContextPath()}/resources/css/style.css" />
    <link rel="stylesheet" media="screen"
          href="${rc.getContextPath()}/resources/css/sass_compiled/components.css" />
    <link rel="stylesheet" media="screen"
          href="${rc.getContextPath()}/resources/css/style_nev.css" />
    <link rel="shortcut icon" href="${rc.getContextPath()}/resources/images/favicon.ico" type="image/x-icon">
    <script>window.baseUrl = "${rc.getContextPath()}"</script>
    <script src="${rc.getContextPath()}/resources/js/libs/jquery-2.2.4.min.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/bootstrap.min.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/jquery.query-object.js"></script>
    <script src="${rc.getContextPath()}/resources/js/lib/handlebars-v4.0.10.js"></script>
    <script src="${rc.getContextPath()}/resources/js/common.js"></script>
    <script>${h.getMessageSourceJS()}</script>
</head>

<body id="mainIndex">
<header id="header" role="banner">
    <div class="inner">
        <#include "/common/navigation.ftl">
        <h1>
            <img src="${rc.getContextPath()}/resources/images/global/logo.png"
                 alt="xStra SERIES" width="184" height="30"/>
        </h1>

        <div class="info">
            <div class="name">
            <#--<figure><img src="${rc.getContextPath()}/resources/images/global/header_icon.png" alt="header_icon"-->
            <#--width="44"-->
            <#--height="44" /></figure>-->
                <p>
                    <#if Session.userInfo??>
                        <#assign userInfo =Session.userInfo>
                        <label>${userInfo.userId} | ${userInfo.userName}</label>
                    </#if>
                </p>
            </div><!-- .name -->
            <div class="logout">
                <a href="${rc.getContextPath()}/logout">
                    <img src="${rc.getContextPath()}/resources/images/global/btn_logout.png"
                         alt="LOGOUT" width="130" height="30" />
                </a>
            </div>
        </div><!-- .info -->

    </div><!-- .inner -->

</header><!-- #header -->

<div id="content">
    <div class="content_main">
        <#--<#nested/>-->
        <div>
            <#include "../common/messages.ftl"> </div>

        <MAP name="19">
            <AREA shape="rect" coords="53,97,77,121" href="19_1.html">
        </MAP>
            <div class="board-wrapper board-full" style="height: 800px;">
            </div>
    </div>
</div><!-- #content -->

<#include "../common/footer.ftl">

<div class="info" style="display: none">
    <input id="data_baseUrl" type="text" value="${rc.getContextPath()}" />
</div>
</body>
</html>
