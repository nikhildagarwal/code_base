#include <stdio.h>
#include <stdlib.h>

const int INIT_SIZE = 0;
const int EMPTY_QUEUE_ERROR = 7;

struct Node{
    int val;
    struct Node* next;
};

typedef struct{
    int size;
    struct Node* front;
    struct Node* end;
} Queue;

void initQueue(Queue* q){
    q->size = INIT_SIZE;
    q->front = NULL;
    q->end = NULL;
}

void push(Queue* q, int value){
    if(q->size == 0){
        q->front = (struct Node*)malloc(sizeof(struct Node));
        q->front->val = value;
        q->end = q->front;
        q->end->next = NULL;
    }else{
        struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
        newNode->val = value;
        newNode->next = NULL;
        q->end->next = newNode;
        q->end = q->end->next;
    }
    q->size++;
}

void printQueue(Queue* q){
    printf("out [");
    struct Node* start = q->front;
    while(start!=NULL){
        printf("%d",start->val);
        if(start->next != NULL){
            printf(", ");
        }
        start = start->next;
    }
    printf("] in\n");
}

void endQueue(Queue* q){
    struct Node* start = q->front;
    while(start!=NULL){
        struct Node* toFree = start;
        start = start->next;
        free(toFree);
    }
}

int isEmpty(Queue* q){
    if(q->size == 0){
        return 1;
    }
    return 0;
}

int size(Queue* q){
    return q->size;
}

int peek(Queue* q){
    if(q->size == 0){
        printf("Empty Queue Error");
        exit(EMPTY_QUEUE_ERROR);
    }
    return q->front->val;
}

int poll(Queue* q){
    if(q->size == 0){
        printf("Empty Queue Error");
        exit(EMPTY_QUEUE_ERROR);
    }
    q->size--;
    int value = q->front->val;
    struct Node* toFree = q->front;
    q->front = q->front->next;
    if(q->front == NULL){
        q->end = NULL;
    }
    free(toFree);
    return value;
}

int contains(Queue* q, int value){
    struct Node* start = q->front;
    while(start!=NULL){
        if(start->val == value){
            return 1;
        }
        start = start->next;
    }
    return 0;
}