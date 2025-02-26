package io.onedev.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.onedev.server.util.CryptoUtils;
import io.onedev.server.util.facade.EmailAddressFacade;
import io.onedev.server.annotation.Editable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Editable
@Entity
@Table(indexes={@Index(columnList="o_owner_id"), @Index(columnList="value")})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EmailAddress extends AbstractEntity {
    
    private static final long serialVersionUID = 1L;
    
    public static final String PROP_OWNER = "owner";
    
    public static final String PROP_VALUE = "value";
    
    @Column(nullable=false, unique=true)
    private String value;
    
    @JsonIgnore
    private String verificationCode = CryptoUtils.generateSecret();
    
    private boolean primary;
    
    private boolean git;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(nullable=false)
    private User owner;

    @Editable
    @Email
    @NotEmpty
    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Editable
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Editable
	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	@Editable
	public boolean isGit() {
		return git;
	}

	public void setGit(boolean git) {
		this.git = git;
	}

	public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

	public boolean isVerified() {
    	return getVerificationCode() == null;
    }

	@Override
	public EmailAddressFacade getFacade() {
		return new EmailAddressFacade(getId(), getValue(), isPrimary(), isGit(), 
				getVerificationCode(), getOwner().getId());
	}
	
}
