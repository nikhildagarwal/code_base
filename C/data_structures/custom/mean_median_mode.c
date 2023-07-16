#include <stdlib.h>
#include <stdio.h>
#include <time.h>

double INIT_AVERAGE = 0.0;
double INIT_MEDIAN = 0.0;
int INIT_MODE = 0;
int INIT_SIZE = 0;
int INIT_CAPACITY = 10;
double INIT_LOAD = 0.0;
int INIT_BUCKET_COUNT = 16;
double LOAD_FACTOR = 0.75;
int INIT_TOTAL = 0;

/**
 * Constructor for node in linked list
 * contains int key and int val
*/
struct Node{
    int key;
    int val;
    struct Node* next;
};

/**
 * Constructor for Map
*/
typedef struct{
    int size;
    double load;
    int bucket_count;
    struct Node** buckets;
} Map;

/**
 * Initializes Map by allocating space for buckets and setting size and load to 0
 * @param m : pointer to map
*/
void initMap(Map* m){
    m->size = INIT_SIZE;
    m->load = INIT_LOAD;
    m->bucket_count = INIT_BUCKET_COUNT;
    m->buckets = (struct Node**)malloc(INIT_BUCKET_COUNT * sizeof(struct Node*));
}

/**
 * End method for Map
 * Frees all previously allocated memory
 * @param m : pointer to map
*/
void endMap(Map* m){
    for(int i = 0;i<m->bucket_count;i++){
        struct Node* head = m->buckets[i];
        while(head != NULL){
            struct Node* toFree = head;
            head = head->next;
            free(toFree);
        }
    }
    free(m->buckets);
}

/**
 * Checks to see if map contains a certain key
 * @param m : pointer to map
 * @param key : int key
 * @return 1 if key is found, 0 otherwise
*/
int containsKey(Map* m, int key){
    int index = key % m->bucket_count;
    if(index<0){
        index *= -1;
    }
    struct Node* head = m->buckets[index];
    while(head!=NULL){
        if(head->key == key){
            return 1;
        }
        head = head->next;
    }
    return 0;
}

/**
 * adds a key, value pair to the map
 * if the key already exists in the map, the value is updated.
 * rearranges nodes into double number of buckets once a load factor of 0.75 has been reached
 * @param m : Pointer to map
 * @param key : int key
 * @param val : int value
*/
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

/**
 * Method to get the value associated with a specific key
 * @param m : pointer to map
 * @param key : int key we want to search for
 * @return integer value of key
*/
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
    return 0;
}

/**
 * Removes key, value pair from map
 * if the key is found, the pair is removed, else, this method returns prematurely without terminating due to error.
 * @param m : pointer to map
 * @param key : int key search for and remove
 * @return 0 if key is not found and not removed, 1 if found and removed
*/
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

/**
 * Prints map to the command line, for testing purposes
 * Displays buckets and corresponding linked lists
 * @param m : pointer to map
*/
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

typedef struct{
    int size;
    double average;
    double median;
    int mode;
    int total;
    Map* thisMap;
}MMM_Structure;

initStructure(MMM_Structure* m){
    m->size = INIT_SIZE;
    m->average = INIT_AVERAGE;
    m->median = INIT_MEDIAN;
    m->mode = INIT_MODE;
    m->total = INIT_TOTAL;
    initMap(m->thisMap);
}

void add(MMM_Structure* m, int val){
    m->size++;
    m->total += val;
    double t = (double) m->total;
    double s = (double) m->size;
    m->average = t / s;
    int currValOfKey = getValueFromKey(m->thisMap,val);
    put(m->thisMap,val,currValOfKey+1);
    int modeFreq = getValueFromKey(m->thisMap,m->mode);
    if((currValOfKey+1) > modeFreq && m->mode != val){
        m->mode = val;
    }
}

void printMMM_Structure(MMM_Structure* m){
    printf("mean: %f | median: %f | mode: %d\n",m->average,m->median,m->mode);
    printf("number of elements: %d\n",m->size);
    printMap_Testing(m->thisMap);
}

int main() {
    srand(time(NULL));
    MMM_Structure m;
    initStructure(&m);
    for(int i = 0;i<76585;i++){
        add(&m,rand() % 100000);
    }
    printMMM_Structure(&m);
    return 0;
}