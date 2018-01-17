#
# An example of using a dictionary in python
#
# Python uses seperate chaining hash tables to
# implement dictionaries.
#

d = {}

d["jason"] = 4;
d[9.384] = "bob";

print 'The key 9.384 stores', d[9.384];
print 'The key jason stores', d["jason"];

# Python has execptions
try:
	print d["bob"];
except KeyError:
	print "bob isn't in the dictionary"

