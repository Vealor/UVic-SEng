@ CSc 230  - Sum the even and odd elements of an integer array
@ Quiz #3 -TEMPLATE ONLY

@ === NAME: JAMES BOND 007

@ PSEUDO-CODE:
@ Print initial message ==> TO BE MODIFIED BY STUDENT
@ open input file and print message
@ 	If problem, message and go to InFileError
@ MainLoop: 
@ 	read integer n for size
@		if eof, exit MainLoop, go to program closure
@ 	read n integers and place them into ArrayIn 
@	print ArrayIn
@		TASK 1: NEW CODE TO SUM EVEN AND ODD ELEMENTS
@		TASK 2: NEW CODE TO STORE ODD INTEGERS IN ARRAY ODD
@				AND STORE THE EVEN INTEGERS IN
@				ARRAY EVEN
@	print SE and SO (code given)
@	print EVEN and ODD arrays (code given)
@	go to MainLoop
@ Closure: final message and close input file


	.equ	SWI_Exit,   0x11	@terminate program
	.equ	SWI_Open,   0x66	@open a file
	.equ	SWI_Close,	0x68	@close a file
	.equ	SWI_PrStr,  0x69	@print a string
	.equ	SWI_PrInt,	0x6b	@print integer to file
	.equ	SWI_RdInt,	0x6c	@read an integer from file
	.equ	Stdout,		1		@output mode for Stdout
	.equ	FileInputMode,  0
	.equ	FileOutputMode, 1
	.equ	MAXSIZE,10		@maximum size for array

	.global _start
	.text	
_start:
@ == Initial messages and identification =====================
	mov	r0,#Stdout		@print to stdout
	ldr	r1,=IDmessage		@ CUSTOMIZE THE CONTENT
	swi	SWI_PrStr
	mov	r0,#Stdout
	ldr	r1,=Welcome1
	swi	SWI_PrStr	
@ == Open a file for reading =================================
	ldr	R0, =InFileName
	mov	R1, #0
	swi	SWI_Open
	bcs	InFileError 		@ If Carry-Bit =1 => ERROR 
	ldr	R1, =InFileHandle  	@ else get address to store handle
	str	R0, [R1]            	@ save the file handle
	mov	r0,#Stdout		@ print open file message
	ldr	r1,=fileopenmsg
	swi	SWI_PrStr

@ == MainLoop for I/O: 
@		Read the size of an array 
@		If not eof then read its elements into ArrayIn
@		Do computation on array elements
@		Repeat reading a size and a list of array elements
@			until end of file
MainLoop:
	@ == Read size of array ===========
	ldr	R0, =InFileHandle  	@ get address of file handle
	ldr	R0, [R0]           	@ get value at address	
	swi 	SWI_RdInt		@ read size into R0	
	bcs	EndOfFile		@ if c bit is set, EOF reached
	
	mov	R9, r0			@ R9 = size of array
	mov	r0,#Stdout		@ print message 
	ldr	r1,=arsizemsg
	swi	SWI_PrStr
	mov	r0,#Stdout		@ print array size
	mov 	r1,r9
	swi 	SWI_PrInt 		@ r1=SIZE; r0=stdout
	mov	r0,#Stdout			
	ldr	r1,=NL			@ print new line
	swi	SWI_PrStr
	ldr	r1,=Size		@store size for later
	str	r9,[r1]
@ == Read the array elements and store in ArrayIn ========
	mov	r4,r9			@ r4 is loop counter for array size
	ldr	r8,=ArrayIn		@ r8 = &ArrayIn
Rdloop:
	ldr	R0, =InFileHandle  	@ get address of file handle	  	
	ldr	R0, [R0]           	@ get file handle	
	swi SWI_RdInt			@ read integer into R0	
	str	r0,[r8],#4		@ store element in array and increase pointer
	subs	r4,r4,#1		@ decrease loop counter
	bne	Rdloop			@ keep reading until all elements in
@ == Print ArrayIn  ========	
	mov	r0,#Stdout		@ print message
	ldr	r1,=elementsmsg	
	swi	SWI_PrStr
	ldr	r0,=ArrayIn		@ r0 = & ArrayIn
	mov	r1,r9			@ r1 is array size
	BL	PrArray			@ void PrArray(R0:& array;R1:size)
	
@ == Sum the odd elements and the even elements of the array 
@	and store the results in SO and SE 
@ Store the even elements in array EVEN and the odd elements
@	in array ODD

