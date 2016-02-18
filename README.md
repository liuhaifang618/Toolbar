# Toolbar
自定义toolbar
[连接](http:)
<img src='http://pica.nipic.com/2007-11-09/200711912453162_2.jpg' width="300px" style='border: #f1f1f1 solid 1px'/>


```
public interface PtrHandler {

    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view.
     * <p/>
     * {@link in.srain.cube.views.ptr.PtrDefaultHandler#checkContentCanBePulledDown}
     */
    public boolean checkCanDoRefresh(final PtrFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     *
     * @param frame
     */
    public void onRefreshBegin(final PtrFrameLayout frame);
}
```
