import sys
import re

canread = False
try:
    infile = open(sys.argv[1],'r')
    canread = True
except IOError:
    print("File Specified Does Not Exist!")

if canread:
    with infile: #read lines in from input
        inline = infile.readlines()
    
    #format lines
    outputline=""
    outputsave=""
    linesize=0
    savesize=0
    counter = 0   #counter for outputlines
    countertwo = 0
    for line in inline: #get palindrome
        for i in range(0,len(line),1):
            m=i
            for j in range(len(line)-1,-1,-1):
                if line[m].lower() == line[j].lower():  # if the INITIAL letters match
                    p=m                    # now check if everything in between matches!
                    k=j
                    if((j>=0) and (j<len(line)-1) and (re.match("\W", line[j+1])==None)):
                        continue
                    while p<k-1:
                        if line[p].lower() == line[k].lower():
                            p+=1
                            k-=1
                        else:
                            break
                        while ((p<=k-1) and (re.match("\W", line[p])!=None)):
                            p+=1
                        while ((p<=k-1) and (re.match("\W", line[k])!=None)):
                            k-=1
                    if p == k or p+1==k:
                        linesize=0
                        for z in range(m,j+1,1): # get the output
                            if(re.match("\W", line[z])!=None):
                                linesize-=1
                            outputline+= line[z]
                            linesize+=1
                        if linesize >= savesize:    
                            savesize=linesize
                            outputsave = outputline
                        outputline = ""
        print outputsave
        outputsave = ""
        savesize = 0
    print
      