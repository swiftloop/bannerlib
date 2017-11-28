package win.oscene.banners.bean;

import android.view.View;

import java.util.List;

/* *
 *Created by: Sorata 2017/11/27 0023 下午 3:37
 *
 */
public class BannerData {

    private List<View> views;


    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }


    public int getShowSize() {
        return views.size() - 2;
    }

    public int getViewSize() {
        return views.size();
    }
}
