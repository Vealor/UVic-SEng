@ -------------- Jakob Roberts
@ -------------- v00 484900
@ -------------- A2csc230.s
@ -------------- Assignment 2
@ -------------- CSC230
@ *************** Assignment 2  ***************
@ This program reads 2 dimensional matrices until end of
@ file, prints them, prints their diagonal, computes their 
@ transpose and checks if they are symmetric or skew symmetric

@ Version 0 - Template given
@ Print initial messages including identification
@ Open the input file 
@	if problems, print message and exit program
@ Call ReadInt to read the number of rows of a matrix
@ While it is not end of file {
@	Call ReadInt to read the number of columns of a matrix
@	Call Rdmat to read integer elements of a matrix and store them
@	Print the matrix headings
@	Call PrMat to print the matrix 
@	TO BE DONE IN ASSIGNMENT:
@		Print the diagonal of the matrix with a heading 
@			(calling subroutine Diag is optional) 
@		Compute the transpose of the matrix 
@		(calling subroutine Transpose is optional) 
@		Print the transpose headings
@		Call PrMat to print the transpose 
@		If the matrix is square
@			Test if symmetric (calling subroutine Symm is optional) 
@			Print message about symmetric
@			Test if skew-symmetric (calling subroutine SkewSymm is optional) 
@			Print message about skew-symmetric
@	Call ReadInt to read the number of columns of a matrix
@ } (end while loop)
@ Exit program: close the input file, print a closing message, exit.


	.equ	SWI_Exit,   0x11	@terminate program
	.equ	SWI_Open,   0x66	@open a file
	.equ	SWI_Close,	0x68	@close a file
	.equ	SWI_PrStr,  0x69	@print a string
	.equ	SWI_PrInt,	0x6b	@print integer 
	.equ	SWI_RdInt,	0x6c	@read an integer from file
	.equ	Stdout,		1		@output mode for Stdout
	.equ	FileInputMode,  0
	.equ	MAXSIZE,	10		@max dimensions for matrix

		.global _start
		.text	
_start:
@ ====  open the input file 
	ldr	r0,=InputFileName	@ set to open input file
	mov	r1,#0				@ R1 = mode = input
	swi	SWI_Open			
	bcs	InFileError			@ if c bit is set, problem, exit
	ldr	r1,=InFileHandle	@ else save the file handle
	str	r0,[r1]				
	
@ ==== print initial messages to screen
	ldr	r1,=Welcome1		
	mov	r0,#Stdout			
	swi	SWI_PrStr
	ldr	r1,=Welcome2		
	mov	r0,#Stdout			
	swi	SWI_PrStr
	
@ ==== read number of rows from input file
	ldr	r0,=InFileHandle	@ load the input file handle
	BL	ReadInt				@ R0= ReadInt(R0:InputFileHandle)
	bcs	Closure				@ if c bit is set, EOF reached	
	mov	r10,#0				@ R10=current matrix number
IOLoop:
	mov	r9,r0				@ R9 = number rows read in
	ldr	r0,=RrowsM			@ R0 = &RrowsM
	str	r9,[r0]				@ save number of rows
	add	r10,r10,#1			@ increase current matrix number
	ldr	r0,=InFileHandle	@ read number columns
	BL	ReadInt				@ R0= ReadInt(R0:InputFileHandle)
	mov	r8,r0				@ R8 = number columns read in
	ldr	r0,=CcolsM			@ R0 = &CcolsM
	str	r8,[r0]				@ save number of columns
@ === read elements of matrix into MatMain	
	ldr	r0,=InFileHandle	@ load the input file handle
	ldr	r1,=MatMain			@ R1 = address of Matrix				
	mov	r2,r9				@ R2 = number rows
	mov	r3,r8				@ R3 = number columns
	BL	RdMat				@ void RdMat(R0:InputFile;R1:addr matrix;
							@ R2:number rows;R3:number columns)
@ === print matrix label and sizes
	ldr	r1,=Matlabel		
	mov	r0,#Stdout			
	swi	SWI_PrStr
	mov	r1,r10				@ R1 = current matrix number
	mov	r0,#Stdout			
	swi	SWI_PrInt
	ldr	r1,=Sizemsg			@ print sizes
	mov	r0,#Stdout			
	swi	SWI_PrStr
	mov	r1,r2				@ r1 = number of rows
	mov	r0,#Stdout
	swi	SWI_PrInt
	ldr	r1,=BLANKS2			@ blanks
	mov	r0,#Stdout			
	swi	SWI_PrStr
	mov	r1,r3				@ r1 = number of columns
	mov	r0,#Stdout			
	swi	SWI_PrInt		
	ldr	r1,=NL				@ new line
	mov	r0,#Stdout			
	swi	SWI_PrStr
