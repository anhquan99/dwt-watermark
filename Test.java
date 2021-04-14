import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Test {

  public static void main(String[] args) throws Exception {
    // WaterMark.RM_RP(
    //   "laptop.jpg",
    //   "noon.jpg",
    //   "Lena.png",
    //   1
    // );
    // WaterMark.inverse_RM_RP("laptop.jpg", "laptopRM_RP.png", 1);
    WaterMark.PT_AME("laptop.jpg", "Samurai.jpg", "glass.jpg", "noon.jpg", "Wallpaper-7.jpg", 3);
    WaterMark.inverse_PT_AME("laptop.jpg", "laptopPT_AME.jpg", 3);
  }

  public static void saveImgFile(double[][] input, String name)
    throws IOException {
    BufferedImage bufferedOut = new BufferedImage(
      input.length,
      input[0].length,
      BufferedImage.TYPE_INT_RGB
    );
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[0].length; ++j) {
        int a = (int) input[i][j];
        int b = (int) input[i][j];
        int c = (int) input[i][j];
        if (a > 255) {
          a = 255;
        }
        if (a < 0) a = 0;
        if (b > 255) b = 255;
        if (b < 0) b = 0;
        if (c > 255) c = 255;
        if (c < 0) c = 0;
        Color newColor = new Color(a, b, c);
        bufferedOut.setRGB(i, j, newColor.getRGB());
      }
    }
    File fileOut = new File(name + ".jpg");
    System.out.println(fileOut.getAbsolutePath());
    ImageIO.write(bufferedOut, "jpg", fileOut);
  }
  public static void saveImgFileColor(double[][] red, double[][] green, double[][] blue, String name)
    throws IOException {
    BufferedImage bufferedOut = new BufferedImage(
      red.length,
      red[0].length,
      BufferedImage.TYPE_INT_RGB
    );
    for (int i = 0; i < red.length; i++) {
      for (int j = 0; j < red[0].length; ++j) {
        int a = (int) red[i][j];
        int b = (int) green[i][j];
        int c = (int) blue[i][j];
        if (a > 255) {
          a = 255;
        }
        if (a < 0) a = 0;
        if (b > 255) b = 255;
        if (b < 0) b = 0;
        if (c > 255) c = 255;
        if (c < 0) c = 0;
        Color newColor = new Color(a, b, c);
        bufferedOut.setRGB(i, j, newColor.getRGB());
      }
    }
    File fileOut = new File(name + ".jpg");
    System.out.println(fileOut.getAbsolutePath());
    ImageIO.write(bufferedOut, "jpg", fileOut);
  }
  public static void saveImgFileRaw(double[][] input, String name)
    throws IOException {
    BufferedImage bufferedOut = new BufferedImage(
      input.length,
      input[0].length,
      BufferedImage.TYPE_INT_RGB
    );
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[0].length; ++j) {
        bufferedOut.setRGB(i, j, (int) input[i][j]);
      }
    }
    File fileOut = new File(name + ".jpg");
    System.out.println(fileOut.getAbsolutePath());
    ImageIO.write(bufferedOut, "jpg", fileOut);
  }
}
