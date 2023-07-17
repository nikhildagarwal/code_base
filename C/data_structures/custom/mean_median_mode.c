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
 * Constructor for double node
*/
struct dNode{
  int val;
  struct dNode* next;
  struct dNode* prev;
};

/**
 * Constructor for Double Linked List with center node references
*/
typedef struct{
    int size;
    struct dNode* left;
    struct dNode* right;
    struct dNode* center_left;
    struct dNode* center_right;
}MiddleQueue;

/**
 * Initialize doubly linked list
 * @param mq : pointer to MiddleQueue var
*/
void initMQ(MiddleQueue* mq){
    mq->size = INIT_SIZE;
    mq->left = NULL;
    mq->right = NULL;
    mq->center_left = NULL;
    mq->center_right = NULL;
}

/**
 * Add value to Doubly linked list
 * update positions of end referenes and center refs
 * @param mq : Pointer to MiddleQueue var
 * @param val : integer value to add
*/
void addMQ(MiddleQueue* mq,int val){
    struct dNode* new_d_node = (struct dNode*)malloc(sizeof(struct dNode));
    new_d_node->val = val;
    if(mq->size > 2){
        if(mq->size % 2 == 0){
            if(val < mq->left->val){
                /*Section 0*/
                new_d_node->next = mq->left;
                mq->left->prev = new_d_node;
                mq->left = new_d_node;
                mq->center_right = mq->center_left;
            }else if(val >= mq->right->val){
                /*Section 4*/
                new_d_node->prev = mq->right;
                mq->right->next = new_d_node;
                mq->right = new_d_node;
                mq->center_left = mq->center_right;
            }else if(val >= mq->left->val && val< mq->center_left->val){
                /*Section 1*/
                struct dNode* start = mq->left;
                while(start != mq->center_left){
                    struct dNode* l = start;
                    struct dNode* r = start->next;
                    if(val>=l->val && val<=r->val){
                        new_d_node->next = l->next;
                        l->next = new_d_node;
                        new_d_node->prev = r->prev;
                        r->prev = new_d_node;
                        start = mq->center_left->prev;
                    }
                    start = start->next;
                }
                mq->center_right = mq->center_left;
            }else if(val>= mq->center_right->val && val<mq->right->val){
                /*Section 3*/
                struct dNode* start = mq->center_right;
                while(start != mq->right){
                    struct dNode* l = start;
                    struct dNode* r = start->next;
                    if(val>=l->val && val<=r->val){
                        new_d_node->next = l->next;
                        l->next = new_d_node;
                        new_d_node->prev = r->prev;
                        r->prev = new_d_node;
                        start = mq->right->prev;
                    }
                    start = start->next;
                }
                mq->center_left = mq->center_right;
            }else{
                /*Section 2*/
                new_d_node->next = mq->center_right;
                mq->center_left->next = new_d_node;
                new_d_node->prev = mq->center_left;
                mq->center_right->prev = new_d_node;
                mq->center_right = new_d_node;
                mq->center_left = new_d_node;
            }
        }else{
            if(val < mq->left->val){
                /*Section 0*/
                new_d_node->next = mq->left;
                mq->left->prev = new_d_node;
                mq->left = new_d_node;
                mq->center_left = mq->center_left->prev;
            }else if(val >= mq->right->val){
                /*Section 3*/
                mq->right->next = new_d_node;
                new_d_node->prev = mq->right;
                mq->right = new_d_node;
                mq->center_right = mq->center_right->next;
            }else if(val>=mq->left->val && val<mq->center_left->val){
                /*Section 1*/
                struct dNode* start = mq->left;
                while(start != mq->center_left){
                    struct dNode* l = start;
                    struct dNode* r = start->next;
                    if(val>=l->val && val<=r->val){
                        new_d_node->next = l->next;
                        l->next = new_d_node;
                        new_d_node->prev = r->prev;
                        r->prev = new_d_node;
                        start = mq->center_left->prev;
                    }
                    start = start->next;
                }
                mq->center_left = mq->center_left->prev;
            }else{
                /*Section 2*/
                struct dNode* start = mq->center_left;
                while(start != mq->right){
                    struct dNode* l = start;
                    struct dNode* r = start->next;
                    if(val>=l->val && val<=r->val){
                        new_d_node->next = l->next;
                        l->next = new_d_node;
                        new_d_node->prev = r->prev;
                        r->prev = new_d_node;
                        start = mq->right->prev;
                    }
                    start = start->next;
                }
                mq->center_right = mq->center_right->next;
            }
        }
    }else if(mq->size == 0){
        mq->center_left = new_d_node;
        mq->center_right = new_d_node;
        mq->right = new_d_node;
        mq->left = new_d_node;
    }else if(mq->size==1){
        if(val>mq->center_left->val){
            mq->center_left->next = new_d_node;
            new_d_node->prev = mq->center_left;
            mq->center_right = new_d_node;
            mq->right = new_d_node;
        }else{
            mq->center_left->prev = new_d_node;
            new_d_node->next = mq->center_left;
            mq->center_left = new_d_node;
            mq->left = new_d_node;
        }
    }else if(mq->size == 2){
        if(val < mq->center_left->val){
            new_d_node->next = mq->center_left;
            mq->center_left->prev = new_d_node;
            mq->left = new_d_node;
            mq->center_right = mq->center_left;
        }else if(val > mq->center_right->val){
            new_d_node->prev = mq->center_right;
            mq->center_right->next = new_d_node;
            mq->right = new_d_node;
            mq->center_left = mq->center_right;
        }else{
            new_d_node->next = mq->center_left->next;
            mq->center_left->next = new_d_node;
            new_d_node->prev = mq->center_right->prev;
            mq->center_right->prev = new_d_node;
            mq->center_right = new_d_node;
            mq->center_left = new_d_node;
        }
    }
    mq->size++;
}

