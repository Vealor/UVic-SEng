/* CSc230 -  - Lab #10
 * Example program
 */

#include <stdio.h> /* printf */

void Print(long long unsigned int value) {
	printf("%d\n", value);
}

/* Hmmm... What does this do? */
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

#define INPUTS 10

int main() {

	/* Prepare some inputs: */
	int i, inputs[INPUTS+1];
	for ( i = 0; i < INPUTS; i++ ) {
		inputs[i] = i;
	}

	/* Mark end-of-input with -1 */
	inputs[INPUTS] = -1;

	/* Compute with these inputs. */
	Compute(inputs);

	return 0;
}
