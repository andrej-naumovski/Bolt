package mk.edu.ukim.feit.bolt.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by gjorgjim on 8/7/17.
 */
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_interest",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "interest_id", referencedColumnName = "id"))
    private Set<Interest> interests;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "friends",
        joinColumns = @JoinColumn(name = "first_user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "second_user_id", referencedColumnName = "id"))
    private Set<User> friends;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "friend_requests",
        joinColumns = @JoinColumn(name = "sender_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "receiver_id", referencedColumnName = "id"))
    private Set<User> friendRequestsSent;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "friend_requests",
            joinColumns = @JoinColumn(name = "receiver_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sender_id", referencedColumnName = "id"))
    private Set<User> friendRequestsReceived;

    @JsonIgnore
    public Set<User> getFriendRequestsSent() {
        return friendRequestsSent;
    }

    public void setFriendRequestsSent(Set<User> friendRequestsSent) {
        this.friendRequestsSent = friendRequestsSent;
    }

    @JsonIgnore
    public Set<User> getFriendRequestsReceived() {
        return friendRequestsReceived;
    }

    public void setFriendRequestsReceived(Set<User> friendRequestsReceived) {
        this.friendRequestsReceived = friendRequestsReceived;
    }

    @JsonIgnore
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", authorities=" + authorities +
                ", contact=" + contact +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        User u = (User) obj;
        if(this.id.equals(u.getId())
                && this.username.equals(u.getUsername())) {
            return true;
        }
        return false;
    }
}
