package martell.com.vice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.List;

import martell.com.vice.models.Article;

/**
 * Created by adao1 on 4/18/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> mArticles;
    private static final String TAG = "ArticleAdapter";
    private ImageLoaderConfiguration config;
    private final OnRVItemClickListener listener;
    private final OnLastArticleShownListener lastArticleShownListener;
    private Context context;

    public interface OnRVItemClickListener {
        void onRVItemClick(Article article);
    }
    public interface OnLastArticleShownListener {
        void onLastArticleShown(int position);
    }

    public ArticleAdapter(List<Article> mArticles, OnRVItemClickListener listener, OnLastArticleShownListener lastArticleShownListener) {
        this.mArticles = mArticles;
        this.listener = listener;
        this.lastArticleShownListener = lastArticleShownListener;
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        config = new ImageLoaderConfiguration.Builder(parent.getContext()).build();
        View articleView = inflater.inflate(R.layout.recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(articleView);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        TextView titleTextView = holder.titleTextView;
        if (holder.previewTextView!=null){
            TextView previewTextView = holder.previewTextView;
            previewTextView.setText(Html.fromHtml(article.getArticlePreview()));
        }
        ImageView imageView = holder.imageView;
        titleTextView.setText(article.getArticleTitle());
//        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
//        imageLoader.init(config);
//        imageLoader.displayImage(article.getArticleThumbURL(), imageView);
        Picasso.with(context)
                .load(article.getArticleThumbURL())
                .into(imageView);
        holder.bind(mArticles.get(position), listener);

        if (position== mArticles.size()-1){
            lastArticleShownListener.onLastArticleShown(position);
        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView previewTextView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titleTextView = (TextView)itemView.findViewById(R.id.item_title);
            if (itemView.findViewById(R.id.item_body)!=null)this.previewTextView = (TextView)itemView.findViewById(R.id.item_body);
            this.imageView = (ImageView)itemView.findViewById(R.id.item_image);
        }
        public void bind(final Article article, final OnRVItemClickListener listener){
            //titleTextView.setText(article.getArticleTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRVItemClick(article);
                }
            });
        }
    }
}
