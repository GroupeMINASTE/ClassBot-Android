package me.nathanfallet.classbot.sections;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import me.nathanfallet.classbot.R;
import me.nathanfallet.classbot.interfaces.HomeContainer;
import me.nathanfallet.classbot.models.Devoirs;
import me.nathanfallet.classbot.views.DevoirsCell;
import me.nathanfallet.classbot.views.HeaderCell;

public class DevoirsSection extends Section {

    private HomeContainer container;

    public DevoirsSection(HomeContainer container) {
        super(SectionParameters.builder().itemViewWillBeProvided().headerViewWillBeProvided().build());

        this.container = container;
    }

    public int getContentItemsTotal() {
        return container.getDevoirs().length;
    }

    public View getItemView(ViewGroup parent) {
        return new DevoirsCell(parent.getContext());
    }

    public View getHeaderView(ViewGroup parent) {
        return new HeaderCell(parent.getContext());
    }

    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionedRecyclerViewAdapter.EmptyViewHolder itemHolder = (SectionedRecyclerViewAdapter.EmptyViewHolder) holder;
        final Devoirs devoirs = container.getDevoirs()[position];

        // bind your view here
        if (itemHolder.itemView instanceof DevoirsCell) {
            ((DevoirsCell) itemHolder.itemView).with(devoirs);
        }
    }

    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // Check if it's a headerCell
        if (view instanceof HeaderCell) {
            ((HeaderCell) view).with(R.string.devoirs);
        }

        // return an empty instance of ViewHolder for the headers of this section
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

}
