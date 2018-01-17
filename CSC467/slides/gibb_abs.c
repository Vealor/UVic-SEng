/* Gibbens and Hunt EBW Formula */

#include <stdio.h>
#include <math.h>
#include <stdlib.h>

main(int argc, char *argv[])
{
	double on, off, lamda, mu, zeta, buffer;
	double temp, temp1, temp2;
        double ebw;
        double MBS, scr, p, B, pcr, Ma;
 	double PCR, SCR, CLR,Sfty_margin, resolution;

	int buf1;
       
        
        if (argc != 6)
        {
           printf("Parameter Error !!!\n");
           printf("Usage: gibb SCR PCR CLR MBS BufferSize\n");
           exit(0);
        }

	scr = atof(argv[1]);
	pcr = atof(argv[2]);
	p = atof(argv[3]);
	MBS = atof(argv[4]);
        buf1 = atoi(argv[5]);

printf("%e \t %e\n %e \t %e\n",scr,pcr,p,MBS);
	printf("Buffer\tVBW \t\tGain\n"); 

           buffer = (double) buf1;
       	   PCR = pcr/53./8.;
   	   SCR = scr/53./8.;
   	   CLR = p;
   	   Sfty_margin = 2.; 
  	   resolution = 1e-15;

/*     	   Ma = 1. / (1. - pow (10., -1. * Sfty_margin / MBS)); */
	   Ma = MBS;
           temp=Ma;
           on = temp/pcr;
           off = (temp)*((1.0/scr)-(1.0/pcr));

	   mu = 1.0/on;
           lamda = 1.0/off;

	   zeta = (log(p))/buffer;
           temp = ((zeta*pcr)+mu+lamda);
	   temp1 = ((zeta*pcr)+mu-lamda);
	   temp2 = sqrt((temp1*temp1)+(4.0*lamda*mu));

    	   ebw = (temp-temp2)/(2.0*zeta);
     
           printf("%d\t",buf1);
	   printf("%e\t",ebw);
           printf("%f\n",pcr/ebw);

}
