#checks python output files

import sys
import os

#============
extra = 0 
#============


testextra = "20_extra"
testnum      = ['01','02','03','04','05','06','07','08','09','10']
testnum.extend(['12','13','14','15','16','17','18','19','20'])

filein = ""
fileinextra = "python sengfmtextra.py ./in" + testextra + ".txt > ./myoutput.txt"
fileout = ""
output = ""

if extra == 1:
	os.system(fileinextra)
	print("Extra file made!")

for i in testnum:
	print("----------TESTING " + i + "----------")
	filein = "python sengfmt.py ./outputs/in" + i + ".txt > ./myoutput.txt"
	fileout = "diff ./outputs/out" + i + ".txt ./myoutput.txt"
	print(filein)
	
	os.system(filein)
	os.system(fileout)
