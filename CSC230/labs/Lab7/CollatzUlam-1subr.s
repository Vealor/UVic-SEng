@ Collatz/Ulam's convergence ***

@ This program counts the steps for the Collatz/Ulam
@ convergence 
@@@ VERSION 2: ONE FUNCTION FOR COUNTING STEPS
	
@ Pseudo-code:
@	Get number n
@	if n even then n := n/2
@	else n := n*3+1
@	Repeat, it eventually converges to 1
@	Count the steps to convergence

@ Flowchart:
@ Get n, k=0 (here n is read from an input file)
@ Mainloop: Is it EOF? ==> YES, go to finish
@ 	Cloop:Is n = 1? ==> YES, go to done
@ 		steps k++
@ 		Check: Is n even? ==> YES go to even
@		n := n*3 +1 ==> go to  Check
@   	even: n := n/2 ==> go to Cloop

@Register map:
@ r1	number to be tested
@ r0	counter for k steps to convergence
@ r2-r5	computational

	.equ	SWI_PrInt, 	0x6b
	.equ	SWI_PrStr,  0x69
	.equ	SWI_AlStr,  0x06
	.equ	SWI_PrChr,  0x00
	.equ	SWI_Open,   0x66
	.equ	SWI_Close,	0x68
	.equ	SWI_RdInt,	0x6c
	.equ	SWI_Exit,   0x11
	.equ	Console,	1
	.text		
	.global	_start	
_start:
	ldr	r0,=InputFileName		@ open input file
	mov	r1,#0				
	swi	SWI_Open			
	bcs	InFileError				@ if file not ok, exit
	ldr	r1,=InFileHandle
	str	r0,[r1]
	ldr	r1,=Hello				@ print initial message
	mov	r0,#Console		
	swi	SWI_PrStr	
Mainloop:
	ldr	r0,=InFileHandle		@ read N into R0
	ldr	r0,[r0]				
	swi	SWI_RdInt			
	bcs	Endoffile
	mov	r6,r0				@ r6 = copy of N
	mov	r4,r0				@ r4 = copy of N
	bl	Countsteps			@ k:r0 = Countsteps(N:r4)
	
	mov	r3,r0				@ save result for printing later
	ldr	r1,=Result1			@print message and 		
	mov	r0,#Console			@print N from R4 and	
	swi	SWI_PrStr			@steps from R3
	mov	r1,r6
	mov	r0,#Console			
	swi	SWI_PrInt	
	ldr	r1,=Result2					
	mov	r0,#Console				
	swi	SWI_PrStr
	mov	r1,r3
	mov	r0,#Console			
	swi	SWI_PrInt	
	ldr	r1,=NL				@ New line
	mov	r0,#Console		
	swi	SWI_PrStr
	bal	Mainloop
	@*********************
Endoffile:
	ldr	r1,=EOFmsg
	mov	r0,#Console	
	swi	SWI_PrStr	
	bal	Closure
InFileError:
	ldr	r0,=InFileErrorMessage
	swi	SWI_AlStr
Closure:
	ldr	r0,=InFileHandle	@ close the input file
	ldr	r0,[r0]				
	swi	SWI_Close			
	ldr	r1,=Bye
	mov	r0,#Console	
	swi	SWI_PrStr			@ final message to screen				
CollatzUlamEnd:
	swi	0x11
	
@@@@@ k:r0 = Countsteps (N:r4) @@@@@
@@ Count the steps in the Collatz/Ulam convergence
@	if n even then n := n/2
@	else n := n*3+1
@	Repeat, it eventually converges to 1
@	Count the steps to convergence
Countsteps:
	STMFD	sp!,{R2-R6,lr}	
	mov	r6,r4			@ keep of copy of original N
	mov	r3,#0				@ r3 counts steps to convergence	
Cloop:
	cmp	r4,#1				@ converged yet?
	beq	Done1
Check:
	add		r3,r3,#1		@increment # steps
	mov		r2,r4			@copy number
	ands	r2,r2,#1		@is n odd or even?
	beq		Even
	mov		r2,#3			@compute n*3+1
	mul		r5,r4,r2
	add		r4,r5,#1
	bal		Check
Even:
	mov		r4,r4,asr #1	@divide by 2
	bal		Cloop
Done1:	
	mov		r0,r3			@return steps in r0
ENDCT:	LDMFD	sp!,{R2-R6,pc}

	
		
@ ========== Data ==========
	.data		
	.align	
InFileHandle:	.word	0
Bye:	.asciz	"The End - Bye\n"
Hello:	.asciz	"Welcome to Collatz/Ulam\n"
InputFileName:
	.asciz	"CollatzUlam.txt"
InFileErrorMessage:
	.asciz	"Unable to open input file\n"
EOFmsg:
	.asciz	"End of file reached\n"
Result1:
	.asciz	"For the number N = "
Result2:
	.asciz	"   the number of steps is K = "
NL:	.asciz	"\n"	@ new line 

	.end
	