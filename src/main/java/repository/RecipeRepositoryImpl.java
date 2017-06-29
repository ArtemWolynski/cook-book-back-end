package repository;


import model.Recipe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional (readOnly = true)
public class RecipeRepositoryImpl implements RecipeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {
        if (recipe.isNew()){
          em.persist(recipe);
          return recipe;
        } else {
           return em.merge(recipe);
        }
    }


    @Override
    public Recipe get(int id) {
        return em.find(Recipe.class, id);
    }

    @Override
    public Recipe findByName(String name) {
        return em.createNamedQuery(Recipe.FIND_BY_NAME, Recipe.class).setParameter("name", name).getSingleResult();
    }

    @Override
    @Transactional
    public void delete(int id) {
        em.remove(em.find(Recipe.class, id));
    }

    @Override
    public List<Recipe> getAll() {
        return em.createNamedQuery(Recipe.GET_ALL, Recipe.class).getResultList();
    }
}
