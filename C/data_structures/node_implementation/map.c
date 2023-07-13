#include <stdlib.h>
#include <stdio.h>

int INIT_SIZE = 0;
double INIT_LOAD = 0.0;
int INIT_BUCKET_COUNT = 16;
double LOAD_FACTOR = 0.75;

struct Node{
    int key;
    int val;
    struct Node* next;
};

typedef struct{
    int size;
    double load;
    int bucket_count;
    struct Node** buckets;
} Map;

void initMap(Map* m){
    m->size = INIT_SIZE;
    m->load = INIT_LOAD;
    m->bucket_count = INIT_BUCKET_COUNT;
    m->buckets = (struct Node**)malloc(INIT_BUCKET_COUNT * sizeof(struct Node*));
}

void put(Map* m, int key, int val){
    
}

void printMap_Testing(Map* m){
    printf("size: %d, load: %f\n",m->size,m->load);
    for(int i = 0;i<m->bucket_count;i++){
        struct Node* start = m->buckets[i];
        printf("[%d] ",i);
        while(start != NULL){
            printf("<%d:%d> -> ",start->key,start->val);
            start = start->next;
        }
        printf("null\n");
    }
    printf("\n");
}

int main() {
    Map m1;
    initMap(&m1);
    for(int i = -5;i<5;i++){
        put(&m1,i,1);
    }
    put(&m1,13,342);
    put(&m1,51,34);
    printMap_Testing(&m1);
    return 0;
}