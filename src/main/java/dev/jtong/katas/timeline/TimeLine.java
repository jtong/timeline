package dev.jtong.katas.timeline;


import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


public class TimeLine <T extends TimeNodeData>{
    private List<TimeNode<T>> timeNodes = new ArrayList<>();
    private Map<String, Integer> tags = new HashMap<>();


    public void addNewVersion(TimeNode contentHistory) {
        this.timeNodes.add(contentHistory);
    }

    public void restore(int contentId) {
        TimeNode<T> contentHistory = this.timeNodes.get(contentId);
        TimeNode<T> duplicated = contentHistory.duplicate(Instant.now());
        this.timeNodes.add(duplicated);
    }

    public void tag(int indexInTimeLine, String tagName) {
        tags.put(tagName, indexInTimeLine);
    }

    public void restore(String tagName) {
        Integer indexInTimeLine = this.tags.get(tagName);
        restore(indexInTimeLine);
    }

    public TimeNode<T> getNewest() {
        return this.timeNodes.get(this.timeNodes.size() - 1);
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(this.tags.keySet().stream().collect(Collectors.toList()));
    }

    public List<TimeNode<T>> getHistories(){
        return Collections.unmodifiableList(timeNodes);
    }


    public boolean hasTag(String tagName) {
        return this.tags.containsKey(tagName);
    }
}

