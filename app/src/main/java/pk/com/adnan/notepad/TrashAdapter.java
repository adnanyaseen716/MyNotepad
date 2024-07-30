package pk.com.adnan.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrashAdapter extends RecyclerView.Adapter<TrashAdapter.RecylebinViewHolder>{
   private List<TrashModel> recyclebinList;

    private OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
    public TrashAdapter(List<TrashModel> recyclebinList) {
        this.recyclebinList = recyclebinList;
    }

    @NonNull
    @Override
    public RecylebinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trash_list_view, null);
        return new RecylebinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylebinViewHolder holder, int position) {

        TrashModel trashModel = recyclebinList.get(position);

        holder.tvTrashTitle.setText(trashModel.getTrashTitle());
        holder.tvTrashDescription.setText(trashModel.getTrashDescription());

    }

    @Override
    public int getItemCount() {
        return recyclebinList.size();
    }

    class RecylebinViewHolder extends RecyclerView.ViewHolder {
       TextView tvTrashTitle , tvTrashDescription ;

       public RecylebinViewHolder(@NonNull View itemView) {
            super(itemView);

           tvTrashTitle = itemView.findViewById(R.id.tvTrashTitle);
           tvTrashDescription = itemView.findViewById(R.id.tvTrashDescription);



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



    interface OnItemLongClickListener {
        void onItemLongClick (int position);
    }

}
