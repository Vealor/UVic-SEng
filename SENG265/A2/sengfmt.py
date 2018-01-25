# Jakob Roberts
# SEng 265
# Assignment 2
# sengfmt.py
#==========================================================================
import sys
#===Global Values
margin  =  0
width   = -1
fmt     =  0
formats = ['?mrgn','?width','?fmt']
output  = ""
#=== Main
def main():
	global output
	counter = 0
	newlinecounter = 0
	inlines = []
	
	try:
		infile = open(sys.argv[1],'r')
	except IndexError:
		infile = sys.stdin
	with infile:
		inlines = infile.readlines()
	
	for line in inlines:
		counter += 1			# for printing last line
		linesave = line[:]		# for use with FMT = 0
		
		words = line.split()	# split apart input line

		

		if not words:
			if output:
				printline()
				print("")
			
			#if len(output)!=0:
			#	printline()
			#	print("")
			
			#printline()
			#if len(output)!=0:
			if newlinecounter >0 and fmt ==1:
				print("")
			newlinecounter += 1
		if words and newlinecounter >0:
			print("")
			newlinecounter = 0
		if words and words[0] in formats:
			assignpara(words)
			words = words[2:]
		if fmt == 1 and words:
			outputbuild(words)
		elif words:
			print(linesave,end="")

	# print last line
	if counter != 1:
		if fmt==1:
			print(output)
		else:
			print(output,end="")

#=== Build output
def outputbuild(line):
	global output
	for word in line:
		if not output:
			addmargin()
		if len(output) + len(word) < width:
			if margin != len(output):
				output += " "
			output += word
		else:
			printline()
			print("")
			addmargin()
			output += word
#=== Printing
def printline():
	global output
	print(output,end="")
	output = ""
#=== Add Margins
def addmargin():
	global output
	output += " " * margin
#=== Assignment of Parameters
def assignpara(line):
	global margin
	global width
	global fmt
	para = line[0][1:]
	if para == 'width':
		width = int(line[1])
		fmt = 1
	if para == 'mrgn':
		if line[1][0] == '-':
			sub = int(line[1][1:])
			margin -= sub
			if margin < 0:
				margin = 0
		elif line[1][0] == '+':
			add = int(line[1][1:])
			margin += add
			if margin > (width - 20):
				margin = width-20
		else:
			margin = int(line[1])
	if para == 'fmt':
		if line[1] == 'on':
			fmt = 1
		if line[1] == 'off':
			fmt = 0
#==========================================================================
if __name__ == "__main__":
	main()
