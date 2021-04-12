package my.e.lists;

public class Model {

    // Integers assigned to each layout
    public static final int TYPE_ONE = 0;
    public static final int TYPE_TWO = 1;

    // This variable specifies
    // which layout is expected in the given item
    private int viewType;

    //variable for a first layout
    private int iconFirstLayout;
    private String textFirstLayout;

    // variable for a second layout
    private String textSecondLayout;

    //constructor for a first layout
    public Model(int viewType, int iconFirstLayout, String textFirstLayout) {
        this.viewType = viewType;
        this.iconFirstLayout = iconFirstLayout;
        this.textFirstLayout = textFirstLayout;
    }

    // constructor for a second layout
    public Model(int viewType, String textSecondLayout) {
        this.viewType = viewType;
        this.textSecondLayout = textSecondLayout;
    }

    // getter and setter for vars first layout
    public int getIconFirstLayout() {
        return iconFirstLayout;
    }
    public void setIconFirstLayout(int iconFirstLayout) {
        this.iconFirstLayout = iconFirstLayout;
    }

    public String getTextFirstLayout() {
        return textFirstLayout;
    }

    public void setTextFirstLayout(String textFirstLayout) {
        this.textFirstLayout = textFirstLayout;
    }

    // getter and setter for vars second layout
    public String getTextSecondLayout() {
        return textSecondLayout;
    }

    public void setTextSecondLayout(String textSecondLayout) {
        this.textSecondLayout = textSecondLayout;
    }

    //getter and setter for viewType

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
