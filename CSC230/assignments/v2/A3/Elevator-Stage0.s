@ CSC230 --  Elevator simulation program

@ Author:  Micaela Serra 
@ Modified by: Captain Picard  <---- PUT YOUR NAME AND ID HERE!!!! 

@===== STAGE 0
@  	Sets initial outputs and screen
@	Enters IDLE state and updates simulated time every second
@	Polls for left black button to exit simulation
        .equ    SWI_EXIT, 		0x11		@terminate program
        @ swi codes for using the Embest board
        .equ    SWI_SETSEG8, 		0x200	@display on 8 Segment
        .equ    SWI_SETLED, 		0x201	@LEDs on/off
        .equ    SWI_CheckBlack, 	0x202	@check press Black button
        .equ    SWI_CheckBlue, 		0x203	@check press Blue button
        .equ    SWI_DRAW_STRING, 	0x204	@display a string on LCD
        .equ    SWI_DRAW_INT, 		0x205	@display an int on LCD  
        .equ    SWI_CLEAR_DISPLAY, 	0x206	@clear LCD
        .equ    SWI_DRAW_CHAR, 		0x207	@display a char on LCD
        .equ    SWI_CLEAR_LINE, 	0x208	@clear a line on LCD
        .equ 	SEG_A,	0x80		@ patterns for 8 segment display
		.equ 	SEG_B,	0x40
		.equ 	SEG_C,	0x20
		.equ 	SEG_D,	0x08
		.equ 	SEG_E,	0x04
		.equ 	SEG_F,	0x02
		.equ 	SEG_G,	0x01
		.equ 	SEG_P,	0x10                
        .equ    LEFT_LED, 	0x02	@patterns for LED lights
        .equ    RIGHT_LED, 	0x01
        .equ    BOTH_LED, 	0x03
        .equ    NO_LED, 	0x00       
        .equ    LEFT_BLACK_BUTTON, 	0x02	@ bit patterns for black buttons
        .equ    RIGHT_BLACK_BUTTON, 0x01
        @ bit patterns for blue keys
        .equ    C1, 		1<<0	@ =1
        .equ    C2, 		1<<1	@ =2
        .equ    C3, 		1<<2	@ =4
        .equ    C4, 		1<<3	@ =8
        .equ    F1UP, 		1<<4	@ =16
        .equ    F2UP, 		1<<5	@ =32
        .equ    F3UP, 		1<<6	@ =64
        .equ    F2DW, 		1<<9	@ = 512
        .equ    F3DW, 		1<<10	@ = 1024
        .equ    F4DW, 		1<<11	@ = 2056
		@ timing related
		.equ    SWI_GetTicks, 		0x6d	@get current time 
		.equ    EmbestTimerMask, 	0x7fff	@ 15 bit mask for Embest timer
											@(2^15) -1 = 32,767        										
        .equ	OneSecond,	1000	@ Time intervals
        .equ	HalfSecond,	500
        .equ	WaitFloors,	5		@ seconds moving between floors
        .equ	WaitDoors,	5		@ seconds opening doors	
		@ Values used for initialization
		.equ	NumTextLines,	5
		.equ	EndSimulation,	-2
		.equ	EmergencyStop,	-1
		.equ	RequestsDone,	0
		.equ	TopFloor,		4
		.equ	BottomFloor,	1
 
       .text           
       .global _start

@===== The entry point of the program
_start:		@go to initial idle state at floor 1
	ldr	r3,=FloorNum	@draw initial screen
	bl	Initdraw		@Initdraw(R3:&floor)
	ldr	r4,=SimulTime
	mov	r0,#0			@no requests
MainControl:			@ go to Idling until some event
	ldr	r7,[r3]			@r7 = current floor
	bl	Idling			@R0<-Idling(R3:&floor;R4:&simulation time)
	
	@ when back here, some event happened: check which one
	@ It cannot be emergency, that is taken care of directly

	cmp	r0,#EndSimulation		@was it end of program?
	beq	NormalExit
	@ Else it must be Blue button event: 
	@		if event is > floor, go to MoveUp (in main)	
	@		if event < floor, go to MoveDown (in main)
	@		ignore if event is at current floor	
