package project.parallax.emarti.logic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.parallax.emarti.R;
import project.parallax.emarti.logic.models.BuildingCommitteeModel;

/**
 * Created by MohammadSommakia on 4/23/2018.
 */


public class BuildingCommitteeAdapter extends RecyclerView.Adapter<BuildingCommitteeAdapter.MyViewHolder> {

    private Context context;
    private List<BuildingCommitteeModel> modelList;

    public BuildingCommitteeAdapter(Context context, List<BuildingCommitteeModel> modelList) {

        this.context = context;
        this.modelList = modelList;
    }
    public void setClickListener(BuildingCommitteeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private BuildingCommitteeAdapter.BuildingCommitteeClickListener clickListener = null;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_single_layout, parent, false);
        BuildingCommitteeAdapter.MyViewHolder view = new BuildingCommitteeAdapter.MyViewHolder(itemView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(modelList.get(position).getImageProfile()).into(holder.userImage);
//        holder.userName.setText(modelList.get(position).getUserName());
        Log.e("hollll", modelList.get(position).getImageProfile());
        holder.userEmail.setText(modelList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void add(BuildingCommitteeModel model, int i) {
        modelList.add(model);
        notifyItemInserted(i);
    }

    public BuildingCommitteeModel get(int position) {
        return modelList.get(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView userImage;
        private TextView userName;
        private TextView userEmail;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            userImage = itemView.findViewById(R.id.user_single_image);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public interface BuildingCommitteeClickListener {
        public void itemClicked(View view, int position);
    }

}
