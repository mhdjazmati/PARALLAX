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
import project.parallax.emarti.logic.models.ApartmentModel;

/**
 * Created by MohammadSommakia on 4/12/2018.
 */

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.MyViewHolder> {

    private final Context context;
    List<ApartmentModel> modelList;
    private ApartmentClickListener clickListener = null;

    int i = 0;
    private int start = 6;
    private int end = 11;


    public ApartmentAdapter(Context context, List<ApartmentModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    public void setClickListener(ApartmentClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public void add(ApartmentModel model, int i) {
        modelList.add(model);
        notifyItemInserted(i);
    }
    public ApartmentModel get(int i) {
        return modelList.get(i);
    }

    public void remove(int i) {
        modelList.remove(i);
        notifyItemRemoved(i);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.apparment_card, parent, false);
        MyViewHolder view = new MyViewHolder(itemView);

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Glide.with(context).load(modelList.get(position).getApartmentPhotoUrl()).into(holder.apartmentPhoto);
        holder.floorNumber.setText(modelList.get(position).getFloorNumber());
        holder.buildingName.setText(modelList.get(position).getBuildingName());

////        if ((getItemCount() - 1) - holder.getAdapterPosition() < 1) {
////            String s = String.valueOf(start);
////            String e = String.valueOf(end);
////            apartmentDatabase.orderByKey().startAt(String.valueOf(5)).endAt("10").addListenerForSingleValueEvent(new ValueEventListener() {
////                @Override
////                public void onDataChange(DataSnapshot dataSnapshot) {
////                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
////                        i++;
////                        ApartmentModel apartmentModel = postSnapshot.getValue(ApartmentModel.class);
////                        add(apartmentModel, getItemCount() + i);
////                    }
////                    Log.e("datasnap",dataSnapshot.toString());
////                    Log.e("datasnap",getItemCount()+"");
////                    Log.e("datasnap", holder.getAdapterPosition()+"");
////                    start += 5;
////                    end += 5;
////                }
////
////                @Override
////                public void onCancelled(DatabaseError databaseError) {
////
////                }
////            });
//        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView buildingName;
        TextView floorNumber;
        ImageView apartmentPhoto;

        public MyViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            buildingName = view.findViewById(R.id.building_name_value);
            apartmentPhoto = view.findViewById(R.id.apartment_photo);
            floorNumber = view.findViewById(R.id.floor_number_value);
        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }
    public interface ApartmentClickListener {
        public void itemClicked(View view, int position);
    }
}