SwitchOnBlue:			@ check which blue button
	nop		@ CODE TO BE DONE 
	@@@@ Example of Switch control:
	@@@@	cmp		r0,#C1	 go to a label in main then action	
	@@@@	beq		CF1UP
	@@@@	cmp		r0,#C2			
	@@@@	beq		CF2UPDW
	@@@@	etc etc
	
	bal		MainControl		@if any other button, ignore

@ CF2UPDW:
@@@		do action for going up or down to floor 2
@@@		first test should be to simply go up 1 floor from floor 1
@@@			as stated in Stage 1
NormalExit:
	bl	ExitClear			@clear all, come back and exit	
	bal	EndElevator
EndElevator:
	swi	SWI_EXIT			@ program main exit
	
@ === Idling (R3:&floor;R4:&simulation time)-->R0
@   Inputs:	R3 = & floor; R4 = & simulation time	
@   Output:  R0 = #EndSimulation, 
@				 = #EmergencyStop
@				 = number >0 for blue button pattern
@   Description:
@ 		Poll buttons continuosly
@		Every 1 second, update simulation time on screen
Idling:
	stmfd	sp!,{r1-r10,lr}
	mov		r5,#0			@ Display Idle State on all outputs
	BL		UpdateDirectionScreen	@UpdateDirectionScreen(R5:direction)
	mov		r5,#1			@ point (stopped)
	BL		UpdateFloor		@UpdateFloor(R3:&floor;R5:stopped)
	mov		r0, #NO_LED		@LEDs off
	swi		SWI_SETLED
	ldr     r7, =EmbestTimerMask	@mask for 15 bit timer
	ldr		r10,=OneSecond			@interval to update time
PollMainEv:		@ wait and poll protocol
				@@@ Hint: use this code as template for
				@@@ WaitAndPoll function?
	swi     SWI_GetTicks			@get time T0
	and		r0,r0,r7				@T0 in 15 bits
	ldr     r1, =Time0
	str     r0, [r1]				@save T1 in Time0
RepeatTillTimeEv:
	swi     SWI_CheckBlack
	cmp     r0, #LEFT_BLACK_BUTTON	@ end of simulation
	beq     ShutEv
	cmp     r0, #RIGHT_BLACK_BUTTON	@ emergency manual shutdown
	beq     EmButtonEv
	swi		SWI_CheckBlue			@ car or floor button
	cmp		r0,#0				
	bne		BlueButtonEv			@ pressed blue buttons
	@ else here no events detected, keep checking time passing
	swi     SWI_GetTicks			@get time T1
	and		r0,r0,r7				@T1 in 15 bits
	mov		r2,r0					@r2 is T1
	ldr     r3, =Time0
	ldr     r1, [r3]				@ r1 is T0
	cmp		r2,r1					@ is T1>T0?
	bge		SimpletimeEv
	sub		r9,r7,r1				@ elapsed TIME= 32,676 - T0
	add		r9,r9,r2				@    + T1
	bal		CheckIntervalEv
SimpletimeEv:
	sub		r9,r2,r1				@ elapsed TIME = T1-T0
CheckIntervalEv:
	cmp		r9,r10					@is TIME < update period?
	blt		RepeatTillTimeEv
	@ enough time passed without events, need to update outputs
	str     r0, [r3]        		@ update Time0	
	BL		UpdateTime				@ UpdateTime(R4: & simul time)
	bal		PollMainEv				@ then keep polling till event
BlueButtonEv:	@ store event type in global array 
	BL	SetButtonsArray				@SetButtonsArray(r0:blue button)
	bal	DoneIdling
EmButtonEv:
	mov	r0,#EmergencyStop			@ get out
	bal	DoneIdling
ShutEv:
	mov	r0,#EndSimulation			@ending simulation
DoneIdling:
	LDMFD	sp!,{r1-r10,pc}
		
