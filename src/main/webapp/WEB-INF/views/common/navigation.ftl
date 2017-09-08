<nav class="global">
    <div class="navigation_btn">
        <img src="${rc.getContextPath()}/resources/images/global/btn.png" alt="btn" width="60" height="60"/>
    </div>
    <ul>
        <li class="main"><a href="${rc.getContextPath()}/">ホーム</a></li>

    <#if Session.menuStructures??>
        <#assign menuStructures=Session.menuStructures>
            <#list menuStructures as menuStructure>
            <li class="${menuStructure.getCssClass()!""}">
                <div class="name">${menuStructure.getMenuParentName()!""}</div>
                <ul class="childNavigation">
                    <#list menuStructure.mgrMenus as mgrMenu>
                        <li><a href="${rc.getContextPath()}/${mgrMenu.getMenuInformation().getUrl()}">${mgrMenu.getFunctionName()}</a></li>
                    </#list>
                </ul>
            </li>
            </#list>
    </#if>
        <#--<li class="user">-->
            <#--<div class="name">ユーザー</div>-->
            <#--<ul>-->
                <#--<li><a href="${rc.getContextPath()}/user">ユーザー一覧</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/profile">プロファイル一覧</a></li>-->
            <#--</ul>-->
        <#--</li>-->
        <#--<li class="report">-->
            <#--<div class="name">レポート</div>-->
            <#--<ul>-->
                <#--<li><a href="${rc.getContextPath()}/licenseHistory">ライセンス利用状況</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/evidentLog">証跡ログ</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/loginView">ログイン状況</a></li>-->
            <#--</ul>-->
        <#--</li>-->
        <#--<li class="authority">-->
            <#--<div class="name">権限</div>-->
            <#--<ul>-->
                <#--<li><a href="${rc.getContextPath()}/templateAccessRight">テンプレート一覧</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/menuAccessRight/menuList">メニュー一覧</a></li>-->
            <#--</ul>-->
        <#--</li>-->
        <#--<li class="process">-->
            <#--<div class="name">プロセス</div>-->
            <#--<ul>-->
                <#--<li><a href="${rc.getContextPath()}/process">プロセス一覧</a></li>-->
                <#--<li><a href=${rc.getContextPath()}/site>サイト</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/directory">データディレクトリ一覧</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/workspace">ワークスペース一覧</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/schedule">スケジュール一覧</a></li>-->
            <#--</ul>-->
        <#--</li>-->
        <#--<li class="data">-->
            <#--<div class="name">データモデル</div>-->
            <#--<ul>-->
                <#--<li><a href="${rc.getContextPath()}/template?templateType=PROCESS">プロセス</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/template?templateType=WORKITEM">ワークアイテム</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/template?templateType=FOLDERITEM">フォルダアイテム</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/template?templateType=DOCUMENT">ドキュメント</a></li>-->
                <#--<li><a href="${rc.getContextPath()}/template?templateType=LAYER">レイヤー</a></li>-->
            <#--</ul>-->
        <#--</li>-->
        <#--<li class="tool">-->
            <#--<div class="name">ツール</div>-->
            <#--<ul>-->
                <#--<li><a href="${rc.getContextPath()}/imageviewer/svgImageViewer">イメージビューア</a></li>-->
            <#--</ul>-->
        <#--</li>-->
    </ul>
</nav>