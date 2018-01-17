#include <stdio.h>
#include <pcap.h>
#include <stdlib.h>

int main(int argc, char **argv)
{
  unsigned int packet_counter=0;
  struct pcap_pkthdr header;
  const u_char *packet;

  if (argc < 2) {
    fprintf(stderr, "Usage: %s <pcap>\n", argv[0]);
    exit(1);
  }

   pcap_t *handle;
   char errbuf[PCAP_ERRBUF_SIZE];
   handle = pcap_open_offline(argv[1], errbuf);

   if (handle == NULL) {
     fprintf(stderr,"Couldn't open pcap file %s: %s\n", argv[1], errbuf);
     return(2);
   }

   while (packet = pcap_next(handle,&header)) {

      packet_counter++;

    }
    pcap_close(handle);


  printf("%d\n", packet_counter);
  return 0;
}
