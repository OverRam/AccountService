package account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"period", "user_email"})})
public class Payroll implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long salary;

    private String period;

    @ManyToOne
    @JoinColumn(name = "user_email", columnDefinition = "varchar_ignorecase(255) NOT NULL")
    private User userEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payroll payroll = (Payroll) o;
        return id != null && Objects.equals(id, payroll.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}