package com.indicai.indicai.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username", nullable = false, unique= true, length = 50)
    private String username;

    @Column(name="password", nullable = false, length = 300)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false, length = 25)
    private Role role = Role.ROLE_EMPLOYEE;

    private int anoNascimento;
    private String cidade;
    private String estado;

    @ManyToMany
    private List<Usuario> amigos;

    public enum Role{
        ROLE_ADMIN, ROLE_EMPLOYEE
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + "]";
    }
}
