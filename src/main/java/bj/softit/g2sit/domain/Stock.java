package bj.softit.g2sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Stock.
 */
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "select s from Stock s")
})
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite", precision=10, scale=2)
    private BigDecimal quantite;

    @Column(name = "description")
    private String description;

    @Column(name = "dateentrer")
    private ZonedDateTime dateentrer;

    @Column(name = "retour")
    private Boolean retour;

    @Column(name = "jhi_comment")
    private String comment;

    @OneToOne
    @JoinColumn(unique = true)
    private Produits produit;

    @ManyToOne
    private Operant operantst;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public Stock quantite(BigDecimal quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public Stock description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDateentrer() {
        return dateentrer;
    }

    public Stock dateentrer(ZonedDateTime dateentrer) {
        this.dateentrer = dateentrer;
        return this;
    }

    public void setDateentrer(ZonedDateTime dateentrer) {
        this.dateentrer = dateentrer;
    }

    public Boolean isRetour() {
        return retour;
    }

    public Stock retour(Boolean retour) {
        this.retour = retour;
        return this;
    }

    public void setRetour(Boolean retour) {
        this.retour = retour;
    }

    public String getComment() {
        return comment;
    }

    public Stock comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Produits getProduit() {
        return produit;
    }

    public Stock produit(Produits produits) {
        this.produit = produits;
        return this;
    }

    public void setProduit(Produits produits) {
        this.produit = produits;
    }

    public Operant getOperantst() {
        return operantst;
    }

    public Stock operantst(Operant operant) {
        this.operantst = operant;
        return this;
    }

    public void setOperantst(Operant operant) {
        this.operantst = operant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        if (stock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", quantite='" + getQuantite() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateentrer='" + getDateentrer() + "'" +
            ", retour='" + isRetour() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
