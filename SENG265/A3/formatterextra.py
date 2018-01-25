# Jakob Roberts
# SEng 265
# Assignment 3
# formatterextra.py
#==============================================================================
#! /opt/rh/python33/root/usr/bin/python
import re
#==============================================================================
class Formatter:
	"A class for formatting input strings"
#====================================== Class Initialization
	def __init__(self, inputlines):
		
		#delcare inital parameters
		self.fmtopt = {'width':0, 'mrgn':0, 'fmt':False, 'align':0}
		#Align:
		# 0 = Left
		# 1 = Right
		# 2 = Center
		# 3 = Full (Width)
		self.newcheck = {'lineone':False,'needsecond':False}
		
		#temp line used for output building
		self.tmpline = ""

		#the output line for printing
		self.output = []
		
		#the input from file or stdin
		self.input  = inputlines
		
		#start formatting
		self.runfmt()
#====================================== Start Formatting
	def runfmt(self):
		counter = 0
		for line in self.input:
			counter +=1
			chkfmt = re.match(r"\?(width|mrgn|fmt|align) (\+|\-)?(\d+|on|off|left|center|right|full)",line)
			
			if chkfmt:
				check = chkfmt.groups()
				self.runopt(check)
			else:
				self.fmtline(line)
		if counter !=1:
			if self.fmtopt['fmt'] == True and self.newcheck['lineone'] == False:
				self.output.extend([self.tmpline])
			
#====================================== Format Each Line
	def fmtline(self,line):
		
		linesave = line[:]   #make copy to use if fmt is off
		words = line.split() # split line into list of words
		#if formatting off, add basic line to output
		if self.fmtopt['fmt'] == False:
			linesave = linesave.strip('\n')
			self.output.append(linesave)
		#formatting must be on, then use parameters to format
		elif words:
			self.newcheck['lineone'] = False
			self.newcheck['needsecond'] = False
			for word in words:
				#if empty, add margin
				if not self.tmpline:
					self.tmpline += " " * self.fmtopt['mrgn']
				# if it fits, add the next word
				if len(self.tmpline) + len(word) < self.fmtopt['width']:
					if self.fmtopt['mrgn'] != len(self.tmpline):
						self.tmpline += " "
					self.tmpline += word
				#line is full, add to output and make empty
				else:
					#self.output.extend([self.tmpline])
					self.outfmt(self.tmpline,1)
					self.tmpline = ""
					self.tmpline += " " * self.fmtopt['mrgn']
					self.tmpline += word

		else:
			if self.newcheck['needsecond'] == True:
				self.output.append('')
			if self.tmpline:
				#self.output.extend([self.tmpline])
				self.outfmt(self.tmpline,2)
				self.tmpline = ""
				#self.output.extend([self.tmpline])
				self.outfmt(self.tmpline,1)
				self.newcheck['lineone'] = True
				self.newcheck['needsecond'] = True
#====================================== Format Output
	def outfmt(self, tempout, para):
		if   self.fmtopt['align'] == 0 or (para == 2 and self.fmtopt['align'] == 3): # left
			self.output.extend([tempout])
		elif self.fmtopt['align'] == 1: # right
			length = len(tempout)
			lenrem = self.fmtopt['width'] - length
			linecopy = tempout[:]
			linewords = linecopy.split()
			tempout = ""
			tempout += " " * (lenrem + self.fmtopt['mrgn'])
			spacecheck = 0
			for word in linewords:
				if spacecheck !=0:
					tempout += " "
				spacecheck += 1
				tempout += word
			self.output.extend([tempout])
		elif self.fmtopt['align'] == 2: # center
			length = len(tempout)
			lenrem = self.fmtopt['width'] - length
			linecopy = tempout[:]
			linewords = linecopy.split()
			tempout = ""
			tempout += " " * int((lenrem + self.fmtopt['mrgn'])/2)
			spacecheck = 0
			for word in linewords:
				if spacecheck !=0:
					tempout += " "
				spacecheck += 1
				tempout += word
			self.output.extend([tempout])
		elif self.fmtopt['align'] == 3: # full
			length = len(tempout)
			lenrem = self.fmtopt['width'] - length
			linecopy = tempout[:]
			linewords = linecopy.split()
			tempout = " " * (self.fmtopt['mrgn'])
			numwords = len(linewords)
			availspace = numwords - 1
			if lenrem >0:
				fullspace = int(lenrem/availspace) + 1
				tmpspace  = int(lenrem%availspace)
			else:
				fullspace = 1
				tmpspace  = 0
			
			
			spacecheck = 0
			for word in linewords:
				if spacecheck !=0:
					for i in range(0, fullspace):
						tempout += " "
					if tmpspace > 0:
						tempout += " "
						tmpspace -= 1
				spacecheck += 1
				tempout += word
			self.output.extend([tempout])
#====================================== Change Parameters
	def runopt(self, cmdgroup):	
	#= Does WIDTH Option
		if cmdgroup[0] == "width":
			self.fmtopt['width'] = int(cmdgroup[2])
			self.fmtopt['fmt'] = True
	#= Does MRGN Option
		if cmdgroup[0] == "mrgn":
			if cmdgroup[1]   == "+":
				self.fmtopt['mrgn'] += int(cmdgroup[2])
				if self.fmtopt['mrgn'] > (self.fmtopt['width'] -20):
					self.fmtopt['mrgn'] = self.fmtopt['width'] - 20
			elif cmdgroup[1] == "-":
				self.fmtopt['mrgn'] -= int(cmdgroup[2])
				if self.fmtopt['mrgn'] < 0:
					self.fmtopt['mrgn'] = 0
			else:
				self.fmtopt['mrgn']   = int(cmdgroup[2])
	#= Does FMT Option
		if cmdgroup[0]	 == "fmt":
			if cmdgroup[2]   == "on":
				self.fmtopt['fmt'] = True
			elif cmdgroup[2] == "off":
				self.fmtopt['fmt'] = False
	#= Does ALIGN Option
		if cmdgroup[0]   == "align":
			if cmdgroup[2]   == "left":
				self.fmtopt['align'] = 0
			elif cmdgroup[2] == "right":
				self.fmtopt['align'] = 1
			elif cmdgroup[2] == "center":
				self.fmtopt['align'] = 2
			elif cmdgroup[2] == "full":
				self.fmtopt['align'] = 3
#====================================== Returns Formatted Lines
	def get_lines(self):
		return self.output
