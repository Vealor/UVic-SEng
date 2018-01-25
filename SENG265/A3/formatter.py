# Jakob Roberts
# SEng 265
# Assignment 3
# sengfmt2.py
#==============================================================================
#! /opt/rh/python33/root/usr/bin/python
import re
#==============================================================================
class Formatter:
	"A class for formatting input strings"
#====================================== Class Initialization
	def __init__(self, inputlines):
		
		#delcare inital parameters
		self.fmtopt = {'width':0, 'mrgn':0, 'fmt':False}
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
		counter = 0			# counter for checking if last line needs to be printed
		for line in self.input:
			counter +=1
			# check if parameter
			chkfmt = re.match(r"\?(width|mrgn|fmt) (\+|\-)?(\d+|on|off)",line)
			
			if chkfmt:		# go to parameter assignment function
				check = chkfmt.groups()
				self.runopt(check)
			else:			# format the line because not parameter
				self.fmtline(line)
		# print last line if needed
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
			# reset newline parameters
			self.newcheck['lineone'] = False
			self.newcheck['needsecond'] = False
			for word in words:
				#if empty, add margin
				if not self.tmpline:
					self.tmpline += " " * self.fmtopt['mrgn']
				# if it fits, add the next word
				if len(self.tmpline) + len(word) < self.fmtopt['width']:
					# add spaces
					if self.fmtopt['mrgn'] != len(self.tmpline):
						self.tmpline += " "
					self.tmpline += word
				#line is full, add to output and make empty
				else:
					self.output.extend([self.tmpline])
					self.tmpline = ""
					self.tmpline += " " * self.fmtopt['mrgn']
					self.tmpline += word

		else:
			# check for double new line
			if self.newcheck['needsecond'] == True:
				self.output.append('')
			# if newline and stuff still on line, print it then make a new line
			if self.tmpline:
				self.output.extend([self.tmpline])
				self.tmpline = ""
				self.output.extend([self.tmpline])
				self.newcheck['lineone'] = True
				self.newcheck['needsecond'] = True
#====================================== Change Parameters
	def runopt(self, cmdgroup):	
	#= Does WIDTH Option
		# assigns a new width value
		if cmdgroup[0] == "width":
			self.fmtopt['width'] = int(cmdgroup[2])
			# when width is initalized, formatting is turned on
			self.fmtopt['fmt'] = True
	#= Does MRGN Option
		if cmdgroup[0] == "mrgn":
			# adds a value to existing margin
			if cmdgroup[1]   == "+":
				self.fmtopt['mrgn'] += int(cmdgroup[2])
				if self.fmtopt['mrgn'] > (self.fmtopt['width'] -20):
					self.fmtopt['mrgn'] = self.fmtopt['width'] - 20
			# subtracts a value from existing margin
			elif cmdgroup[1] == "-":
				self.fmtopt['mrgn'] -= int(cmdgroup[2])
				if self.fmtopt['mrgn'] < 0:
					self.fmtopt['mrgn'] = 0
			# assigns new value to margin
			else:
				self.fmtopt['mrgn']   = int(cmdgroup[2])
	#= Does FMT Option
		if cmdgroup[0]	 == "fmt":

			# turns FMT on
			if cmdgroup[2]   == "on":
				self.fmtopt['fmt'] = True

			# turns FMT off
			elif cmdgroup[2] == "off":
				self.fmtopt['fmt'] = False
#====================================== Returns Formatted Lines
	def get_lines(self): # returns the output that has been made
		return self.output
