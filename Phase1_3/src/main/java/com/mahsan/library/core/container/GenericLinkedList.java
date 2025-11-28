package com.mahsan.library.core.container;

import java.util.ArrayList;

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

    public String getStringList(){
        StringBuilder list = new StringBuilder();
        GenericNode<T> node = headNode;
        while(node != null){
            list.append(node).append("\n");
            node = node.getNextNode();
        }
        return list.toString();
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

