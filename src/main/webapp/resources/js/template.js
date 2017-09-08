function getIdIncrement() {
    var id = -1;
    $(".template-row").each(function () {
        var currentId = parseInt($(this).data('row-id'));
        if (currentId > id) {
            id = currentId;
        }
    });
    return id + 1;
}

function checkEmptyAnMaxSize(field, size, fieldName) {
    if (field == "") {
        earth.addModalMessage("E0001", fieldName);
        return true;
    }
    if(earth.isLargerThan(field, size)){
        earth.addModalMessage("E0026", fieldName, size);
        return true;
    }
    return false;
}

$(function () {
    $("#addField").click(function () {
        $('#addFieldModal').find("table .table_body").html(earth.buildHtml("#template-edit-row-template"));
        openModal();
    });
    $("#tab1").click(function () {
        $(this).children().css('color','#0077E5');
        $(this).children().css('font-weight','bold');
        $(this).children().css('background-color','#fff');
        $("#tab2").children().css('color','black');
        $("#tab2").children().css('font-weight','');
        $("#tab2").children().css('background-color','#E6E6E6');
    });

    $("#tab2").click(function () {
        $(this).children().css('color','#0077E5');
        $(this).children().css('font-weight','bold');
        $(this).children().css('background-color','#fff');
        $("#tab1").children().css('color','black');
        $("#tab1").children().css('font-weight','');
        $("#tab1").children().css('background-color','#E6E6E6');
    });

    $('#addRow').click(function () {
        var name = $('#name').val();
        var description = $('#description').val();
        var typeDisplay = $('#fieldType option:selected').text();
        var typeValue = $('#fieldType option:selected').val();
        var size = $('#size').val();
        var required = $('#required').is(':checked');
        var encrypted = $('#encrypted').is(':checked');
        var fieldName = earth.Messages['field.name'];
        var fieldDescription = earth.Messages['field.description'];
        var fieldSize = earth.Messages['field.size'];

        earth.removeAllModalMessage();
        if (checkEmptyAnMaxSize(name, 30, fieldName)) {
            return;
        }

        if(isSameName()) {
            earth.addModalMessage("E0003", fieldName)
            return;
        }

        if (checkEmptyAnMaxSize(description, 255, fieldDescription)) {
            return;
        }

        if (typeDisplay == 'NVARCHAR2') {
            if (size == "") {
                earth.addModalMessage("E0001", fieldSize);
                return;
            }

            if(!(/^\d+$/.test(size))) {
                earth.addModalMessage("E0008", fieldSize);
                return;
            }

            if (parseInt(size) > 255) {
                earth.addModalMessage("E0006", fieldSize, 255);
                return;
            }

            if (parseInt(size) < 1) {
                size = 1;
            }
        }

        var fieldId = $("#fieldId").val();
        var isAddNew = false;
        if (fieldId === '') {
            fieldId = getIdIncrement();
            isAddNew = true;
        }
        var row = earth.buildHtml("#template-row-template", {
            i: fieldId, name: name, description: description,
            typeDisplay: typeDisplay, typeValue: typeValue, size: size,
            size: size, required: required, encrypted: encrypted
        });
        if (isAddNew) {
            $('#fieldList tbody').append(row);
        } else {
            $("#fieldList tr[data-row-id='" + fieldId + "']").replaceWith(row);
        }

        $('#name').val("");
        $('#description').val("");
        $('#type').val("");
        $('#size').val("");
        $('#required').prop("checked",
            false);
        $('#encrypted').prop("checked",
            false);
        $("#message").text("");

        // close popup
        closeModal();
    });

    window.editTemplateRow = function (i) {
        var context = {}
        $(".template-row[data-row-id='" + i + "']").find("[data-name]").each(function () {
            context[$(this).data("name")] = $(this).attr("value");
        });
        context["id"] = i;

        $('#addFieldModal').find("table .table_body").html(earth.buildHtml("#template-edit-row-template", context));
        openModal();
    }

    function openModal() {
        earth.removeModalMessage(["E0001", "E0003", "E0006", "E0008", "E0026"]);
        disableFieldsOnEdit();
        sizeChange();
        $('#addFieldModal').modal('show');
    }

    function closeModal() {
        $('#addFieldModal').modal('hide');
    }

    $('#clearRow').click(function () {
        $('#name').val("");
        $('#description').val("");
        $('#type').val("");
        $('#size').val("");
        $('#required').prop("checked", false);
        $('#encrypted').prop("checked", false);
    })

    $("#removeField").click(function (e) {
        e.preventDefault();
        $("#fieldList").find(".template-row [name=DeleteRow]").each(function () {
            if ($(this).is(":checked")) {
                $(this).parents("tr").remove();
            }
        })
    });

    function isSameName() {
    	var currentId = $(".addTemplate #fieldId")[0].value;
    	var currentValue = $(".addTemplate #name")[0].value
    	var listOfField = $("#userTbody input:hidden[name*='.name']");
    	
    	// there is only one field, no need
    	if(listOfField.length == 0 ) {
    		return false;
    	} 
    		
    	// have two or more
    	for(var index = 0; index < listOfField.length; index++) {
    		fieldName = listOfField[index];
    	

    		var name = fieldName.name; 
    		var value = fieldName.value; 
    		var matches = name.match(/\[(.*?)\]/);
    		var id = "-1";
    		if (matches) {
                id = matches[1];
    		}
    		
			if(currentId != id){
				if(value == currentValue) {
					return true;
				}
			}
    	}
    	return false;
    }
    
    function sizeChange() {
        var fieldType = $('#fieldType option:selected').text();
        var isDisabled = (fieldType == 'NUMBER' || fieldType == 'INTEGER');
        $('#size').prop("disabled", isDisabled || isPersisted());
    }

    function isPersisted() {
        return ($("#isPersisted")[0].value == 'Y');
    }
    function disableFieldsOnEdit() {
        var isDisable = isPersisted();
        $('#name').prop("disabled", isDisable);
        $('#fieldType').prop("disabled", isDisable);
        $('#size').prop("disabled", isDisable);
        $('#required').prop("disabled", isDisable);
        $('#encrypted').prop("disabled", isDisable);

    }

    $("#addFieldModal").on('change','#fieldType',sizeChange);

    earth.onDeleteButtonClick(function () {
        var templateIds = [];
        $('#templateTbody > tr').each(function () {
            if ($(this).find('.deleteCheckBox').prop('checked')) {
                templateIds.push($(this).attr('templateId'));
            }
        });
        if (templateIds.length > 0) {
            $.form(window.baseUrl + "/template/deleteList", {
                "listIds": templateIds,
                workspaceId: $('#workspaceId').val()
            }).submit();
        } else {
            earth.addMessage("E1014");
            return;
        }
    },earth.Messages['template']);
});

function defaultTabIndexForAddTemplate() {
  earth.defaultTabIndex();

  // template add screen
  $('#addFieldModal').on('hidden.bs.modal', function () {
    earth.defaultTabIndex();
  });

  $('#addFieldModal').on('shown.bs.modal', function () {
    earth.defaultTabIndex();
    $('#addFieldModal .addTemplate input[type="text"]:not([disabled])').first().focus();
  });
}

$(document).ready(function () {
  defaultTabIndexForAddTemplate();
});

