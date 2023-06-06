#include <stdio.h>
#include <stdlib.h>

const int INIT_CAPACITY = 4;
const int INDEX_OUT_OF_BOUNDS_ERROR = 9;
const int INIT_SIZE = 0;

typedef struct{
    int size;
    int* array;
} List;

void initList(List* l){
    l->size = 0;
    l->array = (int*)malloc(INIT_CAPACITY * sizeof(int));
}