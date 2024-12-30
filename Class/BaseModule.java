package src.Class;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseModule<T extends BaseEntity> {
    protected List<T> items;

    public BaseModule() {
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public List<T> getItems() {
        return items;
    }
}
