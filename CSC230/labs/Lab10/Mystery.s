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
	.file	"Mystery.c"
	.text
	.align	2
	.global	Mystery
	.type	Mystery, %function
Mystery:
	@ Function supports interworking.
	@ args = 0, pretend = 0, frame = 24
	@ frame_needed = 1, uses_anonymous_args = 0
	@ link register save eliminated.
	str	fp, [sp, #-4]!
	add	fp, sp, #0
	sub	sp, sp, #28
	str	r0, [fp, #-24]
	ldr	r3, [fp, #-24]
	cmp	r3, #1
	bgt	.L2
	mov	r3, #1
	b	.L3
.L2:
	mov	r3, #1
	str	r3, [fp, #-8]
	ldr	r3, [fp, #-8]
	str	r3, [fp, #-20]
	ldr	r2, [fp, #-20]
	ldr	r3, [fp, #-8]
	add	r3, r2, r3
	str	r3, [fp, #-12]
	ldr	r3, [fp, #-24]
	str	r3, [fp, #-16]
	b	.L4
.L5:
	ldr	r3, [fp, #-8]
	str	r3, [fp, #-20]
	ldr	r3, [fp, #-12]
	str	r3, [fp, #-8]
	ldr	r2, [fp, #-20]
	ldr	r3, [fp, #-8]
	add	r3, r2, r3
	str	r3, [fp, #-12]
	ldr	r3, [fp, #-16]
	sub	r3, r3, #1
	str	r3, [fp, #-16]
.L4:
	ldr	r3, [fp, #-16]
	cmp	r3, #2
	bgt	.L5
	ldr	r3, [fp, #-12]
.L3:
	mov	r0, r3
	add	sp, fp, #0
	ldmfd	sp!, {fp}
	bx	lr
	.size	Mystery, .-Mystery
	.ident	"GCC: (Sourcery G++ Lite 2011.03-42) 4.5.2"
