#include <stdio.h>
#include <unistd.h>

#define OUTPUT printf("%d\n", i)

main(){
	int i=0; OUTPUT;
	
	if(fork()){
		wait();
		i+=2; OUTPUT;
	} else {
		i++; OUTPUT; return(0);
	}
}
