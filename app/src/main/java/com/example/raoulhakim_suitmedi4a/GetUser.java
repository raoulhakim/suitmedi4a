package com.example.raoulhakim_suitmedi4a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetUser extends RecyclerView.Adapter<GetUser.ItemVH> {

    private Context context;
    private List<ListUser> users;
    private OnDataClickListener listener;

    public GetUser(Context context, List<ListUser> users, OnDataClickListener listener){
        this.context = context;
        this.users = users;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false);
        return new ItemVH(card);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {
        ListUser user = this.users.get(position);
        Picasso.get().load(user.getAvatar()).into(holder.imgviewProfile);
        holder.txtname1.setText(user.getFirstName());
        holder.txtname2.setText(user.getLastName());
        holder.txtEmail.setText(user.getEmail());

        holder.cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnDataClick(user.getFirstName(), user.getLastName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ItemVH extends RecyclerView.ViewHolder {
        private CardView cardUser;
        private CircleImageView imgviewProfile;
        private TextView txtname1;
        private TextView txtname2;
        private TextView txtEmail;

        public ItemVH(@NonNull View itemView) {
            super(itemView);
            this.imgviewProfile = (CircleImageView) itemView.findViewById(R.id.imgviewProfile);
            this.txtname1 = (TextView) itemView.findViewById(R.id.txtname1);
            this.txtname2 = (TextView) itemView.findViewById(R.id.txtname2);
            this.txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            this.cardUser = (CardView) itemView.findViewById(R.id.cardUser);
        }
    }
}
