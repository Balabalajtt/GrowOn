package com.utte.growon.one.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/12/2.
 */

public class OneData {

    public static List<Essay> data = new ArrayList<>();
    public static OneContentData contentData;

    public static int toYear;
    public static int toMonth;

    public static int nowYear;
    public static int nowMonth;

    public static void last () {
        if (nowMonth > 1) {
            nowMonth--;
        } else {
            nowYear--;
            nowMonth = 12;
        }

    }

    public static String getDate() {

        Date date = new Date();
        OneData.toYear = date.getYear() + 1900;
        OneData.toMonth = date.getMonth() + 1;
        OneData.nowYear = OneData.toYear;
        OneData.nowMonth = OneData.toMonth;

        String rtn = nowYear + "-";
        if (nowMonth < 10) {
            rtn = rtn + "0" + nowMonth;
        } else {
            rtn = rtn + nowMonth;
        }
        rtn = rtn + "-01";

        return rtn;
    }

    public static class Essay {
        public String content_id;
        public String forward;
        public long id;
        public String img_url;
        public String pic_info;
        public String title;
        public String words_info;

//    public String hp_title;
//    public String hp_makettime;
//    public String guide_word;
//    public List<Author> author;

//    public class Author {
//        public String user_name;
//        public String summary;
//        public String web_url;
//    }
    }

    public static class OneContentData {
        public String hp_title;
        public String hp_author;
        public String hp_author_introduce;
        public String hp_content;
        public String guide_word;
        public String last_update_date;
    }

}



