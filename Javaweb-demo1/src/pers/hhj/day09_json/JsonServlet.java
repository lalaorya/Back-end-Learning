package pers.hhj.day09_json;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/jsonServlet")
public class JsonServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        String username = req.getParameter("username");
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<>();

        if("zhangsan".equals(username)){
            map.put("userExist",true);
        }else {
            map.put("userExist",false);
        }

        objectMapper.writeValue(resp.getWriter(),map);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
