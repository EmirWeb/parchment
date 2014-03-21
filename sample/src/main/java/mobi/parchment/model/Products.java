package mobi.parchment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-03-21.
 */
public class Products {

    private static final class Keys {
        public static final String RESULT = "result";
    }


    @SerializedName(Keys.RESULT)
    public Result mResult;

}
