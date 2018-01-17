@ BOARD DEMO in ARMSim# and on the Embest Board
@Written By:M. Serra

@@@ Test any blue buttons for display on 8-segment
@@@ plus use either black button to stop the program

@@ Draw a message on LCD
@@ Display H in 8-segment
@@ Both LED on
@@ LOOP: 
@@		message prompting for any blue/black button
@@		if black button pressed, then exit program with messages
@@ 		if blue pressed, display button number on 8-segment 


@SWI Codes for Board View
	.equ	SWI_SETSEG8,		0x200	@display on 8 Segment
	.equ	SWI_SETLED,		0x201	@LEDs on/off
	.equ	SWI_CheckBlack,		0x202	@check press Black button
	.equ	SWI_CheckBlue,		0x203	@check press Blue button
	.equ	SWI_DRAW_STRING,	0x204	@display a string on LCD
	.equ	SWI_DRAW_INT,		0x205	@display an int on LCD	
	.equ	SWI_CLEAR_DISPLAY,	0x206	@clear LCD
	.equ	SWI_DRAW_CHAR,		0x207	@display a char on LCD
	.equ	SWI_CLEAR_LINE,		0x208	@clear a line on LCD
	.equ	SCREEN_COLS,		40		@number of columns on LCD screen
	.equ	SCREEN_ROWS,		14		@number of rows on LCD screen
	
	.equ	SWI_EXIT,			0x11	@terminate program
	.equ	SWI_GetTicks,		0x6d	@get current time 
@ patterns for 8 segment display
	.equ 	SEG_A,		0x80	@byte values for each segment
	.equ 	SEG_B,		0x40	@of the 8 segment display
	.equ 	SEG_C,		0x20
	.equ 	SEG_D,		0x08
	.equ 	SEG_E,		0x04
	.equ 	SEG_F,		0x02
	.equ 	SEG_G,		0x01
	.equ 	SEG_P,		0x10
		
@bit patters for LED lights
	.equ	LEFT_LED,			0x02
	.equ	RIGHT_LED,			0x01
		
@bit patterns for buttons
	.equ	LEFT_BLACK_BUTTON,		0x02
	.equ	RIGHT_BLACK_BUTTON,		0x01
		
@bit patterns for blue buttons
	.equ	BLUE_KEY_00,			0x0001		@button(0)
	.equ	BLUE_KEY_01,			0x0002		@button(1)
	.equ	BLUE_KEY_02,			0x0004		@button(2)
	.equ	BLUE_KEY_03,			0x0008		@button(3)
	.equ	BLUE_KEY_04,			0x0010		@button(4)
	.equ	BLUE_KEY_05,			0x0020		@button(5)
	.equ	BLUE_KEY_06,			0x0040		@button(6)
	.equ	BLUE_KEY_07,			0x0080		@button(7)
	.equ	BLUE_KEY_08,			0x0100		@button(8)
	.equ	BLUE_KEY_09,			0x0200		@button(9)
	.equ	BLUE_KEY_10,			0x0400		@button(10)
	.equ	BLUE_KEY_11,			0x0800		@button(11)
	.equ	BLUE_KEY_12,			0x1000		@button(12)
	.equ	BLUE_KEY_13,			0x2000		@button(13)
	.equ	BLUE_KEY_14,			0x4000		@button(14)
	.equ	BLUE_KEY_15,			0x8000		@button(15)
	.text
	.global	_start
_start:
@Clear the board by clearing each peripheral in turn
@Clear the LCD screen	
	swi	SWI_CLEAR_DISPLAY
@Both LEDs off
	mov	r0,#0
	swi	SWI_SETLED
@8-segment blank
	mov	r0,#10
	mov	r1,#0
	BL	Display8Segment		@Display8Segment(R0:num;R1:P)

@draw a message to the lcd screen on line#1, column 4
	mov	r0,#4				@ column number
	mov r1,#1				@ row number
	ldr	r2,=Welcome			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	
@display the letter H in 7segment display
	ldr	r0,=SEG_B|SEG_C|SEG_G|SEG_E|SEG_F
	swi	SWI_SETSEG8
	
