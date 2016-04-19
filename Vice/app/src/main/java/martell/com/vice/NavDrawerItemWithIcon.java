package martell.com.vice;

/**
 * Created by mstarace on 4/18/16.
 */
public class NavDrawerItemWithIcon extends NavDrawerEntry {
    private String title;
    private int iconId;

    public NavDrawerItemWithIcon(String title, int iconId) {
        this.setTitle(title);
        this.setIconId(iconId);
    }

    public int getIconId() {
        return iconId;
    }

    private void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }
}
