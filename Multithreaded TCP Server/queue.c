#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

typedef struct node node;
typedef struct queue queue;


struct node {
    int data;
    node* next;
};

struct queue {
    node* head;
    node* tail;
};

queue* push(queue* q, int data) {
    node* temp = malloc(sizeof(node));
    if (temp == NULL) {
        return;
	}
    temp->data = data;
    temp->next = NULL;
    
    if (q->tail != NULL) {
        q->tail->next = temp;
	}
    q->tail = temp;
    
    if (q->head == NULL) {
        q->head = temp;
	}
	return q;
}

int pop(queue* q) {
	node* temp = malloc(sizeof(node));
	
	
    if (q->head == NULL) {
		//printf("Cannot pop, Queue is empty\n");
		return -1;
	}
	
	temp->data = q->head->data;
	
	
    q->head = q->head->next;
    return temp->data;
}

//~ int head(queue* q) {
	//~ if (q->head != NULL) {
		//~ printf("HEAD : %s\n", q->head->data);
	//~ }
	//~ else {
		//~ printf("Cannot print head, queue is empty\n");
	//~ }
	//~ return q->head->data;
//~ }

void init(queue* q) {
	q->head = NULL;
	q->tail = NULL;
}

//Used for testing
//~ void main(int argc, char** argv) {
	//~ queue* q = malloc(sizeof(queue));
    //~ init(q);
    //~ 
    //~ char* test = "test";
    //~ char* test2 = "test2";
    //~ 
    //~ push(q, test);
    //~ printf("Head data : %s\n", q->head->data);
    //~ push(q, test2);
    //~ printf("Head data : %s\n", q->head->next->data);
    //~ pop(q);
    //~ printf("Head data : %s\n", q->head->data);
 //~ 
    //~ pop(q);
    //~ printf("Head data : %s\n", q->head->data);
    //~ 
    //~ 
//~ }
