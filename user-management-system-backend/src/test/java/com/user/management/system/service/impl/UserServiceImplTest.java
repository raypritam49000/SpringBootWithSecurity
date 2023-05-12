package com.user.management.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.user.management.system.dto.UserDTO;
import com.user.management.system.entity.User;
import com.user.management.system.exception.AlreadyExistsException;
import com.user.management.system.exception.ResourceNotFoundException;
import com.user.management.system.jwt.request.JwtRequest;
import com.user.management.system.repository.UserRepository;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#saveUser(UserDTO)}
     */
    @Test
    void testSaveUser() {
        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        when(modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn("Map");
        when(modelMapper.map((Object) any(), (Class<User>) any())).thenReturn(user);

        User user1 = new User();
        user1.setEmailId("42");
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");

        User user2 = new User();
        user2.setEmailId("42");
        user2.setFirstName("Jane");
        user2.setId("42");
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmailId((String) any())).thenReturn(ofResult);
        assertThrows(AlreadyExistsException.class,
                () -> userServiceImpl.saveUser(new UserDTO("42", "Jane", "Doe", "42", "iloveyou")));
        verify(modelMapper).map((Object) any(), (Class<Object>) any());
        verify(userRepository).findByEmailId((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(String, UserDTO)}
     */
    @Test
    void testUpdateUser() {
        UserDTO userDTO = new UserDTO("42", "Jane", "Doe", "42", "iloveyou");

        when(modelMapper.map((Object) any(), (Class<UserDTO>) any())).thenReturn(userDTO);

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmailId("42");
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertSame(userDTO, userServiceImpl.updateUser("42", new UserDTO("42", "Jane", "Doe", "42", "iloveyou")));
        verify(modelMapper).map((Object) any(), (Class<UserDTO>) any());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(String, UserDTO)}
     */
    @Test
    void testUpdateUser2() {
        when(modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn("Map");
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenReturn(new UserDTO("42", "Jane", "Doe", "42", "iloveyou"));

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenThrow(new AlreadyExistsException("An error occurred"));
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(AlreadyExistsException.class,
                () -> userServiceImpl.updateUser("42", new UserDTO("42", "Jane", "Doe", "42", "iloveyou")));
        verify(modelMapper).map((Object) any(), (Class<Object>) any());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(String, UserDTO)}
     */
    @Test
    void testUpdateUser3() {
        when(modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn("Map");
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenReturn(new UserDTO("42", "Jane", "Doe", "42", "iloveyou"));
        when(userRepository.save((User) any())).thenThrow(new AlreadyExistsException("An error occurred"));
        when(userRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updateUser("42", new UserDTO("42", "Jane", "Doe", "42", "iloveyou")));
        verify(modelMapper).map((Object) any(), (Class<Object>) any());
        verify(userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(String, UserDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.user.management.system.service.impl.UserServiceImpl.updateUser(UserServiceImpl.java:53)
        //   See https://diff.blue/R013 to resolve this issue.

        when(modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn("Map");
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenReturn(new UserDTO("42", "Jane", "Doe", "42", "iloveyou"));

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenThrow(new AlreadyExistsException("An error occurred"));
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        userServiceImpl.updateUser("42", null);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getUsers().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers2() {
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenReturn(new UserDTO("42", "Jane", "Doe", "42", "iloveyou"));

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(1, userServiceImpl.getUsers().size());
        verify(modelMapper).map((Object) any(), (Class<UserDTO>) any());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers3() {
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenReturn(new UserDTO("42", "Jane", "Doe", "42", "iloveyou"));

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");

        User user1 = new User();
        user1.setEmailId("42");
        user1.setFirstName("Jane");
        user1.setId("42");
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(2, userServiceImpl.getUsers().size());
        verify(modelMapper, atLeast(1)).map((Object) any(), (Class<UserDTO>) any());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers4() {
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenThrow(new AlreadyExistsException("An error occurred"));

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        assertThrows(AlreadyExistsException.class, () -> userServiceImpl.getUsers());
        verify(modelMapper).map((Object) any(), (Class<UserDTO>) any());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(String)}
     */
    @Test
    void testGetUserById() {
        UserDTO userDTO = new UserDTO("42", "Jane", "Doe", "42", "iloveyou");

        when(modelMapper.map((Object) any(), (Class<UserDTO>) any())).thenReturn(userDTO);

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertSame(userDTO, userServiceImpl.getUserById("42"));
        verify(modelMapper).map((Object) any(), (Class<UserDTO>) any());
        verify(userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(String)}
     */
    @Test
    void testGetUserById2() {
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenThrow(new AlreadyExistsException("An error occurred"));

        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(AlreadyExistsException.class, () -> userServiceImpl.getUserById("42"));
        verify(modelMapper).map((Object) any(), (Class<UserDTO>) any());
        verify(userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(String)}
     */
    @Test
    void testGetUserById3() {
        when(modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn("Map");
        when(modelMapper.map((Object) any(), (Class<UserDTO>) any()))
                .thenReturn(new UserDTO("42", "Jane", "Doe", "42", "iloveyou"));
        when(userRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getUserById("42"));
        verify(modelMapper).map((Object) any(), (Class<Object>) any());
        verify(userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(String)}
     */
    @Test
    void testDeleteUserById() {
        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertTrue(userServiceImpl.deleteUserById("42"));
        verify(userRepository).findById((String) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(String)}
     */
    @Test
    void testDeleteUserById2() {
        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        doThrow(new AlreadyExistsException("An error occurred")).when(userRepository).delete((User) any());
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(AlreadyExistsException.class, () -> userServiceImpl.deleteUserById("42"));
        verify(userRepository).findById((String) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(String)}
     */
    @Test
    void testDeleteUserById3() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUserById("42"));
        verify(userRepository).findById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#authenticateUser(JwtRequest)}
     */
    @Test
    void testAuthenticateUser() {
        User user = new User();
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setId("42");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmailId((String) any())).thenReturn(ofResult);
        userServiceImpl.authenticateUser(new JwtRequest("42", "iloveyou"));
        verify(userRepository).findByEmailId((String) any());
    }
}

