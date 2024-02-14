#include <stdio.h>
#include <string.h>


int main()
{
 while(1)
 {
     char* input = (char*)malloc(100);
      if (input == NULL) {
            fprintf(stderr, "Memory allocation failed.\n");
            exit(-1);
      }
    fscanf(stdin,"%s",input);
    fprintf(stdout,"%s\n",input);
    if(strcmp(input,"exit")==0)
      break;
    free(input);
 }
    return 0;
}