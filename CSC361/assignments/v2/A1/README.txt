######################################
#### Simple HTTP Client & Server #####
######################################


######################
~~~~ INSTALLATION ~~~~
run the following in the code folder:

	$ make all

Alternatively,
to create only the client executable:

	$ make client

to create only the server executable:

	$ make server


########################
~~~~ RUNNING CLIENT ~~~~
Be in the directory where the file was made

	$ ./SimpClient <Your_URI>

<Your_URI> is going to be the server you are accessing

examples of <Your_URI>:

	$ ./SimpClient http://uvic.ca/index.html
	$ ./SimpClient http://www.google.com
	$ ./SimpClient http://192.168.1.1:8080


########################
~~~~ RUNNING SERVER ~~~~
Be in the directory where the file was made

	$ ./SimpServer <port_#> <directory>

examples:

	$ ./SimpServer 80 /
	$ ./SimpServer 8080 /var/www

