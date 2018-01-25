	.file	"Auxiliary2.c"
	.text
	.globl	_Mystery
	.def	_Mystery;	.scl	2;	.type	32;	.endef
_Mystery:
LFB0:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$16, %esp
	cmpl	$1, 8(%ebp)
	jg	L2
	movl	$1, %eax
	jmp	L3
L2:
	movl	$1, -4(%ebp)
	movl	-4(%ebp), %eax
	movl	%eax, -16(%ebp)
	movl	-4(%ebp), %eax
	movl	-16(%ebp), %edx
	addl	%edx, %eax
	movl	%eax, -8(%ebp)
	movl	8(%ebp), %eax
	movl	%eax, -12(%ebp)
	jmp	L4
L5:
	movl	-4(%ebp), %eax
	movl	%eax, -16(%ebp)
	movl	-8(%ebp), %eax
	movl	%eax, -4(%ebp)
	movl	-4(%ebp), %eax
	movl	-16(%ebp), %edx
	addl	%edx, %eax
	movl	%eax, -8(%ebp)
	decl	-12(%ebp)
L4:
	cmpl	$2, -12(%ebp)
	jg	L5
	movl	-8(%ebp), %eax
L3:
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE0:
	.globl	_Compute
	.def	_Compute;	.scl	2;	.type	32;	.endef
_Compute:
LFB1:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$40, %esp
	jmp	L7
L8:
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	_Mystery
	movl	%eax, (%esp)
	call	_Print
L7:
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	%eax, -12(%ebp)
	cmpl	$-1, -12(%ebp)
	setne	%al
	addl	$4, 8(%ebp)
	testb	%al, %al
	jne	L8
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE1:
	.def	_Print;	.scl	2;	.type	32;	.endef
