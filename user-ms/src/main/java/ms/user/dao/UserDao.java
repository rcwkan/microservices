 
package ms.user.dao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ms.user.models.UserAccount;

@RequestScoped 
public class UserDao {

   
    @PersistenceContext(name = "jpa-unit") 
    private EntityManager em;

   
    public void createUser(UserAccount user) { 
        em.persist(user); 
    }
  
    public UserAccount readUser(int UserId) {
       
        return em.find(UserAccount.class, UserId);
       
    }
   
    public void updateUser(UserAccount User) {
        
        em.merge(User);
    
    }
 
    public void deleteUser(UserAccount User) {
    
        em.remove(User);
   
    }
    
    public List<UserAccount> readAllUsers () {
        return em.createNamedQuery("UserAccount.findAll", UserAccount.class).getResultList();
    }
  
    public List<UserAccount> findUser(String username ) {
        return em.createNamedQuery("UserAccount.findUser", UserAccount.class)
            .setParameter("username", username)
         .getResultList();
    }
 
}
 
