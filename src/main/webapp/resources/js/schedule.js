$(function () {
    earth.onDeleteButtonClick(function () {
        var scheduleIds = [];
        $('#scheduleTbody > tr').each(function () {
            if ($(this).find('.deleteCheckBox').prop('checked')) {
                scheduleIds.push($(this).attr('scheduleId'));
            }
        });
        //console.log(scheduleIds);
        //console.log($('#workspaceSelection').val());

        var searchColumns0 = $("#searchColumns\\[0\\]").val();
        var searchColumns1 = $("#searchColumns\\[1\\]").val();
        var searchColumns2 = $("#searchColumns\\[2\\]").val();
        var searchColumns3 = $("#searchColumns\\[3\\]").val();
        var searchColumns4 = $("#searchColumns\\[4\\]").val();
        var searchColumns5 = $("#searchColumns\\[5\\]").val();
        
        if (scheduleIds.length > 0) {
            $.form(window.baseUrl + "/schedule/deleteList", {"listIds": scheduleIds,
            	"searchColumns[0]": searchColumns0,
            	"searchColumns[1]": searchColumns1,
            	"searchColumns[2]": searchColumns2,
            	"searchColumns[3]": searchColumns3,
            	"searchColumns[4]": searchColumns4,
            	"searchColumns[5]": searchColumns5,
                workspaceId: $('#workspaceId').val()}).submit();
        } else {
            earth.addMessage("E1014");
            return;
        }
    },earth.Messages['schedule']);

    $('#enable').click(function() {
		$("#enableDisable").val($('#enable').val());
		$('#disable').prop('checked',false);
		
	});
	$('#disable').click(function() {
		$("#enableDisable").val($('#disable').val());
		$('#enable').prop('checked',false);
	});
});

function updateTaskSelectByProcess() {
  var defaultValue = null;
  var processId = $("#processName").val();
    $('#taskName').html($('#taskNameOrigin').html());
  $("#taskName option").each(function () {
    console.log("aa");
    if($(this).data("processid") != processId) {
      $(this).remove();
    } else {
      if(!defaultValue) {
        defaultValue = $(this).attr("value");
      }
    }
  });
    if (onLoaded === "1") {
        $("#taskName").val(defaultValue);
    }

}
var onLoaded="0";
$(document).ready(function () {
  updateTaskSelectByProcess();

  $("#processName").change(function () {
      onLoaded="1";
    updateTaskSelectByProcess();
    forRefreshInIE("taskName");
  })
});

$(document).ready(function () {
  earth.defaultTabIndex();
})

