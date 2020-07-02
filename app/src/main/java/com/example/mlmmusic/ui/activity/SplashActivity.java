package com.example.mlmmusic.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mlmmusic.R;
import com.example.mlmmusic.base.BaseActivity;
import com.example.mlmmusic.dialog.NormalAskDialog;
import com.example.mlmmusic.util.PermissionUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends BaseActivity implements PermissionUtil.OnRequestPermissionsResultCallbacks {

    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;

    // 声明一个数组，用来存储所有需要动态申请的权限
    private static final int REQUEST_CODE = 0;
    private final Map<String, Integer> permissionsMap = new HashMap<>();

    public SplashActivity() {
        // 定位权限等获取到配置再看是否要请求，
        // 照相
        permissionsMap.put(Manifest.permission.CAMERA, R.string.permission_photo);
        // 麦克风
        permissionsMap.put(Manifest.permission.RECORD_AUDIO, R.string.permission_microphone);
        // 手机状态
        permissionsMap.put(Manifest.permission.READ_PHONE_STATE, R.string.permission_phone_status);
        // 存储权限
        permissionsMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, R.string.permission_storage);
        permissionsMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.permission_storage);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //请求定位以外的权限，
        boolean hasall = requestPermissions();
        if (hasall) {
            toHomeActivity();
        }
    }
    private boolean requestPermissions() {
        return PermissionUtil.requestPermissions(this, REQUEST_CODE, permissionsMap.keySet().toArray(new String[]{}));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted) {
        if (isAllGranted) {// 请求权限返回 已全部授权,做你想做的配置呀，初始化，或者splashactivity必须要请求权限后的处理
            toHomeActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {// 设置 手动开启权限 返回 再次判断是否获取全部权限
            boolean hasall = requestPermissions();
            if (hasall) {
                toHomeActivity();
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied) {
        if (perms != null && perms.size() > 0) {
            // 用set去重，有的复数权限
            Set<String> tipSet = new HashSet<>();
            for (int i = 0; i < perms.size(); i++) {
                tipSet.add(getString(permissionsMap.get(perms.get(i))));
            }
            String tipS = TextUtils.join(", ", tipSet);
            boolean onceAgain = PermissionUtil.deniedRequestPermissionsAgain(this, perms.toArray(new String[perms.size()]));
            NormalAskDialog normalAskDialog = new NormalAskDialog(this);
            normalAskDialog.show();

            if (onceAgain) {// 部分 || 所有权限被拒绝且选择了（选择了不再询问 || 部分机型默认为不在询问）


                normalAskDialog.setData("需要开启" + tipS + "权限才能使用此功能", "退出", "设置", false);
                normalAskDialog.setOnActionListener(new NormalAskDialog.OnActionListener() {
                    @Override
                    public void onLeftAction() {
                        SplashActivity.this.finish();
                    }

                    @Override
                    public void onRightAction() {
                        //引导用户到设置中去进行设置
                        PermissionUtil.startApplicationDetailsSettings(SplashActivity.this, REQUEST_CODE);
                    }
                });
            } else {// 部分 || 所有权限被拒绝
                normalAskDialog.setData("需要开启" + tipS + "权限才能使用此功能", "退出", "设置", false);
                normalAskDialog.setOnActionListener(new NormalAskDialog.OnActionListener() {
                    @Override
                    public void onLeftAction() {
                        SplashActivity.this.finish();
                    }

                    @Override
                    public void onRightAction() {
                        //引导用户到设置中去进行设置
                        PermissionUtil.startApplicationDetailsSettings(SplashActivity.this, REQUEST_CODE);
                    }
                });
            }
        } else {
            toHomeActivity();
        }
    }


    private void toHomeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startSystemActivity(SplashActivity.this, MainActivity.class);
                startSystemActivity(SplashActivity.this, QueryActivity.class);
//                startSystemActivity(SplashActivity.this, PlayActivity.class);
//                SplashActivity.this.finish();
            }
        }, 500);

    }

    @OnClick(R.id.tv_version_code)
    public void onViewClicked() {
        toHomeActivity();
    }


}




/*
package com.example.mlmmusic.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mlmmusic.MainActivity;
import com.example.mlmmusic.R;
import com.example.mlmmusic.base.BaseActivity;
import com.example.mlmmusic.dialog.NormalAskDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends BaseActivity {

    private static final int REQUEST_SDCARD_CODE = 0x0002;
    private static final int SET_REQUESTCODE = 0x0003;  //跳转到设置权限界面的请求与回调标示
    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;

    private boolean hasrefuse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //动态申请权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED//读取存储卡权限
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_SDCARD_CODE);
            } else {
                toHomeActivity();
            }
        } else {
            toHomeActivity();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SDCARD_CODE) {
            if (hasAllPermissionsGranted(grantResults)) { //权限全部申请好了
                toHomeActivity();
            } else {
                NormalAskDialog normalAskDialog = new NormalAskDialog(this);
                normalAskDialog.show();
                normalAskDialog.setData("需要开启权限才能使用此功能", "退出", "设置", false);
                normalAskDialog.setOnActionListener(new NormalAskDialog.OnActionListener() {
                    @Override
                    public void onLeftAction() {
                        SplashActivity.this.finish();
                    }

                    @Override
                    public void onRightAction() {
                        //引导用户到设置中去进行设置
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, SET_REQUESTCODE);
                    }
                });

                //当用户拒绝并不再提示 会有bug
//                NormalAskDialog normalAskDialog = new NormalAskDialog(this);
//                normalAskDialog.show();
//                normalAskDialog.setData("请同意应用相关权限", "退出", "好的", false);
//                normalAskDialog.setOnActionListener(new NormalAskDialog.OnActionListener() {
//                    @Override
//                    public void onLeftAction() {
//                        SplashActivity.this.finish();
//                    }
//
//                    @Override
//                    public void onRightAction() {
//                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_SDCARD_CODE);
//                    }
//                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_REQUESTCODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED//读取存储卡权限
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_SDCARD_CODE);
                } else {
                    toHomeActivity();
                }
            } else {
                toHomeActivity();
            }
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    private void toHomeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startSystemActivity(SplashActivity.this, MainActivity.class);
                startSystemActivity(SplashActivity.this, QueryActivity.class);
//                startSystemActivity(SplashActivity.this, PlayActivity.class);
//                SplashActivity.this.finish();
            }
        }, 500);

    }

    @OnClick(R.id.tv_version_code)
    public void onViewClicked() {
        toHomeActivity();
    }
}
*/
