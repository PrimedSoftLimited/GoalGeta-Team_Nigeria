package com.android.goalgeta2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.goalgeta2.R;
import com.android.goalgeta2.activities.Dashboard;
import com.android.goalgeta2.activities.EditGoals;
import com.android.goalgeta2.api.RetrofitClient;
import com.android.goalgeta2.models.Goal;
import com.android.goalgeta2.models.GoalResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder> {

    private Context mCtx;
    private ArrayList<Goal> goals;
    public String token;
    public SharedPreferences preferences;
    private Goal mRecentlyDeletedItem;
    int mRecentlyDeletedItemPosition;

    public GoalsAdapter(Dashboard mCtx, ArrayList<Goal> goals) {
        this.mCtx = mCtx;
        this.goals = goals;
    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goals_model, viewGroup, false);
        return new GoalsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GoalsViewHolder goalsViewHolder, int i) {

        final Goal model = goals.get(i);

        goalsViewHolder.goalTitle.setText(goals.get(i).getTitle());
        goalsViewHolder.goalDescription.setText(goals.get(i).getDescription());
        goalsViewHolder.goalCompletion.setText(goals.get(i).getCompleted());
        goalsViewHolder.goalDueDate.setText(goals.get(i).getFinish());

        goalsViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
                alertDialog.setTitle("Delete or Edit Goal?");

//                Dialog buttons
                alertDialog.setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                id = (int) model.getId();
                                token = preferences.getString("token", "");
                                Call<GoalResponse> call = RetrofitClient.getInstance().getApi().goalsDelete("token", id);
                                call.enqueue(new Callback<GoalResponse>() {
                                    @Override
                                    public void onResponse(Call<GoalResponse> call, Response<GoalResponse> response) {
                                        Toast.makeText(mCtx, response.body().getData().getMessage(), Toast.LENGTH_LONG);
                                        goals.remove(model.getId());
                                        notifyDataSetChanged();
                                        notifyItemRemoved((int) model.getId());
                                    }

                                    @Override
                                    public void onFailure(Call<GoalResponse> call, Throwable t) {
                                        Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                final Intent intent = new Intent(mCtx, EditGoals.class);
                                intent.putExtra("token", token);
                                mCtx.startActivity(intent);
                                goalsViewHolder.goalTitle.setText("Edit Goal");
                            }
                        });
//                Create and show Alert dialog
                AlertDialog alertDialogCreate = alertDialog.create();
                alertDialogCreate.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public Context getmCtx() {
        return mCtx;
    }

    public void setmCtx(Context mCtx) {
        this.mCtx = mCtx;
    }


    public class GoalsViewHolder extends RecyclerView.ViewHolder {

        TextView goalTitle, goalDescription, goalDueDate, goalCompletion;

        public GoalsViewHolder(@NonNull View itemView) {
            super(itemView);
            goalTitle = itemView.findViewById(R.id.goal_title);
            goalDescription = itemView.findViewById(R.id.goal_description);
            goalDueDate = itemView.findViewById(R.id.complete_date);
            goalCompletion = itemView.findViewById(R.id.complete_unit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    on click behavior goes here
                }
            });

        }
    }

//    For swipe to delete function

//    public void deleteItem(int position) {
//        mRecentlyDeletedItem = goals.get(position);
//        mRecentlyDeletedItemPosition = position;
//        goals.remove(position);
//        notifyItemRemoved(position);
//        showUndoSnackbar();
//    }
//
//    private void showUndoSnackbar() {
//        View view = .findViewById(R.id.card_view);
//        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text,
//                Snackbar.LENGTH_LONG);
//        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GoalsAdapter.this.undoDelete();
//            }
//        });
//        snackbar.show();
//    }
//
//    private void undoDelete() {
//        goals.add(mRecentlyDeletedItemPosition,
//                mRecentlyDeletedItem);
//        notifyItemInserted(mRecentlyDeletedItemPosition);
//    }
}
