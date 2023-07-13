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
    int index = key % m->bucket_count;
    if(index < 0){
        index *= -1;
    }
    struct Node* start = m->buckets[index];
    while(start != NULL){
        if(start->key == key){
            start->val = val;
            return;
        }
        start = start->next;
    }
    double distance = 1.0 / m->bucket_count;
    if(m->load + distance >= LOAD_FACTOR){
        m->bucket_count *= 2;
        m->buckets = (struct Node**)realloc(m->buckets,m->bucket_count * sizeof(struct Node*));
        for(int i = 0;i < m->bucket_count / 2;i++){
            struct Node* head = m->buckets[i];
            struct Node* prev = NULL;
            while(head != NULL){
                int newIndex = head->key % m->bucket_count;
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
                        m->buckets[i] = head;
                    }else{
                        prev->next = head->next;
                        head = head->next;
                    }
                    severed->next = m->buckets[newIndex];
                    m->buckets[newIndex] = severed;
                }
            }
        }
    }
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    newNode->key = key;
    newNode->val = val;
    int finalIndex = key % m->bucket_count;
    if(finalIndex < 0){
        finalIndex *= -1;
    }
    newNode->next = m->buckets[finalIndex];
    m->buckets[finalIndex] = newNode;
    m->size++;
    double noe = (double) m->size;
    double nob = (double) m->bucket_count;
    m->load = noe / nob;
}

int getValueFromKey(Map* m, int key){
    int index = key % m->bucket_count;
    if(index < 0){
        index *= -1;
    }
    struct Node* head = m->buckets[index];
    while(head!=NULL){
        if(head->key == key){
            return head->val;
        }
        head = head->next;
    }
    return NULL;
}

int* getKeysFromValue(Map* m, int value){
    int* array = (int*)malloc(sizeof(int));
    array[0] = 0;
    for(int i = 0;i < m->bucket_count;i++){
        struct Node* head = m->buckets[i];
        while(head!=NULL){
            if(head->val == value){
                array[0]++;
                array = (int*)realloc(array,(array[0]+1)*sizeof(int));
                array[array[0]] = head->key;
            }
            head = head->next;
        }
    }
    return array;
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

int size(Map* m){
    return m->size;
}

int removeKey(Map* m, int key){
    int index = key % m->bucket_count;
    if(index < 0){
        index *= -1;
    }
    struct Node* head = m->buckets[index];
    struct Node* prev = NULL;
    while(head!=NULL){
        if(head->key == key){
            struct Node* toFree = head;
            if(prev == NULL){
                head = head->next;
                m->buckets[index] = head;
            }else{
                prev->next = head->next;
                head = head->next;
            }
            m->size--;
            double noe = (double) m->size;
            double nob = (double) m->bucket_count;
            m->load = noe / nob;
            free(toFree);
            return 1;
        }
        prev = head;
        head = head->next;
    }
    return 0;
}

int main() {
    Map m1;
    initMap(&m1);
    put(&m1,3,77);
    put(&m1,5,234);
    put(&m1,5,32);
    put(&m1,3,63);
    put(&m1,6,63);
    put(&m1,-3,63);
    put(&m1,11,467);
    put(&m1,27,63);
    put(&m1,14,63);
    put(&m1,30,353);
    removeKey(&m1,3);
    printMap_Testing(&m1);
    return 0;
}