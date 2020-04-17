package agorithim;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class VCEncode {

    static final String PATHIMAGE = ".\\pictures\\root\\steg.png";

    public  void Encode(String name_forder) {
        BufferedImage theImage = readImageFile(PATHIMAGE);
        Random rand = new Random();
        int width = theImage.getWidth();
        int height = theImage.getHeight();

        BufferedImage imageShare1 = new BufferedImage(width, height*2, BufferedImage.TYPE_INT_RGB );
        BufferedImage imageShare2 = new BufferedImage(width, height*2, BufferedImage.TYPE_INT_RGB );

        for(int w = 0; w < width; w++){
            for (int h = 0; h < height;h++){
                //get pixel value
                int p = theImage.getRGB(w,h);
                System.out.println(rand.nextInt(2));
                if(p==-1){
                    if(rand.nextInt(2)==1){
                        imageShare1.setRGB(w,h*2,-16777216);
                        imageShare1.setRGB(w,h*2 +1,-1);
                        imageShare2.setRGB(w,h*2,-1);
                        imageShare2.setRGB(w,h*2 +1,-16777216);
                    }else{
                        imageShare1.setRGB(w,h*2,-1);
                        imageShare1.setRGB(w,h*2 +1,-16777216);
                        imageShare2.setRGB(w,h*2,-16777216);
                        imageShare2.setRGB(w,h*2 +1,-1);
                    }
                }else{
                    if(rand.nextInt(2)==1){
                        imageShare1.setRGB(w,h*2,-1);
                        imageShare1.setRGB(w,h*2 +1,p);
                        imageShare2.setRGB(w,h*2,-1);
                        imageShare2.setRGB(w,h*2 +1,p);
                    }else{
                        imageShare1.setRGB(w,h*2,p);
                        imageShare1.setRGB(w,h*2 +1,-1);
                        imageShare2.setRGB(w,h*2,p);
                        imageShare2.setRGB(w,h*2 +1,-1);
                    }
                }
            }
        }
        boolean path = new File(".\\pictures\\" +name_forder).mkdir();
        
        File share1 = new File(".\\pictures\\"+name_forder+"\\share1.png");
        File share2 = new File(".\\pictures\\"+name_forder+"\\share2.png");
        try {
            ImageIO.write(imageShare2, "png", share2);
            ImageIO.write(imageShare1, "png", share1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
			File filedelete = new File(PATHIMAGE);
			filedelete.delete();
		}

    }

    public static BufferedImage readImageFile(String COVERIMAGEFILE) {
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



