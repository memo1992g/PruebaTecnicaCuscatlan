package backend.apiscart.dao;

import backend.apiscart.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuariosDao extends JpaRepository<Usuarios, Long> {

    @Query("SELECT count(u) FROM Usuarios u WHERE u.username = :username AND u.password = :password")
    Integer cantidad (@Param("username") String username, @Param("password") String password);
}
