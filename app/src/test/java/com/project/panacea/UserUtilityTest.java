package com.project.panacea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.ByteArrayOutputStream;
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class UserUtilityTest {

    @Mock
    private FirebaseAuth mockAuth;
    @Mock
    private FirebaseDatabase mockDatabase;
    @Mock
    private FirebaseStorage mockStorage;
    @Mock
    private DatabaseReference mockDatabaseReference;
    @Mock
    private StorageReference mockStorageReference;
    @Mock
    private FirebaseUser mockFirebaseUser;

    @Mock
    private UploadTask mockUploadTask;
    @Mock
    private Task<Void> mockVoidTask;
    @Mock
    private Task<DataSnapshot> mockDataSnapshotTask;
    @Mock
    private DataSnapshot mockDataSnapshot;
    @Mock
    private Task<byte[]> mockBytesTask;
    @Mock
    private MockedStatic<FirebaseDatabase> mockedFirebaseDatabase;
    @Mock
    private MockedStatic<FirebaseStorage> mockedFirebaseStorage;

    private UserUtility userUtility;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedFirebaseDatabase.when(FirebaseDatabase::getInstance).thenReturn(mockDatabase);
        mockedFirebaseStorage.when(FirebaseStorage::getInstance).thenReturn(mockStorage);

        when(mockAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockFirebaseUser.getUid()).thenReturn("uid123");
        when(mockDatabase.getReference(anyString())).thenReturn(mockDatabaseReference);
        when(mockStorage.getReference()).thenReturn(mockStorageReference);
        when(mockStorageReference.child(anyString())).thenReturn(mockStorageReference);
        when(mockStorageReference.getBytes(anyLong())).thenReturn(mockBytesTask);
        when(mockStorageReference.putBytes(any())).thenReturn(mockUploadTask);
        when(mockUploadTask.getException()).thenReturn(new Exception("Failed to upload profile picture"));
        when(mockDatabaseReference.child(anyString())).thenReturn(mockDatabaseReference);
        when(mockDatabaseReference.setValue(any())).thenReturn(mockVoidTask);
        when(mockDatabaseReference.removeValue()).thenReturn(mockVoidTask);
        when(mockDatabaseReference.get()).thenReturn(mockDataSnapshotTask);
        when(mockDataSnapshotTask.getResult()).thenReturn(mockDataSnapshot);
        when(mockBytesTask.getResult()).thenReturn(new byte[0]);
        when(mockVoidTask.isSuccessful()).thenReturn(true);
        when(mockDataSnapshotTask.isSuccessful()).thenReturn(true);
        when(mockBytesTask.isSuccessful()).thenReturn(true);

        AuthUtility authUtility = new AuthUtility(mockAuth, mockDatabase);
        userUtility = new UserUtility(authUtility, mockDatabase, mockStorage);
    }

    @Test
    public void testCreateUserSuccess() {
        when(mockVoidTask.isSuccessful()).thenReturn(true);
        User user = new User();
        userUtility.createUser(user, new UserUtility.OnUserCreatedListener() {
            @Override
            public void onSuccess(String uid) {
                assertNotNull(uid);
            }

            @Override
            public void onError(String message) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockDatabaseReference).setValue(user);
    }

    @Test
    public void testCreateUserFailure() {
        when(mockVoidTask.isSuccessful()).thenReturn(false);
        when(mockVoidTask.getException()).thenReturn(new Exception("Failed to create user"));
        User user = new User();
        userUtility.createUser(user, new UserUtility.OnUserCreatedListener() {
            @Override
            public void onSuccess(String uid) {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String message) {
                assertEquals("Failed to create user", message);
            }
        });
        verify(mockDatabaseReference).setValue(user);
    }

    @Test
    public void testGetUserSuccess() {
        when(mockDataSnapshotTask.isSuccessful()).thenReturn(true);
        User user = new User();
        when(mockDataSnapshot.getValue(User.class)).thenReturn(user);
        userUtility.getUser("uid123", new UserUtility.OnUserRetrievedListener() {
            @Override
            public void onSuccess(User retrievedUser) {
                assertEquals(user, retrievedUser);
            }

            @Override
            public void onError(String message) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockDatabaseReference).get();
    }

    @Test
    public void testGetUserFailure() {
        when(mockDataSnapshotTask.isSuccessful()).thenReturn(false);
        when(mockDataSnapshotTask.getException()).thenReturn(new Exception("Failed to retrieve user"));
        userUtility.getUser("uid123", new UserUtility.OnUserRetrievedListener() {
            @Override
            public void onSuccess(User user) {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String message) {
                assertEquals("Failed to retrieve user", message);
            }
        });
        verify(mockDatabaseReference).get();
    }

    @Test
    public void testUpdateUserSuccess() {
        when(mockVoidTask.isSuccessful()).thenReturn(true);
        User user = new User();
        userUtility.updateUser("uid123", user, new UserUtility.OnUserUpdatedListener() {
            @Override
            public void onSuccess() {
                assertTrue(true);
            }

            @Override
            public void onError(String message) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockDatabaseReference).setValue(user);
    }

    @Test
    public void testUpdateUserFailure() {
        when(mockVoidTask.isSuccessful()).thenReturn(false);
        when(mockVoidTask.getException()).thenReturn(new Exception("Failed to update user"));
        User user = new User();
        userUtility.updateUser("uid123", user, new UserUtility.OnUserUpdatedListener() {
            @Override
            public void onSuccess() {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String message) {
                assertEquals("Failed to update user", message);
            }
        });
        verify(mockDatabaseReference).setValue(user);
    }

    @Test
    public void testDeleteUserSuccess() {
        when(mockVoidTask.isSuccessful()).thenReturn(true);
        userUtility.deleteUser("uid123", new UserUtility.OnUserDeletedListener() {
            @Override
            public void onSuccess() {
                assertTrue(true);
            }

            @Override
            public void onError(String message) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockDatabaseReference).removeValue();
    }

    @Test
    public void testDeleteUserFailure() {
        when(mockVoidTask.isSuccessful()).thenReturn(false);
        when(mockVoidTask.getException()).thenReturn(new Exception("Failed to delete user"));
        userUtility.deleteUser("uid123", new UserUtility.OnUserDeletedListener() {
            @Override
            public void onSuccess() {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String message) {
                assertEquals("Failed to delete user", message);
            }
        });
        verify(mockDatabaseReference).removeValue();
    }

    @Test
    public void testUploadProfilePicSuccess() {
        //when(mockUploadTask.addOnCompleteListener(any())).thenReturn(mockUploadTask);
        when(mockUploadTask.isSuccessful()).thenReturn(true);
        when(mockUploadTask.getException()).thenReturn(new Exception("Failed to upload profile picture"));
        //get bitmap from drawable image signup_bg.jpg

        Bitmap bitmap = BitmapFactory.decodeByteArray(new byte[0], 0, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        userUtility.uploadProfilePic("uid123", bitmap, new UserUtility.OnProfilePicUploadedListener() {
            @Override
            public void onSuccess() {
                assertTrue(true);
            }

            @Override
            public void onError(String message) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockStorageReference).putBytes(any());
    }

    @Test
    public void testUploadProfilePicFailure() {
        //when(mockUploadTask.addOnCompleteListener(any())).thenReturn(mockUploadTask);
        when(mockUploadTask.isSuccessful()).thenReturn(false);
        when(mockUploadTask.getException()).thenReturn(new Exception("Failed to upload profile picture"));

        Bitmap bitmap = BitmapFactory.decodeByteArray(new byte[0], 0, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        userUtility.uploadProfilePic("uid123", bitmap, new UserUtility.OnProfilePicUploadedListener() {
            @Override
            public void onSuccess() {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String message) {
                assertEquals("Failed to upload profile picture", message);
            }
        });
        verify(mockStorageReference).putBytes(any());
    }

    @Test
    public void testDownloadProfilePicSuccess() {
        when(mockBytesTask.isSuccessful()).thenReturn(true);
        when(mockStorageReference.getBytes(Long.MAX_VALUE)).thenReturn(mockBytesTask);
        userUtility.downloadProfilePic("uid123", new UserUtility.OnProfilePicDownloadedListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                assertNotNull(bitmap);
            }

            @Override
            public void onError(String message) {
                fail("Expected onSuccess, but onError was called");
            }
        });
        verify(mockStorageReference).getBytes(Long.MAX_VALUE);
    }

    @Test
    public void testDownloadProfilePicFailure() {
        when(mockBytesTask.isSuccessful()).thenReturn(false);
        when(mockBytesTask.getException()).thenReturn(new Exception("Failed to download profile picture"));
        userUtility.downloadProfilePic("uid123", new UserUtility.OnProfilePicDownloadedListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                fail("Expected onError, but onSuccess was called");
            }

            @Override
            public void onError(String message) {
                assertEquals("Failed to download profile picture", message);
            }
        });
        verify(mockStorageReference).getBytes(Long.MAX_VALUE);
    }

    @After
    public void tearDown() {
        mockedFirebaseDatabase.close();
        mockedFirebaseStorage.close();
    }
}