@@@@ THIS IS WHERE NEW CODE MUST BE PLACED
@@@@@@@@@@@@@  HERE IS THE NEW CODE @@@@@@@@@@@@@@@@@
@ STEP 1: Reload all needed data into registers
@ DO NOT assume that any register contains anything
@	useful at this point

@@ == FOR CONSISTENCY:
@@	R3 <= address of ArrayIn
@@	R4 <= Size
@@	R5 <= SO
@@	R6 <= SE
@@	R7 <= address of array EVEN
@@	R8 <= address of array ODD

@===============STARTUP============================
	LDR R3,=ArrayIn		@ R3 <- address of ArrayIn
	LDR R4,=Size		@ R4 <- address of length
	LDR R4,[R4]			@ R4 <- length
	LDR R5,=SO			@ R5 <- address of SO
	LDR R5,[R5]			@ R5 <- SO
	MOV R5,#0			@ set current SO to 0
	LDR R6,=SE			@ R6 <- address of SO
	LDR R6,[R6]			@ R6 <- SE
	MOV R6,#0			@ set current SE to 0
	LDR R7,=EVEN		@ R7 <- address of EVEN
	LDR R8,=ODD			@ R8 <- address of ODD
	MOV R9,#0			@ R9 <- length = 0
@===============LOOPING============================
CheckLoop:				@ loop for eve/odd check
	CMP	R9,R4			@ is k < length?
	BEQ	DoneCheckLoop	@ if k = length, exit loop
	LDR R1,[R3],#4		@ R1 <- next element of ArrayIn and increment to next
	ANDS R10,R1,#1		@ use AND to check for comparison
	CMP R10,#1			@ compare if even or odd
	BEQ Odd				@ skip to odd if compare is 0
Even:					@ number is even, do stuff!
	ADD R6,R6,R1		@ increase sum of SE
	STR R1,[R7],#4		@ put current # into EVEN array and increment
	BAL endCheck		@ skip Odd
Odd:					@ number is odd, do stuff!
	ADD R5,R5,R1		@ increase sum of SO
	STR R1,[R8],#4		@ put current # into ODD array and increment
endCheck:
	ADD	R9,R9,#1		@ k++
	BAL CheckLoop		@ restart the loop!
DoneCheckLoop:
	LDR R2,=SO			@ R2 <- address of SO
	STR R5,[R2]			@ store SO, R5 -> SO
	LDR R2,=SE			@ R2 <- address of SE
	STR R6,[R2]			@ store SE, R6 -> SE
@===============END================================

@@@@@@@@@@@@@  ABOVE IS THE NEW CODE @@@@@@@@@@@@@@@@@

@ == Print sum of odd and even elements
@@@@ Note that it will print 0 until NEW code is inserted
	mov	r0,#Stdout		@ print SO message
	ldr	r1,=SumOmsg	
	swi	SWI_PrStr
	mov	r0,#Stdout	
	ldr 	r1,=SO			@ get SO
	ldr	r1,[r1]
	swi 	SWI_PrInt		@ print SO
	mov	r0,#Stdout		@ print SO message
	ldr	r1,=NL
	swi	SWI_PrStr		@ print new line	
	mov	r0,#Stdout		@ print SE message
	ldr	r1,=SumEmsg	
	swi	SWI_PrStr
	mov	r0,#Stdout	
	ldr 	r1,=SE			@ get SE
	ldr	r1,[r1]
	swi 	SWI_PrInt		@ print SE
	mov	r0,#Stdout		@ print SO message
	ldr	r1,=NL
	swi	SWI_PrStr		@ print new line 

@ == Print EVEN and ODD arrays. If it is not done, it will
@	print two arrays with 10 elements = 0
@ == Print EVEN array  ========	
	mov	r0,#Stdout		@ print message
	ldr	r1,=evenmsg	
	swi	SWI_PrStr
	ldr	r0,=EVEN		@ r0 = & EVEN array
	mov	r1,#MAXSIZE		@ r1 is array size
	BL	PrArray			@ void PrArray(R0:& array;R1:size)
	ldr	r1,=NL
	swi	SWI_PrStr		@print new line 
