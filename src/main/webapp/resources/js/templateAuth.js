function proxyDelRow(name) {
    delRow(name);
    var index = $('table.' + name + 'AccessRightTable tbody > tr').size();
    $('.' + name + 'AccessRightTable tbody').attr('index', index);
}

function editRow(name, value, accessRight, screenType) {
    console.log("accessRight:" + accessRight);
    $('#' + name + 'EditItem').val("1");
    // console.log($('#' + name + 'editItem'));
    $('#' + name + 'IdOld').val(value.toString());

    if (screenType === "1") {
        // display user select box as label
        $("#text" + name).show();
        $("#text" + name).html(value);
        $('#' + name + 'Select').hide();
    }

    $('#' + name + 'Select option').each(function () {
        if ($(this).val() === value) {
            $(this).prop('selected', true);
        }
    });

    $('input[type=radio]').each(function () {
        if ($(this).val() === accessRight) {
            $(this).prop('checked', true);
        }
    });

    var btnAdd = $("#addRow_" + name);
    if (btnAdd.attr("disabled") == "disabled") {
        btnAdd.removeAttr("disabled");
        btnAdd.css("color", "white");
    }

    $('#addForm' + name).modal('show');
}

$(function () {
    //  Template Authority

    // User
    $('#addFormuser').on('shown.bs.modal', function () {
        $('#userSelect').focus();
    })

    function openModalUser() {
        $('#addFormuser').modal('show');
    };

    function closeModalUser() {
        $('#addFormuser').modal('hide');
    };

    $("#addUser").click(function () {
        // $("#userTbody > tr").each(function () {
        //     var name = $(this).attr("userId");
        //     console.log(name);
        //     $('#userSelect option[value="' + name + '"]').remove();
        // });
        removeSelectedValue(true);
        $('.accessRights').each(function () {
            $('input[type=radio]:first', this).prop('checked', true);
        });
        $("#textuser").hide();
        forRefreshInIE('userSelect');
        $('#userEditItem').val(0);

        // No user /profile -> disable button add
        var btnAddRowUser = $("#addRow_user");
        if ($('#userSelect option').length <= 0) {
            btnAddRowUser.attr("disabled", "disabled");
            btnAddRowUser.css("color", "black");
        } else {
            btnAddRowUser.removeAttr("disabled");
            btnAddRowUser.css("color", "white");
        }
        openModalUser();
    });

    // Profile
    $("#addProfile").click(function () {
        // $("#profileTbody > tr").each(function () {
        //     var name = $(this).attr("profileId");
        //     $('#profileSelect option[value="' + name + '"]').remove();
        // });
        removeSelectedValue(false);
        $('.accessRights').each(function () {
            $('input[type=radio]:first', this).prop('checked', true);
        });
        $("#textprofile").hide();
        forRefreshInIE('profileSelect');
        $('#profileEditItem').val(0);
        var btnAddRowProfile = $("#addRow_profile");
        if ($('#profileSelect option').length <= 0) {
            btnAddRowProfile.attr("disabled", "disabled");
            btnAddRowProfile.css("color", "black");
        } else {
            btnAddRowProfile.removeAttr("disabled");
            btnAddRowProfile.css("color", "white");
        }
        openModalProfile();
    });

    $('#addFormprofile').on('shown.bs.modal', function () {
        $('#profileSelect').focus();
    })
    function openModalProfile() {
        $('#addFormprofile').modal('show');
    };

    function closeModalProfile() {
        $('#addFormprofile').modal('hide');
    };

    // Constant
    var ConstantSave = {
        ADD: 0,
        EDIT: 1
    };

    window.editRowNew = function (name) {
        var source = $("#addRow").html();
        var template = Handlebars.compile(source);
        var value = $('#' + name + 'Select option:selected').val();
        var saveAction = $.trim($('#' + name + 'EditItem').val());

        // Select no item when add User / Profile
        if (saveAction == ConstantSave.ADD && ((typeof value == 'undefined') || (value.length <= 0))) {
            $('#addForm' + name).modal('hide');
            return;
        }

        if ($('#' + name + 'EditItem').val() == ConstantSave.EDIT) {
            value = $("#text" + name).html();
        }

        var indx = $('.' + name + 'AccessRightTable tbody').attr('index');
        var screenType = "1";
        var accessRight = $('input[name=accessRight' + name + ']:checked').attr('data-name');
        var accessRightValue = $('input[name=accessRight' + name + ']:checked').val();

        if ($('#' + name + 'EditItem').val() == "0") {
            if ($('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').length <= 0) {
                var html = template({
                    id: value,
                    name: name,
                    index: indx,
                    accessRight: accessRight,
                    screenType: screenType,
                    accessRightValue: accessRightValue
                });
                $('table.' + name + 'AccessRightTable tbody').append(html);
                $('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').attr('index', indx);
                var index = parseInt(indx) + 1;
                $('.' + name + 'AccessRightTable tbody').attr('index', index);
            }
        } else {
            indx = $('table.' + name + 'AccessRightTable tbody >  tr[' + name + 'Id=' + value + ']').attr("index");
            html = template({
                id: value,
                name: name,
                index: indx,
                accessRight: accessRight,
                screenType: screenType,
                accessRightValue: accessRightValue
            });
            $('table.' + name + 'AccessRightTable tbody >  tr[' + name + 'Id=' + value + ']').replaceWith(html);
            $('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').attr('index', indx);
        }

        $('#' + name + 'Select option').prop('selected', false);
        $('input[type=radio]').prop('checked', false);
        $('#addForm' + name).modal('hide');
    };

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
});

$(document).ready(function () {
    $("#tab1").trigger('click');
});

function defaultTabIndexForAddTemplate() {
    earth.defaultTabIndex();

    // template add screen
    $($('#addFormuser, #addFormprofile').on('hidden.bs.modal', function () {
        earth.defaultTabIndex();
    }));

    $($('#addFormuser, #addFormprofile').on('shown.bs.modal', function () {
        earth.defaultTabIndex();
    }))
}
$(document).ready(function () {
    defaultTabIndexForAddTemplate();
});

