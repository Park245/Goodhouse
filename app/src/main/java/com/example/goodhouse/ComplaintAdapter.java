package com.example.goodhouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder>{
    ArrayList<Complaint> list = new ArrayList<>();
    public void addList(Complaint c){
        list.add(c);
    }
    public void setList(ArrayList<Complaint> list){this.list = list;}

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.message_left_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Complaint item = list.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeText;//시간을 받음
        TextView content;//민원 내용을 받음

        public ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.datetimeText);
        }

        public void setItem(Complaint complaint){
            timeText.setText(complaint.time);
            content.setText("소음 종류 : "+complaint.complaintKind);
        }
    }
}
