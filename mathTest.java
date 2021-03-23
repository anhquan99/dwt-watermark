import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class mathTest {

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(new BufferedReader(new FileReader("matrix.txt")));
    int rows = 16;
    int columns = 16;
    double[][] myArray = new double[rows][columns];
    while (sc.hasNextLine()) {
      for (int i = 0; i < myArray.length; i++) {
        String[] line = sc.nextLine().trim().split(" ");
        for (int j = 0; j < line.length; j++) {
          myArray[i][j] = Double.parseDouble(line[j]);
        }
      }
    }
    double[][] savedArr = HaarDWT.copyArr(myArray);
    // 1st pass
    savedArr = HaarDWT.discompose(savedArr);
    double[][] savedLL = MatrixControl.getLL(savedArr);
    double[][] savedLH = MatrixControl.getLH(savedArr);
    double[][] savedHL = MatrixControl.getHL(savedArr);
    double[][] savedHH = MatrixControl.getHH(savedArr);
    // 2nd pass
    savedLL = HaarDWT.discompose(savedLL);
    double[][] savedLL1 = MatrixControl.getLL(savedLL);
    double[][] savedLH1 = MatrixControl.getLH(savedLL);
    double[][] savedHL1 = MatrixControl.getHL(savedLL);
    double[][] savedHH1 = MatrixControl.getHH(savedLL);

    System.out.println("Array:");
    HaarDWT.printArr(savedArr);
    // tempArr =  HaarDWT.discompose(tempArr); // 1st pass
    // double[][] tempLL = MatrixControl.getLL(tempArr);
    // tempLL = HaarDWT.discompose(tempLL); // 2nd pass
    // System.out.println("savedLL: ");
    // HaarDWT.printArr(savedLL);

    sc = new Scanner(new BufferedReader(new FileReader("binaryMatrix1.txt")));
    double[][] binaryArr1 = new double[4][4];
    while (sc.hasNextLine()) {
      for (int i = 0; i < binaryArr1.length; i++) {
        String[] line = sc.nextLine().trim().split(" ");
        for (int j = 0; j < binaryArr1.length; j++) {
          binaryArr1[i][j] = Double.parseDouble(line[j]);
        }
      }
    }
    System.out.println("Binary 1:");
    HaarDWT.printArr(binaryArr1);

    sc = new Scanner(new BufferedReader(new FileReader("binaryMatrix2.txt")));
    double[][] binaryArr2 = new double[4][4];
    while (sc.hasNextLine()) {
      for (int i = 0; i < binaryArr2.length; i++) {
        String[] line = sc.nextLine().trim().split(" ");
        for (int j = 0; j < binaryArr2.length; j++) {
          binaryArr2[i][j] = Double.parseDouble(line[j]);
        }
      }
    }
    System.out.println("Binary 2:");
    HaarDWT.printArr(binaryArr2);

    myArray = HaarDWT.discompose(myArray);
    double[][] LL = MatrixControl.getLL(myArray);
    double[][] LH = MatrixControl.getLH(myArray);
    double[][] HL = MatrixControl.getHL(myArray);
    double[][] HH = MatrixControl.getHH(myArray);
    LL = HaarDWT.discompose(LL);
    double[][] LL1 = MatrixControl.getLL(LL);
    double[][] LH1 = MatrixControl.getLH(LL);
    double[][] HL1 = MatrixControl.getHL(LL);
    double[][] HH1 = MatrixControl.getHH(LL);

    LL1 = WaterMark.embedCalculation(LL1, binaryArr1, 1);
    HH1 = WaterMark.embedCalculation(HH1, binaryArr2, 1);

    LL = MatrixControl.getMatrix(LL1, LH1, HL1, HH1);
    System.out.println("LL changed before inverse: ");
    HaarDWT.printArr(myArray);

    LL = HaarDWT.inverse(LL);

    myArray = MatrixControl.getMatrix(LL, LH, HL, HH);
    myArray = HaarDWT.inverse(myArray);
    System.out.println("Array after discompose and change: ");
    HaarDWT.printArr(myArray);

    myArray = HaarDWT.discompose(myArray);
    LL = MatrixControl.getLL(myArray);
    LL = HaarDWT.discompose(LL);
    System.out.println("LL after discompose and change: ");
    HaarDWT.printArr(myArray);
    LL1 = MatrixControl.getLL(LL);
    HH1 = MatrixControl.getHH(LL);

    double[][] resultBinary1 = WaterMark.inverseCalculation(savedLL1, LL1, 1);
    System.out.println("Binary 1: ");
    HaarDWT.printArr(resultBinary1);

    double[][] resultBinary2 = WaterMark.inverseCalculation(savedHH1, HH1, 1);
    System.out.println("Binary 2: ");
    HaarDWT.printArr(resultBinary2);
  }
}
