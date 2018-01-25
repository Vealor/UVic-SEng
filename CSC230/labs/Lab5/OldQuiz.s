@ CSc 230  - Old Quiz example
@ use in Lab for code tracing exercise

@@@ Manually trace the code below and state:
@@@		what does the code do?
@@@		what does memory look like at the end?
@@@		at the end what are the contents of 
@@@			registers R2, R3, R4, R5, R6, R8?
	.equ		CONST, 6
	.equ		ZERO,  0
	
	.global _start
	.text
_start:		
	LDR	R2,=SUM
	LDR	R3,=S2
	MOV	R4,#CONST
	MOV	R8,#CONST
	MOV	R5,#ZERO
	SUB	R6,R5,R4
	SUB	R4,R4,#1
Mainloop:	
	LDRB	R7,[R2,R4]
	STRB	R7,[R3,R6]
	SUB	R6,R5,R4
	SUB	R4,R4,#1	
	SUBS	R8,R8,#1
	BNE	Mainloop
	SWI	0x11
	.data		
	.align	
SUM:	.ascii	"GNIRPS"
W1:		.skip	CONST
S2:		.skip	0
	.end
