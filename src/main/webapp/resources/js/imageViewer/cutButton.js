ImageViewer.prototype.onCut = function() {
    cutAnnos = [];
    copyTimeRotation = currentRotation;
    resetClipBoard();
    copyArray(clipBoardAnnos, selectedAnnos);
    copyArray(cutAnnos, selectedAnnos);
    unSelectMultipleSvg();
    for (var i in clipBoardAnnos) {
        SVG.get(clipBoardAnnos[i]).hide();
    }
}