package mobi.parchment;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.AdapterView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mobi.parchment.model.Products;

/**
 * Created by emir on 16/03/14.
 */
public abstract class BaseActivity extends Activity {
    private ProductsAdapter mProductsAdapter = new ProductsAdapter();

    public abstract AdapterView<?> getAdapterView();

    private static final Gson GSON = new Gson();
    private static final String PRODUCTS_URL = "http://lcboapi.com/products?page=%d";

    public ProductsAdapter getProductsAdapter() {
        return mProductsAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ProductsAsyncTask productsAsyncTask = new ProductsAsyncTask();
        productsAsyncTask.execute((String[]) null);
    }

    private class ProductsAsyncTask extends AsyncTask<String, Integer, List<Products>> {

        private static final int SIZE = 10;

        @Override
        protected List<Products> doInBackground(String... params) {

            final List<Products> products = new ArrayList<Products>(SIZE);
            for (int page = 0; page < SIZE; page++){
                products.add(getProducts(page));
            }

            return products;

        }

        private Products getProducts(final int page) {
            final HttpClient httpclient = new DefaultHttpClient();
            final String url = String.format(PRODUCTS_URL, page);
            final HttpGet httpget = new HttpGet(url);

            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            try {
                final HttpResponse response = httpclient.execute(httpget);
                final HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                inputStreamReader = new InputStreamReader(inputStream);
                final Products products = GSON.fromJson(inputStreamReader, Products.class);
                return products;

            } catch (final Exception exception) {
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (final IOException ioException) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (final IOException ioException) {
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Products> products) {
            if (products == null) {
                return;
            }

            mProductsAdapter.setProducts(products);
        }
    }


}
