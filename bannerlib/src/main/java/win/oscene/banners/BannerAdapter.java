package win.oscene.banners;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import win.oscene.banners.bean.BannerData;

/* *
 *Created by: Sorata 2017/11/27 0023 下午 3:21
 *
 */
public class BannerAdapter extends PagerAdapter {


    private BannerData bannerData;

    public BannerAdapter(BannerData bannerData) {
        this.bannerData = bannerData;
    }


    @Override
    public int getCount() {
        return bannerData.getViews().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(bannerData.getViews().get(position));
        return bannerData.getViews().get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



}
