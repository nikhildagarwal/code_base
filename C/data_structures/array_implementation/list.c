#include <stdio.h>
#include <stdlib.h>

const int INIT_CAPACITY = 4;
const int INDEX_OUT_OF_BOUNDS_ERROR = 9;
const int INIT_SIZE = 0;

typedef struct{
    int size;
    int length;
    int* array;
} List;

void initList(List* l){
    l->size = 0;
    l->length = INIT_CAPACITY;
    l->array = (int*)malloc(INIT_CAPACITY * sizeof(int));
}

void endList(List* l){
    free(l->array);
    printf("Exited Normally (0)");
}

void printList(List* l){
    printf("[");
    for(int i = 0;i<l->length;i++){
        printf("%d",l->array[i]);
        if(i!=l->length - 1){
            printf(", ");
        }
    }
    printf("]\n");
}

void add(List* l, int value){
    if(l->size == l->length){
        l->array = (int*)realloc(l->array,(l->length + INIT_CAPACITY)*sizeof(int));
        l->length += INIT_CAPACITY;
    }
    l->array[l->size] = value;
    l->size++;
}

int delete(List* l, int index){
    if(index<0 || index >= l->size){
        printf("Index Out Of Bounds Exception (9)");
        exit(INDEX_OUT_OF_BOUNDS_ERROR);
    }
    l->size--;
    int value = l->array[index];
    for(int i = index;i < l->size;i++){
        l->array[i] = l->array[i+1];
    }
    if(l->length - l->size >= INIT_CAPACITY * 2){
        l->array = (int*)realloc(l->array,(l->length - INIT_CAPACITY) * sizeof(int));
        l->length -= INIT_CAPACITY;
    }
    return value;
}

int size(List* l){
    return l->size;
}

int main(){
    List list1;
    initList(&list1);
    for(int i =0;i<7;i++){
        add(&list1,i);
    }
    for(int i = 0;i<4;i++){
        printf("%d\n",delete(&list1,0));
    }
    return 0;
}