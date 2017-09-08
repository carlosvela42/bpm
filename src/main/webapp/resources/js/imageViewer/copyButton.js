function onCopy() {
    copyTimeRotation = currentRotation;
    resetClipBoard();

    // Copy clipBoardAnnos from selectedAnnos.
    copyArray(clipBoardAnnos, selectedAnnos);
}