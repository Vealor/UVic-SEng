@ CSc 230  - Lab Example 2 for IO

@ Illustration of file input using SWI codes.
@@@ OPEN INPUT FILE, READ INTEGER FROM FILE, CLOSE INPUT FILE
@@@ PRINT STRINGS TO THE SCREEN
@@@ PRINT INTEGERS TO THE SCREEN

	.equ	SWI_Open,   0x66	@open a file
	.equ	SWI_Close,	0x68	@close a file
	.equ	FileInputMode,  0
	.equ	SWI_PrChr,	0x00  	@ Write a byte as an ASCII char to Output View
	.equ	SWI_PrStr, 	0x69  	@ Write a null-ending string 
	.equ	SWI_PrInt,	0x6b	@ Write an Integer
	.equ	SWI_RdInt,	0x6c	@ Read an Integer from a file
	.equ	Stdout,		1		@ Set output mode to be Output View
	.equ	SWI_Exit, 	0x11  	@ Stop execution

	.global _start
	.text	
_start:

@ print an initial message to the screen
	mov	R0,#Stdout	@ R0 =mode = Output view
	ldr	R1, =Message1  	@ load address of Message1 label
	swi	SWI_PrStr  	@ display message to Output View
	
@ == Open an input file for reading =========================================
@	if problems, print message to screen and exit
@ Open (SWI 0x66) expects certain values in R0 & R1
	ldr	r0,=InputFileName	@ R0=address of input file name
	mov	r1,#0			@ R1 = mode = input
	swi	SWI_Open		@ open file for input
@ Check value of Carry-Bit (C) in CPSR, 1 => ERROR 	
	bcs	InFileError 		@ Check Carry-Bit (C): if= 1 then ERROR
@ No error => no branch, continue here
@ R0 (contains file handle) should be >= 0 here
@ Save the file handle in memory:
	ldr	r1,=InputFileHandle	@ if OK, load input file handle
	str	r0,[r1]			@ save the file handle	

@ == Read an integer ===================================================
	ldr	r0,=InputFileHandle	@ load input file handle
	ldr	r0,[r0]
	swi	SWI_RdInt		@ read the integer							
	@ the integer is now in register R0
	
@ print the integer to the screen
	mov	r1,r0			@ R1 = integer to print
	mov	R0,#Stdout		@ R0 = mode = Output view
	swi	SWI_PrInt
	
@ print a new line as a string to the screen
	mov	R0,#Stdout	@ R0 =mode = Output view
	ldr	r1, =NL		@ R1 = address of end-of-line string
	swi	SWI_PrStr
	
@ == Read a second integer and print it ================================
	ldr	r0,=InputFileHandle	@ load input file handle
	ldr	r0,[r0]
	swi	SWI_RdInt		@ read the integer							
	@ the integer is now in register R0
	mov	r1,r0			@ r1 has integer to print
	mov	R0,#Stdout		@ R0 =mode = Output view
	swi	SWI_PrInt
		
@ print a new line as a string to the screen
	mov	R0,#Stdout	@ R0 =mode = Output view
	ldr	r1, =NL		@ R1 = address of end of line char
	swi	SWI_PrStr
	
@ == Close a file ===================================================
	ldr	R0, =InputFileHandle  @ get address of file handle
	ldr	R0, [R0]              @ get value at address
	swi	SWI_Close
	
@ print a final message to the screen
	mov	R0,#Stdout	@ R0 =mode = Output view
	ldr	R1, =Message2  	@ load address of Message2 label
	swi	SWI_PrStr  	@ display message to Output View
	
Exit:	
	swi	SWI_Exit  @ stop executing instructions: end of program
	
InFileError:
	mov	R1, R0           
	ldr	R0, =FileOpenInpErrMsg
	swi	SWI_PrStr     	@ pop-up alert
	bal	Exit            @ give up, go to end
		
	.data
	.align @ IMPORTANT: Begin next data at word-aligned address
	@declare all integers and word-aligned data first
InputFileHandle:	.skip	4
InputFileName:  	.asciz	"LabIO2.txt"
FileOpenInpErrMsg: 	.asciz	"Failed to open input file \n"
ColonSpace:     	.asciz	": "
NL:					.asciz	"\n"	@ new line 
Message1: 	.asciz	"Hello World! \n"
Message2: 	.asciz	"Bye World! \n"

	.end