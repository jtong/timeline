package dev.jtong.katas.timeline.model.impl;

import dev.jtong.katas.timeline.TimeNodeData;


public class ArticleContent implements TimeNodeData {
    private String title;
    private String content;

    public ArticleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public TimeNodeData duplicate() {
        return new ArticleContent(this.title, this.content);
    }

}