@ === print the matrix
	ldr	r0,=MatMain			@ R0 = address of Matrix				
	mov	r1,r9				@ R1 = number of rows
	mov	r2,r8				@ R2 = number of columns
	BL	PrMat		@ void PrMat(R0:addr matrix;R1:number of rows;
@								R2:number of columns)
@---------------------------------------------------------------------------------------------
@---------------------------------------------------------------------------------------------
@ === NEW CODE HERE
	@ 1. Print the diagonal
	@ 2. Compute the transpose and store it in MatTransp
	@ 3. Print the transpose
	@ 4. If matrix is square,check if symmetric and print message
	@ 5. If matrix is square,check if skew -symmetric and print message
	
	@ === Reading an integer from a file 	
	@ int:R0 = ReadInt( R0:InputFileHandle) 
	@ INPUTS:R0 - pointer to input file
	@ OUTPUTS: R0 - integer read
	@ SIDE EFFECTS: If EOF then C bit is set in CPSR
@---------------------------------------------------------------------------------------------
	LDR R0,=MatMain				@ R0 <= Address of MatMain
	LDR R3,=CcolsM				@ R3 <= Address of CcolsM
	LDR R3,[R3]					@ R3 <= Value of MatMain Cols
	LDR R2,=RrowsM				@ R2 <= Address of RrowsM
	LDR R2,[R2]					@ R2 <= Value of MatMain Rows
	BL Diag						@ void Diag(R0:addr matrix; R2:number rows; R3:number columns)
	
	LDR R0,=MatMain				@ R0 <= Address of MatMain
	LDR R1,=MatTransp			@ R1 <= Address of MatTransp
	LDR R3,=CcolsM				@ R3 <= Address of CcolsM
	LDR R3,[R3]					@ R3 <= Value of MatMain Cols
	LDR R2,=RrowsM				@ R2 <= Address of RrowsM
	LDR R2,[R2]					@ R2 <= Value of MatMain Rows
	BL Transpose				@ void Transpose(R0:addr matrix; R1:addr transpose; R2:number rows; R3:number columns)

CheckSquare:					@ checks if matrix is square and performs appropriate operations
	LDR R8,=CcolsM				@ R5 <= Address of CcolsM
	LDR R8,[R8]					@ R5 <= Value of MatMain Cols
	LDR R9,=RrowsM				@ R6 <= Address of RrowsM
	LDR R9,[R9]					@ R6 <= Value of MatMain Rows
	CMP R8,R9					@ compares Row and Col to see if same
	BNE isnotSquare				@ branch to print NotSquare if Row != Col
	
	LDR R0,=MatMain				@ R0 <= Address of MatMain
	LDR R1,=MatTransp			@ R1 <= Address of MatTransp
	MUL R2,R8,R9				@ R2 <= length of matrices
	BL Symm						@ void Symm(R0:addr matrix; R1:addr transpose; R2:total number of elements)
	
	LDR R0,=MatMain				@ R0 <= Address of MatMain
	LDR R1,=MatTransp			@ R1 <= Address of MatTransp
	MUL R2,R8,R9				@ R2 <= length of matrices
	BL SkewSymm					@ void CheckSkewSymm(R0:addr matrix; R1:addr transpose; R2:total number of elements)
	BAL endSquare				@ after checks, finish SquareCheck
isnotSquare:
	ldr	r1,=NotSquare		@ Printout Notsquare
	mov	r0,#Stdout			@ -
	swi	SWI_PrStr			@ -
endSquare:
	
@---------------------------------------------------------------------------------------------
@---------------------------------------------------------------------------------------------
@ === read next matrix number of rows and check for end of file
	ldr	r0,=InFileHandle	@ load the input file handle
	BL	ReadInt				@ R0= ReadInt(R0:InputFileHandle)
	bcs	Closure				@ if c bit is set, EOF  reached
	bal	IOLoop				@ else keep looping
	
