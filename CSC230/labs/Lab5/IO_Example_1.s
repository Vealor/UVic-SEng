@ CSc 230  - IO Example 1

@ Use .equ directives to define numeric constants
@ Use the ARMSim SWI codes to display messages
	.equ	N, 65  @ N = 65
	    	@ In other words, replace "N" with "65" in all code that follows
	@ We use hex constants for the SWI codes:
	.equ	SWI_Exit,   0x11	@terminate program
	.equ	SWI_PrStr,  0x69	@print a string
	.equ	SWI_PrInt,	0x6b	@print integer 
	.equ	SWI_PrChr,	0x00  	@print a character
	.equ	Stdout,		1		@output mode for Stdout
	
	.global _start
	.text
@ == print opening message ===========
_start:					
	mov	R0,#Stdout		@ output mode is Stdout
	ldr	R1,=HelloMsg  	@ load address of Message label
	swi	SWI_PrStr  		@ display message 
@ == print the integer 23 ===========	
	mov	R0,#Stdout		@ output mode is Stdout
	mov	R1,#23  		@ integer to be printed
	swi	SWI_PrInt  		@ print integer 
@ == print the character A ===========	
	mov	R0, #'A  		@ R02 <- ASCII "A" = 65 		
	swi	SWI_PrChr  		@ Stdout: display ASCII "A"
@ == print final message ===========	
	mov	R0,#Stdout		@ output mode is Stdout
	ldr	R1,=ByeMsg  	@ load address of Message label	
	swi	SWI_PrStr  		@ display message 
	swi	SWI_Exit  		@ stop executing 	
	.data
HelloMsg: 	.asciz	"Hello World!\n"
MoreJunk:	.ascii	"blah"
ByeMsg: 	.asciz	"Good Bye!"
@ Inspect memory. Notice the difference between .ascii and .asciz
@ => .asciz adds a 0 byte (null-termination), .ascii does not!
	.end