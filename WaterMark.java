import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;

public class WaterMark {

public static void RM_RP(
    String cover,
    String embedImage1,
    String embedImage2,
    double alpha
  )
    throws Exception {
    double[][] binaryEmbedImage1 = ImageControl.makeBinary(embedImage1);
    double[][] binaryEmbedImage2 = ImageControl.makeBinary(embedImage2);
    double[][] coverMatrix = ImageControl.getMatrixText(cover);

    // get rgb
    double[][] resizeCover = MatrixControl.resizeToSquare(coverMatrix);

    // resize embed image
    double[][] squareImage1 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage1,
      resizeCover.length
    );
    double[][] squareImage2 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage2,
      resizeCover.length
    );
    double[][] resizeImage1 = MatrixControl.resizeToFitCover(
      squareImage1,
      resizeCover
    );
    double[][] resizeImage2 = MatrixControl.resizeToFitCover(
      squareImage2,
      resizeCover
    );
    resizeCover =
      RM_RPCalculation(resizeCover, resizeImage1, resizeImage2, alpha);
    String coverFilename = cover.split("\\.")[0];
    // String extenstion = cover.split("\\.")[1];
    String extenstion = "png";

    ImageControl.saveImgFile(resizeCover, coverFilename + "RM_RP", extenstion);
  }
  public static void RM_RPColor(
    String cover,
    String embedImage1,
    String embedImage2,
    double alpha
  )
    throws Exception {
    double[][] binaryEmbedImage1 = ImageControl.makeBinary(embedImage1);
    double[][] binaryEmbedImage2 = ImageControl.makeBinary(embedImage2);
    double[][] coverMatrix = ImageControl.getMatrixRaw(cover);

    // get rgb
    double[][] resizeCover = MatrixControl.resizeToSquare(coverMatrix);
    double[][] resizeRed = ImageControl.getRed(resizeCover);
    double[][] resizeGreen = ImageControl.getGreen(resizeCover);
    double[][] resizeBlue = ImageControl.getBlue(resizeCover);

    // resize embed image
    double[][] squareImage1 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage1,
      resizeCover.length
    );
    double[][] squareImage2 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage2,
      resizeCover.length
    );
    double[][] resizeImage1 = MatrixControl.resizeToFitCover(
      squareImage1,
      resizeCover
    );
    double[][] resizeImage2 = MatrixControl.resizeToFitCover(
      squareImage2,
      resizeCover
    );
    resizeRed = RM_RPCalculation(resizeRed, resizeImage1, resizeImage2, alpha);

    String coverFilename = cover.split("\\.")[0];
    Test.saveImgFileColor(resizeRed, resizeGreen, resizeBlue, coverFilename + "RM_RP");
  }

  public static double[][] RM_RPCalculation(
    double[][] coverColor,
    double[][] embedImg1,
    double[][] embedImg2,
    double alpha
  )
    throws Exception {
    // original

    // // 1st pass
    double[][] coverWavelet = HaarDWT.discompose(coverColor);
    double[][] LL = MatrixControl.getLL(coverWavelet);
    double[][] LH = MatrixControl.getLH(coverWavelet);
    double[][] HL = MatrixControl.getHL(coverWavelet);
    double[][] HH = MatrixControl.getHH(coverWavelet);

    // 2nd pass
    LL = HaarDWT.discompose(LL);
    double[][] LL1 = MatrixControl.getLL(LL);
    double[][] LH1 = MatrixControl.getLH(LL);
    double[][] HL1 = MatrixControl.getHL(LL);
    double[][] HH1 = MatrixControl.getHH(LL);

    // LL1
    LL1 = WaterMark.embedCalculation(LL1, embedImg1, alpha);

    // HH1
    HH1 = WaterMark.embedCalculation(HH1, embedImg2, alpha);

    LL = MatrixControl.getMatrix(LL1, LH1, HL1, HH1);

    LL = HaarDWT.inverse(LL);

    coverWavelet = MatrixControl.getMatrix(LL, LH, HL, HH);
    coverWavelet = HaarDWT.inverse(coverWavelet);

    for (int i = 0; i < coverWavelet.length; i++) {
      for (int j = 0; j < coverWavelet[i].length; j++) {
        if (coverWavelet[i][j] < 0) {
          coverWavelet[i][j] = Math.abs(coverWavelet[i][j]) * alpha;
        }
      }
    }
    return coverWavelet;
  }

  public static void inverse_RM_RP(
    String original,
    String embeded,
    double alpha
  )
    throws Exception {
    // resize original matrix
    double[][] originalMatrix = ImageControl.getMatrixText(original);
    double[][] originalResizedMatrix = MatrixControl.resizeToSquare(
      originalMatrix
    );
    double[][] embededMatrix = ImageControl.getMatrixText(embeded);

    // 1st pass
    // original
    originalResizedMatrix = HaarDWT.discompose(originalResizedMatrix);
    double[][] originalLL = MatrixControl.getLL(originalResizedMatrix);
    // embeded
    embededMatrix = HaarDWT.discompose(embededMatrix);
    double[][] embededLL = MatrixControl.getLL(embededMatrix);

    // 2nd pass
    // original LL
    originalLL = HaarDWT.discompose(originalLL);
    double[][] originalLL1 = MatrixControl.getLL(originalLL);
    double[][] originalHH1 = MatrixControl.getHH(originalLL);

    // embeded LL
    embededLL = HaarDWT.discompose(embededLL);
    double[][] embededLL1 = MatrixControl.getLL(embededLL);
    double[][] embededHH1 = MatrixControl.getHH(embededLL);

    // inverse calculation
    String orginalFilename = original.split("\\.")[0];
    // String extenstion = original.split("\\.")[1];

    String extenstion = "png";

    double[][] embededImg1 = WaterMark.inverseCalculation(
      originalLL1,
      embededLL1,
      alpha
    );

    ImageControl.saveImgFile(
      embededImg1,
      orginalFilename + "RM_RP_ExtractedLL",
      extenstion
    );

    double[][] embededImg2 = WaterMark.inverseCalculation(
      originalHH1,
      embededHH1,
      alpha
    );

    ImageControl.saveImgFile(
      embededImg2,
      orginalFilename + "RM_RP_ExtractedHH",
      extenstion
    );
  }

  public static void inverse_RM_RPColor(
    String original,
    String embeded,
    double alpha
  )
    throws Exception {
    // resize original matrix
    double[][] originalMatrix = ImageControl.getMatrixRaw(original);
    double[][] originalResizedMatrix = MatrixControl.resizeToSquare(
      originalMatrix
    );
    double[][] originRed = ImageControl.getRed(originalResizedMatrix);

    double[][] embededMatrix = ImageControl.getMatrixRaw(embeded);
    double[][] embededRed = ImageControl.getRed(embededMatrix);
    // 1st pass
    // original
    originRed = HaarDWT.discompose(originRed);
    double[][] originalLL = MatrixControl.getLL(originRed);
    // // embeded
    embededRed = HaarDWT.discompose(embededRed);
    double[][] embededLL = MatrixControl.getLL(embededRed);

    // 2nd pass
    // original LL
    originalLL = HaarDWT.discompose(originalLL);
    double[][] originalLL1 = MatrixControl.getLL(originalLL);
    double[][] originalHH1 = MatrixControl.getHH(originalLL);

    System.out.println("original LL1 inverse:");
    for (int i = 0; i < 10; i++) {
      System.out.print(originalLL1[0][i] + " ");
    }
    System.out.println();
    System.out.println("original HH1 inverse:");
    for (int i = 0; i < 10; i++) {
      System.out.print(originalHH1[0][i] + " ");
    }
    System.out.println();

    // embeded LL
    embededLL = HaarDWT.discompose(embededLL);
    double[][] embededLL1 = MatrixControl.getLL(embededLL);
    double[][] embededHH1 = MatrixControl.getHH(embededLL);

    System.out.println("Embedded LL1 inverse:");
    for (int i = 0; i < 10; i++) {
      System.out.print(embededLL1[0][i] + " ");
    }
    System.out.println();
    System.out.println("Embedded HH1 inverse:");
    for (int i = 0; i < 10; i++) {
      System.out.print(embededHH1[0][i] + " ");
    }
    System.out.println();

    // inverse calculation
    String originalFilename = original.split("\\.")[0];

    double[][] embededImg1 = inverseCalculation(originalLL1, embededLL1, alpha);

    System.out.println("Result Img LL:");
    for (int i = 0; i < 10; i++) {
      System.out.print(embededImg1[0][i] + " ");
    }
    System.out.println();
    BufferedImage bufferedOut = new BufferedImage(
      embededImg1.length,
      embededImg1[0].length,
      BufferedImage.TYPE_INT_RGB
    );
    for (int i = 0; i < embededImg1.length; i++) {
      for (int j = 0; j < embededImg1[0].length; ++j) {
        bufferedOut.setRGB(i, j, (int) embededImg1[i][j]);
      }
    }
    File fileOut = new File(originalFilename + "ExtractedLL" + ".jpg");
    System.out.println(fileOut.getAbsolutePath());
    ImageIO.write(bufferedOut, "jpg", fileOut);
    
    double[][] embededImg2 = inverseCalculation(originalHH1, embededHH1, alpha);

    System.out.println("Result Img HH:");
    for (int i = 0; i < 10; i++) {
      System.out.print(embededImg2[0][i] + " ");
    }
    System.out.println();
    for (int i = 0; i < embededImg2.length; i++) {
      for (int j = 0; j < embededImg2[0].length; ++j) {
        bufferedOut.setRGB(i, j, (int) embededImg2[i][j]);
      }
    }
    fileOut = new File(originalFilename + "ExtractedHH" + ".jpg");
    System.out.println(fileOut.getAbsolutePath());
    ImageIO.write(bufferedOut, "jpg", fileOut);
  }

  public static void PT_AME(
    String cover,
    String embedImage1,
    String embedImage2,
    String embedImage3,
    String embedImage4,
    double alpha
  )
    throws Exception {
    double[][] binaryEmbedImage1 = ImageControl.makeBinary(embedImage1);
    double[][] binaryEmbedImage2 = ImageControl.makeBinary(embedImage2);
    double[][] binaryEmbedImage3 = ImageControl.makeBinary(embedImage3);
    double[][] binaryEmbedImage4 = ImageControl.makeBinary(embedImage4);
    double[][] coverMatrix = ImageControl.getMatrix(cover);

    double[][] resizeCover = MatrixControl.resizeToSquare(coverMatrix);

    double[][] squareImage1 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage1,
      resizeCover.length
    );
    double[][] squareImage2 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage2,
      resizeCover.length
    );
    double[][] squareImage3 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage3,
      resizeCover.length
    );
    double[][] squareImage4 = MatrixControl.resizeEmbedToSquare(
      binaryEmbedImage4,
      resizeCover.length
    );

    double[][] resizeImage1 = MatrixControl.resizeToFitCover(
      squareImage1,
      resizeCover
    );
    double[][] resizeImage2 = MatrixControl.resizeToFitCover(
      squareImage2,
      resizeCover
    );
    double[][] resizeImage3 = MatrixControl.resizeToFitCover(
      squareImage3,
      resizeCover
    );
    double[][] resizeImage4 = MatrixControl.resizeToFitCover(
      squareImage4,
      resizeCover
    );

    resizeCover = HaarDWT.discompose(resizeCover);
    double[][] LL = MatrixControl.getLL(resizeCover);
    double[][] LH = MatrixControl.getLH(resizeCover);
    double[][] HL = MatrixControl.getHL(resizeCover);
    double[][] HH = MatrixControl.getHH(resizeCover);

    HaarDWT.printArrSmall(LL, "LL");
    HaarDWT.printArrSmall(LH, "LH");
    HaarDWT.printArrSmall(LH, "LH");
    HaarDWT.printArrSmall(HH, "HH");

    LL = embedCalculation(LL, resizeImage1, alpha);
    LH = embedCalculation(LH, resizeImage2, alpha);
    HL = embedCalculation(HL, resizeImage3, alpha);
    HH = embedCalculation(HH, resizeImage4, alpha);

    HaarDWT.printArrSmall(LL, "LL embeded");
    HaarDWT.printArrSmall(LH, "LH embeded");
    HaarDWT.printArrSmall(LH, "LH embeded");
    HaarDWT.printArrSmall(HH, "HH embeded");

    resizeCover = MatrixControl.getMatrix(LL, LH, HL, HH);
    resizeCover = HaarDWT.inverse(resizeCover);

    // save file
    String orginalFilename = cover.split("\\.")[0];
    // String extenstion = original.split("\\.")[1];

    String extenstion = "png";

    ImageControl.saveImgFile(resizeCover, orginalFilename + "PT_AME", extenstion);
  }

  public static void inverse_PT_AME(String cover, String embeded, double alpha)
    throws Exception {
    double[][] coverMatrix = ImageControl.getMatrix(cover);
    double[][] embededMatrix = ImageControl.getMatrix(embeded);

    double[][] resizeCover = MatrixControl.resizeToSquare(coverMatrix);

    resizeCover = HaarDWT.discompose(resizeCover);
    double[][] coverLL = MatrixControl.getLL(resizeCover);
    double[][] coverLH = MatrixControl.getLH(resizeCover);
    double[][] coverHL = MatrixControl.getHL(resizeCover);
    double[][] coverHH = MatrixControl.getHH(resizeCover);

    HaarDWT.printArrSmall(coverLL, "coverLL");
    HaarDWT.printArrSmall(coverLH, "coverLH");
    HaarDWT.printArrSmall(coverLH, "coverLH");
    HaarDWT.printArrSmall(coverHH, "coverHH");

    embededMatrix = HaarDWT.discompose(embededMatrix);
    double[][] embededLL = MatrixControl.getLL(embededMatrix);
    double[][] embededLH = MatrixControl.getLH(embededMatrix);
    double[][] embededHL = MatrixControl.getHL(embededMatrix);
    double[][] embededHH = MatrixControl.getHH(embededMatrix);

    HaarDWT.printArrSmall(embededLL, "embededLL");
    HaarDWT.printArrSmall(embededLH, "embededLH");
    HaarDWT.printArrSmall(embededLH, "embededLH");
    HaarDWT.printArrSmall(embededHH, "embededHH");

    double[][] embedImg1 = inverseCalculation(coverLL, embededLL, alpha);
    double[][] embedImg2 = inverseCalculation(coverLH, embededLH, alpha);
    double[][] embedImg3 = inverseCalculation(coverHL, embededHL, alpha);
    double[][] embedImg4 = inverseCalculation(coverHH, embededHH, alpha);

    HaarDWT.printArrSmall(embedImg1, "embedImg1");
    HaarDWT.printArrSmall(embedImg2, "embedImg2");
    HaarDWT.printArrSmall(embedImg3, "embedImg3");
    HaarDWT.printArrSmall(embedImg4, "embedImg4");

    String coverFilename = cover.split("\\.")[0];
    String extenstion = "png";

    ImageControl.saveImgFile(embedImg1, coverFilename + "EtractedPY_AME_LL", extenstion);
    ImageControl.saveImgFile(embedImg2, coverFilename + "EtractedPY_AME_LH", extenstion);
    ImageControl.saveImgFile(embedImg3, coverFilename + "EtractedPY_AME_HL", extenstion);
    ImageControl.saveImgFile(embedImg4, coverFilename + "EtractedPY_AME_HH", extenstion);
  }

  public static double[][] embedCalculation(
    double[][] matrix,
    double[][] embedMatrix,
    double alpha
  ) {
    double[][] result = new double[matrix.length][matrix.length];
    for (int i = 0; i < embedMatrix.length; i++) {
      for (int j = 0; j < embedMatrix.length; j++) {
        result[i][j] = (matrix[i][j] + (alpha * embedMatrix[i][j]));
      }
    }
    return result;
  }

  public static double[][] inverseCalculation(
    double[][] original,
    double[][] embeded,
    double alpha
  ) {
    double[][] result = new double[original.length][original.length];
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original.length; j++) {
        result[i][j] = ((embeded[i][j] - original[i][j]) / alpha);
        if(result[i][j] > 0) result[i][j] = 16777215;;
      }
    }
    return result;
  }
}
