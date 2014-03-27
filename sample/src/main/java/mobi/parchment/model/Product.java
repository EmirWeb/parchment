package mobi.parchment.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Emir Hasanbegovic on 2014-03-21.
 */
public class Product {

    private static final class Keys {
        public static final String ID = "id";
        public static final String IMAGE_THUMB_URL = "image_thumb_url";
        public static final String IMAGE_URL = "image_url";
        public static final String NAME = "name";
    }

    @SerializedName(Keys.ID)
    public Long mId;

    @SerializedName(Keys.IMAGE_THUMB_URL)
    public String mImageThumbUrl;

    @SerializedName(Keys.IMAGE_URL)
    public String mImageUrl;

    @SerializedName(Keys.NAME)
    public String mName;
}
