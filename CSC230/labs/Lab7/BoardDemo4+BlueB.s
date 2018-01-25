@ BOARD DEMO in ARMSim# and on the Embest Board
@Written By:M. Serra

@@@ Test only the blue buttons for a maximum of 16 tests

@@ Draw a message on LCD
@@ 		prompting for blue buttons 
@@ Loop for 16 tests max and display test number
@@ 		When button press, display corresponding number in 8-segment
@@		or display invalid message if >9
@@ Clear everything and exit

@SWI Codes for Board View
	.equ	SWI_SETSEG8,		0x200	@display on 8 Segment
	.equ	SWI_SETLED,			0x201	@LEDs on/off
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
@ patterns for time delays
	.equ	ONESEC,				1000
	.equ	TWOSEC,				2000
	.equ	THREESEC,			3000
	
	.equ	MaxTests,			16
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
	ldr	r2,=WelcomeBlue		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen

@turn on both LEDs
	mov	r0,#(LEFT_LED|RIGHT_LED)
	swi	SWI_SETLED
	
@ Test the blue buttons with prompting, 
@ display its number on 8-segment for 3 seconds. 
@ If >9, invalid message on LCD and 8-seg blank.
@ Loop for max of 16 tests, and show test number

@Draw a message to inform user to press a blue button
	mov	r0,#6				@ column number
	mov r1,#3				@ row number
	ldr	r2,=PressBlue		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	@ set loop for tests
	mov r4,#1				@ counter for number of tests
	mov r5,#MaxTests		@ maximum number of tests allowed
	mov	r6,#10				@ initial column for test number
	mov	r7,#10				@ initial row for test number
	mov	r0,#1				@ display msg for number of tests
	mov r1,r7				@ col 1, row 10	
	ldr	r2,=TestBlue		
	swi	SWI_DRAW_STRING	
BLUELOOP:			@ prompt user to press blue button
	mov	r0,#6				@ column number
	mov r1,#3				@ row number
	ldr	r2,=PressBlue		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	swi	SWI_CheckBlue
	mov r0,#0				
BB1:				@wait for user to press blue button
	swi	SWI_CheckBlue		@get button press into R0
	ldr	r3,=200				@ wait for key bounce
	BL	Wait				@ void Wait(R3:delaytime)
	cmp	r0,#0
	beq	BB1					@ if zero, no button pressed
	@ here a blue button has been pressed - which one?
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
	bal	ErrorDetecting
ZERO:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#0
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
ONE:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#1
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
TWO:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#2
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
THREE:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#3
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
FOUR:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#4
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
FIVE:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#5
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
SIX:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#6
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
SEVEN:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#7
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
EIGHT:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#8
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
NINE:
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r1,#0			@display blue button number on 8-seg
	mov	r0,#9
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
TEN:			@invalid message
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#4				@ column number
	mov r1,#5				@ row number
	ldr	r2,=InvBlue			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0
	mov	r0,#10				@ clear 8-segment
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#5				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
ELEVEN:			@invalid message
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#4				@ column number
	mov r1,#5				@ row number
	ldr	r2,=InvBlue			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0
	mov	r0,#10				@ clear 8-segment
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#5			@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
TWELVE:			@invalid message
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#4				@ column number
	mov r1,#5				@ row number
	ldr	r2,=InvBlue			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0
	mov	r0,#10				@ clear 8-segment
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#5			@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
THIRTEEN:			@invalid message
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#4				@ column number
	mov r1,#5				@ row number
	ldr	r2,=InvBlue			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0
	mov	r0,#10				@ clear 8-segment
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#5			@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
FOURTEEN:			@invalid message
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#4				@ column number
	mov r1,#5				@ row number
	ldr	r2,=InvBlue			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0
	mov	r0,#10				@ clear 8-segment
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#5			@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP
FIFTEEN:			@invalid message
	mov	r0,#3				@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#4				@ column number
	mov r1,#5				@ row number
	ldr	r2,=InvBlue			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	mov	r1,#0
	mov	r0,#10				@ clear 8-segment
	BL	Display8Segment
	@draw a message to the lcd screen on line#3, column 4
	mov	r0,#4				@ column number
	mov r1,#3				@ row number
	ldr	r2,=WaitMsg			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#3			@clear previous line 
	swi	SWI_CLEAR_LINE
	mov	r0,#5			@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	CKBLUELOOP

ErrorDetecting:
	mov	r0,#4				@ column number
	mov r1,#5				@ row number
	ldr	r2,=ErrorMsg		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	ldr	r3,=2000			@ wait a bit
	BL	Wait				@ void Wait(R3:delaytime)
	mov	r0,#5				@clear previous line 
	swi	SWI_CLEAR_LINE
	bal	ALLDONE
CKBLUELOOP:					@ display test number				
	mov r0,r6				@column number
	mov r1,r7				@row number
	mov r2,r4				@test number
	swi	SWI_DRAW_INT
	add	r6,r6,#3			@update col number for test #
	cmp	r6,#SCREEN_COLS		@at right side of screen?
	blt	TS3					@if not, okay
	mov	r6,#10				@else restart at column
	add	r7,r7,#1			@and go to next row
TS3:	
	add	r4,r4,#1			@update test number
	cmp	r4,r5				@all tests done?
	ble	BLUELOOP			@give only 16 tests
	
@Prepare to exit: lst message and clear the board
@draw a message to the lcd screen on line#10, column 1
ALLDONE:
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
	ldr	r3,=TWOSEC			@delay a bit
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
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_G @0
	.word 	SEG_B|SEG_C @1
	.word 	SEG_A|SEG_B|SEG_F|SEG_E|SEG_D @2
	.word 	SEG_A|SEG_B|SEG_F|SEG_C|SEG_D @3
	.word 	SEG_G|SEG_F|SEG_B|SEG_C @4
	.word 	SEG_A|SEG_G|SEG_F|SEG_C|SEG_D @5
	.word 	SEG_A|SEG_G|SEG_F|SEG_E|SEG_D|SEG_C @6
	.word 	SEG_A|SEG_B|SEG_C @7
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_F|SEG_G @8
	.word 	SEG_A|SEG_B|SEG_F|SEG_G|SEG_C @9
	.word 	0 		@Blank display
WelcomeBlue:	.asciz	"Welcome to Board Testing - Blue Buttons"
WaitMsg:	.asciz	"Blue pressed - wait"
Bye:		.asciz	"Bye for now."
Blank:		.asciz	" "
PressBlue:	.asciz	"Press a BLUE button"
InvBlue:	.asciz	"Invalid blue button - try again"
TestBlue:	.asciz	"Tests ="
ErrorMsg:	.asciz	"Error detecting button"

	.end