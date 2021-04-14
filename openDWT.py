from PIL import Image as im
import numpy as np
import sys
import traceback
import cv2
try:
    # open image then convert to arr and save to text file
    an_image = cv2.imread(sys.argv[1])

    # width, height = an_image.size


    # input()
    image_array = np.int32(an_image)
    height = image_array.shape[0]
    width = image_array.shape[1]
    temp = []
    count = 0
    fileBlue = open("imgMatrix.txt", "w+")
    for i in range(0, height):
        for j in range(0, width):
            red = image_array[i][j][0]
            green = image_array[i][j][1]
            blue = image_array[i][j][2]
            # if count < 10:
            #     print("red: " + str(red))
            #     print("green: " + str(green))
            #     print("blue: " + str(blue))
            #     print("--------------------------------")
            #     count+=1
            # else: break

            rgb = image_array[i][j][0]
            rgb = (rgb << 8) + image_array[i][j][1]
            rgb = (rgb << 8) + image_array[i][j][2]
            # rgb = 0xFFFF * image_array[i][j][0] + 0xFF * image_array[i][j][1] + image_array[i][j][2]
            temp.append(rgb)
            # if count < 10:
            #     print(rgb)
            #     count+=1  
            fileBlue.write(str(rgb) + " ")          
        fileBlue.write("\n")
    fileBlue.flush()
    fileBlue.close()
    print("Success")
except Exception as e:
    print(e)
