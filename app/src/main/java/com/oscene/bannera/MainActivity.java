package com.oscene.bannera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import win.oscene.banners.Banner;
import win.oscene.banners.listener.SelectedPageListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<View> aList = new ArrayList<>(3);
        List<View> aList2 = new ArrayList<>(3);
        ImageView image1 = new ImageView(this);
        image1.setBackgroundResource(R.mipmap.timg);
        image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image2 = new ImageView(this);
        image2.setBackgroundResource(R.mipmap.timg2);
        image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image3 = new ImageView(this);
        image3.setBackgroundResource(R.mipmap.timg3);
        image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image4 = new ImageView(this);
        image4.setBackgroundResource(R.mipmap.timg);
        image4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image5 = new ImageView(this);
        image5.setBackgroundResource(R.mipmap.timg3);
        image5.setScaleType(ImageView.ScaleType.CENTER_CROP);


        ImageView image6 = new ImageView(this);
        image6.setBackgroundResource(R.mipmap.timg2);
        image6.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image7 = new ImageView(this);
        image7.setBackgroundResource(R.mipmap.timg3);
        image7.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image8 = new ImageView(this);
        image8.setBackgroundResource(R.mipmap.timg4);
        image8.setScaleType(ImageView.ScaleType.CENTER_CROP);


        ImageView image9 = new ImageView(this);
        image9.setBackgroundResource(R.mipmap.timg4);
        image9.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image10 = new ImageView(this);
        image10.setBackgroundResource(R.mipmap.timg2);
        image10.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView image11 = new ImageView(this);
        image11.setBackgroundResource(R.mipmap.timg3);
        image11.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final List<View> aList3 = new ArrayList<>(3);

        aList3.add(image9);
        aList3.add(image10);
        aList3.add(image11);


        aList.add(image6);
        aList.add(image7);
        aList.add(image8);


        aList2.add(image5);
        aList2.add(image1);
        aList2.add(image2);
        aList2.add(image3);
        aList2.add(image4);


        final Banner banner = (Banner) findViewById(R.id.banner);
        banner.init(aList2).autoPlay(true).setDelay(800).build();
        banner.setSelectedPageListener(new SelectedPageListener() {
            @Override
            public void selected(final int position, int size, final View view) {
                System.out.println("position ===>" + position + "   size :    ===>" + size + "   view:   " + view.getClass().getName());
                System.out.println("选择的位置是1：" + position);
                final int index = position;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("选择的位置是2：" + index);
                        if (index == 1)
                            //  banner.update(aList);
                            banner.start();
                        else {
                            banner.stop();
                            banner.update(aList3);
                        }
                    }
                });

            }
        });

    }


}
