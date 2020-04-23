package com.lagou.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String created;
    private String modified;
    private String categories;
    private String tags;
    @Column(name = "allow_coment")
    private String allowcoment;
    private String thumbnail;

    public Article() {
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.categories = categories;
        this.tags = tags;
        this.allowcoment = allowcoment;
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", categories='" + categories + '\'' +
                ", tags='" + tags + '\'' +
                ", allowcoment='" + allowcoment + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAllowcoment() {
        return allowcoment;
    }

    public void setAllowcoment(String allowcoment) {
        this.allowcoment = allowcoment;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
