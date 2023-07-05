package com.example.nftime_simple_backoffice_seoul_illust_fair;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity implements WorksListAdapter.OnWorksListListener {
    private TextView tvAddress;
    private TextView tvArtist;
    private TextView tvStatus;

    private BottomSheetDialog bottomSheetDialog;
    private WorkInfoStore workInfoStore;

    private WorkInfo selectedWorkInfo;

    private boolean isMinted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        workInfoStore = new WorkInfoStore();

        tvAddress = findViewById(R.id.tv_address);
        tvAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= tvAddress.getRight() - tvAddress.getTotalPaddingRight()) {
                        tvAddress.setText("");

                        return true;
                    }
                }
                return false;
            }
        });

        TextView tvQR = findViewById(R.id.tv_qr);
        tvQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanOptions options = new ScanOptions();
                options.setOrientationLocked(true);
                barcodeLauncher.launch(options);
            }
        });

        tvArtist = findViewById(R.id.tv_artist);
        tvArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        tvStatus = findViewById(R.id.tv_status);
        Button btnMint = findViewById(R.id.btn_mint);
        btnMint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMinted){
                    if(tvAddress.getText() == null){
                        Toast.makeText(v.getContext(), "Address is null", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(tvAddress.getText().equals("")){
                        Toast.makeText(v.getContext(), "Address is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(tvArtist.getText() == null){
                        Toast.makeText(v.getContext(), "Artist is null", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(tvArtist.getText().equals("")){
                        Toast.makeText(v.getContext(), "Artist is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    isMinted = true;
                    tvAddress.setEnabled(false);
                    btnMint.setBackgroundColor(Color.GRAY);
                    btnMint.setText("Clear");

                    new MintWithWorkIdTask(v.getContext(), new MintWithWorkIdTask.AsyncResponse<Integer>() {
                        @Override
                        public void onAsyncSuccess(Integer result) {
                            if(result == 200 || result == 201){
                                Toast.makeText(v.getContext(), "Minting Succeed", Toast.LENGTH_SHORT);
                                tvStatus.setTextColor(Color.BLUE);
                                tvStatus.setText("민팅에 성공하였습니다. HTTPCode: " + String.valueOf(result));
                            }
                            else {
                                Toast.makeText(v.getContext(), "Minting Failed", Toast.LENGTH_SHORT);
                                tvStatus.setTextColor(Color.RED);
                                tvStatus.setText("민팅에 실패하였습니다. HTTPCode: " + String.valueOf(result));
                            }
                        }
                    }).execute(
                            String.valueOf(selectedWorkInfo.getWorkId()),
                            tvAddress.getText().toString());
                }
                else {
                    isMinted = false;
                    tvAddress.setEnabled(true);
                    btnMint.setBackgroundColor(Color.BLACK);
                    btnMint.setText("NFT 발행하기");

                    tvAddress.setText("");
                    tvArtist.setText("");
                    tvStatus.setText("");
                }
            }
        });
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                    tvAddress.setText(result.getContents());
                }
            });

    private void showBottomSheetDialog(){
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);

        RecyclerView rvWorks = bottomSheetDialog.findViewById(R.id.rv_works);
        WorksListAdapter adapter = new WorksListAdapter(this, new WorksListAdapter.WorksDiff());
        rvWorks.setAdapter(adapter);
        adapter.submitList(workInfoStore.getWorkInfoList());

        ImageView ivClose = bottomSheetDialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });

        View v = (View) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        v.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        BottomSheetBehavior behavior = BottomSheetBehavior.from(v);
        behavior.setPeekHeight(getResources().getDisplayMetrics().heightPixels);
        behavior.setDraggable(false);

        bottomSheetDialog.show();
    }

    @Override
    public void onWorkClicked(WorkInfo workInfo) {
        selectedWorkInfo = workInfo;
        String fullText = selectedWorkInfo.getArtistName() + " | " + selectedWorkInfo.getWorkName();
        tvArtist.setText(fullText);
        bottomSheetDialog.cancel();
    }
}