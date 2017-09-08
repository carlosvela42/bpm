
$(function () {
    var countChecked = function () {
//           var selected = [];
        var str = ""
        $('input[name=DeleteRow]:checked').each(function () {
            str += $(this).attr('value') +
                ",";
        });
        var res = str.substring(0, str.length - 1);
        console.log(res);
        var h = "";
        if (str.length > 0) {
            h = "?userIds=" + res;
        }

        $("a[name=deleteUser]").attr('href', h);
    };
    countChecked();

    $('#addFormuser').on('shown.bs.modal', function () {
        $('#userSelect').focus();
    })

    // User
    function openModalUser() {
        $('#addFormuser').modal('show');
    };

    function closeModalUser() {
        $('#addFormuser').modal('hide');
    };

    $("#addUser").click(function () {
        removeSelectedValue(true);
        $("#textuser").hide();
        // $('#userSelect').show();
        forRefreshInIE('userSelect');
        $('#userEditItem').val(0);
        var btnAddUser = $("#addRowUser");
        if ($("#userSelect option").length <= 0) {
            btnAddUser.attr("disabled", "disabled");
            btnAddUser.css("color", "black");
        } else {
            btnAddUser.removeAttr("disabled");
            btnAddUser.css("color", "white");
        }
        openModalUser();
    });

    // Profile
    $("#addProfile").click(function () {
        removeSelectedValue(false);
        $("#textprofile").hide();
        // $('#profileSelect').show();
        forRefreshInIE('profileSelect');
        $('#profileEditItem').val(0);
        var btnAddProfile = $("#addRowProfile");
        if ($("#profileSelect option").length <= 0) {
            btnAddProfile.attr("disabled", "disabled");
            btnAddProfile.css("color", "black");
        } else {
            btnAddProfile.removeAttr("disabled");
            btnAddProfile.css("color", "white");
        }
        openModalProfile();
    });

    function refractorCodeInThisFunction() {
        $("#tab1").click(function () {
            $(this).children().css('color', '#0077E5');
            $(this).children().css('font-weight', 'bold');
            $(this).children().css('background-color', '#fff');
            $("#tab2").children().css('color', 'black');
            $("#tab2").children().css('font-weight', '');
            $("#tab2").children().css('background-color', '#E6E6E6');
        });

        $("#tab2").click(function () {
            $(this).children().css('color', '#0077E5');
            $(this).children().css('font-weight', 'bold');
            $(this).children().css('background-color', '#fff');
            $("#tab1").children().css('color', 'black');
            $("#tab1").children().css('font-weight', '');
            $("#tab1").children().css('background-color', '#E6E6E6');
        });
    }

    refractorCodeInThisFunction();

    $('#addFormprofile').on('shown.bs.modal', function () {
        $('#profileSelect').focus();
    })

    function openModalProfile() {
        $('#addFormprofile').modal('show');
    };

    function closeModalProfile() {
        $('#addFormprofile').modal('hide');
    };

    window.editRowNew = function (name) {
        var source = $("#addRow").html();
        var template = Handlebars.compile(source);
        var value = $('#' + name + 'Select option:selected').val();
        if (value && value.length > 0) {
            var indx = $('.' + name + 'AccessRightTable tbody').attr('index');
            var screenType = "1";
            var accessRight = $("#accessRightName").val();
            var valueAccess = $("#accessRightValue").val();
            var valueOld = $('#' + name + 'IdOld').val();
            if ($('#' + name + 'EditItem').val() == "0") {
                if ($('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').length <= 0) {
                    var html = template({
                        id: value,
                        name: name,
                        index: indx,
                        accessRight: accessRight,
                        screenType: screenType,
                        valueAccess: valueAccess
                    });
                    $('table.' + name + 'AccessRightTable tbody').append(html);
                    $('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').attr('index', indx);
                    var index = parseInt(indx) + 1;
                    $('.' + name + 'AccessRightTable tbody').attr('index', index);
                }
            } else {
                indx = $('table.' + name + 'AccessRightTable tbody >  tr[' + name + 'Id=' + valueOld + ']').attr("index");
                html = template({
                    id: value,
                    name: name,
                    index: indx,
                    accessRight: accessRight,
                    valueAccess: valueAccess
                });
                if (valueOld == value) {
                    var tdObject = $('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']');
                    tdObject.replaceWith(html);
                    tdObject.attr('index', indx);
                } else {
                    if ($('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').length <= 0) {
                        $('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + valueOld + ']').replaceWith(html);
                        $('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').attr('index', indx);
                    }
                }
            }
        }
        $('#' + name + 'Select option').prop('selected', false);
        $('input[type=radio]').prop('checked', false);
        $('#addForm' + name).modal('hide');
    }
})

$(document).ready(function () {
    $("#tab1").trigger('click');
})

$(document).ready(function () {
    earth.defaultTabIndex();
});

// process for user, profile setting popup


function editMenuRow(name, value, accessRight) {
    $('#' + name + 'EditItem').val("1");
    // console.log($('#' + name + 'editItem'));
    $('#' + name + 'IdOld').val(value.toString());

    $('#' + name + 'Select option').each(function () {
        if ($(this).val() === value) {
            $(this).prop('selected', true);
        }
    });

    var isUser = name === "user";
    removeSelectedValue(isUser, value);

    $('input[type=radio]').each(function () {
        if ($(this).val() === accessRight) {
            $(this).prop('checked', true);
        }
    });
    var btnAdd;
    if (isUser) {
        btnAdd = $("#addRowUser");
    } else {
        btnAdd = $("#addRowProfile");
    }
    if (btnAdd.attr("disabled") == "disabled") {
        btnAdd.removeAttr("disabled");
        btnAdd.css("color", "white");
    }
    $('#addForm' + name).modal('show');
}


