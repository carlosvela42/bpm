package co.jp.nej.earth.model;

import org.springframework.data.geo.Point;

public class Annotation {
    private Integer color;
    private Integer fillColor;
    private String fontName;
    private Integer graphicsType;
    private String imageName;
    private String label;
    private Integer annotationRotation;
    private Integer mode;
    private String owner;
    private double pensize;
    private Point point;
    private boolean readOnly;
    private String text;
    private double textThorizonalScale;
    private Integer textStyle;

    /**
     * @return the color
     */
    public Integer getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * @return the fillColor
     */
    public Integer getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor the fillColor to set
     */
    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the fontName
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * @param fontName the fontName to set
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    /**
     * @return the graphicsType
     */
    public Integer getGraphicsType() {
        return graphicsType;
    }

    /**
     * @param graphicsType the graphicsType to set
     */
    public void setGraphicsType(int graphicsType) {
        this.graphicsType = graphicsType;
    }

    /**
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the annotationRotation
     */
    public Integer getAnnotationRotation() {
        return annotationRotation;
    }

    /**
     * @param annotationRotation the annotationRotation to set
     */
    public void setAnnotationRotation(int annotationRotation) {
        this.annotationRotation = annotationRotation;
    }

    /**
     * @return the mode
     */
    public Integer getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the pensize
     */
    public double getPensize() {
        return pensize;
    }

    /**
     * @param pensize the pensize to set
     */
    public void setPensize(double pensize) {
        this.pensize = pensize;
    }

    /**
     * @return the point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the textThorizonalScale
     */
    public double getTextThorizonalScale() {
        return textThorizonalScale;
    }

    /**
     * @param textThorizonalScale the textThorizonalScale to set
     */
    public void setTextThorizonalScale(double textThorizonalScale) {
        this.textThorizonalScale = textThorizonalScale;
    }

    /**
     * @return the textStyle
     */
    public Integer getTextStyle() {
        return textStyle;
    }

    /**
     * @param textStyle the textStyle to set
     */
    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }
}
