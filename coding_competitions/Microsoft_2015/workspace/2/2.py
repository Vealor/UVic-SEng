import sys

def main():
    canread = False
    try:
        infile = open(sys.argv[1],'r')
        canread = True
    except IOError:
        print("File Specified Does Not Exist!")
    
    if canread:
        with infile: #read lines in from input
            inlines = infile.readlines()
        
        #format lines
        
        #get the output
        
        #print it
        for line in inlines:
            print(line)
          
          
          
          
            
if __name__ == "__main__":
    main()