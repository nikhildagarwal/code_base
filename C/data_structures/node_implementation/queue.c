/**
 * Implementation of a Queue data structure in C.
 * Uses Node structures linked together into a singly linked list.
 * Queue follows First in First Out (FIFO) principle
 * Init as -->  Queue queue1;
 *              initQueue(&queue1);
 * Destroy -->  endQueue(&queue1);
 * Methods -->  void push(Queue* q, int value);
 *              void printQueue(Queue* q);
 *              int isEmpty(Queue* q);
 *              int size(Queue* q);
 *              int peek(Queue* q);
 *              int pop(Queue* q);
 *              int contains(Queue* q, int value);
 *              int count(Queue* q, int value);
 * @author Nikhil Daehee Agarwal
*/

#include <stdio.h>
#include <stdlib.h>

const int INIT_SIZE = 0;
const int EMPTY_QUEUE_ERROR = 7;

/**
 * structure definition for Node structure (Makes up the compoenets of the Linked List);
*/
struct Node{
    int val;
    struct Node* next;
};

/**
 * structure definition for Queue (holds size of linked List and pointers to the front and end of the list);
*/
typedef struct{
    int size;
    struct Node* front;
    struct Node* end;
} Queue;

/**
 * Initializes Queue structure
 * Sets queue size to 0 and pointers to NULL
*/
void initQueue(Queue* q){
    q->size = INIT_SIZE;
    q->front = NULL;
    q->end = NULL;
}

/**
 * Push an int into the Queue
 * Dynamically allocates memeory for new node structure and adds node structure to the end of our linked list.
*/
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

/**
 * Prints the queue as an observable in the terminal
*/
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

/**
 * Method to destroy Queue structure and free all dynamically memory
*/
void endQueue(Queue* q){
    struct Node* start = q->front;
    while(start!=NULL){
        struct Node* toFree = start;
        start = start->next;
        free(toFree);
    }
}

/**
 * Returns 1 if the Queue is empty (size is 0), returns 0 if the queue still points to some nodes
*/
int isEmpty(Queue* q){
    if(q->size == 0){
        return 1;
    }
    return 0;
}

/**
 * returns the size of the Queue (the number of elements it holds)
*/
int size(Queue* q){
    return q->size;
}

/**
 * returns the value at front of the Queue, or prints error message if queue is empty
*/
int peek(Queue* q){
    if(q->size == 0){
        printf("Empty Queue Error");
        exit(EMPTY_QUEUE_ERROR);
    }
    return q->front->val;
}

/**
 * returns the value at the front of the queue, after it removes this value fron the Queue and
 * frees the memory that was dynamically allocated for it.
*/
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

/**
 * returns 1 if the queue contains a given value, 0 otherwise
*/
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

/**
 * returns the number of times a given value is found in the queue
*/
int count(Queue* q, int value){
    int ans = 0;
    struct Node* start = q->front;
    while(start!=NULL){
        if(start->val == value){
            ans++;
        }
        start = start->next;
    }
    return ans;
}