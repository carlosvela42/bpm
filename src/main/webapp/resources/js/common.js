// function getUrlParameter(sParam) {
//   var sPageURL = decodeURIComponent(window.location.search.substring(1)),
//       sURLVariables = sPageURL.split('&'), sParameterName, i;
//   for (i = 0; i < sURLVariables.length; i++) {
//     sParameterName = sURLVariables[i].split('=');
//     if (sParameterName[0] === sParam) {
//       return sParameterName[1] === undefined ? true : sParameterName[1];
//     }
//   }
// };

window.earth = {}
var errorEnum = {
    SESSION_TIME_OUT: 901
}
$(function () {
    var ww = $(window).width();
    var wh = $(window).height();
    menuIsOpened = false;

    $(document ).ajaxError(function( event, jqxhr, settings, thrownError ) {
        if (jqxhr.status == errorEnum.SESSION_TIME_OUT) {
            window.location.href = window.baseUrl + "/login/";;
        }
    });

    $(document).on('blur', '.number', function(){
        var regex = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/;
        var numberValue = $(this).val();
        if ((numberValue != "") && (!regex.test($(this).val()))) {
            $(this).val("");
        }
    });

    $(document).click(function (event) {
        var clickover = $(event.target);
        var isNavigation = clickover.parents('.global').length;
        if (!isNavigation && menuIsOpened) {
            $(".global .navigation_btn").click();
        }
    });

    $("body").css({
        "min-height": wh
    });

    $(".global ul:not(.childNavigation)").css({
        "height": wh - 60
    });


    $(".global .navigation_btn").click(function () {
        $(".global > ul").fadeToggle();
        menuIsOpened = !menuIsOpened;
    });

    $(".global > ul > li").click(function () {
        $(this).siblings().find("ul").hide("fast");
        $(this).find("ul").stop().fadeToggle("fast");
    });

    $(".global > ul > li").hover(function () {
            $(this).addClass("active");
        },
        function () {
            $(this).removeClass("active");
        });
    $('#addFormSearchColumn').on('shown.bs.modal', function () {
        $('#valid').focus();
    })

});

$(window).on('resize', function () {

    var ww = $(window).width();
    var wh = $(window).height();

    $(".global ul:not(.childNavigation)").css({
        "height": wh - 60
    });

    $("body").css({
        "min-height": wh
    });

});

jQuery(function ($) {
    $.extend({
        form: function (url, data, method) {
            if (method == null) method = 'POST';
            if (data == null) data = {};

            var form = $('<form>').attr({
                method: method,
                action: url
            }).css({
                display: 'none'
            });

            var addData = function (name, data) {
                if ($.isArray(data)) {
                    for (var i = 0; i < data.length; i++) {
                        var value = data[i];
                        addData(name + '[' + i + ']', value);
                    }
                } else if (typeof data === 'object') {
                    for (var key in data) {
                        if (data.hasOwnProperty(key)) {
                            addData(name + '[' + key + ']', data[key]);
                        }
                    }
                } else if (data != null) {
                    form.append($('<input>').attr({
                        type: 'hidden',
                        name: String(name),
                        value: String(data)
                    }));
                }
            };

            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    addData(key, data[key]);
                }
            }

            return form.appendTo('body');
        }
    });
});

function initWorkspaceSelectBox() {
    $("#workspaceSelection li a").click(function (e) {
        e.preventDefault();
        $("#workspaceSelection .workspaceName").html($(this).data("name"));
        var form = $("#filter");
        form.find("[name='workspaceId']").val($(this).data("value"));
        form.submit();
    });
}

// All Common Init Function should be here
$(function () {
    initWorkspaceSelectBox();
});


// Common button
$(function () {
    $("#saveButton").click(function () {
        var formId = $(this).data("form_id");
        $("#" + formId).submit();
    })
});

//search box
$(function () {
    $('table.clientSearch').each(function () {
        var $this = $(this);
        $this.find('tbody').each(function () {
            $(this).css("visibility", "hidden")
        })

        // $(CHECKBOX)
        $this.find('.deleteAllCheckBox').click(function () {
            var table = $(this).parents("table");
            table.find('tbody > tr').each(function () {
                $(this).find('.deleteCheckBox').prop('checked', $this.find('.deleteAllCheckBox').prop('checked'))
            });
        });

        //Search
        $this.find('.condition td > input').each(function () {
            $(this).bind('input', function () {
                earth.clientSearch($this)
                sendSearchClientCondition();
            });
        });

        // search
        earth.clientSearch($this);
    });
});

