package test;

import Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import servlet.UserServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServletTest {

    @InjectMocks
    private UserServlet userServlet;

    @Mock
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoPost_UserRegistration_Success() throws Exception {
        UserDto userDTO = new UserDto();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");
        userDTO.setAdmin(false);

//        when(request.getInputStream()).thenReturn(new DelegetingServletInputStream(new ByteArrayInputStream(objectMapper.writeValueAsBytes(userDTO))));
        when(userService.registerUser(userDTO).thenReturn(true);

        userServlet.doPost(request, response);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    public void testDoGet_UserNotFound() throws Exception {
        when(request.getParameter("username")).thenReturn("unknownUser");
        when(userService.getUser("unknownUser")).thenReturn(null);

        userServlet.doGet(request, response);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);

