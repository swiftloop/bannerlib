# bannerlib
Android Banner  ViewPager通用组件

#使用方法

```java
    
        final Banner banner = (Banner) findViewById(R.id.banner);
        banner.init(aList2).autoPlay(true).setDelay(800).build();
        banner.setSelectedPageListener(new SelectedPageListener() {
            @Override
            public void selected(final int position, int size, final View view) {
                //todo
            }
        });      
        
```
###模式
|Enum|说明|
|---|---|
|Noraml|默认无无限轮播且有界
|Infinite|无限轮播模式，无界