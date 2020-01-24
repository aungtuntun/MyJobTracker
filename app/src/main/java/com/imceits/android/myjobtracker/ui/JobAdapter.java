package com.imceits.android.myjobtracker.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.imceits.android.myjobtracker.data.Jobs;
import com.imceits.android.myjobtracker.databinding.JobItemBinding;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Jobs> dataList = new ArrayList<>();

    JobAdapter() {
    }

    void setDataList(List<Jobs> jobs) {
        dataList = jobs;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JobItemBinding itemBinding = JobItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new JobViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        holder.onBind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        JobItemBinding binding;
        JobViewHolder(@NonNull JobItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }

        void onBind(Jobs jobs) {
            binding.setJob(jobs);
            binding.executePendingBindings();
            JobsFragmentDirections.ActionJobsToJobDetail directions = JobsFragmentDirections.actionJobsToJobDetail(jobs);
            binding.btnAccept.setOnClickListener(Navigation.createNavigateOnClickListener(directions));
        }
    }
}
