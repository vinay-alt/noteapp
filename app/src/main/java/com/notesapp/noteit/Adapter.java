package com.notesapp.noteit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {

    Context context;
    Activity activity;
    List<model> noteslist;
    List<model> newlist;

    public Adapter(Context context, Activity activity, List<model> noteslist) {
        this.context = context;
        this.activity = activity;
        this.noteslist = noteslist;
        newlist = new ArrayList<model>(noteslist);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.text.setText(noteslist.get(position).getTitle());
        holder.desc.setText(noteslist.get(position).getDesc());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNotesActivity.class);
                intent.putExtra("title", noteslist.get(position).getTitle());
                intent.putExtra("desc", noteslist.get(position).getDesc());
                intent.putExtra("id", noteslist.get(position).getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<model> filteredList = new ArrayList<model>();
            if (constraint==null||constraint.length()==0) {
                filteredList.addAll(newlist);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (model item:newlist)
                {
                    if (item.getTitle().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteslist.clear();
            noteslist.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text, desc;
        RelativeLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.note_title);
            desc =itemView.findViewById(R.id.desc);
            layout = itemView.findViewById(R.id.note_layout);
        }
    }

    public List<model> getList() {
        return noteslist;
    }

    public void removeItem(int position) {
        noteslist.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(model item, int position) {
        noteslist.add(position, item);
        notifyItemInserted(position);
    }

}
