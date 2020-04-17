package agorithim;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VCDecode {
    public String Decode(String path1, String path2, String rootpath) {
        BufferedImage imageShare1 = readImageFile(path1);
        BufferedImage imageShare2 = readImageFile(path2);


        int width = imageShare1.getWidth();
        int height = imageShare1.getHeight()/2;
        System.out.println("widght: " + width + "height :" + height);
        BufferedImage imageDecode = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );


        for(int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if ((imageShare1.getRGB(w, h * 2) == -1 )&& (imageShare1.getRGB(w, h * 2 + 1) == -16777216) &&( imageShare2.getRGB(w, h * 2) == -16777216) && (imageShare2.getRGB(w, h * 2 + 1) == -1)) {
                    imageDecode.setRGB(w, h, -1);
                } else if ((imageShare1.getRGB(w, h * 2) == -16777216) &&( imageShare1.getRGB(w, h * 2 + 1) == -1) &&( imageShare2.getRGB(w, h * 2) == -1) && (imageShare2.getRGB(w, h * 2 + 1) == -16777216)) {
                    imageDecode.setRGB(w, h, -1);
                } else {
                    int p1 = imageShare1.getRGB(w,h*2 +1);
                    int p2 = imageShare1.getRGB(w,h*2);
                    if(p1!=-1)
                        imageDecode.setRGB(w, h, p1);
                    else{
                        imageDecode.setRGB(w, h, p2);
                    }
                }
            }
        }
        File image = new File(rootpath+"\\imageDecode.png");
        try {
            ImageIO.write(imageDecode, "png", image);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(width);
		return image.getPath();
    }

    public BufferedImage readImageFile(String COVERIMAGEFILE) {
        BufferedImage theImage = null;
        File p = new File(COVERIMAGEFILE);
        try {
            theImage = ImageIO.read(p);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return theImage;
    }

}
