package com.mahsan.library.model;

public class GenericLinkedList<T> {
    private GenericNode<T> headNode;
    private int size;

    public GenericLinkedList(){
        headNode = null;
        size = 0;
    }

    public void insert(T data){
        if(isEmpty()){
            headNode = new GenericNode<>(data , null);
            incrementSize();
        }
        else {
            GenericNode<T> newNode = new GenericNode<>(data , null);
            findTailNode().setNextNode(newNode);
        }
    }

    public void removeByKey(T key){
        if(isEmpty()) return;

        while(headNode != null && headNode.getData().equals(key)){
            headNode = headNode.getNextNode();
            decrementSize();
        }

        GenericNode<T> prevNode = headNode;
        GenericNode<T> node = headNode.getNextNode();

        while(node != null){
            if(node.getData().equals(key)){
                node = node.getNextNode();
                prevNode.setNextNode(node);
                decrementSize();
            }
            else {
                prevNode = node;
                node = node.getNextNode();
            }
        }

    }

    public GenericNode<T> getHeadNode(){
        return headNode;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void incrementSize(){
        size += 1;
    }

    private void decrementSize(){
        if(size >= 1) size -= 1;
    }

    private GenericNode<T> findTailNode(){
        GenericNode<T> node = headNode;
        while(node.getNextNode() != null){
            node = node.getNextNode();
        }
        return node;
    }
}

