package com.example.eddie.rippleview;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.eddie.rippleview.view.CircleImageView;
import com.example.eddie.rippleview.view.RippleView;

public class MainActivity extends AppCompatActivity {

    private static final int Nou = 1;

    private RippleView mMainRv;
    private CircleImageView mMainCiv;
    private SoundPool sp;
    private int music;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == Nou) {
                handler.sendEmptyMessageDelayed(Nou, 5000);
                sp.play(music, 1, 1, 0, 0, 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music = sp.load(this, R.raw.hongbao_gq, 1);
        mMainRv = (RippleView) findViewById(R.id.rv_main);
        mMainCiv = (CircleImageView) findViewById(R.id.civ_main);
        mMainCiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mMainRv.isStarting()){
                    //如果动画正在运行就停止，否则就继续执行
                    mMainRv.stop();
                    //结束进程
                    handler.removeMessages(Nou);
                }else{
                    // 执行动画
                    mMainRv.start();
                    handler.sendEmptyMessage(Nou);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(Nou);
    }
}
