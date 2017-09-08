window.earth = window.earth || {}

// Show and close progress view
earth.showProgress = function () {
  $('#progressPopup').removeClass('hidden');
};

earth.closeProgress = function () {
  $('#progressPopup').addClass('hidden');
};

// Earth default tab index, if you need another tabindex layout, do not call this function in jquery ready
earth.defaultTabIndex = function () {
  // all input and tab is set to same tabindex, so that the tab is layout automatically from up to bottom, left to right
  $('#content').find('input,a,button,select').attr('tabindex', 2);
  // span that work as button
  $('span.focusable').attr('tabindex', 2);

  //next is cancel button in edit view
  $('a.btn_confirm_delete').removeAttr('tabindex');
  $('button#cancelButton').attr('tabindex', 3);
  //next is delete button in listview
  $('button#deleteButton').attr('tabindex', 3);
  // next is workspace selection
  $("#workspaceSelection button.name").attr('tabindex', -1);
  $("#workspaceSelection button.dropdown-toggle").attr('tabindex', 9);

  //last is logout button
  $('.logout a').attr('tabindex', 10);

  //default index in modal is began from 100(firs level modal)
  if($(".modal").length > 0 ) {
    // handle multiple modal
    $(".modal").find("input,a,select,button").attr('tabindex', 100);
    $(".modal button.btn_cancel").attr('tabindex', 101);
    $(".modal button.btn_cancel_popup").attr('tabindex', 101);
    $(".modal button.close").attr('tabindex', 110);
  }
};

earth.layoutModelTabIndex = function(modal, startTabIndex) {
  // handle multiple modal
  modal.find("input,a,select,button").attr('tabindex', startTabIndex);
  modal.find("button.btn_cancel").attr('tabindex', startTabIndex + 1);
  modal.find("button.btn_cancel_popup").attr('tabindex', startTabIndex + 1);
  modal.find("button.close").attr('tabindex', startTabIndex + 10);
};

earth.isLargerThan = function (string, maxSize) {
  return string.length > maxSize;
}
