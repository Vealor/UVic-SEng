# Jakob Roberts
# SEng 265
# Assignment 2
# sengfmtextra.py
#==========================================================================
import sys
#===Global Values
margin  =  0
width   = -1
fmt     =  0
align   =  1
formats = ['?mrgn','?width','?fmt','?align']
	#Align specs:
		# left   = 1
		# right  = 2
		# center = 3
		# full   = 4
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
				printline(1)
				print("")	
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
			printline(0)
			print("")
			addmargin()
			output += word
#=== Printing
def printline(command):
	global output
		
	if align==1 or (align == 4 and command == 1):
		print(output,end="")
	elif align==2: #right
		print('{0:' '>{w}}'.format(output,w=width),end="")
	elif align==3: #center
		print("",end="")
		output = output.split()
		output = " ".join(output)
		print('{0:' '^{w}}'.format(output,w=width),end="")
	elif align==4: #full
		print("",end="")
		newout = output.split()
		gap = width - len(output)
		spaces = len(newout) -1
		output = ""
		addmargin()
		allspace = int(gap/spaces)
		leftover = int(gap%spaces)
		for word in newout:
			output += word
			if spaces !=0:
				output += " "
				spaces -= 1
			for i in range(0,allspace):
				output += " "
			if leftover != 0:
				leftover -= 1
				output += " "	
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
	global align
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
	if para == 'align':
		if line[1] == 'left':
			align = 1
		if line[1] == 'right':
			align = 2
		if line[1] == 'center':
			align = 3
		if line[1] == 'full':
			align = 4
#==========================================================================
if __name__ == "__main__":
	main()
