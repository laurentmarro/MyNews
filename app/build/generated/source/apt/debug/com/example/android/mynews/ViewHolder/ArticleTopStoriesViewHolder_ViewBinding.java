// Generated code from Butter Knife. Do not modify!
package com.example.android.mynews.ViewHolder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.android.mynews.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArticleTopStoriesViewHolder_ViewBinding implements Unbinder {
  private ArticleTopStoriesViewHolder target;

  @UiThread
  public ArticleTopStoriesViewHolder_ViewBinding(ArticleTopStoriesViewHolder target, View source) {
    this.target = target;

    target.sectionandsubsection = Utils.findRequiredViewAsType(source, R.id.sectionandsubsection, "field 'sectionandsubsection'", TextView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.description, "field 'description'", TextView.class);
    target.articleimage = Utils.findRequiredViewAsType(source, R.id.articleimage, "field 'articleimage'", ImageView.class);
    target.article_date = Utils.findRequiredViewAsType(source, R.id.date, "field 'article_date'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ArticleTopStoriesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sectionandsubsection = null;
    target.description = null;
    target.articleimage = null;
    target.article_date = null;
  }
}
