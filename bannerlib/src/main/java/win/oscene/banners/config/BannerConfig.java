package win.oscene.banners.config;

/* *
 *Created by: Sorata 2017/11/27 0023 下午 3:15
 *
 */
public class BannerConfig {

    private static ModelType currentModel = ModelType.Infinite;


    public static ModelType getCurrentModel() {
        return currentModel;
    }

    public static void setCurrentModel(ModelType currentModel) {
        BannerConfig.currentModel = currentModel;
    }

    public enum ModelType {
        Normal(0), //默认的PagerView 配置
        Infinite(1);//无限轮播模式  （无限轮播模式要求：一. View的数量大于1     二. View需要配置ViewList为：  3 1 2 3 1  （表示1,2,3三张图，在list中的位置  实际的List大小为5））
        private int model;

        ModelType(int model) {
            this.model = model;
        }

        public int getModel() {
            return model;
        }
    }


}
