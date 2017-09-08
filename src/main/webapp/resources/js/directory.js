window.onload = function () {
    var baseUrl = "${rc.getContextPath()}";
}
$(function () {
    earth.onDeleteButtonClick(function () {
        var directoryIds = [];
        $('#directoryTbody > tr').each(function () {
            if ($(this).find('.deleteCheckBox').prop('checked')) {
                directoryIds.push($(this).attr('directoryId'));
            }
        });
        if (directoryIds.length > 0) {
            $.form(window.baseUrl + "/directory/deleteList", {
                "processIds": directoryIds
            }).submit();
        } else {
            earth.addMessage("E1014");
            return;
        }
    }, earth.Messages['directory']);

    $('#enable').click(function () {
        $("#newCreateFile").val($('#enable').val());
        $('#disable').prop('checked', false);

    });
    $('#disable').click(function () {
        $("#newCreateFile").val($('#disable').val());
        $('#enable').prop('checked', false);
    });

    function getSize() {
        var folderPath = $("#txtFolderPath").val();

        folderPath = folderPath.replace(/\\/g, "/");
        var urlSwitchWorkspace = baseUrl + "/directory/getSizeFolder"
            + "?folderPath=" + folderPath;
        $.ajax({
            type: 'GET',
            url: urlSwitchWorkspace,
            dataType: "json",
            success: function (data) {
                $("#txtDiskVolSize").val(data);
            }
        });
    }

    $("#txtFolderPath").on('keydown', function(e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            getSize();
            e.preventDefault();
            return false;
        } else {
            $("#txtDiskVolSize").val(0);
        }
    });
});

$(document).ready(function () {
    earth.defaultTabIndex();
})