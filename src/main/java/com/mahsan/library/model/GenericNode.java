package com.mahsan.library.model;

import java.util.Objects;

public class GenericNode<T> {
    private T data;
    private GenericNode<T> nextNode;

    GenericNode(T data, GenericNode<T> nextNode) {
        this.data = data;
        this.nextNode = nextNode;
    }

    public T getData() {
        return data;
    }

    public GenericNode<T> getNextNode() {
        return nextNode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNextNode(GenericNode<T> nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass())
            return false;
        else if (object == this)
            return true;
        else {
            GenericNode<?> node = (GenericNode<?>) object;
            return Objects.equals(node.getData(), data) &&
                    Objects.equals(node.getNextNode(), nextNode);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, nextNode);
    }
}