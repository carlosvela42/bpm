/*
 * JS for edit work item screen 
 */
var result = {
    SUCCESS : 1,
    ERROR   : 0
};

var constantValue = {
    EMPTY: ""
};

var ItemTypeEnum = {
    PROCESS     : '1',
    WORKITEM    : '2',
    FOLDER_ITEM : '3',
    DOCUMENT    : '4',
    LAYER       : '5'
};

var accessRightData = {
    NONE    : 'NONE',
    SO      : 'SO',
    RO      : 'RO',
    RW      : 'RW',
    FULL    : 'FULL'
};

var eventTypeEnum = {
    IS_LINK_CLICK   : 1,
    IS_BUTTON_CLICK : 0,
    IS_CHANGED_LIST : 2
};
var isChangedData = false;
var isChangedTempate = false;

var url = {
    SHOW_TEMPLATE       :   window.baseUrl + "/workItem/showTemplate",
    LOAD_TEMPLATE_FIELD :   window.baseUrl + "/workItem/setTemplateField",
    UPDATE_TEMPLATE     :   window.baseUrl + "/workItem/updateTemplate",
    CLOSE               :   window.baseUrl + "/workItem/closeEdit",
    UPDATE_TASK       :   window.baseUrl + "/workItem/updateTask"
};

/**
 * Function
 */
$(function () {

    // Cancel click event
    $("#cancelButton").on("click", function () {
        var requestParams = {
            workItemId: $("#workItemId").val()
        };
        ajaxAction(url.CLOSE, requestParams, function (data, res) {
            if (res == result.SUCCESS) {
                window.location.href = window.baseUrl + "/workItem/";
            } else {
                console.log("Back error: " + data);
            }
        })
    });

    // Button save click event
    $("#saveButton").on("click", function () {
        saveTemplate(eventTypeEnum.IS_BUTTON_CLICK);
    });

    // Change value of template event
    $(document).on('change', 'input', function() {
        isChangedData = true;
    });

    // Change task
    $(document).on('change', '#taskList', function() {
        var updateParams = {
            taskId : $.trim($("#taskList").val()),
            workItemId: $.trim($("#workItemId").val())
        };

        $.ajax({
            url         : url.UPDATE_TASK,
            async       : true,
            type        : "GET",
            data        : updateParams,
            success: function (data, textStatus, xhr) {
                console.log("update taskID successfully. data = " + data);
            },
            error: function (data, xhr, textStatus, errorThrown) {
                // TODO display system error page
                console.log("update taskID fail: " + errorThrown);
            }
        });
    });

    // Change template
    $(document).on('change', '#templateList', function() {
        isChangedTempate = true;
    });

    // Click node
    $("li .label-link").on("click", function () {
        var accessRight = $(this).attr("accessRight");
        if (accessRight == accessRightData.SO) {
            // Click process, workitem or folderitem
            focusNodeWhenError(this);
        } else {
            $(".label-link").removeClass("node-selected");
            $(this).addClass("node-selected");
        }
    });

    // Focus to first node when load screen
    $(".first-node .label-link:first").trigger("click");
});

/**
 * Focus to node when has error and do move to other node
 *
 * @param nextNode
 */
function focusNodeWhenError(nextNode) {
    var nextType = $(nextNode).attr("type");
    var currentType = $("#type").val();

    // Focusing to Process, WorkItem or Folder
    if (currentType != ItemTypeEnum.DOCUMENT) {

        // Next node is not document
        if (nextType != ItemTypeEnum.DOCUMENT) {

            // Focusing process, workitem or folderitem, click other item on tree (Not document)
            $(nextNode).removeClass("node-selected");
            $("." + $("#currentNode").val()).addClass("node-selected");
        } else if (nextType == ItemTypeEnum.DOCUMENT) {
            $(".current-fieldset").removeClass("current-fieldset");
        }
    } else {

        // Node is document
        var parentFolderClass = $(".current-document").attr("parentNode");
        $(nextNode).removeClass("node-selected");
        $("." + parentFolderClass).addClass("node-selected");
    }
}

