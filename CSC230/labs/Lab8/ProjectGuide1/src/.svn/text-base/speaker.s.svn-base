	
	.include	"ports.a"
	.include	"speaker.a"

	.text
	.global		speaker_init
	.global		speaker_beep

speaker_init:
	mov	pc,lr

speaker_beep:
	stmfd	sp!,{r1-r3,lr}

	ldr		r3,=0x01f7
	ldr		r1,=rPDATE
	ldr		r2,[r1]
	cmp		r2,#0
	orreq	r2,r2,#0x0008
	andne	r2,r2,r3
	str		r2,[r1]

	ldmfd	sp!,{r1-r3,pc}
	
	.end
	
