package io.projectreactor.examples;

/**
 * @author Simon Baslé
 */
public class Sir {

	private String firstName;
	private String lastName;

	public Sir() {
	}

	public Sir(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Sir sir = (Sir) o;

		if (firstName != null ? !firstName.equals(sir.firstName) :
				sir.firstName != null) {
			return false;
		}
		return lastName != null ? lastName.equals(sir.lastName) : sir.lastName == null;

	}

	@Override
	public int hashCode() {
		int result = firstName != null ? firstName.hashCode() : 0;
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}
}