/**
 * Show template data
 *
 * @param obj
 */
function showTemplate(obj) {
    if (isChangedData || isChangedTempate) {
        saveTemplate(eventTypeEnum.IS_LINK_CLICK, function (saveStatus) {
            if(saveStatus) {
                pShowTemplateData(obj)
            } else {
                focusNodeWhenError(obj);
            }
        });
    } else {
        pShowTemplateData(obj);
    }
}

/**
 * pShowTemplateData
 *
 * @param obj
 */
function pShowTemplateData(obj) {
    var typeTemplate = $(obj).attr("type");

    // Hide all document image
    if (typeTemplate != ItemTypeEnum.DOCUMENT) {
        hideAllDocumentImage();
    }

    // Load template
    var requestParams = {
        workspaceId: $("#workspaceId").val(),
        workItemId: $("#workItemId").val(),
        type: typeTemplate
    };
    var accessRight = $.trim($(obj).attr("accessRight"));
    if (typeTemplate ==  ItemTypeEnum.PROCESS) {
        requestParams["processId"] = $(obj).attr("processId");
    } else if (typeTemplate == ItemTypeEnum.WORKITEM) {
        // For maintain
    } else if (typeTemplate == ItemTypeEnum.FOLDER_ITEM) {
        // Show document image
        var folderItemNo = $(obj).attr("folderItemNo");

        // Display thumbnail
        $("#folder" + folderItemNo).find("svg").remove();
        if ((accessRight ==  accessRightData.RO)
            || (accessRight ==  accessRightData.RW) || accessRight ==  accessRightData.FULL) {
            loadThumbnail(folderItemNo, function() {
                showDocumentImageOfFolder(folderItemNo);
            });
        } else {
            showDocumentImageOfFolder(folderItemNo);
        }

        requestParams["folderItemNo"] = folderItemNo;
    } else if (typeTemplate == ItemTypeEnum.DOCUMENT) {
        requestParams["folderItemNo"] = $(obj).attr("folderItemNo");
        requestParams["documentNo"] = $(obj).attr("documentNo");
    }


    ajaxAction(url.SHOW_TEMPLATE, requestParams, function (data, res) {
        if (res == result.SUCCESS) {
            $("#template_area").html(data).promise().done(function(){
                $("#templateList").val($("#currentTemplateId").val());
                $("#accessRight").val(accessRight);
                var templateFieldSet = $("#template_area_fieldSet");
                templateFieldSet.attr("accessRight", accessRight);
                if(accessRight == accessRightData.RO) {
                    templateFieldSet.attr("disabled", true);
                    templateFieldSet.find("input").attr("disabled", true);
                } else if((accessRight == accessRightData.FULL) || (accessRight == accessRightData.RW)) {
                    templateFieldSet.attr("disabled", false);
                } else {
                    alert("No permission");
                }
            });
        } else {
            console.log("Error show template. requestParams = " + requestParams);
        }
        if (typeTemplate == ItemTypeEnum.DOCUMENT) {
            var documentLink = $(".field-document-image .document-link");
            documentLink.removeClass("current-document");
            documentLink.parent().closest('div').removeClass("current-fieldset");
            $(obj).addClass("current-document");
            $(obj).parent().closest('div').addClass("current-fieldset");
        }

        clearErrorMessage();
        isChangedData = false;
        isChangedTempate = false;
    })
}

/**
 * Reload template with newest selected template
 */
function setTemplateField() {
    var templateId = $("#templateList").val();
    var preTemplateId = $("#currentTemplateId").val();
    if (templateId == preTemplateId) {
        return;
    }
    if (isChangedData) {
        saveTemplate(eventTypeEnum.IS_CHANGED_LIST, function (saveStatus) {
            if(saveStatus) {
                pSetTemplateField(templateId);
            } else {
                $("#templateList").val(preTemplateId);
            }
        });
    } else {
        pSetTemplateField(templateId);
    }
}

/**
 * Private SetTemplateField
 *
 * @param templateId
 */
