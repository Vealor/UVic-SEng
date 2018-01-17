@ CSC230 --  Assignment 4
@	     Kolakoski's Sequence

@ Author:  Jakob Roberts
@		v00484900
@ Filename:	A4KolakoskiA.s

@ ====================================================================
@ Local Constants
@ ====================================================================
	.equ	SWI_EXIT,      	0x11
	.equ	SWI_PrintInt,  	0x6B
	.equ	SWI_PrStr,	0x02
	.equ	Stdout,        	1
	.equ	EndInput,	-1
	
	.equ	MAXRUNS,	50
@ ====================================================================
        .text           
        .global _start
@ ====================================================================
@ ====================================================================
@===== The entry point of the program
_start:		
	LDR	R0,=Name	@ Name & Student # print
	SWI	SWI_PrStr	@ --
	LDR	R0,=Start	@ Say Start of Program
	SWI	SWI_PrStr	@ --
	
	LDR	R0,=KseqArray	@ initialize Kseq
	MOV	R1,#3		@ initialize index
	LDR	R2,=NumOnes	@ initialize NumOnes
	BL	AppendKseq
	MOV	R5,R0		@ R5 = index
@ ===============
	LDR	R0,=SeqLen	@ Length Sequence Print Start
	SWI	SWI_PrStr	@ --
	
	MOV	R1,R5        	@ R1:value-to-print = R5:Index
	MOV	R0, #Stdout   	@ print to console
	SWI	SWI_PrintInt  	@ PrintInt(R0:where, R1:value)
	
	LDR	R0,=With	@ with
	SWI	SWI_PrStr	@ --
	
	MOV	R1,#MAXRUNS     @ R1:value-to-print = MAXRUNS
	MOV	R0, #Stdout   	@ print to console
	SWI	SWI_PrintInt  	@ PrintInt(R0:where, R1:value)
	
	LDR	R0,=Runs	@ runs
	SWI	SWI_PrStr	@ --
@ ===============		
	LDR	R0,=Ones	@ ones
	SWI	SWI_PrStr	@ --
	
	LDR	R2,=NumOnes
	LDR	R1,[R2]     	@ R1:value-to-print = R2:Num1
	MOV	R0, #Stdout   	@ print to console
	SWI	SWI_PrintInt  	@ PrintInt(R0:where, R1:value)
	
	LDR	R0,=Twos	@ twos
	SWI	SWI_PrStr	@ --
	
	SUB	R1,R5,R1
	MOV	R0, #Stdout   	@ print to console
	SWI	SWI_PrintInt  	@ PrintInt(R0:where, R1:value)
	
	LDR	R0,=NewLine	@ newline
	SWI	SWI_PrStr	@ --
@ ===============	
	MOV	R0,R5
	LDR	R3,=KseqArray
	BL	PrintKseq
	
	LDR	R0,=Ends	@ END String Print
	SWI	SWI_PrStr	@ --
EndKolaoskis:
	SWI	SWI_EXIT		@ program main exit
@---------------------------------------------------------------------------------------------	
@ === AppendKseq (R0:&Kseq ; R1:Index ; R2:&NumOnes)-->R0
@   Inputs:	R0 = &Kseq ;R1 = Index ; R2 = &NumOnes	
@   Output:  	R0 = new index location 
@
@   Description:
@ 		Add the Necessary Numbers to the array for the sequence
AppendKseq:
	STMFD	sp!,{r1-r10,lr}
	MOV	R5,#4		@ FOR MUL 
	MUL	R5,R1,R5
	MOV	R1,R5		
	MOV	R5,#4		@ FOR MUL 
	LDR	R10,[R2]	@ Num1
	MOV	R8,#1		@ n=2
TopAppend:
	ADD	R8,R8,#1
	MOV	R9,#1		@ i=1
	CMP	R8,#MAXRUNS	@ n<MAXRUNS
	BGT	DoneAppendKseq	@ end for
MidAppend:
	MUL	R6,R5,R8	@ for pre-indexing
	LDR	R7,[R0,R6]	@ get # @ index n
	CMP	R9,R7		@ compare
	BGT	TopAppend	@ Go back to top loop & end for
	
	AND	R7,R8,#1	@ n%2
	ADD	R3,R7,#1	@ (1+(n%2))
	
	STR	R3,[R0,R1]	@ put new number into array
	ADD	R1,R1,#4	@ increment index
	
	CMP	R3,#1		@ was a 1 just added to array?
	BEQ	AddNum		@ if it was, increment the Num1 counter
	ADD	R9,R9,#1	@ else, i++
	BAL	MidAppend
