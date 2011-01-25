#if defined(__PCH__)
node_t * create_node(long size,node_t *ptr ) // create node at given location
{
   node_t *result;
   result =ptr;
   result->size=size;
   result->next = NULL;
   return result;
}
void update_node(node_t *node,long size) // update the size of given node
{
   node->size=size;
}
#define csize 32768
#define pos 15
#else
node_t * create_node(int size,node_t *ptr ) // create node at given location
{
   node_t *result;
   result =ptr;
   result->size=size;
   result->next = NULL;
   return result;
}
void update_node(node_t *node,int size)// update the size of given node
{
   node->size=size;
}
#define csize 128
#define pos 7

#endif

/* Insert node immediately after place */
void insert_node_after(node_t *place,node_t *node)// place the node after another given node
{
    if (place->next==NULL)
       node->next= NULL;
    else
       node->next=place->next;
    place->next=node;
}

void remove_node(node_t *node) {// remove the given node from the memlist
   node_t *ptr;
   for(ptr=__DYNAMIC_HEAD;ptr->next!=node;ptr++);
   ptr->next=node->next;
   node=NULL;
}


