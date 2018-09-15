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


public class RegisterFragment extends Fragment implements Alertable {


    EditText inputEmail, inputPassword;
    String email, password;
    View view;
    Context context;
    TextView textError;
    LinearLayout swapLoginLayout;
    LoginFragment loginFragment;
    SuccessfulLoginListener loginListener;
    boolean isShowingErrorMessage;
    AlertableSwapListener alertableSwapListener;

    public void setAlertableSwapListener(AlertableSwapListener listener){
        this.alertableSwapListener = listener;
    }

    public void setEmail(String email) {
        this.email = email;
        inputEmail.setText(email);
    }

    public void setPassword(String password) {
        this.password = password;
        inputPassword.setText(password);
    }

    public void setLoginListener(SuccessfulLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_register, container, false);
        context = view.getContext();
        Button actionBtn = view.findViewById(R.id.btn_login);
        inputEmail = view.findViewById(R.id.input_username);
        inputPassword = view.findViewById(R.id.input_password);
        textError = view.findViewById(R.id.textError);
        swapLoginLayout = view.findViewById(R.id.swapLoginLayout);

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                register(email, password);
            }
        });

        swapLoginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginFragment == null) {
                    loginFragment = new LoginFragment();
                    loginFragment.setLoginListener(loginListener);
                    loginFragment.setAlertableSwapListener(alertableSwapListener);
                }
                alertableSwapListener.onAlertableSwapped(loginFragment);
                getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.loginFragmentContainer, loginFragment).commit();
            }
        });


        return view;
    }

    public void register(String email, String password) {

        CredentialsManager credentialsHelper = CredentialsManager.getCredentialsManager();
        credentialsHelper.setLoginListener(loginListener);
        boolean toToggleError = false;
        String result = credentialsHelper.loginOrRegister(email, password, "register");
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
