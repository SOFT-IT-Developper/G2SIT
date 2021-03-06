package bj.softit.g2sit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * This is an example model
 */
@ApiModel(description = "This is an example model")
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "categorie")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_categorie", nullable = false)
    private String nameCategorie;

    @Column(name = "fournisseur")
    private String fournisseur;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "categorie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produits> produits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategorie() {
        return nameCategorie;
    }

    public Categorie nameCategorie(String nameCategorie) {
        this.nameCategorie = nameCategorie;
        return this;
    }

    public void setNameCategorie(String nameCategorie) {
        this.nameCategorie = nameCategorie;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public Categorie fournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getDescription() {
        return description;
    }

    public Categorie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Produits> getProduits() {
        return produits;
    }

    public Categorie produits(Set<Produits> produits) {
        this.produits = produits;
        return this;
    }

    public Categorie addProduit(Produits produits) {
        this.produits.add(produits);
        produits.setCategorie(this);
        return this;
    }

    public Categorie removeProduit(Produits produits) {
        this.produits.remove(produits);
        produits.setCategorie(null);
        return this;
    }

    public void setProduits(Set<Produits> produits) {
        this.produits = produits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Categorie categorie = (Categorie) o;
        if (categorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", nameCategorie='" + getNameCategorie() + "'" +
            ", fournisseur='" + getFournisseur() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