function pSetTemplateField(templateId) {
    if (templateId == constantValue.EMPTY) {
        $("#div_template").html(constantValue.EMPTY);
        $("#currentTemplateId").val(constantValue.EMPTY);
    } else {

        // Load template field
        var requestParams = {
            templateField: $("#templateList option:selected").attr("templateField"),
            templateId: templateId,
            folderItemNo: $("#folderItemNo").val(),
            workItemId: $("#workItemId").val(),
            processId: $("#processId").val(),
            documentNo: $("#documentNo").val(),
            type: $("#type").val()
        };
        ajaxAction(url.LOAD_TEMPLATE_FIELD, requestParams, function (data, res) {
            if (res == result.SUCCESS) {
                $("#div_template").html(data).promise().done(function(){
                    $("#currentTemplateId").val($("#templateList").val());
                });
            } else {
                // TODO Need to do something here
                console.log(data);
            }
        })
    }
}

/*
 * Show document image by folder
 */
function showDocumentImageOfFolder(folderItemNo) {
    $("#folder" + (folderItemNo)).removeClass("hidden");
}

/*
 * Hide all document image
 */
function hideAllDocumentImage() {
    $(".field-document-image").addClass("hidden");
}

function saveTemplateFromModal() {
    // Remove modal
    $('#closeEditWorkItemModal').modal('hide');

    // Validate data and do save
    saveTemplate(eventTypeEnum.IS_BUTTON_CLICK);
}

/**
 * Save template data
 */
function saveTemplate(eventType, callback) {
    removeClassError();
    var accessRight = $("#accessRight").val();
    var templateId = $.trim($("#templateList").val());
    if (eventType == eventTypeEnum.IS_CHANGED_LIST) {
        templateId = $.trim($("#currentTemplateId").val());
    }
    var updateParams;
    if (templateId == '') {
        updateParams = {
            workspaceId         :   $.trim($("#workspaceId").val()),
            templateId          :   null,
            eventType           :   $.trim(eventType),
            workItemId          :   $.trim($("#workItemId").val()),
            accessRight         :   $.trim($("#template_area_fieldSet").attr("accessRight"))
        }
    } else {
        // Get template data
        var templateData = [];
        $(".table_list>tbody>tr").each(function () {
            var item = {};
            var name = ($(this).find("input[name='fieldName']").val());
            item [name] = ($(this).find("input[name='fieldValue']").val());
            templateData.push(item);
        });
        var currentOption = $("#templateList option[value="+templateId+"]");
        updateParams = {
            workspaceId         :   $.trim($("#workspaceId").val()),
            templateId          :   templateId,
            templateField       :   $.trim(currentOption.attr("templateField")),
            templateTableName   :   $.trim(currentOption.attr("templateTableName")),
            templateType        :   $.trim(currentOption.attr("templateType")),
            templateName        :   $.trim(currentOption.text()),
            templateData        :   JSON.stringify(templateData),
            eventType           :   $.trim(eventType),
            workItemId          :   $.trim($("#workItemId").val()),
            accessRight         :   $.trim($("#template_area_fieldSet").attr("accessRight"))
        }
    }

    var itemType = $.trim($("#type").val());
    var urlUpdate = url.UPDATE_TEMPLATE;
    if (itemType == ItemTypeEnum.PROCESS) {

        // Update process
        updateParams["processId"] = $.trim($("#processId").val());
    } else if (itemType == ItemTypeEnum.WORKITEM) {

        // Update work item
        updateParams["task"] = $("#taskList").val();
    } else if (itemType == ItemTypeEnum.FOLDER_ITEM) {

        // Update folder Item
        updateParams["folderItemNo"] =  $.trim($("#folderItemNo").val());
    } else if (itemType == ItemTypeEnum.DOCUMENT) {

        // Update document
        updateParams["folderItemNo"] =  $.trim($("#folderItemNo").val());
        updateParams["documentNo"]   =  $.trim($("#documentNo").val());
    }

    $.ajax({
        url         : urlUpdate,
        async       : true,
        dataType    : 'html',
        type        : "POST",
        cache       : true,
        data        : updateParams,
        success: function () {
            if (eventType == eventTypeEnum.IS_BUTTON_CLICK) {
                window.location.href = window.baseUrl + "/workItem/";
            }
            clearErrorMessage();
            if ($.isFunction(callback)){
                callback(true);
            }
        },
        error: function (data) {
            clearErrorMessage();
            setErrorMessage(data.responseText, callback);
        }
    });
}

