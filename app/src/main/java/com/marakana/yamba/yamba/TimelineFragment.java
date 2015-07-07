package com.marakana.yamba.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;


public class TimelineFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = TimelineFragment.class.getSimpleName(); //
    private static final String[] FROM = { StatusContract.Column.USER,
            StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT,
            StatusContract.Column.CREATED_AT }; //
    private static final int[] TO = { R.id.list_item_text_user,
            R.id.list_item_text_message, R.id.list_item_text_created_at, R.id.list_item_text_created_at};
    private SimpleCursorAdapter mAdapter; //
    private static final int LOADER_ID = 42;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { //
        super.onActivityCreated(savedInstanceState);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
                null, FROM, TO, 0); //
        setListAdapter(mAdapter); //
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    // --- Loader Callbacks ---
// Executed on a non-UI thread
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) { //
        if (id != LOADER_ID)
            return null;
        Log.d(TAG, "onCreateLoader");
        return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
                null, null, null, StatusContract.DEFAULT_SORT); //
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) { //
        Log.d(TAG, "onLoadFinished with cursor: " + cursor.getCount());
        mAdapter.swapCursor(cursor); //
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) { //
        mAdapter.swapCursor(null);
    }
}
