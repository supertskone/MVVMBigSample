package com.neopix.test.orders.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.NotesItemBinding;
import com.neopix.test.orders.service.model.Notes;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

  private ArrayList<Notes> notes;
  private Context context;
  private NotesItemBinding notesBinding;

  public NotesAdapter(Context ctx) {
    context = ctx;
  }

  public void setNotesList(Context context, final ArrayList<Notes> notes) {
    this.context = context;
    if (this.notes == null) {
      this.notes = notes;
      notifyItemRangeInserted(0, notes.size());
    }
  }

  @Override
  public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
    notesBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.getContext()),
      R.layout.notes_item, parent, false);

    return new ViewHolder(notesBinding);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Notes dataModel = notes.get(position);
    holder.binding.setNotes(dataModel);
    holder.binding.executePendingBindings();

    //holder.bind(dataModel);
  }

  @Override
  public int getItemCount() {
    return this.notes.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public NotesItemBinding binding;

    public ViewHolder(NotesItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Object obj) {
      binding.setVariable(0, obj);
      binding.executePendingBindings();
    }
  }
}


