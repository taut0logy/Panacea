package com.project.panacea;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class RegisterActivityTest {
    //@Rule
    //public ActivityScenarioRule<RecordActivity> activityScenarioRule = new ActivityScenarioRule<>(RecordActivity.class);

    private AuthUtility mockAuthUtility;
    private UserUtility mockUserUtility;


    @Before
    public void setUp() throws Exception {
        // Add setup for the test
        MockitoAnnotations.openMocks(this);
        Constructor<AuthUtility> constructor = AuthUtility.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        AuthUtility mockAuthUtility2 = constructor.newInstance();
        mockAuthUtility = spy(mockAuthUtility2);

        Constructor<UserUtility> constructor2 = UserUtility.class.getDeclaredConstructor();
        constructor2.setAccessible(true);
        UserUtility mockUserUtility2 = constructor2.newInstance();
        mockUserUtility = spy(mockUserUtility2);
        try(ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    java.lang.reflect.Field authUtilityField = RegisterActivity.class.getDeclaredField("authUtility");
                    authUtilityField.setAccessible(true);
                    authUtilityField.set(activity, mockAuthUtility);
                    Field userUtilityField = RegisterActivity.class.getDeclaredField("userUtility");
                    userUtilityField.setAccessible(true);
                    userUtilityField.set(activity, mockUserUtility);

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    @Test
    public void testRegisterSuccess() {
        try(ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("tester");
                    etPassword.setText("tester");
                    etConfirmPassword.setText("tester");
                    etUsername.setText("tester");
                    etPhone.setText("tester");
                    etDob.setText("tester");

                    ArgumentCaptor<UserUtility.OnUserCreatedListener> captor = ArgumentCaptor.forClass(UserUtility.OnUserCreatedListener.class);
                    doAnswer(invocation -> {
                        UserUtility.OnUserCreatedListener listener = captor.getValue();
                        listener.onSuccess("testUid");
                        return null;
                    }).when(mockUserUtility).createUser(any(User.class), captor.capture());

                    btnRegister.performClick();
                    //get the intent launched by the activity
//                    Intent expectedIntent = new Intent(activity, MainActivity.class);
//                    Intent actual = new Intent(activity, HomeActivity.class);
//                    assertEquals(expectedIntent.getComponent(), actual.getComponent());
                    assertNotNull(ActivityScenario.launch(HomeActivity.class));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
    }

    @Test
    public void testRegisterFailure() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("tester");
                    etPassword.setText("tester");
                    etConfirmPassword.setText("tester");
                    etUsername.setText("tester");
                    etPhone.setText("tester");
                    etDob.setText("tester");

                    ArgumentCaptor<UserUtility.OnUserCreatedListener> captor = ArgumentCaptor.forClass(UserUtility.OnUserCreatedListener.class);
                    doAnswer(invocation -> {
                        UserUtility.OnUserCreatedListener listener = captor.getValue();
                        listener.onError("Registration failed");
                        return null;
                    }).when(mockUserUtility).createUser(any(User.class), captor.capture());

                    btnRegister.performClick();
                    assertNull(ActivityScenario.launch(HomeActivity.class));
                    //get the intent launched by the activity
                    //Intent expectedIntent = new Intent(activity, MainActivity.class);
                    //Intent actual = new Intent(activity, HomeActivity.class);
                    //assertEquals(expectedIntent.getComponent(), actual.getComponent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    @Test
    public void validateInputSuccessTest() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("tester@testmail.com");
                    etPassword.setText("Tester001@@");
                    etConfirmPassword.setText("Tester001@@");
                    etUsername.setText("tester01");
                    etPhone.setText("01234567891");
                    etDob.setText("01/01/2003");

                    boolean result = activity.validateInput();
                    assertTrue(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    @Test
    public void validateInputFailureTestEmailFormat() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("ff");
                    etPassword.setText("Tester001@@");
                    etConfirmPassword.setText("Tester001@@");
                    etUsername.setText("tester01");
                    etPhone.setText("01234567891");
                    etDob.setText("01/01/2003");

                    boolean result = activity.validateInput();
                    assertFalse(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void validateInputFailureTestUnmatchedPassword() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("tester@test.com");
                    etPassword.setText("Tester001@@");
                    etConfirmPassword.setText("Tester002@@");
                    etUsername.setText("tester01");
                    etPhone.setText("01234567891");
                    etDob.setText("01/01/2003");

                    boolean result = activity.validateInput();
                    assertFalse(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void validateInputFailureTestWrongPasswordFormat() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("test@test.com");
                    etPassword.setText("tester001@@");
                    etConfirmPassword.setText("tester001@@");
                    etUsername.setText("tester01");
                    etPhone.setText("01234567891");
                    etDob.setText("01/01/2003");

                    boolean result = activity.validateInput();
                    assertEquals(false, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void validateInputFailureWrongDateFormat() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("ff");
                    etPassword.setText("Tester001@@");
                    etConfirmPassword.setText("Tester001@@");
                    etUsername.setText("tester01");
                    etPhone.setText("01234567891");
                    etDob.setText("2003/01/2003");

                    boolean result = activity.validateInput();
                    assertFalse(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void validateInputFailureWrongPhoneNumberFormat() {
        try (ActivityScenario<RegisterActivity> scenario = ActivityScenario.launch(RegisterActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    EditText etEmail = activity.findViewById(R.id.regEtEmail);
                    EditText etPassword = activity.findViewById(R.id.regEtPassword);
                    EditText etConfirmPassword = activity.findViewById(R.id.regEtPasswordConf);
                    EditText etUsername = activity.findViewById(R.id.regEtName);
                    EditText etPhone = activity.findViewById(R.id.regEtPhone);
                    EditText etDob = activity.findViewById(R.id.regEtDob);
                    Button btnRegister = activity.findViewById(R.id.regBtnRegister);

                    etEmail.setText("ff");
                    etPassword.setText("Tester001@@");
                    etConfirmPassword.setText("Tester001@@");
                    etUsername.setText("tester01");
                    etPhone.setText("0123456789");
                    etDob.setText("01/01/2003");

                    boolean result = activity.validateInput();
                    assertFalse(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
