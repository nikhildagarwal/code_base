#include <stdlib.h>
#include <stdio.h>

int INIT_SIZE = 0;

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
 * Frees all allocated Memory in Doubly linked list (MiddleQueue)
 * @param mq : pointer to MiddleQueue var
*/
void endMQ(MiddleQueue* mq){
    struct dNode* head = mq->left;
    while(head!=NULL){
        struct dNode* toFree = head;
        head = head->next;
        head->prev = NULL;
        toFree->next = NULL;
        free(toFree);
    }
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

int main() {
    return 0;
}