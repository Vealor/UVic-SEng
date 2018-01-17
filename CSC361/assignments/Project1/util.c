/*------------------------------
* util.c
* Description: util program
* CSC 361
* Instructor: Kui Wu
-------------------------------*/


/* ------------
* util.c: used by client.c and server.c 
* ---------------*/

/* write "size" bytes of "ptr" to "sd" */

writen(int sd, char *ptr, int size)
{
    int no_left, no_written;

    no_left = size; 
    while (no_left > 0)
    {
       no_written = write(sd, ptr, no_left);
       if (no_written <=0)
            return(no_written);
       no_left -= no_written;
       ptr += no_written;
    }
    return(size - no_left);
}

/* read "size bytes from "sd" to "ptr" */

readn(int sd, char *ptr, int size)
{
   int no_left, no_read;
   no_left = size;
   while (no_left >0)
   {
      no_read = read(sd, ptr, no_left);
      if (no_read <0)
         return(no_read);
      if (no_read ==0)
         break;
      no_left -= no_read;
      ptr += no_read;
    }
   return(size - no_left);
}

