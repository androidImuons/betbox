package com.lotus.model;

import com.models.BaseModel;

/**
 * @author Manish Kumar
 * @since 9/6/18
 */

public class HeadlineModel extends BaseModel {

    String Marquee;

    public String getMarquee() {
        return getValidString(Marquee);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof HeadlineModel) {
            return ((HeadlineModel) obj).getMarquee().equals(getMarquee());
        }
        return false;
    }
}
