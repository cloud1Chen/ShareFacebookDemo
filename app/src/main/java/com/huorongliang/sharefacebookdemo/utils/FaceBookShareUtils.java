package com.huorongliang.sharefacebookdemo.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by huorong.liang on 2016/12/30.
 */

public class FaceBookShareUtils {
    private Activity mActivity;
    private ShareDialog shareDialog;
    private CallbackManager callBackManager;
    public static final int SHARE_REQUEST_CODE = 10010;
    private ShareLinkContent.Builder shareLinkContentBuilder;

    public FaceBookShareUtils(Activity activity, CallbackManager callBackManager, FacebookCallback facebookCallback) {
        this.mActivity = activity;
        this.callBackManager = callBackManager;
        shareDialog = new ShareDialog(mActivity);
        //注册分享状态监听回调接口
        shareDialog.registerCallback(callBackManager, facebookCallback, FaceBookShareUtils.SHARE_REQUEST_CODE);
        shareLinkContentBuilder = new ShareLinkContent.Builder();
    }

    /**
     * 分享
     */
    public void shareLink(String contentTitle, String imageUrl, String contentUrl) {


        shareLinkContentBuilder
                .setContentTitle(contentTitle)//链接中的标题
                .setImageUrl(Uri.parse(imageUrl))//将在帖子中显示的缩略图的网址
                .setContentDescription("这是一个很好的网站，欢迎大家来吐槽")
//                .setQuote("尔曹身与名俱灭")
                .setContentUrl(Uri.parse(contentUrl));//要分享的链接


        ShareLinkContent shareLinkContent = shareLinkContentBuilder.build();

        if (shareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.show(mActivity, shareLinkContent);
        }
    }

    /**
     * 分享
     */
    public void sharePhoto(String contentTitle, Bitmap image, String desc) {

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)

                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        if (shareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.show(mActivity, content);
        }

        //        SharePhoto photo = new SharePhoto.Builder()
        //                .setBitmap(imageUrl)
        //                .setCaption(desc)
        //                .build();
        //
        //        SharePhotoContent content = new SharePhotoContent.Builder()
        //                .addPhoto(photo)
        ////                .setContentUrl(Uri.parse(Api.SHARE_LEFT_URL))
        //                .build();
        //
        //        ShareApi.share(content, null);
        //        if(shareDialog.canShow(SharePhotoContent.class)) {
        //            shareDialog.show(mActivity,content);
        //        }


    }
}

