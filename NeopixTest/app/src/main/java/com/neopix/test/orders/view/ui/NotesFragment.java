package com.neopix.test.orders.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.neopix.test.orders.R;
import com.neopix.test.orders.databinding.FragmentNotesBinding;
import com.neopix.test.orders.service.model.Notes;
import com.neopix.test.orders.view.adapter.NotesAdapter;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    public MutableLiveData<ArrayList<Notes>> notes = new MutableLiveData<>();
    public FragmentNotesBinding binding;
    public NotesAdapter notesAdapter;

    public NotesFragment(ArrayList<Notes> notes) {
        this.notes.setValue(notes);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false);

        if (notes.getValue().size() > 0) {

            notesAdapter = new NotesAdapter(getContext());
            notesAdapter.setNotesList(getContext(), this.notes.getValue());

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setAdapter(notesAdapter);

            binding.notesImage.setVisibility(View.GONE);
            binding.notesTitle.setVisibility(View.GONE);
            binding.notesText.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }
}
