# Jakob Roberts
# SEng 265
# Assignment 3
# sengfmt2extra.py
#==============================================================================
#! /opt/rh/python33/root/usr/bin/python
from formatterextra import Formatter
import sys
def main():
	canread = False
	try:
		infile = open(sys.argv[1],'r')
		canread = True
	except IndexError:
		infile = sys.stdin
		canread = True
	except IOError:
		print("File Specified Does Not Exist!!")
	if canread:
		with infile:
			inlines = infile.readlines()

		f = Formatter(inlines)
		lines = f.get_lines()

		for line in lines:
			print(line)

if __name__ == "__main__":
	main()
