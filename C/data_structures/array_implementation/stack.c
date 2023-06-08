/**
 * Implementation of Stack in C.
 * Dynamically resizes itself when size is too small. 
 * Dynamically resizes it self when (length(of internal array) - size(of stack)) diff reaches a certain value.
 * Init as -->  Stack stack1;
 *              initStack(&stack1);
 * Destroy -->  endStack(&stack1);
 * Methods -->  void printStack(Stack* stack);
 *              void push(Stack* stack, int value);
 *              int isEmpty(Stack* stack);
 *              int contains(Stack* stack, int value);
 *              int peek(Stack* stack);
 *              int pop(Stack* stack);
 *              int count(Stack* stack, int value);
 *              int size(Stack* stack);
 * @author Nikhil Daehee Agarwal
*/

#include <stdio.h>
#include <stdlib.h>

const int INIT_CAPACITY = 10;
const int INIT_SIZE = 0;
const int EMPTY_STACK_ERROR = 8;

/**
 * Definition for Stack structure
*/
typedef struct{
    int size;
    int length;
    int* array;
} Stack;

/**
 * Initializes the stack structure and allocates space for the stack.
*/
void initStack(Stack* s){
    s->size = INIT_SIZE;
    s->length = INIT_CAPACITY;
    s->array = (int*) malloc(INIT_CAPACITY * sizeof(int));
}

/**
 * Free's allocated memory for stack
*/
void endStack(Stack* s){
    free(s->array);
    printf("Free Pointer: Success\n");
}

/**
 * Prints Stack Structure to command line
*/
void printStack(Stack* s){
    printf("[");
    for(int i = 0;i<s->size;i++){
        printf("%d",s->array[i]);
        if(i!=s->size - 1){
            printf(", ");
        }
    }
    printf("] --> top\n");
}

/**
 * Adds a value to the stack strucutre at the top.
 * Stack follows LIFO principle
*/
void push(Stack* s, int value){
    if(s->size == s->length){
        s->array = (int*)realloc(s->array,(s->length + INIT_CAPACITY) * sizeof(int));
        s->length += INIT_CAPACITY;
    }
    s->array[s->size] = value;
    s->size++;
}

/**
 * returns 1 if the stack contains no elements (size is 0),
 * return 0 if stack is not empty.
*/
int isEmpty(Stack* s){
    if(s->size == 0){
        return 1;
    }
    return 0;
}

/**
 * checks to see if the stack structure contains a specified value
*/
int contains(Stack* s, int value){
    for(int i = 0;i < s->size;i++){
        if(s->array[i] == value){
            return 1;
        }
    }
    return 0;
}

/**
 * returns the top element of the stack without removing the element
*/
int peek(Stack* s){
    if(s->size == 0){
        printf("Empty Stack Exception");
        exit(EMPTY_STACK_ERROR);
    }
    return s->array[s->size - 1];
}

/**
 * Pops the top most element of our stack.
 * returns the popped value.
 * if the stack structure is empty, throws an error
*/
int pop(Stack* s){
    if(s->size == 0){
        printf("Empty Stack Exception");
        exit(EMPTY_STACK_ERROR);
    }
    int value = s->array[s->size - 1];
    s->size--;
    if(s->length - s->size >= INIT_CAPACITY * 2){
        s->length -= INIT_CAPACITY;
        s->array = (int*)realloc(s->array,s->length * sizeof(int));
    }
    return value;
}

/**
 * getter method for the size of the structure.
*/
int size(Stack* s){
    return s->size;
}

/**
 * Returns the number of times a certain value is found in the stack structure
*/
int count(Stack* s, int value){
    int c = 0;
    for(int i = 0;i<s->size;i++){
        if(s->array[i] == value){
            c++;
        }
    }
    return c;
}