// MinhTV
$(function () {

    $('#save').on("click", function () {
        hideAndShowFilter()
        $('#addFormSearchColumn').modal('hide');

    });
    earth.onSearchButtonClick(function () {
        earth.showProgress();
        $.ajax({
            type: "POST",
            url: window.baseUrl + "/evidentLog",
            data: $("#searchByColumnForm").serialize(), // serializes the form's elements.
            success: function (data) {
                var context = {
                    strLogAccesses: data
                }
                earth.closeProgress();
                $('#evidentTBody').html(earth.buildHtml("#evidenceLogRow", context));
            }
        });
    },"Limit");
})

$(document).ready(function () {
    earth.defaultTabIndex();
});

