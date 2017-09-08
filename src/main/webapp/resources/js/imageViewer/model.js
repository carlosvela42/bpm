var WIDTH_DEFAULT = 1460;
var HEIGHT_DEFAULT = 0;

var ImageViewer = function () {
    documents = [];
    currentDocument = null;
    documentPath = null;

    this.zoomDefault = 0.1;
    this.panDefault = -0.1;

    existUser = false;
    layerActive = null;
    annotest = null;
    // Initialize A New Annotation by type is chosen on the Popup option.
    // Line,Rectangle,ecllipse.
    settingAnno = null;
    // Save Latest Annotation has just been drawn.
    drawAnno = null;
    // Save selected annotation from screen.
    selectedAnnos = [];
    // Save an annotation after processing copy or cut.
    clipBoardGroup = null;
    clipBoardAnnos = [];
    cutAnnos = [];

    // Rect template for Selecting one or mutiple svg elements.
    sMultipleSvg = null;

    allAnnos = [];

    // Transform info.
    currentRotation = 0;
    copyTimeRotation = 0;
    currentScale = 1;

    // Contain all the contents of image document.
    svg = null;
    svgStartLeft = 0;
    svgStartTop = 0;
    svgId = null;

    imgId = null;
    imgWidth = null;
    imgHeight = null;

    drawStartX = null;
    drawStartY = null;

    scrollTop=0;
    scrollLeft=0;

    selectedGroup = null;
    hiddenRect = null;
    this.toolbarFlg = toolbars.BTN_SELECT;
    copyCutFlg = false;
    drawFlg = false;
    selectedFlg = false;
};

var popup = {
    LINE : "#myModalLine",
    TEXT:"#myModalText",
    COMMENT:"#myModalComment"
}

var waitMouseUpFlg = false;
var deleteLayerFlag = true;


var ctrlDown = false;
var CTRLKEY = 17;
var CMDKEY = 91;
var VKEY = 86;
var CKEY = 67;
var clickOnSelectedBound = false;
var layerNoMax = 0;
var checkGrayscale = true;
var tmpContrast = 0;
var tmpBrightness = 0;
var tmpCheckGrayscale = true;
var xGrayscale = null;
var filterChild = null;
var userInfo = window.userId;
var templateLayerName = "";
var commentImage = "resources/images/imageViewer/rsz_commenticon.jpg";
var cursorImage = "resources/images/imageViewer/Pen-50.png";

var result = {
    SUCCESS: 1,
    ERROR: 0
};

var hideMenu = ['.icon_01','.icon_03','.icon_04','.icon_05','.icon_06'];

var fonts = [8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72];

var penColors = ['Black', 'White', 'Red', 'Green', 'Blue', 'Yellow', 'Cyan', 'Purple',
    'Darkgrey', 'Mediumpurple', 'Darkred', 'Lightgreen', 'Lightblue', 'Lightyellow', 'Lightcyan', 'Grey'];

var colors = ['000000', '800000', '8b4513', '2e4f4f', '00807f', '000080', '4b0082', '696969', 'b22122', 'a52a2a'
    , 'daa51f', '006400', '40e0d1', '0001cc', '800080', '808080', 'ff0000', 'ff8c00', 'ffd700', '008100', '02ffff'
    , '0000ff', 'ed82ee', 'a9a9a9', 'ffa07a', 'ffa500', 'ffff02', '00ff02', 'afeeed', 'add8e6', 'dda0dd', 'd3d3d3'
    , 'fff0f5', 'faebd7', 'ffffdf', 'efffef', 'f0ffff', 'f0f8ff', 'e6e5f9', 'none'];

