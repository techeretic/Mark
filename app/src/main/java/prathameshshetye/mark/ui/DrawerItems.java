package prathameshshetye.mark.ui;

/**
 * Created by prathamesh on 5/1/15.
 */
public class DrawerItems {
    private String mTitle;
    private int mIcon;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public DrawerItems(String mTitle, int mIcon) {
        this.mTitle = mTitle;
        this.mIcon = mIcon;
    }

}
