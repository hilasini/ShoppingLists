package com.example.shoppinglists;


 import com.google.firebase.auth.FirebaseAuth;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.mockito.Mock;
 import org.mockito.junit.MockitoJUnitRunner;
 import static junit.framework.Assert.assertEquals;
 import static org.mockito.Mockito.mock;
 import static org.mockito.Mockito.when;

/**
 * Created by hilas_000 on 1/15/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {


    private static final String Email = "hila@gmail.com";
     @Mock
     User user;
     FirebaseAuth mAuth;

    @Test
    public void EmailTest() {
            user = mock(User.class);
            when(user.getEmail()).thenReturn(Email);
            assertEquals(user.getEmail(), Email);
        }
}
