import sys
import re

def main():
    canread = False
    try:
        infile = open(sys.argv[1],'r')
        canread = True
    except IOError:
        print("File Specified Does Not Exist!")
    
    a=0
    b=0
    c=""
    isfirst = True
    issecond = False
    theline=""
    find=""
    
    
    if canread:
        with infile: #read lines in from input
            inlines = infile.readlines()
        thelines=""
        for line in inlines:
            thelines +=" "
            thelines += line
        thelines.strip('\n')
        mylines = thelines.split()
        
        myword=mylines[0]
        counter = 0
        theword=""
        for i in range(0,len(myword)):
            theword+=myword[i]
        for word in mylines:
            if not issecond:
                issecond=True
                continue
            for i in range (0,len(myword)):
                if ((len(word)>=len(myword)) and (theword[i] == word[i].lower())):
                    counter+=1
            if counter == len(myword):
                #print theword
                #print word
                b+=1
                if isfirst:
                    isfirst = False
                    c=word
                if word.lower() == theword:
                    a+=1
            counter = 0
    print("%d;%d;%s" % (a,b,c))
          
            
if __name__ == "__main__":
    main()