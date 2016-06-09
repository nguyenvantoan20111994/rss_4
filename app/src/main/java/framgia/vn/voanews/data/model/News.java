package framgia.vn.voanews.data.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by nghicv on 20/05/2016.
 */
public class News extends RealmObject {

    @Required
    private String mTitle;

    @Required
    private String mDescription;

    @Required
    private String mLink;

    private String mEnclosure;

    @Required
    private Date mDate;

    @Required
    private String mCategory;

    private String mAuthor;

    private boolean mIsViewed = false;

    public News(String title, String description, String link, String enclosure, Date date, String category, String author) {
        mTitle = title;
        mDescription = description;
        mLink = link;
        mEnclosure = enclosure;
        mDate = date;
        mCategory = category;
        mAuthor = author;
    }

    public News() {

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public String getEnclosure() {
        return mEnclosure;
    }

    public void setEnclosure(String mEnclosure) {
        this.mEnclosure = mEnclosure;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }


    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public void setIsViewed(boolean isViewed) {
        mIsViewed = isViewed;
    }

    public boolean isViewed() {
        return mIsViewed;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }
}
