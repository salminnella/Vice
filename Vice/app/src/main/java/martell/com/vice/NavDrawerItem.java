package martell.com.vice;

/**
 * Created by mstarace on 4/18/16.
 */
public class NavDrawerItem extends NavDrawerEntry {

    private String title;

    public NavDrawerItem(String title) {
        this.setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }
}
