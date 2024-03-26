package com.example.myapplication

/**
 * Hash Map - Hash collision when we get same index after applying hash on different key.
 * Solution -
 * a. Method of chaining. Store the items in linked list which will be associated to bucket index.
 * Load factor - Average length of the list of the chain. If n is total items(key) and m is total no of index i.e.
 * array size then load factor = n/m
 * b. open addressing - it will not have any linked list. Each index will hold only single item(key/value).
 * limitation - no of items should be less than array size.
 */
class HashMap {
}