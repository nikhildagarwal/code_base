/**
 * Implementation of a Set data structure in C.
 * Uses Node structures linked together into multiple singly linked list.
 * Uses an array of "buckets" where the contents of a each bucket is a linked list.
 * By definition of a set, each element must be unique.
 * Automatically increases the number of buckets and redistributes nodes to the buckets after the ratio of elements to buckets has reached 0.75 (load factor of 0.75)
 * Init as -->  Set s1;
 *              initSet(&s1);
 * Destroy -->  endSet(&s1);
 * Methods -->  int add(Set* s, int value);
 *              void printSet_Testing(Set* s);
 *              int size(Set* s);
 *              int setContains(Set* s, int value);
 *              int setRemove(Set* s, int value);
 * @author Nikhil Daehee Agarwal
*/

#include <stdlib.h>
#include <stdio.h>

const int INIT_SIZE = 0;
const int INIT_BUCKET_COUNT = 16;
const double START_LOAD = 0.0;
const double LOAD_FACTOR = 0.75;

/**
 * Constructor for Node in linked List
*/
struct Node{
    int val;
    struct Node* next;
};

/**
 * Set constructor
*/
typedef struct{
    int size;
    double load;
    struct Node** buckets;
    int bucket_count;
} Set;

/**
 * Initialize new set
 * allocate memory for buckets
*/
void initSet(Set* s){
    s->size = INIT_SIZE;
    s->load = START_LOAD;
    s->bucket_count = INIT_BUCKET_COUNT;
    s->buckets = (struct Node**)malloc(INIT_BUCKET_COUNT*sizeof(struct Node*));
}

/**
 * Free all allocated memory
 * @param s : pointer to set var
*/
void endSet(Set* s){
    for(int i = 0;i<s->bucket_count;i++){
        if(s->buckets[i] != NULL){
            struct Node* head = s->buckets[i];
            s->buckets[i] = NULL;
            while(head!=NULL){
                struct Node* toFree = head;
                head = head->next;
                free(toFree);
            }
        }
    }
    free(s->buckets);
}

/**
 * adds a value to the set
 * resizes and reallocates memory for more buckets once load factor has been reached
 * @param s : pointer to Set var
 * @param value : integer value to add
 * @return 1 if value added, 0 if value not added because set already contains value
*/
int add(Set* s, int value){
     double distance = 1.0 / s->bucket_count;
     if(s->load + distance >= LOAD_FACTOR){
         s->bucket_count *= 2;
         s->buckets = (struct Node**)realloc(s->buckets,s->bucket_count * sizeof(struct Node*));
         for(int i = 0;i<s->bucket_count / 2;i++){
             struct Node* head = s->buckets[i];
             struct Node* prev = NULL;
             while(head!=NULL){
                 int newIndex = head->val % s->bucket_count;
                 if(newIndex<0){
                     newIndex *= -1;
                 }
                 if(newIndex == i){
                     prev = head;
                     head = head->next;
                 }else{
                     struct Node* severed = head;
                     if(prev == NULL){
                         head = head->next;
                         s->buckets[i] = head;
                     }else{
                         prev->next = head->next;
                         head = head->next;
                     }
                     severed->next = s->buckets[newIndex];
                     s->buckets[newIndex] = severed;
                 }
             }
         }
     }
     int index = value % s->bucket_count;
     if(index<0){
         index *= -1;
     }
     if(s->buckets[index] == NULL){
         struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
         newNode->val = value;
         s->buckets[index] = newNode;
         s->size++;
         double noe = (double) s->size;
         double nob = (double) s->bucket_count;
         s->load = noe / nob;
     }else{
         struct Node* head = s->buckets[index];
         while(head!=NULL){
             if(head->val == value){
                 return 0;
             }
             head = head->next;
         }
         struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
         newNode->val = value;
         newNode->next = s->buckets[index];
         s->buckets[index] = newNode;
         s->size++;
         double noe = (double) s->size;
         double nob = (double) s->bucket_count;
         s->load = noe / nob;
     }
     return 1;
}

/**
 * Method to check if a set contains a value
 * @param s : pointer to set var
 * @param value : integer value to check for
 * @return 1 if found, 0 if value not found
*/
int setContains(Set* s, int value){
    int index = value % s->bucket_count;
    if(index<0){
        index *= -1;
    }
    struct Node* head = s->buckets[index];
    while(head!=NULL){
        if(head->val == value){
            return 1;
        }
        head = head->next;
    }
    return 0;
}

/**
 * Removes a given value from the set if the set contains that value
 * @param s : pointer to set var
 * @param value : integer value to remove
 * @return 1 if value removed, 0 if value not removed 
*/
int setRemove(Set* s, int value){
    int index = value % s->bucket_count;
    if(index<0){
        index *= -1;
    }
    struct Node* head = s->buckets[index];
    struct Node* prev = NULL;
    while(head != NULL){
        if(head->val == value){
            struct Node* toFree = head;
            if(prev == NULL){
                head = head->next;
                s->buckets[index] = head;
            }else{
                prev->next = head->next;
                head = head->next;
            }
            s->size--;
            double noe = s->size;
            double nob = s->bucket_count;
            s->load = noe / nob;
            free(toFree);
            return 1;
        }
        prev = head;
        head = head->next;
    }
    return 0;
}

/**
 * print set to command line for testing purposes
 * shows each bucket/linked list
 * displays size and load factor of the set
 * @param s : pointer to set var
*/
void printSet_Testing(Set* s){
    printf("size: %d, load: %f\n",s->size,s->load);
    for(int i = 0;i<s->bucket_count;i++){
        struct Node* start = s->buckets[i];
        printf("[%d] ",i);
        while(start != NULL){
            printf("%d-> ",start->val);
            start = start->next;
        }
        printf("null\n");
    }
    printf("\n");
}

/**
 * gets the size of the set
 * @param s : pointer to set variable
 * @return integer number of elements in the set
*/
int size(Set* s){
    return s->size;
}

int main() {
    Set s1;
    initSet(&s1);
    for(int i = -4;i<13;i++){
        add(&s1,i);
    }
    printSet_Testing(&s1);
    return 0;
}