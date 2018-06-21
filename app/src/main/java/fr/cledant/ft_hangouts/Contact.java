package fr.cledant.ft_hangouts;

public class Contact
{
	private long id;
	private String firstname;
	private String lastname;
	private String surname;
	private String phonenumber;
	private String email;
	private String image_path;

	public Contact(long id, String firstname, String lastname,
				   String surname, String phonenumber, String email,
				   String img_path)
	{
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.surname = surname;
		this.phonenumber = phonenumber;
		this.email = email;
		this.image_path = img_path;
	}

	/*
		Setters
	 */

	public void setId(long id)
	{
		this.id = id;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setImagePath(String img_path)
	{
		this.image_path = img_path;
	}

	/*
		Getters
	 */

	public long getId()
	{
		return this.id;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public String getLastname()
	{
		return this.lastname;
	}

	public String getSurname()
	{
		return this.surname;
	}

	public String getPhonenumber()
	{
		return this.phonenumber;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getImagePath()
	{
		return this.image_path;
	}
}
