package com.utte.growon.douban.model;

import java.util.List;

/**
 * Created by 江婷婷 on 2017/11/30.
 */

public class DoubanDetail {
    public Rating rating;
    public List<String> pubdate;
    public Pic pic;
    public String intro;
    public String year;
    public List<String> genres;
    public String title;
    public List<String> languages;
    public List<Person> actors;
    public List<String> durations;
    public List<String> countries;
    public String release_date;
    public List<Person> directors;

    public class Rating {
        public double star_count;
        public double value;
    }
    public class Pic {
        public String large;
        public String normal;
    }
    public class Person {
        public String name;
        public List<String> roles;
        public String cover_url;
    }
}
