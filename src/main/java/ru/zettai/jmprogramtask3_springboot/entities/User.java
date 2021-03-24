package ru.zettai.jmprogramtask3_springboot.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.zettai.jmprogramtask3_springboot.services.StringListConverter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, message = "Не меньше 5 знаков" )
    @Column(name = "username")
    private String username;

    @Size(min=1, message = "Не допускается пустой пароль")
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirmation;

    @NotBlank(message = "Имя пользователя не должно быть пустым!!")
    @Column(name = "firstname")
    private String firstName;

    @NotBlank(message = "Фамилия пользователя не должна быть пустой!")
    @Column(name = "surname")
    private String surName;

    @Min(value = 1, message = "Вам не может быть 0 или меньше лет")
    @Max(value = 150, message = "Если Вам больше 150, то вы вероятно горец или не человек.")
    @Column(name = "age")
    private int age;

    @NotBlank(message = "Поле должно содержать значение!")
    @Email(message = "Некорректный ввод почты")
    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    @Convert(converter = StringListConverter.class)
    private List<Role> roles;

    public User() {
        roles = new ArrayList<>();
    }

    public User(String username, String surname, int age, String email) {
        this.username = username;
        this.surName = surname;
        this.age = age;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}