#include <stdio.h>
#include <stdlib.h>

const int INIT_SIZE = 0;
const int EMPTY_STACK_ERROR = 8;

struct Node{
    int val;
    struct Node* next;
};

typedef struct{
    int size;
    struct Node* top;
} Stack;

void initStack(Stack* s){
    s->size = INIT_SIZE;
    s->top = NULL;
}

void endStack(Stack* s){
    while(s->top!=NULL){
        struct Node* toFree = s->top;
        s->top = s->top->next;
        free(toFree);
    }
}

void push(Stack* s, int value){
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    newNode->val = value;
    if(s->size == 0){
        newNode->next = NULL;
        s->top = newNode;
    }else{
        newNode->next = s->top;
        s->top = newNode;
    }
    s->size++;
}

void printStack(Stack* s){
    struct Node* start = s->top;
    printf("-top-> [");
    while(start!=NULL){
        printf("%d",start->val);
        if(start->next!=NULL){
            printf(", ");
        }
        start = start->next;
    }
    printf("]\n");
}

int count(Stack* s, int value){
    int c = 0;
    struct Node* start = s->top;
    while(start!=NULL){
        if(start->val == value){
            c++;
        }
        start = start->next;
    }
    return c;
}

int contains(Stack* s, int value){
    struct Node* start = s->top;
    while(start!=NULL){
        if(start->val == value){
            return 1;
        }
        start = start->next;
    }
    return 0;
}

int isEmpty(Stack* s){
    if(s->size == 0){
        return 1;
    }
    return 0;
}

int pop(Stack* s){
    if(s->size == 0){
        printf("Empty Stack Error");
        exit(EMPTY_STACK_ERROR);
    }
    int value = s->top->val;
    struct Node* toFree = s->top;
    s->top = s->top->next;
    s->size--;
    free(toFree);
    return value;
}

int peek(Stack* s){
    if(s->size == 0){
        printf("Empty Stack Error");
        exit(EMPTY_STACK_ERROR);
    }
    return s->top->val;
}

int size(Stack* s){
    return s->size;
}