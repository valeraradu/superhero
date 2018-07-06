package superhero.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperheroRepository extends CrudRepository<Superhero, Long> {

    @Query("select c from Superhero c where c.name = :name")
    Superhero findByName(@Param("name") String name);

    @Query("select c from Superhero c where c.pseudonym = :pseudonym")
    Superhero findByPseudonym(@Param("pseudonym") String pseudonym);

}