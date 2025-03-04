 
package ms.user.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ms.user.models.User;
 

import jakarta.enterprise.context.RequestScoped;

@RequestScoped 
public class UserDao {

   
    @PersistenceContext(name = "jpa-unit") 
    private EntityManager em;

   
    public void createUser(User user) { 
        em.persist(user); 
    }
  
    public User readUser(int UserId) {
       
        return em.find(User.class, UserId);
       
    }
   
    public void updateUser(User User) {
        
        em.merge(User);
    
    }
 
    public void deleteUser(User User) {
    
        em.remove(User);
   
    }
    
    public List<User> readAllUsers () {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }
  
    public List<User> findUser(String username ) {
        return em.createNamedQuery("User.findUser", User.class)
            .setParameter("username", username)
         .getResultList();
    }
 
}
 
