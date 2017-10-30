package com.android.andryyu.lifehelper.entity.douban;


/**
 * Created by quantan.liu on 2017/3/28.
 */

public class PersonBean {
    private String alt;

    // 导演或演员
    private String type;

    private HotMovieBean.SubjectsBean.ImagesBean avatars;
    private String name;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HotMovieBean.SubjectsBean.ImagesBean getAvatars() {
        return avatars;
    }

    public void setAvatars(HotMovieBean.SubjectsBean.ImagesBean avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "alt='" + alt + '\'' +
                ", type='" + type + '\'' +
                ", avatars=" + avatars +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
