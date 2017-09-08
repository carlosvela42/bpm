$(document).on('keydown', function (e) {
    // You may replace `c` with whatever key you want
    if(!$(popup.LINE).hasClass('in')&&!$(popup.TEXT).hasClass('in')&&!$(popup.COMMENT).hasClass('in')){
    if ((e.metaKey || e.ctrlKey) && ( String.fromCharCode(e.which).toLowerCase() === 'x')) {
        iv.onCut();
    }
    else if ((e.metaKey || e.ctrlKey) && ( String.fromCharCode(e.which).toLowerCase() === 'c')) {
        onCopy();
    }
    else if ((e.metaKey || e.ctrlKey) && ( String.fromCharCode(e.which).toLowerCase() === 'v')) {
        onPaste();
    }}
});