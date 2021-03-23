import java.awt.Color;
import java.lang.Math;

public class MatrixControl {

  public static double[][] getLL(double[][] matrix) {
    double[][] result = new double[matrix.length / 2][matrix[0].length / 2];
    for (int i = 0; i < matrix.length / 2; i++) {
      for (int j = 0; j < matrix[0].length / 2; j++) {
        result[i][j] = matrix[i][j];
      }
    }
    return result;
  }

  public static double[][] getLH(double[][] matrix) {
    double[][] result = new double[matrix.length / 2][matrix[0].length / 2];
    // for(int i=0; i<matrix.length / 2; i++){
    //     for(int j=0, k = matrix.length/2; j< (matrix[0].length / 2) ; j++, k++){
    //         result[i][j] = matrix[i][k];
    //     }
    // }
    for (int i = 0; i < matrix.length / 2; i++) {
      for (int j = 0; j < matrix[0].length / 2; j++) {
        result[i][j] = matrix[i][j + (matrix[0].length / 2)];
      }
    }
    return result;
  }

  public static double[][] getHL(double[][] matrix) {
    double[][] result = new double[matrix.length / 2][matrix[0].length / 2];
    for (int i = 0, k = matrix.length / 2; i < matrix.length / 2; i++, k++) {
      for (int j = 0; j < matrix[0].length / 2; j++) {
        result[i][j] = matrix[k][j];
      }
    }
    return result;
  }

  public static double[][] getHH(double[][] matrix) {
    double[][] result = new double[matrix.length / 2][matrix[0].length / 2];
    for (int i = 0; i < matrix.length / 2; i++) {
      for (int j = 0; j < matrix[0].length / 2; j++) {
        result[i][j] =
          matrix[i + matrix.length / 2][j + (matrix[0].length / 2)];
      }
    }
    return result;
  }

  public static double[][] getMatrix(
    double[][] LL,
    double[][] LH,
    double[][] HL,
    double[][] HH
  ) {
    double[][] result = new double[LL.length * 2][LL[0].length * 2];
    for (int i = 0; i < result.length / 2; i++) {
      for (int j = 0; j < result[0].length / 2; j++) {
        result[i][j] = LL[i][j];
        result[i][(result.length / 2) + j] = LH[i][j];
        result[(result.length / 2) + i][j] = HL[i][j];
        result[(result.length / 2) + i][(result.length / 2) + j] = HH[i][j];
      }
    }
    return result;
  }

  public static double[][] resizeToSquare(double[][] matrix) {
    double[][] result;
    // width < height
    if (matrix.length < matrix[0].length) {
      int size = getPowOf2(matrix.length);
      result = new double[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    }
    // width > height
    else if (matrix.length > matrix[0].length) {
      int size = getPowOf2(matrix[0].length);
      result = new double[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    }
    // width = height
    else {
      int size = getPowOf2(matrix.length);
      result = new double[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    }
    return result;
  }

  public static double[][] resizeEmbedToSquare(double[][] matrix, int max) {
    double[][] result;

    // width < height
    if (matrix[0].length < matrix.length ) {
      int size = 0;
      if (matrix[0].length > max) {
        size = max;
      } else {
        size = matrix[0].length;
      }
      result = new double[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    }
    // width > height
    else if (matrix[0].length > matrix.length) {
      int size = 0;
      if (matrix.length > max) {
        size = max;
      } else {
        size = matrix.length;
      }
      result = new double[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    }
    // width = height
    else {
      int size = 0;
      if (matrix.length > max) {
        size = max;
      } else {
        size = matrix.length;
      }
      result = new double[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    }
    return result;
  }

  public static int getPowOf2(int max) {
    int temp = 2;
    int result = 0;
    while (temp <= max) {
      result = temp;
      temp *= 2;
    }
    return result;
  }

  public static double[][] resizeToFitCover(
    double[][] matrix,
    double[][] cover
  ) {
    double result[][];
    if (matrix.length > (cover.length / 4)) {
      result = new double[cover.length / 4][cover[0].length / 4];
      for (int i = 0; i < result.length; i++) {
        for (int j = 0; j < result[0].length; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    } else if (matrix.length < (cover.length / 4)) {
      result = new double[cover.length / 4][cover[0].length / 4];
      for (int i = 0; i < result.length; i++) {
        for (int j = 0; j < result.length; j++) {
          if (i >= matrix.length || j >= matrix[0].length) {
            result[i][j] = -1;
          } else {
            result[i][j] = matrix[i][j];
          }
        }
      }
    } else {
      result = HaarDWT.copyArr(matrix);
    }
    return result;
  }

  public static double[][] resizeToFitCoverPT_AME(
    double[][] matrix,
    double[][] cover
  ) {
    double result[][];
    if (matrix.length > (cover.length / 2)) {
      result = new double[cover.length / 2][cover[0].length / 2];
      for (int i = 0; i < result.length; i++) {
        for (int j = 0; j < result[0].length; j++) {
          result[i][j] = matrix[i][j];
        }
      }
    } else if (matrix.length < (cover.length / 2)) {
      result = new double[cover.length / 2][cover[0].length / 2];
      for (int i = 0; i < result.length; i++) {
        for (int j = 0; j < result.length; j++) {
          if (i >= matrix.length || j >= matrix[0].length) {
            result[i][j] = -1;
          } else {
            result[i][j] = matrix[i][j];
          }
        }
      }
    } else {
      result = HaarDWT.copyArr(matrix);
    }
    return result;
  }
}
