package mobi.parchment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mobi.parchment.model.Product;
import mobi.parchment.model.Products;

/**
 * Created by Emir Hasanbegovic on 2014-03-21.
 */
public class ProductsAdapter extends BaseAdapter {

    private List<Product> mProducts = new ArrayList<Product>();

    @Override
    public int getCount() {
        return Math.max(0,mProducts.size() - 2) ;
    }

    @Override
    public Object getItem(final int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private View getView(final Context context, final View convertView, final ViewGroup viewGroup) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            final View view = layoutInflater.inflate(R.layout.list_item_product, viewGroup, false);
            return view;
        }
        return convertView;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Context context = parent.getContext();
        final View view = getView(context, convertView, parent);
        final Product product = (Product) getItem(position);

        final ImageView imageView = (ImageView) view.findViewById(R.id.list_item_product_image_view);
        imageView.setImageBitmap(null);
        Picasso.with(context).load(product.mImageThumbUrl).into(imageView);

        final TextView textView= (TextView) view.findViewById(R.id.list_item_product_text_view);
        textView.setText(product.mName);
        return view;
    }

    public void setProducts(final List<Products> productList) {
        mProducts = new ArrayList<Product>();
        if (productList != null) {
            for (final Products products : productList) {
                final List<Product> result = products.mResult;
                if (result != null) {
                    for (final Product product : new ArrayList<Product>(result)) {
                        if (product.mImageThumbUrl == null) {
                            result.remove(product);
                        }
                    }
                    mProducts.addAll(result);
                }
            }
        }
        notifyDataSetChanged();
    }
}
