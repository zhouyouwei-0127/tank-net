package com.zyw.tanktest;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * test load image for tank
 */
public class ImageTest {

    @Test
    public void testLoadImage() throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif")));
        assert image != null;
    }
}
