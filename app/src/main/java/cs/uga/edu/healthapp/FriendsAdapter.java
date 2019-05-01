package cs.uga.edu.healthapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>
{
    private Context mContext;
    private List<User> mUsers;

    public FriendsAdapter(Context mContext, List<User> mUsers)
    {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.friends_item, viewGroup, false);

        return new FriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        User user = mUsers.get(i);
        viewHolder.username.setText((user.getName()));

    }

    @Override
    public int getItemCount()
    {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
        }
    }

}