@ === Errors Handling 	
InFileError:
	mov	r0,#Stdout				@ error for input file
	ldr	r1,=InFileErrorMessage	@ to Stdout	
	swi	SWI_PrStr
	bal	Closure					@ exit
	
@ === Exit segment	
Closure:	
	ldr	r1,=Bye				@ final message
	mov	r0,#Stdout			@ to screen
	swi	SWI_PrStr			 
	ldr	r0,=InFileHandle	
	ldr	r0,[r0]			
	swi	SWI_Close			@ close the input file
	swi	SWI_Exit
	
@ ========== OTHER FUNCTIONS ============	
@---------------------------------------------------------------------------------------------
@----Symm-------------------------------------------------------------------------------------
Symm:							@ void Symm(R0:addr matrix; R1:addr transpose; R2:total number of elements)
	STMFD	sp!,{R3-R10,lr}		@ save registers
	MOV R7,R0					@ R7 <= Address of MatMain
	LDR R4,[R7]					@ R4 <= first value of MatMain
	MOV R6,R1					@ R6 <= Address of MatTransp
	LDR R3,[R6]					@ R3 <= first value of MatTransp
	MOV R5,#0					@ R5 <= loop counter i=0
symmLoop:
	CMP R5,R2					@ is i<Length			
	BEQ isSymm					@ end of loop and go to print isSymm
	CMP	R4,R3					@ compare values in matrices
	BNE isnotSymm				@ if matrix values not same, print notSymm
	LDR R4,[R7],#4				@ get MatMain and Increment
	LDR R3,[R6],#4				@ get MatTransp and Increment
	ADD R5,R5,#1				@ increment Length check counter
	BAL symmLoop				@ return to top of symmLoop
isSymm:
	ldr	r1,=SymmYes				@ Printout Is Symm
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	BAL endSymm					@ get out, is Symm
isnotSymm:
	ldr	r1,=SymmNo				@ Printout Not Symm
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
endSymm:
	LDMFD	sp!,{R3-R10,pc}		@ reload registers
@----SkewSymm---------------------------------------------------------------------------------
SkewSymm:						@ void CheckSkewSymm(R0:addr matrix; R1:addr transpose; R2:total number of elements)
	STMFD	sp!,{R3-R10,lr}		@ save registers
	MOV R7,R0					@ R7 <= Address of MatMain
	LDR R4,[R7]					@ R4 <= first value of MatMain
	MOV R6,R1					@ R6 <= Address of MatTransp
	LDR R3,[R6]					@ R3 <= first value of MatTransp
	MOV R0,#-1					@ used for negative conversion in multiply
	MOV R5,#0					@ R5 <= loop counter i=0
skewsymmLoop:
	CMP R5,R2					@ is i<Length			
	BEQ isSkewSymm				@ end of loop and go to print isSkewSymm
	MUL R1,R4,R0				@ takes negative value of MatMain
	CMP	R1,R3					@ compare values in matrices (with one being negative)
	BNE isnotSkewSymm			@ if matrix values not same, print notSkewSymm
	LDR R4,[R7],#4				@ get MatMain and Increment
	LDR R3,[R6],#4				@ get MatTransp and Increment
	ADD R5,R5,#1				@ increment Length check counter
	BAL skewsymmLoop			@ return to top of symmLoop
isSkewSymm:
	ldr	r1,=SkSymmYes			@ Printout Is SkewSymm
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	BAL endSkewSymm				@ get out, is SkewSymm
isnotSkewSymm:
	ldr	r1,=SkSymmNo			@ Printout Not SkewSymm
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
endSkewSymm:
	LDMFD	sp!,{R3-R10,pc}		@ reload registers
@---Transpose---------------------------------------------------------------------------------
Transpose:						@ void Transpose(R0:addr matrix; R1:addr transpose; R2:number rows; R3:number columns)
	STMFD	sp!,{R4-R10,lr}		@ save registers
	MOV R7,R0					@ R7 <= Address of MatMain
	MOV R6,R1					@ R6 <= Address of MatTransp
	MOV R9,#0					@ R9 <=outloop counter i=0
	MOV R4,#4					@ For Multiplication
	MOV R5,#0					@ Counter for Column offset
	MUL R0,R3,R4				@ Offset required to get to next row
