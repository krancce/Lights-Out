public class QueueImplementation<E> implements Queue<E> {

    private static class Elem<T> {

        private T value;
        private Elem<T> next;

        private Elem(T value, Elem<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Elem<E> front;
    private Elem<E> rear;

    public E peek() {
        if(isEmpty()){
            throw new IllegalStateException("Cannot call peek on an empty queue");
        }
        return front.value;
    }

    public void enqueue(E value) {
        if(value == null){
            throw new NullPointerException("Cannot enqueue a null value");
        }
       
        Elem<E> newElem;
        newElem = new Elem<E>(value, null );

        if (rear == null) {
            front = rear = newElem;
        } else {
            rear.next = newElem;
            rear = newElem;
        }
    }

    public E dequeue() {
        if(isEmpty()){
            throw new IllegalStateException("Cannot call dequeue on an empty queue");
        }
        E result = front.value;
        if (front != null & front.next == null) {
            front = rear = null;
        } else {
            front = front.next;
        }
        return result;
    }

    public boolean isEmpty() {
        return front == null;
    }

}
