package com.andryyu.helper.sub.github;

/**
 * Created by Quinn on 7/24/15.
 * 网络问题抛出的异常
 */
public class GithubError extends Exception {

    public GithubError(String exceptionMessage) {
        super(exceptionMessage);
    }
}