@ == Print ODD array  ========	
	mov	r0,#Stdout		@ print message
	ldr	r1,=oddmsg	
	swi	SWI_PrStr
	ldr	r0,=ODD			@ r0 = & EVEN array
	mov	r1,#MAXSIZE		@ r1 is array size
	BL	PrArray			@ void PrArray(R0:& array;R1:size)
	ldr	r1,=NL
	swi	SWI_PrStr		@print new line 
@ == Reset EVEN and ODD to 0
	ldr	r1,=EVEN
	ldr	r2,=ODD
	BL	ResetEvenOdd

	bal	MainLoop		@ and go to MainLoop

InFileError:
	mov	r0,#1			@ print to stdout
	ldr	r1,=FileOpenErrMsg
	swi	SWI_PrStr
	bal	MainExit		@ give up, go to end
EndOfFile:
	mov	r0,#1			@ print eof message
	ldr	r1,=eofmessage
	swi	SWI_PrStr
Exit1:	@ == Close a file =================
	ldr	R0, =InFileHandle  	@ get address of file handle
	ldr	R0, [R0]           	@ get value at address
	swi	SWI_Close		
MainExit:	
	mov	r0,#1			@print to stdout
	ldr	r1,=Bye1
	swi	SWI_PrStr
	swi	SWI_Exit  		@ stop executing instructions

@ ==========================================	
@=== void PrArray(R0:addr 1D array; R1:size)
@ Prints a 1 dimensional array of integers
@ Inputs: 	R0 = addr 1D array;
@			R1 = size of array
@ Outputs:	None
PrArray:
	STMFD	sp!,{r0-r4,lr}
	mov	r4,r1			@ r4 is loop counter for array size
	mov	r3,r0			@ r3 = &array
PrloopIn:
	mov	r0,#Stdout		@ print elements of array
	ldr 	r1,[r3],#4
	swi 	SWI_PrInt 
	mov	r0,#Stdout		@ print blanks
	ldr	r1,=Blanks
	swi	SWI_PrStr
	subs	r4,r4,#1		@ decrease loop counter
	bne	PrloopIn		@ keep printing until all elements done
	mov	r0,#Stdout		@ new line			
	ldr	r1,=NL
	swi	SWI_PrStr
	LDMFD	sp!,{r0-r4,pc}

@ ==========================================	
@=== void ResetEvenOdd(R1:addr EVEN array; R2:addr ODD array)
@ Resets the EVEN and ODD array to contains 0
@ Inputs: 	R1 = addr EVEN array;
@			R2 = addr ODD array
@ Outputs:	None	
ResetEvenOdd:
	STMFD	sp!,{r1-r4,lr}
	mov	r4,#MAXSIZE		@ r4 is loop counter for array size
	mov 	r3,#0
RSloopIn:
	str	r3,[r1],#4		@ *EVEN = 0 and EVEN++
	str	r3,[r2],#4		@ *ODD = 0 and ODD++
	subs	r4,r4,#1		@ decrease loop counter
	bne	RSloopIn		@ keep printing until all elements done
	LDMFD	sp!,{r1-r4,pc}
@ ==========================================		
	.data
	.align	@ IMPORTANT: Begin next data at word-aligned address
InFileHandle:		.skip	4
ArrayIn:   		.skip	MAXSIZE*4
SE:			.word	0
SO:			.word	0
Size:			.skip	4
EVEN:			.skip	MAXSIZE*4
ODD:			.skip	MAXSIZE*4

@	declare all char variables below to avoid alignment problems
InFileName:  		.asciz	"Quiz3in.txt"
FileOpenErrMsg: 	.asciz	"Failed to open input file\n"
ColonSpace:     	.asciz	": "
NL:			.asciz	"\n"	@ new line 
Blanks:			.asciz	"  "	@ 2 blanks
Welcome1:		.asciz	"Quiz 3 program starts\n"
IDmessage:		.asciz	"My Name is = Jakob Roberts, v00484900\n"
Bye1:			.asciz	"Quiz 3 program ends\n"
fileopenmsg:		.asciz	"Input file opened\n\n"
eofmessage:		.asciz	"\n End of file reached\n"
arsizemsg:		.asciz	"\n ## Array size =  "
elementsmsg:		.asciz	"Elements are: \n "
SumEmsg:		.asciz	"Sum of even elements = "
SumOmsg:		.asciz	"Sum of odd elements = "
oddmsg:			.asciz	"\nArray of odd elements is:\n"
evenmsg:		.asciz	"\nArray of even elements is:\n"

	.end