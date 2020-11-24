package models;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    public String username;

    @Constraints.Required
    public String password;

    public static Finder<Long, User> find = new Finder<>(User.class){};

    public static User findByName(String name) {
        return find.query()
                .where()
                .eq("username", name)
                .findOne();
    }
}
