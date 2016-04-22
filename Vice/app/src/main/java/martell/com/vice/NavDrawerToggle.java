package martell.com.vice;

/**
 * Represents a Toggle switch in the nav drawer layout
 * superclass NavDrawer
 * Created by mstarace on 4/18/16.
 */
public class NavDrawerToggle extends NavDrawerEntry{
    private String title;
    private boolean checked;

    public NavDrawerToggle(String title) {
        this.setTitle(title);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

}
