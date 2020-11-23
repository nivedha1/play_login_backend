package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Person;
import models.LoginRepository;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
public class LoginController extends Controller {

    private final FormFactory formFactory;
    private final LoginRepository personRepository;
    private final HttpExecutionContext ec;

    @Inject
    public LoginController(FormFactory formFactory, LoginRepository personRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.personRepository = personRepository;
        this.ec = ec;
    }

    public Result registerUser(Http.Request request) {
        JsonNode req = request.body().asJson();
        String username = req.get("username").asText();
        String password = req.get("password").asText();
        Person person = new Person();
        person.setName(username);
        person.setPassword(password);

        personRepository
                .register(person)
                .thenApplyAsync((Person p) -> {
                    ObjectNode result = null;
                    if (p.id != null) {
                        System.out.println("new user");
                        result = Json.newObject();
                        result.put("body", p.name);
                    }
                    return ok(result);
                });
        return ok("false");

    }



    public Result loginUser(Http.Request request) {
        JsonNode req = request.body().asJson();
        String username = req.get("username").asText();
        String password = req.get("password").asText();
        Person person = new Person();
        person.setName(username);
        person.setPassword(password);
        try {
            personRepository
                    .login(person)
                    .thenApplyAsync((Boolean result) -> {
                        if (result) {
                            return ok("true");
                        } else {
                            return ok("false");
                        }
                    });
        } catch (Exception e) {
            return ok("false");
        }
        return ok("true");

    }


}