AddNum:
	ADD	R10,R10,#1	@ Num1++
	ADD	R9,R9,#1	@ i++
	BAL	MidAppend
	
DoneAppendKseq:
	STR	R10,[R2]
	MOV	R0,R1,LSR #2	@ to be shifted to get actual number
	SUB	R0,R0,#1
	LDMFD	sp!,{r1-r10,pc}
@---------------------------------------------------------------------------------------------
@ === PrintKseq (R0:Index ; R3:&Kseq)-->R0
@   Inputs:	R0 = Index ; R3 = &Kseq		
@   Output:  	NONE
@
@   Description:
@ 		Print the Kolaoskis Array (KseqArray)
PrintKseq:
	STMFD	sp!,{r1-r10,lr}
	
	MOV	R5,#4		@ j=1
	MOV	R8,#0
	MOV	R10,R0		@ index save
NewLinePrint:
	MOV	R9,#0
	LDR	R0,=TwoSpace	@ Two spaces
	SWI	SWI_PrStr	@ --

NextIntPrint:
	ADD	R9,R9,#1
	LDR	R0,=FourSpace	@ Four spaces
	SWI	SWI_PrStr	@ --
	
	LDR	R2,[R3,R5]	@ get current #
	ADD	R5,R5,#4	@ j++
	
	CMP	R8,R10
	BEQ	DonePrintKseq
	ADD	R8,R8,#1
	
	MOV	R1,R2     	@ R1:value-to-print = R3:number
	MOV	R0, #Stdout   	@ print to console
	SWI	SWI_PrintInt  	@ PrintInt(R0:where, R1:value)

	CMP	R9,#10
	BEQ	NextLine	@ print newline and get next int
	
	BAL	NextIntPrint	@ top loop to get next int
	
NextLine:
	LDR	R0,=NewLine	@ newline
	SWI	SWI_PrStr	@ --
	BAL	NewLinePrint
	
DonePrintKseq:
	LDR	R0,=NewLine	@ newline
	SWI	SWI_PrStr	@ --
	LDMFD	sp!,{r1-r10,pc}
@---------------------------------------------------------------------------------------------
@@@@@@@@@@@@=========================
	.data
	.align
NumOnes:	.word	1
					    @Line#
KseqArray:	.word	0,1,2,0,0,0,0,0,0,0 @1
		.word	0,0,0,0,0,0,0,0,0,0 @2
		.word	0,0,0,0,0,0,0,0,0,0 @3
		.word	0,0,0,0,0,0,0,0,0,0 @4
		.word	0,0,0,0,0,0,0,0,0,0 @5
		.word	0,0,0,0,0,0,0,0,0,0 @6
		.word	0,0,0,0,0,0,0,0,0,0 @7
		.word	0,0,0,0,0,0,0,0,0,0 @8
		.word	0,0,0,0,0,0,0,0,0,0 @9
		.word	0,0,0,0,0,0,0,0,0,0 @10
		.word	0,0,0,0,0,0,0,0,0,0 @11
		.word	0,0,0,0,0,0,0,0,0,0 @12
		.word	0,0,0,0,0,0,0,0,0,0 @13
		.word	0,0,0,0,0,0,0,0,0,0 @14
		.word	0,0,0,0,0,0,0,0,0,0 @15
		.word	0,0,0,0,0,0,0,0,0,0 @16
		.word	0,0,0,0,0,0,0,0,0,0 @17
		.word	0,0,0,0,0,0,0,0,0,0 @18
		.word	0,0,0,0,0,0,0,0,0,0 @19
		.word	0,0,0,0,0,0,0,0,0,0 @20
	.align
Name:		.asciz "Jakob Roberts V00484900\n"
Start:		.asciz "Kolakoski Program starts\n\n"
SeqLen:		.asciz "Kolakoski sequence of length " 
With:		.asciz " with "
Runs:		.asciz " Runs\n"
Ones:		.asciz "Number of 1's is = "
Twos:		.asciz "\nNumber of 2's is = "
NewLine:	.asciz "\n"
Ends:		.asciz "Kolakoski Program ends\n"
TwoSpace:	.asciz "  "
FourSpace:	.asciz "    "
	.end




mov	r0, #0x0A     @ ASCII new-line character