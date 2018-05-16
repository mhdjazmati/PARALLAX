package project.parallax.emarti.logic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.models.BuildingModel;

/**
 * Created by MohammadSommakia on 4/19/2018.
 */

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.MyViewHolder> {

    private final Context context;
    List<BuildingModel> modelList;
    private BuildingClickListener clickListener = null;

    public BuildingAdapter(Context context, List<BuildingModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    public void add(BuildingModel model, int i) {
        modelList.add(model);
        notifyItemInserted(i);
    }

    public void setClickListener(BuildingClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BuildingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.building_card, parent, false);
        BuildingAdapter.MyViewHolder view = new BuildingAdapter.MyViewHolder(itemView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(modelList.get(position).getBuildingPhotoUrl()).into(holder.buildingPhoto);
        holder.numberOfFloors.setText(modelList.get(position).getNumberOfFloors());
        holder.buildingName.setText(modelList.get(position).getBuildingName());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public BuildingModel get(int position) {
        return modelList.get(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView buildingName;
        TextView numberOfFloors;
        ImageView buildingPhoto;
        ImageView checked;

        public MyViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            buildingName = view.findViewById(R.id.building_name_value);
            buildingPhoto = view.findViewById(R.id.apartment_photo);
            numberOfFloors = view.findViewById(R.id.number_of_floors_value);
            checked = view.findViewById(R.id.checked);
        }

        @Override
        public void onClick(View v) {

            if (modelList != null) {
                boolean a = modelList.get(getAdapterPosition()).getIsChecked();
                modelList.get(getAdapterPosition()).setChecked(!a);
                checked.setVisibility(checked.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

            }
        }
    }


    public interface BuildingClickListener {
        public void itemClicked(View view, int position);
    }
}
