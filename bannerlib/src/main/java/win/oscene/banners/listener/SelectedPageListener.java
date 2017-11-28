package win.oscene.banners.listener;

import android.view.View;

/* *
 *Created by: Sorata 2017/11/28 0024 上午 10:13
 *
 */
public interface SelectedPageListener {


    /**
     * 监听ViewPager页面的加载
     *
     * @param position 当前显示的View的位置 从 1开始
     * @param size     显示给用户看到的View的大小 当模式为Normal时，返回的是传入的List 的大小  当为Infinite时 返回的是传入的List的大小减2
     * @param view     当前展示的View对象
     */
    void selected(final int position, int size, View view);
}
