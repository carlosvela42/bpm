// Site management
$(function () {
    earth.onDeleteButtonClick(function () {
        var siteIds = "";
        $('#siteTbody > tr').each(function () {
            if ($(this).find('.deleteCheckBox').prop('checked')) {
                siteIds+=$(this).attr('siteId') + ",";
            }
        });
        console.log(siteIds);
        cSiteId = $("#searchColumns\\[0\\]").val();
        if (siteIds.length > 0) {
            $.form(window.baseUrl + "/site/deleteList", {"siteIds": siteIds, "searchColumns[0]":cSiteId}).submit();
        } else {
            earth.addMessage("E1014");
            return;
        }
    },earth.Messages['site']);

    $(".deleteCheckBox").click(function(){
        checked();
    });

    $(".deleteAllCheckBox").click(function(){
        checked();
    });
    

    $('#addButton').click(function (e) { 
        var $form = $("#siteSearchForm");
        $form.attr("action", window.baseUrl + "/site/addNew");
        $form.submit(); 
       return  e.preventDefault ? e.preventDefault() : e.returnValue = false;
    });

    $('.editButton').click(function () { 
    	var id = $(this).data('id');
    	var $form = $("#siteSearchForm");
    	$form.attr("action", window.baseUrl + "/site/showDetail?siteId=" + id);
        $form.submit();
        return  e.preventDefault ? e.preventDefault() : e.returnValue = false;
    });

});


function checked () {
    var siteIds = "";
    $('#siteTbody > tr').each(function () {
        if ($(this).find('.deleteCheckBox').prop('checked')) {
            siteIds += $(this).find('input').val() + ","
        }
    });
    console.log(siteIds);
    if (siteIds.length > 0) {
        siteIds = siteIds.substring(0, siteIds.length - 1);
    }
    $("#directoryIds").val(siteIds);
    return;
};


function filter() {
    // Declare variables
    var siteIdInput, i;
    siteIdInput = document.getElementById('searchColumns[0]').value
            .toUpperCase();
    $('#valueSearch').val(siteIdInput);
    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < $("#sitesTable tr ").length - 2; i++) {

        if (($("#siteId" + i).html().toUpperCase().indexOf(siteIdInput) > -1)) {
            $("#row" + i).show();

        } else {
            $("#row" + i).hide();
        }
    }
}


// Add site
window.onload = function() {
    var disableSaveButton = $("#disableSaveButton").val();
    if (disableSaveButton == 'true') {
        $('#saveButton').prop('disabled', disableSaveButton);
    }


    var countChecked = function() {
        var str = ""
        $('input[name=ChooseRow]:checked').each(function() {
            str += $(this).attr('value') + ",";
        });

        if (str.length > 0) {
            str = str.substring(0, str.length - 1);
        }
        $("#directoryIds").val(str);
    }
    $("input[name=ChooseRow]").on("click", countChecked);
}


function validate() {
    var str = $("#directoryIds").val();

    if (str.length > 0) {
        document.forms[0].submit();
        $("siteSearchForm").submit();
    } else {
        $("#message").html("choose directory");
    }
}

$(document).ready(function () {
    earth.defaultTabIndex();
})