import numpy as np 
import cv2 
import pywt
import random
# # Load an color image in grayscale 
# img = cv2.imread('Lena.png',cv2.IMREAD_COLOR) 
# # extract red channel
# red_channel = img[:,:,2]

# # create empty image with same shape as that of src image
# red_img = np.zeros(img.shape)

# coeffs = pywt.dwt2(red_channel, 'haar')

# #assign the red channel of src to empty image
# red_img[:,:,2] = coeffs

# plt.imshow(image_data)

# #save image
# # cv2.imwrite('cv2-red-channel.png',coeffs)


# arr = np.zeros((8,8))
# count = 1
# for i in range(0,8):
#     for j in range(0,8):
#         arr[i][j] = count
#         count = count + 1
# coeffs = pywt.dwt2(arr, 'haar')
# cA, (cH, cV, cD) = coeffs
# for i in cA:
#     for j in i:
#             print(j, end=" ")
#     print()
# print()
# for i in cH:
#     for j in i:
#             print(j, end=" ")
#     print()
# print()
# for i in cV:
#     for j in i:
#             print(j, end=" ")
#     print()
# print()
# for i in cD:
#     for j in i:
#             print(j, end=" ")
#     print()
# print()
# coeffs = pywt.idwt2(coeffs, 'haar')
# for i in coeffs:
#     for j in i:
#             print(j, end=" ")
#     print()
for i in range(0,4):
    for j in range(0,4):
        print(random.randint(-1, 0), end=" ")
    print()
