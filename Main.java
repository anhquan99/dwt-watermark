public class Main {

  public static void main(String[] args) throws Exception {
    // WaterMark.RM_RP(
    //   "laptop.jpg",
    //   "noon.jpg",
    //   "Lena.png",
    //   1
    // );
    // WaterMark.inverse_RM_RP("laptop.jpg", "laptopRM_RP.png", 1);
    // WaterMark.PT_AME("laptop.jpg", "Samurai.jpg", "glass.jpg", "noon.jpg", "Wallpaper-7.jpg", 1,2,3,4);
    // WaterMark.inverse_PT_AME("laptop.jpg", "laptopPT_AME.png", 1,2,3,4);
    // openFile("laptop.jpg");
    int length = args.length;
    switch (length) {
      case 6:
        if (
          args[0].equalsIgnoreCase("-RM_RP") && args[1].equalsIgnoreCase("-E")
        ) {
          double alpha = Double.parseDouble(args[5]);
          WaterMark.RM_RP(args[2], args[3], args[4], alpha);
        } else printError();
        break;
      case 5:
        if (
          args[0].equalsIgnoreCase("-RM_RP") && args[1].equalsIgnoreCase("-D")
        ) {
          double alpha = Double.parseDouble(args[4]);
          WaterMark.inverse_RM_RP(args[2], args[3], alpha);
        } else printError();
        break;
      case 11:
        if (
          args[0].equalsIgnoreCase("-PT_AME") && args[1].equalsIgnoreCase("-E")
        ) {
          double alpha1 = Double.parseDouble(args[7]);
          double alpha2 = Double.parseDouble(args[8]);
          double alpha3 = Double.parseDouble(args[9]);
          double alpha4 = Double.parseDouble(args[10]);
          WaterMark.PT_AME(
            args[2],
            args[3],
            args[4],
            args[5],
            args[6],
            alpha1,
            alpha2,
            alpha3,
            alpha4
          );
        } else printError();
        break;
      case 8:
        if (
          args[0].equalsIgnoreCase("-PT_AME") && args[1].equalsIgnoreCase("-D")
        ) {
          double alpha1 = Double.parseDouble(args[4]);
          double alpha2 = Double.parseDouble(args[5]);
          double alpha3 = Double.parseDouble(args[6]);
          double alpha4 = Double.parseDouble(args[7]);
          WaterMark.inverse_PT_AME(
            args[2],
            args[3],
            alpha1,
            alpha2,
            alpha3,
            alpha4
          );
        } else printError();
        break;
    }
  }

  private static void printError() {
    System.out.println("*** !! Error !! ERROR !! Error !!***");
    System.out.println("*** EXECUTION ==> ");
    System.out.println(
      "*** ENCODING *** ==> \t -TYPE -E inputFile  secretFile alpha"
    );
    System.out.println(
      "*** DECODING *** ==> \t -TYPE -D originalFile  embededFile alpha"
    );
  }
}
