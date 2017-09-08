<#if buildInfo??>
    <p class="debug_info">
        [${buildInfo.date}]
        <a class="revisionDetail" href="${buildInfo.url}">${buildInfo.revision}</a>
    </p>
    <script>
        $(function () {
            $("a.revisionDetail").click(function (e) {
                e.preventDefault();
                open($(this).attr("href"));
            })
        })
    </script>
</#if>

