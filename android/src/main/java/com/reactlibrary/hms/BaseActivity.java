
package com.reactlibrary.hms;

import com.reactlibrary.R;
import com.reactlibrary.hms.logger.LoggerActivity;

import android.app.FragmentTransaction;

public class BaseActivity extends LoggerActivity
{
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void addLogFragment() {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        final LogFragment fragment = new LogFragment();
        transaction.replace(R.id.framelog, fragment);
        transaction.commit();
    }
}
