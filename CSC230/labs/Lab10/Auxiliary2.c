/* File: Auxiliary.c
	containing 2 functions to be called
	by Lab10_Main2.c to show separate compilation */

/* Hmmm... What does this do? */
extern void Print(int);
int Mystery(int n) {
	int v1, v2, v3, k;
	if ( n <= 1 ) return 1;
	v1 = v2 = 1;
	v3 = v1 + v2;
	for ( k = n; k > 2; k-- ) {
		v1 = v2;
		v2 = v3;
		v3 = v1 + v2;
	}
	return v3;
}
/* Visit inputs until -1.
 * Apply Mystery to each input and Print.
 */
void Compute(int* inputs) {
	int input;
	while ( (input = *(inputs++)) != -1 ) {
		Print(Mystery(input));
	}
}
