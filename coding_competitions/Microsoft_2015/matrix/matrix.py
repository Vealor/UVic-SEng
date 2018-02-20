import sys
import re
import string
import math
import numpy as np

def main():
    canread = False
    mlist = []
    firstline = True
    try:
        infile = open(sys.argv[1],'r')
        canread = True
    except IOError:
        print("File Specified Does Not Exist!")
    if canread:
        with infile:
            inlines = infile.readlines()
        for line in inlines:
            line = str(line).strip('\n\t\r')
            thing = line.split('|')

            count = 0

            for item in thing:
                item = filter(None, item.split(' '))
                if len(item) > 0:
                    if firstline:
                        tmp = []
                        # if firstline:
                        #     mlist[count] = 1
                        tmp.append(item)
                        # print(tmp)
                        mlist.append(tmp)
                        count+=1
                    else:
                        mlist[count].append(item)

                        count+=1
            firstline = False

        # multiply time
        tmp1 = []
        tmp2 = []
        for data in mlist:
            print len(tmp1)
            print len(tmp2)
            if len(tmp1)==0:
                tmp1 = np.matrix(data)
                print tmp1
                print len(tmp1)
                continue
            elif len(tmp2)==0:
                tmp2 = np.matrix(data)
                tmp1 =  tmp1*tmp2



        # for i in len(m1):

        #     for j in len(m2):


        # print tmp1
        # print tmp2
        # print mlist









if __name__ == "__main__":
    main()
