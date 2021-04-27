package org.cchao.test.model;

import java.util.List;

/**
 * Created by shucc on 17/12/7.
 * cc@cchao.org
 */
public class CommentModel {

    private String content;

    private List<String> images;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