earth.clientSearch = function (table) {
    var array = [];

    table.find('.condition td > input').each(function () {
        // save condition for element has id
        //if(this.id) {
        //	earth.sessionSet(this.id,$(this).val())
        //}
        if ($(this).val()) {
            var searchTxt = $(this).val().toUpperCase();
            var col = $(this).attr('col');
            var count = 0;
            table.find('tbody > tr').each(function () {
                if (!searchTxt || $(this).find('td:nth-child(' + col + ')').text().toUpperCase().indexOf(searchTxt) >= 0) {
                    if (!array[count]) {
                        array[count] = 1;
                        $(this).show();
                    } else {
                        if (array[count] === 1) {
                            $(this).show();
                        } else {
                            $(this).hide();
                        }
                    }
                } else {
                    array[count] = 2;
                    $(this).hide();
                }
                count++;
            });
            console.log('MinhTV');

        }

    });
    if (array.length === 0) {
        table.find('tbody > tr').each(function () {
            $(this).show();
        });
    }
    table.find('tbody').each(function () {
        $(this).removeAttr("style")
    })
}
/*
 earth.Messages = {
 E0001: "{0}を入力してください。",
 E0012: "リストには{0}が既に存在しています。",
 E0013: 'You did not tick into CheckBox for Row before deleting.',
 E0012: 'Not choose Confirm Delete when execute deleted.',
 };
 */
// Delete button
earth.onDeleteButtonClick = function (cb, promptMsg) {
    $('#deleteButton').click(function () {
        earth.removeAllMessage([]);

        if ($(".table_list .table_body td:nth-child(1) input:checked").length == 0) {
            earth.addMessage("E0010", promptMsg);
            return;
        }

        if (!$('#deleteConfirm').prop('checked')) {
            earth.addMessage("E0010", earth.Messages['button.confirmDelete']);
            return;
        }
        cb();
    });
};

// Search server
earth.onSearchButtonClick= function (cb, promptMsg) {
    $('#searchButton').click(function () {
        earth.removeMessage(["E0008"]);
        var limit = $("#limitRecord").val();
        var skip = $("#skipRecord").val();
        var search = "1";
        if (limit != "" && isNaN(limit)) {
            search = "0";
            earth.addMessage("E0008","Limit");
            return;
        }
        if (skip != "" && isNaN(skip)) {
            search = "0";
            earth.addMessage("E0008", "Skip");
            return;
        }
        cb();
    });
};
Handlebars.registerHelper('ifCond', function (v1, v2, options) {
    if (v1 == v2) {
        return options.fn(this);
    }
    return options.inverse(this);
});

earth.buildHtml = function (selector, context) {
    var context = context || {};
    var source = $(selector).html();
    var template = Handlebars.compile(source);
    return template(context);
};

function addMessageToElement(select, code, params) {
    var source = $("#message-template").html();
    var template = Handlebars.compile(source);
    var message = earth.Messages[code];

    for (var i = 0; i < params.length; i++) {
        message = message.replace("{" + i + "}", params[i]);
    }

    if (!message) {
        alert("Error: message does not exists. Add it to javascript.")
        return;
    }

    var messageDiv = $(select);
    messageDiv.removeClass("hidden");
    if (messageDiv.find("b[data-code='" + code + "']").length == 0) {
        messageDiv.find(".message-table > tBody").append(template({code: code, message: message}));
    }
}

earth.addMessage = function (code, args) {
    var params = Array.prototype.splice.call(arguments, 1);
    addMessageToElement("#messages", code, params);
};

earth.addModalMessage = function (code, args) {
    var params = Array.prototype.splice.call(arguments, 1);
    addMessageToElement("#modal-messages", code, params);
};

earth.removeModalMessage = function (codes) {
    var messageDiv = $("#modal-messages");
    codes.forEach(function (code) {
        messageDiv.find("b[data-code='" + code + "']").remove();
    });
};

earth.removeAllModalMessage = function () {
    var messageDiv = $("#modal-messages");
    if (messageDiv.length > 0) {
        messageDiv.html("");
    }
}

earth.removeMessage = function (codes) {
    var messageDiv = $("#messages");
    messageDiv.addClass("hidden");

    codes.forEach(function (code) {
        messageDiv.find("b[data-code='" + code + "']").remove();
        messageDiv.find(".message-table > tBody > tr").each(function () {
            if ($(this).attr('id') === code) {
                $(this).remove();
            }
        })
    });
}

