@ CSc 230 Example 2 for Lab on Introduction to ARM tools 

@ A simple assembly program that illustrates operations over arrays
@ of memory values. The repetitive actions have been "unrolled" since
@ we have not yet seen how to implement looping in assembly.

@ == Program Code ====================================================
	.text
	.global _start
_start:	nop		@ means "no operation"
				@ Do nothing: converted to "mov r0,r0"
				@ useful for a temporary label
	mov	r2, #23

	ldr	r4, =Num1  @ R4 <- address of "Num1"
	ldr	r5, [r4]   @ ...load the value at that address
	ldr	r6, =Num2  @ R6 <- address of "Num2"
	str	r5, [r6]   @ store: Memory[Num2 = R6] <- R5
@ What are the addressing modes in the 4 instructions above?
@ What was accomplished by the 4 instructions?
@ Is there a faster way to do it?
	ldr	r1, =Array1
	ldr	r5, =Array2
	ldr	r10, =A1
	ldr	r11, =A2
@ Look at the definitions for "Array1" & "A1", etc., below.
@ Compare the values in the corresponding registers.
@ What do you notice? What can you infer about labels?
	mov	r0, #0
	ldr	r2, [r1]
@ What exactly does r2 contain here?
@ Give a description, not just the value in r2.
	add	r0, r0, r2
	str	r2, [r5]	
	add	r1, r1, #4
	add	r5, r5, #4
@ What did these increment accomplish?
@ Why +4?
	ldr	r2, [r1]
	add	r0, r0, r2
	str	r2, [r5]
@ Loop #3:
	add	r1, r1, #4
	add	r5, r5, #4
	ldr	r2, [r1]
	add	r0, r0, r2
	str	r2, [r5]

@ Loop #4:
	add	r1, r1, #4
	add	r5, r5, #4
	ldr	r2, [r1]
	add	r0, r0, r2
	str	r2, [r5]

@ Loop #5:
	add	r1, r1, #4
	add	r5, r5, #4
	ldr	r2, [r1]
	add	r0, r0, r2
	str	r2, [r5]

@ Loop #6:
	add	r1, r1, #4
	add	r5, r5, #4
	ldr	r2, [r1]
	add	r0, r0, r2
	str	r2, [r5]

	ldr	r3, =Sum
	str	r0, [r3]  @ Store sum (R0) in memory

	swi	0x11 @ Exit


@ == Program Data ====================================================

	.data

Num1:	.word	0x5555AAAA @ one word of memory, with an initial value in hex
Num2:	.skip	4          @ 4 **BYTES** (= 1 word) of memory, uninitialized!

A1:
Array1:	.word	1, 2, 3 @ three words of memory
       	.word	4, 5, 6 @ three more words of memory

A2:    	.skip	0           @ 0 bytes, i.e. no memory (...silly)
Array2:	.word	0,0,0,0,0,0 @ 6 words of memory

Sum:	.word	0			@ end sum

	.end

