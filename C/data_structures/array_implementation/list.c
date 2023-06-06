#include <stdio.h>
#include <stdlib.h>

const int INIT_CAPACITY = 10;
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
    for(int i = 0;i<l->size;i++){
        printf("%d",l->array[i]);
        if(i!=l->size - 1){
            printf(", ");
        }
    }
    printf("]\n");
}

void reverse(List* l){
    int mid = l->size / 2;
    int pR = l->size - 1;
    for(int i =0;i<mid;i++){
        int temp = l->array[i];
        l->array[i] = l->array[pR];
        l->array[pR] = temp;
        pR--;
    }
}

void add(List* l, int value){
    if(l->size == l->length){
        l->array = (int*)realloc(l->array,(l->length + INIT_CAPACITY)*sizeof(int));
        l->length += INIT_CAPACITY;
    }
    l->array[l->size] = value;
    l->size++;
}

void set(List* l, int index, int value){
    if(index<0 || index >= l->size){
        printf("Index Out Of Bounds Exception (9)");
        exit(INDEX_OUT_OF_BOUNDS_ERROR);
    }
    l->array[index] = value;
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

int isEmpty(List* l){
    if(l->size == 0){
        return 1;
    }
    return 0;
}

int contains(List* l, int value){
    for(int i = 0;i<l->size;i++){
        if(l->array[i] == value){
            return 1;
        }
    }
    return 0;
}

int get(List* l, int index){
    if(index<0 || index >= l->size){
        printf("Index Out Of Bounds Exception (9)");
        exit(INDEX_OUT_OF_BOUNDS_ERROR);
    }
    return l->array[index];
}

int main(){
    List list1;
    initList(&list1);
    for(int i =0;i<7;i++){
        add(&list1,i);
    }
    printList(&list1);
    reverse(&list1);
    printList(&list1);
    for(int i = 0;i<4;i++){
        printf("%d\n",delete(&list1,0));
    }
    return 0;
}