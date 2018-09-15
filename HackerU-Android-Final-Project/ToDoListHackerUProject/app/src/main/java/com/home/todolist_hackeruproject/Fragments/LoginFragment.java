package com.home.todolist_hackeruproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.todolist_hackeruproject.Activities.AlertableSwapListener;
import com.home.todolist_hackeruproject.Credentials.CredentialsManager;
import com.home.todolist_hackeruproject.Credentials.SuccessfulLoginListener;
import com.home.todolist_hackeruproject.R;


public class LoginFragment extends Fragment implements Alertable {

    EditText inputEmail, inputPassword;
    String email, password;
    View view;
    Context context;
    TextView textError;
    LinearLayout swapSignupLayout;
    RegisterFragment registerFragment;
    SuccessfulLoginListener loginListener;
    boolean isShowingErrorMessage;
    AlertableSwapListener alertableSwapListener;

    public void setLoginListener(SuccessfulLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setAlertableSwapListener(AlertableSwapListener listener){
        this.alertableSwapListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_login, container, false);
        context = view.getContext();
        Button actionBtn = view.findViewById(R.id.btn_login);
        inputEmail = view.findViewById(R.id.input_username);
        inputPassword = view.findViewById(R.id.input_password);
        textError = view.findViewById(R.id.textError);
        swapSignupLayout = view.findViewById(R.id.swapSignupLayout);


        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                login(email, password);
            }
        });

        swapSignupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerFragment == null) {
                    registerFragment = new RegisterFragment();
                    registerFragment.setLoginListener(loginListener);
                    registerFragment.setAlertableSwapListener(alertableSwapListener);
                }
                alertableSwapListener.onAlertableSwapped(registerFragment);
                getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.loginFragmentContainer, registerFragment).commit();
            }
        });


        return view;
    }

    public void login(String email, String password) {

        CredentialsManager credentialsHelper = CredentialsManager.getCredentialsManager();
        credentialsHelper.setLoginListener(loginListener);
        boolean toToggleError = false;
        String result = credentialsHelper.loginOrRegister(email, password,"login");
        if (!result.isEmpty())
            toToggleError = true;
        toggleAlertMessage(toToggleError, result);
    }

    @Override
    public void toggleAlertMessage(boolean shouldShowError, String messageToDisplay) {

        if (shouldShowError) {
            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);

            fadeIn.setDuration(300);
            fadeIn.setFillAfter(true);

            textError.startAnimation(fadeIn);
            textError.setText("\u26A0 " + messageToDisplay);
            isShowingErrorMessage = true;

            return;
        } else {

            if (isShowingErrorMessage) {
                AlphaAnimation fadeOut = new AlphaAnimation(textError.getAlpha(), 0.0f);

                fadeOut.setDuration(100);
                fadeOut.setFillAfter(true);

                textError.startAnimation(fadeOut);
                isShowingErrorMessage = false;
            }
        }
    }
}
