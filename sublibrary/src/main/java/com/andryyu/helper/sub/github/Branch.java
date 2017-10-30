package com.andryyu.helper.sub.github;

import java.io.Serializable;

/**
 * Created by Quinn on 9/25/15.
 */
public class Branch implements Serializable {
    private static final long serialVersionUID = 4927461901146433920L;
    private String name;
    private Commit commit;

    public Branch() {
    }

    public String getName() {
        return this.name;
    }

    public Branch setName(String name) {
        this.name = name;
        return this;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "commit=" + commit +
                ", name='" + name + '\'' +
                '}';
    }
}