earth.removeAllMessage = function () {
    var messageDiv = $("#messages");
    if (messageDiv.length > 0) {
        messageDiv.find(".message-table").html("");
    }
    messageDiv.addClass("hidden");
}

earth.sessionSet = function (key, value) {
    sessionStorage.setItem(key, value);
}

earth.sessionGet = function (key) {
    if (sessionStorage.getItem(key) === null) {
        return "";
    }
    return sessionStorage.getItem(key);
}

function addRow(name, value, indx, accessRight) {
    var source = $("#addRow").html();
    var template = Handlebars.compile(source);
    var html = template({id: value, name: name, index: indx, accessRight: accessRight});
    var valueOld = $('#' + name + 'IdOld').val();
    if ($('#' + name + 'EditItem').val() == "0") {
        if ($('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').length <= 0) {
            $('table.' + name + 'AccessRightTable tbody').append(html);
            var index = parseInt(indx) + 1;
            // console.log(index);
            // console.log("html : " + html);
            // console.log($('.' + name + 'AccessRightTable'));

            $('.' + name + 'AccessRightTable tbody').attr('index', index);
        }
    } else {
        if (valueOld == value) {
            $('table.' + name + 'AccessRightTable tbody >  tr[' + name + 'Id=' + valueOld + ']').each(function () {
                $(this).attr(name + 'Id', value);
                // console.log($(this));
                $(this).find('td').each(function () {
                    // console.log($(this));
                    $(this).find('a').attr('onclick', 'editRow("' + name + '","' + value + '");');
                    $(this).find('span').html(value);
                    $(this).find('input').val(value);
                });
                $(this).find("td:last-child").find('span').html(accessRight);
                $(this).find("td:last-child").find('input').val(accessRight);
            });
        } else {
            if ($('table.' + name + 'AccessRightTable tbody > tr[' + name + 'Id=' + value + ']').length <= 0) {
                $('table.' + name + 'AccessRightTable tbody >  tr[' + name + 'Id=' + valueOld + ']').each(function () {
                    $(this).attr(name + 'Id', value);
                    // console.log($(this));
                    $(this).find('td').each(function () {
                        // console.log($(this));
                        $(this).find('a').attr('onclick', 'editRow("' + name + '","' + value + '");');
                        $(this).find('span').html(value);
                        $(this).find('input').val(value);
                    });
                    $(this).find("td:last-child").find('span').html(accessRight);
                    $(this).find("td:last-child").find('input').val(accessRight);
                });
            }
        }
    }

    cancel(name);
    return;
}


function delRow(name) {
    var hasChecked = 0;

    earth.removeMessage(["E0010"]);
    $('table.' + name + 'AccessRightTable tbody > tr').each(function () {
        if ($(this).find('.deleteCheckBox').prop('checked') == true) {
            $(this).remove();
            hasChecked++;
        }
    });

    if (hasChecked == 0) {
        earth.addMessage("E0010", earth.Messages[name])
    }
}

function delRowField(name) {
    var hasChecked = 0;
    var countRow = 0;

    earth.removeMessage(["E0010"]);
    $('table.' + name + 'List tbody > tr').each(function () {
        countRow++;
        if ($(this).find('.deleteCheckBox').prop('checked') == true) {
            $(this).remove();
            hasChecked++;
        }
    });

    if (hasChecked == 0 && countRow != 0) {
        earth.addMessage("E0010", earth.Messages[name])
    }
}

function cancel(name) {
    $('#' + name + 'Select option').prop('selected', false);
    $('#' + name + 'IdOld').val("");
    $('#' + name + 'EditItem').val(0);
    $("#text" + name).hide();
    $('#' + name + 'Select').show();
    $('#' + name + 'Select').html($('#' + name + 'SelectOrigin').html());

    $('input[type=radio]').prop('checked', false);
}


