/*********************************************************************/
#ifndef _STDLIBM
#define _STDLIBM
/* Memory Management Functions*/
#include <stddef.h>
#if defined(__PCB__)
typedef struct nodet {
   int size;
   int next; }node_t;
#elif defined(__PCM__)
typedef struct nodet {
   int size;
   long next; }node_t;
#elif defined(__PCH__)
typedef struct nodet {
   long size;
   long next; }node_t;
#endif
#include <memmgmt.c>

 void traverse()
 {
 node_t *node,*temp;
 int nsize,nextsize;
 node=__DYNAMIC_HEAD;
 while(node!=NULL)
 {
     if(!bit_test(node->size,pos))// node free
      {
         nsize=node->size;
         temp=(long)node->next;
         if(!bit_test(temp->size,pos)&& (temp==((long)node+nsize+sizeof(node_t))))//next node free and consecutive, so combine
         {
            nextsize=temp->size;
            nsize+=nextsize+sizeof(node_t);
            remove_node(temp);
            update_node(node,nsize);
         }
         else
         node=node->next;
      }
      else
      node=node->next;
}
}

char * malloc(size_t size)
{
   node_t *node,*new;
   int nsize;
   node=__DYNAMIC_HEAD;
   while(node!=NULL) // chk until end of memlist
   {
      if(!bit_test(node->size,pos) && node->size >=size) // node is free and > = req size
      {
         nsize=node->size;
         update_node(node,size+csize);
         if(nsize>size) //node > req size, so split and add new node to memlist
         {
            new=create_node(nsize-size-sizeof(node_t),(long)node+sizeof(node_t)+size);
            insert_node_after(node,new);
         }//end if
         break;
      }//end if
      node=node->next;
   }//end while
   if(node==NULL)// reached end without finding an appropriate node
   {
      printf("\r\n Not enough memory for allocation");
      return NULL;
   }
   else
   return (char *)node+sizeof(node_t); // return pointer to allocated space
}

char *calloc(size_t nmemb,size_t size)
{
   node_t *node,*new;
   int nsize,resize;
   node=__DYNAMIC_HEAD;
   resize=nmemb*size;
   while(node!=NULL) // chk until end of memlist
   {
      if(!bit_test(node->size,pos) && node->size >=resize)// node is free and > = req size
      {
         nsize=node->size;
         update_node(node,resize+csize);
         if(nsize>resize)//node > req size, so split and add new node to memlist
         {
            new=create_node(nsize-resize-sizeof(node_t),(long)node+sizeof(node_t)+resize);
            insert_node_after(node,new);
         }//end if
         break;
      }//end if
      node=node->next;
   }//end while
   if(node==NULL)// reached end without finding an appropriate node
   {
      printf("\r\n Not enough memory for allocation");
      return NULL;
   }
   else
   {
      memset((long)node+sizeof(node_t),0,resize);// initialize to 0
      return (char *)(long)node+sizeof(node_t);// return pointer to allocated space
   }
}
void free( void * ptr)
{
   node_t *node,*temp;
   int nsize,nextsize;

   if(ptr==NULL) // not a valid pointer
      return;
   else
   {
      node=ptr-sizeof(node_t);
      if(bit_test(node->size,pos))// node occupied
      {
         nsize=node->size-csize;
         update_node(node,nsize);
         ptr=NULL;

      }
      else // wrong input, return
      {
         ptr=NULL;
         return;
      }
   }
   traverse();
}

char *realloc(void *ptr,size_t size)
{
   node_t *node,*new,*temp;
   int nsize,nextsize;

   if(ptr==NULL)// null pointer, so malloc the req memory
      malloc(size);
   else if(size==0)
   {
   free(ptr);
   }
   else
   {
      node=ptr-sizeof(node_t);
      if(bit_test(node->size,pos))// chk if valid pointer
      {
         nsize=node->size-csize;
         temp=(long)node->next;
         if(nsize>size)// block > req size
         {
               update_node(node,size+csize); // update block
               if(!bit_test(temp->size,pos) && (temp==((long)node+nsize+sizeof(node_t))))// next block free and consecutive
               {
                  nextsize=temp->size;
                  new=create_node(nextsize+(nsize-size),(long)node+size+sizeof(node_t));
                  insert_node_after(node,new);
                  remove_node(temp);

               }
               else
               {
                  new=create_node(nsize-size-sizeof(node_t),(long)node+sizeof(node_t)+size);
                  insert_node_after(node,new);
               }

         }
         else // block < req size
         {
            if(!bit_test(temp->size,pos) && (temp==((long)node+nsize+sizeof(node_t))))// next block free and consecutive
            {
               nextsize=temp->size;
               if(nextsize>=size-nsize) // next block >=difference
               {
                  update_node(node,size+csize);// update block
                  if(nextsize>size-nsize)
                  {
                      remove_node(temp);
                      new=create_node(nextsize-(size-nsize),(long)node+size+sizeof(node_t));
                      insert_node_after(node,new);
                  }
               }
            }
         }
         return (char *)node+sizeof(node_t); // return pointer to the reallocated block
      }
      else // invalid input return
      {
         ptr=NULL;
         return;
      }
   }
 }
#ENDIF