/**
 * remove a value from Double linked list
 * update positions of end referenes and center refs
 * @param mq : Pointer to MiddleQueue var
 * @param val : integer value to add
*/
void removeMQ(MiddleQueue* mq, int val){
    struct dNode* toFree = NULL;
    struct dNode* first = mq->left;
    while(first!=NULL){
        if(first->val == val){
            toFree = first;
            first = NULL;
        }
        if(first != NULL){
            first = first->next;
        }
    }
    if(toFree == NULL){
        return;
    }
    if(mq->size>2){
        if(mq->size % 2 == 0){
            if(toFree == mq->left){
                mq->left = mq->left->next;
                mq->left->prev = NULL;
                mq->center_left = mq->center_left->next;
            }else if(toFree == mq->right){
                mq->right = mq->right->prev;
                mq->right->next = NULL;
                mq->center_right = mq->center_right->prev;
            }else if(toFree == mq->center_left){
                struct dNode* l = mq->center_left->prev;
                struct dNode* r = mq->center_left->next;
                l->next = r;
                r->prev = l;
                mq->center_left = r;
            }else if(toFree == mq->center_right){
                struct dNode* l = mq->center_left->prev;
                struct dNode* r = mq->center_left->next;
                l->next = r;
                r->prev = l;
                mq->center_right = l;
            }else if(val < mq->center_left->val){
                struct dNode* l = toFree->prev;
                struct dNode* r = toFree->next;
                l->next = r;
                r->prev = l;
                mq->center_left = mq->center_left->next;
            }else if(val>mq->center_right->val){
                struct dNode* l = toFree->prev;
                struct dNode* r = toFree->next;
                l->next = r;
                r->prev = l;
                mq->center_right = mq->center_right->prev;
            }
        }else{
            if(toFree == mq->center_left){
                mq->center_left = mq->center_left->prev;
                mq->center_right = mq->center_right->next;
                mq->center_left->next = mq->center_right;
                mq->center_right->prev = mq->center_left;
                
            }else if(toFree == mq->left){
                mq->left = mq->left->next;
                mq->left->prev = NULL;
                mq->center_right = mq->center_right->next;
                
            }else if(toFree == mq->right){
                mq->right = mq->right->prev;
                mq->right->next = NULL;
                mq->center_left = mq->center_left->prev;
                
            }else if(val < mq->center_left->val){
                struct dNode* l = toFree->prev;
                struct dNode* r = toFree->next;
                l->next = r;
                r->prev = l;
                mq->center_right = mq->center_right->next;
                
            }else if(val > mq->center_right->val){
                struct dNode* l = toFree->prev;
                struct dNode* r = toFree->next;
                l->next = r;
                r->prev = l;
                mq->center_left = mq->center_left->prev;
                
            }
        }
    }else if(mq->size == 2){
        mq->left->next = NULL;
        mq->right->prev = NULL;
        if(toFree == mq->left){
            mq->left = mq->right;
            mq->center_left = mq->right;
        }else{
            mq->right = mq->left;
            mq->center_right = mq->left;
        }
        
    }else if(mq->size == 1){
        mq->left = NULL;
        mq->right = NULL;
        mq->center_right = NULL;
        mq->center_left = NULL;
    }
    toFree->next = NULL;
    toFree->prev = NULL;
    mq->size--;
    free(toFree);
}