@ === Void SetButtonsArray(r0:blue button)
@   Inputs:	r0=blue button request	
@   Results:  none
@   Description:
@ 		Set the appropriate entry in the global array of requests
@		if it is a valid button
SetButtonsArray:
	STMFD	sp!,{r0-r6,lr}
	ldr	r3,=ButtonsArray	@address of global array
	mov	r2,#1			@flag
	ldr	r4,=0x0000FFFF		@mask to clear upper register
	and	r0,r0,r4		@clear upper 16 bits
	mov	r5,#1			@find position of blue button
	mov	r6,#1			@to translate to index in array
LP1:
	cmp	r5,r0			@is this position?
	beq	Index			@position found
	mov	r5,r5,lsl #1		@else try next position
	add	r6,r6,#1
	bal	LP1
Index:	
	sub	r0,r6,#1		@index=button-1
	cmp	r0,#12			@button 12,13,14,15 invalid
	bge	SBAend
	cmp	r0,#7			@button 7,8 invalid
	beq	SBAend
	cmp	r0,#8	
	beq	SBAend
	str	r2,[r3,r0,LSL #2]	@array[index*4]	
SBAend:
	swi	SWI_CheckBlue		@clear for buttons bounce?
	LDMFD	sp!,{r0-r6,pc}

@ === Void ClearButtonsArray(r3: &floor)
@   Inputs:	r3 = & floor	
@   Results:  none
@   Description:
@ 		Clear the entries in the global array for a given floor
ClearButtonsArray:
	STMFD	sp!,{r1-r4,lr}
	ldr	r1,=ButtonsArray	@address of global array
	mov	r2,#0				@flag
	ldr	r3,[r3]				@floor
	sub	r3,r3,#1			@index=floor-1
	mov	r4,#3				@loop counter for 3 arrays
RB:	str	r2,[r1,r3,LSL #2]	@array[index*4]
	add	r3,r3,#4			@next array
	subs	r4,r4,#1
	bne	RB	
	LDMFD	sp!,{r1-r4,pc}
	
@@@@@@@ TO BE DONE - MUST DO  @@@@@@@@@@@@@	
@ === WaitAndPoll (R4: & simul time;R5:waiting time)
@   Inputs:	R4 = & simulation time
@			R5 = time in seconds to wait while polling	
@   Results:  R0 = #EndSimulation, 
@				 = #EmergencyStop
@				 = 0 otherwise
@	Side effects: if events captured, global arrays updated
@   Description:
@ 		Poll buttons while waiting for a length of time   
@		in seconds, and every 1 second update simulation
@		time on screen. If polling captures event, arrays
@		for signals are updated
@ 	Used when elevator is moving between floors 
@	or when doors are opening 
@	Similar code is embedded directly in Idling
WaitAndPoll:
	stmfd	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndWaitAndPoll:
	LDMFD	sp!,{r1-r10,pc}

@@@@@@@ Strongly suggested  @@@@@@@@@@@@@		
@ ===CheckSignalsHigher(r3: &floor)
@   Output:	r3 = & floor	
@   Results:  R0 = 0 (no to higher signal); 1 (yes to higher signal)
@   Description:
@ 		Check global arrays for any signals at floor>given floor
CheckSignalsHigher:
	STMFD	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndCheckSignalsHigher:
	LDMFD	sp!,{r1-r10,pc}

@@@@@@@ Strongly suggested  @@@@@@@@@@@@@		
@ ===CheckSignalsLower(r3: &floor)
@   Inputs:	r3 = & floor	
@   Output:  R0 = 0 (no to lower signal); 1 (yes to lower signal)
@   Description:
@ 		Check global arrays for any signals at floor<given floor
CheckSignalsLower:
	STMFD	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndCheckSignalsLower:
	LDMFD	sp!,{r1-r10,pc}

@@@@@@@ Strongly suggested  @@@@@@@@@@@@@	
@ ===CheckFloorDoors(r3: &floor)
@   Inputs:	r3 = & floor	
@   Output:  none
@   Description:
@ 		Check global arrays for any signals at current floor
CheckFloorDoors:
	STMFD	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndCheckFloorDoors:
	LDMFD	sp!,{r1-r10,pc}

@@@@@@@ Strongly suggested  @@@@@@@@@@@@@	
@ ===MovingUp (R3: &floor;R4: &simul time)
@   Inputs:	R3 = & floor; R4 = & simulation time	
@   Output:  R0 = #EndSimulation, 
@				 = #EmergencyStop
@				 = 0 otherwise
@	Side effects: if events captured, global arrays updated
@   Description:
@ 		Move up each floor until all UP requests done
MovingUp:
	STMFD	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndMovingUp:
	LDMFD	sp!,{r1-r10,pc}

@@@@@@@ Strongly suggested  @@@@@@@@@@@@@		
@ === MovingDown(R3: &floor;R4: &simul time)
@   Inputs:	R3 = & floor; R4 = & simulation time	
@   Output:  R0 = #EndSimulation, 
@				 = #EmergencyStop
@				 = 0 otherwise
@	Side effects: if events captured, global arrays updated
@   Description:
@ 		Move down each floor until all DOWN requests done
MovingDown:
	STMFD	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndMovingDown:
	LDMFD	sp!,{r1-r10,pc}

@@@@@@@ Strongly suggested @@@@@@@@@@@@@	
@ === OpenDoors (R3: &floor;R4: &simul time)
@   Inputs:	R3 = & floor; R4 = & simulation time	
@   Output:  R0 = #EndSimulation, 
@				 = #EmergencyStop
@				 = 0 otherwise
@	Side effects: clears arrays at floor number
@   Description:
@ 		Set the outputs for doors open,
@		stay for X seconds while polling
OpenDoors:
	STMFD	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndOpenDoors:
	LDMFD	sp!,{r1-r10,pc}
	
@===== UpdateTime(R4:& simulated time)
@   Inputs:  R4 = & simulated time
@   Output: none
@   Description:
@      Displays the updated value of the current simulation time
@		on screen and updates its variable
UpdateTime:
	stmfd	sp!, {r0-r4,lr}
	ldr		r2,[r4]			@update simulated time
	add		r2,r2,#1	 
	str		r2,[r4]
	mov		r1, #2			@ r1 = next line number to display
	mov		r0, #20			@ r0 = column number for displayed strings
	swi		SWI_DRAW_INT
EndUpdateTime:
	ldmfd	sp!, {r0-r4,pc}

@===== UpdateFloor(R3:&floor;R5:stopped)
@   Inputs:  R3 = & floor number;R5 = 1(idle) or 0(moving)
@   Output: none
@   Description:
@      Displays the value of the current floor on the LCD screen
@		and in the 8-segment
UpdateFloor:
	stmfd	sp!, {r0-r2,lr}
	ldr		r0,[r3]				@r0=floor number
	mov		r1,r5				@ point?
	BL		Display8Segment
	mov		r2,r0				@r2=floor number
	mov		r0,#20				@column on screen
	mov		r1,#4				@row on screen
	swi		SWI_DRAW_INT
EndUpdateFloor:	
	ldmfd	sp!, {r0-r2,pc}
	
@===== UpdateDirectionScreen(R5:direction)
@   Inputs:  R5 = current direction of elevator movement
@   Output: none
@   Description:
@      Displays the pattern for the elevator direction on 
@		the appropriate 5 lines on the LCD screen
@		Direction: -1 = down; 0 = stopped; 1 = up ; 2 open doors
UpdateDirectionScreen:
	stmfd	sp!, {r0-r5,lr}
	mov		r4,#0			@line counter
	cmp		r5,#0			@establish direction
	blt		DirDown
	beq		DirIdle
	cmp		r5,#1
	beq		DirUp
DirOp:		ldr	r2,=lineD1op
	bal		DrawDir
DirDown:	ldr	r2,=lineD1dw
	bal		DrawDir
DirIdle:	ldr	r2,=lineD1st
	bal		DrawDir
DirUp:		ldr	r2,=lineD1up
DrawDir:
	mov		r1, #6			@ r1 = row
	mov		r0, #0			@ r0 = column
Drl:	
	swi		SWI_DRAW_STRING
	add		r1, r1, #1		@next line number
	add		r2,r2,#8		@next string
	add		r4,r4,#1		@update line counter
	cmp		r4, #NumTextLines
	blt		Drl
EndUpdateDirectionScreen:
	ldmfd	sp!, {r0-r5,pc}
	
@ =====Initdraw(R3:& floor)
@   Inputs:  R3 = & floor
@   Output: none
@   Description:
@      	Draws the initial state of LCD screen, LEDs
@		and 8-segment
Initdraw:
	stmfd	sp!, {r0-r5,lr}
	mov		r1, #0			@ r1 = row
	mov		r0, #0			@ r0 = column 
	ldr		r2, =lineID		@ identification
	swi		SWI_DRAW_STRING
	mov		r1, #2			@ r1 = row
	mov		r0, #10			@ r0 = column 
	ldr		r2, =lineTime	@ time XXX
	swi		SWI_DRAW_STRING
	mov		r1, #4			@ r1 = row
	mov		r0, #10			@ r0 = column
	ldr		r2, =lineFloor	@ floor number XX
	swi		SWI_DRAW_STRING	
	mov		r1, #11			@ r1 = row
	mov		r0, #1			@ r0 = column
	ldr		r2, =keysline1	@ floor number XX
	swi		SWI_DRAW_STRING
	mov		r1, #12			@ r1 =row
	mov		r0, #1			@ r0 = column
	ldr		r2, =keysline2	@ floor number XX
	swi		SWI_DRAW_STRING
	mov		r1, #13			@ r1 = row
	mov		r0, #1			@ r0 = column
	ldr		r2, =keysline3	@ floor number XX
	swi		SWI_DRAW_STRING
	mov		r5,#0			@ Draw the Idle State strings
	BL		UpdateDirectionScreen	@UpdateDirectionScreen(R5:direction)
	mov		r5,#1			@ point (stopped)
	BL		UpdateFloor		@UpdateFloor(R3:&floor;R5:stopped)
	mov		r0, #NO_LED		@LEDs off
	swi		SWI_SETLED
	mov		r0,#1			@floor 1			
	mov		r1,#1			@stopped
	BL		Display8Segment	@Display8Segment(R0:number;R1:point)
EndInitdraw:
	ldmfd	sp!, {r0-r5,pc}

@@@@@@@ TO BE DONE - MUST DO  @@@@@@@@@@@@@	
@=====EmergencyState()
@   Inputs:  none
@   Output:  none
@   Description:
@     	The function sets the  configuration for
@		the output devices in case of emergency.
@	NOTE: this function does not return to Main Control;
@		execution stays here in an infinite loop after
@		updating outputs, until program is ended manually
EmergencyState:
	STMFD	sp!,{r1-r10,lr}
	NOP		@TO BE DONE
EndEmergencyState:
	LDMFD	sp!,{r1-r10,pc}
	
@===== ExitClear()
@   Inputs:  none
@   Output: none
@   Description:
@      Clear the board and display the last message
ExitClear:	
	stmfd	sp!, {r0-r2,lr}
	mov		r0, #10				@8-segment pattern off
	mov		r1,#0
	BL		Display8Segment		@Display8Segment(R0:number;R1:point)
	mov		r0, #NO_LED
	swi		SWI_SETLED
	swi		SWI_CLEAR_DISPLAY
	mov		r0, #5
	mov		r1, #7
	ldr		r2, =Goodbye
	swi		SWI_DRAW_STRING  	@ display goodbye message on line 7
EndExitClear:
	ldmfd	sp!, {r0-r2,pc}

@ ==== void Wait(Delay:r10) 
@   Inputs:  R10 = delay in milliseconds
@   Output: none
@   Description:
@      Wait for r10 milliseconds using a 15-bit timer 
Wait:
	stmfd	sp!, {r0-r2,r7-r10,lr}
	ldr     r7, =EmbestTimerMask
	swi     SWI_GetTicks		@get time T1
	and		r1,r0,r7			@T1 in 15 bits
WaitLoop:
	swi 	SWI_GetTicks		@get time T2
	and		r2,r0,r7			@T2 in 15 bits
	cmp		r2,r1				@ is T2>T1?
	bge		simpletimeW
	sub		r9,r7,r1			@ elapsed TIME= 32,676 - T1
	add		r9,r9,r2			@    + T2
	bal		CheckIntervalW
simpletimeW:
	sub		r9,r2,r1			@ elapsed TIME = T2-T1
CheckIntervalW:
	cmp		r9,r10				@is TIME < desired interval?
	blt		WaitLoop
EndWait:
	ldmfd	sp!, {r0-r2,r7-r10,pc}	

@ *** Display8Segment (Number:R0; Point:R1) ***
@   Inputs:  R0=bumber to display; R1=point or no point
@   Output:  none
@   Description:
@ 		Displays the number 0-9 in R0 on the 8-segment
@ 		If R1 = 1, the point is also shown
Display8Segment:
	STMFD 	sp!,{r0-r2,lr}
	ldr 	r2,=Digits
	ldr 	r0,[r2,r0,lsl#2]
	tst 	r1,#0x01 @if r1=1,
	orrne 	r0,r0,#SEG_P 			@then show P
	swi 	SWI_SETSEG8
EndDisplay8Segment:
	LDMFD 	sp!,{r0-r2,pc}
	
@@@@@@@@@@@@=========================
	.data
	.align
SimulTime:	.word   0
Time0:		.skip	4
FloorNum:	.word	1
ButtonsArray:
ButtonsCar:	.word	0,0,0,0		@ 4 words for 4 car buttons
FloorUp:	.word	0,0,0,0		@ floor 1,2,3 up,4 unused 
FloorDw:	.word	0,0,0,0		@ floor 2,3,4 down, 1 unused
Digits:							@ for 8-segment display
	.word SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_G 	@0
	.word SEG_B|SEG_C 							@1
	.word SEG_A|SEG_B|SEG_F|SEG_E|SEG_D 		@2
	.word SEG_A|SEG_B|SEG_F|SEG_C|SEG_D 		@3
	.word SEG_G|SEG_F|SEG_B|SEG_C 				@4
	.word SEG_A|SEG_G|SEG_F|SEG_C|SEG_D 		@5
	.word SEG_A|SEG_G|SEG_F|SEG_E|SEG_D|SEG_C 	@6
	.word SEG_A|SEG_B|SEG_C 					@7
	.word SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_F|SEG_G @8
	.word SEG_A|SEG_B|SEG_F|SEG_G|SEG_C 		@9
	.word 0 									@Blank 

lineID:		.asciz	"Elevator -- Captain Picard, V0012345"
lineTime:  	.asciz  "Time:            seconds"
lineFloor:	.asciz	"Floor =    "
keysline1:	.asciz	"Blue Keys:     C1,  C2,  C3,  C4"
keysline2:	.asciz	"              FU1   FU2  FU3  XX"
keysline3:	.asciz	"               XX   FD2  FD3  FD4"
	.align
lineD1up:	.asciz	"   @   "
lineD2up:	.asciz	"  @|@  "
lineD3up:	.asciz	" @ | @ "
lineD4up:	.asciz	"   |   "
lineD5up:	.asciz	"   |   "
lineD1st:	.asciz	"@@@@@@@"
lineD2st:	.asciz	"@@@@@@@"
lineD3st:	.asciz	"@@@@@@@"
lineD4st:	.asciz	"@@@@@@@"
lineD5st:	.asciz	"@@@@@@@"
lineD1dw:	.asciz	"   |   "
lineD2dw:	.asciz	"   |   "
lineD3dw:	.asciz	" @ | @ "
lineD4dw:	.asciz	"  @|@  "
lineD5dw:	.asciz	"   @   "
lineD1op:	.asciz	"@@@@@@@"
lineD2op:	.asciz	"@     @"
lineD3op:	.asciz	"@     @"
lineD4op:	.asciz	"@     @"
lineD5op:	.asciz	"@@@@@@@"


Goodbye:
	.asciz	"*** Elevator program ended ***"
EmergencyMessage:
	.asciz	"***ELEVATOR EMERGENCY - GO TO MANUAL***"

	.end

