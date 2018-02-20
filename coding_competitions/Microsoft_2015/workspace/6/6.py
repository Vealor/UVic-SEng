import sys
import re

def main():
    canread = False
    try:
        infile = open(sys.argv[1],'r')
        
        # outfile.open("output.txt", 'r')
        canread = True
    except IOError:
        print("File Specified Does Not Exist!")
    
    if canread:
        with infile: #read lines in from input
            inline = infile.readlines()
        
        #format lines
        nums = []
        outfile = open('output.txt','w')
        for line in inline:
            after_Split = re.split('\ |\.', line) #Split line on "." and " "
            # print after_Split
            for x in range(0,4):
                check = True
                checkInvalid = False
                if len(after_Split) > 12:
                    # print('Invalid', outfile)
                    outfile.write('InValid\n')
                    check = False
                    checkInvalid = True
                    break
                try:
                    nums = [int(i) for i in after_Split]
                except:
                    outfile.write('InValid\n')
                    check = False
                    checkInvalid = True
                    break
                # print nums[x+8]
                if nums[x+8] > 255 or nums[x+8] < 0:
                    outfile.write('InValid\n')
                    check = False
                    checkInvalid = True
                    break
                elif nums[x+8] < nums[x] or nums[x+8] > nums[x+4]:
                    if not checkInvalid:
                        outfile.write('OutRange\n')
                        check = False
                    else:
                        break
            if (check): 
                outfile.write('InRange\n')
          
          
          
            
if __name__ == "__main__":
    main()