/**
 * Visual representation of Doubly linked list to command line
 * @param mq : pointer to MiddleQueue var
*/
void printMQ(MiddleQueue* mq){
    printf("----- MQ START -----\n");
    printf("Middle Queue Size: %d\n",mq->size);
    struct dNode* head = mq->left;
    while(head!=NULL){
        if(head == mq->center_left && head == mq->center_right){
            printf("(%d) --> ",head->val);
        }else if(head == mq->center_left){
            printf("{%d{ --> ",head->val);
        }else if(head == mq->center_right){
            printf("}%d} --> ",head->val);
        }else{
            printf("%d --> ",head->val);
        }
        head = head->next;
    }
    printf("null\n");
    printf("----- MQ END -----\n");
}

/**
 * getter method for median of values in doubly linked list
 * @param mq : pointer to MiddleQueue var
 * @return double median
*/
double getMedianMQ(MiddleQueue* mq){
    double first = (double) mq->center_left->val;
    double second = (double) mq->center_right->val;
    return (first + second) / 2.0;
}

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
    free(m);
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
 * method to get corresponding key of max value in map
 * @param m : pointer to Map
*/
int getKeyOfMaxValue(Map* m){
    int key = 0;
    int val = 0;
    for(int i = 0;i<m->bucket_count;i++){
        struct Node* head = m->buckets[i];
        while(head!=NULL){
            if(head->val > val){
                val = head->val;
                key = head->key;
            }
            head = head->next;
        }
    }
    return key;
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
    printf("----- MAP START -----\n");
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
    printf("----- MAP END -----\n");
}

/**
 * Constructor for MMM_Structure (Mean, Median, Mode)
*/
typedef struct{
    int size;
    double average;
    double median;
    int mode;
    int total;
    Map* thisMap;
    MiddleQueue* thisMQ;
}MMM_Structure;

/**
 * Initialze MMM structure
 * @param m : pointer to MMM_Structure var
*/
void initMMMStructure(MMM_Structure* m){
    m->size = INIT_SIZE;
    m->average = INIT_AVERAGE;
    m->median = INIT_MEDIAN;
    m->mode = INIT_MODE;
    m->total = INIT_TOTAL;
    m->thisMap = (Map*)malloc(sizeof(Map));
    initMap(m->thisMap);
    m->thisMQ = (MiddleQueue*)malloc(sizeof(MiddleQueue));
    initMQ(m->thisMQ);
}

/**
 * add value to MMM structure
 * @param m : pointer to MMM_Structure var
 * @param val : integer val to add
*/
void MMMadd(MMM_Structure* m, int val){
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
    addMQ(m->thisMQ,val);
    m->median = getMedianMQ(m->thisMQ);
}

/**
 * remove value from MMM structure
 * @param m : pointer to MMM_Structure var
 * @param val : integer val to remove
*/
void MMMremove(MMM_Structure* m, int val){
    if(m->size <= 0 || containsKey(m->thisMap,val) == 0){
        return;
    }
    m->size--;
    m->total -= val;
    double t = (double) m->total;
    double s = (double) m->size;
    m->average = t / s;
    int occ = getValueFromKey(m->thisMap,val);
    if(occ == 1){
        removeKey(m->thisMap,val);
    }else{
        put(m->thisMap,val,occ-1);
    }
    if(val == m->mode){
        m->mode = getKeyOfMaxValue(m->thisMap);
    }
    removeMQ(m->thisMQ,val);
    m->median = getMedianMQ(m->thisMQ);
}

/**
 * Prints MMM structure to command line for better visualization of structure
 * @param m : pointer to MMM_Structure var
*/
void printMMM_Structure(MMM_Structure* m){
    printf("mean: %f | median: %f | mode: %d\n",m->average,m->median,m->mode);
    printf("MMM_Structure size: %d\n",m->size);
    printMap_Testing(m->thisMap);
    printMQ(m->thisMQ);
}

/**
 * getter method for mean (average) of MMM structure values
 * @param m : pointer to MMM_Structure var
 * @return double average
*/
double MMM_mean(MMM_Structure* m){
    return m->average;
}

/**
 * getter method for median (middle value) of MMM structure values
 * @param m : pointer to MMM_Structure var
 * @return double median
*/
double MMM_median(MMM_Structure* m){
    return m->median;
}

/**
 * getter method for mode (most frequent value) in MMM structure
 * @param m : pointer to MMM_Structure var
 * @return int mode
*/
int MMM_mode(MMM_Structure* m){
    return m->mode;
}

int main() {
    srand(time(NULL));
    MMM_Structure mmm;
    initMMMStructure(&mmm);
    MMMadd(&mmm,4);
    MMMadd(&mmm,5);
    MMMadd(&mmm,9);
    MMMadd(&mmm,2);
    MMMadd(&mmm,23);
    MMMremove(&mmm,5);
    printMMM_Structure(&mmm);
    return 0;
}