package pk.com.adnan.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>{
    List<FavouritesModel> favouritesList;

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public FavouritesAdapter(List<FavouritesModel> favouritesList) {
        this.favouritesList = favouritesList;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_list_view,null);
        return new FavouritesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {

        FavouritesModel favourites = favouritesList.get(position);
        holder.tvFavTitle.setText(favourites.getFavTitle());
        holder.tvFavDescription.setText(favourites.getFavDescription());

    }


    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    class FavouritesViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFavImage;
        TextView tvFavTitle , tvFavDescription , tvFavTime;
        public FavouritesViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFavTitle = itemView.findViewById(R.id.tvFavTitle);
            tvFavDescription = itemView.findViewById(R.id.tvFavDescription);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null){
                        int position = getAdapterPosition();
                        onItemLongClickListener.onLongClick(position);
                    }
                    return false;
                }
            });

        }
    }


    interface OnItemLongClickListener {
        void onLongClick(int position);
    }
}
