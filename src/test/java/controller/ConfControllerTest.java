//package controller;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import conference.App;
//import conference.controller.ConfController;
//import conference.repository.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = App.class)
//@AutoConfigureMockMvc
//public class ConfControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {
//
//        this.mockMvc.perform(get("/schedule")).andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.[0].userName").value("UserA"));
//    }
//}
