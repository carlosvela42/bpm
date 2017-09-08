// MinhTV


window.onload = function () {

    $('.icon_edit').on("click", function (e) {
        // e.preventDefault();
        console.log("aaa");
        if(1==1){
            return true;
        }
        return false;
    });
    $('#unlockButton').on("click", function (){
        var i=0;
        $('#workItemTBody > tr').each(function() {

            if($(this).find('.deleteCheckBox').prop('checked')){
                var value=$(this).attr('eventId');
                var context={
                    id:i,
                    value:value
                };
                $('#unlockForm').append(earth.buildHtml("#unlockRows", context))
                i++;
            }
        });
        if(i > 0){
            $('.deleteAllCheckBox').prop("checked",false);
            $.ajax({
                type: "POST",
                url: window.baseUrl + "/workItem/unlock",
                data: $("#unlockForm").serialize(), // serializes the form's elements.
                success: function (data) {
                    var context = {
                        workItems: data
                    }
                    $('#workItemTBody').html(earth.buildHtml("#searchWorkItemRows", context));
                    $('#unlockForm').html('');

                }
            });
        }
    });

    $('#save').on("click", function () {
        hideAndShowFilter()
        $('#addFormSearchColumn').modal('hide');

    });

    $('#searchButton').on("click", function () {
        earth.removeModalMessage(["E0008"]);
        var limit = $("#limitRecord").val();
        var skip = $("#skipRecord").val();
        var search = "1";
        if (limit != "" && isNaN(limit)) {
            search = "0";
            earth.addModalMessage("E0008","Limit");
            return;
        }
        if (skip != "" && isNaN(skip)) {
            search = "0";
            earth.addModalMessage("E0008", "Skip");
            return;
        }
        $("#templateIdCondition").val($('#templateId').val());
        $("#templateTypeCondition").val($('#templateType').val());
        $.ajax({
            type: "POST",
            url: window.baseUrl + "/workItem/searchColumn",
            data: $("#searchByColumnForm").serialize(), // serializes the form's elements.
            success: function (data) {
                var context = {
                    searchForms: data
                }
                $('#searchListDiv').html(earth.buildHtml("#searchList", context));
                $("table.search").find("tr.rows").click(function () {
                    $("#workItemId").val(($(this).find('td:first').html()));
                })
                $('#tableWorkitem td').css('width',(80/$('#tableWorkitem td').length)+'%');

                layoutModelSearchTabIndex();
            }
        });
    });

    $('#templateType').change(function () {
        $("#templateTypeCondition").val($('#templateType').val());

        if($('#templateType').val()!=''){
            $.ajax({
                type: "POST",
                url: window.baseUrl + "/workItem/getTemplateName",
                data: $("#searchByColumnForm").serialize(), // serializes the form's elements.
                success: function (data) {
                    if(data.length==0){
                        // alert($('#templateType').find(":selected").text().trim()+'情報が取得できない、もしくは0件です。');
                        var context = {}
                        $('#selectTemplateName').html(earth.buildHtml("#selectTemplateTypeOption", context));
                    }else{
                        var context = {
                            mgrTemplates: data
                        }
                        $('#selectTemplateName').html(earth.buildHtml("#selectTemplateTypeOption", context));
                        var templateID=$("#templateIdCondition").val();
                        $("#selectTemplateName").find("#templateId").find("option").each(function () {
                            if($(this).val()===templateID){
                                $(this).attr("selected","selected");
                            }
                        })
                    }
                    layoutModelSearchTabIndex();
                }
            });
        } else{
            var context = {}
            $('#selectTemplateName').html(earth.buildHtml("#selectTemplateTypeOption", context));
            layoutModelSearchTabIndex();
        }
    });


    $('#btnReflect').on("click", function () {
        $.ajax({
            type: "POST",
            url: window.baseUrl + "/workItem/getList",
            data: $("#deleteListForm").serialize(), // serializes the form's elements.
            success: function (data) {
                var context = {
                    workItems: data
                }
                $('#workItemTBody').html(earth.buildHtml("#searchWorkItemRows", context));
                $('#addFormSearch').modal('hide');
            }
        });
    });
    $('#btnCancel').on("click", function () {
        $("#workItemId").val('');
        $.ajax({
            type: "POST",
            url: window.baseUrl + "/workItem/getList",
            data: $("#deleteListForm").serialize(), // serializes the form's elements.
            success: function (data) {
                var context = {
                    workItems: data
                }
                $('#workItemTBody').html(earth.buildHtml("#searchWorkItemRows", context));
                $('#addFormSearch').modal('hide');

            }
        });

    });
}

$(function () {


    // popup search
    function openFormSearch() {
        earth.removeModalMessage(["E0008"]);
        $('#addFormSearch').modal('show');
    };

    function closeFormSearch() {
        $('#addFormSearch').modal('hide');
    };

    $("#btnSearchForm").click(function () {
        $.ajax({
            type: "POST",
            url: window.baseUrl + "/workItem/searchColumn",
            data: $("#searchByColumnForm").serialize(), // serializes the form's elements.
            success: function (data) {
                var context = {
                    searchForms: data
                }
                $('#searchListDiv').html(earth.buildHtml("#searchList", context));
                $("table.search").find("tr.rows").click(function () {
                    $("#workItemId").val(($(this).find('td:first').html()));
                })

            }
        });
        $('#templateType').change();

        openFormSearch();

    });

    // popup search by column
    function openFormSearchColumn() {
        $('#addFormSearchColumn').modal('show');
    };

    function closeFormSearchColumn() {
        $('#addFormSearchColumn').modal('hide');
    };

});

function defaultTabIndexForWorkItemList() {
    earth.defaultTabIndex();

    //fist level modal
    $('#addFormSearch').on('hidden.bs.modal', function () {
        earth.defaultTabIndex();
    });

    $('#addFormSearch').on('shown.bs.modal', function () {
        layoutModelSearchTabIndex();
        $("#templateType").focus();
    });

    // second level modal
    $('#addFormSearchColumn').on('shown.bs.modal', function () {
        layoutModelSearchColumnTabIndex();
    });
}

function layoutModelSearchTabIndex() {
    earth.layoutModelTabIndex($('#addFormSearch'), 100);
}

function layoutModelSearchColumnTabIndex() {
    earth.layoutModelTabIndex($('#addFormSearchColumn'), 200);
}
$(function () {
    defaultTabIndexForWorkItemList();
});