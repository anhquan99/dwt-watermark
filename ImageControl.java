import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class ImageControl {

  public static double[][] getMatrix(String fileName) {
    try {
      String fileNameStr = getStrPath(fileName);
      File file = new File(fileNameStr);
      BufferedImage bufferedImage = ImageIO.read(file);
      double image[][] = new double[bufferedImage.getWidth()][bufferedImage.getHeight()];
      for (int i = 0; i < bufferedImage.getWidth(); ++i) {
        for (int j = 0; j < bufferedImage.getHeight(); ++j) {
          int rgb = bufferedImage.getRGB(i, j);
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = (rgb & 0xFF);

          int grayLevel = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

          image[i][j] = grayLevel;
        }
      }
      return image;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static double[][] getMatrixText(String fileName) throws Exception {
    Runtime cmd = Runtime.getRuntime();
    Process process = cmd.exec(String.format("cmd.exe /c " + ".\\openDWT.py " + fileName));
    process.waitFor();
    StringBuilder builder = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String tempLine;
    while((tempLine = reader.readLine()) != null){
      builder.append(tempLine);
    }
    if(!builder.toString().equalsIgnoreCase("success")) throw new Exception("File invalid \n" + builder.toString());
    Scanner sc = new Scanner(new BufferedReader(new FileReader("imgMatrix.txt")));
    int rows = 0, columns = 0;
    while(sc.hasNextLine()) {
      String[] line = sc.nextLine().trim().split(" ");
      columns = line.length;
      rows++;
    }
    double[][] myArray = new double[rows][columns];
    sc = new Scanner(new BufferedReader(new FileReader("imgMatrix.txt")));
    while(sc.hasNextLine()) {
      for(int i = 0; i < myArray.length; i++){
        String[] line = sc.nextLine().trim().split(" ");
        for(int j = 0; j < myArray[0].length; j++){
          myArray[i][j] = Double.parseDouble(line[j]);
        }
      }
    }
    return myArray;
  }

  public static double[][] getMatrixRaw(String fileName) {
    try {
      String fileNameStr = getStrPath(fileName);
      File file = new File(fileNameStr);
      BufferedImage bufferedImage = ImageIO.read(file);
      double image[][] = new double[bufferedImage.getWidth()][bufferedImage.getHeight()];
      for (int i = 0; i < bufferedImage.getWidth(); ++i) {
        for (int j = 0; j < bufferedImage.getHeight(); ++j) {
          int rgb = bufferedImage.getRGB(i, j);
          image[i][j] = rgb;
        }
      }
      return image;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static double[][] makeBinary(String fileName) {
    try {
      String fileNameStr = getStrPath(fileName);
      File file = new File(fileNameStr);
      BufferedImage bufferedImage = ImageIO.read(file);

      // BufferedImage bufferedOut = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

      double image[][] = new double[bufferedImage.getWidth()][bufferedImage.getHeight()];

      for (int i = 0; i < bufferedImage.getWidth(); ++i) {
        for (int j = 0; j < bufferedImage.getHeight(); ++j) {
          int rgb = bufferedImage.getRGB(i, j);
          int r = (0x00ff0000 & rgb) >> 16; //red
          int g = (0x0000ff00 & rgb) >> 8; //green
          int b = (0x000000ff & rgb); //blue
          int avg = (r + b + g);

          if (avg >= 383) {
            image[i][j] = 1;
            // image[i][j] = Color.WHITE.getRGB();
            // bufferedOut.setRGB(i, j, Color.WHITE.getRGB());
          } else {
            image[i][j] = 0;
            // image[i][j] = Color.BLACK.getRGB();
            // bufferedOut.setRGB(i, j, Color.BLACK.getRGB());
          }
        }
      }
      return image;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static double[][] makeBinaryMatrix(double[][] matrix) {
    try {
      double image[][] = new double[matrix.length][matrix[0].length];
      for (int i = 0; i < matrix.length; ++i) {
        for (int j = 0; j < matrix[0].length; ++j) {
          int rgb = (int) matrix[i][j];
          int r = (0x00ff0000 & rgb) >> 16; //red
          int g = (0x0000ff00 & rgb) >> 8; //green
          int b = (0x000000ff & rgb); //blue
          int avg = (r + b + g);

          if (avg >= 383) {
            image[i][j] = 1;
            // bufferedOut.setRGB(i, j, Color.WHITE.getRGB());
          } else {
            image[i][j] = 0;
            // bufferedOut.setRGB(i, j, Color.BLACK.getRGB());
          }
        }
      }
      return image;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static double[][] getRed(double[][] img) {
    double image[][] = new double[img.length][img[0].length];
    for (int x = 0; x < img.length; ++x) {
      for (int y = 0; y < img[0].length; ++y) {
        int rgb = (int) img[x][y];
        int r = (rgb >> 16) & 0xFF;
        image[x][y] = r;
      }
    }
    return image;
  }

  public static int getRedInt(double img) {
    int image = 0;
    int rgb = (int) img;
    int r = (rgb >> 16) & 0xFF;
    image = r;
    return image;
  }

  public static double[][] getGreen(double[][] img) {
    double image[][] = new double[img.length][img[0].length];
    for (int x = 0; x < img.length; ++x) {
      for (int y = 0; y < img[0].length; ++y) {
        int rgb = (int) img[x][y];
        int g = (rgb >> 8) & 0xFF;
        image[x][y] = g;
      }
    }
    return image;
  }

  public static int getGreenInt(double img) {
    int image = 0;
    int rgb = (int) img;
    int g = (rgb >> 8) & 0xFF;
    image = g;
    return image;
  }

  public static double[][] getBlue(double[][] img) {
    double image[][] = new double[img.length][img[0].length];
    for (int x = 0; x < img.length; ++x) {
      for (int y = 0; y < img[0].length; ++y) {
        int rgb = (int) img[x][y];
        int b = (rgb & 0xFF);
        image[x][y] = b;
      }
    }
    return image;
  }

  public static int getBlueInt(double img) {
    int image = 0;
    int rgb = (int) img;
    int b = (rgb & 0xFF);
    image = b;
    return image;
  }

  public static double[][] makeColor(
    double[][] redCover,
    double[][] greenCover,
    double[][] blueCover
  ) {
    double[][] result = new double[redCover.length][redCover[0].length];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        int r = (int) redCover[i][j];
        int g = (int) greenCover[i][j];
        int b = (int) blueCover[i][j];
        if (r > 255) r = 255;
        if (r < 0) r = 0;
        if (g > 255) g = 255;
        if (g < 0) g = 0;
        if (b > 255) b = 255;
        if (b < 0) b = 0;
        Color newColor = new Color(r, g, b);
        result[i][j] = newColor.getRGB();
      }
    }
    return result;
  }

  public static double[][] makeColorFromRed(double[][] redCover) {
    double[][] result = new double[redCover.length][redCover[0].length];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        int r = (int) redCover[i][j];
        if (r > 255) r = 255;
        if (r < 0) r = 0;
        Color newColor = new Color(r, 0, 0);
        result[i][j] = newColor.getRGB();
      }
    }
    return result;
  }

  public static String getStrPath(String pathStr) {
    Path path = Paths.get(pathStr);
    return path.toString();
  }
  public static void saveImgFile(double[][] cover,String fileName, String extenstion) throws Exception {
    File fout = new File("resultMatrix.txt");
    FileOutputStream fos = new FileOutputStream(fout);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    for(int i = 0; i < cover.length; i++) {
      for(int j = 0; j < cover[i].length; j++) {
        bw.write((int) cover[i][j] + " ");
      }
      bw.newLine();
    }
    bw.close();
    Runtime cmd = Runtime.getRuntime();
    Process process = cmd.exec(String.format("cmd.exe /c " + ".\\saveDWT.py " + fileName + "." + extenstion));
    process.waitFor();
    StringBuilder builder = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String tempLine;
    while((tempLine = reader.readLine()) != null){
      builder.append(tempLine);
    }
    if(!builder.toString().equalsIgnoreCase("Success")) throw new Exception("Failed to save file \n" + builder.toString());
  }
}
