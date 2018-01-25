@ CSc 230 Example 1 for Lab on 
@ Introduction to ARM tools 

@ A simple assembly program that illustrates some 
@ of the basic instructions
@ for loading values into registers, as well as some 
@ simple arithmetic instructions.


	.text           @ Begin a "text" section, i.e. instructions
	.global _start  @ Expose the label "_start" outside this program

_start:
	mov	R1, #42			@ R1 <- (value) 42
	mov	R2, #0x2A   	@ R2 <- (value) 42 in Hex
	ldr	R4, =A      @ R4 <- address of memory at "A" label
	ldr	R3, [R4]    @ R3 <- Memory[R4], that is, R3=content of A
	mov	R3, #23     @ R3 <- 23
	add	R0, R1, R3  @ R0 <- R1+R3 (=65)
	sub	R2, R2, R3  @ R2 <- R2-R3 (=19)
	add	R2, R2, #1  @ R2 <- R2+1 (=20)
	mul	R4, R1, R3  @ R4 <- R1*R3 (=966)
	swi	0x11        @ stop execution	

	.data	    @ Begin a "data" section, i.e. memory definitions
	.align	 @ align the following memory allocation on a word boundary
A:	.word	30  @ One "word" (32 bits) of memory, initialized to 30

	.end  	@ End 
	

