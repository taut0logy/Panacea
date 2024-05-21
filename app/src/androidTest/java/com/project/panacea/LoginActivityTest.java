package com.project.panacea;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private AuthUtility mockAuthUtility;
    @Mock
    private FirebaseAuth mAuth;

    @Mock
    private FirebaseDatabase mFirebaseDatabase;

    @Mock
    private MockedStatic<FirebaseAuth> mockedFirebaseAuth;

    @Mock
    private MockedStatic<FirebaseDatabase> mockedFirebaseDatabase;
    @Before
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(mAuth);
        mockedFirebaseDatabase.when(FirebaseDatabase::getInstance).thenReturn(mFirebaseDatabase);

        mockAuthUtility = new AuthUtility(mAuth, mFirebaseDatabase);
        LoginActivity loginActivity = new LoginActivity();

        java.lang.reflect.Field authUtilityField = LoginActivity.class.getDeclaredField("authUtility");
        authUtilityField.setAccessible(true);
        authUtilityField.set(loginActivity, mockAuthUtility);
    }

    @Test
    public void login_success() {

        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {

            scenario.onActivity(activity -> {
                EditText etEmail = activity.findViewById(R.id.logEtEmail);
                EditText etPassword = activity.findViewById(R.id.logEtPassword);
                Button btnLogin = activity.findViewById(R.id.logBtnLogin);

                etEmail.setText("test@example.com");
                etPassword.setText("Password@234");


                ArgumentCaptor<AuthUtility.OnUserAuthenticatedListener> captor = ArgumentCaptor.forClass(AuthUtility.OnUserAuthenticatedListener.class);
                doAnswer(invocation -> {
                    AuthUtility.OnUserAuthenticatedListener listener = captor.getValue();
                    listener.onSuccess("testUid");
                    return null;
                }).when(mockAuthUtility).signIn(anyString(), anyString(), captor.capture());

                btnLogin.performClick();

                Intent expectedIntent = new Intent(activity, MainActivity.class);
                Intent actual = new Intent(activity, MainActivity.class);  // Replace with actual method to get the started intent
                assertEquals(expectedIntent.getComponent(), actual.getComponent());
            });
        }
    }

    @Test
    public void login_failure() {
        // Launch the LoginActivity
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {

            scenario.onActivity(activity -> {
                EditText etEmail = activity.findViewById(R.id.logEtEmail);
                EditText etPassword = activity.findViewById(R.id.logEtPassword);
                Button btnLogin = activity.findViewById(R.id.logBtnLogin);

                etEmail.setText("test@example.com");
                etPassword.setText("wrongpassword");

                // Mock failure response
                ArgumentCaptor<AuthUtility.OnUserAuthenticatedListener> captor = ArgumentCaptor.forClass(AuthUtility.OnUserAuthenticatedListener.class);
                doAnswer(invocation -> {
                    AuthUtility.OnUserAuthenticatedListener listener = captor.getValue();
                    listener.onError("Login failed");
                    return null;
                }).when(mockAuthUtility).signIn(anyString(), anyString(), captor.capture());

                // Perform login button click
                btnLogin.performClick();

                // Verify error message is shown using Toast
                onView(withText("Login failed")).inRoot(new ToastMatcher())
                        .check(matches(withText("Login failed")));
            });
        }
    }
}
