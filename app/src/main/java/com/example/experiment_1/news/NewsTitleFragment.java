package com.example.experiment_1.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experiment_1.R;
import com.example.experiment_1.model.News;

import java.util.List;

/**
 * @author ylqq
 */
public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.news_title_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //双页模式
        isTwoPane = requireActivity().findViewById(R.id.news_content_layout) != null;
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private final List<News> newsList;

        public NewsAdapter(List<News> news) {
            newsList = news;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            //绑定view到viewHolder
            final ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(v -> {
                News news = newsList.get(viewHolder.getAdapterPosition());
                if (isTwoPane) {
                    //如果是双页模式
                    assert getFragmentManager() != null;
                    NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                    //刷新newsContentFragment中的内容
                    assert newsContentFragment != null;
                    newsContentFragment.refresh(news.getTitle(), news.getContent());
                } else {
                    //actionStart的作用为传递信息，起到一个刷新，预加载
                    NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news = newsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }
    }
}
