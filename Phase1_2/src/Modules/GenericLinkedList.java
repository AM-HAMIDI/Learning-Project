package Modules;

import java.util.Objects;

class GenericNode<T> {
    private T data;
    private GenericNode<T> nextNode;
    GenericNode(T data , GenericNode<T> nextNode){
        this.data = data;
        this.nextNode = nextNode;
    }

    T getData(){
        return data;
    }

    GenericNode<T> getNextNode(){
        return nextNode;
    }

    @Override
    public String toString(){
        return "(data : %s)".formatted(data);
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
    private int size = 0;

    public void insertNode(T data){

    }

    public void removeNode(){

    }

    public void printList(){
        GenericNode<T> node = headNode;
        while(node != null){
            System.out.println(node);
            node = node.getNextNode();
        }
    }
}

