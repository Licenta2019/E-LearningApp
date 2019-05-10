package learningapp.observer;

public interface Observable<T> {

    void add(Observer<T> obs);

    void delete(Observer<T> obs);

    void notify(T event);
}
