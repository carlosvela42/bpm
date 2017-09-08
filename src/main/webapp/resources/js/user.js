$(function () {
    earth.onDeleteButtonClick(function () {
        var userIds = [];
        $('#userTbody > tr').each(function () {
            if ($(this).find('.deleteCheckBox').prop('checked')) {
                userIds.push($(this).attr('userId'));
            }
        });
        //console.log(userIds);
        var searchColumns0 = $("#searchColumns\\[0\\]").val();
        var searchColumns1 = $("#searchColumns\\[1\\]").val();
        if (userIds.length > 0) {
            $.form(window.baseUrl + "/user/deleteList", {
            	"searchColumns[0]": searchColumns0,
            	"searchColumns[1]": searchColumns1,"listIds": userIds}).submit();
        } else {
            earth.addMessage("E1014");
            return;
        }
    },earth.Messages['user']);

    // Edit User
    $("input[name=changePassword]").change(function () {
        $('input[type=password]').prop("disabled", !this.checked);
    });

    $("input[name=changePassword]").change(function () {
        $('input[type=password]').val("");
    });

    $("input[name=changePassword]").trigger("change");
});

$(document).ready(function () {
    earth.defaultTabIndex();
})