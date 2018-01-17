import getpass
import psycopg2
import sys

def main():
	output = sys.stdout
	sys.stdout = open('/dev/tty','w')
	if(len(sys.argv) ==2):
		input = sys.argv[1]	
		command = "SELECT * FROM JMR('" + input + "');"
		print "Connecting to:  studentdb.csc.uvic.ca @ DB: imdb"
		print "--------------Please provide input--------------"
		username = raw_input('Username: ')
		pswrd = getpass.getpass('Password: ')	
		try:
			conn = psycopg2.connect(host="studentdb.csc.uvic.ca",dbname="imdb",user=username,password=pswrd)
			cur = conn.cursor('cursor_of_doom')
			cur.execute(command)
			#rows = cur.fetchall()
			sys.stdout = output
			print "<!DOCTYPE html>\n<html>\n<head>\n<style>\ntable, th, td {\n\tborder: 2px solid black;\n\tborder-collapse: collapse;\n}\nth, td {\n\tpadding: 3px;\n}\n</style>\n</head>\n<body>\n\n<table style=\"width:100\"%>\n  <tr>\n   <td><strong>id</strong></td>\n   <td align=\"right\"><strong>year</strong></td>\n   <td align=\"right\"><strong>rank</strong></td>\n   <td align=\"right\"><strong>votes</strong></td>\n  </tr>"
			print "Director:",input,"<br><br>\n"
			count=0
			row = cur.fetchone()
			while row is not None:
				count+=1
				print "<tr>"
				for i in range(0,4):
					if(row[i]==None):
						print " <td>"+""+"</td>"
					elif(i==0):
						print " <td>",row[i],"</td>"
					else:
						print " <td align=\"right\">",row[i],"</td>"	
				print "</tr>"
				row = cur.fetchone()	
			print "</table>\n<br>Total:",count,"movies"
			print "\n</body>\n</html>"
		except:
			print "Database Error!!"
	else:
		print "Correct usage:  python a5python.py <pid>"
	conn.close()
if __name__ == "__main__":
	main()
