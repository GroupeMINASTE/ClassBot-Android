package me.nathanfallet.classbot.sections;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import me.nathanfallet.classbot.R;
import me.nathanfallet.classbot.interfaces.HomeContainer;
import me.nathanfallet.classbot.views.HeaderCell;
import me.nathanfallet.classbot.views.LabelCell;

public class MoreSection extends Section {

    private HomeContainer container;

    public MoreSection(HomeContainer container) {
        super(SectionParameters.builder().itemViewWillBeProvided().headerViewWillBeProvided().build());

        this.container = container;
    }

    public int getContentItemsTotal() {
        return 3;
    }

    public View getItemView(ViewGroup parent) {
        return new LabelCell(parent.getContext());
    }

    public View getHeaderView(ViewGroup parent) {
        return new HeaderCell(parent.getContext());
    }

    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SectionedRecyclerViewAdapter.EmptyViewHolder itemHolder = (SectionedRecyclerViewAdapter.EmptyViewHolder) holder;

        // bind your view here
        if (itemHolder.itemView instanceof LabelCell) {
            if (position == 0) {
                ((LabelCell) itemHolder.itemView).with(R.string.configuration);
            } else if (position == 1) {
                ((LabelCell) itemHolder.itemView).with("Groupe MINASTE");
            } else {
                ((LabelCell) itemHolder.itemView).with(R.string.delta);
            }
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (position == 0) {
                        // Configuration
                        container.showConfiguration();
                    } else if (position == 1) {
                        // Groupe MINASTE
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.groupe-minaste.org/"));
                        itemHolder.itemView.getContext().startActivity(browserIntent);
                    } else {
                        // Delta
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=fr.zabricraft.delta"));
                        itemHolder.itemView.getContext().startActivity(browserIntent);
                    }
                }
            });
        }
    }

    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // Check if it's a headerCell
        if (view instanceof HeaderCell) {
            ((HeaderCell) view).with(R.string.more);
        }

        // return an empty instance of ViewHolder for the headers of this section
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

}
