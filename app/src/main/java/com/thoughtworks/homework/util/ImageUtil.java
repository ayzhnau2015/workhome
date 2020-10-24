package com.thoughtworks.homework.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 *
 * @author zhuyaan
 * @since  2020-10-20
 */
public class ImageUtil {
    /**
     * 加载圆角图片
     *
     * @param context   Context
     * @param imageUrl  url
     * @param imageView ImageView
     * @param placeId   缺省图
     */
    public static void loadRoundedCorner(Context context, String imageUrl, ImageView imageView, int placeId) {
        if(TextUtils.isEmpty(imageUrl)){
            return ;
        }
        String newUrl = imageUrl;
        if(imageUrl.startsWith("http://")){
            newUrl = imageUrl.replace("http://","https://");
        }
        //设置图片圆角角度
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(35)).placeholder(placeId).error(placeId);
        Glide.with(context).load(newUrl).apply(options.diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context   Context
     * @param imageUrl  url
     * @param imageView ImageView
     * @param placeId   缺省图
     */
    public static void load(Context context, String imageUrl, ImageView imageView, int placeId) {
        if(TextUtils.isEmpty(imageUrl)){
            return ;
        }
        String newUrl = imageUrl;
        if(imageUrl.startsWith("http://")){
            newUrl = imageUrl.replace("http://","https://");
        }
        Glide.with(context).load(newUrl).apply(getPlaceErrorCenter(placeId).diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
    }

    private static RequestOptions getPlaceErrorCenter(int errorResId) {
        return new RequestOptions().placeholder(errorResId).error(errorResId).centerCrop();
    }
}
