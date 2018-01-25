	.cpu arm7tdmi
	.fpu softvfp
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 1
	.eabi_attribute 30, 6
	.eabi_attribute 18, 4
	.file	"A4AppendKseqCsub.c"
	.text
	.align	2
	.global	AppendKseq
	.type	AppendKseq, %function
AppendKseq:
	@ Function supports interworking.
	@ args = 0, pretend = 0, frame = 24
	@ frame_needed = 1, uses_anonymous_args = 0
	@ link register save eliminated.
	str	fp, [sp, #-4]!
	add	fp, sp, #0
	sub	sp, sp, #28
	str	r0, [fp, #-16]
	str	r1, [fp, #-20]
	str	r2, [fp, #-24]
	mov	r3, #2
	str	r3, [fp, #-8]
	b	.L2
.L6:
	mov	r3, #1
	str	r3, [fp, #-12]
	b	.L3
.L5:
	ldr	r3, [fp, #-20]
	mov	r3, r3, asl #2
	ldr	r2, [fp, #-16]
	add	r1, r2, r3
	ldr	r2, [fp, #-8]
	mov	r3, r2, asr #31
	mov	r3, r3, lsr #31
	add	r2, r2, r3
	and	r2, r2, #1
	rsb	r3, r3, r2
	add	r3, r3, #1
	str	r3, [r1, #0]
	ldr	r3, [fp, #-20]
	mov	r3, r3, asl #2
	ldr	r2, [fp, #-16]
	add	r3, r2, r3
	ldr	r3, [r3, #0]
	cmp	r3, #1
	bne	.L4
	ldr	r3, [fp, #-24]
	ldr	r3, [r3, #0]
	add	r2, r3, #1
	ldr	r3, [fp, #-24]
	str	r2, [r3, #0]
.L4:
	ldr	r3, [fp, #-20]
	add	r3, r3, #1
	str	r3, [fp, #-20]
	ldr	r3, [fp, #-12]
	add	r3, r3, #1
	str	r3, [fp, #-12]
.L3:
	ldr	r3, [fp, #-8]
	mov	r3, r3, asl #2
	ldr	r2, [fp, #-16]
	add	r3, r2, r3
	ldr	r2, [r3, #0]
	ldr	r3, [fp, #-12]
	cmp	r2, r3
	bge	.L5
	ldr	r3, [fp, #-8]
	add	r3, r3, #1
	str	r3, [fp, #-8]
.L2:
	ldr	r3, [fp, #-8]
	cmp	r3, #50
	ble	.L6
	ldr	r3, [fp, #-20]
	sub	r3, r3, #1
	mov	r0, r3
	add	sp, fp, #0
	ldmfd	sp!, {fp}
	bx	lr
	.size	AppendKseq, .-AppendKseq
	.ident	"GCC: (Sourcery G++ Lite 2011.03-42) 4.5.2"
