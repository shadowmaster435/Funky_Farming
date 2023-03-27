package shadowmaster435.funkyfarming.util;

import java.util.ArrayList;

public class ScrollableList<E> extends ArrayList<E> {

    private final ArrayList<E> holder;

    private final int size;

    public ScrollableList(int size, E defaultVal) {
        this.size = size;
        ArrayList<E> temp = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            temp.add(defaultVal);
        }
        this.holder = temp;
    }

    public ArrayList<E> asList() {
        return holder;
    }

    public ScrollableList(ArrayList<E> list) {
        this.holder = list;
        this.size = list.size();
    }
    public int getSize() {
        return size;
    }

    public void set(E obj, int index) {
        holder.set(index, obj);
    }

    public E get(int index) {
        return holder.get(index);
    }

    public int indexOf(Object obj) {
        return holder.indexOf(obj);
    }

    public void addScroll(E obj) {
        this.holder.add(this.size - 1, obj);
        this.holder.remove(0);
    }



}
