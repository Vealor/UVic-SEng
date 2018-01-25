/* 
 * UVic SENG 265, Summer 2014, A#4
 *
 * Code for driver.c -- Calls the "format_lines()" routine within the
 * formatter.c module.  ("format_lines()" may, of course, be called
 * from within other programs that also link in formatter.o.
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "formatter.h"

int main(int argc, char *argv[]) {
	char **result;
    /* Note: This line is here for testing purposes only.
     * This array of strings will be stored in the "code"
     * section of the binary, and cannot be modified.
     * Put differently, I can only modify a copy of this
     * string, not the string itself.
     */

	char *lines[] = {
		"?width 30",
		"While there    are enough characters   here to",
		"fill",
		"   at least one line, there is",
		"plenty",
		"of",
		"            white space that needs to be",
		"eliminated",
		"from the original",
		"         text file."	
	};
	char **line;


	result = format_lines(lines, 10);

	if (result == NULL) {
		printf("%s: it appears 'format_lines' is not yet complete\n",
			argv[0]);
		exit(1);
	}

	for (line = result; *line != NULL; line++) {
		printf ("%s\n", *line);
	}

	exit(0);
}
