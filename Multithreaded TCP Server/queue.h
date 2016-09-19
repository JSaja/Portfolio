#ifndef QUEUE_H
#define QUEUE_H

typedef struct node node;
typedef struct queue queue;


struct node {
    char* data;
    node* next;
};

struct queue {
    node* head;
    node* tail;
};

queue* push(queue* q, int data);
int pop(queue* q);
//int head(queue* q);
void init(queue* q);

#endif /* QUEUE_H */
