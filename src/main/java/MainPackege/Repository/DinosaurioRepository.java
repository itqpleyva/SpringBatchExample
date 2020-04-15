package MainPackege.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MainPackege.model.Dinosaurio;

public interface DinosaurioRepository extends JpaRepository<Dinosaurio, Integer>   {

}
