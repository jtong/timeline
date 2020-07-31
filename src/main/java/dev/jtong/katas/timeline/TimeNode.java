package dev.jtong.katas.timeline;

import java.time.Instant;

class TimeNode<T extends TimeNodeData> {
    private final Instant now;
    private Instant createdAt;
    private T data;

    public TimeNode(T data, Instant now) {
        this.data = data;
        this.now = now;
    }

    public TimeNode<T> duplicate(Instant now){
        return new TimeNode<T>(data, now);
    }

    public T getData() {
        return data;
    }


}
