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
            url: window.baseUrl + "/licenseHistory",
            data: $("#searchByColumnForm").serialize(), // serializes the form's elements.
            success: function (data) {
                earth.closeProgress();
                var context = {
                    strCals : data
                }
                $('#licenseHistoryTBody').html(earth.buildHtml("#licenseHistoryRow", context));
            }
        });
    });
})

$(document).ready(function () {
    earth.defaultTabIndex();
});

