// Generated code from Butter Knife. Do not modify!
package com.example.android.mynews.Controllers.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.android.mynews.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MostPopularFragment_ViewBinding implements Unbinder {
  private MostPopularFragment target;

  @UiThread
  public MostPopularFragment_ViewBinding(MostPopularFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.mostpopular_recycler_view, "field 'recyclerView'", RecyclerView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.mostpopular_swipe_container, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MostPopularFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.swipeRefreshLayout = null;
  }
}
