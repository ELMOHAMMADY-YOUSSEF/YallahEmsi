package emsi.ma.model;

public class Utilisateur {

		//les attribut li eandy f base de donner 
		private int id;
	    private String nom;
	    private String prenom;
	    private String email;
	    private String motDePasse;
	    private String telephone;
	    
	    private String cne;
	    private String filiere;
	    private String niveau;
	    private String role;
	    
	    //hada constructeur par defaut drto vid darori
	    public Utilisateur() {
	    }
	    
	    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, String telephone, String cne, String filiere, String niveau, String role) {
	        this.id = id;
	        this.nom = nom;
	        this.prenom = prenom;
	        this.email = email;
	        this.motDePasse = motDePasse;
	        this.telephone = telephone;
	        this.cne = cne;
	        this.filiere = filiere;
	        this.niveau = niveau;
	        this.role = role;
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getPrenom() {
			return prenom;
		}

		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMotDePasse() {
			return motDePasse;
		}

		public void setMotDePasse(String motDePasse) {
			this.motDePasse = motDePasse;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getCne() {
			return cne;
		}

		public void setCne(String cne) {
			this.cne = cne;
		}

		public String getFiliere() {
			return filiere;
		}

		public void setFiliere(String filiere) {
			this.filiere = filiere;
		}

		public String getNiveau() {
			return niveau;
		}

		public void setNiveau(String niveau) {
			this.niveau = niveau;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
	    
	    
	    
	    
	    
}