@turn on BOTH led
	mov	r0,#(LEFT_LED|RIGHT_LED)
	swi	SWI_SETLED
	@draw a message to the lcd screen on line#2, column 4
	mov	r0,#4				@ column number
	mov r1,#2				@ row number
	ldr	r2,=BothLED			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
@Wait for 3 second
	ldr	r3,=3000
	BL 	Wait				@ void Wait(R3:delaytime)
	
@@@@@@@@@@@@@@@@@@
@ Part 2 to test blue and black buttons
@ clear previous line 2
	mov	r0,#2		
	swi	SWI_CLEAR_LINE
	mov	r0,#3		
	swi	SWI_CLEAR_LINE
@draw a message to inform user to press any blue button
	mov	r0,#6				@ column number
	mov r1,#2				@ row number
	ldr	r2,=PressBlueBlack1	@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r0,#6				@ column number
	mov r1,#3				@ row number
	ldr	r2,=PressBlueBlack2	@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
@wait for user to press a blue button
	mov r0,#0
LB1:	
	@check black button first
	swi	SWI_CheckBlack		@want to stop?
	cmp	r0,#0
	bne	Closure				@black=stop program
	
	swi	SWI_CheckBlue		@blue pressed?
	cmp	r0,#0
	beq	LB1

	mov	r5,r0		@remember button code
@ clear previous lines
	mov	r0,#2		
	swi	SWI_CLEAR_LINE
	mov	r0,#3		
	swi	SWI_CLEAR_LINE
	mov	r0,#5		
	swi	SWI_CLEAR_LINE
@ here a blue button has been pressed - which one?
	mov	r0,r5
	cmp	r0,#BLUE_KEY_15		
	beq	FIFTEEN
	cmp	r0,#BLUE_KEY_14		
	beq	FOURTEEN
	cmp	r0,#BLUE_KEY_13		
	beq	THIRTEEN
	cmp	r0,#BLUE_KEY_12		
	beq	TWELVE
	cmp	r0,#BLUE_KEY_11		
	beq	ELEVEN
	cmp	r0,#BLUE_KEY_10		
	beq	TEN
	cmp	r0,#BLUE_KEY_09		
	beq	NINE
	cmp	r0,#BLUE_KEY_08		
	beq	EIGHT
	cmp	r0,#BLUE_KEY_07		
	beq	SEVEN
	cmp	r0,#BLUE_KEY_06		
	beq	SIX
	cmp	r0,#BLUE_KEY_05		
	beq	FIVE
	cmp	r0,#BLUE_KEY_04		
	beq	FOUR
	cmp	r0,#BLUE_KEY_03		
	beq	THREE
	cmp	r0,#BLUE_KEY_02		
	beq	TWO
	cmp	r0,#BLUE_KEY_01		
	beq	ONE
	cmp	r0,#BLUE_KEY_00
	beq	ZERO
	bal	Closure
ZERO:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#0
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
ONE:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#1
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
TWO:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#2
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
THREE:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#3
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
FOUR:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#4
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
FIVE:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#5
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
SIX:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#6
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
SEVEN:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#7
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
EIGHT:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#8
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
NINE:
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#9
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
TEN:			
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#1				@display the point for +10
	mov	r0,#0				
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
ELEVEN:			
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#1				@display the point for +10
	mov	r0,#1				
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
TWELVE:			
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#1				@display the point for +10
	mov	r0,#2				
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
THIRTEEN:			@invalid message
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#1				@display the point for +10
	mov	r0,#3				
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
FOURTEEN:			@invalid message
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#1				@display the point for +10
	mov	r0,#4				
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
FIFTEEN:			@invalid message
	mov	r0,#8				@ column number
	mov r1,#5				@ row number
	ldr	r2,=LookDisplay		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#1				@display the point for +10
	mov	r0,#5				@ clear 8-segment
	BL	Display8Segment
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	bal	MAINLOOPCHECK
MAINLOOPCHECK:
	swi	SWI_CheckBlue		@clear any extra key bounce signal
@draw a message to inform user to press any blue button
	mov	r1,#0			@clear 8-segment
	mov	r0,#10
	BL	Display8Segment
	mov	r0,#5		
	swi	SWI_CLEAR_LINE
	mov	r0,#6				@ column number
	mov r1,#2				@ row number
	ldr	r2,=PressBlueBlack1	@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r0,#6				@ column number
	mov r1,#3				@ row number
	ldr	r2,=PressBlueBlack2	@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	BAL	LB1
