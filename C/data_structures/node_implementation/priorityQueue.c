/**
 * Implementation of a PriorityQueue data structure in C.
 * Uses Node structures linked together into a singly linked list.
 * Queue follows First in First Out (FIFO) principle.
 * Automatically inserts nodes into the linkedlist at the correct place to order all elements from least to greatest.
 * Init as -->  PriorityQueue pq1;
 *              initPriorityQueue(&pq1);
 * Destroy -->  endPriorityQueue(&pq1);
 * Methods -->  void add(PriorityQueue* pq, int value);
 *              void printPriorityQueue(PriorityQueue* pq);
 *              int size(PriorityQueue* pq);
 *              int peek(PriorityQueue* pq);
 *              int poll(PriorityQueue* pq);
 *              int reverse(PriorityQueue* pq);
 * @author Nikhil Daehee Agarwal
*/

#include <stdio.h>
#include <stdlib.h>

const int INIT_SIZE = 0;
const int EMPTY_QUEUE_ERROR = 7;

/**
 * Constructor For node in linked list
*/
struct Node{
    int val;
    struct Node* next;
};

/**
 * Constructor for PriorityQueue
*/
typedef struct{
    int size;
    struct Node* front;
} PriorityQueue;

/**
 * Initializes the Priority Queue
 * @param pq : pointer to PriorityQueue variable
*/
void initPriorityQueue(PriorityQueue* pq){
    pq->size = INIT_SIZE;
    pq->front = NULL;
}

/**
 * Destroys the Priority Queue
 * Frees all allocated memory
 * @param pq : pointer to PriorityQueue variable
*/
void endPriorityQueue(PriorityQueue* pq){
    pq->size = INIT_SIZE;
    struct Node* start = pq->front;
    while(start!=NULL){
        struct Node* nodeToFree = start;
        start = start->next;
        free(nodeToFree);
    }
}

/**
 * Prints priority Queue to command line
 * @param pq : pointer to PriorityQueue variable
*/
void printPriorityQueue(PriorityQueue* pq){
    printf("front [");
    struct Node* start = pq->front;
    while(start!=NULL){
        printf("%d",start->val);
        if(start->next != NULL){
            printf(", ");
        }
        start = start->next;
    }
    printf("]\n");
}

/**
 * adds value to the priorityQueue\
 * @param pq : pointer to PriorityQueue variable
 * @param value : integer value to add
*/
void add(PriorityQueue* pq, int value){
    if(pq->size == 0){
        pq->front = (struct Node*)malloc(sizeof(struct Node));
        pq->front->val = value;
        pq->front->next = NULL;
    }else{
        struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
        newNode->val = value;
        int signal = 0;
        struct Node* start = pq->front;
        if(value<=start->val){
            newNode->next = start;
            pq->front = newNode;
            signal = 1;
        }
        while(start->next!=NULL && signal != 1){
            if(start->val<=value && value<=start->next->val){
                newNode->next = start->next;
                start->next = newNode;
                signal = 1;
            }
            start = start->next;   
        }
        if(signal!=1){
            newNode->next = NULL;
            start->next = newNode;
        }
    }
    pq->size++;
}

/**
 * method to return the size of the PriorityQueue
 * @param pq : Pointer to PriorityQueue variable
*/
int size(PriorityQueue* pq){
    return pq->size;
}

/**
 * method to return the top value of the priorityQueue
 * @param pq : Pointer to PriorityQueue variable
*/
int peek(PriorityQueue* pq){
    if(pq->size == 0){
        printf("Empty Queue Error");
        exit(EMPTY_QUEUE_ERROR);
    }
    return pq->front->val;
}

/**
 * method to return the top value of the priorityQueue while also removing the value from the PriorityQueue
 * @param pq : Pointer to PriorityQueue variable
*/
int poll(PriorityQueue* pq){
    if(pq->size == 0){
        printf("Empty Queue Error");
        exit(EMPTY_QUEUE_ERROR);
    }
    struct Node* nodeToPop = pq->front;
    pq->front = pq->front->next;
    int valToReturn = nodeToPop->val;
    pq->size--;
    free(nodeToPop);
    return valToReturn;
}

/**
 * method to reverse the direction of the PriorityQueue
 * @param pq : Pointer to PriorityQueue variable
*/
void reverse(PriorityQueue* pq){
    if(pq->size == 0 || pq->size == 1){
        return;
    }
    struct Node* prev = NULL;
    while(pq->front->next!=NULL){
        struct Node* next = pq->front->next;
        pq->front->next = prev;
        prev = pq->front;
        pq->front = next;
    }
    pq->front->next = prev;
}

int main() {
    PriorityQueue pq1;
    initPriorityQueue(&pq1);
    for(int i = 0;i<13;i++){
        add(&pq1,i);
        reverse(&pq1);
        printPriorityQueue(&pq1);
    }
    endPriorityQueue(&pq1);
    return 0;
}