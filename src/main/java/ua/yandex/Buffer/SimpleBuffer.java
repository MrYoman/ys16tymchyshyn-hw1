
package ua.yandex.Buffer;

public interface SimpleBuffer<TypeOfElements> {
    void put(TypeOfElements element);
    TypeOfElements get();
    
    int size();
    int capacity();
    
    boolean isEmpty();
    boolean isFull();
}
