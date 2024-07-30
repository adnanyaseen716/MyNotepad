package pk.com.adnan.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapterGrid extends RecyclerView.Adapter<NoteAdapterGrid.NoteViewHolder> {

    List <Note> notesList;

    public NoteAdapterGrid(List<Note> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_grid_view,null);
        return new NoteViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.tvGridTitle.setText(note.getNoteTitle());
        holder.tvGridDescription.setText(note.getNoteDescription());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView tvGridTitle , tvGridDescription , tvNoteTime;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGridTitle = itemView.findViewById(R.id.tvGridTitle);
            tvGridDescription = itemView.findViewById(R.id.tvGridDescription);

        }



    }



}
