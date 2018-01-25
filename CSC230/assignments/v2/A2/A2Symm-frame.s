@ =============== Micaela Serra
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
	
@ === NEW CODE HERE
	@ 1. Print the diagonal
	@ 2. Compute the transpose and store it in MatTransp
	@ 3. Print the transpose
	@ 4. If matrix is square,check if symmetric and print message
	@ 5. If matrix is square,check if skew -symmetric and print message
	
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
Welcome2:				.asciz	"\nJean Luc Picard, V0000000\n"
Bye:					.asciz	"\nAll done - Bye\n"
InputFileName:			.asciz	"INA2.txt"
InFileErrorMessage:		.asciz	"Unable to open input file\n"
Matlabel:				.asciz	"\n\n### MATRIX  "
Sizemsg:				.asciz	"  of size: "
Transplabel:			.asciz	"\n\nIts Transpose ###\n"
NotSquare:				.asciz	"\nMatrix is not square, no testing done\n\n"
Symm:					.asciz	"\nMatrix is symmetric\n\n"
SkSymm:					.asciz	"\nMatrix is skew symmetric\n\n"
SymmNo:					.asciz	"\nMatrix is not symmetric\n\n"
SkSymmNo:				.asciz	"\nMatrix is not skew symmetric\n\n"

	.end
	
