package mobi.parchment;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

/**
 * Created by emir on 16/03/14.
 */
public abstract class BaseActivity extends Activity {
    private CountBaseAdapter mCountBaseAdapter = new CountBaseAdapter();
    public abstract AdapterView<?> getAdapterView();

    public CountBaseAdapter getCountBaseAdapter(){
        return mCountBaseAdapter;
    }

    public void add(final View view) {
        mCountBaseAdapter.add();
    }

    public void remove(final View view) {
        mCountBaseAdapter.remove();
    }

    public void addOne(final View view) {
        mCountBaseAdapter.addOne();
    }

    public void removeOne(final View view) {
        mCountBaseAdapter.removeOne();
    }

    public void select(final View view) {
        final EditText editText = (EditText) findViewById(R.id.select);
        final int selectedPosition = Integer.parseInt(editText.getText().toString());
        getAdapterView().setSelection(selectedPosition);
    }
}
