/**
 * Implementation of List in C.
 * Dynamically resizes itself when size is too small. 
 * Dynamically resizes it self when (length(of array) - size(of list)) diff reaches a certain value.
 * Init as -->  List list1;
 *              initList(&list1);
 * Destroy -->  endList(&list1);
 * Methods -->  void printList(List* list);
 *              void reverse(List* list);
 *              void add(List* list, int value);
 *              void set(List* list, int index, int value);
 *              int delete(List* list, int index);
 *              int size(List* list);
 *              int isEmpty(List* list);
 *              int contains(List* list, int value);
 *              int get(List* list, int index);
 * @author Nikhil Daehee Agarwal
*/

#include <stdio.h>
#include <stdlib.h>

const int INIT_CAPACITY = 10;
const int INDEX_OUT_OF_BOUNDS_ERROR = 9;
const int INIT_SIZE = 0;

/**
 * Definition for List Structure
*/
typedef struct{
    int size;
    int length;
    int* array;
} List;

/**
 * Initializes List
 * Sets size to 0, and initial array length to the const INIT_CAPACITY
 * Dynamically Allocates memory for List.
*/
void initList(List* l){
    l->size = 0;
    l->length = INIT_CAPACITY;
    l->array = (int*)malloc(INIT_CAPACITY * sizeof(int));
}

/**
 * End instruction for List.
 * Free the int pointer in List structure
*/
void endList(List* l){
    free(l->array);
    printf("Exited Normally (0)");
}

/**
 * Prints List Structure to command line
*/
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

/**
 * Reverses the contents of the List
*/
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

/**
 * Adds integer to List Structure
 * if the dynamically allocated memory is full, reallocate some more memory.
*/
void add(List* l, int value){
    if(l->size == l->length){
        l->array = (int*)realloc(l->array,(l->length + INIT_CAPACITY)*sizeof(int));
        l->length += INIT_CAPACITY;
    }
    l->array[l->size] = value;
    l->size++;
}

/**
 * Sets the index of an array to a specific value
*/
void set(List* l, int index, int value){
    if(index<0 || index >= l->size){
        printf("Index Out Of Bounds Exception (9)");
        exit(INDEX_OUT_OF_BOUNDS_ERROR);
    }
    l->array[index] = value;
}

/**
 * removes a value at a specified index from List structure
 * if the length of the array - size of our List structure >= 2 times the INIT_CAPACITY, shrink our array by reallocating less memory
 * return the value of the deleted index;
*/
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

/**
 * Getter method for the size of our List structure
 * return int size
*/
int size(List* l){
    return l->size;
}

/**
 * Checks to see if List Structure is Empty or not.
 * return 1 if empty, 0 if not empty
*/
int isEmpty(List* l){
    if(l->size == 0){
        return 1;
    }
    return 0;
}

/**
 * Checks to see if our List structure contains a certain value
 * return 1 if the list contains the value, 0 otherwise
*/
int contains(List* l, int value){
    for(int i = 0;i<l->size;i++){
        if(l->array[i] == value){
            return 1;
        }
    }
    return 0;
}

/**
 * returns the value at a specific index in List
*/
int get(List* l, int index){
    if(index<0 || index >= l->size){
        printf("Index Out Of Bounds Exception (9)");
        exit(INDEX_OUT_OF_BOUNDS_ERROR);
    }
    return l->array[index];
}