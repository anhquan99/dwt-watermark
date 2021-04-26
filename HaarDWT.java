public class HaarDWT {

  private static final double w0 = 0.5;
  private static final double w1 = -0.5;
  private static final double s0 = 0.5;
  private static final double s1 = 0.5;

  public static double[][] discompose(double[][] matrix) {
    int width = matrix[0].length;
    int height = matrix.length;
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    double[][] result = new double[height][width];

    // horizontal pass
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < halfWidth; j++) {
        result[i][j] = (matrix[i][j * 2] + matrix[i][(j * 2) + 1]) / 2;
        result[i][j + halfWidth] =
          (matrix[i][j * 2] - matrix[i][(j * 2) + 1]) / 2;
      }
    }
      
    // vertical pass
    double tempArr[][] = copyArr(result);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < halfHeight; j++) {
        result[j][i] = (tempArr[j * 2][i] + tempArr[(j * 2) + 1][i]) / 2;
        result[j + halfHeight][i] =
          (tempArr[j * 2][i] - tempArr[(j * 2) + 1][i]) / 2;
      }
    }

    return result;
  }

  public static double[][] inverse(double[][] matrix) {
    int width = matrix[0].length;
    int height = matrix.length;
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    double[][] result = copyArr(matrix);

    // vertical pass
    double tempArr[][] = copyArr(matrix);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < halfHeight; j++) {
        result[j * 2][i] = tempArr[j][i] + tempArr[j + halfHeight][i];
        result[(j * 2) + 1][i] = tempArr[j][i] - tempArr[j + halfHeight][i];
        // result[j][i] = matrix[j * 2][i] + matrix[(j * 2) + 1][i];
        // result[j+halfHeight][i] = matrix[j * 2][i] - matrix[(j  * 2 ) + 1][i];
      }
    }
    // horizontal pass
    tempArr = copyArr(result);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < halfWidth; j++) {
        result[i][j * 2] = tempArr[i][j] + tempArr[i][j + halfWidth];
        result[i][(j * 2) + 1] = tempArr[i][j] - tempArr[i][j + halfWidth];
        // result[i][j] = ( tempArr[i][j * 2] + tempArr[i][ (j * 2) + 1] );
        // result[i][j+halfWidth] = ( tempArr[i][j * 2] - tempArr[i][(j * 2) +1] );
      }
    }

    return result;
  }

  public static int[][] discompose(int[][] matrix) {
    int width = matrix[0].length;
    int height = matrix.length;
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    int[][] result = new int[height][width];

    // horizontal pass
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < halfWidth; j++) {
        result[i][j] = (matrix[i][j * 2] + matrix[i][(j * 2) + 1]) / 2;
        result[i][j + halfWidth] =
          (matrix[i][j * 2] - matrix[i][(j * 2) + 1]) / 2;
      }
    }

    // vertical pass
    int tempArr[][] = copyArr(result);
    for (int i = 0; i < halfWidth; i++) {
      for (int j = 0; j < halfHeight; j++) {
        result[j][i] = (tempArr[j * 2][i] + tempArr[(j * 2) + 1][i]) / 2;
        result[j + halfHeight][i] =
          (tempArr[j * 2][i] - tempArr[(j * 2) + 1][i]) / 2;
      }
    }

    return result;
  }

  public static int[][] inverse(int[][] matrix) {
    int width = matrix[0].length;
    int height = matrix.length;
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    int[][] result = copyArr(matrix);

    // vertical pass
    int tempArr[][] = copyArr(matrix);
    for (int i = 0; i < halfWidth; i++) {
      for (int j = 0; j < halfHeight; j++) {
        result[j * 2][i] = tempArr[j][i] + tempArr[j + halfHeight][i];
        result[(j * 2) + 1][i] = tempArr[j][i] - tempArr[j + halfHeight][i];
        // result[j][i] = matrix[j * 2][i] + matrix[(j * 2) + 1][i];
        // result[j+halfHeight][i] = matrix[j * 2][i] - matrix[(j  * 2 ) + 1][i];
      }
    }
    // horizontal pass
    tempArr = copyArr(result);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < halfWidth; j++) {
        result[i][j * 2] = tempArr[i][j] + tempArr[i][j + halfWidth];
        result[i][(j * 2) + 1] = tempArr[i][j] - tempArr[i][j + halfWidth];
        // result[i][j] = ( tempArr[i][j * 2] + tempArr[i][ (j * 2) + 1] );
        // result[i][j+halfWidth] = ( tempArr[i][j * 2] - tempArr[i][(j * 2) +1] );
      }
    }

    return result;
  }

  public static void printArr(double[][] arr, String arrName) {
    int width = arr[0].length;
    int height = arr.length;
    System.out.println(arrName);
    System.out.println(width + " " + height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        System.out.format("%30.2f", arr[i][j]);
      }
      System.out.println();
    }
  }

  public static void printArr(double[][] arr) {
    int width = arr[0].length;
    int height = arr.length;
    System.out.println(width + " " + height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        System.out.format("%30.2f", arr[i][j]);
      }
      System.out.println();
    }
  }
  public static void printArrSmall(double[][] arr, String arrName) {
    int width = arr[0].length;
    int height = arr.length;
    System.out.println(arrName);
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        System.out.print(arr[i][j] + "\t \t");
      }
      System.out.println();
    }
  }

  public static double[][] copyArr(double[][] input) {
    double[][] temp = new double[input.length][input[0].length];
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[0].length; j++) {
        temp[i][j] = input[i][j];
      }
    }
    return temp;
  }

  public static int[][] copyArr(int[][] input) {
    int[][] temp = new int[input.length][input[0].length];
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[0].length; j++) {
        temp[i][j] = input[i][j];
      }
    }
    return temp;
  }

  public static double[][] forwardwavlet(double[][] img) {
    //BufferedImage bimg = ImageIO.read(new File("F:\\KUSH\\pics\\dv.jpg"));// reading the image
    int width = img[0].length;
    int height = img.length;

    double img1[][] = new double[height][width]; //holds horizontal pass result
    double img2[][] = new double[height][width]; //holds vertical pass result
    int halfwidth = width / 2;
    int halfheight = height / 2;
    // horizontal pass starts --
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < halfwidth; j++) {
        img1[i][j] = (img[i][2 * j] + img[i][(2 * j) + 1]);
        img1[i][j + halfwidth] = (img[i][2 * j] - img[i][(2 * j) + 1]);
      }
    }
    //--ends here

    //vertical pass starts||
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < halfheight; j++) {
        img2[j][i] = (img1[j * 2][i] + img1[(j * 2) + 1][i]);

        img2[j + halfheight][i] = (img1[j * 2][i] - img1[(j * 2) + 1][i]);
      }
    }
    // ||ends here
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        img2[i][j] = img2[i][j] / 2;
      }
    }
    return img2;
  }
}
