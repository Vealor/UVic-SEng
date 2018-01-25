# Jakob Roberts
# SEng 265
# Assignment 3
# sengfmt2.py
#==============================================================================
#! /opt/rh/python33/root/usr/bin/python
from formatter import Formatter
import sys
def main():
	canread = False
	try:				# check if can open file
		infile = open(sys.argv[1],'r')
		canread = True
	except IndexError:	# read from stdin if no file
		infile = sys.stdin
		canread = True
	except IOError:		# file doesn't exist so print error
		print("File Specified Does Not Exist!!")
	if canread:
		with infile:	# read lines in from input
			inlines = infile.readlines()
		# format the lines
		f = Formatter(inlines)
		# get the output from Formatter class
		lines = f.get_lines()
		# print the output from Formatter class
		for line in lines:
			print(line)

if __name__ == "__main__":
	main()
