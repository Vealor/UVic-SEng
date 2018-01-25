	.file	"Lab10_Main2.c"
	.section .rdata,"dr"
LC0:
	.ascii "%d\12\0"
	.text
	.globl	_Print
	.def	_Print;	.scl	2;	.type	32;	.endef
_Print:
LFB6:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	subl	$24, %esp
	movl	8(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$LC0, (%esp)
	call	_printf
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE6:
	.def	___main;	.scl	2;	.type	32;	.endef
	.globl	_main
	.def	_main;	.scl	2;	.type	32;	.endef
_main:
LFB7:
	.cfi_startproc
	pushl	%ebp
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp
	.cfi_def_cfa_register 5
	andl	$-16, %esp
	subl	$64, %esp
	call	___main
	movl	$0, 60(%esp)
	jmp	L3
L4:
	movl	60(%esp), %eax
	movl	60(%esp), %edx
	movl	%edx, 16(%esp,%eax,4)
	incl	60(%esp)
L3:
	cmpl	$9, 60(%esp)
	jle	L4
	movl	$-1, 56(%esp)
	leal	16(%esp), %eax
	movl	%eax, (%esp)
	call	_Compute
	movl	$0, %eax
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE7:
	.def	_printf;	.scl	2;	.type	32;	.endef
	.def	_Compute;	.scl	2;	.type	32;	.endef
