package com.project.panacea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

//@Config(sdk = 28)
//@RunWith(RobolectricTestRunner.class)
public class AuthUtilityTest {
    @Mock
    private FirebaseAuth mockAuth;
    @Mock
    private FirebaseDatabase mockDatabase;
    @Mock
    private Task<AuthResult> mockAuthResultTask;
    @Mock
    private Task<Void> mockVoidTask;
    @Mock
    private FirebaseUser mockFirebaseUser;

    @Mock
    private MockedStatic<FirebaseAuth> mockedFirebaseAuth;

    @Mock
    private MockedStatic<FirebaseDatabase> mockedFirebaseDatabase;

    private AuthUtility authUtility;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(mockAuth);
        mockedFirebaseDatabase.when(FirebaseDatabase::getInstance).thenReturn(mockDatabase);
        when(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);
        when(mockFirebaseUser.delete()).thenReturn(mockVoidTask);
        when(mockAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockFirebaseUser.getUid()).thenReturn("uid123");
        when(mockAuthResultTask.isSuccessful()).thenReturn(true);
        when(mockAuthResultTask.getException()).thenReturn(new Exception("Authentication failed"));

        authUtility = new AuthUtility(mockAuth, mockDatabase);
    }


    @Test
    public void test() {
        int x=1;
        assertEquals(1, x);
    }

    @Test
    public void authUtilityInstanceTest() {
        AuthUtility authUtility = AuthUtility.getInstance();
        assertNotNull(authUtility);
        AuthUtility authUtility2 = AuthUtility.getInstance();
        assertEquals(authUtility.hashCode(),authUtility2.hashCode());
    }

    @Test

    public void testSignUpSuccess() {
        authUtility.signUp("test@example.com", "password123", new AuthUtility.OnUserCreatedListener() {
            @Override
            public void onSuccess(String uid) {
                assertEquals("uid123", uid);
            }

            @Override
            public void onError(String error) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockAuth).createUserWithEmailAndPassword("test@example.com", "password123");
    }

    @Test
    public void testSignUpFailure() {
        when(mockAuthResultTask.isSuccessful()).thenReturn(false);

        authUtility.signUp("fail@example.com", "password", new AuthUtility.OnUserCreatedListener() {
            @Override
            public void onSuccess(String uid) {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String error) {
                assertNotNull(error);
            }
        });
    }

    @Test
    public void testGetUserUid() {
        authUtility.getUserUid(new AuthUtility.OnUserUidRetrievedListener() {
            @Override
            public void onSuccess(String uid) {
                assertEquals("uid123", uid);
            }

            @Override
            public void onError(String error) {
                fail("Expected onSuccess, but onError was called");
            }
        });
    }

    @Test
    public void testSignOut() {
        doNothing().when(mockAuth).signOut();
        authUtility.signOut(new AuthUtility.OnUserSignedOutListener() {
            @Override
            public void onSuccess() {
                assertTrue(true); // Just check that it completes.
            }

            @Override
            public void onError(String error) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockAuth).signOut();
    }

    @Test
    public void testDeleteAccountSuccess() {
        when(mockVoidTask.isSuccessful()).thenReturn(true);
        authUtility.deleteAccount(new AuthUtility.OnUserDeletedListener() {
            @Override
            public void onSuccess() {
                assertTrue(true);
            }

            @Override
            public void onError(String error) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockFirebaseUser).delete();
    }

    @Test
    public void testDeleteAccountFailure() {
        when(mockVoidTask.isSuccessful()).thenReturn(false);

        authUtility.deleteAccount(new AuthUtility.OnUserDeletedListener() {
            @Override
            public void onSuccess() {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String error) {
                assertNotNull(error);
            }
        });
    }

    @After
    public void tearDown() {
        mockedFirebaseAuth.close();
        mockedFirebaseDatabase.close();
        mockAuth = null;
        mockFirebaseUser = null;
        authUtility = null;
    }

}