function openSearchCondition(element, idx, type, name, label, encrypted, isDate) {
    // if (encrypted === 'false') {
    $('#addFormSearchColumn').modal('show');
    // }
    var en = 1;
    if (encrypted === 'false') {
        en = 0
    }
    var isDated = 1;
    if (isDate === 'false') {
        isDated = 0
    }
    var columnName = name;
    $("#searchByColumnForms").val(idx);
    $("#nameBtnFilter").val(columnName);
    $('#conditionTBody').html();
    var valid=$('#valid' + idx).val();
    $('#selectValid').html(earth.buildHtml("#selectValidChild", {
        index: idx,
        type: type,
        encrypted: en,
        isDate: isDated,
        valid:valid
    }));
    if ($('#searchByColumnForm' + idx).length == 0) {
        $('#conditionTBody')
            .html(earth.buildHtml("#searchCondition", {
                index: 0,
                columnName: columnName,
                idx: idx,
                type: type,
                label: label,
                encrypted: en,
                isDate: isDated
            }));
        hideOption(0, idx, type, encrypted);

        $('#searchByColumnForm').append(earth.buildHtml("#valueChild", {idx: idx}))
    } else {
        if ($("#tBody" + idx).length === 0 || $("#tBody" + idx).html().trim() === "") {
            $('#conditionTBody')
                .html(earth.buildHtml("#searchCondition", {
                    index: 0,
                    columnName: columnName,
                    idx: idx,
                    type: type,
                    label: label,
                    encrypted: en,
                    isDate: isDated
                }));
            hideOption(0, idx, type, encrypted);
            $('#searchByColumnForm').append(earth.buildHtml("#valueChild", {idx: idx}));
        }
        else {
            $('#conditionTBody').html($("#tBody" + idx).html())
            $("#conditionTBody").find("tr").each(function () {
                $('#value').val($('#values').val());
                // Set value for Operator
                $(this).find('#columnOperator').each(function () {
                    var idx = $(this).attr("idx");
                    var index = $(this).attr("index");
                    var operatorValue = $('#changeOperator' + index + '_' + idx).val();
                    $('#operator' + index + '_' + idx + '  option[value=' + operatorValue + ']').prop('selected', true);

                });

                $(this).find('#columnValue').each(function () {
                    var idx = $(this).attr("idx");
                    var index = $(this).attr("index");
                    $('#value_' + index + '_' + idx).val($('#values_' + index + '_' + idx).val());
                });
            })
        }
        var value;
        $("#searchByColumnForm" + idx).find("#valid" + idx).each(function () {
            value = $(this).val();
        })

        $('#valid option[value=' + value + ']').prop('selected', true);

    }
    $('#clearButtonFooter').html(earth.buildHtml("#clearChild", {
        idx: idx,
        columnName: columnName,
        type: type,
        label: label,
        encrypted: en,
        isDate: isDated
    }));

}

function changeOperator(i, idx) {
    var val = $('#operator' + i + "_" + idx + ' :selected').val();
    $("#changeOperator" + i + "_" + idx).val(val);
    $("#value_" + i + "_" + idx).removeAttr("readonly");
    if (val === "IsEmpty" || val === "IsNotEmpty" || val === "IsNull" || val === "IsNotNull") {
        $("#value_" + i + "_" + idx).attr("readonly", "readonly");
        $("#value_" + i + "_" + idx).val("");
        $("#values_" + i + "_" + idx).val("");
    }

}

function changeValid(element, idx) {
    $("#valid" + idx).val($(element).val());
}

function addValues(element, index, idx, type, label, encrypted, isDate) {
    $("#values_" + index + "_" + idx).val($(element).val());

    if ($('#img' + index + '_' + idx).length == 0) {

        $(element).removeAttr("onkeyup");
        $("#columnImg" + index + "_" + idx).html(earth.buildHtml("#addImage", {index: index, idx: idx}));
        i = parseInt(index) + 1;
        // $("#conditionTBody > tr").find("#num" + index + "_" + idx).each(function () {
        //     $(this).html(i);
        // });


        $('#conditionTBody')
            .append(earth.buildHtml("#searchCondition", {
                index: i,
                columnName: $("#columnNameSearch" + index).val(),
                idx: idx,
                type: type,
                label: label,
                encrypted: encrypted,
                isDate: isDate
            }));
        hideOption(parseInt(index + 1), idx, type, 'false');
        countRow();

    }
}
function countRow(){
    i=1;
    $("#conditionTBody > tr .num:not(:last)").each(function () {
        $(this).html(i);
        i++;
    });
}
function changeValue(element, index, idx, type) {
    $("#values_" + index + "_" + idx).val($(element).val());
}

function deleteRowSearch(index, idx) {
    $('#' + index + '_' + idx).remove();
    countRow();
}

function changeText(element, name) {
    $('#' + name + 'Condition').val($(element).val());
}

