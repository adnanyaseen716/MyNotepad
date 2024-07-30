package pk.com.adnan.notepad;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

List <Note> notesList;

private List<Note> filteredNoteList;
private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    private    OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
    Context context;
    public NoteAdapter(List<Note> notesList , Context context) {
        this.notesList = notesList;

        this.filteredNoteList = notesList;

        this.context = context;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_view,null);
        return new NoteViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
       //-------  --------------------------------
        Note note = filteredNoteList.get(position);

        holder.tvNoteTitle.setText(note.getNoteTitle());
        holder.tvNoteDescription.setText(note.getNoteDescription());

    }

    @Override
    public int getItemCount() {
        //---------------------------------
        return filteredNoteList.size();
    }
//---------------------------------- SEARCH-------------------------------------------------
    private SpannableString highlightSearchText(String text , String query){
        SpannableString spannableString = new SpannableString(text);
        int index = text.toLowerCase().indexOf(query);
        while (index>=0){
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), index , index+query.length(),SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            index =  text.toLowerCase().indexOf(query,index+1);
        }

        return spannableString;
    }

    //------------------------- SEARCH --------------------------------------------------------------

    public Filter getFilter(){

        return new MyFilter();
    }

    private class MyFilter extends Filter{



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String query = constraint.toString().toLowerCase();
            List<Note> filteredList =  new ArrayList<>();
            for (Note note : notesList){
                if (note.getNoteTitle().toLowerCase().contains(query)){
                    filteredList.add(note);
                }
            }

            if (filteredList.isEmpty()){
                Toast.makeText(context, "No Record Found!", Toast.LENGTH_SHORT).show();
            }

            FilterResults results = new FilterResults();
            results.values= filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredNoteList = (List<Note>) results.values;
            notifyDataSetChanged();
        }
    }


    //------------------------- SEARCH --------------------------------------------------------------

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle , tvNoteDescription ;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteDescription = itemView.findViewById(R.id.tvNoteDescription);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null){
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null){
                        int position = getAdapterPosition();
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return false;
                }
            });

        }
    }

    interface OnItemClickListener {
        void onItemClick (int position);
    }


    interface OnItemLongClickListener {
        void onItemLongClick (int position);
    }

}
