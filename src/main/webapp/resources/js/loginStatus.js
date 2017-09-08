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
            url: window.baseUrl + "/loginView",
            data: $("#searchByColumnForm").serialize(), // serializes the form's elements.
            success: function (data) {
                earth.closeProgress();
                var context = {
                    ctlLogins : data
                }
                $('#evidentTBody').html(earth.buildHtml("#loginStatusRow", context));
            }
        });
    });
})

$(document).ready(function () {
    earth.defaultTabIndex();
})

