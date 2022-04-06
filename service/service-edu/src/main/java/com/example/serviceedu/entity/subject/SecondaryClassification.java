package com.example.serviceedu.entity.subject;

/**
 * com.example.serviceedu.entity.subject
 *
 * @author xzwnp
 * 2022/1/31
 * 11:07
 * Steps：
 */
public class SecondaryClassification {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SecondaryClassification() {
    }

    public SecondaryClassification(String id, String title) {

        this.id = id;
        this.title = title;
    }
}
