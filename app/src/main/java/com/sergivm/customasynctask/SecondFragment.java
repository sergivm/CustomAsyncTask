package com.sergivm.customasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sergivm.customasynctask.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        mContext = getActivity().getApplicationContext();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAsyncTask customAsyncTask = new CustomAsyncTask() {
                    private ProgressDialog progressDialog;

                    @Override
                    public void onPreExecute() {
                        progressDialog = new ProgressDialog(getActivity(), R.style.ProgressDialog);
                        progressDialog.setMessage("Loading"); // TODO
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }

                    @Override
                    public void doInBackground() {
                        try {
                            Thread.sleep(2000);
                            NavHostFragment.findNavController(SecondFragment.this)
                                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onPostExecute() {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }

                    @Override
                    public void onFailed(Exception exception) {

                    }
                };
                customAsyncTask.execute();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}