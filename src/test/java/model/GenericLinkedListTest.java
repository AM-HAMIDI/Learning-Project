package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mahsan.library.model.*;

import java.util.ArrayList;

public class GenericLinkedListTest {
    private GenericLinkedList<String> genericLinkedList;

    @BeforeEach
    public void setUp() {
        genericLinkedList = new GenericLinkedList<>();
    }

    // ------------- Insert tests -------------
    @Test
    public void testSingleInsert() {
        assertTrue(genericLinkedList.isEmpty());
        genericLinkedList.insert("hi");
        assertFalse(genericLinkedList.isEmpty());
        assertEquals("hi", genericLinkedList.getHeadNode().getData());
    }

    @Test
    public void testMultipleInsert() {
        genericLinkedList.insert("element1");
        genericLinkedList.insert("element2");
        genericLinkedList.insert("element3");
        ArrayList<String> keysArrayList = getKeysArrayList(genericLinkedList);
        assertEquals(3, keysArrayList.size());
        assertEquals("element1", keysArrayList.get(0));
        assertEquals("element2", keysArrayList.get(1));
        assertEquals("element3", keysArrayList.get(2));
    }

    // ------------- Remove tests -------------
    @Test
    public void testRemoveFromEmptyList() {
        assertTrue(genericLinkedList.isEmpty());
        genericLinkedList.removeByKey("hi");
        assertTrue(genericLinkedList.isEmpty());
    }

    @Test
    public void testRemoveNonExistingElement() {
        genericLinkedList.insert("element1");
        genericLinkedList.insert("element2");
        genericLinkedList.removeByKey("element3");
        assertEquals(2, genericLinkedList.getSize());
        assertEquals("element1", genericLinkedList.getHeadNode().getData());
        assertEquals("element2", genericLinkedList.getHeadNode().getNextNode().getData());
    }

    @Test
    public void testRemoveSingleElement_1() {
        genericLinkedList.insert("element1");
        assertEquals(1, genericLinkedList.getSize());
        genericLinkedList.removeByKey("element1");
        assertTrue(genericLinkedList.isEmpty());
    }

    @Test
    public void testRemoveSingleElement_2() {
        genericLinkedList.insert("element1");
        genericLinkedList.insert("element2");
        genericLinkedList.insert("element3");
        genericLinkedList.insert("element1");
        genericLinkedList.insert("element2");

        genericLinkedList.removeByKey("element1");

        ArrayList<String> keysArrayList = getKeysArrayList(genericLinkedList);

        assertEquals(3, genericLinkedList.getSize());
        assertEquals(3, keysArrayList.size());
        assertEquals("element2", keysArrayList.get(0));
        assertEquals("element3", keysArrayList.get(1));
        assertEquals("element2", keysArrayList.get(2));
    }

    @Test
    public void testRemoveMultipleElements() {
        genericLinkedList.insert("element1");
        genericLinkedList.insert("element1");
        genericLinkedList.insert("element2");
        genericLinkedList.insert("element1");
        genericLinkedList.insert("element3");
        genericLinkedList.insert("element4");

        genericLinkedList.removeByKey("element1");
        ArrayList<String> keysArrayList = getKeysArrayList(genericLinkedList);
        assertEquals("element2", keysArrayList.get(0));
        assertEquals("element3", keysArrayList.get(1));
        assertEquals("element4", keysArrayList.get(2));

        genericLinkedList.removeByKey("element3");
        keysArrayList = getKeysArrayList(genericLinkedList);
        assertEquals(2, keysArrayList.size());
        assertEquals("element2", keysArrayList.get(0));
        assertEquals("element4", keysArrayList.get(1));
    }

    public ArrayList<String> getKeysArrayList(GenericLinkedList<String> linkList) {
        ArrayList<String> arrayList = new ArrayList<>();
        GenericNode<String> node = linkList.getHeadNode();
        ;
        while (node != null) {
            arrayList.add(node.getData());
            node = node.getNextNode();
        }
        return arrayList;
    }
}
