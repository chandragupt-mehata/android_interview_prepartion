package com.example.test.dsa


class MyLinkedList {

    private var head: Node? = null
    private var tail: Node? = null
    private var length = 0

    inner class Node(var value: Int) {
        var next: Node? = null
    }

    fun prepend(value: Int) {
        val newNode = Node(value)
        newNode.next = this.head
        if (head == null) tail = newNode
        head = newNode
        length ++
    }

    fun append(value: Int) {
        val newNode = Node(value)
        newNode.next = null
        if (length == 0) {
            head = newNode
            tail = newNode
        } else {
            tail!!.next = newNode
            tail = newNode
        }
        length ++
    }

    fun printList() {
        var temp = head
        while (temp != null) {
            println(temp.value)
            temp = temp.next
        }
    }

    fun getHead(): Node? {
        if (head == null) {
            println("Head: null")
        } else {
            println("Head: " + head!!.value)
        }
        return head
    }

    fun getTail() {
        if (head == null) {
            println("Tail: null")
        } else {
            println("Tail: " + tail!!.value)
        }
    }

    fun getLength() {
        println("Length: $length")
    }

    fun removeLast(): Node? {
        var pre: Node? = head
        var temp: Node? = head
        while (temp?.next != null) {
            pre = temp
            temp = temp.next
        }
        pre?.next = null
        tail = pre
        length --
        if (length == 0) {
            head = null
            tail = null
        }
        return temp
    }

}

fun main() {
    val myLinkedList = MyLinkedList()
    myLinkedList.append(10)
    myLinkedList.append(12)
    myLinkedList.prepend(9)
    myLinkedList.prepend(7)
    myLinkedList.removeLast()
    myLinkedList.removeLast()
    myLinkedList.removeLast()
    myLinkedList.removeLast()
    myLinkedList.removeLast()
    myLinkedList.removeLast()
    myLinkedList.printList()
}