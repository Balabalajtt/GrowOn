package com.utte.growon.one.entity;


import java.util.List;

public class OneHomePageData {

    public String id;
    public List<Content_list> content_list;
    public Menu menu;

    public class Content_list {
        public String id;
        public String category;
        public int display_category;
        public String item_id;
        public String title;
        public String forward;
        public String img_url;
        public int like_count;
        public String post_date;
        public String last_update_date;
        public Author author;
        public String video_url;
        public String audio_url;
        public int audio_platform;
        public String start_video;
        public int has_reading;
        public String volume;
        public String pic_info;
        public String words_info;
        public String subtitle;
        public int number;
        public int serial_id;
        public List<String> serial_list;
        public int movie_story_id;
        public int ad_id;
        public int ad_type;
        public String ad_pvurl;
        public String ad_linkurl;
        public String ad_makettime;
        public String ad_closetime;
        public String ad_share_cnt;
        public String ad_pvurl_vendor;
        public String content_id;
        public String content_type;
        public String content_bgcolor;
        public String share_url;
//        public Share_info share_info;
//        public Share_list share_list;
        public List<Tag> tag_list;
//        public String orientation;
    }

    public class Author {
        public String user_id;
        public String user_name;
        public String desc;
        public String wb_name;
        public String is_settled;
        public String settled_type;
        public String summary;
        public String fans_total;
        public String web_url;
    }

    public class DataList {
        private String content_type;
        private String content_id;
        private String title;
        private Tag tag;
    }

    public class Menu {
        private String vol;
        private List<DataList> list;
    }

    public class Tag {
        private String id;
        private String title;
    }
}
