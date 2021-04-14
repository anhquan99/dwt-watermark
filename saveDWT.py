from PIL import Image as im
import numpy as np
import sys
import pathlib
import traceback
import cv2

try:
    # open text file and convert to array then save image file
    text_file = open("resultMatrix.txt", "r")
    lines = text_file.readlines()
    height = len(lines)
    width = len(lines[0].split(" ")) - 1
    # print(str(width) + " " + str(height))
    arr = []
    count = 0
    for i in range(0, height):
        line = lines[i].replace("\n", "")
        lineArr = line.split(" ")
        tempArr = []
        for j in range(0, width):
            rgb = int(lineArr[j])
            # arr.append(rgb)
            red = (rgb >> 16) & 0xFF
            green = (rgb >> 8) & 0xFF
            blue = rgb & 0xFF
            # if count < 10:
            #     print("red: " + str(red))
            #     print("green: " + str(green))
            #     print("blue: " + str(blue))
            #     print("--------------------------------")
            #     count+=1  
            # else: break
            arr.append(red)
            arr.append(green)
            arr.append(blue)
    arr = np.array(arr)
    arr = arr.reshape(height, width, 3)
    cv2.imwrite(sys.argv[1],arr)
    # new_im = im.fromarray((arr).astype(np.uint8)).resize(
    #     (width, height )).convert('RGB')
    # new_im = im.fromarray((arr).astype(np.uint8)).resize(
    #     (width, height )).convert('RGB')
    # new_im.show()
    # image_array = np.array(new_im)
    # print("read again:")
    # for i in range(0,10):
    #     for i in range(0,10):
    #         print(image_array[i][j])
    # new_im.save(sys.argv[1])
    print("Success")
except Exception as e:
    print(e)