outloop:
	CMP	R9,R3					@ is i<Cols?
	BEQ endtransloops			@ if i = Cols, end loops
	LDR R7,=MatMain				@ R7 <= Address of MatMain
	ADD R7,R7,R5				@ Increment to Col Offset					
	ADD R5,R5,#4				@ Increment Col Offset
	ADD	R9,R9,#1				@ i++
	MOV	R8,#0					@ R3 <= inloop counter j=0
inloop:
	CMP	R8,R2					@ is j<Rows?
	BEQ	outloop					@ if j = Rows, go to outloop
	LDR	R1,[R7],R0				@ gets MatMain Value
	STR	R1,[R6],#4				@ puts value into Transp & increment transpose location
	ADD	R8,R8,#1				@ j++
	BAL	inloop					@ back to beginning of inner loop
endtransloops:
	LDR R3,=CcolsM				@ R5 <= Address of CcolsM
	LDR R3,[R3]					@ R5 <= Value of MatMain Cols
	LDR R2,=RrowsM				@ R6 <= Address of RrowsM
	LDR R2,[R2]					@ R6 <= Value of MatMain Rows
	LDR R5,=CcolsTr				@ R5 <= Address of CcolsTr
	LDR R5,[R5]					@ R5 <= Value of Transpose Cols
	LDR R6,=RrowsTr				@ R6 <= Address of RrowsTr
	LDR R6,[R6]					@ R6 <= Value of Transpose Rows
	MOV R5,R2					@ Assign Col of Transp
	MOV R6,R3					@ Assign Row of Transp
	@ === print transpose label and sizes
	ldr	r1,=Transplabel			@ Print Transpose Header
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	mov	r1,r6					@ r1 = number of rows
	mov	r0,#Stdout				@ -
	swi	SWI_PrInt				@ -
	ldr	r1,=BLANKS2				@ blanks
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	mov	r1,r5					@ r1 = number of columns
	mov	r0,#Stdout				@ -
	swi	SWI_PrInt				@ -
	ldr	r1,=Transpmsg			@ "is:"
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	ldr	r1,=NL					@ new line
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	LDR R0,=MatTransp			@ R0 = address of Transpose
	MOV R1,R6					@ R1 = number of rows
	MOV R2,R5					@ R2 = number of columns
	BL PrMat					@ Prints Transpose Matrix
endTrans:
	LDMFD	sp!,{R4-R10,pc}		@ reload registers
@---Diagonal----------------------------------------------------------------------------------
Diag:							@ void Diag(R0:addr matrix; R2:number rows; R3:number columns)
	STMFD	sp!,{R1,R4-R10,lr}	@ save registers
	MOV R7,R0					@ R7 <= Address of MatMain
	ldr	r1,=Diagis				@ print Diag message
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -

	
	CMP	R3,R2					@ compare row and Col
	BLE	Diagcol					@ move to col if row > col
	MOV	R5,#0					@ R5 <= loop counter i=0
	ADD R9,R3,#1				@ R9 <= diag grab increment
	MOV R8,#4					@ use for mult
	MUL R4,R9,R8				@ get col increment
rowLoop:
	CMP	R5,R2					@ is i<Rows?
	BEQ	endDiag					@ if Rows=0, exit loop
	ldr	R1,[R7],R4				@ print Diagonals
	mov	R0,#Stdout				@ -
	swi	SWI_PrInt				@ -
	ldr	r1,=BLANKS2				@ Print Space
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	ADD	R5,R5,#1				@ i++
	BAL	rowLoop					@ repeat
Diagcol:
	MOV	R5,#0					@ R5 <= loop counter i=0
	ADD R9,R3,#1				@ R9 <= diag grab increment
	MOV R8,#4					@ Use for Mult R8 <= 4
	MUL R4,R9,R8				@ Mult for col offset to "get to next line"
colLoop:
	CMP	R5,R3					@ is i<Cols?
	BEQ	endDiag					@ if Cols=0, exit loop
	ldr	R1,[R7],R4				@ print Diagonals
	mov	R0,#Stdout				@ -
	swi	SWI_PrInt				@ -
	ldr	r1,=BLANKS2				@ Print Space
	mov	r0,#Stdout				@ -
	swi	SWI_PrStr				@ -
	ADD	R5,R5,#1				@ i++
	BAL	colLoop					@ return to top of colLoop
endDiag:
	LDMFD	sp!,{R1,R4-R10,pc}		@ reload registers
