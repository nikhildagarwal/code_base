#include <stdio.h>
#include <stdlib.h>

const int INIT_SIZE = 0;
const int EMPTY_STACK_ERROR = 8;

typedef struct{
    int size;
    int* top;
} Stack;

void endStack(Stack* s){
    free(s->top);
    printf("Exited Normally (0)");
}

void push(Stack* s, int value){
    s->size++;
    *s->top = value;
    s->top -= sizeof(int);
}

void initStack(Stack* s){
    s->size = INIT_SIZE;
    s->top = malloc(sizeof(int));
}

int pop(Stack* s){
    if(s->size == 0){
        printf("Exited with Empty Stack Error (%d)\n",EMPTY_STACK_ERROR);
        exit(EMPTY_STACK_ERROR);
    }
    s->top += sizeof(int);
    s->size--;
    return *s->top;
}

int peek(Stack* s){
    if(s->size == 0){
        printf("Stack is Empty!\n");
        exit(EMPTY_STACK_ERROR);
    }
    int* pointer = s->top + sizeof(int);
    return *pointer;
}

int isEmpty(Stack* s){
    if(s->size == 0){
        return 1;
    }
    return 0;
}

int contains(Stack* s, int value){
    int start = sizeof(int);
    for(int i = 0;i<s->size;i++){
        int* p = s->top + start;
        if(*p == value){
            return 1;
        }
        start += sizeof(int);
    }
    return 0;
}

int size(Stack* s){
    return s->size;
}

int main() {
    Stack stack1;
    initStack(&stack1);
    for(int i = 0;i<13;i++){
        push(&stack1,i);
    }
    printf("Size: %d\n",size(&stack1));
    printf("Contains 6?: %d\n",contains(&stack1,6));
    printf("Contains 13?: %d\n",contains(&stack1,13));
    while(!isEmpty(&stack1)){
        printf("%d\n",pop(&stack1));
    }
    endStack(&stack1);
    return 0;
}