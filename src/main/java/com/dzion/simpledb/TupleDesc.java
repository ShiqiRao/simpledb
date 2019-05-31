package com.dzion.simpledb;

import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TupleDesc describes the schema of a tuple.
 * <p>
 * TupleDesc描述了元组的模式。
 */
public class TupleDesc implements Serializable {

    TDItem[] items;

    /**
     * A help class to facilitate organizing the information of each field
     */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         */
        Type fieldType;

        /**
         * The name of the field
         */
        String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        @Override
        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof TDItem)) {
                return false;
            }
            return ((TDItem) obj).fieldName.equals(this.fieldName)
                    && ((TDItem) obj).fieldType.equals(this.fieldType);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    private class SimpleArrayIterator implements Iterator<TDItem> {
        private int nextSlot = 0;

        @Override
        public boolean hasNext() {
            return nextSlot < items.length;
        }

        @Override
        public TDItem next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[nextSlot++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * @return An iterator which iterates over all the field TDItems
     * that are included in this TupleDesc
     */
    public Iterator<TDItem> iterator() {
        // some code goes here
        return new SimpleArrayIterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     *
     * @param typeAr  array specifying the number of and types of fields in this
     *                TupleDesc. It must contain at least one entry.
     * @param fieldAr array specifying the names of the fields. Note that names may
     *                be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        // some code goes here
        if (typeAr.length != fieldAr.length) {
            System.err.println("类型数组与字段名数组长度需要保持一致");
        }
        this.items = new TDItem[typeAr.length];
        for (int i = 0; i < typeAr.length; i++) {
            items[i] = new TDItem(typeAr[i], fieldAr[i]);
        }
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     *
     * @param typeAr array specifying the number of and types of fields in this
     *               TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // some code goes here
        this.items = new TDItem[typeAr.length];
        for (int i = 0; i < typeAr.length; i++) {
            items[i] = new TDItem(typeAr[i], "unamed" + i);
        }
    }

    public TupleDesc(TDItem[] items) {
        this.items = items;
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return items.length;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     *
     * @param i index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // some code goes here
        if (i >= items.length) {
            throw new NoSuchElementException();
        }
        return items[i].fieldName;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     *
     * @param i The index of the field to get the type of. It must be a valid
     *          index.
     * @return the type of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        // some code goes here
        if (i >= items.length) {
            throw new NoSuchElementException();
        }
        return items[i].fieldType;
    }

    /**
     * Find the index of the field with a given name.
     *
     * @param name name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here
        for (int i = 0; i < items.length; i++) {
            if (items[i].fieldName.equals(name)) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     * Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here
        int ret = 0;
        for (TDItem i : items) {
            ret += i.fieldType.getLen();
        }
        return ret;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     *
     * @param td1 The TupleDesc with the first fields of the new TupleDesc
     * @param td2 The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        // some code goes here
        TDItem[] itArr;
        TDItem[] itArr1 = td1.items;
        TDItem[] itArr2 = td2.items;
        int destPos = 0;
        int shortStartPos = itArr1.length;
        if (itArr2.length > itArr1.length) {
            itArr = itArr1;
            itArr1 = itArr2;
            itArr2 = itArr;
            destPos = itArr2.length;
            shortStartPos = 0;
        }
        TDItem[] its = new TDItem[itArr1.length + itArr2.length];
        System.arraycopy(itArr1, 0, its, destPos, itArr1.length);
        for (int i = 0; i < itArr2.length; i++) {
            TDItem it = itArr2[i];
            if (ArrayUtils.contains(itArr1, i)) {
                it.fieldName = it.fieldName + "1";
            }
            its[shortStartPos + i] = it;
        }
        return new TupleDesc(its);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     *
     * @param o the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    @Override
    public boolean equals(Object o) {
        // some code goes here
        if (!(o instanceof TupleDesc)) {
            return false;
        }
        return Arrays.equals(((TupleDesc) o).items, this.items);
    }

    @Override
    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        int ret = 17;
        ret = 31 * ret + Arrays.hashCode(items);
        return ret;
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     *
     * @return String describing this descriptor.
     */
    @Override
    public String toString() {
        return "TupleDesc{" +
                "items=" + Arrays.toString(items) +
                '}';
    }
}
