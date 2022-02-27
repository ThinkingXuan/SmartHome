package com.nuist.you.smarthome.ui.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.bean.MessageEvent;
import com.nuist.you.smarthome.bean.SensorData;
import com.nuist.you.smarthome.bean.SensorDataBean;
import com.nuist.you.smarthome.http.MyWebSocket;
import com.nuist.you.smarthome.http.two.WsManager;
import com.nuist.you.smarthome.util.GsonUtil;
import com.nuist.you.smarthome.util.SharedPreUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MyFragment extends Fragment {

    private static final String TAG = MyFragment.class.getSimpleName();
    private MyViewModel myViewModel;
    private Button mButton;
    private EditText mEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myViewModel =
                ViewModelProviders.of(this).get(MyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        final TextView textView = root.findViewById(R.id.text_my);
        myViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        initview(root);
        return root;
    }


    void initview(View view) {
        mButton = view.findViewById(R.id.button_my);
        mEditText = view.findViewById(R.id.editText_my);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreUtil.removeString(getContext(), "userPhone");
                SharedPreUtil.removeString(getContext(), "userPassword");
                SharedPreUtil.removeString(getContext(), "userIpAddress");
                getActivity().finish();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
