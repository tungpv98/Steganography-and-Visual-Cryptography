package net.javaguides.springboot.springsecurity.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String scretKey;
    private String share2;
    private String bankNumber;
    private Integer totalMoney;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    

    public User() {
		super();
	}



	public User(String firstName, String lastName, String email, String password, String scretKey, String share2,
			String bankNumber, Integer totalMoney, Collection<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.scretKey = scretKey;
		this.share2 = share2;
		this.bankNumber = bankNumber;
		this.totalMoney = totalMoney;
		this.roles = roles;
	}



	public User(Long id, String firstName, String lastName, String email, String password, String scretKey,
			String share2, String bankNumber, Integer totalMoney, Collection<Role> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.scretKey = scretKey;
		this.share2 = share2;
		this.bankNumber = bankNumber;
		this.totalMoney = totalMoney;
		this.roles = roles;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getScretKey() {
		return scretKey;
	}



	public void setScretKey(String scretKey) {
		this.scretKey = scretKey;
	}



	public String getShare2() {
		return share2;
	}



	public void setShare2(String share2) {
		this.share2 = share2;
	}



	public String getBankNumber() {
		return bankNumber;
	}



	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}



	public Integer getTotalMoney() {
		return totalMoney;
	}



	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}



	public Collection<Role> getRoles() {
		return roles;
	}



	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}



	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "*********" + '\'' +
                ", roles=" + roles +
                '}';
    }
}