@---------------------------------------------------------------------------------------------
@---------------------------------------------------------------------------------------------
@ === Reading an integer from a file 	
	@ int:R0 = ReadInt( R0:InputFileHandle) 
	@ INPUTS:R0 - pointer to input file
	@ OUTPUTS: R0 - integer read
	@ SIDE EFFECTS: If EOF then C bit is set in CPSR
ReadInt:
	STMFD	sp!,{lr}		
	ldr	r0,[r0]			@ load input file handle
	swi	SWI_RdInt		@ to read from
	LDMFD	sp!,{pc}
	
@ === Reading a 2D matrix given its sizes	
	@ void RdMat(R0:InputFileHandle;R1:&Matrix;R2:Num rows;R3:Num cols)
	@ Input parameters:
	@ 	R0 - pointer to input file
	@ 	R1 - matrix address
	@ 	R2 - number rows; R3 - number columns
	@ Output parameters: None
	@ Side effects: Matrix is filled with elements
RdMat:
	STMFD	sp!,{r0-r3,r8-r10,lr}				
	mov	r8,r1			@ R8 is pointer to matrix
	mul	r9,r2,r3		@ R9 = total number of elements
	mov r10,r0			@ R10 = pointer to input file
RdAll:
	ldr		r0,[r10]		@ load input file handle		
	swi		SWI_RdInt		@ get matrix element
	str		r0,[r8],#4		@ store it in matrix
	subs	r9,r9,#1		@ count number of elements
	bne		RdAll
DoneRdMat:		
	LDMFD	sp!,{r0-r3,r8-r10,pc}
		
@ === Printing a 2D matrix to screen 	
	@ void PrMat(R0:&Matrix;R1:Num rows;R2:Num cols) 
	@ Input parameters:
	@ 	R0 - matrix address
	@ 	R1 - number rows; R2 - number columns
	@ Output parameters: None
PrMat:
	STMFD	sp!,{r0-r2,r7-r9,lr}				
	mov	r8,r0			@ R8 is pointer to matrix
	mov	r9,r2			@ R9 = number columns
	mov	r7,r1			@ R7 = number rows
PrRow:
	ldr		r1,[r8],#4			@ R1 = Mat[i++]
	mov		r0,#Stdout			@ print to screen
	swi		SWI_PrInt
	ldr		r1,=BLANKS2
	mov		r0,#Stdout			
	swi		SWI_PrStr
	subs	r9,r9,#1			@end of row?
	bne		PrRow
	ldr		r1,=NL				@new line
	mov		r0,#Stdout			
	swi		SWI_PrStr
	subs	r7,r7,#1			@end of all rows?
	beq		DonePrMat
	mov		r9,r2				@reset column count
	bal		PrRow
DonePrMat:		
	LDMFD	sp!,{r0-r2,r7-r9,pc}
	
	
@ *************** Data Segment ***************
	.data
	.align
@ declare integer variables
InFileHandle:			.word	0
MatMain:				.skip	4*MAXSIZE*MAXSIZE
MatTransp:				.skip	4*MAXSIZE*MAXSIZE
RrowsM:					.skip	4
CcolsM:					.skip	4
RrowsTr:				.skip	4
CcolsTr:				.skip	4
CurrMat:				.word	0
@ declare strings and characters 
NL:						.asciz	"\n"	@ new line 
BLANKS2:				.asciz	"  "	@blanks
Welcome1:				.asciz	"\nAssignment 2 on matrices \n"
Welcome2:				.asciz	"\nJakob Roberts, V00484900\n"
Bye:					.asciz	"\nAll done - Bye\n"
InputFileName:			.asciz	"INA2.txt"
InFileErrorMessage:		.asciz	"Unable to open input file\n"
Matlabel:				.asciz	"\n\n### MATRIX  "
Sizemsg:				.asciz	"  of size: "
Diagis:					.asciz  "\nDiagonal is:\n"
Transplabel:			.asciz	"\n\nIts Transpose of size "
Transpmsg:				.asciz	"  is: \n"
NotSquare:				.asciz	"\nMatrix is not square, no testing done\n\n"
SymmYes:				.asciz	"\nMatrix is symmetric\n"
SkSymmYes:				.asciz	"Matrix is skew symmetric\n\n"
SymmNo:					.asciz	"\nMatrix is not symmetric\n"
SkSymmNo:				.asciz	"Matrix is not skew symmetric\n\n"

	.end
	
