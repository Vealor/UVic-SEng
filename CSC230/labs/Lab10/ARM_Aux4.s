@ ====================================================================
@ CSc 230 -  Lab #10
@ Auxiliary Functions
@ ====================================================================

@ ====================================================================
@ Exported Symbols
@ ====================================================================
	.global	Compute

	@ Why not include Mystery here?

@ ====================================================================
@ Imported Symbols
@ ====================================================================
	.extern	Print

	@ Why not include _start here?

@ ====================================================================
	.text
@ ====================================================================

@ ====================================================================
@ Compute(R0:pointer-to-inputs)
@	R0:inputs - expect a sequence of values terminated by -1
@ ====================================================================
Compute:
	stmfd	sp!, {r0-r2,lr} @ YES, we do need this!

	mov	r1, r0       @ R1:inputs = R0:arg1
	mov	r2, #-1
NextInput:
	ldr	r0, [r1], #4 @ R0:input = *(R1:inputs++)
	cmp	r0, r2
	beq	EndCompute   @ R0:input == -1 ?

	stmfd	sp!, {r1-r3} @ PUSH {r1,r3} (caller-saves)

	bl	Mystery      @ R0:tmp = DumbMystery(R0:input)

	ldmfd	sp!, {r1-r3} @ POP  {r1,r3} (caller-saves)

	bl	Print        @ Print(R0:tmp)
	bal	NextInput    @ process another input...

EndCompute:
	ldmfd	sp!, {r0-r2,pc} @ YES, we do need this!

Mystery:
	@ Function supports interworking.
	@ args = 0, pretend = 0, frame = 24
	@ frame_needed = 1, uses_anonymous_args = 0
	mov	ip, sp	@,
	stmfd	sp!, {fp, ip, lr, pc}	@,
	sub	fp, ip, #4	@,,
	sub	sp, sp, #24	@,,
	str	r0, [fp, #-32]	@ n, n
	ldr	r3, [fp, #-32]	@ n, n
	cmp	r3, #1	@ n,
	bgt	.L2	@,
	mov	r3, #1	@,
	str	r3, [fp, #-36]	@, D.1297
	b	.L4	@
.L2:
	mov	r3, #1	@ tmp104,
	str	r3, [fp, #-24]	@ tmp104, v2
	ldr	r3, [fp, #-24]	@ v2, v2
	str	r3, [fp, #-28]	@ v2, v1
	ldr	r2, [fp, #-28]	@ v1, v1
	ldr	r3, [fp, #-24]	@ v2, v2
	add	r3, r2, r3	@ tmp108, v1, v2
	str	r3, [fp, #-20]	@ tmp108, v3
	ldr	r3, [fp, #-32]	@ n, n
	str	r3, [fp, #-16]	@ n, k
	b	.L5	@
.L6:
	ldr	r3, [fp, #-24]	@ v2, v2
	str	r3, [fp, #-28]	@ v2, v1
	ldr	r3, [fp, #-20]	@ v3, v3
	str	r3, [fp, #-24]	@ v3, v2
	ldr	r2, [fp, #-28]	@ v1, v1
	ldr	r3, [fp, #-24]	@ v2, v2
	add	r3, r2, r3	@ tmp114, v1, v2
	str	r3, [fp, #-20]	@ tmp114, v3
	ldr	r3, [fp, #-16]	@ k, k
	sub	r3, r3, #1	@ tmp116, k,
	str	r3, [fp, #-16]	@ tmp116, k
.L5:
	ldr	r3, [fp, #-16]	@ k, k
	cmp	r3, #2	@ k,
	bgt	.L6	@,
	ldr	r3, [fp, #-20]	@, v3
	str	r3, [fp, #-36]	@, D.1297
.L4:
	ldr	r3, [fp, #-36]	@ <result>, D.1297
	mov	r0, r3	@ <result>, <result>
	sub	sp, fp, #12
	ldmfd	sp, {fp, sp, lr}
	bx	lr
	.size	Mystery, .-Mystery
	.ident	"GCC: (GNU) 4.1.0 (CodeSourcery ARM)"


@ ====================================================================
	.end
