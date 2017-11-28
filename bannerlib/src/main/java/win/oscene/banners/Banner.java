package win.oscene.banners;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import win.oscene.banners.bean.BannerData;
import win.oscene.banners.config.BannerConfig;
import win.oscene.banners.listener.SelectedPageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* *
 *Created by: Sorata 2017/11/27 0023 下午 3:20
 *
 */
public class Banner extends BannerViewPager {

    private static final String tag = "Banner";

    private ScheduledExecutorService executorService;
    private BannerData bannerData;
    private PagerAdapter adapter;
    private SelectedPageListener selectedPageListener;
    private int currentPosition = 0; //当前的位置
    private BannerConfig.ModelType modelType = BannerConfig.getCurrentModel(); //ViewPager的模式
    private boolean isAutoPlay = false;
    private int playTime = 3000;
    private boolean isPlaying = false;
    private Build build;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        bannerData = new BannerData();
        this.addOnPageChangeListener(new PageChangeListener());
    }


    /**
     * 初始化方法
     *
     * @param views viewList
     * @return Banner
     */
    public Banner init(List<View> views) {
        bannerData.setViews(views);
        adapter = new BannerAdapter(bannerData);
        this.setAdapter(adapter);
        if (modelType == BannerConfig.ModelType.Infinite) {
            //如果是轮播模式 需要满足一定条件
            if (views.size() < 4) {  //最少四个View  在List中的位置为：  2 1 2 1  最低的要求
                modelType = BannerConfig.ModelType.Normal;
                Log.w(tag, "启用无限轮播模式失败,页面数量小于等于1，自动切换到默认模式。");
            } else {
                this.setCurrentItem(currentPosition + 1);
            }
        }
        this.setFocusable(true);
        return this;
    }


    /**
     * 更新viewpager 内容
     *
     * @param views viewList
     */
    public void update(List<View> views) {
        if (isPlaying) {
            stop();
        }
        bannerData.getViews().clear();
        List<View> list = new ArrayList<>();
        list.addAll(views);
        init(list);
        if (modelType == BannerConfig.ModelType.Infinite) {
            if (views.size() < 4) {
                modelType = BannerConfig.ModelType.Normal;
                sendMessage(0);
                this.setCurrentItem(0);
                isAutoPlay = false;
                Log.w(tag, "更新数据后，启用无限轮播模式失败,页面数量小于等于1，自动切换到默认模式。");
            } else {
                sendMessage(1);
                this.setCurrentItem(1);
                isAutoPlay = true;
            }
        } else {
            sendMessage(0);
            this.setCurrentItem(0);
            isAutoPlay = false;
        }
    }


    /**
     * 是否开启自动轮播
     *
     * @param bool true 开启
     * @return 构造器
     */
    public Build autoPlay(boolean bool) {
        if (modelType == BannerConfig.ModelType.Normal) return build;
        isAutoPlay = bool;
        build = new Build(playTime);
        return build;
    }


    /**
     * 开始轮播
     */
    public void start() {
        if (isAutoPlay) {
            isPlaying = true;
            if (executorService != null) {
                executorService.shutdown();
                executorService = null;
            }
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(task, build.delay, build.delay, TimeUnit.MILLISECONDS);
        }
    }


    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            currentPosition = currentPosition % bannerData.getShowSize() + 1;
            if (currentPosition == 1) handler.sendEmptyMessage(1);
            else if (currentPosition == bannerData.getShowSize())
                handler.sendEmptyMessage(bannerData.getShowSize());
            else handler.sendEmptyMessage(currentPosition);
        }
    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Banner.this.setCurrentItem(msg.what, false);
            return false;
        }
    });


    /**
     * 使用Handler  的postDelayed 方法 来实现轮播
     */
    private final Runnable mission = new Runnable() {
        @Override
        public void run() {
            currentPosition = currentPosition % bannerData.getShowSize() + 1;
            if (currentPosition == 1) {
                Banner.this.setCurrentItem(1, false);
                handler.postDelayed(this, playTime);
            } else if (currentPosition == bannerData.getShowSize()) {
                Banner.this.setCurrentItem(bannerData.getShowSize(), false);
                handler.postDelayed(this, playTime);
            } else {
                Banner.this.setCurrentItem(currentPosition, false);
                handler.postDelayed(this, playTime);
            }
        }
    };


    /**
     * 结束轮播
     */
    public void stop() {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
        isPlaying = false;
        //  handler.removeCallbacks(task);
    }


    public void setSelectedPageListener(SelectedPageListener selectedPageListener) {
        this.selectedPageListener = selectedPageListener;
        if (modelType == BannerConfig.ModelType.Normal) sendMessage(0);
        else sendMessage(1);
    }


    /**
     * 设置当前Viewpager的模式
     *
     * @param modelType 执行模式
     * @see win.oscene.banners.config.BannerConfig.ModelType
     */
    public void setModelType(BannerConfig.ModelType modelType) {
        this.modelType = modelType;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        //用户手指触摸时停止自动轮播  当离开时恢复自动轮播
        //单点触摸以及多点触摸停止自动轮播
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            stop();
        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_OUTSIDE || action == MotionEvent.ACTION_POINTER_UP) {
            start();
        }
        return super.dispatchTouchEvent(ev);
    }


    class PageChangeListener extends SimpleOnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //跳转完成调用此方法
            currentPosition = position;
            sendMessage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            if (modelType == BannerConfig.ModelType.Infinite) {
                //        若viewpager滑动未停止，直接返回
                if (state != SCROLL_STATE_IDLE) return;
                if (currentPosition == 0) {
                    Banner.this.setCurrentItem(bannerData.getShowSize(), false);
                } else if (currentPosition > bannerData.getShowSize()) {
                    Banner.this.setCurrentItem(1, false);
                }
            }


        }
    }


    /**
     * 监听viewpager当前的view
     *
     * @param position 当前位置 从1开始
     */
    private void sendMessage(int position) {
        if (modelType == BannerConfig.ModelType.Normal) {
            if (null != selectedPageListener)
                //下标从1开始  返回的总显示数为传入的List的大小
                selectedPageListener.selected(position + 1, bannerData.getViewSize(), bannerData.getViews().get(position));
        } else if (modelType == BannerConfig.ModelType.Infinite) {
            if (position != 0 && position <= bannerData.getShowSize()) { //忽视过度的页面
                if (null != selectedPageListener)
                    //下标从1开始
                    selectedPageListener.selected(position, bannerData.getShowSize(), bannerData.getViews().get(position));
            }
        }
    }


    /**
     * 构造器
     */
    public class Build {
        private int delay;

        Build(int delayTime) {
            this.delay = delayTime;
        }

        public Build setDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public void build() {
            if (isAutoPlay) {
                start();
                modelType = BannerConfig.ModelType.Infinite;
            }

        }
    }


}
