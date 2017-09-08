$(function () {
    earth.onDeleteButtonClick(function () {
        var profileIds = [];
        $('#profileTbody > tr').each(function () {
            if ($(this).find('.deleteCheckBox').prop('checked')) {
                profileIds.push($(this).attr('profileId'));
            }
        });
        //console.log(profileIds);
        if (profileIds.length > 0) {
            $.form(window.baseUrl + "/profile/deleteList", {"listIds": profileIds}).submit();
        } else {
            earth.addMessage("E1014");
            return;
        }
    },earth.Messages['profile']);
    
	$(".deleteCheckBox").click(function(){
	    checked();
	});

    $(".deleteAllCheckBox").click(function(){
        checked();
    });

});
function checked () {
    var userIds = "";
    $('#userTbody > tr').each(function () {
        var $this =$(this);
        if ($this.find('.deleteCheckBox').prop('checked')) {
            $this.find('.deleteCheckBox').val($this.attr('userId').toString());
            console.log($(this).attr('userId').toString());
            userIds += $(this).attr('userId').toString() + ",";
        }
    });
    console.log(userIds);
    if (userIds.length > 0) {
        userIds = userIds.substring(0, userIds.length - 1);
    }
    $("#userIds").val(userIds);
    return;
};

$(document).ready(function () {
    earth.defaultTabIndex();
})
