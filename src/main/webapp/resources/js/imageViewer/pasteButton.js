function onPaste() {
    if (clipBoardAnnos.length > 0) {
        unSelectMultipleSvg();
        cloneArray(selectedAnnos, clipBoardAnnos);
        copyArray(allAnnos, selectedAnnos);
        iv.selectedGroup = iv.svg.group();
        displaySelectedAnnos();
        // Update transform as copy time.
        if (selectedAnnos.length == 1) {
            // smartTransform("#" + e.node.id, copyTimeRotation -
            // currentRotation);
            SVG.get(selectedAnnos[0]).rotate(copyTimeRotation - currentRotation);
            // smartTransform(selectedAnnos[0], (copyTimeRotation - currentRotation));
        } else {
            // smartTransform("#" + iv.selectedGroup.node.id, copyTimeRotation -
            // currentRotation);
            for (var i in selectedAnnos) {
                SVG.get(selectedAnnos[i]).rotate(copyTimeRotation - currentRotation);
                // smartTransform(selectedAnnos[i], (copyTimeRotation - currentRotation));
            }
        }
        resetClipBoard();
        copyArray(clipBoardAnnos, selectedAnnos);
    }
}