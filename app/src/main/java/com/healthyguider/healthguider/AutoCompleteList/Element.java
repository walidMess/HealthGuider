package com.healthyguider.healthguider.AutoCompleteList;

/**
 * Created by wezzy on 30/04/2018.
 */

public class Element {

    private String Option;
    private String Option_type;
    private int id;

    public Element(String id,String Option, String Option_type) {
        this.Option = Option;
        this.Option_type = Option_type;
        this.id=Integer.parseInt(id);

    }

    public String getOption() {
        return Option;
    }

    public void setOption(String option) {
        Option = option;
    }

    public String getOption_type() {
        return Option_type;
    }

    public void setOption_type(String option_type) {
        Option_type = option_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
