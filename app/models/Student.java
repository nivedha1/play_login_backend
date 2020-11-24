package models;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Student extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    public String studentname;

    public static Finder<Long, Student> find = new Finder<>(Student.class){};

    public static Student findByName(String name) {
        return find.query()
                .where()
                .eq("studentname", name)
                .findOne();
    }
}
