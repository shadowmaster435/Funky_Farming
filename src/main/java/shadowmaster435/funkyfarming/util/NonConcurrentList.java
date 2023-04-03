package shadowmaster435.funkyfarming.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class NonConcurrentList<E> extends ArrayList<E> {



    public void removeIndex(int index) {
        boolean co = this.contains(this.get(index));
        this.removeIf(e -> co);
    }

}
