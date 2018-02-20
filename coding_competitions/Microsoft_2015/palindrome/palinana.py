import sys
import re
import string
import math

def main():
    canread = False
    try:
        infile = open(sys.argv[1],'r')
        canread = True
    except IOError:
        print("File Specified Does Not Exist!")
    if canread:
        with infile:
            inline = infile.readlines()
        d = dict.fromkeys(string.ascii_lowercase,0)
        # print(d)
        for word in inline:
            d = dict.fromkeys(string.ascii_lowercase,0)
            word =  word.strip(' \n\r\t')
            # print word
            for letter in word:
                d[letter] += 1
                # print(d[letter])
            numodd = 0
            numzero = 0
            for key in d:
                if d[key] % 2 ==1:
                    numodd +=1
                if d[key] <= 1:
                    numzero +=1
            for key in d:
                if d[key] %2 ==1:
                    if d[key] >2:
                        numzero-=1
                        break

            for i in range(0,numodd):
                for key in d:
                    if d[key] %2==1:
                        d[key]-=1

            if numodd >=1:
                numodd -= 1
            # print("NUMZERO: " + str(26-numzero))

            top = 0
            for key in d:
                if d[key] >1:
                    top += d[key]
            top = top/2
            top = math.factorial(top)

            bottom = 1
            for key in d:
                bottom *= math.factorial(d[key]/2)
            # print("TOP "+ str(top) + "   BOTTOM " + str(bottom))

            print(str(numodd)+ "," + str(top/bottom))
            # print(d)







if __name__ == "__main__":
    main()
