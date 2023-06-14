#include <stdio.h>
#include <stdlib.h>

const int INIT_SIZE = 0;
const int EMPTY_STACK_ERROR = 8;

/**
 * structure definition for Node structure (Makes up the compoenets of the Linked List);
*/
struct Node{
    int val;
    struct Node* next;
};

/**
 * structure definiton for Stack structure
 * contains pointer to the top node
*/
typedef struct{
    int size;
    struct Node* top;
} Stack;

/**
 * Initializes stack structure,
 * size to 0 and pointer to top node as NULL
*/
void initStack(Stack* s){
    s->size = INIT_SIZE;
    s->top = NULL;
}

/**
 * Destroys the stack structure by freeing all of the dynamically allocated memory for the stack
*/
void endStack(Stack* s){
    while(s->top!=NULL){
        struct Node* toFree = s->top;
        s->top = s->top->next;
        free(toFree);
    }
}

/**
 * pushes an int value to the top of the stack
 * Creates a new node specified value and adds it to the front at the linked list.
*/
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

/**
 * prints the stack to the command line (terminal)
 * visual representation of stack
*/
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

/**
 * returns the number of times a value is found in the stack
*/
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

/**
 * returns 1 if the stack contains a specified value, 0 otherwise
*/
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

/**
 * returns 1 if the stack is empty (size is 0), 0 otherwise
*/
int isEmpty(Stack* s){
    if(s->size == 0){
        return 1;
    }
    return 0;
}

/**
 * returns the top value of the stack, and removes that value from the top of the stack.
 * Frees the dynamically allocated memory for the popped node.
 * prints error message if the stack is empty
*/
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

/**
 * returns the top value of the stack
 * prints error message if the stack is empty
*/
int peek(Stack* s){
    if(s->size == 0){
        printf("Empty Stack Error");
        exit(EMPTY_STACK_ERROR);
    }
    return s->top->val;
}

/**
 * returns the number of the elements currently in the stack
*/
int size(Stack* s){
    return s->size;
}