function hideOption(index, idx, typeColumn, encrypted) {
    $("#operator_" + index + "_" + idx + "  option").show();
    if (encrypted === 'true') {
        $("#operator" + index + "_" + idx).find("option").each(function () {
            if (!($(this).val().toUpperCase() === 'EQUAL')) {
                $(this).remove();
            }
        })
    }
    else {
        if (typeColumn.indexOf("CHAR") <= 0) {
            // $("#operator" + index + "_" + idx + " option[value='Like']").remove();
            // $("#operator" + index + "_" + idx + " option[value='NotLike']").remove();
            $("#operator" + index + "_" + idx + "  option[value='IsEmpty']").remove();
            $("#operator" + index + "_" + idx + " option[value='IsNotEmpty']").remove();
        }
    }
}

function clearCondition(idx, columnName, type, label, encrypted, isDate) {
    $('#searchByColumnForm' + idx).html("");
    $('#conditionTBody')
        .html(earth.buildHtml("#searchCondition", {
            index: 0,
            columnName: columnName,
            idx: idx,
            type: type,
            label: label,
            encrypted: encrypted,
            isDate: isDate

        }));
    hideOption(0, idx, type, 'false');
    $('#searchByColumnForm').append(earth.buildHtml("#valueChild", {idx: idx}))
    var nameBtnFilter = $('#nameBtnFilter').val();
    $('#btnFilter'+nameBtnFilter).removeClass("btn_filter_select");
    $('#btnFilter'+nameBtnFilter).addClass("btn_filter");
}

function sendSearchClientCondition() {
    $.ajax({
        type: "POST",
        url: window.baseUrl + "/searchClientForm",
        data: $("#searchClientForm").serialize(), // serializes the form's elements.
        success: function (data) {
            console.log(data)
        }
    });
}
//format number in handlebars
Handlebars.registerHelper('format', function (context, options) {
    var type = options.hash.type;
    switch (type) {
        case 'price':
            return 'R$ ' + context; //formatPrice ...
        case 'percent':
            return formatPercent(context, options);
        case 'float':
            return formatFloat(context, options);
        default :
            return context.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");;
    }
});
function formatPercent(context, options) {
    var fixed = options.hash.fixed || 2;
    return  (context * 100).toFixed(fixed) + '%';
};
function formatFloat(context, options) {
    var fixed = options.hash.fixed || 2;
    return  parseFloat(context).toFixed(fixed);
};

// Config for IE
function forRefreshInIE(id){
    $('#'+id).hide().show(0);
}
function removeSelectedValue(isUser, exceptValue) {
    var table = isUser ? $("#userTbody > tr") : $("#profileTbody > tr");
    var attrValueName = isUser ? "userId" : "profileId";
    var selectOption = isUser ? $("#userSelect") : $("#profileSelect");
    table.each(function () {
        var name = $(this).attr(attrValueName);
        var removed = true;
        if (exceptValue && exceptValue === name) {
            removed = false;
        }

        if (removed) {
            selectOption.find('option[value="' + name + '"]').remove();
        }
    });
}

function hideAndShowFilter() {
    var indx = $('#searchByColumnForms').val();
    $("#searchByColumnForm" + indx).html($('#valid' + indx));
    $("#searchByColumnForm" + indx).append($('#type' + indx));
    $("#searchByColumnForm" + indx).append($('#isDate' + indx));
    $("#searchByColumnForm" + indx).append($('#encrypted' + indx));
    $("#searchByColumnForm" + indx).append('<div id="tBody' + indx + '"></div>');

    $("#tBody" + indx).append($('#conditionTBody').html());
    var nameBtnFilter = $('#nameBtnFilter').val();
    if($('#conditionTBody').find('tr').length >= 2){
        $('#btnFilter'+nameBtnFilter).removeClass("btn_filter");
        $('#btnFilter'+nameBtnFilter).addClass("btn_filter_select");

    }else{
        $('#btnFilter'+nameBtnFilter).removeClass("btn_filter_select");
        $('#btnFilter'+nameBtnFilter).addClass("btn_filter");
        $('#conditionTBody').find('tr').each(function () {
            $(this).find("#columnOperator").each(function () {
                $(this).find("input[type=hidden]").each(function () {
                    var val=$(this).val();
                    if (val === "IsEmpty" || val === "IsNotEmpty" || val === "IsNull" || val === "IsNotNull") {
                        $('#btnFilter'+nameBtnFilter).removeClass("btn_filter");
                        $('#btnFilter'+nameBtnFilter).addClass("btn_filter_select");
                    }
                })
            })
        });
    }
}

