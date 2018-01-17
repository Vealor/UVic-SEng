/* CSc230 -  - Lab #10
 * Example program
 */

#include <stdio.h> /* printf */
extern void Compute(int*);
void Print(int value) {
	printf("%d\n", value);
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