/**
 * Clear error message
 */
function clearErrorMessage() {
    $("#errorMessage").html(constantValue.EMPTY);
}

/**
 * Set error message
 *
 * @param errorMessage
 * @param callback Callback function
 */
function setErrorMessage(errorMessage, callback) {
    $("#errorMessage").html(errorMessage).promise().done(function() {
        $("#messages").find("li").each(function() {
            $("." + $(this).attr("data-code")).addClass("has-error");
        });
        $(".has-error:first").focus();
        if ($.isFunction(callback)){
            callback(false);
        }
    });
}

/**
 * Load document thumbnail
 *
 * @param folderItemNo
 * @param callback Callback function
 */
function loadThumbnail(folderItemNo, callback) {
    var documentNoList = getDocumentNoByFolder(folderItemNo);
    var documentTypeList = getDocumentTypeByFolder(folderItemNo);
    var documentPageCountList = getDocumentPageCountByFolder(folderItemNo);

    if (documentNoList.length <= 0) {
        if ($.isFunction(callback)){
            callback();
        }
        return;
    }

    for (i = 0; i < documentNoList.length; i++) {
        var documentNo = documentNoList[i];
        var documentType = documentTypeList[i];
        var pageCount = documentPageCountList[i];
        var token = $("#hdnSession").val();

        var params = 'workspaceId=' + $("#workspaceId").val()
            + '&workItemId=' + $("#workItemId").val()
            + '&folderItemNo=' + folderItemNo
            + '&documentNo=' + documentNo
            + '&token=' + token;
        var elementId = 'document_' + folderItemNo + "_" + documentNo;
        init(elementId, params, documentType, pageCount, callback);
    }
}

/**
 * Get document no in folder
 *
 * @param folderItemNo
 * @returns {Array}
 */
function getDocumentNoByFolder(folderItemNo) {
    var div = $("#folder"+ folderItemNo);
    var documentNoList = [];
    div.find("[name='documentNo']").each(function() {
        documentNoList.push($(this).val());
    });
    return documentNoList;
}

/**
 * Get document type in folder
 *
 * @param folderItemNo
 * @returns {Array}
 */
function getDocumentTypeByFolder(folderItemNo) {
    var div = $("#folder"+ folderItemNo);
    var documentNoList = [];
    div.find("[name='documentType']").each(function() {
        documentNoList.push($(this).val());
    });
    return documentNoList;
}

/**
 * Get page count of document in folder
 *
 * @param folderItemNo
 * @returns {Array}
 */
function getDocumentPageCountByFolder(folderItemNo) {
    var div = $("#folder"+ folderItemNo);
    var documentNoList = [];
    div.find("[name='pageCount']").each(function() {
        documentNoList.push($(this).val());
    });
    return documentNoList;
}

/**
 * Load template by ajax
 *
 * @param url
 * @param data
 * @param callback
 * @returns
 */
function ajaxAction(url, data, callback) {
    $.ajax({
        url         : url,
        async       : true,
        dataType    : 'html',
        type        : "POST",
        cache       : true,
        data        : data,
        success: function (data) {
            return callback(data, result.SUCCESS);
        },
        error: function (data, xhr) {
            return callback(xhr.status, result.ERROR);
        }
    });
}

/**
 * Remove error class
 */
function removeClassError() {
    $(".has-error").removeClass("has-error");
}

function defaultTabIndexForWorkItemList() {
    earth.defaultTabIndex();

    var addFormSearch = $('#addFormSearch');
    // template add screen
    addFormSearch.on('hidden.bs.modal', function () {
        earth.defaultTabIndex();
    });

    addFormSearch.on('shown.bs.modal', function () {
        $("#templateType").focus();
    });
}

$(function () {
    defaultTabIndexForWorkItemList();
});
