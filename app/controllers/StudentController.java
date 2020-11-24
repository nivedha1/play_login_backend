package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Student;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.ok;


public class StudentController {

    public Result search(Http.Request request) {

        JsonNode req = request.body().asJson();
        String studentname = req.get("studentname").asText();

        try {
            Student found = Student.findByName(studentname); // ( match where username and password both match )
            if(found!=null && studentname.equals(found.studentname)){
                return ok("true");
            }else{
                return ok("false");
            }
        }
        catch (Exception e) {
            return ok("false");
        }

    }


    /**
     * When a student is saved
     * POST
     * @return success if valid, fail if already taken
     */
    public Result addStudent(Http.Request request) {
        System.out.println("Adding student");
        JsonNode req = request.body().asJson();
        String studentname = req.get("studentname").asText();

        Student u = Student.findByName(studentname);
        ObjectNode result = null;
        if (u == null) {
            System.out.println("new student");
            result = Json.newObject();
            Student student = new Student();
            student.studentname=studentname;
            student.save();
            result.put("body", studentname);
        }
        return ok(result);
    }

}
