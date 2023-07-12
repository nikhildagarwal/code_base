#include <stdlib.h>
#include <stdio.h>

const int INIT_SIZE = 0;
const int INIT_BUCKET_COUNT = 16;
const double START_LOAD = 0.0;
const double LOAD_FACTOR = 0.75;

struct Node{
    int val;
    struct Node* next;
};

typedef struct{
    int size;
    double load;
    struct Node** buckets;
    int bucket_count;
} Set;

void initSet(Set* s){
    s->size = INIT_SIZE;
    s->load = START_LOAD;
    s->bucket_count = INIT_BUCKET_COUNT;
    s->buckets = (struct Node**)malloc(INIT_BUCKET_COUNT*sizeof(struct Node*));
}

int add(Set* s, int value){
     double distance = 1.0 / s->bucket_count;
     printf("%f\n",distance);
     if(s->load + distance >= LOAD_FACTOR){
         s->bucket_count *= 2;
         s->buckets = (struct Node**)realloc(s->buckets,s->bucket_count * sizeof(struct Node*));
         for(int i = 0;i<s->bucket_count / 2;i++){
             struct Node* head = s->buckets[i];
             struct Node* prev = NULL;
             while(head!=NULL){
                 int newIndex = head->val % s->bucket_count;
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

int setContains(Set* s, int value){
    int index = value % s->bucket_count;
    struct Node* head = s->buckets[index];
    while(head!=NULL){
        if(head->val == value){
            return 1;
        }
        head = head->next;
    }
    return 0;
}

int setRemove(Set* s, int value){
    int index = value % s->bucket_count;
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

int size(Set* s){
    return s->size;
}

int main() {
    Set s1;
    initSet(&s1);
    for(int i = 0;i<15;i++){
        add(&s1,i);
    }
    for(int i = 0;i<15;i++){
        add(&s1,i);
    }
    printSet_Testing(&s1);
    return 0;
}