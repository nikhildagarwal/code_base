#include <stdio.h>
#include <stdlib.h>

const int INIT_CAPACITY = 10;
const int INIT_SIZE = 0;
const int EMPTY_STACK_ERROR = 8;


typedef struct{
    int size;
    int length;
    int* array;
} Stack;

void initStack(Stack* s){
    s->size = INIT_SIZE;
    s->length = INIT_CAPACITY;
    s->array = (int*) malloc(INIT_CAPACITY * sizeof(int));
}

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

void push(Stack* s, int value){
    if(s->size == s->length){
        s->array = (int*)realloc(s->array,(s->length + INIT_CAPACITY) * sizeof(int));
        s->length += INIT_CAPACITY;
    }
    s->array[s->size] = value;
    s->size++;
}

int isEmpty(Stack* s){
    if(s->size == 0){
        return 1;
    }
    return 0;
}

int contains(Stack* s, int value){
    for(int i = 0;i < s->size;i++){
        if(s->array[i] == value){
            return 1;
        }
    }
    return 0;
}

int peek(Stack* s){
    if(s->size == 0){
        printf("Empty Stack Exception");
        exit(EMPTY_STACK_ERROR);
    }
    return s->array[s->size - 1];
}

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

int count(Stack* s, int value){
    int c = 0;
    for(int i = 0;i<s->size;i++){
        if(s->array[i] == value){
            c++;
        }
    }
    return c;
}

int main() {
    Stack stack1;
    initStack(&stack1);
    printStack(&stack1);
    for(int i = 0;i<17;i++){
        push(&stack1,i+1);
    }
    push(&stack1,3);
    push(&stack1,3);
    printStack(&stack1);
    printf("count of value 3: %d\n",count(&stack1,3));
    printf("Stack contains 17? --> %d\n",contains(&stack1,17));
    printf("Stack contains 18? --> %d\n",contains(&stack1,18));
    printf("top value: %d\n",peek(&stack1));
    while(!isEmpty(&stack1)){
        printStack(&stack1);
        printf("popped: %d\n",pop(&stack1));
    }
    printStack(&stack1);
    endStack(&stack1);
    return 0;
}