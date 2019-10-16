package com.utte.growon.douban;

import com.utte.growon.kaiyan.category.CategoryData;
import com.utte.growon.kaiyan.category.CategoryItem;
import com.utte.growon.kaiyan.category.CategoryVideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by 江婷婷 on 2017/11/25.
 */

public class ParseWork {

    private static final String TAG = "ParseWork";

    public static void parseCategoryData(Response response) {
        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray("itemList");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i).getJSONObject("data");
                JSONObject header = item.getJSONObject("header");
                String categoryTitle = header.getString("title");
                String subTitle = header.getString("subTitle");
//                Log.d(TAG, categoryTitle + "\n" + subTitle);
                CategoryItem categoryItem = new CategoryItem(categoryTitle, subTitle);

                JSONArray itemList = item.getJSONArray("itemList");
                for (int j = 0; j < itemList.length(); j++) {
                    JSONObject data = itemList.getJSONObject(j).getJSONObject("data");
                    String title = data.getString("title");
                    JSONObject cover = data.getJSONObject("cover");
                    String feed = cover.getString("feed");
                    String playUrl = data.getString("playUrl");
                    long duration = data.getLong("duration");
                    String category = data.getString("category");
                    String description = "#" + category + " / " + transform(duration);
                    CategoryVideoItem categoryVideoItem = new CategoryVideoItem(title, description, feed, playUrl);
                    categoryItem.itemList.add(categoryVideoItem);
//                    Log.d(TAG, title + "\n" + description + "\n" + feed + "\n" + playUrl);
                }
                CategoryData.categoryItemList.add(categoryItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void parseTuChongCategoryData(Response response) {
//        JSONObject jsonObject = null;
//        try {
//
//            jsonObject = new JSONObject(response.body().string());
//            JSONArray jsonArray = jsonObject.getJSONArray("categories");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                String name = jsonArray.getJSONObject(i).getString("tag_name");
//                int id = jsonArray.getJSONObject(i).getInt("tag_id");
//                String url = "https://api.tuchong.com/discover/" + id + "/category";
//                CategoryUrl.categoryUrl.put(name, url);
//                Log.d(TAG, "parseTuChongCategoryData: " + url);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void parseTuChongDetailData(Response response) {
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(response.body().string());
//            JSONArray jsonArray = jsonObject.getJSONArray("post_list");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject object = jsonArray.getJSONObject(i);
//                String type = object.getString("type");
//                if (type.equals("text")) {
//                    String excerpt = object.getString("excerpt");
//                    String title = object.getString("title");
//                    int imageCount = object.getInt("image_count");
//                    JSONObject imgObject = object.getJSONObject("title_image");
//                    String titleImageUrl = imgObject.getString("url");
//                    int width = imgObject.getInt("width");
//                    int height = imgObject.getInt("height");
//                    String appUrl = object.getString("app_url");
//
//                    TuChongItemData.tuChongItems.add(new TuChongItem(type, new TuChongText(title, titleImageUrl)));
//
//
////                    Log.d(TAG, "text   " + title + " " + titleImageUrl);
//                } else if (type.equals("multi-photo")) {
//                    String excerpt = object.getString("excerpt");
//                    String title = object.getString("title");
//                    int imageCount = object.getInt("image_count");
//                    JSONArray array = object.getJSONArray("images");
//                    List<String> imgUrl = new ArrayList<>();
//                    for (int j = 0; j < array.length(); j++) {
//                        JSONObject o = array.getJSONObject(j);
//                        int width = o.getInt("width");
//                        int height = o.getInt("height");
//                        //https://photo.tuchong.com/ + user_id +/f/ + img_id
//                        String url = "https://photo.tuchong.com/" + o.getLong("user_id") + "/f/" + o.getLong("img_id") + ".jpg";
////                        Log.d(TAG, "image   " + title + " " + url);
//                        imgUrl.add(url);
//                    }
//
//                    TuChongItemData.tuChongItems.add(new TuChongItem(type, new TuChongPhoto(excerpt, title, imgUrl)));
//                }
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static String transform(long time) {
        String rtn;
        int s = (int) (time % 60);
        int m = (int) (time / 60 % 60);
        if (m < 10) {
            rtn = "0" + m + "\'";
        } else {
            rtn = m + "\'";
        }
        if (s < 10) {
            rtn = rtn + "0" + s + "\"";
        } else {
            rtn = rtn + s + "\"";
        }
        return rtn;
    }

}
