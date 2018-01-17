	
	.include	"ports.a"
	.include	"leds.a"

	.text
	.global		leds_display
	.global		leds_init

@Init the LED's to a known state. In this case we will
@make sure they are both OFF.
leds_init:
	stmfd	sp!,{r0,lr}
	mov		r0,#0
	bl		leds_display
	ldmfd	sp!,{r0,pc}
	
@R0:leds to display
leds_display:
	stmfd	sp!,{r0-r3,lr}

	@get the current value from PORTB
	ldr		r1,=rPDATB
	ldr		r2,[r1]
	
	@clear the top 11 bits
	mov		r2,r2,lsl#21
	mov		r2,r2,lsr#21
	
	@set the appropriate bit based on the specified LED's ti turn on,
	@otherwise claer the bit
	mov		r3,#0x01
	tst		r0,#LEFT_LED
	bicne	r2,r2,r3,LSL#9
	orreq	r2,r2,r3,LSL#9

	@and same for the right LED
	tst		r0,#RIGHT_LED
	bicne	r2,r2,r3,LSL#10
	orreq	r2,r2,r3,LSL#10

	@write new port value to PORTB
	str		r2,[r1]
	
	ldmfd	sp!,{r0-r3,pc}

	.end
	
