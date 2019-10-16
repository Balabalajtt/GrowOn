package com.utte.growon.mono.model;

import java.util.List;

public class Meow {

    public int bang_count;
    public boolean is_folded;
    public int kind;
    public String share_text;
    public Group group;
    public String text;
    public String title;
    public String description;
    public Thumb thumb;
    public List<Pic> pics;
    public List<Pic> images;
    public String banner_img_url;
    public boolean is_external_link;
    public String share_img;
    public String exposure_url;
    public String meow_status;
    public int object_type;
    public long id;
    public int comment_count;
    public long create_time;
    public User user;
    public int is_filtered;
    public int meow_type;
    public Category category;
    public int is_post_by_master;
    public String rec_url;

    public class Pic {
        public String raw;
    }
}