Closure:	
	@ clear previous lines
	mov	r0,#2		
	swi	SWI_CLEAR_LINE
	mov	r0,#3		
	swi	SWI_CLEAR_LINE
@Prepare to exit: lst message and clear the board
@draw a message to the lcd screen on line#10, column 1
	mov	r0,#1				@ column number
	mov r1,#10				@ row number
	ldr	r2,=Bye				@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen	
@Turn off both LED's
	ldr	r0,=0
	swi	SWI_SETLED
@8-segment blank
	mov	r0,#10
	mov	r1,#0
	BL	Display8Segment		@Display8Segment(R0:num;R1:P)
	ldr	r3,=2000			@delay a bit
	BL	Wait
@Clear the LCD screen	
	swi	SWI_CLEAR_DISPLAY
		
	swi	SWI_EXIT			@all done, exit
	
@ ===================================================================
@ Display8Segment (Number:R0; Point:R1) 
@ Displays the number 0-9 on the LED 8-segment display
@	with or without the extra point
@ INPUT PARAMETERS:
@	R0 = number to be displayed
@	R1: if =1, the point is also shown, else not
@ OUTPUT PARAMETERS: none
Display8Segment:
	stmfd 	sp!,{r0-r2,lr}
	ldr 	r2,=Digits			@get table of digits pre-made
	ldr 	r0,[r2,r0,lsl#2]	@compute display value in R0
	tst 	r1,#0x01 			@if r1=1,
	orrne 	r0,r0,#SEG_P 		@then show P dot
	swi 	SWI_SETSEG8
	ldmfd 	sp!,{r0-r2,pc}

@@ ======================================================
@ Wait(Delay:r3) wait for r3 milliseconds
@ Delays for the amount of time stored in r3 for a 15-bit timer
@ INPUT PARAMETERS: R3 = times in milliseconds
@ OUTPUT PARAMETERS: none
Wait:
	stmfd	sp!,{r0-r5,lr}	
	ldr	r4,=0x00007FFF	@mask for 15-bit timer
	SWI	SWI_GetTicks	@Get start time
	and	r1,r0,r4		@adjusted time to 15-bit			
Wloop:
	SWI	SWI_GetTicks	@Get current time
	and	r2,r0,r4		@adjusted time to 15-bit
	cmp	r2,r1
	blt	Roll			@rolled above 15 bits
	sub	r5,r2,r1		@compute easy elapsed time
	bal	CmpLoop
Roll:	
	sub	r5,r4,r1		@compute rolled elapsed time
	add	r5,r5,r2
CmpLoop:	
	cmp	r5,r3			@is elapsed time < delay?
	blt	Wloop			@Continue with delay	
Xwait:	
	ldmfd	sp!,{r0-r5,pc}


@ ================================================
	.data
	.align
Digits:
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_G 	@0
	.word 	SEG_B|SEG_C 							@1
	.word 	SEG_A|SEG_B|SEG_F|SEG_E|SEG_D 			@2
	.word 	SEG_A|SEG_B|SEG_F|SEG_C|SEG_D 			@3
	.word 	SEG_G|SEG_F|SEG_B|SEG_C 				@4
	.word 	SEG_A|SEG_G|SEG_F|SEG_C|SEG_D 			@5
	.word 	SEG_A|SEG_G|SEG_F|SEG_E|SEG_D|SEG_C 	@6
	.word 	SEG_A|SEG_B|SEG_C 						@7
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_F|SEG_G @8
	.word 	SEG_A|SEG_B|SEG_F|SEG_G|SEG_C 			@9
	.word 	0 										@Blank display
Welcome:	.asciz	"Welcome to Board Testing"
BothLED:	.asciz	"BOTH lights - 3 seconds"
PressBlueBlack1:	.asciz	"Press any BLUE to display "
PressBlueBlack2:	.asciz	"    or Black to stop" 
LookDisplay:	.asciz	"Look and check 8-segment"
Bye:		.asciz	"Bye for now."
Blank:		.asciz	" "

	.end