var family = ["cursive", "monospace", "serif", "sans-serif", "fantasy", "default", "Arial", "Arial Black",
    "Arial Narrow", "Arial Rounded MT Bold", "Bookman Old Style", "Bradley Hand ITC", "Century",
    "Century Gothic", "Comic Sans MS", "Courier", "Courier New", "Georgia", "Gentium", "Impact", "King",
    "Lucida Console", "Lalit", "Modena", "Monotype Corsiva", "Papyrus", "Tahoma", "TeX", "Times",
    "Times New Roman", "Trebuchet MS", "Verdana", "Verona"];

var documentType = {
    IMAGE: "2",
    PDF: "3",
    TIFF: "1"
};

var rotateType = {
    RIGHT: 1,
    LEFT: -1
};

var propertiesType = {
    penColor: "#000000",
    fillColor: "none",
    highlightColor: "#ffff02",
    lineSize: 1,
    fontFamily: "Times New Roman",
    fontStyle: "Regular",
    fontSize: "18"
};

var toolbars = {
    BTN_SELECT: "#select",
    BTN_ZOOMFP: "#zoomFullPage",
    BTN_ZOOMFW: "#zoomFullWidth",
    BTN_ZOOM200: "#zoom200",
    BTN_ZOOM100: "#zoom100",
    BTN_ZOOM75: "#zoom75",
    BTN_ZOOM50: "#zoom50",
    BTN_ZOOMIN: "#zoomin",
    BTN_ZOOMOUT: "#zoomout",
    BTN_ROTATE_LEFT: "#rotateC",
    BTN_ROTATE_RIGHT: "#rotate",
    BTN_COPY: "#copy",
    BTN_CUT: "#cut",
    BTN_PASTE: "#paste",
    BTN_PROPERTIES: "#properties",
    BTN_OKPROPERTIES: "#okProperties",
    BTN_CANCELPROPERTIES: "#cancelProperties",
    BTN_TEXTPROPERTIES: "#textProperties",
    BTN_COMMENTPROPERTIES: "#commentProperties",
    BTN_CANCELCOMMENTPROPERTIES: "#cancelCommentProperties",
    BTN_PRINTALL: "#print",
    BTN_PRINTIMAGE: "#print0",
    BTN_LAYER: "#btnLayer",
    BTN_LAST: "#last",
    BTN_NEXT: "#next",
    BTN_PREVIOUS: "#previous",
    BTN_FIRST: "#first",
    BTN_ADDLAYER: "#addLayer",
    BTN_REMOVELAYER: "#removeLayer",
    BTN_RENAMELAYER: "#renameLayer",
    BTN_ACTIVELAYER: "#activeLayer",
    BTN_DISPLAYLAYER: "#displayLayer",
    BTN_OKLAYER: "#okLayer",
    BTN_OKLAYERDELETE: "#okLayerDelete",
    BTN_GRAYSCALE: "#btnGrayscale",
    BTN_OKGRAYSCALE: "#okGrayscale",
    BTN_CANCELGRAYSCALE: "#cancelGrayscale",
    BTN_CONTROLS: "#controls",
    BTN_CONTROLS1: "#controls1",
    BTN_CBOX1: "#cbox1",
    BTN_SAVEIMAGE: "#saveImage",
    BTN_SUBIMAGE: "#subImage"
};

var textExample = {
    COMMENTTEXT: "#commentText",
    FONTSTYLEID: "#fontStyle",
    INPUTCOLOR: "input[name=color]:checked",
    INPUTCOLOR1: "input[name=color]",
    INPUTFILL: "input[name=fill]:checked",
    INPUTHIGHLIGHT: "input[name=highlight]:checked",
    WIDTH: '#width',
    COLOR: '#color',
    COLORSELECT: '#color option:selected'
}

var commentExample = {
    COMMENTTEXTBOX: "#tbComment",
    COMMENTTEXTAREA: '#commentTxtArea',
    COMMENTPOPUP: "#myModalComment",
    COMMENTCLASS: "newComment"
}

var annoTypes = {
    LINE: "#line",
    RECTANGLE: "#rectangle",
    TEXT: "#text",
    HIGHLIGHT: "#highlight",
    COMMENT: "#comment"
};