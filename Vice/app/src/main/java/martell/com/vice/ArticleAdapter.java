package martell.com.vice;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import martell.com.vice.models.Article;

/**
 * Created by adao1 on 4/18/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> mArticles;
    private static final String TAG = "ArticleAdapter";
    public ArticleAdapter(List<Article> mArticles) {
        this.mArticles = mArticles;
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View articleView = inflater.inflate(R.layout.recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(articleView);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        TextView titleTextView = holder.titleTextView;
        TextView bodyTextView = holder.bodyTextView;
        titleTextView.setText(article.getArticleTitle());
        bodyTextView.setText(article.getArticleBody());
        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView bodyTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titleTextView = (TextView)itemView.findViewById(R.id.item_title);
            this.bodyTextView = (TextView)itemView.findViewById(R.id.item_body);
        }
    }
}
