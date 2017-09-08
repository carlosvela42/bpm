package co.jp.nej.earth.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {
    public static final String JPG_TYPE = "jpg";
    public static final String PNG_TYPE = "png";
    public static final int WIDTH_DEFAULT = 500;
    public static final int HEIGHT_DEFAULT = 500;

    public static byte[] getThumbnail(byte[] imageData, Integer width, Integer height, String outPutType) {
        ByteArrayInputStream in = new ByteArrayInputStream(imageData);
        try {
            BufferedImage img = ImageIO.read(in);
            if (height == null || height.intValue() == 0) {
                height = HEIGHT_DEFAULT;
            }

            if (width == null || width.intValue() == 0) {
                width = WIDTH_DEFAULT;
            }

            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ImageIO.write(imageBuff, outPutType, buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}