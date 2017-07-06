package bj.softit.g2sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A OutStock.
 */
@Entity
@Table(name = "out_stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "outstock")
public class OutStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantite", precision=10, scale=2, nullable = false)
    private BigDecimal quantite;

    @Column(name = "datesortir")
    private ZonedDateTime datesortir;

    @Column(name = "projet")
    private String projet;

    @Column(name = "client")
    private String client;

    @Column(name = "cause")
    private String cause;

    @ManyToOne
    private Operant operantos;

    @ManyToOne(optional = false)
    @NotNull
    private Produits produit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public OutStock quantite(BigDecimal quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public ZonedDateTime getDatesortir() {
        return datesortir;
    }

    public OutStock datesortir(ZonedDateTime datesortir) {
        this.datesortir = datesortir;
        return this;
    }

    public void setDatesortir(ZonedDateTime datesortir) {
        this.datesortir = datesortir;
    }

    public String getProjet() {
        return projet;
    }

    public OutStock projet(String projet) {
        this.projet = projet;
        return this;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getClient() {
        return client;
    }

    public OutStock client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCause() {
        return cause;
    }

    public OutStock cause(String cause) {
        this.cause = cause;
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Operant getOperantos() {
        return operantos;
    }

    public OutStock operantos(Operant operant) {
        this.operantos = operant;
        return this;
    }

    public void setOperantos(Operant operant) {
        this.operantos = operant;
    }

    public Produits getProduit() {
        return produit;
    }

    public OutStock produit(Produits produits) {
        this.produit = produits;
        return this;
    }

    public void setProduit(Produits produits) {
        this.produit = produits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OutStock outStock = (OutStock) o;
        if (outStock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), outStock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OutStock{" +
            "id=" + getId() +
            ", quantite='" + getQuantite() + "'" +
            ", datesortir='" + getDatesortir() + "'" +
            ", projet='" + getProjet() + "'" +
            ", client='" + getClient() + "'" +
            ", cause='" + getCause() + "'" +
            "}";
    }
}
