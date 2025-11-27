package com.mahsan.library.Modules;

import java.util.ArrayList;
import java.util.Objects;

class GenericNode<T> {
    private T data;
    private GenericNode<T> nextNode;
    GenericNode(T data , GenericNode<T> nextNode){
        this.data = data;
        this.nextNode = nextNode;
    }

    public T getData(){
        return data;
    }

    public GenericNode<T> getNextNode(){
        return nextNode;
    }

    public void setData(T data){
        this.data = data;
    }

    public void setNextNode(GenericNode<T> nextNode){
        this.nextNode = nextNode;
    }

    @Override
    public String toString(){
        return data.toString();
    }

    @Override
    public boolean equals(Object object){
        if(object == null || object.getClass() != getClass()) return false;
        else if(object == this) return true;
        else {
            GenericNode<?> node = (GenericNode<?>) object;
            return Objects.equals(node.getData() , data) &&
                    Objects.equals(node.getNextNode() , nextNode);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(data , nextNode);
    }
}

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

        // Remove invalid heads
        while(headNode != null && headNode.getData().equals(key)){
            headNode = headNode.getNextNode();
            decrementSize();
        }

        // Remove invalid nodes
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

    public void printList(){
        GenericNode<T> node = headNode;
        while(node != null){
            System.out.println(node);
            node = node.getNextNode();
        }
    }

    private void incrementSize(){
        size += 1;
    }

    private void decrementSize(){
        if(size >= 1) size -= 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public GenericNode<T> getHeadNode(){
        return headNode;
    }

    private GenericNode<T> findTailNode(){
        GenericNode<T> node = headNode;
        while(node.getNextNode() != null){
            node = node.getNextNode();
        }
        return node;
    }

    public ArrayList<T> getKeysArrayList(){
        ArrayList<T> keysArrayList = new ArrayList<>();
        GenericNode<T> node = headNode;
        while(node != null){
            keysArrayList.add(node.getData());
            node = node.getNextNode();
        }
        return keysArrayList;
    }
}

